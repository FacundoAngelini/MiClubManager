package Clase.Gestiones;

import Clase.Club.Estadio;
import Clase.Json.JSONUtiles;
import exeptions.AccionImposible;
import exeptions.FondoInsuficienteEx;
import exeptions.IngresoInvalido;
import org.json.JSONObject;

import java.time.LocalDate;

public class GestionEstadio {

    private Estadio estadio;
    private final GestionPresupuesto presupuestoCentral;

    public GestionEstadio(GestionPresupuesto presupuestoCentral) {
        this.presupuestoCentral = presupuestoCentral;
    }

    public void agregarEstadio(String nombre, int capacidad, String ubicacion, double costoMantenimiento) {
        try {
            if (nombre == null || !nombre.matches("[a-zA-Z ]+")) {
                throw new IllegalArgumentException("el nombre solo puede contener letras y espacios");
            }

            estadio = new Estadio(nombre, capacidad, ubicacion, costoMantenimiento);
            guardarJSON();
            System.out.println("estadio agregado correctamente");
        } catch (IllegalArgumentException e) {
            System.out.println("error al agregar estadio " + e.getMessage());
        }
    }

    public void modificarCapacidad(int nuevaCapacidad) {
        try {
            validarEstadioExistente();
            estadio.setCapacidad(nuevaCapacidad);
            guardarJSON();
            System.out.println("capacidad modificada correctamente");
        } catch (AccionImposible | IllegalArgumentException e) {
            System.out.println("error al modificar capacidad " + e.getMessage());
        }
    }

    public void actualizarCostoMantenimiento(double nuevoCosto) {
        try {
            validarEstadioExistente();
            estadio.setCostoMantenimiento(nuevoCosto);
            guardarJSON();
            System.out.println("costo de mantenimiento actualizado correctamente");
        } catch (AccionImposible | IllegalArgumentException e) {
            System.out.println("error al actualizar costo " + e.getMessage());
        }
    }

    public void pagarMantenimiento(LocalDate fecha) {
        try {
            validarEstadioExistente();

            if (fecha == null || fecha.isAfter(LocalDate.now())) {
                throw new IngresoInvalido("la fecha no puede ser futura ni nula");
            }

            double monto = estadio.getCostoMantenimiento();
            presupuestoCentral.quitarFondos(monto, "pago de mantenimiento del estadio", fecha);
            guardarJSON();
            System.out.println("mantenimiento pagado correctamente $" + monto);
        } catch (AccionImposible | IngresoInvalido e) {
            System.out.println("error al pagar mantenimiento " + e.getMessage());
        } catch (FondoInsuficienteEx e) {
            System.out.println("fondos insuficientes para pagar mantenimiento " + e.getMessage());
        }
    }

    public void cambiarNombre(String nuevoNombre) {
        try {
            validarEstadioExistente();

            if (nuevoNombre == null || !nuevoNombre.matches("[a-zA-Z ]+")) {
                throw new IllegalArgumentException("el nombre solo puede contener letras y espacios");
            }

            estadio.setNombre(nuevoNombre);
            guardarJSON();
            System.out.println("nombre del estadio actualizado correctamente");
        } catch (AccionImposible | IllegalArgumentException e) {
            System.out.println("error al cambiar nombre " + e.getMessage());
        }
    }

    public void guardarJSON() {
        if (estadio == null) return;

        JSONObject obj = new JSONObject();
        obj.put("nombre", estadio.getNombre());
        obj.put("capacidad", estadio.getCapacidad());
        obj.put("ubicacion", estadio.getUbicacion());
        obj.put("costoMantenimiento", estadio.getCostoMantenimiento());

        JSONUtiles.uploadJSON(obj, "Clases_Manu.Estadio");
    }

    public void validarEstadioExistente() throws AccionImposible {
        if (estadio == null) {
            throw new AccionImposible("no hay un estadio creado");
        }
    }

    public Estadio getEstadio() {
        return estadio;
    }
}
