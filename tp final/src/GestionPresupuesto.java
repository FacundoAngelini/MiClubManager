import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestionPresupuesto {
    private final Presupuesto presupuesto;
    private final ArrayList<Transaccion> listaTransacciones;

    public GestionPresupuesto(Presupuesto presupuestoCentral) {
        this.presupuesto = presupuestoCentral;
        this.listaTransacciones = new ArrayList<>();
    }
    public void agregar_fondos(double dinero, String descripcion,String fecha) throws IngresoInvalido{
       if(dinero<=0)
       {
           throw new IngresoInvalido("El ingreso de dinero es invalido");
       }
       if(descripcion==null || descripcion.isEmpty()){
           throw new IngresoInvalido("la descripcion es obligatoria");
       }
       presupuesto.aniadir_monto(dinero);
       Transaccion t=new Transaccion(descripcion,dinero,"INGRESO", fecha);
       listaTransacciones.add(t);
    }


    public void quitarFondos(double dinero, String descripcion,String fecha) throws FondoInsuficienteEx, IngresoInvalido {
        if(dinero<=0) {
            throw new FondoInsuficienteEx("El monto que se va a retirar debe ser mayor a 0");
        }
        if(descripcion==null || descripcion.isEmpty()){
            throw new IngresoInvalido("la descripcion es obligatoria");
        }
        presupuesto.quitar_fondos(dinero);
        Transaccion t=new Transaccion(descripcion,dinero,"RETIRO", fecha);
        listaTransacciones.add(t);
    }


    public double verSaldoActual(){
        return presupuesto.getPresupuesto();
    }

    public ArrayList<Transaccion> listarTransacciones() {
        return new ArrayList<>(listaTransacciones);
    }

    public void guardarJSON() {
        JSONObject root = new JSONObject();

        root.put("presupuestoActual", presupuesto.getPresupuesto());

        JSONArray transaccionesArray = new JSONArray();
        for (Transaccion t : listaTransacciones) {
            JSONObject obj = new JSONObject();
            obj.put("descripcion", t.getDescripcion());
            obj.put("monto", t.getMonto());
            obj.put("tipo", t.getTipo());
            obj.put("fecha", t.getFecha());
            transaccionesArray.put(obj);
        }

        root.put("transacciones", transaccionesArray);

        JSONUtiles.uploadJSON(root, "presupuesto");
    }
}

