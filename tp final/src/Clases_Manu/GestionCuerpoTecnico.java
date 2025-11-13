package Clases_Manu;

import enums.Puesto;
import exeptions.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GestionCuerpoTecnico implements MetodosComunes<CuerpoTecnico, String> {

    private HashMap<String, CuerpoTecnico> cuerpoTecnico = new HashMap<>();
    private GestionPresupuesto gestionPresupuesto;

    public GestionCuerpoTecnico() {
        this.cuerpoTecnico = cuerpoTecnico;
        this.gestionPresupuesto = gestionPresupuesto;
    }

    public void agregarElemento(String dni, String nombre, String apellido, String fechaNacimiento, String nacionalidad, double salario, String fechaInicio, String fechaFin, int mesesDuracion, Puesto puesto, int aniosExp) throws AccionImposible, ElementoDuplicadoEx {
        if (cuerpoTecnico.containsKey(dni)) {
            throw new ElementoDuplicadoEx("Ya existe un cuerpo técnico con ese DNI");
        }

        Contrato contrato = new Contrato(dni, salario, fechaFin, true, fechaInicio, mesesDuracion);
        CuerpoTecnico nuevoCT = new CuerpoTecnico(dni, nombre, apellido, fechaNacimiento, nacionalidad, contrato, puesto, aniosExp);

        cuerpoTecnico.put(dni, nuevoCT);
    }

    @Override
    public void eliminarElemento(String id) throws AccionImposible {
        if (!cuerpoTecnico.containsKey(id)) {
            throw new ElementoInexistenteEx("El elemento no existe");
        }
        cuerpoTecnico.remove(id);
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
            total += ct.getContrato().getSalario();
        }
        return total;
    }
    public void aplicarGastoSalarios(String fecha) throws IngresoInvalido {
        double gasto = calcularGastoSalarios();
        try {
            gestionPresupuesto.quitarFondos(gasto, "Pago de salarios del cuerpo técnico", fecha);
        } catch (FondoInsuficienteEx e) {
            gestionPresupuesto.getPresupuesto();
        }
    }

    public void cambiarEstadoContrato(String dni, boolean nuevoEstado) throws ElementoInexistenteEx {
        CuerpoTecnico ct = cuerpoTecnico.get(dni);

        if (ct == null) {
            throw new ElementoInexistenteEx("No existe un miembro del cuerpo técnico con DNI: " + dni);
        }

        Contrato contrato = ct.getContrato();
        if (contrato == null) {
            throw new ElementoInexistenteEx("El cuerpo técnico no tiene contrato asignado.");
        }

        contrato.setContratoActivo(nuevoEstado);
        cuerpoTecnico.put(dni, ct);
    }

    @Override
    public void guardarJSON() {
        JSONArray arrayCT = new JSONArray();

        for (CuerpoTecnico ct : cuerpoTecnico.values()) {
            JSONObject obj = new JSONObject();
            obj.put("DNI", ct.getDni());
            obj.put("nombre", ct.getNombre());
            obj.put("apellido", ct.getApellido());
            obj.put("fechaNacimiento", ct.getFechaNacimiento());
            obj.put("nacionalidad", ct.getNacionalidad());
            obj.put("puesto", ct.getPuesto().toString());
            obj.put("aniosExp", ct.getAniosExp());

            Contrato contrato = ct.getContrato();
            if (contrato != null) {
                JSONObject contratoJSON = new JSONObject();
                contratoJSON.put("dni", contrato.getDni());
                contratoJSON.put("salario", contrato.getSalario());
                contratoJSON.put("fechaInicio", contrato.getFechaInicio());
                contratoJSON.put("fechaFin", contrato.getFechaFin());
                contratoJSON.put("mesesDuracion", contrato.getMesesDuracion());
                contratoJSON.put("contratoActivo", contrato.isContratoActivo());
                obj.put("contrato", contratoJSON);
            } else {
                obj.put("contrato", JSONObject.NULL);
            }

            arrayCT.put(obj);
        }
        JSONUtiles.uploadJSON(arrayCT, "cuerpoTecnico");
    }

    public void modificarCuerpoTecnico(String dni, String nombre, String apellido, String fechaNacimiento, String nacionalidad, Contrato contrato, Puesto puesto, int aniosExp)  throws AccionImposible {
        if (!cuerpoTecnico.containsKey(dni)) {
            throw new ElementoInexistenteEx("El cuerpo técnico no existe");
        }
        CuerpoTecnico ctExistente = cuerpoTecnico.get(dni);
        ctExistente.setNombre(nombre);
        ctExistente.setApellido(apellido);
        ctExistente.setFechaNacimiento(fechaNacimiento);
        ctExistente.setNacionalidad(nacionalidad);
        ctExistente.setContrato(contrato);
        ctExistente.setPuesto(puesto);
        ctExistente.setAniosExp(aniosExp);

        cuerpoTecnico.put(dni, ctExistente);
    }

}
