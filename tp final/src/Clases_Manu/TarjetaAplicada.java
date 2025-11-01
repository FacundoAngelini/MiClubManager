package Clases_Manu;

import java.time.LocalDate;

public class TarjetaAplicada {
    private TipoTarjeta tipoTarjeta;
    private Jugador jugador;
    private LocalDate minuto;

    public TarjetaAplicada(TipoTarjeta tipoTarjeta, Jugador jugador, LocalDate minuto) {
        this.tipoTarjeta = tipoTarjeta;
        this.jugador = jugador;
        this.minuto = minuto;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Clases_Manu.TarjetaAplicada{");
        sb.append("tipoTarjeta=").append(tipoTarjeta);
        sb.append(", jugador=").append(jugador);
        sb.append(", minuto=").append(minuto);
        sb.append('}');
        return sb.toString();
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

    public LocalDate getMinuto() {
        return minuto;
    }

    public void setMinuto(LocalDate minuto) {
        this.minuto = minuto;
    }
}
