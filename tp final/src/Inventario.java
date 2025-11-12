import exeptions.AccionImposible;
import exeptions.IngresoInvalido;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventario<T extends Producto> implements MetodosComunes<T, String>{
    private HashMap<String, T> items;

    public Inventario() {
        items = new HashMap<>();
    }

    public void agregarProducto(String tipo, String nombre, String marca, int cantidad, String... extra) throws IngresoInvalido {
        if (cantidad <= 0) {
            throw new IngresoInvalido("Cantidad debe ser mayor que 0");
        }
        Producto producto;

        switch (tipo.toLowerCase()) {
            case "pelota":
                if (extra.length < 1) {
                    throw new IngresoInvalido("Falta el modelo para la pelota");
                }
                String modelo = extra[0];
                producto = new Pelota(nombre, marca, cantidad, modelo);
                break;

            case "camiseta":
                if (extra.length < 1) {
                    throw new IngresoInvalido("Falta el sponsor para la camiseta");
                }
                String sponsor = extra[0];
                producto = new Camiseta(nombre, marca, cantidad, sponsor);
                break;

            default:
                throw new IngresoInvalido("Tipo de producto no reconocido");
        }
        items.put(nombre, (T) producto);
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