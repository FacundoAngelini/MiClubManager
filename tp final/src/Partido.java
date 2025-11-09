import java.time.LocalDate;

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

    public double obtenerGanancia(Estadio estadioDelClub) {
        if (this.estadio.equals(estadioDelClub)) {
            return valorEntrada.getPrecio() * (entradasVendidas - EntradasDadasSocio);
        } else {
            return 0;
        }
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEntradasDadasSocio() {
        return EntradasDadasSocio;
    }

    public void setEntradasDadasSocio(int entradasDadasSocio) {
        EntradasDadasSocio = entradasDadasSocio;
    }

    public ValorEntradas getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(ValorEntradas valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public FichaDelPartido getFichaDelPartido() {
        return fichaDelPartido;
    }

    public void setFichaDelPartido(FichaDelPartido fichaDelPartido) {
        this.fichaDelPartido = fichaDelPartido;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public void setEntradasVendidas(int entradasVendidas) {
        this.entradasVendidas = entradasVendidas;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "fecha='" + fecha + '\'' +
                ", entradasVendidas=" + entradasVendidas +
                ", estadio=" + estadio +
                ", fichaDelPartido=" + fichaDelPartido +
                ", valorEntrada=" + valorEntrada +
                ", EntradasDadasSocio=" + EntradasDadasSocio +
                '}';
    }
}
