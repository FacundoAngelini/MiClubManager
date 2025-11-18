package Clase.Club;

public class Estadio {
    private String nombre;
    private int capacidad;
    private final String ubicacion;
    private double costoMantenimiento;

    public Estadio(String nombre, int capacidad, String ubicacion, double costoMantenimiento) {
        setNombre(nombre);
        setCapacidad(capacidad);
        this.ubicacion = ubicacion;
        setCostoMantenimiento(costoMantenimiento);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre == null || !nombre.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios");
        }
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        if(capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }
        this.capacidad = capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public double getCostoMantenimiento() {
        return costoMantenimiento;
    }

    public void setCostoMantenimiento(double costoMantenimiento) {
        if(costoMantenimiento < 0) {
            throw new IllegalArgumentException("El costo de mantenimiento no puede ser negativo");
        }
        this.costoMantenimiento = costoMantenimiento;
    }
}
