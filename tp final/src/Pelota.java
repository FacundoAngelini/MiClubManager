public class Pelota extends Producto{
    private String modelo;

    public Pelota(String nombre, String marca, int cantidad, String modelo) {
        super(nombre, marca, cantidad);
        this.modelo = modelo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    protected String muestraDatos() {
        return "Pelota{" +
                "modelo='" + modelo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", cantidad=" + cantidad +
                '}' ;
    }
}
