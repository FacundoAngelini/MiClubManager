package Clase.Menus;
import java.util.ArrayList;
import java.util.Scanner;
import Clase.Persona.Socio;
import enums.Tiposocio;
import exeptions.*;

public class MenuSocios {

    private final MenuClub menuClub;
    private final Scanner scanner;

    public MenuSocios(MenuClub menuClub) {
        this.menuClub = menuClub;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuSocios() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n----- MENU SOCIOS -----");
            System.out.println("1. Agregar socio");
            System.out.println("2. Eliminar socio");
            System.out.println("3. Modificar socio");
            System.out.println("4. Listar socios");
            System.out.println("5. Aplicar recaudacion");
            System.out.println("6. Cambiar tipo de socio");
            System.out.println("7. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            String opcion = scanner.nextLine().trim();

            try {
                switch (opcion) {
                    case "1" -> agregarSocio();
                    case "2" -> eliminarSocio();
                    case "3" -> modificarSocio();
                    case "4" -> listarSocios();
                    case "5" -> aplicarRecaudacion();
                    case "6" -> cambiarTipoSocio();
                    case "7" -> salir = true;
                    default -> System.out.println("Opcion invalida, intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
    }

    private void agregarSocio() {
        try {
            System.out.println("\n--- Alta de nuevo socio ---");

            System.out.print("DNI: ");
            String dni = scanner.nextLine().trim();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine().trim();

            System.out.print("Fecha de nacimiento (dd/mm/yyyy): ");
            String fechaNacimiento = scanner.nextLine().trim();

            System.out.print("Nacionalidad: ");
            String nacionalidad = scanner.nextLine().trim();

            System.out.print("Cuota al dia (true/false): ");
            boolean cuotaAlDia = Boolean.parseBoolean(scanner.nextLine().trim());

            System.out.print("Fecha de alta (dd/mm/yyyy): ");
            String fechaAlta = scanner.nextLine().trim();

            System.out.print("Tipo de socio (JUVENIL, ACTIVO, VITALICIO): ");
            Tiposocio tipo = Tiposocio.valueOf(scanner.nextLine().trim().toUpperCase());

            menuClub.club.getGestionSocios().agregarSocio(
                    dni, nombre, apellido, fechaNacimiento, nacionalidad,
                    cuotaAlDia, fechaAlta, tipo
            );

            System.out.println("Socio agregado correctamente y guardado en el sistema.");

        } catch (ElementoDuplicadoEx | IngresoInvalido e) {
            System.out.println("Error al agregar socio: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de socio invalido. Debe ser JUVENIL, ACTIVO o VITALICIO.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private void eliminarSocio() {
        try {
            System.out.print("DNI del socio a eliminar: ");
            String dni = scanner.nextLine().trim();

            menuClub.club.getGestionSocios().eliminarElemento(dni);

            System.out.println("Socio eliminado y cambios guardados.");

        } catch (AccionImposible e) {
            System.out.println("No se pudo eliminar el socio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private void modificarSocio() {
        try {
            System.out.print("DNI del socio a modificar: ");
            String dni = scanner.nextLine().trim();

            Socio socio = menuClub.club.getGestionSocios().devuelveElemento(dni);

            System.out.println("Deje vacio si no desea modificar un campo.");

            System.out.print("Nuevo nombre (" + socio.getNombre() + "): ");
            String nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty()) socio.setNombre(nombre);

            System.out.print("Nuevo apellido (" + socio.getApellido() + "): ");
            String apellido = scanner.nextLine().trim();
            if (!apellido.isEmpty()) socio.setApellido(apellido);

            System.out.print("Cuota al dia (" + socio.isCuotaAlDia() + "): ");
            String cuota = scanner.nextLine().trim();
            if (!cuota.isEmpty()) socio.setCuotaAlDia(Boolean.parseBoolean(cuota));

            menuClub.club.getGestionSocios().modificarElemento(socio);

            System.out.println("Datos modificados y guardados correctamente.");

        } catch (AccionImposible e) {
            System.out.println("Error al modificar socio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private void listarSocios() {
        try {
            ArrayList<Socio> lista = menuClub.club.getGestionSocios().listar();

            if (lista.isEmpty()) {
                System.out.println("No hay socios registrados.");
                return;
            }

            System.out.println("\nLISTA DE SOCIOS:");
            for (Socio s : lista) {
                System.out.println("- " + s.getNumeroSocio() + " | " + s.getDni() + " | " +
                        s.getNombre() + " " + s.getApellido() +
                        " | Tipo: " + s.getTipoSocio() +
                        " | Cuota al dia: " + s.isCuotaAlDia());
            }

        } catch (Exception e) {
            System.out.println("Error al listar socios: " + e.getMessage());
        }
    }

    private void aplicarRecaudacion() {
        try {
            System.out.print("Fecha de aplicacion (dd/mm/yyyy): ");
            String fecha = scanner.nextLine().trim();

            menuClub.club.getGestionSocios().aplicarRecaudacion(fecha);

            System.out.println("Recaudacion aplicada y guardada correctamente.");

        } catch (IngresoInvalido e) {
            System.out.println("Error al aplicar recaudacion: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private void cambiarTipoSocio() {
        try {
            System.out.print("DNI del socio: ");
            String dni = scanner.nextLine().trim();

            menuClub.club.getGestionSocios().cambiarTipoSocio(dni);

            System.out.println("Tipo de socio actualizado y guardado.");

        } catch (AccionImposible e) {
            System.out.println("No se pudo cambiar el tipo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
}
