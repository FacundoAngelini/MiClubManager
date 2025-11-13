import java.util.Scanner;
import exeptions.FondoInsuficienteEx;
import exeptions.IngresoInvalido;

public class MenuPresupuesto {

    private final GestionPresupuesto gestionPresupuesto;
    private final Scanner scanner;

    public MenuPresupuesto(GestionPresupuesto gestionPresupuesto) {
        this.gestionPresupuesto = gestionPresupuesto;
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

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero valido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarFondos();
                    break;
                case 2:
                    quitarFondos();
                    break;
                case 3:
                    verSaldo();
                    break;
                case 4:
                    listarTransacciones();
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida");
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

            gestionPresupuesto.agregar_fondos(monto, descripcion, fecha);
            gestionPresupuesto.guardarJSON();
            System.out.println("Fondos agregados correctamente.");

        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: monto invalido.");
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

            gestionPresupuesto.quitarFondos(monto, descripcion, fecha);
            gestionPresupuesto.guardarJSON();
            System.out.println("Fondos retirados correctamente.");

        } catch (IngresoInvalido | FondoInsuficienteEx e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: monto invalido.");
        }
    }

    private void verSaldo() {
        double saldo = gestionPresupuesto.verSaldoActual();
        System.out.println("Saldo actual: " + saldo);
    }

    private void listarTransacciones() {
        System.out.println("\nLista de transacciones:");
        if (gestionPresupuesto.getListaTransacciones().isEmpty()) {
            System.out.println("No hay transacciones registradas.");
            return;
        }

        gestionPresupuesto.getListaTransacciones().forEach(t -> {
            System.out.println("Tipo: " + t.getTipo() + " | Monto: " + t.getMonto() +
                    " | Descripcion: " + t.getDescripcion() + " | Fecha: " + t.getFecha());
        });
    }
}
