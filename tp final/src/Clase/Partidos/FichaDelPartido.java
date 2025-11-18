package Clase.Partidos;

import Clase.Persona.Jugador;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FichaDelPartido {

    private final Map<Jugador, Integer> golesPorJugador;
    private final Map<Jugador, String> tarjetas;
    private final Set<Jugador> lesionados;

    public FichaDelPartido() {
        this.golesPorJugador = new HashMap<>();
        this.tarjetas = new HashMap<>();
        this.lesionados = new HashSet<>();
    }

    public void registrarGol(Jugador jugador, boolean esLocal) {
        if (jugador == null) {
            throw new IllegalArgumentException("No se puede registrar gol para un jugador nulo");
        }
        golesPorJugador.put(jugador, golesPorJugador.getOrDefault(jugador, 0) + 1);
    }

    public void registrarTarjeta(Jugador jugador, String tipo) {
        if (jugador == null) {
            throw new IllegalArgumentException("No se puede registrar tarjeta para un jugador nulo");
        }
        if (tipo == null || (!tipo.equalsIgnoreCase("amarilla") && !tipo.equalsIgnoreCase("roja"))) {
            throw new IllegalArgumentException("Tipo de tarjeta invalido " + tipo + " solo amarilla o roja");
        }
        tarjetas.put(jugador, tipo.toLowerCase());
    }

    public void registrarLesion(Jugador jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException("No se puede registrar lesion para un jugador nulo");
        }
        if (lesionados.contains(jugador)) {
            throw new IllegalArgumentException("El jugador " + jugador.getNombre() + " ya esta registrado como lesionado");
        }
        lesionados.add(jugador);
    }

    public Map<Jugador, Integer> getGolesPorJugador() {
        return new HashMap<>(golesPorJugador);
    }

    public Map<Jugador, String> getTarjetas() {
        return new HashMap<>(tarjetas);
    }

    public Set<Jugador> getLesionados() {
        return new HashSet<>(lesionados);
    }

    @Override
    public String toString() {
        return "FichaDelPartido{" +
                "golesPorJugador=" + golesPorJugador +
                ", tarjetas=" + tarjetas +
                ", lesionados=" + lesionados +
                '}';
    }
}
