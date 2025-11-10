import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

 public class GestorPartido implements MetodosComunes<Partido, String> {

    private final ArrayList<Partido> partidos;

    public GestorPartido() {
        this.partidos = new ArrayList<>();
    }

    public void agregarPartido(String fecha, int entradasVendidas, Estadio estadio,
                               FichaDelPartido fichaDelPartido, ValorEntradas valorEntrada,
                               int entradasDadasSocio)
            throws IngresoInvalido, ElementoDuplicadoEx {

        if (fecha == null || fecha.isEmpty()) {
            throw new IngresoInvalido("La fecha del partido no puede estar vacía.");
        }

        boolean existe = partidos.stream()
                .anyMatch(p -> p.getFecha().equals(fecha));

        if (existe) {
            throw new ElementoDuplicadoEx("Ya existe un partido registrado para la fecha " + fecha);
        }

        Partido nuevoPartido = new Partido(fecha, entradasVendidas, estadio, fichaDelPartido, valorEntrada, entradasDadasSocio);
        partidos.add(nuevoPartido);
    }


     public void eliminarElemento(String fecha) throws AccionImposible {
         Partido partidoAEliminar = partidos.stream()
                 .filter(p -> p.getFecha().equals(fecha))
                 .findFirst()
                 .orElse(null);

         if (partidoAEliminar == null) {
             throw new AccionImposible("No se encontró un partido con la fecha " + fecha);
         }

         partidos.remove(partidoAEliminar);
     }

     public Partido devuelveElemento(String fecha) throws AccionImposible {
         return partidos.stream()
                 .filter(p -> p.getFecha().equals(fecha))
                 .findFirst()
                 .orElseThrow(() -> new AccionImposible("No existe un partido con la fecha " + fecha));
     }


     public boolean existe(String fecha) throws ElementoInexistenteEx {
         boolean existe = partidos.stream().anyMatch(p -> p.getFecha().equals(fecha));
         if (!existe) {
             throw new ElementoInexistenteEx("No se encontró un partido con la fecha " + fecha);
         }
         return true;
     }


     public ArrayList<Partido> listar() {
         return new ArrayList<>(partidos);
     }


     public void guardarJSON() {
         JSONArray array = new JSONArray();

         for (Partido p : partidos) {
             JSONObject obj = new JSONObject();
             obj.put("fecha", p.getFecha());
             obj.put("entradasVendidas", p.getEntradasVendidas());
             obj.put("entradasDadasSocio", p.getEntradasDadasSocio());
             obj.put("valorEntrada", (p.getValorEntrada() != null) ? p.getValorEntrada().getPrecio() : 0);
             obj.put("estadio", (p.getEstadio() != null) ? p.getEstadio().getNombre() : "Sin estadio");

             FichaDelPartido ficha = p.getFichaDelPartido();
             if (ficha != null) {
                 JSONObject fichaObj = new JSONObject();
                 fichaObj.put("golesLocal", ficha.getGolesLocal());
                 fichaObj.put("golesVisitante", ficha.getGolesVisitante());


                 JSONArray golesArray = new JSONArray();
                 for (Gol g : ficha.getGoles()) {
                     JSONObject golObj = new JSONObject();
                     golObj.put("minuto", g.getMinuto());
                     golObj.put("jugador", g.getJugador().getNombre());
                     golesArray.put(golObj);
                 }
                 fichaObj.put("goles", golesArray);

                 JSONArray tarjetasArray = new JSONArray();
                 for (TarjetaAplicada t : ficha.getTarjetas()) {
                     JSONObject tarjetaObj = new JSONObject();
                     tarjetaObj.put("jugador", t.getJugador().getNombre());
                     tarjetaObj.put("tipo", t.getTipoTarjeta());
                     tarjetasArray.put(tarjetaObj);
                 }
                 fichaObj.put("tarjetas", tarjetasArray);

                 JSONArray lesionadosArray = new JSONArray();
                 for (Jugador j : ficha.getLesionados()) {
                     lesionadosArray.put(j.getNombre());
                 }
                 fichaObj.put("lesionados", lesionadosArray);

                 obj.put("fichaDelPartido", fichaObj);
             }

             array.put(obj);
         }

         JSONUtiles.uploadJSON(array, "partidos");
     }

     public Partido buscarPorFecha(String fecha) {
         return partidos.stream()
                 .filter(p -> p.getFecha().equals(fecha))
                 .findFirst()
                 .orElse(null);
     }

     public double calcularRecaudacionTotal() {
         return partidos.stream()
                 .mapToDouble(Partido::obtenerGanancia)
                 .sum();
     }
 }