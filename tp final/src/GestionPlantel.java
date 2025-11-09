import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GestionPlantel implements MetodosComunes<Persona> {
    private HashMap<Integer, Jugador> jugadores;
    private ArrayList<CuerpoTecnico> cuerpoTecnico;

    public GestionPlantel() {
        jugadores = new HashMap<>();
        cuerpoTecnico = new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) throws IngresoInvalido {
        int numero = jugador.getNumeroCamiseta();
        if(jugadores.containsKey(numero)){
            throw new IngresoInvalido("Jugador existente");
        }
        jugadores.put(numero, jugador);
    }

    public void elimnarJugador(int numero) throws AccionImposible {
        if(!jugadores.containsKey(numero)){
            throw new AccionImposible("EL jugador no existe");
        }
        jugadores.remove(numero);
    }

    public void modificarJugador(Jugador jugador) throws AccionImposible {
        int numero =  jugador.getNumeroCamiseta();
        if(!jugadores.containsKey(numero)){
            throw new AccionImposible("El jugador no existe");
        }
        jugadores.put(numero, jugador);
    }

    public Jugador buscarJugador(int numeroCamiseta) {
        return jugadores.get(numeroCamiseta);
    }

    public ArrayList<Jugador> listarJugadores() {
        return new ArrayList<>(jugadores.values());
    }

    public void agregarCuerpoTecnico(CuerpoTecnico ct) throws IngresoInvalido {
        if(cuerpoTecnico.contains(ct)){
            throw new IngresoInvalido("El cuerpo tecnico existente");
        }
        cuerpoTecnico.add(ct);
    }

    public void eliminarCuerpoTecnico(CuerpoTecnico ct) throws AccionImposible {
        if(!cuerpoTecnico.contains(ct)){
            throw new AccionImposible("El cuerpo tecnico no existente");
        }
        cuerpoTecnico.remove(ct);
    }

    public void modificarCuerpoTecnico(CuerpoTecnico ct) throws AccionImposible {
        boolean encontrado = false;
        for (int i = 0; i < cuerpoTecnico.size(); i++) {
            if (cuerpoTecnico.get(i).getDni().equals(ct.getDni())) {
                cuerpoTecnico.set(i, ct);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new AccionImposible("Cuerpo técnico con DNI " + ct.getDni() + " no encontrado");
        }
    }

    public ArrayList<CuerpoTecnico> listarCuerpoTecnico() {
        return cuerpoTecnico;
    }

    public double calcularGastoSalarios() {
        double total = 0;
        for (Jugador j : jugadores.values()) {
            total += j.getContrato().obtenerSalario();
        }
        for (CuerpoTecnico ct : cuerpoTecnico) {
            total += ct.getContrato().obtenerSalario();
        }
        return total;
    }

    public void agregarElemento(Persona elemento) throws IngresoInvalido {
        if (elemento instanceof Jugador) {
            agregarJugador((Jugador) elemento);
        } else if (elemento instanceof CuerpoTecnico) {
            agregarCuerpoTecnico((CuerpoTecnico) elemento);
        } else {
            throw new IngresoInvalido("Elemento desconocido");
        }
    }

    public void eliminarElemento(Persona elemento) throws AccionImposible {
        if (elemento instanceof Jugador) {
            Jugador j = (Jugador) elemento;
            elimnarJugador(j.getNumeroCamiseta());
        } else if (elemento instanceof CuerpoTecnico) {
            eliminarCuerpoTecnico((CuerpoTecnico) elemento);
        } else {
            throw new AccionImposible("Elemento desconocido");
        }
    }

    public void modificarElemento(Persona elemento) throws AccionImposible {
        if (elemento instanceof Jugador) {
            modificarJugador((Jugador) elemento);
        } else if (elemento instanceof CuerpoTecnico) {
            modificarCuerpoTecnico((CuerpoTecnico) elemento); // usa búsqueda por DNI
        } else {
            throw new AccionImposible("Elemento desconocido");
        }
    }

    public boolean existe(Persona elemento) {
        if (elemento instanceof Jugador) {
            Jugador j = (Jugador) elemento;
            return jugadores.containsKey(j.getNumeroCamiseta());
        } else if (elemento instanceof CuerpoTecnico) {
            return cuerpoTecnico.contains((CuerpoTecnico) elemento);
        }
        return false;
    }

    public ArrayList<Persona> listar() {
        ArrayList<Persona> lista = new ArrayList<>();
        lista.addAll(jugadores.values());
        lista.addAll(cuerpoTecnico);
        return lista;
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


            for (CuerpoTecnico ct : cuerpoTecnico) {
                JSONObject objC = new JSONObject();
                objC.put("dni", ct.getDni());
                objC.put("nombre", ct.getNombre());
                objC.put("apellido", ct.getApellido());
                objC.put("fechaNacimiento", ct.getFechaNacimiento());
                objC.put("nacionalidad", ct.getNacionalidad());
                objC.put("puesto", ct.getPuesto().toString());
                objC.put("salario", ct.getContrato().obtenerSalario());
                objC.put("tipo", "cuerpoTecnico");
                array.put(objC);
            }
        JSONUtiles.uploadJSON(array,"Plantel");
    }
}
