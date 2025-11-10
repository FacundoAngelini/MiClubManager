import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

//METODOS A AGREGAR: MOSTRAR LESIONES, GOLES, TARJETAS

public class GestionJugadores implements MetodosComunes<Jugador, String> {
    private HashMap<String, Jugador> jugadores = new HashMap<>();
    private GestionPresupuesto gestorpresupuesto;
    private GestorPartido gestorPartidos;

    public GestionJugadores(HashMap<String, Jugador> jugadores, GestionPresupuesto gestorpresupuesto, GestorPartido gestorPartidos) {
        this.jugadores = jugadores;
        this.gestorpresupuesto = gestorpresupuesto;
        this.gestorPartidos = gestorPartidos;
    }

    public void agregarJugador(String dni, String nombre, String apellido, String fechaNacimeiento, String nacionalidad, EstadisticaJugador estadisticaJugador, int numeroCamiseta, double valorJugador, double salario, String fechaFin, String fechaInicio, int mesesDuracion, Posicion posicion) throws IngresoInvalido, ElementoDuplicadoEx {

        if (jugadores.containsKey(dni)) {
            throw new ElementoDuplicadoEx("El numero de camiseta ya esta cargado");
        }
        boolean jugadorCargado = jugadores.values().stream().anyMatch(jugador->jugador.getNumeroCamiseta() == numeroCamiseta);
        if (jugadorCargado) {
            throw new ElementoDuplicadoEx("La camiseta esta ocupada");
        }
        Contrato newContrato = new Contrato(dni, salario, fechaFin, true, fechaInicio, mesesDuracion);
        Jugador newJugador=new Jugador(dni,nombre,apellido,fechaNacimeiento,nacionalidad,numeroCamiseta,newContrato,posicion);
        jugadores.put(dni, newJugador);
    }
    public void consultar_lesiones(String dni) throws ElementoInexistenteEx {
        if(!jugadores.containsKey(dni)){
            throw new ElementoInexistenteEx("El jugador no existe");
        }
        Jugador jugador = jugadores.get(dni);
        jugador.getEstadisticaJugador().getLesiones();
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
        if (!jugadores.containsKey(dni)) {
            throw new ElementoInexistenteEx("El jugador no existe");
        }
        return jugadores.get(dni);
    }

    @Override
    public boolean existe(String dni) throws ElementoInexistenteEx {
        if (!jugadores.containsKey(dni)) {
            return false;
        }
        return true;
    }

    public ArrayList<Jugador> listar() {
        Collection<Jugador> listadevalores = jugadores.values();
        return new ArrayList<>(listadevalores);
    }

    public double calcularGastoSalarios() {
        double total = 0;
        for (Jugador j : jugadores.values()) {
            total += j.getContrato().getSalario();
        }
        return total;
    }

    public void pagar_salarios(String fecha){
        double monto=calcularGastoSalarios();
        gestorpresupuesto.quitarFondos(monto,"Pago de salario de jugadores",fecha);
    }

    public void comprar_jugador(double monto,Jugador jugador, String fecha) throws FondoInsuficienteEx{
        if(gestorpresupuesto.verSaldoActual()<monto){
            throw new FondoInsuficienteEx("Saldo Insuficiente");
        }
        jugadores.put(jugador.getDni(), jugador);
        gestorpresupuesto.quitarFondos(monto,"COMPRA DE JUGADOR:" + jugador.getApellido(),fecha);
    }

    public void vender_jugador(double monto,String dni, String fecha){
        if(!jugadores.containsKey(dni)){
            throw new ElementoInexistenteEx("El jugador no existe");
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
            obj.put("salario", j.getContrato().getSalario());
            obj.put("valor", j.getValorJugador());
            obj.put("tipo", "jugador");
            array.put(obj);
        }
        JSONUtiles.uploadJSON(array, "Plantel");
    }
}
