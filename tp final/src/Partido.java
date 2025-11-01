import java.time.LocalDate;

public class Partido {
    private LocalDate fecha;
    private int entradasVendidas;
    private Estadio estadio;
    private FichaDelPartido fichaDelPartido;

    public Partido(LocalDate fecha, int entradasVendidas, Estadio estadio, FichaDelPartido fichaDelPartido) {
        this.fecha = fecha;
        this.entradasVendidas = entradasVendidas;
        this.estadio = estadio;
        this.fichaDelPartido = fichaDelPartido;
    }
    //METODO PARA GETRECAUDACION
    //METODO PARA VER SI EL SOCIO PUEDE COMPRAR LA ENTRADA(IF SOCIOACTIVO=TRUE)
}
