package Clase.Productos;

import Clase.Json.JSONUtiles;
import interfaz.MetodosComunes;
import exeptions.AccionImposible;
import exeptions.IngresoInvalido;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventario<T extends Producto> implements MetodosComunes<T, String> {

    private final HashMap<String, T> items;

    public Inventario() {
        items = new HashMap<>();
        cargarJSON();
    }

    private String generarClave(String nombre, String tipo, String marca) {
        return tipo.toLowerCase() + nombre.toLowerCase() +  marca.toLowerCase();
    }

    public void agregarProducto(String tipo, String nombre, String marca, int cantidad, String... extra) throws IngresoInvalido {
        if (cantidad <= 0) {
            throw new IngresoInvalido("La cantidad debe ser mayor a 0");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IngresoInvalido("El nombre no puede estar vacio");
        }
        if (marca == null || marca.isBlank()) {
            throw new IngresoInvalido("La marca no puede estar vacia");
        }

        Producto producto;

        switch (tipo.toLowerCase()) {
            case "pelota" -> {
                if (extra.length < 1) {
                    throw new IngresoInvalido("indique el modelo de la pelota");
                }
                producto = new Pelota(nombre, marca, cantidad, extra[0]);
            }
            case "camiseta" -> {
                if (extra.length < 1) {
                    throw new IngresoInvalido("indique el sponsor de la camiseta");
                }
                producto = new Camiseta(nombre, marca, cantidad, extra[0]);
            }
            default -> throw new IngresoInvalido("tipo de producto no reconocido: " + tipo);
        }

        T prod = (T) producto;
        String clave = generarClave(nombre, tipo, marca);

        if (items.containsKey(clave)) {
            T existente = items.get(clave);
            existente.setCantidad(existente.getCantidad() + cantidad);
            System.out.println("Se ha actualizado la cantidad del producto existente: " + nombre);
        } else {
            items.put(clave, prod);
        }

        guardarJSON();
    }
    public boolean existeProducto(String clave) {
        return items.containsKey(clave);
    }

    public void cargarJSON() {
        try {
            JSONArray array = JSONUtiles.downloadJSONArray("inventario");
            if (array == null) return;

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String tipo = obj.getString("tipo");
                String nombre = obj.getString("nombre");
                String marca = obj.getString("marca");
                int cantidad = obj.getInt("cantidad");

                Producto producto;

                if (tipo.equalsIgnoreCase("pelota")) {
                    String modelo = obj.getString("modelo");
                    producto = new Pelota(nombre, marca, cantidad, modelo);
                } else if (tipo.equalsIgnoreCase("camiseta")) {
                    String sponsor = obj.getString("sponsor");
                    producto = new Camiseta(nombre, marca, cantidad, sponsor);
                } else {
                    continue;
                }
                String clave = generarClave(nombre, tipo, marca);

                if (items.containsKey(clave)) {
                    T existente = items.get(clave);
                    existente.setCantidad(existente.getCantidad() + cantidad);
                } else {
                    items.put(clave, (T) producto);
                }
            }
            System.out.println("Inventario cargado correctamente");
        } catch (Exception e) {
            System.out.println("Error al cargar inventario: " + e.getMessage());
        }
    }



    @Override
    public void eliminarElemento(String clave) throws AccionImposible {
        if (!items.containsKey(clave)) {
            throw new AccionImposible("Producto no encontrado con la clave: " + clave);
        }
        items.remove(clave);
        System.out.println("Producto eliminado correctamente");
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

    public void guardarJSON() {
        try {
            JSONArray array = new JSONArray();
            for (T item : items.values()) {
                array.put(item.toJSON());
            }
            JSONUtiles.uploadJSON(array, "inventario");
        } catch (Exception e) {
            System.out.println("Error al guardar inventario: " );
        }
    }



    public String buscarProducto(String tipo, String nombre, String marca) throws AccionImposible {
        if (!tipo.equalsIgnoreCase("pelota") && !tipo.equalsIgnoreCase("camiseta")) {
            throw new AccionImposible("Tipo inválido. Debe ser 'pelota' o 'camiseta'.");
        }

        String clave = generarClave(nombre, tipo, marca);

        if (!items.containsKey(clave)) {
            throw new AccionImposible("Producto no encontrado con los datos indicados.");
        }

        return items.get(clave).muestraDatos();
    }
    public boolean marcaExisteParaTipo(String tipo, String marca) {
        for (T item : items.values()) {
            if (item.getTipo().equalsIgnoreCase(tipo) && item.getMarca().equalsIgnoreCase(marca)) {
                return true;
            }
        }
        return false;
    }



    public String consultarStockPorTipoMarca(String tipo, String marca) throws AccionImposible {
        if (!tipo.equalsIgnoreCase("pelota") && !tipo.equalsIgnoreCase("camiseta")) {
            throw new AccionImposible("Tipo inválido. Debe ser 'pelota' o 'camiseta'.");
        }

        boolean encontrado = false;
        StringBuilder sb = new StringBuilder();

        for (T item : items.values()) {
            if (item.getTipo().equalsIgnoreCase(tipo) && item.getMarca().equalsIgnoreCase(marca)) {
                sb.append(item.muestraDatos()).append("\n");
                encontrado = true;
            }
        }

        if (!encontrado) {
            throw new AccionImposible("No hay productos de tipo '" + tipo + "' y marca '" + marca + "'.");
        }

        return sb.toString();
    }

}
