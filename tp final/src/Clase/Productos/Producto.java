package Clase.Productos;

import org.json.JSONObject;

public abstract class Producto{
    protected String nombre;
    protected String marca;
    protected int cantidad;

    public Producto(String nombre, String marca, int cantidad) {
        this.nombre = nombre;
        this.marca = marca;
        this.cantidad = cantidad;
    }

    protected String tipo;

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public abstract String muestraDatos();
    public abstract JSONObject toJSON();

    public String getMarca() {
        return marca;
    }
}
