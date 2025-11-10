import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GestionPresupuesto implements MetodosComunes <Presupuesto, Integer>{
    private Presupuesto presupuesto;

    public GestionPresupuesto() {
        this.presupuesto = new Presupuesto(0,0);
    }

    @Override
    public void agregarElemento(Presupuesto elemento) throws IngresoInvalido {
        throw new IngresoInvalido("No se puede ingresar un nuevo presupuesto");
    }

    @Override
    public void eliminarElemento(int id) throws AccionImposible {
        throw new AccionImposible("No se puede eliminar un nuevo presupuesto");
    }

    @Override
    public void modificarElemento(Presupuesto elemento) throws AccionImposible {
        throw new AccionImposible("No se puede modificar directamente el presupuesto");
    }

    @Override
    public boolean existe(Presupuesto elemento) {
        return elemento != null;
    }

    @Override
    public ArrayList<Presupuesto> listar() {
        ArrayList<Presupuesto> lista = new ArrayList<>();
        lista.add(presupuesto);
        return lista;
    }

    public  void guardarJSON() {
        JSONObject obj = new JSONObject();
        obj.put("ingreso", presupuesto.getIngreso());
        obj.put("egreso", presupuesto.getEgreso());
        obj.put("balance total", presupuesto.calcularResultados());

        JSONUtiles.uploadJSON(obj, "presupuesto");
    }

    public void agregarIngreso(double monto)  {
        try{
            presupuesto.agregarBeneificos(monto);
        } catch (IngresoInvalido e) {
            System.out.println("Error al ingreso del presupuesto" +e.getMessage());
        }
    }

    public void agregarEgreso(double monto)  {
        try{
            presupuesto.agregarPerdidas(monto);
        } catch (IngresoInvalido e) {
            System.out.println("Error al ingresar un egreso del presupuesto" +e.getMessage());
        }
    }

    public void reiniciarPresupuesto(){
        presupuesto.reiniciarPresupuesto();
    }

    public void mostrarBalance(){
        presupuesto.mostrarResumen();
    }



}

