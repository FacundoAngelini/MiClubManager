package Clase.Gestiones;

import Clase.Partidos.Partido;
import exeptions.AccionImposible;
import exeptions.ElementoInexistenteEx;
import interfaz.MetodosComunes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class GestorPartido implements MetodosComunes<Partido, LocalDate> {

    private final ArrayList<Partido> partidos;
    private final int capacidadEstadio;

    public GestorPartido(int capacidadEstadio) {
        this.partidos = new ArrayList<>();
        this.capacidadEstadio = capacidadEstadio;
    }

    public void agregarPartido(
            LocalDate fecha, boolean esLocal, String rival,
            int golesAFavor, int golesEnContra,
            int entradasVendidas, double precioEntrada
    ) throws AccionImposible {

        if (fecha == null || fecha.isAfter(LocalDate.now()))
            throw new AccionImposible("Fecha invalida o futura");

        if (rival == null || rival.isBlank())
            throw new AccionImposible("Rival invalido");

        if (golesAFavor < 0 || golesEnContra < 0)
            throw new AccionImposible("Goles negativos");

        if (esLocal) {
            if (entradasVendidas < 0 || entradasVendidas > capacidadEstadio)
                throw new AccionImposible("Entradas invalidas");

            if (precioEntrada < 0)
                throw new AccionImposible("Precio invalido");
        }

        if (existeInterno(fecha))
            throw new AccionImposible("Ya existe un partido en esa fecha");

        Partido partido = new Partido(
                fecha, esLocal, rival,
                golesAFavor, golesEnContra,
                esLocal ? entradasVendidas : 0,
                esLocal ? precioEntrada : 0,
                capacidadEstadio
        );

        partidos.add(partido);
        guardarJSON();
    }

    @Override
    public void eliminarElemento(LocalDate fecha) throws AccionImposible {
        if (fecha == null)
            throw new AccionImposible("Fecha invalida");

        boolean eliminado = partidos.removeIf(p -> p.getFecha().equals(fecha));

        if (!eliminado)
            throw new AccionImposible("Partido no encontrado");

        guardarJSON();
    }

    @Override
    public Partido devuelveElemento(LocalDate fecha) throws AccionImposible {
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

    public boolean existeInterno(LocalDate fecha) {
        return partidos.stream().anyMatch(p -> p.getFecha().equals(fecha));
    }

    @Override
    public ArrayList<Partido> listar() {
        return new ArrayList<>(partidos);
    }

    public ArrayList<String> listarPartidosInfo() {
        ArrayList<String> lista = new ArrayList<>();

        for (Partido p : partidos) {
            String info = p.getFecha()
                    + " | "
                    + (p.isEsLocal() ? "Local" : "Visitante")
                    + " vs "
                    + p.getRival()
                    + " | "
                    + p.getGolesAFavor()
                    + "-"
                    + p.getGolesEnContra();

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
    }

    // Metodos de estadisticas

    public int getGanados() {
        int c = 0;
        for (Partido p : partidos)
            if (p.gano()) c++;
        return c;
    }

    public int getPerdidos() {
        int c = 0;
        for (Partido p : partidos)
            if (p.perdio()) c++;
        return c;
    }

    public int getEmpatados() {
        int c = 0;
        for (Partido p : partidos)
            if (p.empato()) c++;
        return c;
    }

    public double getPorcentajeGanados() {
        if (partidos.isEmpty()) return 0;
        return (getGanados() * 100.0) / partidos.size();
    }

    public double getPorcentajeEmpatados() {
        if (partidos.isEmpty()) return 0;
        return (getEmpatados() * 100.0) / partidos.size();
    }

    public double getPorcentajePerdidos() {
        if (partidos.isEmpty()) return 0;
        return (getPerdidos() * 100.0) / partidos.size();
    }
}
