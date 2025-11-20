package Clase.Menus;
import enums.Tiposocio;
import exeptions.ElementoDuplicadoEx;
import exeptions.ElementoInexistenteEx;
import exeptions.IngresoInvalido;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                System.out.println("Error: ingrese un numero valido");
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
                default -> System.out.println("Opcion invalida");
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
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (dni == null) {
            System.out.print("DNI ");
            String input = scanner.nextLine().trim();
            if (input.isBlank() || !input.matches("\\d+")) {
                System.out.println("Error DNI no puede estar vacio y debe tener solo numeros");
                continue;
            }
            if (input.length() < 8) {
                System.out.println("Error DNI debe tener minimo 8 digitos");
                continue;
            }
            dni = input;
        }


        while (nombre == null) {
            System.out.print("Nombre: ");
            String input = scanner.nextLine().trim();
            if (input.isBlank() || !input.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
                System.out.println("Error: Nombre invalido. Solo letras");
            } else {
                nombre = input;
            }
        }

        while (apellido == null) {
            System.out.print("Apellido: ");
            String input = scanner.nextLine().trim();
            if (input.isBlank() || !input.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
                System.out.println("Error: Apellido invalido. Solo letras");
            } else {
                apellido = input;
            }
        }

        while (fechaNacimiento == null) {
            System.out.print("Fecha nacimiento (dd/MM/yyyy): ");
            String input = scanner.nextLine().trim();
            try {
                LocalDate fecha = LocalDate.parse(input, formato);
                fechaNacimiento = fecha;
            } catch (Exception e) {
                System.out.println("Error: formato de fecha invalido");
            }
        }

        while (nacionalidad == null) {
            System.out.print("Nacionalidad: ");
            String input = scanner.nextLine().trim();
            if (input.isBlank() || !input.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
                System.out.println("Error: Nacionalidad invalida. Solo letras");
            } else {
                nacionalidad = input;
            }
        }

        while (fechaAlta == null) {
            System.out.print("Fecha alta (dd/MM/yyyy): ");
            String input = scanner.nextLine().trim();
            try {
                fechaAlta = LocalDate.parse(input, formato);
            } catch (Exception e) {
                System.out.println("Error: formato de fecha invalido");
            }
        }

        while (tipoSocio == null) {
            System.out.println("Tipo socio: 1- JUVENIL | 2- ACTIVO | 3- VITALICIO | 4- INACTIVO");
            String input = scanner.nextLine().trim();
            try {
                int t = Integer.parseInt(input);
                tipoSocio = switch (t) {
                    case 1 -> Tiposocio.JUVENIL;
                    case 2 -> Tiposocio.ACTIVO;
                    case 3 -> Tiposocio.VITALICIO;
                    case 4 -> Tiposocio.INACTIVO;
                    default -> null;
                };
                if (tipoSocio == null) System.out.println("Tipo invalido");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número valido para el tipo.");
            }
        }

        try {
            menuClub.club.getGestionSocios().agregarSocio(dni, nombre, apellido, fechaNacimiento, nacionalidad, fechaAlta, tipoSocio);
            System.out.println("Socio agregado correctamente");
        } catch (ElementoDuplicadoEx | IngresoInvalido e) {
            System.out.println("Error al agregar socio: " + e.getMessage());
        }
    }



    private void eliminarSocio() {
        try {
            System.out.print("dni del socio a eliminar: ");
            String dni = scanner.nextLine();

            menuClub.club.getGestionSocios().eliminarElemento(dni);
            System.out.println("socio eliminado");
        } catch (Exception e) {
            System.out.println("error: ");
        }
    }

    private void listarSocios() {
        var lista = menuClub.club.getGestionSocios().listar();

        if (lista.isEmpty()) {
            System.out.println("no hay socios registrados");
            return;
        }

        lista.forEach(s -> System.out.println(s.getNumeroSocio() + " | " + s.getNombre() + " " + s.getApellido() + " | DNI: " + s.getDni() + " | Tipo: " + s.getTiposocio()));
    }

    private void modificarSocio() {
        try {
            System.out.print("DNI del socio a modificar: ");
            String dni = scanner.nextLine().trim();
            if (dni.isBlank() || !dni.matches("\\d+")) {
                throw new IngresoInvalido("Error: DNI invalido. Debe contener solo numeros y no estar vacio");
            }

            String nombre = null;
            while (true) {
                System.out.print("Nuevo nombre (enter para dejar igual): ");
                String input = scanner.nextLine().trim();
                if (input.isBlank()) {
                    break;
                } else if (!input.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    System.out.println("Error: El nombre solo puede contener letras y espacios.");
                } else {
                    nombre = input;
                    break;
                }
            }
            String apellido = null;
            while (true) {
                System.out.print("Nuevo apellido (enter para dejar igual): ");
                String input = scanner.nextLine().trim();
                if (input.isBlank()) {
                    break;
                } else if (!input.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    System.out.println("Error: El apellido solo puede contener letras y espacios.");
                } else {
                    apellido = input;
                    break;
                }
            }

            LocalDate fechaAlta = null;
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            while (true) {
                System.out.print("Nueva fecha de alta (dd/MM/yyyy, enter para dejar igual): ");
                String input = scanner.nextLine().trim();
                if (input.isBlank()) {
                    break;
                }
                try {
                    fechaAlta = LocalDate.parse(input, formato);
                    break;
                } catch (Exception e) {
                    System.out.println("Error: formato de fecha inválido. Debe ser dd/MM/yyyy.");
                }
            }

            menuClub.club.getGestionSocios().modificarSocio(dni, nombre, apellido, fechaAlta);
            System.out.println("Socio modificado correctamente");

        } catch (IngresoInvalido e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrio un error inesperado");
        }
    }


    private void cambiarTipoSocio() {
        try {
            System.out.print("dni socio: ");
            String dni = scanner.nextLine();

            menuClub.club.getGestionSocios().cambiarTipoSocio(dni);
            System.out.println("tipo de socio actualizado");

        } catch (Exception e) {
            System.out.println("error");
        }
    }
    private void agregarProducto() {
        String tipo = null;
        String nombre = null;
        String marca = null;
        Integer cantidad = null;
        String extra = null;

        while (tipo == null) {
            System.out.print("Tipo (pelota/camiseta): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (!input.matches("pelota|camiseta")) {
                System.out.println("Error: tipo invalido, debe ser pelota o camiseta");
            } else {
                tipo = input;
            }
        }

        while (nombre == null) {
            System.out.print("Nombre: ");
            String input = scanner.nextLine().trim();

            if (!input.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("Error: el nombre solo puede contener letras");
            } else {
                nombre = input;
            }
        }

        while (marca == null) {
            System.out.print("Marca: ");
            String input = scanner.nextLine().trim();

            if (!input.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("Error: la marca solo puede contener letras");
            } else {
                marca = input;
            }
        }

        while (cantidad == null) {
            System.out.print("Cantidad: ");
            String input = scanner.nextLine().trim();

            try {
                int val = Integer.parseInt(input);
                if (val <= 0) {
                    System.out.println("Error: la cantidad debe ser mayor a 0");
                } else {
                    cantidad = val;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número valido");
            }
        }

        while (extra == null) {
            if (tipo.equals("pelota")) System.out.print("Modelo de la pelota: ");
            else System.out.print("Sponsor de la camiseta: ");

            String input = scanner.nextLine().trim();

            if (!input.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("Error: el dato contiene caracteres invalidos");
            } else {
                extra = input;
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

        String tipo = null;
        String nombre = null;
        String marca = null;

        while (tipo == null) {
            System.out.print("Tipo del producto (pelota/camiseta): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (!input.matches("pelota|camiseta")) {
                System.out.println("Error: tipo invalido");
            } else {
                tipo = input;
            }
        }

        while (nombre == null) {
            System.out.print("Nombre del producto: ");
            String input = scanner.nextLine().trim();

            if (!input.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("Error: el nombre solo puede contener letras");
            } else {
                nombre = input;
            }
        }

        while (marca == null) {
            System.out.print("Marca del producto: ");
            String input = scanner.nextLine().trim();

            if (!input.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("Error: la marca solo puede contener letras");
            } else {
                marca = input;
            }
        }

        try {
            String clave = generarClave(tipo, nombre, marca);
            if (!menuClub.club.getInventario().existeProducto(clave)) {
                System.out.println("Error: el producto no existe, no se puede eliminar");
                return;
            }

            menuClub.club.getInventario().eliminarElemento(clave);
            System.out.println("Producto eliminado correctamente");

        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void consultarStock() {
        String tipo = null;
        String marca = null;
        while (tipo == null) {
            System.out.print("Tipo (pelota/camiseta): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("pelota") || input.equals("camiseta")) {
                tipo = input;
            } else {
                System.out.println("Error: tipo invalido. Debe ser pelota o camiseta. Intente de nuevo");
            }
        }

        while (marca == null) {
            System.out.print("Marca: ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Error: la marca no puede estar vacia. Intente de nuevo.");
                continue;
            }

            try {
                boolean marcaExiste = menuClub.club.getInventario().marcaExisteParaTipo(tipo, input);
                if (marcaExiste) {
                    marca = input;
                } else {
                    System.out.println("Error: marca invalida para este tipo. Intente de nuevo");
                }
            } catch (Exception e) {
                System.out.println("Error inesperado" );
            }
        }
        try {
            String info = menuClub.club.getInventario().consultarStockPorTipoMarca(tipo, marca);
            System.out.println("\n--- STOCK DISPONIBLE ---");
            System.out.println(info);
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarProducto() {
        String tipo = null;
        String nombre = null;
        String marca = null;
        while (tipo == null) {
            System.out.print("Tipo (pelota/camiseta): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("pelota") || input.equals("camiseta")) {
                tipo = input;
            } else {
                System.out.println("Error: tipo invalido. Debe ser pelota o camiseta. Intente de nuevo");
            }
        }
        while (nombre == null) {
            System.out.print("Nombre del producto: ");
            String input = scanner.nextLine().trim();
            if (!input.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("Error: el nombre solo puede contener letras. Intente de nuevo");
            } else {
                nombre = input;
            }
        }

        while (marca == null) {
            System.out.print("Marca: ");
            String input = scanner.nextLine().trim();
            if (!input.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("Error: la marca solo puede contener letras. Intente de nuevo");
                continue;
            }

            try {
                boolean marcaExiste = menuClub.club.getInventario().marcaExisteParaTipo(tipo, input);
                if (marcaExiste) {
                    marca = input;
                } else {
                    System.out.println("Error: marca invalida para este tipo. Intente de nuevo");
                }
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }

        try {
            String info = menuClub.club.getInventario().buscarProducto(tipo, nombre, marca);
            System.out.println("\n--- INFORMACIÓN DEL PRODUCTO ---");
            System.out.println(info);
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String generarClave(String tipo, String nombre, String marca) {
        return tipo.toLowerCase() + nombre.toLowerCase() + marca.toLowerCase();
    }
}



