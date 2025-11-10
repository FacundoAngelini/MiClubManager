public class Estadio {
    private final String nombre;
    private int capacidad;
    private final String ubicacion;
    private double costoMantenimiento;

    public Estadio(String nombre, int capacidad, String ubicacion, double costoMantenimiento) {
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

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public double getCostoMantenimiento() {
        return costoMantenimiento;
    }

    public void setCostoMantenimiento(double costoMantenimiento) {
        this.costoMantenimiento = costoMantenimiento;
    }
}
