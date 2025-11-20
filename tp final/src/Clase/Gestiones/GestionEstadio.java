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

    public void agregarEstadio(String nombre, int capacidad, String ubicacion, double costoMantenimiento)
            throws IllegalArgumentException {

        if (nombre == null || !nombre.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios");
        }
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }
        if (ubicacion == null || ubicacion.isBlank()) {
            throw new IllegalArgumentException("Ubicacion invalida");
        }
        if (costoMantenimiento < 0) {
            throw new IllegalArgumentException("Costo de mantenimiento no puede ser negativo");
        }

        estadio = new Estadio(nombre, capacidad, ubicacion, costoMantenimiento);
        guardarJSON();
        System.out.println("Estadio agregado correctamente");
    }

    public void modificarCapacidad(int nuevaCapacidad) throws AccionImposible {
        validarEstadioExistente();

        if (nuevaCapacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }

        estadio.setCapacidad(nuevaCapacidad);
        guardarJSON();
        System.out.println("Capacidad modificada correctamente");
    }

    public void actualizarCostoMantenimiento(double nuevoCosto) throws AccionImposible {
        validarEstadioExistente();

        if (nuevoCosto < 0) {
            throw new IllegalArgumentException("El costo de mantenimiento no puede ser negativo");
        }

        estadio.setCostoMantenimiento(nuevoCosto);
        guardarJSON();
        System.out.println("Costo de mantenimiento actualizado correctamente");
    }

    public void pagarMantenimiento(LocalDate fecha)
            throws AccionImposible, IngresoInvalido, FondoInsuficienteEx {

        validarEstadioExistente();

        if (fecha == null || fecha.isAfter(LocalDate.now())) {
            throw new IngresoInvalido("La fecha no puede ser futura ni nula");
        }

        double monto = estadio.getCostoMantenimiento();
        presupuestoCentral.quitarFondos(monto, "Pago de mantenimiento del estadio", fecha);
        guardarJSON();
        System.out.println("Mantenimiento pagado correctamente $" + monto);
    }

    public void cambiarNombre(String nuevoNombre) throws AccionImposible {

        validarEstadioExistente();

        if (nuevoNombre == null || !nuevoNombre.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios");
        }

        estadio.setNombre(nuevoNombre);
        guardarJSON();
        System.out.println("Nombre del estadio actualizado correctamente");
    }

    public void guardarJSON() {
        if (estadio == null) return;

        JSONObject obj = new JSONObject();
        obj.put("nombre", estadio.getNombre());
        obj.put("capacidad", estadio.getCapacidad());
        obj.put("ubicacion", estadio.getUbicacion());
        obj.put("costoMantenimiento", estadio.getCostoMantenimiento());

        JSONUtiles.uploadJSON(obj, "Estadio");
    }

    public void validarEstadioExistente() throws AccionImposible {
        if (estadio == null) {
            throw new AccionImposible("No hay un estadio creado");
        }
    }

    public Estadio getEstadio() {
        return estadio;
    }
}
