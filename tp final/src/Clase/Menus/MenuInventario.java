package Clase.Menus;

import Clase.Productos.Camiseta;
import Clase.Productos.Pelota;
import Clase.Productos.Producto;
import exeptions.AccionImposible;
import exeptions.IngresoInvalido;

import java.util.Scanner;

public class MenuInventario {

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
            System.out.println("6. Modificar pelota");
            System.out.println("7. Modificar camiseta");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida");
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarProducto();
                break;
                case 2:
                    eliminarProducto();
                    break;
                case 3 :
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
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
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

            // Delegamos la creación al inventario (no rompemos encapsulamiento)
            menuClub.club.getInventario().agregarProducto(tipo, nombre, marca, cantidad, extra);
            menuClub.club.getInventario().guardarJSON();
            System.out.println("Producto agregado correctamente");
        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Cantidad inválida");
        }
    }

    private void eliminarProducto() {
        try {
            System.out.print("Nombre del producto a eliminar: ");
            String nombre = sc.nextLine().trim();
            menuClub.club.getInventario().eliminarElemento(nombre);
            menuClub.club.getInventario().guardarJSON();
            System.out.println("Producto eliminado correctamente");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void consultarStock() {
        try {
            System.out.print("Nombre del producto: ");
            String nombre = sc.nextLine().trim();
            int cantidad = menuClub.club.getInventario().consultarStock(nombre);
            System.out.println("Stock actual de " + nombre + ": " + cantidad);
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarInventario() {
        menuClub.club.getInventario().mostrarInventario();
    }

    private void listarProductos() {
        System.out.println("Lista de productos:");
        for (Producto producto : menuClub.club.getInventario().listar()) {
            System.out.println(producto.muestraDatos());
        }
    }

    private void modificarPelota() {
        try {
            System.out.print("Nombre de la pelota a modificar: ");
            Producto prod = menuClub.club.getInventario().devuelveElemento(sc.nextLine().trim());

            if (prod instanceof Pelota pelota) {
                System.out.print("Nuevo modelo: ");
                pelota.setModelo(sc.nextLine().trim());
                menuClub.club.getInventario().guardarJSON();
                System.out.println("Modelo actualizado");
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
            Producto prod = menuClub.club.getInventario().devuelveElemento(sc.nextLine().trim());

            if (prod instanceof Camiseta camiseta) {
                System.out.print("Nuevo sponsor: ");
                camiseta.cambiarSponsor(sc.nextLine().trim());
                menuClub.club.getInventario().guardarJSON();
                System.out.println("Sponsor actualizado");
            } else {
                System.out.println("El producto no es una camiseta");
            }
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
