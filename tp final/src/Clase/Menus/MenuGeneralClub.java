package Clase.Menus;

import exeptions.FondoInsuficienteEx;
import exeptions.IngresoInvalido;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import exeptions.AccionImposible;


public class MenuGeneralClub {

    private MenuClub menuClub;
    private final Scanner scanner;

    public MenuGeneralClub(MenuClub menuClub) {
        this.menuClub = menuClub;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuGeneral() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n----- Menu General Club -----");
            System.out.println("1. Agregar estadio");
            System.out.println("2. Modificar capacidad del estadio");
            System.out.println("3. Cambiar nombre del estadio");
            System.out.println("4. Validar existencia del estadio");
            System.out.println("5. ");
            System.out.println("6. Eliminar jugador");
            System.out.println("7. Mostrar jugador");
            System.out.println("8. Acutualizar estadisticas ");
            System.out.println("9. Listar jugadores");
            System.out.println("10. Agregar partido");
            System.out.println("11. Eliminar partido");
            System.out.println("12. Buscar partido existente");
            System.out.println("13. Mostrar partidos");
            System.out.println("14. Salir del menu Plantel");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero valido.");
                continue;
            }

            switch (opcion) {
                case 1 -> agregarEstadio();
                case 2 -> modificarCapacidad();
                case 3 -> cambiarNombre();
                case 4 -> validarEstadioExistente();
                case 5 ->
                case 6 ->
                case 7 ->
                case 8 ->
                case 9 -> listarJugadoresMenu();
                case 10 -> agregarPartido();
                case 11 -> eliminarPartido();
                case 12 -> existePartido();
                case 13 -> listarPartidos();
                case 14 -> salir = true;
                default -> System.out.println("Opcion invalida.");
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
        } catch (NumberFormatException e) {
            System.out.println("Error: capacidad o costo invalido");
        }
    }

    private void modificarCapacidad() {
        try {
            System.out.print("Nueva capacidad: ");
            int nuevaCapacidad = Integer.parseInt(scanner.nextLine());
            menuClub.club.getGestionEstadios().modificarCapacidad(nuevaCapacidad);
        } catch (NumberFormatException e) {
            System.out.println("Error: capacidad invalida");
        }
    }

    private void cambiarNombre() {
        System.out.print("Nuevo nombre del estadio: ");
        String nuevoNombre = scanner.nextLine();
        menuClub.club.getGestionEstadios().cambiarNombre(nuevoNombre);
    }

    private void validarEstadioExistente() {
        try {
            menuClub.club.getGestionEstadios().validarEstadioExistente();
            System.out.println("Estadio existe y esta listo para operar");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}



}