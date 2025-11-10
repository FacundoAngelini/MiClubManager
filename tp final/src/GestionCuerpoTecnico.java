import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GestionCuerpoTecnico implements MetodosComunes<CuerpoTecnico, String> {
    private HashMap<String, CuerpoTecnico> cuerpoTecnico = new HashMap<>();

    @Override
    public void agregarElemento(CuerpoTecnico elemento) throws IngresoInvalido, AccionImposible {
        if (elemento == null) {
            throw new AccionImposible("El elemento esta vacio");
        }
        if (cuerpoTecnico.containsKey(elemento.getDni())) {
            throw new ElementoDuplicadoEx("DNI duplicado");
        }
        cuerpoTecnico.put(elemento.getDni(), elemento);
    }

    @Override
    public void eliminarElemento(String id) throws AccionImposible {
        if (!cuerpoTecnico.containsKey(id)) {
            throw new ElementoInexistenteEx("El elemento no existe");
        }
        cuerpoTecnico.remove(id);
    }

    @Override
    public void modificarElemento(CuerpoTecnico elemento) throws AccionImposible {
        if (!cuerpoTecnico.containsKey(elemento.getDni())) {
            throw new ElementoInexistenteEx("El elemento no existe");
        }
        cuerpoTecnico.put(elemento.getDni(), elemento);
    }

    @Override
    public CuerpoTecnico devuelveElemento(String dni) throws AccionImposible {
        if(!cuerpoTecnico.containsKey(dni)) {
            throw new ElementoInexistenteEx("El elemento no existe");
        }
        return cuerpoTecnico.get(dni);
    }

    @Override
    public boolean existe(String id) throws ElementoInexistenteEx {
        if (!cuerpoTecnico.containsKey(id)) {
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<CuerpoTecnico> listar() {
        Collection<CuerpoTecnico> listadevalores = cuerpoTecnico.values();
        return new ArrayList<>(listadevalores);
    }

    public double calcularGastoSalarios() {
        double total = 0;
        for (CuerpoTecnico ct : cuerpoTecnico.values()) {
            total += ct.getContrato().obtenerSalario();
        }
        return total;
    }

    @Override
    public void guardarJSON() {
        JSONArray arrayCT = new JSONArray();
        for (CuerpoTecnico ct : cuerpoTecnico.values()) {
            JSONObject obj = new JSONObject();
            obj.put("DNI", ct.getDni());
            obj.put("nombre", ct.getNombre());
            obj.put("apellido", ct.getApellido());
            obj.put("fecha de nacimiento", ct.getFechaNacimiento());
            obj.put("nacionalidad", ct.getNacionalidad());
            obj.put("contrato", ct.getContrato());
            obj.put("puesto", ct.getPuesto());
            obj.put("años exp", ct.getAniosExp());
            arrayCT.put(obj);
        }
        JSONUtiles.uploadJSON(arrayCT, "cuerpoTecnico");
    }
}
