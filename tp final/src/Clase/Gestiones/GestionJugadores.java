package Clase.Gestiones;

import Clase.Json.JSONUtiles;
import Clase.Persona.EstadisticaJugador;
import Clase.Persona.Jugador;
import Clase.Presupuesto.Contrato;
import enums.Posicion;
import exeptions.*;
import interfaz.MetodosComunes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class GestionJugadores implements MetodosComunes<Jugador, String> {

    private final HashMap<String, Jugador> jugadores = new HashMap<>();
    private final HashMap<String, EstadisticaJugador> estadisticas = new HashMap<>();
    private final GestionPresupuesto gestorpresupuesto;

    public GestionJugadores(GestionPresupuesto gestorpresupuesto) {
        this.gestorpresupuesto = gestorpresupuesto;
    }

    public void agregarJugador(String dni, String nombre, String apellido, LocalDate fechaNacimiento,
                               String nacionalidad, int numeroCamiseta, double valorJugador,
                               double salario, LocalDate fechaInicioContrato, LocalDate fechaFinContrato,
                               Posicion posicion)
            throws ElementoDuplicadoEx, IngresoInvalido, FondoInsuficienteEx {

        if (dni == null || dni.isBlank()) {
            throw new IngresoInvalido("DNI no puede ser nulo o vacío");
        }

        if (jugadores.containsKey(dni)) {
            throw new ElementoDuplicadoEx("Ya existe un jugador con ese DNI");
        }

        if (fechaNacimiento == null || fechaNacimiento.plusYears(14).isAfter(LocalDate.now())) {
            throw new IngresoInvalido("El jugador debe tener al menos 14 años");
        }

        if (numeroCamiseta < 1 || numeroCamiseta > 99) {
            throw new IngresoInvalido("Número de camiseta debe estar entre 1 y 99");
        }

        boolean camisetaOcupada = jugadores.values().stream()
                .anyMatch(j -> j.getNumeroCamiseta() == numeroCamiseta);
        if (camisetaOcupada) {
            throw new ElementoDuplicadoEx("El número de camiseta ya está en uso");
        }

        if (valorJugador <= 0) {
            throw new IngresoInvalido("El valor del jugador debe ser mayor a 0");
        }

        if (salario <= 0) {
            throw new IngresoInvalido("El salario debe ser mayor a 0");
        }

        if (fechaInicioContrato == null || fechaFinContrato == null) {
            throw new IngresoInvalido("Las fechas del contrato no pueden ser nulas");
        }

        if (fechaFinContrato.isBefore(fechaInicioContrato)) {
            throw new IngresoInvalido("Fecha de fin de contrato no puede ser anterior a la fecha de inicio");
        }

        if (gestorpresupuesto.verSaldoActual() < salario) {
            throw new FondoInsuficienteEx("Fondos insuficientes para pagar el contrato inicial");
        }

        gestorpresupuesto.quitarFondos(
                salario,
                "Contrato jugador " + nombre + " " + apellido,
                fechaInicioContrato
        );

        Contrato contrato = new Contrato(dni, salario, fechaInicioContrato, fechaFinContrato, fechaNacimiento);

        Jugador jugador = new Jugador(dni, nombre, apellido, fechaNacimiento, nacionalidad,
                numeroCamiseta, contrato, posicion, valorJugador);

        jugadores.put(dni, jugador);
        estadisticas.put(dni, new EstadisticaJugador());
        guardarJSON();
    }

    @Override
    public void eliminarElemento(String dni) throws AccionImposible {
        if (!jugadores.containsKey(dni)) {
            throw new AccionImposible("El jugador no existe");
        }
        jugadores.remove(dni);
        estadisticas.remove(dni);
        guardarJSON();
        System.out.println("Jugador eliminado correctamente");
    }

    @Override
    public Jugador devuelveElemento(String dni) throws AccionImposible {
        Jugador jugador = jugadores.get(dni);
        if (jugador == null) {
            throw new AccionImposible("Jugador no encontrado");
        }
        return jugador;
    }

    @Override
    public boolean existe(String dni) throws ElementoInexistenteEx {
        if (!jugadores.containsKey(dni)) {
            throw new ElementoInexistenteEx("El jugador no existe");
        }
        return true;
    }

    public boolean existeJugador(String dni) {
        return jugadores.containsKey(dni);
    }


    @Override
    public ArrayList<Jugador> listar() {
        return new ArrayList<>(jugadores.values());
    }

    public ArrayList<String> listarJugadoresInfo() {
        ArrayList<String> listaInfo = new ArrayList<>();
        for (Jugador j : jugadores.values()) {
            listaInfo.add(
                    j.getDni() + " - " + j.getNombre() + " " + j.getApellido() +
                            " | " + j.getPosicion() +
                            " | Nº " + j.getNumeroCamiseta()
            );
        }
        return listaInfo;
    }


    @Override
    public void guardarJSON() {
        JSONArray array = new JSONArray();

        for (Jugador j : jugadores.values()) {
            JSONObject obj = new JSONObject();
            obj.put("dni", j.getDni());
            obj.put("nombre", j.getNombre());
            obj.put("apellido", j.getApellido());
            obj.put("fechaNacimiento", j.getFechaNacimiento().toString());
            obj.put("nacionalidad", j.getNacionalidad());
            obj.put("numeroCamiseta", j.getNumeroCamiseta());
            obj.put("valorJugador", j.getValorJugador());
            obj.put("posicion", j.getPosicion().toString());

            Contrato c = j.getContrato();
            if (c != null) {
                JSONObject contratoJSON = new JSONObject();
                contratoJSON.put("dni", c.getDni());
                contratoJSON.put("salario", c.getSalario());
                contratoJSON.put("fechaInicio", c.getFechaInicio().toString());
                contratoJSON.put("fechaFin", c.getFechaFin().toString());
                contratoJSON.put("contratoActivo", c.isContratoActivo());
                obj.put("contrato", contratoJSON);
            }

            EstadisticaJugador stats = estadisticas.get(j.getDni());
            if (stats != null) obj.put("estadisticas", stats.toString());

            array.put(obj);
        }

        JSONUtiles.uploadJSON(array, "Plantel");
    }

    public void actualizarEstadisticas(String dni, int goles, int asistencias, int vallasInvictas)
            throws ElementoInexistenteEx, IngresoInvalido {

        EstadisticaJugador stats = estadisticas.get(dni);
        if (stats == null) throw new ElementoInexistenteEx("Estadísticas no encontradas para ese jugador");

        if (goles < 0 || asistencias < 0 || vallasInvictas < 0) {
            throw new IngresoInvalido("Las estadísticas no pueden ser negativas");
        }

        stats.agregarGoles(goles);
        stats.agregarAsistencias(asistencias);

        Jugador jugador = jugadores.get(dni);
        if (jugador != null && jugador.getPosicion() == Posicion.ARQUERO) {
            stats.agregarVallasInvictas(vallasInvictas);
        }

        guardarJSON();
    }

    public double calcularGastoSalarios() {
        return jugadores.values().stream()
                .mapToDouble(j -> j.getContrato().getSalario())
                .sum();
    }

    public void pagarSalarios(LocalDate fecha) throws FondoInsuficienteEx, IngresoInvalido {
        double monto = calcularGastoSalarios();

        if (monto <= 0) {
            throw new IngresoInvalido("No hay salarios para pagar");
        }

        if (gestorpresupuesto.verSaldoActual() < monto) {
            throw new FondoInsuficienteEx("Saldo insuficiente para pagar salarios");
        }

        gestorpresupuesto.quitarFondos(monto, "Pago sueldos plantel", fecha);
        System.out.println("Salarios pagados correctamente");
    }

    public String mostrarJugador(String dni) throws AccionImposible {
        Jugador j = jugadores.get(dni);
        if (j == null) throw new AccionImposible("Jugador no encontrado");

        return j.getDni() + " - " + j.getNombre() + " " + j.getApellido()
                + " | " + j.getPosicion() + " | Nº " + j.getNumeroCamiseta()
                + " | Valor: " + j.getValorJugador()
                + " | Salario: " + j.getContrato().getSalario()
                + " | Contrato: " + j.getContrato().getFechaInicio() + " a " + j.getContrato().getFechaFin();
    }

}
