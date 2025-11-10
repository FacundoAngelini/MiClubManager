import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventario<T extends Producto> implements MetodosComunes<T, String>{
    private HashMap<String, T> items;

    public Inventario() {
        items = new HashMap<>();
    }

    public void agregarElemento(T item) throws IngresoInvalido {
        if(item == null) {
            throw new IngresoInvalido("No se puede agregar un item nulo");
        }
        if(item.getCantidad() <= 0) {
            throw new IngresoInvalido("Cantidad debe ser mayor que 0");
        }
        items.put(item.getNombre(), item);
    }

    public void eliminarElemento(String id) throws AccionImposible {
        if(!items.containsKey(id)) {
            throw new AccionImposible("Producto no encontrado");
        }
        items.remove(id);
    }
    public T devuelveElemento(String key) throws AccionImposible {
        if(!items.containsKey(key)) {
            throw new AccionImposible("Producto no encontrado");
        }
        return items.get(key);
    }

    public boolean existe(String id) {
        return items.containsKey(id);
    }

    public ArrayList<T> listar() {
        return new ArrayList<>(items.values());
    }

    public int consultarStock(String id) throws AccionImposible {
        if(!items.containsKey(id)) {
            throw new AccionImposible("Producto no encontrado");
        }
        return items.get(id).getCantidad();
    }


    public void mostrarInventario() {
        if(items.isEmpty()) {
            System.out.println("Inventario vacío");
            return;
        }
        for(T item : items.values()){
            System.out.println(item.muestraDatos());
        }
    }

    @Override
    public void guardarJSON() {
        JSONArray array = new JSONArray();
        for (T item : items.values()) {
            array.put(item.toJSON());
        }
        JSONUtiles.uploadJSON(array, "inventario");
    }

}