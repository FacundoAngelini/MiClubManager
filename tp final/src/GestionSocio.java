import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GestionSocio implements MetodosComunes<Socio> {
    private HashMap<Integer, Socio> socios;

    public GestionSocio() {
        this.socios = new HashMap<>();
    }

    @Override
    public void agregarElemento(Socio elemento) throws IngresoInvalido {
        if (socios.containsKey(elemento.getNumeroSocio())) {
            throw new IngresoInvalido("El socio ya existe");
        }
        socios.put(elemento.getNumeroSocio(), elemento);
    }

    @Override
    public void eliminarElemento(Socio elemento) throws AccionImposible {
        if (!socios.containsKey(elemento.getNumeroSocio())) {
            throw new AccionImposible("El socio no se encuentra");
        }
        if (socios.get(elemento.getNumeroSocio()) == null) {
            throw new AccionImposible("El socio no existe");
        }
        socios.remove(elemento.getNumeroSocio());
    }

    @Override
    public void modificarElemento(Socio elemento) throws AccionImposible {
        if (!socios.containsKey(elemento.getNumeroSocio())) {
            throw new AccionImposible("El socio a modificar no se encntro");
        }
        socios.put(elemento.getNumeroSocio(), elemento);
    }

    @Override
    public boolean existe(Socio elemento) {
        return socios.containsKey(elemento.getNumeroSocio());
    }

    @Override
    public ArrayList<Socio> listar() {
        return new ArrayList<>(socios.values());
    }

    @Override
    public void guardarJSON() {
        JSONArray array = new JSONArray();

        for (Socio s : socios.values()) {
            JSONObject obj = new JSONObject();
            obj.put("dni", s.getDni());
            obj.put("nombre", s.getNombre());
            obj.put("apellido", s.getApellido());
            obj.put("fechaNacimiento", s.getFechaNacimiento());
            obj.put("nacionalidad", s.getNacionalidad());
            obj.put("numeroSocio", s.getNumeroSocio());
            obj.put("fechaAlta", s.getFechaAlta());
            obj.put("cuotaAlDia", s.isCuotaAlDia());
            obj.put("tipoSocio", s.getTiposocio().toString());
            array.put(obj);
        }
        JSONUtiles.uploadJSON(array, "socios");
    }

}
