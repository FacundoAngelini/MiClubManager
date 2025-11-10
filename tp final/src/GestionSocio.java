import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GestionSocio implements MetodosComunes<Socio, String> {
    private final HashMap<Integer, Socio> socios;

    public GestionSocio() {
        this.socios = new HashMap<>();
    }

    public void agregarSocio(String dni, String nombre, String apellido,  String fechaNacimiento, String nacionalidad,  boolean cuotaAlDia, String fechaAlta, Tiposocio tipoSocio)  throws IngresoInvalido, ElementoDuplicadoEx {
        if (dni == null || dni.isEmpty()) {
            throw new IngresoInvalido("El DNI no fue cargado correctamente.");
        }

        boolean existeSocio = socios.values().stream()
                .anyMatch(s -> dni.equals(s.getDni()));

        if (existeSocio) {
            throw new ElementoDuplicadoEx("El socio con DNI " + dni + " ya existe.");
        }

        Socio nuevoSocio = new Socio(dni, nombre, apellido, fechaNacimiento,
                nacionalidad, cuotaAlDia, fechaAlta, tipoSocio);

        socios.put(nuevoSocio.getNumeroSocio(), nuevoSocio);
    }


    public void eliminarElemento(String dni) throws AccionImposible {
        Socio socioAEliminar = socios.values().stream()
                .filter(s -> s.getDni().equals(dni))
                .findFirst()
                .orElse(null);

        if (socioAEliminar == null) {
            throw new AccionImposible("El socio con DNI " + dni + " no se encuentra registrado.");
        }

        socios.remove(socioAEliminar.getNumeroSocio());
    }

    public Socio devuelveElemento(String dni) throws AccionImposible {
        return socios.values().stream()
                .filter(s -> s.getDni().equals(dni))
                .findFirst()
                .orElseThrow(() -> new AccionImposible("No existe un socio con DNI " + dni));
    }


    public boolean existe(String dni) throws ElementoInexistenteEx {
        boolean existe = socios.values().stream().anyMatch(s -> s.getDni().equals(dni));
        if (!existe) {
            throw new ElementoInexistenteEx("No se encontró un socio con el DNI: " + dni);
        }
        return true;
    }

    public ArrayList<Socio> listar() {
        return new ArrayList<>(socios.values());
    }

    public void modificarElemento(Socio socioModificado) throws AccionImposible {
        if (socioModificado == null) {
            throw new AccionImposible("El socio a modificar no puede ser nulo.");
        }

        int numeroSocio = socioModificado.getNumeroSocio();
        if (!socios.containsKey(numeroSocio)) {
            throw new AccionImposible("No se encontró un socio con el número " + numeroSocio);
        }

        socios.put(numeroSocio, socioModificado);
    }


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
            obj.put("tipoSocio", s.getTipoSocio().toString());
            array.put(obj);
        }

        JSONUtiles.uploadJSON(array, "socios");
    }


    public double obtenerRecaudacionTotal() {
        return socios.values().stream()
                .mapToDouble(Socio::obtenerMontoRecaudado)
                .sum();
    }
}

