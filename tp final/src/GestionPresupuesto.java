import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class GestionPresupuesto {
    private Presupuesto presupuesto;
    private ArrayList<Transaccion> listaTransacciones;

    public GestionPresupuesto(double saldoInicial) {
        this.presupuesto = new Presupuesto(saldoInicial);
        this.listaTransacciones = new ArrayList<>();
    }

    public GestionPresupuesto(Presupuesto presupuestoCentral) {
        this.presupuesto = presupuestoCentral;
        this.listaTransacciones = new ArrayList<>();
    }

    public void agregar_fondos(double dinero, String descripcion, String fecha) throws IngresoInvalido {
        if (dinero <= 0) {
            throw new IngresoInvalido("El ingreso de dinero es inválido");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IngresoInvalido("La descripción es obligatoria");
        }

        presupuesto.aniadir_monto(dinero);

        Transaccion t = new Transaccion(descripcion, dinero, "INGRESO", fecha);
        listaTransacciones.add(t);
    }

    public void quitarFondos(double dinero, String descripcion, String fecha) throws FondoInsuficienteEx, IngresoInvalido {
        if (dinero <= 0) {
            throw new IngresoInvalido("El monto debe ser mayor que 0");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IngresoInvalido("La descripción es obligatoria");
        }

        if (presupuesto.getPresupuesto() < dinero) {
            throw new FondoInsuficienteEx("Fondos insuficientes para realizar la operación");
        }

        presupuesto.quitar_fondos(dinero);

        Transaccion t = new Transaccion(descripcion, dinero, "RETIRO", fecha);
        listaTransacciones.add(t);
    }

    public double verSaldoActual() {
        return presupuesto.getPresupuesto();
    }

    public void guardarJSON() {
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
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public ArrayList<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }
}
