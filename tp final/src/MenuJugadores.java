import enums.Posicion;
import exeptions.*;
import java.util.Scanner;
import java.util.ArrayList;

public class MenuJugadores {

    private final GestionJugadores gestionJugadores;
    private final Scanner scanner;

    public MenuJugadores(GestionJugadores gestionJugadores) {
        this.gestionJugadores = gestionJugadores;
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

            gestionJugadores.agregarJugador(dni, nombre, apellido, fechaNacimiento, nacionalidad, numeroCamiseta,
                    valorJugador, salario, fechaInicio, fechaFin, mesesDuracion, posicion);
            gestionJugadores.guardarJSON();
            System.out.println("Jugador agregado correctamente.");

        } catch (ElementoDuplicadoEx | IllegalArgumentException e) {
            System.out.println("Error al agregar jugador: " + e.getMessage());
        }
    }

    private void eliminarJugador() {
        try {
            System.out.print("Ingrese DNI del jugador a eliminar: ");
            String dni = scanner.nextLine();
            gestionJugadores.eliminarElemento(dni);
            gestionJugadores.guardarJSON();
            System.out.println("Jugador eliminado correctamente.");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarJugadores() {
        ArrayList<Jugador> lista = gestionJugadores.listar();
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
            gestionJugadores.cambiarEstadoContrato(dni, nuevoEstado);
            gestionJugadores.guardarJSON();
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
            int numeroCamiseta = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Valor jugador: ");
            double valorJugador = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Salario: ");
            double salario = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Monto compra: ");
            double monto = scanner.nextDouble();
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

            Contrato contrato = new Contrato(dni, salario, fechaFin, true, fechaInicio, mesesDuracion);
            Jugador jugador = new Jugador(dni, nombre, apellido, fechaNacimiento, nacionalidad, numeroCamiseta, contrato, posicion);
            jugador.setValorJugador(valorJugador);

            gestionJugadores.comprar_jugador(monto, jugador, fechaInicio);
            gestionJugadores.guardarJSON();
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

            gestionJugadores.vender_jugador(monto, dni, fecha);
            gestionJugadores.guardarJSON();
            System.out.println("Jugador vendido correctamente.");
        } catch (ElementoInexistenteEx | IngresoInvalido e) {
            System.out.println("Error al vender jugador: " + e.getMessage());
        }
    }

    private void pagarSalarios() {
        try {
            System.out.print("Ingrese fecha de pago: ");
            String fecha = scanner.nextLine();
            gestionJugadores.pagar_salarios(fecha);
            System.out.println("Salarios pagados correctamente.");
        } catch (FondoInsuficienteEx | IngresoInvalido e) {
            System.out.println("Error al pagar salarios: " + e.getMessage());
        }
    }
}
