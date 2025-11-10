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
        final StringBuilder sb = new StringBuilder("Transaccion{");
        sb.append("descripcion='").append(descripcion).append('\'');
        sb.append(", monto=").append(monto);
        sb.append(", tipo='").append(tipo).append('\'');
        sb.append(", fecha='").append(fecha).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
