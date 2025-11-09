import java.time.LocalDate;

public class Gol {
    private Jugador jugador;
    private boolean fueGolLocal;
    private String minuto;

    public Gol(Jugador jugador, boolean fueGolLocal, String minuto) {
        this.jugador = jugador;
        this.fueGolLocal = fueGolLocal;
        this.minuto = minuto;
    }

    @Override
    public String toString() {
        return "Gol{" +
                "jugador=" + jugador +
                ", fueGolLocal=" + fueGolLocal +
                ", minuto='" + minuto + '\'' +
                '}';
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public boolean isFueGolLocal() {
        return fueGolLocal;
    }

    public void setFueGolLocal(boolean fueGolLocal) {
        this.fueGolLocal = fueGolLocal;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }
}
