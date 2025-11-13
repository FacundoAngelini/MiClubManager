package Clase.Partidos;

import Clase.Persona.Jugador;

import java.util.*;

public class FichaDelPartido {
    private int golesLocal;
    private int golesVisitante;
    private final Map<Jugador, Integer> golesPorJugador;
    private final Map<Jugador, String> tarjetas;
    private final List<Jugador> lesionados;

    public FichaDelPartido() {
        this.golesLocal = 0;
        this.golesVisitante = 0;
        this.golesPorJugador = new HashMap<>();
        this.tarjetas = new HashMap<>();
        this.lesionados = new ArrayList<>();
    }

    public void registrarGol(Jugador jugador, boolean esLocal) {
        golesPorJugador.merge(jugador, 1, Integer::sum);
        jugador.getEstadisticaJugador().agregarGol();
        if (esLocal) golesLocal++;
        else golesVisitante++;
    }

    public void registrarTarjeta(Jugador jugador, String tipo) {
        tarjetas.put(jugador, tipo.toLowerCase());
        if (tipo.equalsIgnoreCase("amarilla")) {
            jugador.getEstadisticaJugador().agregarTarjetaAmarilla();
        } else if (tipo.equalsIgnoreCase("roja")) {
            jugador.getEstadisticaJugador().agregarTarjetaRoja();
        }
    }

    public void registrarLesion(Jugador jugador) {
        if (!lesionados.contains(jugador)) {
            lesionados.add(jugador);
            jugador.getEstadisticaJugador().agregarLesion();
        }
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public Map<Jugador, Integer> getGolesPorJugador() {
        return golesPorJugador;
    }

    public Map<Jugador, String> getTarjetas() {
        return tarjetas;
    }

    public List<Jugador> getLesionados() {
        return lesionados;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("resultado: ").append(golesLocal).append(" - ").append(golesVisitante).append("\n");

        sb.append("goles por jugador:\n");
        for (Map.Entry<Jugador, Integer> entry : golesPorJugador.entrySet()) {
            sb.append("  ").append(entry.getKey().getNombre()).append(": ").append(entry.getValue()).append("\n");
        }

        sb.append("tarjetas:\n");
        for (Map.Entry<Jugador, String> entry : tarjetas.entrySet()) {
            sb.append("  ").append(entry.getKey().getNombre()).append(": ").append(entry.getValue()).append("\n");
        }

        if (!lesionados.isEmpty()) {
            sb.append("lesionados:\n");
            for (Jugador j : lesionados) {
                sb.append("  ").append(j.getNombre()).append("\n");
            }
        }

        return sb.toString();
    }
}
