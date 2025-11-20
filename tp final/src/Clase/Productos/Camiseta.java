package Clase.Productos;
import org.json.JSONObject;

public class Camiseta extends Producto {
    private final String sponsor;

    public Camiseta(String nombre, String marca, int cantidad,  String sponsor) {
        super(nombre, marca, cantidad);
        this.tipo = "Camiseta";
        this.sponsor = sponsor;
    }

    @Override
    public String muestraDatos() {
        return "Camiseta" +
                "sponsor='" + sponsor + '\'' +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", cantidad=" + cantidad;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("tipo", "Camiseta");
        obj.put("nombre", nombre);
        obj.put("marca", marca);
        obj.put("cantidad", cantidad);
        obj.put("sponsor", sponsor);
        return obj;
    }

}