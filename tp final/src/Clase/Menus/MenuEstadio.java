package Clase.Menus;

import java.util.Scanner;

import exeptions.AccionImposible;
import exeptions.IngresoInvalido;

public class MenuEstadio {
   private MenuClub menuClub;
    private final Scanner scanner;

    public MenuEstadio() {
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

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero valido");
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarEstadio();
                    break;
                case 2:
                    cambiarNombre();
                    break;
                case 3:
                    modificarCapacidad();
                    break;
                case 4:
                    actualizarCosto();
                    break;
                case 5:
                    pagarMantenimiento();
                    break;
                case 6:
                    mostrarDatos();
                    break;
                case 7:
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }
    }

    private void agregarEstadio() {
        try {
            System.out.print("Nombre del estadio: ");
            String nombre = scanner.nextLine();
            System.out.print("Capacidad: ");
            int capacidad = Integer.parseInt(scanner.nextLine());
            System.out.print("Ubicacion: ");
            String ubicacion = scanner.nextLine();
            System.out.print("Costo de mantenimiento: ");
            double costo = Double.parseDouble(scanner.nextLine());

            menuClub.club.getGestionEstadios().agregarEstadio(nombre, capacidad, ubicacion, costo);
            menuClub.club.getGestionEstadios().guardarJSON();
            System.out.println("Clases_Manu.Estadio agregado correctamente");
        } catch (NumberFormatException e) {
            System.out.println("Error: valor numerico invalido");
        }
    }

    private void cambiarNombre() {
        try {
            System.out.print("Nuevo nombre del estadio: ");
            String nuevoNombre = scanner.nextLine();
            menuClub.club.getGestionEstadios().cambiarNombre(nuevoNombre);
            menuClub.club.getGestionEstadios().guardarJSON();
            System.out.println("Nombre cambiado correctamente");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void modificarCapacidad() {
        try {
            System.out.print("Nueva capacidad: ");
            int nuevaCapacidad = Integer.parseInt(scanner.nextLine());
            menuClub.club.getGestionEstadios().modificar_capacidad(nuevaCapacidad);
            menuClub.club.getGestionEstadios().guardarJSON();
            System.out.println("Capacidad modificada correctamente");
        } catch (NumberFormatException e) {
            System.out.println("Error: valor numerico invalido");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void actualizarCosto() {
        try {
            System.out.print("Nuevo costo de mantenimiento: ");
            int nuevoCosto = Integer.parseInt(scanner.nextLine());
            menuClub.club.getGestionEstadios().actualizar_costo_mantenimiento(nuevoCosto);
            menuClub.club.getGestionEstadios().guardarJSON();
            System.out.println("Costo de mantenimiento actualizado correctamente");
        } catch (NumberFormatException e) {
            System.out.println("Error: valor numerico invalido");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void pagarMantenimiento() {
        try {
            System.out.print("Fecha del pago (dd/mm/yyyy): ");
            String fecha = scanner.nextLine();
            menuClub.club.getGestionEstadios().pagarMantenimiento(fecha);
            menuClub.club.getGestionEstadios().guardarJSON();
            System.out.println("Mantenimiento pagado correctamente");
        } catch (AccionImposible | IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarDatos() {
        if (menuClub.club.getGestionEstadios().getEstadio() == null) {
            System.out.println("No hay estadio creado");
            return;
        }
        System.out.println("Nombre: " + menuClub.club.getGestionEstadios().getEstadio().getNombre());
        System.out.println("Capacidad: " + menuClub.club.getGestionEstadios().getEstadio().getCapacidad());
        System.out.println("Ubicacion: " + menuClub.club.getGestionEstadios().getEstadio().getUbicacion());
        System.out.println("Costo mantenimiento: " +menuClub.club.getGestionEstadios().getEstadio().getCostoMantenimiento());
    }
}
