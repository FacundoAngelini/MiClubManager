package enums;

public enum Tiposocio {
    INACTIVO(0),
    ACTIVO(15000),
    JUVENIL(5000),
    VITALICIO(8000);

    private final double precio;

    Tiposocio(double precio) {
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }
}
