import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GestionPresupuesto {
    private Presupuesto presupuesto;
    private List<Transaccion> listaTransacciones;
    public GestionPresupuesto(Presupuesto presupuestoCentral) {
        this.presupuesto = presupuestoCentral;
        this.listaTransacciones = new ArrayList<>();
    }
    public void agregar_fondos(double dinero, String descripcion,String fecha){
        this.presupuesto.añadir_monto(dinero);
        Transaccion t=new Transaccion(descripcion,dinero,"INGRESO", fecha);
        this.listaTransacciones.add(t);
    }
    public void quitarFondos(double dinero, String descripcion,String fecha) throws FondoInsuficienteEx {

        this.presupuesto.quitar_fondos(dinero);
        Transaccion t=new Transaccion(descripcion,dinero,"RETIRO", fecha);
        this.listaTransacciones.add(t);
    }
    public double verSaldoActual(){
        return this.presupuesto.getPresupuesto();
    }
}

