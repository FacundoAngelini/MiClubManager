import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class GestorPartido implements MetodosComunes<Partido, String> {

    private final ArrayList<Partido> partidos;

    public GestorPartido() {
        this.partidos = new ArrayList<>();
    }

    public void agregarPartido(String fecha, boolean esLocal, String rival, int golesAFavor, int golesEnContra, int entradasVendidas, double precioEntrada) throws IngresoInvalido, ElementoDuplicadoEx {
        if (fecha == null || fecha.isEmpty()) {
            throw new IngresoInvalido("La fecha del partido no puede estar vacía.");
        }

        boolean existe = partidos.stream()
                .anyMatch(p -> p.getFecha().equals(fecha));

        if (existe) {
            throw new ElementoDuplicadoEx("Ya existe un partido registrado en la fecha " + fecha);
        }

        Partido nuevoPartido = new Partido(fecha, esLocal, rival, golesAFavor, golesEnContra, esLocal ? entradasVendidas : 0, esLocal ? precioEntrada : 0);
        partidos.add(nuevoPartido);
    }

    @Override
    public void eliminarElemento(String fecha) throws AccionImposible {
        Partido partidoAEliminar = partidos.stream()
                .filter(p -> p.getFecha().equals(fecha)).findFirst().orElseThrow(() -> new AccionImposible("No se encontró un partido con la fecha " + fecha));
        partidos.remove(partidoAEliminar);
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

    public double calcularRecaudacionTotal() {
        return partidos.stream().filter(Partido::isEsLocal).mapToDouble(Partido::calcularRecaudacion).sum();
    }

    @Override
    public void guardarJSON() {
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

            array.put(obj);
        }

        JSONUtiles.uploadJSON(array, "partidos");
    }

    private String resultadoTexto(Partido p) {
        if (p.gano()) return "Victoria";
        if (p.empato()) return "Empate";
        return "Derrota";
    }

    public Partido buscarPorFecha(String fecha) {
        return partidos.stream().filter(p -> p.getFecha().equals(fecha)).findFirst().orElse(null);
    }

    public String resumenResultados() {
        long ganados = partidos.stream().filter(Partido::gano).count();
        long empatados = partidos.stream().filter(Partido::empato).count();
        long perdidos = partidos.stream().filter(Partido::perdio).count();

        return "resumen: " + ganados + " ganados, " + empatados + " empatados, " + perdidos + " perdidos";
    }
}
