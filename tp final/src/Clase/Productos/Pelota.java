package Clase.Productos;

import org.json.JSONObject;

public class Pelota extends Producto {
    private String modelo;

    public Pelota(String nombre, String marca, int cantidad, String modelo) {
        super(nombre, marca, cantidad);
        this.tipo = "Pelota";
        this.modelo = modelo;
    }


    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String muestraDatos() {
        return "Pelota" +
                "modelo='" + modelo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", cantidad=" + cantidad;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("tipo", "Pelota");
        obj.put("nombre", nombre);
        obj.put("marca", marca);
        obj.put("cantidad", cantidad);
        obj.put("modelo", modelo);
        return obj;
    }
}
