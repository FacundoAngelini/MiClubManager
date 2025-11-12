import java.util.Objects;

public class Partido {
    private String fecha;
    private boolean esLocal;
    private int golesAFavor;
    private int golesEnContra;
    private int entradasVendidas;
    private double precioEntrada;
    private String rival;

    public Partido(String fecha, boolean esLocal, String rival, int golesAFavor, int golesEnContra, int entradasVendidas, double precioEntrada) {
        this.fecha = fecha;
        this.esLocal = esLocal;
        this.rival = rival;
        this.golesAFavor = golesAFavor;
        this.golesEnContra = golesEnContra;
        this.entradasVendidas = esLocal ? entradasVendidas : 0; // solo si fue local
        this.precioEntrada = precioEntrada;
    }

    public String getFecha() {
        return fecha;
    }

    public boolean isEsLocal() {
        return esLocal;
    }

    public String getRival() {
        return rival;
    }

    public int getGolesAFavor() {
        return golesAFavor;
    }

    public int getGolesEnContra() {
        return golesEnContra;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public double getPrecioEntrada() {
        return precioEntrada;
    }

    public boolean gano() {
        return golesAFavor > golesEnContra;
    }

    public boolean empato() {
        return golesAFavor == golesEnContra;
    }

    public boolean perdio() {
        return golesAFavor < golesEnContra;
    }

    public double calcularRecaudacion() {
        return esLocal ? entradasVendidas * precioEntrada : 0;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "fecha='" + fecha + '\'' +
                ", local=" + esLocal +
                ", rival='" + rival + '\'' +
                ", resultado=" + golesAFavor + "-" + golesEnContra +
                ", recaudacion=" + calcularRecaudacion() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return Objects.equals(fecha, partido.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fecha);
    }
}