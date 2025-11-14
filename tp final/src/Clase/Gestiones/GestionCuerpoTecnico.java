package Clase.Gestiones;

import Clase.Json.JSONUtiles;
import Clase.Persona.CuerpoTecnico;
import Clase.Presupuesto.Contrato;
import enums.Puesto;
import exeptions.*;
import interfaz.MetodosComunes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GestionCuerpoTecnico implements MetodosComunes<CuerpoTecnico, String> {

    private final HashMap<String, CuerpoTecnico> cuerpoTecnico;
    private final GestionPresupuesto gestionPresupuesto;

    public GestionCuerpoTecnico(GestionPresupuesto gestionPresupuesto) {
        this.cuerpoTecnico = new HashMap<>();
        this.gestionPresupuesto = gestionPresupuesto;
    }

    public void agregarElemento(String dni, String nombre, String apellido, String fechaNacimiento, String nacionalidad, double salario, String fechaInicio, String fechaFin, int mesesDuracion, Puesto puesto, int aniosExp) {
        try {
            if (cuerpoTecnico.containsKey(dni)) {
                throw new ElementoDuplicadoEx("Ya existe un miembro del cuerpo técnico con ese DNI");
            }

            gestionPresupuesto.quitarFondos(salario, "Contrato cuerpo técnico: " + nombre + " " + apellido, fechaInicio);

            Contrato contrato = new Contrato(dni, salario, fechaFin, true, fechaInicio, mesesDuracion);
            CuerpoTecnico nuevoCT = new CuerpoTecnico(dni, nombre, apellido, fechaNacimiento, nacionalidad, contrato, puesto, aniosExp);

            cuerpoTecnico.put(dni, nuevoCT);

            guardarJSON();
            System.out.println("Cuerpo técnico agregado correctamente.");

        } catch (ElementoDuplicadoEx | FondoInsuficienteEx | IngresoInvalido e) {
            System.out.println("Error al agregar cuerpo técnico: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al agregar cuerpo técnico: " + e.getMessage());
        }
    }

    @Override
    public void eliminarElemento(String dni) {
        try {
            if (!cuerpoTecnico.containsKey(dni)) {
                throw new ElementoInexistenteEx("El cuerpo técnico no existe");
            }

            cuerpoTecnico.remove(dni);
            guardarJSON();
            System.out.println("Cuerpo técnico eliminado correctamente.");
        } catch (ElementoInexistenteEx e) {
            System.out.println("Error al eliminar cuerpo técnico: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al eliminar cuerpo técnico: " + e.getMessage());
        }
    }

    @Override
    public CuerpoTecnico devuelveElemento(String dni) {
        try {
            if (!cuerpoTecnico.containsKey(dni)) {
                throw new ElementoInexistenteEx("El cuerpo técnico no existe");
            }
            return cuerpoTecnico.get(dni);
        } catch (ElementoInexistenteEx e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean existe(String dni) {
        return cuerpoTecnico.containsKey(dni);
    }

    @Override
    public ArrayList<CuerpoTecnico> listar() {
        Collection<CuerpoTecnico> valores = cuerpoTecnico.values();
        return new ArrayList<>(valores);
    }

    public void cambiarEstadoContrato(String dni, boolean nuevoEstado) {
        try {
            CuerpoTecnico ct = cuerpoTecnico.get(dni);
            if (ct == null) {
                throw new ElementoInexistenteEx("No existe el miembro del cuerpo técnico.");
            }

            Contrato contrato = ct.getContrato();
            if (contrato == null) {
                throw new ElementoInexistenteEx("El contrato no existe.");
            }

            contrato.setContratoActivo(nuevoEstado);
            guardarJSON();
            System.out.println("Estado del contrato actualizado.");
        } catch (ElementoInexistenteEx e) {
            System.out.println("Error al cambiar estado de contrato: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    public void modificarCuerpoTecnico(String dni, String nombre, String apellido, String fechaNacimiento, String nacionalidad, Contrato contrato, Puesto puesto, int aniosExp) {
        try {
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
            guardarJSON();
            System.out.println("Cuerpo técnico modificado correctamente.");
        } catch (ElementoInexistenteEx e) {
            System.out.println("Error al modificar cuerpo técnico: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al modificar cuerpo técnico: " + e.getMessage());
        }
    }

    public double calcularGastoSalarios() {
        return cuerpoTecnico.values().stream().mapToDouble(ct -> ct.getContrato().getSalario()).sum();
    }

    public void aplicarGastoSalarios(String fecha) {
        try {
            double gasto = calcularGastoSalarios();
            gestionPresupuesto.quitarFondos(gasto, "Pago de salarios del cuerpo técnico", fecha);
            System.out.println("Salarios pagados correctamente.");
        } catch (FondoInsuficienteEx | IngresoInvalido e) {
            System.out.println("Error al pagar salarios: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    @Override
    public void guardarJSON() {
        try {
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
        } catch (Exception e) {
            System.out.println("Error al guardar JSON: " + e.getMessage());
        }
    }
}
