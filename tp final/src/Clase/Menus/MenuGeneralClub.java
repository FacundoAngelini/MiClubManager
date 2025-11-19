package Clase.Menus;
import enums.Tiposocio;
import exeptions.IngresoInvalido;
import java.time.LocalDate;
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
            System.out.println("5. Agregar socio");
            System.out.println("6. Eliminar socio");
            System.out.println("7. Listar socios");
            System.out.println("8. Modificar socio ");
            System.out.println("9. Cambiar tipo de socio");
            System.out.println("10. Agregar producto");
            System.out.println("11. Eliminar producto");
            System.out.println("12. Consultar stock");
            System.out.println("13. Buscar producto ");
            System.out.println("14. Salir del menu general club");

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
                case 5 -> agregarSocio();
                case 6 -> eliminarSocio();
                case 7 -> listarSocios();
                case 8 -> modificarSocio();
                case 9 -> cambiarTipoSocio();
                case 10 -> agregarProducto();
                case 11 -> eliminarProducto();
                case 12 -> consultarStock();
                case 13 -> buscarProducto();
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
        }catch (AccionImposible e) {
            System.out.println("Error: capacidad invalida");
        }
    }

    private void cambiarNombre() {
        System.out.print("Nuevo nombre del estadio: ");
        String nuevoNombre = scanner.nextLine();
        try {
            menuClub.club.getGestionEstadios().cambiarNombre(nuevoNombre);
        } catch (AccionImposible e) {
            System.out.println("Error: capacidad invalida");
        }
    }

    private void validarEstadioExistente() {
        try {
            menuClub.club.getGestionEstadios().validarEstadioExistente();
            System.out.println("Estadio existe y esta listo para operar");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarSocio() {
        try {
            System.out.print("dni: ");
            String dni = scanner.nextLine();

            System.out.print("nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("fecha nacimiento (yyyy-mm-dd): ");
            LocalDate fn = LocalDate.parse(scanner.nextLine());

            System.out.print("nacionalidad: ");
            String nacionalidad = scanner.nextLine();

            System.out.print("fecha alta (yyyy-mm-dd): ");
            LocalDate alta = LocalDate.parse(scanner.nextLine());

            System.out.println("tipo socio: 1- JUVENIL | 2- ACTIVO | 3- VITALICIO");
            int t = Integer.parseInt(scanner.nextLine());
            Tiposocio tipo = switch (t) {
                case 1 -> Tiposocio.JUVENIL;
                case 2 -> Tiposocio.ACTIVO;
                case 3 -> Tiposocio.VITALICIO;
                default -> throw new IngresoInvalido("tipo invalido");
            };

            menuClub.club.getGestionSocios().agregarSocio(
                    dni, nombre, apellido, fn, nacionalidad, alta, tipo
            );

            System.out.println("socio agregado correctamente");

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void eliminarSocio() {
        try {
            System.out.print("dni del socio a eliminar: ");
            String dni = scanner.nextLine();

            menuClub.club.getGestionSocios().eliminarElemento(dni);
            System.out.println("socio eliminado");
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void listarSocios() {
        var lista = menuClub.club.getGestionSocios().listar();

        if (lista.isEmpty()) {
            System.out.println("no hay socios registrados");
            return;
        }

        lista.forEach(s -> System.out.println(
                s.getNumeroSocio() + " | " + s.getNombre() + " " + s.getApellido() +
                        " | DNI: " + s.getDni() +
                        " | Tipo: " + s.getTiposocio()
        ));
    }

    private void modificarSocio() {
        try {
            System.out.print("dni del socio: ");
            String dni = scanner.nextLine();

            System.out.print("nuevo nombre (enter para dejar igual): ");
            String nombre = scanner.nextLine();
            if (nombre.isBlank()) nombre = null;

            System.out.print("nuevo apellido (enter para dejar igual): ");
            String apellido = scanner.nextLine();
            if (apellido.isBlank()) apellido = null;

            System.out.print("nueva fecha alta (yyyy-mm-dd, enter para dejar igual): ");
            String fa = scanner.nextLine();
            LocalDate fechaAlta = fa.isBlank() ? null : LocalDate.parse(fa);

            menuClub.club.getGestionSocios().modificarSocio(dni, nombre, apellido, fechaAlta);

            System.out.println("socio modificado correctamente");

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void cambiarTipoSocio() {
        try {
            System.out.print("dni socio: ");
            String dni = scanner.nextLine();

            menuClub.club.getGestionSocios().cambiarTipoSocio(dni);
            System.out.println("tipo de socio actualizado");

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void agregarProducto() {
        try {
            System.out.print("Tipo (pelota/camiseta): ");
            String tipo = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            String extra = "";
            if (tipo.equalsIgnoreCase("pelota")) {
                System.out.print("Modelo de la pelota: ");
                extra = scanner.nextLine();
            } else if (tipo.equalsIgnoreCase("camiseta")) {
                System.out.print("Sponsor de la camiseta: ");
                extra = scanner.nextLine();
            }

            menuClub.club.getInventario().agregarProducto(tipo, nombre, marca, cantidad, extra);

        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        try {
            System.out.print("Ingrese tipo del producto: ");
            String tipo = scanner.nextLine();

            System.out.print("Ingrese nombre del producto: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese marca del producto: ");
            String marca = scanner.nextLine();

            String clave = generarClave(tipo, nombre, marca);

            menuClub.club.getInventario().eliminarElemento(clave);

        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void consultarStock() {
        try {
            System.out.print("Tipo: ");
            String tipo = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            String clave = generarClave(tipo, nombre, marca);

            int cantidad = menuClub.club.getInventario().consultarStock(clave);
            System.out.println("Stock actual: " + cantidad);

        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarProducto() {
        try {
            System.out.print("Tipo: ");
            String tipo = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            String clave = generarClave(tipo, nombre, marca);

            var prod = menuClub.club.getInventario().devuelveElemento(clave);
            System.out.println(prod.muestraDatos());

        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String generarClave(String tipo, String nombre, String marca) {
        return tipo.toLowerCase() + nombre.toLowerCase() + marca.toLowerCase();
    }
}



