public class Gol {
    private Jugador jugador;
    private boolean fueGolLocal;
    private int minuto;

    public Gol(Jugador jugador, boolean fueGolLocal, int minuto) {
        this.jugador = jugador;
        this.fueGolLocal = fueGolLocal;
        this.minuto = minuto;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Gol{");
        sb.append("jugador=").append(jugador);
        sb.append(", fueGolLocal=").append(fueGolLocal);
        sb.append(", minuto=").append(minuto);
        sb.append('}');
        return sb.toString();
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

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
}
