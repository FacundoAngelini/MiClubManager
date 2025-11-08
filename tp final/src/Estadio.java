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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Estadio{");
        sb.append("nombre='").append(nombre).append('\'');
        sb.append(", capacidad=").append(capacidad);
        sb.append(", ubicacion='").append(ubicacion).append('\'');
        sb.append(", costoMantenimiento=").append(costoMantenimiento);
        sb.append('}');
        return sb.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getCostoMantenimiento() {
        return costoMantenimiento;
    }

    public void setCostoMantenimiento(double costoMantenimiento) {
        this.costoMantenimiento = costoMantenimiento;
    }
}
