import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GestorPartido implements MetodosComunes<Partido> {
    private ArrayList<Partido> partidos;
    public GestorPartido() {
        this.partidos = new ArrayList<>();
    }

    @Override
    public void agregarElemento(Partido elemento) throws IngresoInvalido {
        if(partidos.contains(elemento)){
            throw new IngresoInvalido("El partido ya existe");
        }
        partidos.add(elemento);
    }

    @Override
    public void eliminarElemento(Partido elemento) throws AccionImposible{
        if(!partidos.contains(elemento)){
            throw new AccionImposible("El partido no existe");
        }
        partidos.remove(elemento);
    }

    public void modificarElemento(Partido partido) throws AccionImposible {
        int index = partidos.indexOf(partido);
        if (index == -1) {
            throw new AccionImposible("Partido no encontrado");
        }
        partidos.set(index, partido);
    }

    public boolean existe(Partido partido) {
        return partidos.contains(partido);
    }


    public ArrayList<Partido> listar() {
        return partidos;
    }

    public void guardarJSON() {
        JSONArray array = new JSONArray();
        for (Partido p : partidos) {
            array.put(p.toJSON());
        }
        JSONUtiles.uploadJSON(array, "Partidos");
    }
    public Partido buscarPartidoPorFecha(String fecha) {
        for (Partido p : partidos) {
            if (p.getFecha().equals(fecha)) {
                return p;
            }
        }
        return null;
    }


    public double calcularRecaudacionTotal(Estadio estadioDelClub) {
        double total = 0;
        for (Partido p : partidos) {
            total += p.obtenerGanancia(estadioDelClub);
        }
        return total;
    }


}
