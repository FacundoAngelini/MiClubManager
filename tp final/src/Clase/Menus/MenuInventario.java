package Clase.Menus;

import Clase.Productos.Camiseta;
import Clase.Productos.Inventario;
import Clase.Productos.Pelota;
import Clase.Productos.Producto;
import exeptions.AccionImposible;
import exeptions.IngresoInvalido;

import java.util.Scanner;

public class MenuInventario<T extends Producto> {
    private MenuClub menuClub;
    private final Scanner sc;

    public MenuInventario() {
        sc = new Scanner(System.in);
    }

    public void mostrarMenuInventario() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENU INVENTARIO ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Consultar stock");
            System.out.println("4. Mostrar inventario");
            System.out.println("5. Listar productos");
            System.out.println("6. Modificar Clases_Manu.Pelota");
            System.out.println("7. Modificar Clases_Manu.Camiseta");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion;
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opcion invalida");
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    eliminarProducto();
                    break;
                case 3:
                    consultarStock();
                    break;
                case 4:
                    mostrarInventario();
                    break;
                case 5:
                    listarProductos();
                break;
                case 6:
                    modificarPelota();
                break;
                case 7:
                    modificarCamiseta();
                    break;
                case 8:
                    salir = true; System.out.println("Saliendo del menu..."); break;
                default: System.out.println("Opcion invalida");
            }
        }
    }

    private void agregarProducto() {
        try {
            System.out.print("Tipo de producto (pelota/camiseta): ");
            String tipo = sc.nextLine().trim();

            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();

            System.out.print("Marca: ");
            String marca = sc.nextLine().trim();

            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(sc.nextLine().trim());

            String extra = "";
            if (tipo.equalsIgnoreCase("pelota")) {
                System.out.print("Modelo de la pelota: ");
                extra = sc.nextLine().trim();
            } else if (tipo.equalsIgnoreCase("camiseta")) {
                System.out.print("Sponsor de la camiseta: ");
                extra = sc.nextLine().trim();
            }

            menuClub.club.getInventario()
            menuclub.inventario.agregarProducto(tipo, nombre, marca, cantidad, extra);
            inventario.guardarJSON();
            System.out.println("Clases_Manu.Producto agregado correctamente");

        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Cantidad invalida");
        }
    }

    private void eliminarProducto() {
        try {
            System.out.print("Nombre del producto a eliminar: ");
            String nombre = sc.nextLine().trim();
            inventario.eliminarElemento(nombre);
            inventario.guardarJSON();
            System.out.println("Clases_Manu.Producto eliminado correctamente");

        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void consultarStock() {
        try {
            System.out.print("Nombre del producto: ");
            String nombre = sc.nextLine().trim();
            int cantidad = inventario.consultarStock(nombre);
            System.out.println("Stock actual de " + nombre + ": " + cantidad);
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarInventario() {
        inventario.mostrarInventario();
    }

    private void listarProductos() {
        System.out.println("Lista de productos:");
        for (T producto : inventario.listar()) {
            System.out.println(producto.muestraDatos());
        }
    }

    private void modificarPelota() {
        try {
            System.out.print("Nombre de la pelota a modificar: ");
            String nombre = sc.nextLine().trim();
            T prod = inventario.devuelveElemento(nombre);

            if (prod instanceof Pelota pelota) {
                System.out.print("Nuevo modelo: ");
                String modelo = sc.nextLine().trim();
                pelota.setModelo(modelo);
                inventario.guardarJSON();
                System.out.println("Modelo de la pelota actualizado");
            } else {
                System.out.println("El producto no es una pelota");
            }
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void modificarCamiseta() {
        try {
            System.out.print("Nombre de la camiseta a modificar: ");
            String nombre = sc.nextLine().trim();
            T prod = inventario.devuelveElemento(nombre);

            if (prod instanceof Camiseta camiseta) {
                System.out.print("Nuevo sponsor: ");
                String sponsor = sc.nextLine().trim();
                try {
                    camiseta.cambiarSponsor(sponsor);
                } catch (AccionImposible e) {
                    System.out.println("Error: " + e.getMessage());
                    return;
                }
                inventario.guardarJSON();
                System.out.println("Sponsor de la camiseta actualizado");
            } else {
                System.out.println("El producto no es una camiseta");
            }
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
