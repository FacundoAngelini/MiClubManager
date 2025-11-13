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

    public MenuInventario(MenuClub menuClub) {
        this.menuClub = menuClub;
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

            int opcion = leerEntero();
            if (opcion == -1) continue;

            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> eliminarProducto();
                case 3 -> consultarStock();
                case 4 -> mostrarInventario();
                case 5 -> listarProductos();
                case 6 -> modificarPelota();
                case 7 -> modificarCamiseta();
                case 8 -> salir = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void agregarProducto() {
        try {
            String tipo = leerStringNoVacio("Tipo de producto (pelota/camiseta): ").toLowerCase();

            if (!tipo.equals("pelota") && !tipo.equals("camiseta")) {
                System.out.println("Tipo de producto no válido");
                return;
            }

            String nombre = leerStringNoVacio("Nombre: ");
            String marca = leerStringNoVacio("Marca: ");
            int cantidad = leerEnteroPositivo("Cantidad: ");

            String extra = "";
            if (tipo.equals("pelota")) {
                extra = leerStringNoVacio("Modelo de la pelota: ");
            } else if (tipo.equals("camiseta")) {
                extra = leerStringNoVacio("Sponsor de la camiseta: ");
            }

            menuClub.club.getInventario().agregarProducto(tipo, nombre, marca, cantidad, extra);
            menuClub.club.getInventario().guardarJSON();
            System.out.println("Producto agregado correctamente");
        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        try {
            String nombre = leerStringNoVacio("Nombre del producto a eliminar: ");
            menuClub.club.getInventario().eliminarElemento(nombre);
            menuClub.club.getInventario().guardarJSON();
            System.out.println("Producto eliminado correctamente");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void consultarStock() {
        try {
            String nombre = leerStringNoVacio("Nombre del producto: ");
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
            String nombre = leerStringNoVacio("Nombre de la pelota a modificar: ");
            Producto prod = menuClub.club.getInventario().devuelveElemento(nombre);

            if (prod instanceof Pelota pelota) {
                String nuevoModelo = leerStringNoVacio("Nuevo modelo: ");
                pelota.setModelo(nuevoModelo);
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
            String nombre = leerStringNoVacio("Nombre de la camiseta a modificar: ");
            Producto prod = menuClub.club.getInventario().devuelveElemento(nombre);

            if (prod instanceof Camiseta camiseta) {
                String nuevoSponsor = leerStringNoVacio("Nuevo sponsor: ");
                camiseta.cambiarSponsor(nuevoSponsor);
                menuClub.club.getInventario().guardarJSON();
                System.out.println("Sponsor actualizado");
            } else {
                System.out.println("El producto no es una camiseta");
            }
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido");
            return -1;
        }
    }

    private int leerEnteroPositivo(String mensaje) {
        int numero;
        do {
            numero = leerEnteroConMensaje(mensaje);
            if (numero <= 0) {
                System.out.println("Debe ser un número mayor que 0");
            }
        } while (numero <= 0);
        return numero;
    }

    private int leerEnteroConMensaje(String mensaje) {
        System.out.print(mensaje);
        return leerEntero();
    }

    private String leerStringNoVacio(String mensaje) {
        String input;
        do {
            System.out.print(mensaje);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("No puede estar vacío");
            }
        } while (input.isEmpty());
        return input;
    }
}
