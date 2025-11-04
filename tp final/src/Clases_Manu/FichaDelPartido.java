package Clases_Manu;

import java.util.ArrayList;
import java.util.List;

public class FichaDelPartido {
    private int golesLocal;
    private int golesVisitante;
    private final List<Gol> goles;
    private final List<TarjetaAplicada> tarjetas;
    private final List<Jugador> lesionados;


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

    public List<Gol> getGoles() {
        return goles;
    }

    public List<TarjetaAplicada> getTarjetas() {
        return tarjetas;
    }

    public List<Jugador> getLesionados() {
        return lesionados;
    }
}
