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

    private String generarClave(String nombre, String tipo, String marca) {
        return tipo.toLowerCase() + "-" + nombre.toLowerCase() + "-" + marca.toLowerCase();
    }

    public void agregarProducto(String tipo, String nombre, String marca, int cantidad, String... extra) throws IngresoInvalido {
        if (cantidad <= 0) {
            throw new IngresoInvalido("La cantidad debe ser mayor a 0.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IngresoInvalido("El nombre no puede estar vacío.");
        }
        if (marca == null || marca.isBlank()) {
            throw new IngresoInvalido("La marca no puede estar vacía.");
        }

        Producto producto;

        switch (tipo.toLowerCase()) {
            case "pelota" -> {
                if (extra.length < 1) {
                    throw new IngresoInvalido("Por favor indique el modelo de la pelota.");
                }
                producto = new Pelota(nombre, marca, cantidad, extra[0]);
            }
            case "camiseta" -> {
                if (extra.length < 1) {
                    throw new IngresoInvalido("Por favor indique el sponsor de la camiseta.");
                }
                producto = new Camiseta(nombre, marca, cantidad, extra[0]);
            }
            default -> throw new IngresoInvalido("Tipo de producto no reconocido: " + tipo);
        }

        T prod = (T) producto;
        String clave = generarClave(nombre, tipo, marca);

        if (items.containsKey(clave)) {
            T existente = items.get(clave);
            existente.setCantidad(existente.getCantidad() + cantidad);
            System.out.println("Se ha actualizado la cantidad del producto existente: " + nombre);
        } else {
            items.put(clave, prod);
            System.out.println("Producto agregado exitosamente: " + nombre);
        }

        guardarJSON();
    }

    @Override
    public void eliminarElemento(String clave) throws AccionImposible {
        if (!items.containsKey(clave)) {
            throw new AccionImposible("Producto no encontrado con la clave: " + clave);
        }
        items.remove(clave);
        System.out.println("Producto eliminado correctamente.");
        guardarJSON();
    }

    @Override
    public T devuelveElemento(String clave) throws AccionImposible {
        if (!items.containsKey(clave)) {
            throw new AccionImposible("Producto no encontrado con la clave: " + clave);
        }
        return items.get(clave);
    }

    @Override
    public boolean existe(String clave) {
        return items.containsKey(clave);
    }

    @Override
    public ArrayList<T> listar() {
        return new ArrayList<>(items.values());
    }

    public int consultarStock(String clave) throws AccionImposible {
        T producto = devuelveElemento(clave);
        return producto.getCantidad();
    }

    public void mostrarInventario() {
        if (items.isEmpty()) {
            System.out.println("Inventario vacío.");
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
