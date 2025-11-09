public class Estadio {
    private String nombre;
    private int capacidad;
    private String ubicacion;
    private double costoMantenimiento;

    public Estadio(String nombre, int capacidad, String ubicacion,  double costoMantenimiento) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.costoMantenimiento = costoMantenimiento;
    }


    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public double CostoMantenimiento() {
        return costoMantenimiento;
    }


    @Override
    public String toString() {
        return "Estadio{" +
                "nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                ", ubicacion='" + ubicacion + '\'' +
                ", costoMantenimiento=" + costoMantenimiento +
                '}';
    }
}
