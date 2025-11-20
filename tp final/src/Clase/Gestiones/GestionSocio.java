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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class GestionSocio implements MetodosComunes<Socio, String> {

    private final HashMap<Integer, Socio> socios = new HashMap<>();
    private final GestionPresupuesto gestionPresupuesto;

    public GestionSocio(GestionPresupuesto gestionPresupuesto) {
        this.gestionPresupuesto = gestionPresupuesto;
    }

    public void agregarSocio(String dni, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, LocalDate fechaAlta, Tiposocio tipoSocio) throws IngresoInvalido, ElementoDuplicadoEx {
        if (dni == null || dni.isBlank())
            throw new IngresoInvalido("DNI invalido");

        boolean existe = socios.values().stream().anyMatch(s -> s.getDni().equals(dni));

        if (existe)
            throw new ElementoDuplicadoEx("DNI repetido");

        if (nombre == null || nombre.isBlank())
            throw new IngresoInvalido("Nombre invalido");

        if (apellido == null || apellido.isBlank())
            throw new IngresoInvalido("Apellido invalido");

        if (fechaNacimiento == null)
            throw new IngresoInvalido("Fecha de nacimiento invalida");

        if (nacionalidad == null || nacionalidad.isBlank())
            throw new IngresoInvalido("Nacionalidad invalida");

        if (fechaAlta == null)
            throw new IngresoInvalido("Fecha de alta invalida");

        if (tipoSocio == null)
            throw new IngresoInvalido("Tipo de socio invalido");

        if (fechaAlta.isBefore(fechaNacimiento))
            throw new IngresoInvalido("Fecha de alta anterior a fecha de nacimiento");

        if (fechaAlta.isAfter(LocalDate.now()))
            throw new IngresoInvalido("Fecha de alta futura");

        Socio nuevo = new Socio(dni, nombre, apellido, fechaNacimiento, nacionalidad, fechaAlta, tipoSocio);
        socios.put(nuevo.getNumeroSocio(), nuevo);
        guardarJSON();
    }

    @Override
    public void eliminarElemento(String dni) throws AccionImposible {
        Socio socio = socios.values().stream().filter(s -> s.getDni().equals(dni)).findFirst().orElseThrow(() -> new AccionImposible("Socio no encontrado"));
        socios.remove(socio.getNumeroSocio());
        guardarJSON();
    }

    @Override
    public Socio devuelveElemento(String dni) throws AccionImposible {
        return socios.values().stream()
                .filter(s -> s.getDni().equals(dni))
                .findFirst()
                .orElseThrow(() -> new AccionImposible("Socio no encontrado"));
    }

    @Override
    public boolean existe(String dni) throws ElementoInexistenteEx {
        boolean encontrado = socios.values().stream().anyMatch(s -> s.getDni().equals(dni));
        if (!encontrado)
            throw new ElementoInexistenteEx("No existe socio");
        return true;
    }

    @Override
    public ArrayList<Socio> listar() {
        return new ArrayList<>(socios.values());
    }

    public void modificarSocio(String dni, String nuevoNombre, String nuevoApellido, LocalDate nuevaFechaAlta)
            throws AccionImposible {

        Socio socio = socios.values().stream().filter(s -> s.getDni().equals(dni)).findFirst().orElseThrow(() -> new AccionImposible("Socio no encontrado"));

        if (nuevoNombre != null && !nuevoNombre.isBlank())
            socio.setNombre(nuevoNombre);

        if (nuevoApellido != null && !nuevoApellido.isBlank())
            socio.setApellido(nuevoApellido);

        if (nuevaFechaAlta != null) {
            if (nuevaFechaAlta.isBefore(socio.getFechaNacimiento()))
                throw new AccionImposible("Fecha de alta inválida");
            if (nuevaFechaAlta.isAfter(LocalDate.now()))
                throw new AccionImposible("Fecha de alta futura");
            socio.setFechaAlta(nuevaFechaAlta);
        }

        guardarJSON();
    }

    public void aplicarRecaudacion(LocalDate fecha) throws IngresoInvalido {
        if (gestionPresupuesto == null)
            throw new IngresoInvalido("Presupuesto no configurado");

        if (fecha == null)
            fecha = LocalDate.now();

        double total = obtenerRecaudacionTotal();
        gestionPresupuesto.agregarFondos(total, "Recaudación socios", fecha);
        gestionPresupuesto.guardarJSON();
    }

    public double obtenerRecaudacionTotal() {
        return socios.values().stream().mapToDouble(Socio::obtenerMontoRecaudado).sum();
    }

    public void cambiarTipoSocio(String dni) throws AccionImposible {
        Socio socio = socios.values().stream().filter(s -> s.getDni().equals(dni)).findFirst().orElseThrow(() -> new AccionImposible("Socio no encontrado"));

        Tiposocio tipo = socio.getTiposocio();
        if (tipo == Tiposocio.JUVENIL)
            socio.setTipoSocio(Tiposocio.ACTIVO);
        else if (tipo == Tiposocio.ACTIVO)
            socio.setTipoSocio(Tiposocio.VITALICIO);
        else if (tipo == Tiposocio.VITALICIO)
            throw new AccionImposible("Ya es vitalicio");

        guardarJSON();
    }

    @Override
    public void guardarJSON() {
        JSONArray array = new JSONArray();
        for (Socio s : socios.values()) {
            JSONObject obj = new JSONObject();
            obj.put("dni", s.getDni());
            obj.put("nombre", s.getNombre());
            obj.put("apellido", s.getApellido());
            obj.put("fechaNacimiento", s.getFechaNacimiento().toString());
            obj.put("nacionalidad", s.getNacionalidad());
            obj.put("numeroSocio", s.getNumeroSocio());
            obj.put("fechaAlta", s.getFechaAlta().toString());
            obj.put("tipoSocio", s.getTiposocio().toString());
            array.put(obj);
        }
        JSONUtiles.uploadJSON(array, "socios");
    }
}
