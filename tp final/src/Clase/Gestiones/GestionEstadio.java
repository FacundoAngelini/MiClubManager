package Clase.Gestiones;

import Clase.Club.Estadio;
import Clase.Json.JSONUtiles;
import exeptions.AccionImposible;
import exeptions.FondoInsuficienteEx;
import exeptions.IngresoInvalido;
import org.json.JSONObject;

public class GestionEstadio {
    private Estadio estadio;
    private final GestionPresupuesto presupuestoCentral;

    public GestionEstadio(GestionPresupuesto presupuestoCentral) {
        this.presupuestoCentral = presupuestoCentral;
    }

    public void agregarEstadio(String nombre, int capacidad, String ubicacion, double costoMantenimiento) {
        this.estadio = new Estadio(nombre, capacidad, ubicacion, costoMantenimiento);
        guardarJSON();
    }

    public void modificarCapacidad(int nuevaCapacidad) {
        try {
            validarEstadioExistente();
            estadio.setCapacidad(nuevaCapacidad);
            guardarJSON();
            System.out.println("Capacidad modificada correctamente.");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void actualizarCostoMantenimiento(double nuevoCosto) {
        try {
            validarEstadioExistente();
            estadio.setCostoMantenimiento(nuevoCosto);
            guardarJSON();
            System.out.println("Costo de mantenimiento actualizado correctamente.");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void pagarMantenimiento(String fecha) {
        try {
            validarEstadioExistente();
            double monto = estadio.getCostoMantenimiento();
            presupuestoCentral.quitarFondos(monto, "Pago de mantenimiento del estadio", fecha);
            guardarJSON();
            System.out.println("Mantenimiento pagado correctamente: $" + monto);
        } catch (AccionImposible | IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        } catch (FondoInsuficienteEx e) {
            System.out.println("No se pudo pagar mantenimiento, fondos insuficientes: " + e.getMessage());
        }
    }

    public void cambiarNombre(String nuevoNombre) {
        try {
            validarEstadioExistente();
            estadio.setNombre(nuevoNombre);
            guardarJSON();
            System.out.println("Nombre del estadio actualizado correctamente.");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void guardarJSON() {
        if(estadio == null) return;
        JSONObject obj = new JSONObject();
        obj.put("nombre", estadio.getNombre());
        obj.put("capacidad", estadio.getCapacidad());
        obj.put("ubicacion", estadio.getUbicacion());
        obj.put("costoMantenimiento", estadio.getCostoMantenimiento());
        JSONUtiles.uploadJSON(obj, "Clases_Manu.Estadio");
    }

    public void validarEstadioExistente() throws AccionImposible {
        if (estadio == null) {
            throw new AccionImposible("No hay un estadio creado.");
        }
    }

    public Estadio getEstadio() {
        return estadio;
    }
}
