package Clase.Gestiones;

import Clase.Json.JSONUtiles;
import interfaz.MetodosComunes;
import Clase.Persona.Socio;
import enums.Tiposocio;
import exeptions.AccionImposible;
import exeptions.ElementoDuplicadoEx;
import exeptions.ElementoInexistenteEx;
import exeptions.IngresoInvalido;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GestionSocio implements MetodosComunes<Socio, String> {
    private final HashMap<Integer, Socio> socios;
    private final GestionPresupuesto gestionPresupuesto;

    public GestionSocio(GestionPresupuesto gestionPresupuesto) {
        this.socios = new HashMap<>();
        this.gestionPresupuesto = gestionPresupuesto;
    }

    public void agregarSocio(String dni, String nombre, String apellido, String fechaNacimiento, String nacionalidad,
                             boolean cuotaAlDia, String fechaAlta, Tiposocio tipoSocio) throws IngresoInvalido, ElementoDuplicadoEx {
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
        guardarJSON();
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
        guardarJSON();
    }

    public Socio devuelveElemento(String dni) throws AccionImposible {
        return socios.values().stream()
                .filter(s -> s.getDni().equals(dni))
                .findFirst()
                .orElseThrow(() -> new AccionImposible("No existe un socio con DNI " + dni));
    }

    public void aplicarRecaudacion(String fecha) throws IngresoInvalido {
        if (gestionPresupuesto == null) {
            throw new IngresoInvalido("No se ha configurado el gestor de presupuesto.");
        }

        double totalRecaudado = obtenerRecaudacionTotal();
        gestionPresupuesto.agregar_fondos(totalRecaudado, "Recaudación de socios", fecha);
        gestionPresupuesto.guardarJSON();
    }

    public boolean existe(String dni) throws ElementoInexistenteEx {
        boolean existe = socios.values().stream().anyMatch(s -> s.getDni().equals(dni));
        if (!existe) {
            throw new ElementoInexistenteEx("No se encontro un socio con el DNI: " + dni);
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
            throw new AccionImposible("No se encontro un socio con el numero " + numeroSocio);
        }

        socios.put(numeroSocio, socioModificado);
        guardarJSON();
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

    public void cambiarTipoSocio(String dni) throws AccionImposible {
        Socio socio = socios.values().stream()
                .filter(s -> s.getDni().equals(dni))
                .findFirst()
                .orElseThrow(() -> new AccionImposible("No se encontro un socio con el DNI: " + dni));

        switch (socio.getTipoSocio()) {
            case Tiposocio.JUVENIL -> {
                socio.setTipoSocio(Tiposocio.ACTIVO);
                System.out.println("El socio con DNI " + dni + " ahora es ACTIVO.");
            }
            case Tiposocio.ACTIVO -> {
                socio.setTipoSocio(Tiposocio.VITALICIO);
                System.out.println("El socio con DNI " + dni + " ahora es VITALICIO.");
            }
            case Tiposocio.VITALICIO ->
                throw new AccionImposible("El socio ya es VITALICIO. No se puede cambiar el tipo.");

            default -> throw new AccionImposible("Tipo de socio desconocido.");
        }

        guardarJSON();
    }
}
