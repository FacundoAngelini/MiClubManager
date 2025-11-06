import java.time.LocalDate;

public class Partido {
    private LocalDate fecha;
    private int entradasVendidas;
    private Estadio estadio;
    private FichaDelPartido fichaDelPartido;
    private ValorEntradas valorEntrada;

    public Partido(LocalDate fecha, Estadio estadio, FichaDelPartido fichaDelPartido, ValorEntradas valorEntrada, int entradasVendidas) {
        this.fecha = fecha;
        this.estadio = estadio;
        this.fichaDelPartido = fichaDelPartido;
        this.valorEntrada = valorEntrada;
        this.entradasVendidas = entradasVendidas;
    }



    //METODO PARA GETRECAUDACION
    //METODO PARA VER SI EL SOCIO PUEDE COMPRAR LA ENTRADA(IF SOCIOACTIVO=TRUE)
}
