package Clase.Menus;

import java.util.Scanner;
import exeptions.FondoInsuficienteEx;
import exeptions.IngresoInvalido;

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
            System.out.println("4. Ver lista de transacciones");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> agregarFondos();
                case "2" -> quitarFondos();
                case "3" -> verSaldo();
                case "4" -> listarTransacciones();
                case "5" -> salir = true;
                default -> System.out.println("Opcion invalida");
            }
        }
    }

    private void agregarFondos() {
        try {
            System.out.print("Ingrese monto a agregar: ");
            double monto = Double.parseDouble(scanner.nextLine());

            System.out.print("Ingrese descripcion del ingreso: ");
            String descripcion = scanner.nextLine();

            System.out.print("Ingrese fecha (dd/mm/yyyy): ");
            String fecha = scanner.nextLine();

            menuClub.club.getGestionPresupuesto().agregar_fondos(monto, descripcion, fecha);

            System.out.println("Fondos agregados correctamente.");
        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: monto invalido");
        } catch (Exception e) {
            System.out.println("Error inesperado al guardar los fondos: " + e.getMessage());
        }
    }

    private void quitarFondos() {
        try {
            System.out.print("Ingrese monto a retirar: ");
            double monto = Double.parseDouble(scanner.nextLine());

            System.out.print("Ingrese descripcion del retiro: ");
            String descripcion = scanner.nextLine();

            System.out.print("Ingrese fecha (dd/mm/yyyy): ");
            String fecha = scanner.nextLine();

            menuClub.club.getGestionPresupuesto().quitarFondos(monto, descripcion, fecha);

            System.out.println("Fondos retirados correctamente.");
        } catch (IngresoInvalido | FondoInsuficienteEx e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: monto invalido");
        } catch (Exception e) {
            System.out.println("Error inesperado al guardar los fondos: " + e.getMessage());
        }
    }

    private void verSaldo() {
        try {
            double saldo = menuClub.club.getGestionPresupuesto().verSaldoActual();
            System.out.println("Saldo actual: " + saldo);
        } catch (Exception e) {
            System.out.println("Error al obtener el saldo: " + e.getMessage());
        }
    }

    private void listarTransacciones() {
        try {
            System.out.println("\nLista de transacciones:");
            if (menuClub.club.getGestionPresupuesto().getListaTransacciones().isEmpty()) {
                System.out.println("No hay transacciones registradas");
                return;
            }

            menuClub.club.getGestionPresupuesto().getListaTransacciones().forEach(t -> {
                System.out.println("Tipo: " + t.getTipo() + " | Monto: " + t.getMonto() +
                        " | Descripcion: " + t.getDescripcion() + " | Fecha: " + t.getFecha());
            });
        } catch (Exception e) {
            System.out.println("Error al listar transacciones: " + e.getMessage());
        }
    }
}
