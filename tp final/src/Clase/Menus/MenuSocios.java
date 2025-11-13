package Clase.Menus;

import java.util.Scanner;
import Clase.Persona.Socio;
import enums.Tiposocio;
import exeptions.AccionImposible;
import exeptions.ElementoDuplicadoEx;
import exeptions.IngresoInvalido;
import java.util.ArrayList;
public class MenuSocios {
    private MenuClub menuClub;
    private final Scanner scanner;

    public MenuSocios() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuSocios() {
        boolean salir = false;

        while (!salir) {
            System.out.println("----- MENU SOCIOS -----");
            System.out.println("1. Agregar socio");
            System.out.println("2. Eliminar socio");
            System.out.println("3. Modificar socio");
            System.out.println("4. Listar socios");
            System.out.println("5. Aplicar recaudacion");
            System.out.println("6. Cambiar tipo de socio");
            System.out.println("7. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarSocio();
                    break;
                case 2:
                    eliminarSocio();
                    break;
                case 3:
                    modificarSocio();
                    break;
                case 4:
                    listarSocios();
                    break;
                case 5:

                    aplicarRecaudacion();
                    break;
                case 6:
                    cambiarTipoSocio();
                    break;
                case 7:
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida, intente de nuevo");
            }
        }
    }

    private void agregarSocio() {
        try {
            System.out.print("DNI: ");
            String dni = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("Fecha de nacimiento (dd/mm/yyyy): ");
            String fechaNacimiento = scanner.nextLine();

            System.out.print("Nacionalidad: ");
            String nacionalidad = scanner.nextLine();

            System.out.print("Cuota al dia (true/false): ");
            boolean cuotaAlDia = Boolean.parseBoolean(scanner.nextLine());

            System.out.print("Fecha de alta (dd/mm/yyyy): ");
            String fechaAlta = scanner.nextLine();

            System.out.print("Tipo de socio (JUVENIL, ACTIVO, VITALICIO): ");
            Tiposocio tipo = Tiposocio.valueOf(scanner.nextLine().toUpperCase());

            menuClub.club.getGestionSocios().agregarSocio(dni, nombre, apellido, fechaNacimiento, nacionalidad, cuotaAlDia, fechaAlta, tipo);
            System.out.println("Clases_Manu.Socio agregado correctamente");
        } catch (IngresoInvalido | ElementoDuplicadoEx e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de socio invalido");
        }
    }

    private void eliminarSocio() {
        try {
            System.out.print("DNI del socio a eliminar: ");
            String dni = scanner.nextLine();
           menuClub.club.getGestionSocios().eliminarElemento(dni);
            System.out.println("Clases_Manu.Socio eliminado correctamente");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void modificarSocio() {
        try {
            System.out.print("DNI del socio a modificar: ");
            String dni = scanner.nextLine();
            Socio socio = menuClub.club.getGestionSocios().devuelveElemento(dni);

            System.out.print("Nuevo nombre (" + socio.getNombre() + "): ");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) socio.setNombre(nombre);

            System.out.print("Nuevo apellido (" + socio.getApellido() + "): ");
            String apellido = scanner.nextLine();
            if (!apellido.isEmpty()) socio.setApellido(apellido);

            System.out.print("Cuota al dia (" + socio.isCuotaAlDia() + "): ");
            String cuota = scanner.nextLine();
            if (!cuota.isEmpty()) socio.setCuotaAlDia(Boolean.parseBoolean(cuota));

            menuClub.club.getGestionSocios().modificarElemento(socio);
            System.out.println("Clases_Manu.Socio modificado correctamente");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarSocios() {
        ArrayList<Socio> lista = menuClub.club.getGestionSocios().listar();
        if (lista.isEmpty()) {
            System.out.println("No hay socios registrados");
        } else {
            for (Socio s : lista) {
                System.out.println("Numero: " + s.getNumeroSocio() + " - DNI: " + s.getDni() +
                        " Nombre: " + s.getNombre() + " " + s.getApellido() +
                        " - Cuota al dia: " + s.isCuotaAlDia() + " - Tipo: " + s.getTipoSocio());
            }
        }
    }

    private void aplicarRecaudacion() {
        try {
            System.out.print("Fecha de aplicacion (dd/mm/yyyy): ");
            String fecha = scanner.nextLine();
            menuClub.club.getGestionSocios().aplicarRecaudacion(fecha);
            System.out.println("Recaudacion aplicada correctamente");
        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cambiarTipoSocio() {
        try {
            System.out.print("DNI del socio a cambiar tipo: ");
            String dni = scanner.nextLine();
            menuClub.club.getGestionSocios().cambiarTipoSocio(dni);
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
