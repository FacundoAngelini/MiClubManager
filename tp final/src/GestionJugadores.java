import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class GestionJugadores implements MetodosComunes<Jugador, String> {
    private HashMap<String, Jugador> jugadores = new HashMap<>();
    private GestionPresupuesto gestorpresupuesto;

    public GestionJugadores(GestionPresupuesto gestorpresupuesto) {
        this.jugadores = jugadores;
        this.gestorpresupuesto = gestorpresupuesto;
    }

    public void agregarJugador(String dni, String nombre, String apellido, String fechaNacimiento, String nacionalidad, int numeroCamiseta, double valorJugador, double salario, String fechaInicio, String fechaFin, int mesesDuracion, Posicion posicion) throws ElementoDuplicadoEx {
        if (jugadores.containsKey(dni)) {
            throw new ElementoDuplicadoEx("El DNI ya está registrado.");
        }

        boolean camisetaOcupada = jugadores.values().stream()
                .anyMatch(j -> j.getNumeroCamiseta() == numeroCamiseta);

        if (camisetaOcupada) {
            throw new ElementoDuplicadoEx("El número de camiseta ya está ocupado.");
        }

        Contrato contrato = new Contrato(dni, salario, fechaFin, true, fechaInicio, mesesDuracion);
        Jugador jugador = new Jugador(dni, nombre, apellido, fechaNacimiento, nacionalidad, numeroCamiseta, contrato, posicion);
        jugador.setValorJugador(valorJugador);

        jugadores.put(dni, jugador);
    }

    @Override
    public void eliminarElemento(String dni) throws AccionImposible {
        if (!jugadores.containsKey(dni)) {
            throw new AccionImposible("EL numero ingresado no existe");
        }
        jugadores.remove(dni);
    }

    @Override
    public Jugador devuelveElemento(String dni) throws AccionImposible {
        Jugador jugador = jugadores.get(dni);
        if (jugador == null) {
            throw new AccionImposible("Jugador no encontrado con DNI " + dni);
        }
        return jugador;
    }

    public boolean existe(String dni) {
        return jugadores.containsKey(dni);
    }

    public ArrayList<Jugador> listar() {
        Collection<Jugador> listadevalores = jugadores.values();
        return new ArrayList<>(listadevalores);
    }

    public double calcularGastoSalarios() {
        return jugadores.values().stream()
                .mapToDouble(j -> j.getContrato().getSalario())
                .sum();
    }

    public void pagar_salarios(String fecha) throws FondoInsuficienteEx{
        double monto=calcularGastoSalarios();
        gestorpresupuesto.quitarFondos(monto,"Pago de salario de jugadores",fecha);
    }

    public void comprar_jugador(double monto,Jugador jugador, String fecha) throws FondoInsuficienteEx, ElementoDuplicadoEx{
        if(gestorpresupuesto.verSaldoActual()<monto){
            throw new FondoInsuficienteEx("Saldo Insuficiente");
        }
        if (jugadores.containsKey(jugador.getDni())) {
            throw new ElementoDuplicadoEx("El jugador ya existe.");
        }
        jugadores.put(jugador.getDni(), jugador);
        gestorpresupuesto.quitarFondos(monto,"COMPRA DE JUGADOR:" + jugador.getApellido(),fecha);
    }

    public void vender_jugador(double monto,String dni, String fecha) throws ElementoInexistenteEx{
        Jugador jugador = jugadores.get(dni);
        if (jugador == null) {
            throw new ElementoInexistenteEx("El jugador no existe.");
        }
        gestorpresupuesto.agregar_fondos(monto,"VENTA DE JUGADOR",fecha);
        jugadores.remove(dni);
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
                obj.put("salario", j.getContrato().getSalario());
                obj.put("posicion", j.getPosicion().toString());
                array.put(obj);
            }

            JSONUtiles.uploadJSON(array, "Plantel");
        }
}
