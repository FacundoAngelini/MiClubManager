package Clase.Menus;

import exeptions.FondoInsuficienteEx;
import exeptions.IngresoInvalido;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuPresupuesto {

    private MenuClub menuClub;
    private final Scanner scanner;

    public MenuPresupuesto(MenuClub menuClub) {
        this.menuClub = menuClub;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuPresupuesto() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n----- MENU PRESUPUESTO -----");
            System.out.println("1. Agregar fondos");
            System.out.println("2. Quitar fondos");
            System.out.println("3. Ver saldo actual");
            System.out.println("4. Ver transacciones");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> agregarFondos();
                case "2" -> quitarFondos();
                case "3" -> verSaldo();
                case "4" -> listarTransacciones();
                case "5" -> calcularGastoSalariosCT();
                case "6" -> aplicarGastoSalariosCT();
                case "7" -> actualizarCostoMantenimientoEstadio();
                case "8" -> pagarMantenimientoEstadio();
                case "9" -> pagarSalariosJugadores();
                case "10" -> aplicarRecaudacionSocios();
                case "11" -> mostrarRecaudacionTotalSocios();
                case "12" -> salir = true;
                default -> System.out.println("Opcion invalida");
            }
        }
    }

    private void agregarFondos() {
        try {
            System.out.print("Monto a agregar: ");
            double monto = Double.parseDouble(scanner.nextLine());

            System.out.print("Descripcion del ingreso: ");
            String descripcion = scanner.nextLine();

            System.out.print("Fecha (yyyy-mm-dd): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());

            menuClub.club.getGestionPresupuesto().agregarFondos(monto, descripcion, fecha);
            System.out.println("Fondos agregados correctamente");

        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: datos invalidos");
        }
    }

    private void quitarFondos() {
        try {
            System.out.print("Monto a retirar: ");
            double monto = Double.parseDouble(scanner.nextLine());

            System.out.print("Descripcion del retiro: ");
            String descripcion = scanner.nextLine();

            System.out.print("Fecha (yyyy-mm-dd): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());

            menuClub.club.getGestionPresupuesto().quitarFondos(monto, descripcion, fecha);
            System.out.println("Fondos retirados correctamente");

        } catch (IngresoInvalido | FondoInsuficienteEx e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: datos invalidos");
        }
    }

    private void verSaldo() {
        double saldo = menuClub.club.getGestionPresupuesto().verSaldoActual();
        System.out.println("Saldo actual: " + saldo);
    }

    private void listarTransacciones() {
        ArrayList<String> transacciones = menuClub.club.getGestionPresupuesto().listarTransaccionesInfo();
        if (transacciones.isEmpty()) {
            System.out.println("No hay transacciones registradas");
            return;
        }

        System.out.println("\nTransacciones:");
        transacciones.forEach(System.out::println);
    }

    private void calcularGastoSalariosCT() {
        try {
            double gasto = menuClub.club.getGestionCuerpoTecnico().calcularGastoSalarios();
            System.out.println("Gasto total en salarios del cuerpo técnico: " + gasto);
        } catch (Exception e) {
            System.out.println("Error al calcular salarios: " + e.getMessage());
        }
    }

    private void aplicarGastoSalariosCT() {
        try {
            System.out.print("Ingrese la fecha del pago (yyyy-mm-dd): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());

            menuClub.club.getGestionCuerpoTecnico().aplicarGastoSalarios(fecha);
        } catch (Exception e) {
            System.out.println("Error al aplicar el pago de salarios: " + e.getMessage());
        }
    }

    private void actualizarCostoMantenimientoEstadio() {
        try {
            System.out.print("Ingrese el nuevo costo de mantenimiento: ");
            double nuevoCosto = Double.parseDouble(scanner.nextLine());

            menuClub.club.getGestionEstadios().actualizarCostoMantenimiento(nuevoCosto);
        } catch (NumberFormatException e) {
            System.out.println("Error: monto invalido");
        } catch (Exception e) {
            System.out.println("Error al actualizar costo: " + e.getMessage());
        }
    }

    private void pagarMantenimientoEstadio() {
        try {
            System.out.print("Ingrese la fecha del pago (yyyy-mm-dd): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());

            menuClub.club.getGestionEstadios().pagarMantenimiento(fecha);
        } catch (Exception e) {
            System.out.println("Error al pagar mantenimiento: " + e.getMessage());
        }
    }

    public void pagarSalariosJugadores() {
        try {
            System.out.print("Ingrese la fecha del pago (yyyy-mm-dd): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());

            menuClub.club.getGestionJugadores().pagarSalarios(fecha);
        } catch (FondoInsuficienteEx | IngresoInvalido e) {
            System.out.println("Error al pagar salarios: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    public void aplicarRecaudacionSocios() {
        try {
            System.out.print("Ingrese la fecha de la recaudacion (yyyy-mm-dd) o deje vacio para hoy: ");
            String input = scanner.nextLine().trim();
            LocalDate fecha;
            if (input.isEmpty()) {
                fecha = LocalDate.now();
            } else {
                fecha = LocalDate.parse(input);
            }

            menuClub.club.getGestionSocios().aplicarRecaudacion(fecha); // Llama al método de la clase de gestión de socios
            System.out.println("Recaudacion aplicada correctamente al presupuesto");

        } catch (IngresoInvalido e) {
            System.out.println("Error al aplicar recaudacion: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    public void mostrarRecaudacionTotalSocios() {
        try {
            double total = menuClub.club.getGestionSocios().obtenerRecaudacionTotal();
            System.out.println("Recaudacion total de socios: $" + total);
        } catch (Exception e) {
            System.out.println("Error al obtener la recaudacion: " + e.getMessage());
        }
    }




}
