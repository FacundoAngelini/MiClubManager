import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GestionJugadores implements MetodosComunes<Jugador, Integer> {
    private HashMap<Integer, Jugador> jugadores = new HashMap<>();

    public void agregarElemento(Jugador jugador) throws IngresoInvalido {
        int numero = jugador.getNumeroCamiseta();
        if (jugadores.containsKey(numero)) {
            throw new IngresoInvalido("Jugador existente");
        }
        jugadores.put(numero, jugador);
    }

    @Override
    public void eliminarElemento(Integer id) throws AccionImposible {
        if (!jugadores.containsKey(id)) {
            throw new AccionImposible("EL jugador no existe");
        }
        jugadores.remove(id);
    }

    @Override
    public void modificarElemento(Jugador elemento) throws AccionImposible {
        int numero = elemento.getNumeroCamiseta();
        if (!jugadores.containsKey(numero)) {
            throw new AccionImposible("El jugador no existe");
        }
        jugadores.put(numero, elemento);
    }

    @Override
    public Jugador devuelveElemento(Integer key) throws AccionImposible {
        if (!jugadores.containsKey(key)) {
            throw new ElementoInexistenteEx("El jugador no existe");
        }
        return jugadores.get(key);
    }

    @Override
    public boolean existe(Integer id) throws ElementoInexistenteEx {
        if (!jugadores.containsKey(id)) {
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
            total += j.getContrato().obtenerSalario();
        }
        return total;
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
            obj.put("salario", j.getContrato().obtenerSalario());
            obj.put("valor", j.getValorJugador());
            obj.put("tipo", "jugador");
            array.put(obj);
        }
        JSONUtiles.uploadJSON(array, "Plantel");
    }
}
