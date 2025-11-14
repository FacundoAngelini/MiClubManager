package Clase.Gestiones;

import Clase.Json.JSONUtiles;
import Clase.Presupuesto.Presupuesto;
import Clase.Presupuesto.Transaccion;
import exeptions.FondoInsuficienteEx;
import exeptions.IngresoInvalido;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class GestionPresupuesto {
    private final Presupuesto presupuesto;
    private final ArrayList<Transaccion> listaTransacciones;

    public GestionPresupuesto() {
        this.presupuesto = new Presupuesto(3000000);
        this.listaTransacciones = new ArrayList<>();
    }


    public void agregar_fondos(double dinero, String descripcion, String fecha) throws IngresoInvalido {
        if (dinero <= 0) {
            throw new IngresoInvalido("El ingreso de dinero debe ser mayor que 0");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IngresoInvalido("La descripcion es obligatoria");
        }

        presupuesto.aniadir_monto(dinero);
        Transaccion t = new Transaccion(descripcion, dinero, "INGRESO", fecha);
        listaTransacciones.add(t);

        try {
            guardarJSON();
        } catch (Exception e) {
            System.out.println("Advertencia: no se pudo guardar el presupuesto en disco. " + e.getMessage());
        }
    }

    public void quitarFondos(double dinero, String descripcion, String fecha) throws FondoInsuficienteEx, IngresoInvalido {
        if (dinero <= 0) {
            throw new IngresoInvalido("El monto debe ser mayor que 0");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IngresoInvalido("La descripcion es obligatoria");
        }

        if (presupuesto.getPresupuesto() < dinero) {
            throw new FondoInsuficienteEx("Fondos insuficientes para realizar la operacion");
        }

        presupuesto.quitar_fondos(dinero);
        Transaccion t = new Transaccion(descripcion, dinero, "RETIRO", fecha);
        listaTransacciones.add(t);

        try {
            guardarJSON();
        } catch (Exception e) {
            System.out.println("Advertencia: no se pudo guardar el presupuesto en disco. " + e.getMessage());
        }
    }

    public double verSaldoActual() {
        return presupuesto.getPresupuesto();
    }

    public void guardarJSON() {
        try {
            JSONObject presupuestoJSON = new JSONObject();
            presupuestoJSON.put("saldoActual", presupuesto.getPresupuesto());

            JSONArray transaccionesArray = new JSONArray();
            for (Transaccion t : listaTransacciones) {
                JSONObject tJSON = new JSONObject();
                tJSON.put("descripcion", t.getDescripcion());
                tJSON.put("monto", t.getMonto());
                tJSON.put("tipo", t.getTipo());
                tJSON.put("fecha", t.getFecha());
                transaccionesArray.put(tJSON);
            }

            presupuestoJSON.put("transacciones", transaccionesArray);

            JSONUtiles.uploadJSON(presupuestoJSON, "presupuesto");
        } catch (Exception e) {
            System.out.println("Error al guardar el presupuesto en JSON: " + e.getMessage());
        }
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public ArrayList<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }
}
