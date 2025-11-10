import java.util.Objects;

public class Partido {
    private String fecha;
    private int entradasVendidas;
    private Estadio estadio;
    private FichaDelPartido fichaDelPartido;
    private ValorEntradas valorEntrada;
    private int EntradasDadasSocio;

    public Partido(String fecha, int entradasVendidas, Estadio estadio, FichaDelPartido fichaDelPartido, ValorEntradas valorEntrada, int entradasDadasSocio) {
        this.fecha = fecha;
        this.entradasVendidas = entradasVendidas;
        this.estadio = estadio;
        this.fichaDelPartido = fichaDelPartido;
        this.valorEntrada = valorEntrada;
        EntradasDadasSocio = entradasDadasSocio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public void setEntradasVendidas(int entradasVendidas) {
        this.entradasVendidas = entradasVendidas;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public FichaDelPartido getFichaDelPartido() {
        return fichaDelPartido;
    }

    public void setFichaDelPartido(FichaDelPartido fichaDelPartido) {
        this.fichaDelPartido = fichaDelPartido;
    }

    public ValorEntradas getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(ValorEntradas valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public int getEntradasDadasSocio() {
        return EntradasDadasSocio;
    }

    public void setEntradasDadasSocio(int entradasDadasSocio) {
        EntradasDadasSocio = entradasDadasSocio;
    }

    public double obtenerGanancia()
    {
        return (valorEntrada.getPrecio() * (entradasVendidas - EntradasDadasSocio));
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
