import java.util.Scanner;
import exeptions.AccionImposible;
import exeptions.IngresoInvalido;

public class MenuEstadio {

    private final GestionEstadio gestionEstadio;
    private final Scanner scanner;

    public MenuEstadio(GestionEstadio gestionEstadio) {
        this.gestionEstadio = gestionEstadio;
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

            gestionEstadio.agregarEstadio(nombre, capacidad, ubicacion, costo);
            gestionEstadio.guardarJSON();
            System.out.println("Estadio agregado correctamente");
        } catch (NumberFormatException e) {
            System.out.println("Error: valor numerico invalido");
        }
    }

    private void cambiarNombre() {
        try {
            System.out.print("Nuevo nombre del estadio: ");
            String nuevoNombre = scanner.nextLine();
            gestionEstadio.cambiarNombre(nuevoNombre);
            gestionEstadio.guardarJSON();
            System.out.println("Nombre cambiado correctamente");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void modificarCapacidad() {
        try {
            System.out.print("Nueva capacidad: ");
            int nuevaCapacidad = Integer.parseInt(scanner.nextLine());
            gestionEstadio.modificar_capacidad(nuevaCapacidad);
            gestionEstadio.guardarJSON();
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
            gestionEstadio.actualizar_costo_mantenimiento(nuevoCosto);
            gestionEstadio.guardarJSON();
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
            gestionEstadio.pagarMantenimiento(fecha);
            gestionEstadio.guardarJSON();
            System.out.println("Mantenimiento pagado correctamente");
        } catch (AccionImposible | IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarDatos() {
        if (gestionEstadio.getEstadio() == null) {
            System.out.println("No hay estadio creado");
            return;
        }
        System.out.println("Nombre: " + gestionEstadio.getEstadio().getNombre());
        System.out.println("Capacidad: " + gestionEstadio.getEstadio().getCapacidad());
        System.out.println("Ubicacion: " + gestionEstadio.getEstadio().getUbicacion());
        System.out.println("Costo mantenimiento: " + gestionEstadio.getEstadio().getCostoMantenimiento());
    }
}
