package Clase.Menus;

import Clase.Gestiones.GestionEstadio;
import exeptions.IngresoInvalido;

import java.util.Scanner;

public class MenuEstadio {

    private MenuClub menuClub;
    private final Scanner scanner;

    public MenuEstadio(MenuClub menuClub) {
        this.menuClub = menuClub;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuEstadio() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n----- MENU ESTADIO -----");
            System.out.println("1. Agregar estadio");
            System.out.println("2. Cambiar nombre del estadio");
            System.out.println("3. Modificar capacidad");
            System.out.println("4. Actualizar costo de mantenimiento");
            System.out.println("5. Pagar mantenimiento");
            System.out.println("6. Mostrar datos del estadio");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = leerEntero();
            if (opcion == -1) continue;

            switch (opcion) {
                case 1 -> agregarEstadio();
                case 2 -> cambiarNombre();
                case 3 -> modificarCapacidad();
                case 4 -> actualizarCosto();
                case 5 -> pagarMantenimiento();
                case 6 -> mostrarDatos();
                case 7 -> salir = true;
                default -> System.out.println("Opcion invalida");
            }
        }
    }

    private void agregarEstadio() {
        try {
            System.out.print("Nombre del estadio: ");
            String nombre = leerString();

            System.out.print("Capacidad: ");
            int capacidad = leerEnteroPositivo();

            System.out.print("Ubicacion: ");
            String ubicacion = leerString();

            System.out.print("Costo de mantenimiento: ");
            double costo = leerDoublePositivo();

            GestionEstadio ge = menuClub.club.getGestionEstadios();
            ge.agregarEstadio(nombre, capacidad, ubicacion, costo);
            ge.guardarJSON();

            System.out.println("Estadio agregado correctamente");
        } catch (Exception e) {
            System.out.println("Error al agregar estadio: " + e.getMessage());
        }
    }

    private void cambiarNombre() {
        GestionEstadio ge = menuClub.club.getGestionEstadios();
        try {
            System.out.print("Nuevo nombre del estadio: ");
            String nuevoNombre = leerString();
            ge.cambiarNombre(nuevoNombre);
        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            ge.guardarJSON();
        }
    }

    private void modificarCapacidad() {
        GestionEstadio ge = menuClub.club.getGestionEstadios();
        try {
            System.out.print("Nueva capacidad: ");
            int nuevaCapacidad = leerEnteroPositivo();
            ge.modificarCapacidad(nuevaCapacidad);
        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            ge.guardarJSON();
        }
    }

    private void actualizarCosto() {
        GestionEstadio ge = menuClub.club.getGestionEstadios();
        try {
            System.out.print("Nuevo costo de mantenimiento: ");
            double nuevoCosto = leerDoublePositivo();
            ge.actualizarCostoMantenimiento((int) nuevoCosto);
        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            ge.guardarJSON();
        }
    }

    private void pagarMantenimiento() {
        GestionEstadio ge = menuClub.club.getGestionEstadios();
        try {
            System.out.print("Fecha del pago (dd/mm/yyyy): ");
            String fecha = leerString();
            ge.pagarMantenimiento(fecha);
            System.out.println("Mantenimiento pagado correctamente");
        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            ge.guardarJSON();
        }
    }

    private void mostrarDatos() {
        GestionEstadio ge = menuClub.club.getGestionEstadios();
        if (ge.getEstadio() == null) {
            System.out.println("No hay estadio creado");
            return;
        }
        System.out.println("Nombre: " + ge.getEstadio().getNombre());
        System.out.println("Capacidad: " + ge.getEstadio().getCapacidad());
        System.out.println("Ubicacion: " + ge.getEstadio().getUbicacion());
        System.out.println("Costo mantenimiento: " + ge.getEstadio().getCostoMantenimiento());
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: ingrese un numero valido");
            return -1;
        }
    }

    private int leerEnteroPositivo() {
        int valor;
        while (true) {
            valor = leerEntero();
            if (valor > 0) return valor;
            System.out.println("El valor debe ser mayor que 0");
        }
    }

    private double leerDoublePositivo() {
        while (true) {
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim());
                if (valor > 0) return valor;
                System.out.println("El valor debe ser mayor que 0");
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero valido");
            }
        }
    }

    private String leerString() {
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (!input.isBlank()) return input;
            System.out.println("El valor no puede estar vacío");
        }
    }
}
