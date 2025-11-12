import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class GestionTransaccion implements MetodosComunes<Transaccion, String> {

    private ArrayList<Transaccion> transacciones;
    private double saldoActual;

    public GestionTransaccion(double saldoInicial) {
        this.transacciones = new ArrayList<>();
        this.saldoActual = saldoInicial;
    }

    public void agregarTransaccion(String descripcion, double monto, String tipo, String fecha)
            throws IngresoInvalido, AccionImposible {

        if (descripcion == null || descripcion.isEmpty()) {
            throw new IngresoInvalido("La descripción no puede estar vacía.");
        }
        if (monto <= 0) {
            throw new IngresoInvalido("El monto debe ser mayor a cero.");
        }
        if (!tipo.equalsIgnoreCase("ingreso") && !tipo.equalsIgnoreCase("egreso")) {
            throw new IngresoInvalido("El tipo de transacción debe ser 'ingreso' o 'egreso'.");
        }

        if (tipo.equalsIgnoreCase("egreso") && monto > saldoActual) {
            throw new AccionImposible("Fondos insuficientes. Saldo actual: $" + saldoActual);
        }

        Transaccion nueva = new Transaccion(descripcion, monto, tipo.toLowerCase(), fecha);
        transacciones.add(nueva);

        if (tipo.equalsIgnoreCase("ingreso")) {
            saldoActual += monto;
        } else {
            saldoActual -= monto;
        }
    }

    public void eliminarElemento(String descripcion) throws AccionImposible {
        Transaccion t = transacciones.stream()
                .filter(tr -> tr.getDescripcion().equalsIgnoreCase(descripcion))
                .findFirst()
                .orElseThrow(() -> new AccionImposible("No se encontró la transacción: " + descripcion));

        // Revertir el efecto de la transacción eliminada
        if (t.getTipo().equalsIgnoreCase("ingreso")) {
            saldoActual -= t.getMonto();
        } else {
            saldoActual += t.getMonto();
        }

        transacciones.remove(t);
    }

    // ✅ Buscar por descripción
    @Override
    public Transaccion devuelveElemento(String descripcion) throws AccionImposible {
        return transacciones.stream()
                .filter(t -> t.getDescripcion().equalsIgnoreCase(descripcion))
                .findFirst()
                .orElseThrow(() -> new AccionImposible("No existe una transacción con esa descripción."));
    }

    // ✅ Verifica existencia
    @Override
    public boolean existe(String descripcion) throws ElementoInexistenteEx {
        boolean existe = transacciones.stream()
                .anyMatch(t -> t.getDescripcion().equalsIgnoreCase(descripcion));
        if (!existe) {
            throw new ElementoInexistenteEx("No se encontró la transacción: " + descripcion);
        }
        return true;
    }

    // ✅ Listar todas
    @Override
    public ArrayList<Transaccion> listar() {
        return new ArrayList<>(transacciones);
    }

    // ✅ Guardar en JSON
    @Override
    public void guardarJSON() {
        JSONArray array = new JSONArray();

        for (Transaccion t : transacciones) {
            JSONObject obj = new JSONObject();
            obj.put("descripcion", t.getDescripcion());
            obj.put("monto", t.getMonto());
            obj.put("tipo", t.getTipo());
            obj.put("fecha", t.getFecha());
            array.put(obj);
        }

        JSONUtiles.uploadJSON(array, "transacciones");
    }

    // ✅ Getters
    public double getSaldoActual() {
        return saldoActual;
    }

    public double calcularTotalIngresos() {
        return transacciones.stream()
                .filter(t -> t.getTipo().equalsIgnoreCase("ingreso"))
                .mapToDouble(Transaccion::getMonto)
                .sum();
    }

    public double calcularTotalEgresos() {
        return transacciones.stream()
                .filter(t -> t.getTipo().equalsIgnoreCase("egreso"))
                .mapToDouble(Transaccion::getMonto)
                .sum();
    }

}