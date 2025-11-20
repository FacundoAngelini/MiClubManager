package Clase.Gestiones;

import Clase.Json.JSONUtiles;
import Clase.Presupuesto.Presupuesto;
import Clase.Presupuesto.Transaccion;
import exeptions.FondoInsuficienteEx;
import exeptions.IngresoInvalido;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class GestionPresupuesto {

    private final Presupuesto presupuesto;
    private final ArrayList<Transaccion> listaTransacciones;

    public GestionPresupuesto() {
        this.presupuesto = new Presupuesto(3000000);
        this.listaTransacciones = new ArrayList<>();
        cargarJSON();
    }

    public void agregarFondos(double dinero, String descripcion, LocalDate fecha) throws IngresoInvalido {
        if(dinero <= 0) {
            throw new IngresoInvalido("El ingreso de dinero debe ser mayor que 0");
        }
        if(descripcion == null || descripcion.isBlank()){
            throw new IngresoInvalido("La descripcion es obligatoria");
        }

        presupuesto.aniadirMonto(dinero);
        Transaccion t = new Transaccion(descripcion, dinero, "INGRESO", fecha);
        listaTransacciones.add(t);
        guardarJSON();
    }

    public void quitarFondos(double dinero, String descripcion, LocalDate fecha) throws FondoInsuficienteEx, IngresoInvalido {
        if(dinero <= 0) throw new IngresoInvalido("El monto debe ser mayor que 0");
        if(descripcion == null || descripcion.isBlank()) throw new IngresoInvalido("La descripcion es obligatoria");

        presupuesto.quitarFondos(dinero);
        Transaccion t = new Transaccion(descripcion, dinero, "RETIRO", fecha);
        listaTransacciones.add(t);
        guardarJSON();
    }

    public double verSaldoActual() {
        return presupuesto.getPresupuesto();
    }

    public void guardarJSON() {
        try {
            JSONObject presupuestoJSON = new JSONObject();
            presupuestoJSON.put("saldoActual", presupuesto.getPresupuesto());

            JSONArray transaccionesArray = new JSONArray();
            for(Transaccion t : listaTransacciones) {
                JSONObject tJSON = new JSONObject();
                tJSON.put("descripcion", t.getDescripcion());
                tJSON.put("monto", t.getMonto());
                tJSON.put("tipo", t.getTipo());
                tJSON.put("fecha", t.getFecha().toString());
                transaccionesArray.put(tJSON);
            }

            presupuestoJSON.put("transacciones", transaccionesArray);
            JSONUtiles.uploadJSON(presupuestoJSON, "presupuesto");
        } catch(Exception e) {
            System.out.println("Error al guardar presupuesto ");
        }
    }

    public ArrayList<String> listarTransaccionesInfo() {
        ArrayList<String> lista = new ArrayList<>();
        for (Transaccion t : listaTransacciones) {
            String info = t.getTipo() + " | " + t.getMonto() + " | " + t.getDescripcion() + " | " + t.getFecha();
            lista.add(info);
        }
        return lista;
    }

    public void cargarJSON() {
        String contenido = JSONUtiles.downloadJSON("presupuesto"); // lee presupuesto.json
        if (contenido.isBlank()) return;
        JSONObject presupuestoJSON = new JSONObject(contenido);
        double saldoActual = presupuestoJSON.getDouble("saldoActual");
        presupuesto.setPresupuesto(saldoActual);
        listaTransacciones.clear();
        JSONArray transaccionesArray = presupuestoJSON.getJSONArray("transacciones");
        for (int i = 0; i < transaccionesArray.length(); i++) {
            JSONObject tJSON = transaccionesArray.getJSONObject(i);
            String descripcion = tJSON.getString("descripcion");
            double monto = tJSON.getDouble("monto");
            String tipo = tJSON.getString("tipo");
            LocalDate fecha = LocalDate.parse(tJSON.getString("fecha"));

            Transaccion t = new Transaccion(descripcion, monto, tipo, fecha);
            listaTransacciones.add(t);
        }
    }



    public Presupuesto getPresupuesto() {
        return presupuesto;
    }
}
