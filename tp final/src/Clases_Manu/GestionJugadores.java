package Clases_Manu;

import enums.Posicion;
import exeptions.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GestionJugadores implements MetodosComunes<Jugador, String> {

    private HashMap<String, Jugador> jugadores = new HashMap<>();
    private HashMap<String, EstadisticaJugador> estadisticas = new HashMap<>();
    private GestionPresupuesto gestorpresupuesto;

    public GestionJugadores(GestionPresupuesto gestorpresupuesto) {
        this.gestorpresupuesto = gestorpresupuesto;
    }

    public void agregarJugador(String dni, String nombre, String apellido, String fechaNacimiento, String nacionalidad, int numeroCamiseta, double valorJugador, double salario, String fechaInicio, String fechaFin, int mesesDuracion, Posicion posicion) throws ElementoDuplicadoEx {

        if (jugadores.containsKey(dni)) {
            throw new ElementoDuplicadoEx("El DNI ya está registrado.");
        }

        boolean camisetaOcupada = jugadores.values().stream().anyMatch(j -> j.getNumeroCamiseta() == numeroCamiseta);
        if (camisetaOcupada) {
            throw new ElementoDuplicadoEx("El número de camiseta ya está ocupado.");
        }

        Contrato contrato = new Contrato(dni, salario, fechaFin, true, fechaInicio, mesesDuracion);
        Jugador jugador = new Jugador(dni, nombre, apellido, fechaNacimiento, nacionalidad, numeroCamiseta, contrato, posicion);
        jugador.setValorJugador(valorJugador);

        jugadores.put(dni, jugador);
        estadisticas.put(dni, new EstadisticaJugador()); // Inicializa estadísticas
    }

    @Override
    public void eliminarElemento(String dni) throws AccionImposible {
        if (!jugadores.containsKey(dni)) throw new AccionImposible("El jugador no existe.");
        jugadores.remove(dni);
        estadisticas.remove(dni);
    }

    @Override
    public Jugador devuelveElemento(String dni) throws AccionImposible {
        Jugador jugador = jugadores.get(dni);
        if (jugador == null) throw new AccionImposible("Clases_Manu.Jugador no encontrado con DNI " + dni);
        return jugador;
    }

    public void cambiarEstadoContrato(String dni, boolean nuevoEstado) throws ElementoInexistenteEx {
        Jugador jugador = jugadores.get(dni);
        if (jugador == null) throw new ElementoInexistenteEx("El jugador no existe.");
        Contrato contrato = jugador.getContrato();
        if (contrato == null) throw new ElementoInexistenteEx("El jugador no tiene contrato asignado.");
        contrato.setContratoActivo(nuevoEstado);
        jugadores.put(dni, jugador);
    }

    public boolean existe(String dni) {
        return jugadores.containsKey(dni);
    }

    @Override
    public ArrayList<Jugador> listar() {
        Collection<Jugador> listadevalores = jugadores.values();
        return new ArrayList<>(listadevalores);
    }

    public double calcularGastoSalarios() {
        return jugadores.values().stream()
                .mapToDouble(j -> j.getContrato().getSalario())
                .sum();
    }

    public void pagar_salarios(String fecha) throws FondoInsuficienteEx, IngresoInvalido {
        double monto = calcularGastoSalarios();
        if (monto <= 0) throw new IngresoInvalido("No hay salarios para pagar.");
        if (gestorpresupuesto.verSaldoActual() < monto)
            throw new FondoInsuficienteEx("Saldo insuficiente.");
        gestorpresupuesto.quitarFondos(monto, "Pago de sueldos del plantel", fecha);
    }

    public void comprar_jugador(double monto, Jugador jugador, String fecha) throws FondoInsuficienteEx, ElementoDuplicadoEx, IngresoInvalido {
        if (gestorpresupuesto.verSaldoActual() < monto)
            throw new FondoInsuficienteEx("Saldo insuficiente para comprar al jugador.");
        if (jugadores.containsKey(jugador.getDni()))
            throw new ElementoDuplicadoEx("El jugador ya está en el club.");

        jugadores.put(jugador.getDni(), jugador);
        estadisticas.put(jugador.getDni(), new EstadisticaJugador());
        gestorpresupuesto.quitarFondos(monto, "Compra de jugador: " + jugador.getApellido(), fecha);
    }

    public void vender_jugador(double monto, String dni, String fecha) throws ElementoInexistenteEx, IngresoInvalido {
        Jugador jugador = jugadores.get(dni);
        if (jugador == null) throw new ElementoInexistenteEx("Clases_Manu.Jugador no encontrado.");
        jugadores.remove(dni);
        estadisticas.remove(dni);
        gestorpresupuesto.agregar_fondos(monto, "Venta de jugador: " + jugador.getApellido(), fecha);
    }


    public void actualizarEstadisticas(String dni, boolean gol, boolean asistencia, boolean vallaInvicta,
                                       boolean amarilla, boolean roja, boolean lesion) throws ElementoInexistenteEx {
        EstadisticaJugador stats = estadisticas.get(dni);
        if (stats == null) throw new ElementoInexistenteEx("No se encontró estadísticas para el jugador con DNI: " + dni);

        if (gol) stats.agregarGol();
        if (asistencia) stats.agregarAsistencia();
        if (vallaInvicta) stats.agregarVallaInvicta();
        if (amarilla) stats.agregarTarjetaAmarilla();
        if (roja) stats.agregarTarjetaRoja();
        if (lesion) stats.agregarLesion();
    }

    public EstadisticaJugador getEstadisticas(String dni) throws ElementoInexistenteEx {
        EstadisticaJugador stats = estadisticas.get(dni);
        if (stats == null) throw new ElementoInexistenteEx("No se encontró estadísticas para el jugador con DNI: " + dni);
        return stats;
    }


    public void guardarJSON() {
        JSONArray array = new JSONArray();
        for (Jugador j : jugadores.values()) {
            JSONObject obj = new JSONObject();
            obj.put("dni", j.getDni());
            obj.put("nombre", j.getNombre());
            obj.put("apellido", j.getApellido());
            obj.put("fechaNacimiento", j.getFechaNacimiento());
            obj.put("nacionalidad", j.getNacionalidad());
            obj.put("numeroCamiseta", j.getNumeroCamiseta());
            obj.put("valorJugador", j.getValorJugador());
            obj.put("posicion", j.getPosicion().toString());

            Contrato contrato = j.getContrato();
            if (contrato != null) {
                JSONObject contratoJSON = new JSONObject();
                contratoJSON.put("dni", contrato.getDni());
                contratoJSON.put("salario", contrato.getSalario());
                contratoJSON.put("fechaInicio", contrato.getFechaInicio());
                contratoJSON.put("fechaFin", contrato.getFechaFin());
                contratoJSON.put("mesesDuracion", contrato.getMesesDuracion());
                contratoJSON.put("contratoActivo", contrato.isContratoActivo());
                obj.put("contrato", contratoJSON);
            } else {
                obj.put("contrato", JSONObject.NULL);
            }

            EstadisticaJugador stats = estadisticas.get(j.getDni());
            if (stats != null) obj.put("estadisticas", stats.toString());

            array.put(obj);
        }
        JSONUtiles.uploadJSON(array, "Plantel");
    }
}
