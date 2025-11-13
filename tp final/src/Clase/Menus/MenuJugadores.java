package Clase.Menus;

import Clase.Persona.Jugador;
import enums.Posicion;
import exeptions.*;
import java.util.Scanner;
import java.util.ArrayList;

public class MenuJugadores {
    private MenuClub menuclub;
    private final Scanner scanner;

    public MenuJugadores() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuJugadores() {
        boolean salir = false;

        while (!salir) {
            System.out.println("----- MENU JUGADORES -----");
            System.out.println("1. Agregar jugador");
            System.out.println("2. Eliminar jugador");
            System.out.println("3. Listar jugadores");
            System.out.println("4. Cambiar estado de contrato");
            System.out.println("5. Comprar jugador");
            System.out.println("6. Vender jugador");
            System.out.println("7. Pagar salarios");
            System.out.println("8. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarJugador();
                    break;
                case 2:
                    eliminarJugador();
                    break;
                case 3:
                    listarJugadores();
                    break;
                case 4:
                    cambiarEstadoContrato();
                    break;
                case 5:
                    comprarJugador();
                    break;
                case 6:
                    venderJugador();
                    break;
                case 7:
                    pagarSalarios();
                    break;
                case 8:
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida, intente de nuevo");
                    break;
            }
        }
    }

    private void agregarJugador() {
        try {
            System.out.print("DNI: ");
            String dni = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("Fecha nacimiento: ");
            String fechaNacimiento = scanner.nextLine();

            System.out.print("Nacionalidad: ");
            String nacionalidad = scanner.nextLine();

            System.out.print("Numero camiseta: ");
            int numeroCamiseta = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Valor jugador: ");
            double valorJugador = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Salario: ");
            double salario = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Fecha inicio contrato: ");
            String fechaInicio = scanner.nextLine();

            System.out.print("Fecha fin contrato: ");
            String fechaFin = scanner.nextLine();

            System.out.print("Duracion meses: ");
            int mesesDuracion = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Posicion (PORTERO, DEFENSOR, MEDIO, DELANTERO): ");
            Posicion posicion = Posicion.valueOf(scanner.nextLine().toUpperCase());

            menuclub.club.getGestionJugadores().agregarJugador(dni, nombre, apellido, fechaNacimiento, nacionalidad, numeroCamiseta,
                    valorJugador, salario, fechaInicio, fechaFin, mesesDuracion, posicion);
            menuclub.club.getGestionJugadores().guardarJSON();
            System.out.println("Clases_Manu.Jugador agregado correctamente.");

        } catch (ElementoDuplicadoEx | IllegalArgumentException e) {
            System.out.println("Error al agregar jugador: " + e.getMessage());
        }
    }

    private void eliminarJugador() {
        try {
            System.out.print("Ingrese DNI del jugador a eliminar: ");
            String dni = scanner.nextLine();
            menuclub.club.getGestionJugadores().eliminarElemento(dni);
            menuclub.club.getGestionJugadores().guardarJSON();
            System.out.println("Clases_Manu.Jugador eliminado correctamente.");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarJugadores() {
        ArrayList<Jugador> lista = menuclub.club.getGestionJugadores().listar();
        if (lista.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
        } else {
            for (Jugador j : lista) {
                System.out.println(j);
            }
        }
    }

    private void cambiarEstadoContrato() {
        try {
            System.out.print("Ingrese DNI del jugador: ");
            String dni = scanner.nextLine();
            System.out.print("Nuevo estado (true = activo, false = inactivo): ");
            boolean nuevoEstado = scanner.nextBoolean();
            scanner.nextLine();
            menuclub.club.getGestionJugadores().cambiarEstadoContrato(dni, nuevoEstado);
            menuclub.club.getGestionJugadores().guardarJSON();
            System.out.println("Estado de contrato actualizado.");
        } catch (ElementoInexistenteEx e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void comprarJugador() {
        try {
            System.out.print("DNI del jugador: ");
            String dni = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("Fecha nacimiento: ");
            String fechaNacimiento = scanner.nextLine();

            System.out.print("Nacionalidad: ");
            String nacionalidad = scanner.nextLine();

            System.out.print("Numero camiseta: ");
            int numeroCamiseta = Integer.parseInt(scanner.nextLine());

            System.out.print("Valor jugador: ");
            double valorJugador = Double.parseDouble(scanner.nextLine());

            System.out.print("Salario: ");
            double salario = Double.parseDouble(scanner.nextLine());

            System.out.print("Monto compra: ");
            double monto = Double.parseDouble(scanner.nextLine());

            System.out.print("Fecha inicio contrato: ");
            String fechaInicio = scanner.nextLine();

            System.out.print("Fecha fin contrato: ");
            String fechaFin = scanner.nextLine();

            System.out.print("Duracion meses: ");
            int mesesDuracion = Integer.parseInt(scanner.nextLine());

            System.out.print("Posicion (PORTERO, DEFENSOR, MEDIO, DELANTERO): ");
            Posicion posicion = Posicion.valueOf(scanner.nextLine().toUpperCase());

            menuclub.club.getGestionJugadores().comprar_jugador(
                    monto, dni, nombre, apellido, fechaNacimiento, nacionalidad,
                    numeroCamiseta, salario, fechaInicio, fechaFin, mesesDuracion,
                    valorJugador, posicion
            );

            System.out.println("Jugador comprado correctamente.");
        } catch (FondoInsuficienteEx | ElementoDuplicadoEx | IngresoInvalido | IllegalArgumentException e) {
            System.out.println("Error al comprar jugador: " + e.getMessage());
        }
    }


    private void venderJugador() {
        try {
            System.out.print("Ingrese DNI del jugador a vender: ");
            String dni = scanner.nextLine();

            System.out.print("Monto venta: ");
            double monto = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Fecha: ");
            String fecha = scanner.nextLine();

            menuclub.club.getGestionJugadores().vender_jugador(monto, dni, fecha);
            menuclub.club.getGestionJugadores().guardarJSON();
            System.out.println("Clases_Manu.Jugador vendido correctamente.");
        } catch (ElementoInexistenteEx | IngresoInvalido e) {
            System.out.println("Error al vender jugador: " + e.getMessage());
        }
    }

    private void pagarSalarios() {
        try {
            System.out.print("Ingrese fecha de pago: ");
            String fecha = scanner.nextLine();
            menuclub.club.getGestionJugadores().pagar_salarios(fecha);
            System.out.println("Salarios pagados correctamente.");
        } catch (FondoInsuficienteEx | IngresoInvalido e) {
            System.out.println("Error al pagar salarios: " + e.getMessage());
        }
    }
}
