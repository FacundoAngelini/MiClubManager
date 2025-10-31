import java.util.HashMap;

public class Inventario {
    private HashMap<String, Integer> items;
    public Inventario() {
        items = new HashMap<>();
    }

    public void addItem(String item, int cantidad) throws AccionImposible {
        try{
            if(cantidad<=0)
            {
                throw new AccionImposible("La cantidad debe ser mayor a 0");

            }
            items.put(item, cantidad);
            System.out.println("Se agrego correctamente");

        }
        catch (AccionImposible ex)
        {
            System.out.println("No se pudo agregar");
        }
    }

    public void removerItems (String nombre, int cantidad) throws AccionImposible
    {
        try {
            if (!items.containsKey(nombre))
            {
                throw new AccionImposible("La cantidad debe ser mayor a 0");
            }
            int StockActual = items.get(nombre);
            if (StockActual >= cantidad) // en caso de que la cantidad ingresada sea mayor que el StockActual, se elimina por completo ese item
            {
                items.remove(nombre);
            } else
            {
                items.put(nombre, StockActual - cantidad); // en caso de que haya mas stock que la cantidad ingresada, se hace la resta para para actualizar el stock
            }
        } catch (AccionImposible ex)
        {
            System.out.println("No se pudo agregar");
        }

    }

    public int consultarStock(String nombre) throws AccionImposible
    {
        try{
            if(!items.containsKey(nombre))
            {
                throw new AccionImposible("No se encuentra ningun item con ese elemento");
            }

        }
        catch (AccionImposible ex)
        {
            System.out.println("No se pudo buscar");
        }
        return items.get(nombre);
    }

    public void mostrarInventario()
    {
        if(items.isEmpty())
        {
            System.out.println("El inventario esta vacio");
        }
        else
        {
           for(String nombre : items.keySet())
           {
               int cantidad = items.get(nombre);
               System.out.println("Item: " +nombre + "  Cantidad: " + cantidad);
           }
        }
    }
}
