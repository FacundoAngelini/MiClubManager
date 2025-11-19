package Clase.Gestiones;

import Clase.Partidos.Partido;
import Clase.Persona.Jugador;
import interfaz.MetodosComunes;
import exeptions.AccionImposible;
import exeptions.ElementoInexistenteEx;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorPartido implements MetodosComunes<Partido, LocalDate> {

    private final ArrayList<Partido> partidos;
    private final int capacidadEstadio;

    public GestorPartido(int capacidadEstadio) {
        this.partidos = new ArrayList<>();
        this.capacidadEstadio = capacidadEstadio;
    }

    public void agregarPartido(LocalDate fecha, boolean esLocal, String rival, int golesAFavor, int golesEnContra, int entradasVendidas, double precioEntrada, List<Jugador> goleadores, List<Jugador> asistencias, List<String[]> tarjetas, List<Jugador> lesionados) throws AccionImposible {
        if (fecha == null)
            throw new AccionImposible("Fecha inválida");
        if (fecha.isAfter(LocalDate.now()))
            throw new AccionImposible("Fecha futura no permitida");
        if (rival == null || rival.isBlank())
            throw new AccionImposible("Rival inválido");
        if (golesAFavor < 0 || golesEnContra < 0)
            throw new AccionImposible("Goles negativos no permitidos");

        if (esLocal) {
            if (entradasVendidas < 0)
                throw new AccionImposible("Entradas vendidas inválidas");
            if (entradasVendidas > capacidadEstadio)
                throw new AccionImposible("Entradas superan la capacidad del estadio");
            if (precioEntrada < 0)
                throw new AccionImposible("Precio de entrada inválido");
        }

        if (existeInterno(fecha))
            throw new AccionImposible("Ya existe un partido en esa fecha");

        validarListaJugadores(goleadores, "goleadores");
        validarListaJugadores(asistencias, "asistencias");
        validarListaJugadores(lesionados, "lesionados");

        if (tarjetas != null) {
            for (String[] t : tarjetas) {
                if (t == null || t.length != 2)
                    throw new AccionImposible("Formato de tarjeta inválido");
                if (t[0] == null || t[0].isBlank())
                    throw new AccionImposible("DNI del jugador en tarjeta inválido");
                if (t[1] == null || t[1].isBlank())
                    throw new AccionImposible("Tipo de tarjeta inválido");
                if (!t[1].equalsIgnoreCase("amarilla") && !t[1].equalsIgnoreCase("roja"))
                    throw new AccionImposible("Tipo de tarjeta desconocido");
            }
        }

        Partido partido = new Partido(
                fecha, esLocal, rival, golesAFavor, golesEnContra,
                esLocal ? entradasVendidas : 0,
                esLocal ? precioEntrada : 0,
                capacidadEstadio
        );

        if (goleadores != null) {
            for (Jugador j : goleadores) {
                partido.getFichaDelPartido().registrarGol(j, esLocal);
                j.getEstadisticaJugador().agregarGoles(1);
                j.getEstadisticaJugador().agregarPartidos(1);
            }
        }

        if (asistencias != null) {
            for (Jugador j : asistencias) {
                j.getEstadisticaJugador().agregarAsistencias(1);
            }
        }

        if (tarjetas != null) {
            for (String[] t : tarjetas) {
                Jugador j = buscarJugadorPorDNI(t[0]);
                partido.getFichaDelPartido().registrarTarjeta(j, t[1]);
                if (t[1].equalsIgnoreCase("amarilla"))
                    j.getEstadisticaJugador().agregarTarjetasAmarillas(1);
                else
                    j.getEstadisticaJugador().agregarTarjetasRojas(1);
            }
        }

        if (lesionados != null) {
            for (Jugador j : lesionados) {
                partido.getFichaDelPartido().registrarLesion(j);
                j.getEstadisticaJugador().agregarLesiones(1);
            }
        }

        if (golesEnContra == 0 && esLocal) {
            for (Jugador p : getPorteros()) {
                p.getEstadisticaJugador().agregarVallasInvictas(1);
            }
        }

        partidos.add(partido);
        guardarJSON();
    }

    @Override
    public void eliminarElemento(LocalDate fecha) throws AccionImposible {
        if (fecha == null)
            throw new AccionImposible("Fecha inválida");

        boolean eliminado = partidos.removeIf(p -> p.getFecha().equals(fecha));
        if (!eliminado)
            throw new AccionImposible("Partido no encontrado");

        guardarJSON();
    }

    @Override
    public Partido devuelveElemento(LocalDate fecha) throws AccionImposible {
        if (fecha == null)
            throw new AccionImposible("Fecha inválida");

        for (Partido p : partidos) {
            if (p.getFecha().equals(fecha))
                return p;
        }

        throw new AccionImposible("Partido no encontrado");
    }

    @Override
    public boolean existe(LocalDate fecha) throws ElementoInexistenteEx {
        if (!existeInterno(fecha))
            throw new ElementoInexistenteEx("El partido no existe");
        return true;
    }

    private boolean existeInterno(LocalDate fecha) {
        return partidos.stream().anyMatch(p -> p.getFecha().equals(fecha));
    }

    @Override
    public ArrayList<Partido> listar() {
        return new ArrayList<>(partidos);
    }

    public ArrayList<String> listarPartidosInfo() {
        ArrayList<String> lista = new ArrayList<>();
        for (Partido p : partidos) {
            String info = p.getFecha() + " | " + (p.isEsLocal() ? "Local" : "Visitante") +
                    " vs " + p.getRival() +
                    " | Goles: " + p.getGolesAFavor() + "-" + p.getGolesEnContra();
            lista.add(info);
        }
        return lista;
    }

    @Override
    public void guardarJSON() {
        JSONArray arr = new JSONArray();
        for (Partido p : partidos) {
            JSONObject obj = new JSONObject();
            obj.put("fecha", p.getFecha().toString());
            obj.put("local", p.isEsLocal());
            obj.put("rival", p.getRival());
            obj.put("golesAFavor", p.getGolesAFavor());
            obj.put("golesEnContra", p.getGolesEnContra());
            obj.put("entradasVendidas", p.getEntradasVendidas());
            obj.put("precioEntrada", p.getPrecioEntrada());
            arr.put(obj);
        }
        System.out.println("JSON partidos guardado");
    }

    private void validarListaJugadores(List<Jugador> lista, String campo) throws AccionImposible {
        if (lista == null) return;
        for (Jugador j : lista) {
            if (j == null)
                throw new AccionImposible("Lista " + campo + " contiene jugador nulo");
        }
    }

    private Jugador buscarJugadorPorDNI(String dni) throws AccionImposible {
        throw new AccionImposible("Método buscarJugadorPorDNI no implementado");
    }

    private List<Jugador> getPorteros() {
        return new ArrayList<>();
    }
}
