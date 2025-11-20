package Clase.Gestiones;

import Clase.Json.JSONUtiles;
import Clase.Persona.CuerpoTecnico;
import Clase.Presupuesto.Contrato;
import enums.Puesto;
import exeptions.*;
import interfaz.MetodosComunes;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.Period;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class GestionCuerpoTecnico implements MetodosComunes<CuerpoTecnico, String> {

    private final HashMap<String, CuerpoTecnico> cuerpoTecnico = new HashMap<>();
    private final GestionPresupuesto gestionPresupuesto;

    public GestionCuerpoTecnico(GestionPresupuesto gestionPresupuesto) {
        this.gestionPresupuesto = gestionPresupuesto;
        cargarJSON();
    }

    public void agregarElemento(String dni, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, double salario, LocalDate fechaInicio, LocalDate fechaFin, Puesto puesto, int aniosExp) throws ElementoDuplicadoEx, FondoInsuficienteEx, IngresoInvalido {
        if (dni == null || !dni.matches("\\d+")) {
            throw new IngresoInvalido("DNI invalido, solo numeros");
        }

        if (cuerpoTecnico.containsKey(dni)) {
            throw new ElementoDuplicadoEx("Ya existe un miembro con ese DNI");
        }

        if (nombre == null || !nombre.matches("[a-zA-Z]+")) {
            throw new IngresoInvalido("Nombre invalido, solo letras");
        }

        if (apellido == null || !apellido.matches("[a-zA-Z]+")) {
            throw new IngresoInvalido("Apellido invalido, solo letras");
        }

        if (nacionalidad == null || !nacionalidad.matches("[a-zA-Z ]+")) {
            throw new IngresoInvalido("Nacionalidad invalida, solo letras ");
        }

        if (fechaNacimiento == null || fechaNacimiento.isAfter(LocalDate.now())) {
            throw new IngresoInvalido("Fecha de nacimiento invalida");
        }

        if (fechaNacimiento.plusYears(18).isAfter(LocalDate.now())) {
            throw new IngresoInvalido("Debe ser mayor a 18 años");
        }

        if (salario <= 0) {
            throw new IngresoInvalido("Salario debe ser mayor que cero");
        }

        if (fechaInicio == null || fechaFin == null) {
            throw new IngresoInvalido("Fechas de contrato invalidas");
        }

        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IngresoInvalido("Fecha inicio no puede ser futura");
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new IngresoInvalido("Fecha fin no puede ser anterior a inicio");
        }

        if (puesto == null) {
            throw new IngresoInvalido("Puesto invalido");
        }

        if (aniosExp < 0) {
            throw new IngresoInvalido("Los años de experiencia no pueden ser negativos");
        }

        Contrato contrato = new Contrato(dni, salario, fechaInicio, fechaFin, fechaNacimiento);
        CuerpoTecnico nuevo = new CuerpoTecnico(dni, nombre, apellido, fechaNacimiento, nacionalidad, contrato, puesto, aniosExp);

        cuerpoTecnico.put(dni, nuevo);
        guardarJSON();
    }

    @Override
    public void eliminarElemento(String dni) throws AccionImposible {
        if (!cuerpoTecnico.containsKey(dni)) {
            throw new AccionImposible("No existe el cuerpo tecnico");
        }

        cuerpoTecnico.remove(dni);
        guardarJSON();
        System.out.println("Cuerpo tecnico eliminado correctamente");
    }

    @Override
    public CuerpoTecnico devuelveElemento(String dni) throws AccionImposible {
        CuerpoTecnico ct = cuerpoTecnico.get(dni);
        if (ct == null) {
            throw new AccionImposible("Cuerpo tecnico no encontrado");
        }

        return ct;
    }
    public void cargarJSON() {
        String contenido = JSONUtiles.downloadJSON("cuerpoTecnico");
        if (contenido.isBlank()) return;
        JSONArray array = new JSONArray(contenido);
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);

            String dni = obj.getString("dni");
            String nombre = obj.getString("nombre");
            String apellido = obj.getString("apellido");
            LocalDate fechaNacimiento = LocalDate.parse(obj.getString("fechaNacimiento"));
            String nacionalidad = obj.getString("nacionalidad");
            Puesto puesto = Puesto.valueOf(obj.getString("puesto"));
            int aniosExp = obj.getInt("aniosExp");
            Contrato contrato = null;
            if (!obj.isNull("contrato")) {
                JSONObject cJSON = obj.getJSONObject("contrato");
                contrato = new Contrato(cJSON.getString("dni"), cJSON.getDouble("salario"), LocalDate.parse(cJSON.getString("fechaInicio")), LocalDate.parse(cJSON.getString("fechaFin")), fechaNacimiento);
            }

            CuerpoTecnico ct = new CuerpoTecnico(dni, nombre, apellido, fechaNacimiento, nacionalidad, contrato, puesto, aniosExp);
            cuerpoTecnico.put(dni, ct);
        }
    }


    @Override
    public boolean existe(String dni) throws ElementoInexistenteEx {
        if (!cuerpoTecnico.containsKey(dni)) {
            throw new ElementoInexistenteEx("No existe el cuerpo tecnico");
        }

        return true;
    }

    @Override
    public ArrayList<CuerpoTecnico> listar() {
        return new ArrayList<>(cuerpoTecnico.values());
    }

    public void modificarCuerpoTecnico(String dni, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, Puesto puesto, int aniosExp) throws ElementoInexistenteEx, IngresoInvalido {
        CuerpoTecnico ct = cuerpoTecnico.get(dni);
        if (ct == null)
            throw new ElementoInexistenteEx("No existe el miembro del cuerpo tecnico con DNI " + dni);

        if (nombre != null && !nombre.matches("[a-zA-Z]+"))
            throw new IngresoInvalido("Nombre invalido");

        if (apellido != null && !apellido.matches("[a-zA-Z]+"))
            throw new IngresoInvalido("Apellido invalido");

        if (nacionalidad != null && !nacionalidad.matches("[a-zA-Z ]+"))
            throw new IngresoInvalido("Nacionalidad invalida");

        if (fechaNacimiento != null) {
            if (fechaNacimiento.isAfter(LocalDate.now()))
                throw new IngresoInvalido("Fecha de nacimiento invalida");

            if (fechaNacimiento.plusYears(18).isAfter(LocalDate.now()))
                throw new IngresoInvalido("Debe tener al menos 18 anos");
        }

        if (aniosExp < -1)
            throw new IngresoInvalido("Anios de experiencia invalidos");

        if (aniosExp >= 0) {
            LocalDate fechaBase = (fechaNacimiento != null) ? fechaNacimiento : ct.getFechaNacimiento();
            int edadActual = Period.between(fechaBase, LocalDate.now()).getYears();
            int maxExp = edadActual - 18;
            if (aniosExp > maxExp)
                throw new IngresoInvalido("La experiencia supera lo permitido segun la edad");
        }

        if (nombre != null) ct.setNombre(nombre);
        if (apellido != null) ct.setApellido(apellido);
        if (fechaNacimiento != null) ct.setFechaNacimiento(fechaNacimiento);
        if (nacionalidad != null) ct.setNacionalidad(nacionalidad);
        if (puesto != null) ct.setPuesto(puesto);
        if (aniosExp >= 0) ct.setAniosExp(aniosExp);

        guardarJSON();
    }


    public double calcularGastoSalarios() {
        return cuerpoTecnico.values().stream().mapToDouble(ct -> ct.getContrato().getSalario()).sum();
    }

    public void aplicarGastoSalarios(LocalDate fecha) {
        try {
            double gasto = calcularGastoSalarios();
            gestionPresupuesto.quitarFondos(gasto, "Salarios cuerpo tecnico", fecha);
            System.out.println("Salarios pagados correctamente");
        } catch (Exception e) {
            System.out.println("Error al pagar salarios: ");
        }
    }

    @Override
    public void guardarJSON() {
        JSONArray array = new JSONArray();

        for (CuerpoTecnico ct : cuerpoTecnico.values()) {
            JSONObject obj = new JSONObject();
            obj.put("dni", ct.getDni());
            obj.put("nombre", ct.getNombre());
            obj.put("apellido", ct.getApellido());
            obj.put("fechaNacimiento", ct.getFechaNacimiento().toString());
            obj.put("nacionalidad", ct.getNacionalidad());
            obj.put("puesto", ct.getPuesto().toString());
            obj.put("aniosExp", ct.getAniosExp());

            Contrato c = ct.getContrato();
            if (c != null) {
                JSONObject contratoJSON = new JSONObject();
                contratoJSON.put("dni", c.getDni());
                contratoJSON.put("salario", c.getSalario());
                contratoJSON.put("fechaInicio", c.getFechaInicio().toString());
                contratoJSON.put("fechaFin", c.getFechaFin().toString());
                contratoJSON.put("contratoActivo", c.isContratoActivo());
                obj.put("contrato", contratoJSON);
            } else {
                obj.put("contrato", JSONObject.NULL);
            }

            array.put(obj);
        }

        JSONUtiles.uploadJSON(array, "cuerpoTecnico");
    }
    public HashMap<String, CuerpoTecnico> getCuerpoTecnico() {
        return cuerpoTecnico;
    }


}
