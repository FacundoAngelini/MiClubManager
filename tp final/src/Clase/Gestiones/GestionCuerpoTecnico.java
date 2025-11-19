package Clase.Gestiones;

import Clase.Json.JSONUtiles;
import Clase.Persona.CuerpoTecnico;
import Clase.Presupuesto.Contrato;
import enums.Puesto;
import exeptions.*;
import interfaz.MetodosComunes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class GestionCuerpoTecnico implements MetodosComunes<CuerpoTecnico, String> {

    private final HashMap<String, CuerpoTecnico> cuerpoTecnico = new HashMap<>();
    private final GestionPresupuesto gestionPresupuesto;

    public GestionCuerpoTecnico(GestionPresupuesto gestionPresupuesto) {
        this.gestionPresupuesto = gestionPresupuesto;
    }

    public void agregarElemento(String dni, String nombre, String apellido, LocalDate fechaNacimiento,
                                String nacionalidad, double salario, LocalDate fechaInicio, LocalDate fechaFin,
                                Puesto puesto, int aniosExp)
            throws ElementoDuplicadoEx, FondoInsuficienteEx, IngresoInvalido {

        // =====================
        // 1. VALIDACIONES
        // =====================
        if (dni == null || !dni.matches("\\d+"))
            throw new IllegalArgumentException("DNI inválido, solo números");

        if (cuerpoTecnico.containsKey(dni))
            throw new ElementoDuplicadoEx("Ya existe un miembro con ese DNI");

        if (nombre == null || !nombre.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Nombre inválido, solo letras");

        if (apellido == null || !apellido.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Apellido inválido, solo letras");

        if (nacionalidad == null || !nacionalidad.matches("[a-zA-Z ]+"))
            throw new IllegalArgumentException("Nacionalidad inválida, solo letras y espacios");

        if (fechaNacimiento == null || fechaNacimiento.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Fecha de nacimiento inválida");

        if (fechaNacimiento.plusYears(18).isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Debe ser mayor a 18 años");

        if (salario <= 0)
            throw new IllegalArgumentException("Salario debe ser mayor que cero");

        if (fechaInicio == null || fechaFin == null)
            throw new IllegalArgumentException("Fechas de contrato inválidas");

        if (fechaInicio.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Fecha inicio no puede ser futura");

        if (fechaFin.isBefore(fechaInicio))
            throw new IllegalArgumentException("Fecha fin no puede ser anterior a inicio");

        if (puesto == null)
            throw new IllegalArgumentException("Puesto inválido");

        if (aniosExp < 0)
            throw new IllegalArgumentException("Los años de experiencia no pueden ser negativos");

        // =====================
        // 2. CREAR OBJETOS
        // =====================
        Contrato contrato = new Contrato(dni, salario, fechaInicio, fechaFin, fechaNacimiento);

        CuerpoTecnico nuevo = new CuerpoTecnico(
                dni, nombre, apellido, fechaNacimiento, nacionalidad,
                contrato, puesto, aniosExp
        );

        // =====================
        // 3. MODIFICAR ESTADO
        // =====================
        gestionPresupuesto.quitarFondos(salario,
                "Contrato cuerpo técnico " + nombre + " " + apellido,
                fechaInicio);

        cuerpoTecnico.put(dni, nuevo);
        guardarJSON();

        System.out.println("Cuerpo técnico agregado correctamente");
    }

    @Override
    public void eliminarElemento(String dni) throws AccionImposible {
        if (!cuerpoTecnico.containsKey(dni))
            throw new AccionImposible("No existe el cuerpo técnico");

        cuerpoTecnico.remove(dni);
        guardarJSON();
        System.out.println("Cuerpo técnico eliminado correctamente");
    }

    @Override
    public CuerpoTecnico devuelveElemento(String dni) throws AccionImposible {
        CuerpoTecnico ct = cuerpoTecnico.get(dni);
        if (ct == null)
            throw new AccionImposible("Cuerpo técnico no encontrado");

        return ct;
    }

    @Override
    public boolean existe(String dni) throws ElementoInexistenteEx {
        if (!cuerpoTecnico.containsKey(dni))
            throw new ElementoInexistenteEx("No existe el cuerpo técnico");

        return true;
    }

    @Override
    public ArrayList<CuerpoTecnico> listar() {
        return new ArrayList<>(cuerpoTecnico.values());
    }

    public void cambiarEstadoContrato(String dni, boolean nuevoEstado) throws ElementoInexistenteEx {
        CuerpoTecnico ct = cuerpoTecnico.get(dni);
        if (ct == null)
            throw new ElementoInexistenteEx("No existe el cuerpo técnico");

        ct.getContrato().setContratoActivo(nuevoEstado);
        guardarJSON();
        System.out.println("Estado contrato actualizado");
    }

    public void modificarCuerpoTecnico(String dni, String nombre, String apellido,
                                       LocalDate fechaNacimiento, String nacionalidad,
                                       Puesto puesto, int aniosExp)
            throws ElementoInexistenteEx {

        CuerpoTecnico ct = cuerpoTecnico.get(dni);
        if (ct == null)
            throw new ElementoInexistenteEx("No existe el miembro del cuerpo técnico con DNI: " + dni);

        // VALIDACIONES
        if (nombre != null && !nombre.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Nombre inválido");
        if (apellido != null && !apellido.matches("[a-zA-Z]+"))
            throw new IllegalArgumentException("Apellido inválido");
        if (nacionalidad != null && !nacionalidad.matches("[a-zA-Z ]+"))
            throw new IllegalArgumentException("Nacionalidad inválida");
        if (aniosExp < 0)
            throw new IllegalArgumentException("Años de experiencia inválidos");

        // MODIFICAR ESTADO
        if (nombre != null) ct.setNombre(nombre);
        if (apellido != null) ct.setApellido(apellido);
        if (fechaNacimiento != null) ct.setFechaNacimiento(fechaNacimiento);
        if (nacionalidad != null) ct.setNacionalidad(nacionalidad);
        if (puesto != null) ct.setPuesto(puesto);
        if (aniosExp >= 0) ct.setAniosExp(aniosExp);

        guardarJSON();
        System.out.println("Cuerpo técnico modificado correctamente");
    }

    public double calcularGastoSalarios() {
        return cuerpoTecnico.values()
                .stream()
                .mapToDouble(ct -> ct.getContrato().getSalario())
                .sum();
    }

    public void aplicarGastoSalarios(LocalDate fecha) {
        try {
            double gasto = calcularGastoSalarios();
            gestionPresupuesto.quitarFondos(gasto, "Salarios cuerpo técnico", fecha);
            System.out.println("Salarios pagados correctamente");
        } catch (Exception e) {
            System.out.println("Error al pagar salarios: " + e.getMessage());
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
}
