package Clase.Gestiones;

import Clase.Json.JSONUtiles;
import Clase.Partidos.FichaDelPartido;
import Clase.Partidos.Partido;
import Clase.Persona.Jugador;
import exeptions.*;
import interfaz.MetodosComunes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GestorPartido implements MetodosComunes<Partido, String> {

    private final ArrayList<Partido> partidos;
    private final GestionJugadores gestionJugadores;
    private final GestionPresupuesto gestionPresupuesto;

    public GestorPartido(GestionJugadores gestionJugadores, GestionPresupuesto gestionPresupuesto) {
        this.partidos = new ArrayList<>();
        this.gestionJugadores = gestionJugadores;
        this.gestionPresupuesto = gestionPresupuesto;
    }

    public void agregarPartido(String fecha, boolean esLocal, String rival, int golesAFavor, int golesEnContra, int entradasVendidas, double precioEntrada) throws IngresoInvalido, ElementoDuplicadoEx {
        if (fecha == null || fecha.isEmpty())
        {
            throw new IngresoInvalido("La fecha del partido no puede estar vacía.");
        }

        if (partidos.stream().anyMatch(p -> p.getFecha().equals(fecha)))
        {
            throw new ElementoDuplicadoEx("Ya existe un partido registrado en la fecha " + fecha);
        }

        Partido nuevoPartido = new Partido(fecha, esLocal, rival, golesAFavor, golesEnContra,
                esLocal ? entradasVendidas : 0, esLocal ? precioEntrada : 0);

        partidos.add(nuevoPartido);

        if (esLocal) {
            double recaudacion = nuevoPartido.calcularRecaudacion();
            gestionPresupuesto.agregar_fondos(recaudacion, "Recaudación partido vs " + rival, fecha);
            gestionPresupuesto.guardarJSON();
        }
        guardarJSON();
    }

    @Override
    public void eliminarElemento(String fecha) throws AccionImposible {
        Partido partido = partidos.stream().filter(p -> p.getFecha().equals(fecha)).findFirst().orElseThrow(() -> new AccionImposible("No se encontró un partido con la fecha " + fecha));

        partidos.remove(partido);
        guardarJSON();
    }

    @Override
    public Partido devuelveElemento(String fecha) throws AccionImposible {
        return partidos.stream().filter(p -> p.getFecha().equals(fecha)).findFirst().orElseThrow(() -> new AccionImposible("No existe un partido con la fecha " + fecha));
    }

    @Override
    public boolean existe(String fecha) throws ElementoInexistenteEx {
        boolean existe = partidos.stream().anyMatch(p -> p.getFecha().equals(fecha));
        if (!existe) {
            throw new ElementoInexistenteEx("No se encontró un partido con la fecha " + fecha);
        }
        return true;
    }

    @Override
    public ArrayList<Partido> listar() {
        return new ArrayList<>(partidos);
    }

    @Override
    public void guardarJSON() {
        try {
            JSONArray array = new JSONArray();
            for (Partido p : partidos) {
                JSONObject obj = new JSONObject();
                obj.put("fecha", p.getFecha());
                obj.put("local", p.isEsLocal());
                obj.put("rival", p.getRival());
                obj.put("golesAFavor", p.getGolesAFavor());
                obj.put("golesEnContra", p.getGolesEnContra());
                obj.put("resultado", resultadoTexto(p));
                obj.put("entradasVendidas", p.getEntradasVendidas());
                obj.put("precioEntrada", p.getPrecioEntrada());
                obj.put("recaudacion", p.calcularRecaudacion());

                FichaDelPartido ficha = p.getFichaDelPartido();
                JSONObject fichaObj = new JSONObject();

                JSONObject golesObj = new JSONObject();
                ficha.getGolesPorJugador().forEach((jug, cant) -> golesObj.put(jug.getNombre(), cant));
                fichaObj.put("golesPorJugador", golesObj);

                JSONObject tarjetasObj = new JSONObject();
                ficha.getTarjetas().forEach((jug, tipo) -> tarjetasObj.put(jug.getNombre(), tipo));
                fichaObj.put("tarjetas", tarjetasObj);

                fichaObj.put("lesionados", ficha.getLesionados().stream().map(Jugador::getNombre).toArray());

                obj.put("fichaDelPartido", fichaObj);
                array.put(obj);
            }
            JSONUtiles.uploadJSON(array, "partidos");
        } catch (Exception e) {
            System.out.println("Error guardando JSON de partidos: " + e.getMessage());
        }
    }

    private String resultadoTexto(Partido p) {
        if (p.gano()) {
            return "Victoria";
        }
        if (p.empato()) {
            return "Empate";
        }
        return "Derrota";
    }

    public void registrarGol(String fechaPartido, Jugador jugador, boolean esLocal) throws ElementoInexistenteEx {
        Partido partido = buscarPorFecha(fechaPartido);
        if (partido == null) {
            throw new ElementoInexistenteEx("No se encontró el partido.");
        }
        partido.getFichaDelPartido().registrarGol(jugador, esLocal);
        gestionJugadores.actualizarEstadisticas(jugador.getDni(), true, false,false, false );
        guardarJSON();
    }

    public void registrarTarjeta(String fechaPartido, Jugador jugador, String tipo) throws ElementoInexistenteEx {
        Partido partido = buscarPorFecha(fechaPartido);
        if (partido == null) {
            throw new ElementoInexistenteEx("No se encontró el partido.");
        }
        partido.getFichaDelPartido().registrarTarjeta(jugador, tipo);

        if (tipo.equalsIgnoreCase("amarilla"))
            gestionJugadores.actualizarEstadisticas(jugador.getDni(), false, true, false, false);
        else if (tipo.equalsIgnoreCase("roja"))
            gestionJugadores.actualizarEstadisticas(jugador.getDni(), false, false, true, false);

        guardarJSON();
    }

    public void registrarLesion(String fechaPartido, Jugador jugador) throws ElementoInexistenteEx {
        Partido partido = buscarPorFecha(fechaPartido);
        if (partido == null) {
            throw new ElementoInexistenteEx("No se encontró el partido.");
        }
        partido.getFichaDelPartido().registrarLesion(jugador);
        gestionJugadores.actualizarEstadisticas(jugador.getDni(), false, false, false, true);
        guardarJSON();
    }

    public Partido buscarPorFecha(String fecha) {
        return partidos.stream().filter(p -> p.getFecha().equals(fecha)).findFirst().orElse(null);
    }

    public String resumenResultados() {
        long ganados = partidos.stream().filter(Partido::gano).count();
        long empatados = partidos.stream().filter(Partido::empato).count();
        long perdidos = partidos.stream().filter(Partido::perdio).count();
        return "Resumen: " + ganados + " ganados, " + empatados + " empatados, " + perdidos + " perdidos";
    }
}
