import java.time.LocalDate;

public class Partido {
    private LocalDate fecha;
    private int entradasVendidas;
    private Estadio estadio;
    private FichaDelPartido fichaDelPartido;
    private ValorEntradas valorEntrada;
    private int EntradasDadasSocio;

    public Partido(LocalDate fecha, int entradasVendidas, Estadio estadio, FichaDelPartido fichaDelPartido, ValorEntradas valorEntrada, int entradasDadasSocio) {
        this.fecha = fecha;
        this.entradasVendidas = entradasVendidas;
        this.estadio = estadio;
        this.fichaDelPartido = fichaDelPartido;
        this.valorEntrada = valorEntrada;
        EntradasDadasSocio = entradasDadasSocio;
    }

    public double obtenerGanancia()
    {
        return (valorEntrada.getPrecio() * (entradasVendidas - EntradasDadasSocio));
    }

    //METODO PARA VER SI EL SOCIO PUEDE COMPRAR LA ENTRADA(IF SOCIOACTIVO=TRUE)
}
