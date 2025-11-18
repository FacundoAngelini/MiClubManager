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

        if (jugadores.containsKey(dni)) {
            throw new ElementoDuplicadoEx("ya existe un jugador con ese dni");
        }

        if (fechaNacimiento.plusYears(14).isAfter(LocalDate.now())) {
            throw new IngresoInvalido("el jugador debe tener al menos 14 anos");
        }

        if (numeroCamiseta < 1 || numeroCamiseta > 99) {
            throw new IngresoInvalido("numero de camiseta debe estar entre 1 y 99");
        }

        boolean camisetaOcupada = jugadores.values().stream()
                .anyMatch(j -> j.getNumeroCamiseta() == numeroCamiseta);

        if (camisetaOcupada) {
            throw new ElementoDuplicadoEx("el numero de camiseta ya esta en uso");
        }

        if (valorJugador <= 0) {
            throw new IngresoInvalido("el valor del jugador debe ser mayor a 0");
        }

        if (salario <= 0) {
            throw new IngresoInvalido("el salario debe ser mayor a 0");
        }

        if (fechaInicioContrato == null || fechaFinContrato == null) {
            throw new IngresoInvalido("las fechas del contrato no pueden ser nulas");
        }

        if (fechaFinContrato.isBefore(fechaInicioContrato)) {
            throw new IngresoInvalido("fecha de fin no puede ser anterior a inicio");
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

        System.out.println("jugador agregado correctamente");
    }

    @Override
    public void eliminarElemento(String dni) throws AccionImposible {
        if (!jugadores.containsKey(dni)) {
            throw new AccionImposible("el jugador no existe");
        }
        jugadores.remove(dni);
        estadisticas.remove(dni);
        guardarJSON();
        System.out.println("jugador eliminado correctamente");
    }

    @Override
    public Jugador devuelveElemento(String dni) throws AccionImposible {
        Jugador jugador = jugadores.get(dni);
        if (jugador == null) {
            throw new AccionImposible("jugador no encontrado");
        }
        return jugador;
    }

    @Override
    public boolean existe(String dni) throws ElementoInexistenteEx {
        if (!jugadores.containsKey(dni)) {
            throw new ElementoInexistenteEx("el jugador no existe");
        }
        return true;
    }

    @Override
    public ArrayList<Jugador> listar() {
        return new ArrayList<>(jugadores.values());
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
            throws ElementoInexistenteEx {

        EstadisticaJugador stats = estadisticas.get(dni);
        if (stats == null) throw new ElementoInexistenteEx("estadisticas no encontradas para ese jugador");

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
            throw new IngresoInvalido("no hay salarios para pagar");
        }

        if (gestorpresupuesto.verSaldoActual() < monto) {
            throw new FondoInsuficienteEx("saldo insuficiente");
        }

        gestorpresupuesto.quitarFondos(monto, "Pago sueldos plantel", fecha);
        System.out.println("salarios pagados correctamente");
    }
}
