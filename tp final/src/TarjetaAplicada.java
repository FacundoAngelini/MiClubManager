import java.time.LocalDate;

public class TarjetaAplicada {
    private TipoTarjeta tipoTarjeta;
    private Jugador jugador;
    private String minuto;

    public TarjetaAplicada(TipoTarjeta tipoTarjeta, Jugador jugador, String minuto) {
        this.tipoTarjeta = tipoTarjeta;
        this.jugador = jugador;
        this.minuto = minuto;
    }

    @Override
    public String toString() {
        return "TarjetaAplicada{" +
                "tipoTarjeta=" + tipoTarjeta +
                ", jugador=" + jugador +
                ", minuto='" + minuto + '\'' +
                '}';
    }

    public TipoTarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(TipoTarjeta tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }
}
