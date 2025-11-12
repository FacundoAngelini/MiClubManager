import exeptions.AccionImposible;
import exeptions.FondoInsuficienteEx;
import exeptions.IngresoInvalido;
import org.json.JSONObject;

public class GestionEstadio {
    private Estadio estadio;
    private GestionPresupuesto  presupuestoCentral;

    public GestionEstadio(GestionPresupuesto presupuestoCentral) {
        this.estadio = estadio;
        this.presupuestoCentral = presupuestoCentral;
    }

    public void agregarEstadio(String nombre, int capacidad, String ubicacion, double costoMantenimiento) {
        this.estadio = new Estadio(nombre, capacidad, ubicacion, costoMantenimiento);
    }

    public void modificar_capacidad(int nuevaCapacidad) throws AccionImposible {
        if(estadio == null){
            throw new AccionImposible("No hay un estadio creado");
        }
         estadio.setCapacidad(nuevaCapacidad);
    }
    public void actualizar_costo_mantenimiento(int nuevoCosto) throws AccionImposible{
      if(estadio == null){
          throw  new AccionImposible("No hay un estadio creado");
      }
      estadio.setCostoMantenimiento(nuevoCosto);
    }

    public void pagarMantenimiento(String fecha) throws AccionImposible, IngresoInvalido {
        if(estadio == null){
            throw new AccionImposible("No hay un estadio creado");
        }

        double monto = estadio.getCostoMantenimiento();

        try {
            this.presupuestoCentral.quitarFondos(monto, "Pago de mantenimiento del estadio", fecha);
        } catch (FondoInsuficienteEx e) {
            System.out.println("Falta de dinero" +e.getMessage());
        }
    }

    public void cambiarNombre(String nuevoNombre) throws AccionImposible {
        if(estadio == null){
            throw new AccionImposible("No hay estadio creado.");
        }
        estadio.setNombre(nuevoNombre);
    }

    public void guardarJSON() {
        if(estadio == null) throw new IllegalStateException("No hay estadio creado.");
        JSONObject obj = new JSONObject();
        obj.put("nombre", estadio.getNombre());
        obj.put("capacidad", estadio.getCapacidad());
        obj.put("ubicacion", estadio.getUbicacion());
        obj.put("costoMantenimiento", estadio.getCostoMantenimiento());
        JSONUtiles.uploadJSON(obj, "Estadio");
    }

    public Estadio getEstadio() {
        return estadio;
    }

}
