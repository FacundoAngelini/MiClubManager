public class Transaccion {
    private final String descripcion;
    private final double monto;
    private final String tipo;
    private final String fecha;

    public Transaccion(String descripcion, double monto, String tipo, String fecha) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public String getTipo() {
        return tipo;
    }

    public String getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "descripcion='" + descripcion + '\'' +
                ", monto=" + monto +
                ", tipo='" + tipo + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
