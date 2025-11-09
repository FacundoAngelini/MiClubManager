import org.json.JSONArray;

import java.util.ArrayList;

public class Inventario<T extends Producto> implements MetodosComunes<T>{
    private ArrayList<T> items;
    public Inventario() {
        items = new ArrayList<T>();
    }


    @Override
    public void agregarElemento(T item) throws AccionImposible{
        if(item == null){
            throw new AccionImposible("No se puede agregar un item vacio");
        }
        if(item.getCantidad()<=0)
        {
            throw new AccionImposible("No se puede agregar un item con cantidad 0");
        }
        items.add(item);
        System.out.println("Se agrego correctamente");
    }

    @Override
    public void eliminarElemento(T item)throws AccionImposible {
        if(item == null || !items.contains(item)){
            throw new AccionImposible("Producto no encontrado");
        }
        items.remove(item);
        System.out.println("Se removio el item");
    }

    @Override
    public void modificarElemento(T item) throws AccionImposible {
        if(item == null || !items.contains(item)) throw new AccionImposible("Producto no encontrado");
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).equals(item)){
                items.set(i, item);
                System.out.println("Producto modificado correctamente");
                return;
            }
        }
    }

    @Override
    public boolean existe(T item) {
        return items.contains(item);
    }

    @Override
    public ArrayList<T> listar() {
        return items;
    }

    public int consultarStock(T item) throws AccionImposible {
        if(item == null || !items.contains(item)) throw new AccionImposible("Producto no encontrado");

        int cantidad = 0;
        for(T i : items){
            if(i.equals(item)){
                cantidad += i.getCantidad();
            }
        }
        return cantidad;
    }

    public void mostrarInventario() {
        if(items.isEmpty()) {
            System.out.println("El inventario está vacío");
        } else {
            for(T item : items){
                System.out.println(item.muestraDatos());
            }
        }
    }

    @Override
    public void guardarJSON() {
        JSONArray array = new JSONArray();
        for (T item : items) {
            array.put(item.toJSON());
        }
        JSONUtiles.uploadJSON(array, "inventario");
    }
}