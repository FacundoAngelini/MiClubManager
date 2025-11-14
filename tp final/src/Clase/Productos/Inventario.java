package Clase.Productos;
import Clase.Json.JSONUtiles;
import interfaz.MetodosComunes;
import exeptions.AccionImposible;
import exeptions.IngresoInvalido;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;

public class Inventario<T extends Producto> implements MetodosComunes<T, String> {

    private final HashMap<String, T> items;

    public Inventario() {
        items = new HashMap<>();
    }

    public void agregarProducto(String tipo, String nombre, String marca, int cantidad, String... extra) throws IngresoInvalido {
        if (cantidad <= 0) {
            throw new IngresoInvalido("La cantidad debe ser mayor que 0");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IngresoInvalido("El nombre no puede estar vacío");
        }

        if (marca == null || marca.isBlank()) {
            throw new IngresoInvalido("La marca no puede estar vacía");
        }

        Producto producto;

        switch (tipo.toLowerCase()) {
            case "pelota" -> {
                if (extra.length < 1) throw new IngresoInvalido("Falta el modelo para la pelota");
                producto = new Pelota(nombre, marca, cantidad, extra[0]);
            }
            case "camiseta" -> {
                if (extra.length < 1) throw new IngresoInvalido("Falta el sponsor para la camiseta");
                producto = new Camiseta(nombre, marca, cantidad, extra[0]);
            }
            default -> throw new IngresoInvalido("Tipo de producto no reconocido: " + tipo);
        }

        T prod = (T) producto;

        if (items.containsKey(nombre)) {
            T existente = items.get(nombre);
            existente.setCantidad(existente.getCantidad() + cantidad);
        } else {
            items.put(nombre, prod);
        }

        guardarJSON();
    }

    public void eliminarElemento(String id) throws AccionImposible {
        if (!items.containsKey(id)) throw new AccionImposible("Producto no encontrado: " + id);
        items.remove(id);
        guardarJSON();
    }

    public T devuelveElemento(String id) throws AccionImposible {
        if (!items.containsKey(id)) throw new AccionImposible("Producto no encontrado: " + id);
        return items.get(id);
    }

    public boolean existe(String id) {
        return items.containsKey(id);
    }

    public ArrayList<T> listar() {
        return new ArrayList<>(items.values());
    }

    public int consultarStock(String id) throws AccionImposible {
        T producto = devuelveElemento(id);
        return producto.getCantidad();
    }

    public void mostrarInventario() {
        if (items.isEmpty()) {
            System.out.println("Inventario vacío");
            return;
        }

        for (T item : items.values()) {
            System.out.println(item.muestraDatos());
        }
    }


    public void guardarJSON() {
        try {
            JSONArray array = new JSONArray();
            for (T item : items.values()) {
                array.put(item.toJSON());
            }
            JSONUtiles.uploadJSON(array, "inventario");
        } catch (Exception e) {
            System.out.println("Error al guardar inventario: " + e.getMessage());
        }
    }
}
