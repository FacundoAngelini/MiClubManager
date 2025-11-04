import java.util.ArrayList;
import java.util.HashMap;

public class Inventario<T extends Producto> {
    private ArrayList<T> items;
    public Inventario() {
        items = new ArrayList<T>();
    }

    public void addItem(T item) throws AccionImposible
    {
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

    public void removerItems (T item) throws AccionImposible
    {
        if(item == null || !items.contains(item)){
            throw new AccionImposible("Producto no encontrado");
        }
        items.remove(item);
        System.out.println("Se removio el item");
    }

    public int consultarStock(T item) throws AccionImposible
    {
        if(item == null || !items.contains(item)){
            throw new AccionImposible("Producto no encontrado");
        }

        int cantidad =0;

        for(T item1 : items){
            if(item1.equals(item)){
                cantidad++;
            }
        }
        return cantidad;
    }

    public void mostrarInventario()
    {
        if(items.isEmpty())
        {
            System.out.println("El inventario esta vacio");
        }
        else
        {
           for(T item : items)
           {
               System.out.println(item.muestraDatos());
           }
        }
    }
}
