import java.util.ArrayList;

public class FichaDelPartido {
    private int golesLocal;
    private int golesVisitante;
    private final ArrayList<Gol> goles;
    private final ArrayList<TarjetaAplicada> tarjetas;
    private final ArrayList<Jugador> lesionados;


    public FichaDelPartido(int golesLocal, int golesVisitante) {
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.goles = new ArrayList<>();
        this.tarjetas = new ArrayList<>();
        this.lesionados = new ArrayList<>();
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public ArrayList<Gol> getGoles() {
        return goles;
    }

    public ArrayList<TarjetaAplicada> getTarjetas() {
        return tarjetas;
    }

    public ArrayList<Jugador> getLesionados() {
        return lesionados;
    }
}
