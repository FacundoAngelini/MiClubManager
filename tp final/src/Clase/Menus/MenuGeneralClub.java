package Clase.Menus;
import enums.Tiposocio;
import exeptions.ElementoDuplicadoEx;
import exeptions.IngresoInvalido;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        String nombre;
        int capacidad;
        String ubicacion;
        double costo;

        while (true) {
            System.out.print("Nombre del estadio: ");
            nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty() && nombre.matches("[a-zA-Z ]+")) {
                break;
            }
            System.out.println("Nombre invalido. Solo letras y espacios, intente de nuevo");
        }

        while (true) {
            try {
                System.out.print("Capacidad: ");
                capacidad = Integer.parseInt(scanner.nextLine());
                if (capacidad > 0) break;
                System.out.println("La capacidad debe ser mayor a 0");
            } catch (NumberFormatException e) {
                System.out.println("Numero invalido. Ingrese un valor entero");
            }
        }

        while (true) {
            System.out.print("Ubicacion: ");
            ubicacion = scanner.nextLine().trim();
            if (!ubicacion.isEmpty()) break;
            System.out.println("Ubicacion invalida. No puede estar vacia");
        }

        while (true) {
            try {
                System.out.print("Costo de mantenimiento: ");
                costo = Double.parseDouble(scanner.nextLine());
                if (costo >= 0) break;
                System.out.println("El costo no puede ser negativo");
            } catch (NumberFormatException e) {
                System.out.println("Numero invalido. Ingrese un valor numerico");
            }
        }

        try {
            menuClub.club.getGestionEstadios().agregarEstadio(nombre, capacidad, ubicacion, costo);
            System.out.println("Estadio agregado correctamente");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al agregar estadio: " + e.getMessage());
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
        String dni = null;
        String nombre = null;
        String apellido = null;
        LocalDate fechaNacimiento = null;
        String nacionalidad = null;
        LocalDate fechaAlta = null;
        Tiposocio tipoSocio = null;

        while (dni == null) {
            System.out.print("DNI: ");
            String input = scanner.nextLine();
            if (!input.isBlank()) dni = input;
            else System.out.println("Error: DNI no puede estar vacio");
        }

        while (nombre == null) {
            System.out.print("Nombre: ");
            String input = scanner.nextLine();
            if (!input.isBlank()) nombre = input;
            else System.out.println("Error: Nombre no puede estar vacio");
        }

        while (apellido == null) {
            System.out.print("Apellido: ");
            String input = scanner.nextLine();
            if (!input.isBlank()) apellido = input;
            else System.out.println("Error: Apellido no puede estar vacio");
        }

        while (fechaNacimiento == null) {
            System.out.print("Fecha nacimiento (yyyy-mm-dd): ");
            String input = scanner.nextLine();
            try {
                fechaNacimiento = LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Error: formato de fecha invalido");
            }
        }

        while (nacionalidad == null) {
            System.out.print("Nacionalidad: ");
            String input = scanner.nextLine();
            if (!input.isBlank()) nacionalidad = input;
            else System.out.println("Error: Nacionalidad no puede estar vacia");
        }

        while (fechaAlta == null) {
            System.out.print("Fecha alta (yyyy-mm-dd): ");
            String input = scanner.nextLine();
            try {
                fechaAlta = LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Error: formato de fecha invalido");
            }
        }

        while (tipoSocio == null) {
            System.out.println("Tipo socio: 1- JUVENIL | 2- ACTIVO | 3- VITALICIO");
            String input = scanner.nextLine();
            try {
                int t = Integer.parseInt(input);
                tipoSocio = switch (t) {
                    case 1 -> Tiposocio.JUVENIL;
                    case 2 -> Tiposocio.ACTIVO;
                    case 3 -> Tiposocio.VITALICIO;
                    default -> throw new IngresoInvalido("Tipo invalido");
                };
            } catch (NumberFormatException | IngresoInvalido e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        boolean agregado = false;
        while (!agregado) {
            try {
                menuClub.club.getGestionSocios().agregarSocio(
                        dni, nombre, apellido, fechaNacimiento, nacionalidad, fechaAlta, tipoSocio
                );
                System.out.println("Socio agregado correctamente");
                agregado = true;
            } catch (ElementoDuplicadoEx e) {
                System.out.println("Error: " + e.getMessage());
                dni = null;
                while (dni == null) {
                    System.out.print("Ingrese un nuevo DNI: ");
                    String input = scanner.nextLine();
                    if (!input.isBlank()) dni = input;
                    else System.out.println("Error: DNI no puede estar vacio");
                }
            } catch (IngresoInvalido e) {
                System.out.println("Error: " + e.getMessage());
                String msg = e.getMessage().toLowerCase();
                if (msg.contains("nombre")) nombre = null;
                if (msg.contains("apellido")) apellido = null;
                if (msg.contains("nacionalidad")) nacionalidad = null;
                if (msg.contains("fecha de nacimiento")) fechaNacimiento = null;
                if (msg.contains("fecha de alta")) fechaAlta = null;
                if (msg.contains("tipo")) tipoSocio = null;

                if (nombre == null) {
                    while (nombre == null) {
                        System.out.print("Nombre: ");
                        String input = scanner.nextLine();
                        if (!input.isBlank()) nombre = input;
                        else System.out.println("Error: Nombre no puede estar vacio");
                    }
                }
                if (apellido == null) {
                    while (apellido == null) {
                        System.out.print("Apellido: ");
                        String input = scanner.nextLine();
                        if (!input.isBlank()) apellido = input;
                        else System.out.println("Error: Apellido no puede estar vacio");
                    }
                }
                if (nacionalidad == null) {
                    while (nacionalidad == null) {
                        System.out.print("Nacionalidad: ");
                        String input = scanner.nextLine();
                        if (!input.isBlank()) nacionalidad = input;
                        else System.out.println("Error: Nacionalidad no puede estar vacia");
                    }
                }
                if (fechaNacimiento == null) {
                    while (fechaNacimiento == null) {
                        System.out.print("Fecha nacimiento (yyyy-mm-dd): ");
                        String input = scanner.nextLine();
                        try {
                            fechaNacimiento = LocalDate.parse(input);
                        } catch (DateTimeParseException ex) {
                            System.out.println("Error: formato de fecha invalido");
                        }
                    }
                }
                if (fechaAlta == null) {
                    while (fechaAlta == null) {
                        System.out.print("Fecha alta (yyyy-mm-dd): ");
                        String input = scanner.nextLine();
                        try {
                            fechaAlta = LocalDate.parse(input);
                        } catch (DateTimeParseException ex) {
                            System.out.println("Error: formato de fecha invalido");
                        }
                    }
                }
                if (tipoSocio == null) {
                    while (tipoSocio == null) {
                        System.out.println("Tipo socio: 1- JUVENIL | 2- ACTIVO | 3- VITALICIO");
                        String input = scanner.nextLine();
                        try {
                            int t = Integer.parseInt(input);
                            tipoSocio = switch (t) {
                                case 1 -> Tiposocio.JUVENIL;
                                case 2 -> Tiposocio.ACTIVO;
                                case 3 -> Tiposocio.VITALICIO;
                                default -> throw new IngresoInvalido("Tipo invalido");
                            };
                        } catch (NumberFormatException | IngresoInvalido ex) {
                            System.out.println("Error: " + ex.getMessage());
                        }
                    }
                }
            }
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
        String tipo = null;
        String nombre = null;
        String marca = null;
        int cantidad = 0;
        String extra = null;

        while (tipo == null) {
            System.out.print("Tipo (pelota/camiseta): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("pelota") || input.equals("camiseta")) {
                tipo = input;
            } else {
                System.out.println("Error: tipo invalido, debe ser pelota o camiseta");
            }
        }

        while (nombre == null) {
            System.out.print("Nombre: ");
            String input = scanner.nextLine().trim();
            if (!input.isBlank()) {
                nombre = input;
            } else {
                System.out.println("Error: nombre no puede estar vacio");
            }
        }

        while (marca == null) {
            System.out.print("Marca: ");
            String input = scanner.nextLine().trim();
            if (!input.isBlank()) {
                marca = input;
            } else {
                System.out.println("Error: marca no puede estar vacia");
            }
        }
        boolean cantidadValida = false;
        while (!cantidadValida) {
            System.out.print("Cantidad: ");
            String input = scanner.nextLine().trim();
            try {
                int val = Integer.parseInt(input);
                if (val > 0) {
                    cantidad = val;
                    cantidadValida = true;
                } else {
                    System.out.println("Error: la cantidad debe ser mayor a 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: cantidad invalida");
            }
        }

        while (extra == null) {
            if (tipo.equals("pelota")) {
                System.out.print("Modelo de la pelota: ");
            } else {
                System.out.print("Sponsor de la camiseta: ");
            }
            String input = scanner.nextLine().trim();
            if (!input.isBlank()) {
                extra = input;
            } else {
                System.out.println("Error: este dato no puede estar vacio");
            }
        }

        try {
            menuClub.club.getInventario().agregarProducto(tipo, nombre, marca, cantidad, extra);
            System.out.println("Producto agregado correctamente");
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



