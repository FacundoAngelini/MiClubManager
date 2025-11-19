package Clase.Menus;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import Clase.Persona.CuerpoTecnico;
import enums.Posicion;
import enums.Puesto;
import exeptions.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuPlantel {

    private MenuClub menuClub;
    private final Scanner scanner;

    public MenuPlantel(MenuClub menuClub) {
        this.menuClub = menuClub;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuPlantel() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n----- Menu Plantel -----");
            System.out.println("1. Agregar miembro del cuerpo tecnico");
            System.out.println("2. Eliminar miembro del cuerpo tecnico");
            System.out.println("3. Listar miembros del cuerpo tecnico");
            System.out.println("4. Modificar miembro de cuerpo tecnico");
            System.out.println("5. Agregar jugador");
            System.out.println("6. Eliminar jugador");
            System.out.println("7. Mostrar jugador");
            System.out.println("8. Acutualizar estadisticas ");
            System.out.println("9. Listar jugadores");
            System.out.println("10. Agregar partido");
            System.out.println("11. Eliminar partido");
            System.out.println("12. Buscar partido existente");
            System.out.println("13. Mostrar partidos");
            System.out.println("14. Salir del menu Plantel");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero valido.");
                continue;
            }

            switch (opcion) {
                case 1 -> agregarMiembroCT();
                case 2 -> eliminarMiembroCT();
                case 3 -> listarMiembrosCT();
                case 4 -> modificarMiembroCT();
                case 5 -> agregarJugadorMenu();
                case 6 -> eliminarJugadorMenu();
                case 7 -> mostrarJugadorMenu();
                case 8 -> actualizarEstadisticasMenu();
                case 9 -> listarJugadoresMenu();
                case 10 -> agregarPartido();
                case 11 -> eliminarPartido();
                case 12 -> existePartido();
                case 13 -> listarPartidos();
                case 14 -> salir = true;
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private void agregarMiembroCT() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dni = null;
        String nombre = null;
        String apellido = null;
        LocalDate fechaNacimiento = null;
        String nacionalidad = null;
        double salario = 0;
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        Puesto puesto = null;
        int aniosExp = -1;

        while (dni == null) {
            System.out.print("DNI: ");
            String input = scanner.nextLine();
            if (input.matches("\\d+")) dni = input;
            else System.out.println("DNI inválido, solo números.");
        }

        while (nombre == null) {
            System.out.print("Nombre: ");
            String input = scanner.nextLine();
            if (input.matches("[a-zA-Z]+")) nombre = input;
            else System.out.println("Nombre inválido, solo letras.");
        }

        while (apellido == null) {
            System.out.print("Apellido: ");
            String input = scanner.nextLine();
            if (input.matches("[a-zA-Z]+")) apellido = input;
            else System.out.println("Apellido inválido, solo letras.");
        }

        while (fechaNacimiento == null) {
            System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
            String input = scanner.nextLine();
            try {
                LocalDate fecha = LocalDate.parse(input, formatter);
                if (fecha.plusYears(18).isAfter(LocalDate.now())) {
                    System.out.println("Debe ser mayor a 18 años.");
                } else {
                    fechaNacimiento = fecha;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto.");
            }
        }

        while (nacionalidad == null) {
            System.out.print("Nacionalidad: ");
            String input = scanner.nextLine();
            if (input.matches("[a-zA-Z ]+")) nacionalidad = input;
            else System.out.println("Nacionalidad inválida, solo letras y espacios.");
        }

        while (salario <= 0) {
            System.out.print("Salario: ");
            try {
                double input = Double.parseDouble(scanner.nextLine());
                if (input > 0) salario = input;
                else System.out.println("Salario debe ser mayor que cero.");
            } catch (NumberFormatException e) {
                System.out.println("Salario inválido, debe ser un número.");
            }
        }

        while (fechaInicio == null) {
            System.out.print("Fecha inicio contrato (dd/MM/yyyy): ");
            try {
                LocalDate input = LocalDate.parse(scanner.nextLine(), formatter);
                if (!input.isAfter(LocalDate.now())) fechaInicio = input;
                else System.out.println("Fecha inicio no puede ser futura.");
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto.");
            }
        }

        while (fechaFin == null) {
            System.out.print("Fecha fin contrato (dd/MM/yyyy): ");
            try {
                LocalDate input = LocalDate.parse(scanner.nextLine(), formatter);
                if (!input.isBefore(fechaInicio)) fechaFin = input;
                else System.out.println("Fecha fin no puede ser anterior a inicio.");
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto.");
            }
        }

        while (puesto == null) {
            System.out.print("Puesto (DT, AYUDANTE, PREPARADOR, FISIOTERAPEUTA): ");
            try {
                puesto = Puesto.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Puesto inválido.");
            }
        }

        while (aniosExp < 0) {
            System.out.print("Años de experiencia: ");
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= 0) aniosExp = input;
                else System.out.println("Años de experiencia no pueden ser negativos.");
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }

        try {
            menuClub.club.getGestionCuerpoTecnico().agregarElemento(
                    dni, nombre, apellido, fechaNacimiento, nacionalidad,
                    salario, fechaInicio, fechaFin, puesto, aniosExp
            );
            menuClub.club.getGestionCuerpoTecnico().aplicarGastoSalarios(fechaInicio);
            menuClub.club.getGestionCuerpoTecnico().guardarJSON();
            System.out.println("Miembro agregado correctamente.");
        } catch (ElementoDuplicadoEx | FondoInsuficienteEx | IngresoInvalido e) {
            System.out.println("Error al agregar miembro: " + e.getMessage());
        }
    }


    private void eliminarMiembroCT() {
        try {
            System.out.print("DNI del miembro a eliminar: ");
            String dni = scanner.nextLine();

            menuClub.club.getGestionCuerpoTecnico().eliminarElemento(dni);
            menuClub.club.getGestionCuerpoTecnico().guardarJSON();
            System.out.println("Miembro eliminado correctamente.");

        } catch (AccionImposible e) {
            System.out.println("Error al eliminar miembro: " + e.getMessage());
        }
    }

    private void listarMiembrosCT() {
        ArrayList<CuerpoTecnico> lista = menuClub.club.getGestionCuerpoTecnico().listar();
        if (lista.isEmpty()) {
            System.out.println("No hay miembros del cuerpo técnico.");
            return;
        }
        for (CuerpoTecnico ct : lista) {
            System.out.println("DNI: " + ct.getDni() +
                    " | Nombre: " + ct.getNombre() +
                    " | Apellido: " + ct.getApellido() +
                    " | Puesto: " + ct.getPuesto() +
                    " | Experiencia: " + ct.getAniosExp() + " años" +
                    " | Salario: " + (ct.getContrato() != null ? ct.getContrato().getSalario() : "N/A") +
                    " | Contrato activo: " + (ct.getContrato() != null ? ct.getContrato().isContratoActivo() : "N/A"));
        }
    }

    private void modificarMiembroCT() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            System.out.print("DNI del miembro a modificar: ");
            String dni = scanner.nextLine();

            System.out.print("Nuevo nombre (Enter para mantener actual): ");
            String nombre = scanner.nextLine();
            if (nombre.isEmpty()) nombre = null;

            System.out.print("Nuevo apellido (Enter para mantener actual): ");
            String apellido = scanner.nextLine();
            if (apellido.isEmpty()) apellido = null;

            System.out.print("Nueva fecha de nacimiento (dd/MM/yyyy, Enter para mantener actual): ");
            String fechaStr = scanner.nextLine();
            LocalDate fechaNacimiento = fechaStr.isEmpty() ? null : LocalDate.parse(fechaStr, formatter);

            System.out.print("Nueva nacionalidad (Enter para mantener actual): ");
            String nacionalidad = scanner.nextLine();
            if (nacionalidad.isEmpty()) nacionalidad = null;

            System.out.print("Nuevo puesto (Enter para mantener actual): ");
            String puestoStr = scanner.nextLine();
            Puesto puesto = puestoStr.isEmpty() ? null : Puesto.valueOf(puestoStr.toUpperCase());

            System.out.print("Nueva experiencia años (Enter para mantener actual): ");
            String expStr = scanner.nextLine();
            int aniosExp = expStr.isEmpty() ? -1 : Integer.parseInt(expStr);

            menuClub.club.getGestionCuerpoTecnico().modificarCuerpoTecnico(
                    dni, nombre, apellido, fechaNacimiento, nacionalidad,
                    puesto, aniosExp
            );

            System.out.println("Miembro modificado correctamente.");

        } catch (ElementoInexistenteEx | IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Datos inválidos: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha incorrecto, debe ser dd/MM/yyyy.");
        }
    }

    private void agregarJugadorMenu() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dni = null;
        while (true) {
            System.out.print("DNI: ");
            dni = scanner.nextLine();
            if (!dni.isBlank()) break;
            System.out.println("DNI inválido, intente de nuevo.");
        }

        String nombre = null;
        while (true) {
            System.out.print("Nombre: ");
            nombre = scanner.nextLine();
            if (!nombre.isBlank()) break;
            System.out.println("Nombre inválido, intente de nuevo.");
        }

        String apellido = null;
        while (true) {
            System.out.print("Apellido: ");
            apellido = scanner.nextLine();
            if (!apellido.isBlank()) break;
            System.out.println("Apellido inválido, intente de nuevo.");
        }

        LocalDate fechaNacimiento = null;
        while (true) {
            try {
                System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
                fechaNacimiento = LocalDate.parse(scanner.nextLine(), formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Debe ser dd/MM/yyyy.");
            }
        }

        String nacionalidad = null;
        while (true) {
            System.out.print("Nacionalidad: ");
            nacionalidad = scanner.nextLine();
            if (!nacionalidad.isBlank()) break;
            System.out.println("Nacionalidad inválida, intente de nuevo.");
        }

        int numeroCamiseta = -1;
        while (true) {
            try {
                System.out.print("Número de camiseta (1-99): ");
                numeroCamiseta = Integer.parseInt(scanner.nextLine());
                if (numeroCamiseta >= 1 && numeroCamiseta <= 99) break;
                else System.out.println("Número debe estar entre 1 y 99.");
            } catch (NumberFormatException e) {
                System.out.println("Número inválido, intente de nuevo.");
            }
        }

        double valorJugador = -1;
        while (true) {
            try {
                System.out.print("Valor del jugador: ");
                valorJugador = Double.parseDouble(scanner.nextLine());
                if (valorJugador > 0) break;
                else System.out.println("Valor debe ser mayor a 0.");
            } catch (NumberFormatException e) {
                System.out.println("Número inválido, intente de nuevo.");
            }
        }

        double salario = -1;
        while (true) {
            try {
                System.out.print("Salario: ");
                salario = Double.parseDouble(scanner.nextLine());
                if (salario > 0) break;
                else System.out.println("Salario debe ser mayor a 0.");
            } catch (NumberFormatException e) {
                System.out.println("Número inválido, intente de nuevo.");
            }
        }

        LocalDate fechaInicioContrato = null;
        while (true) {
            try {
                System.out.print("Fecha inicio contrato (dd/MM/yyyy): ");
                fechaInicioContrato = LocalDate.parse(scanner.nextLine(), formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Debe ser dd/MM/yyyy.");
            }
        }

        LocalDate fechaFinContrato = null;
        while (true) {
            try {
                System.out.print("Fecha fin contrato (dd/MM/yyyy): ");
                fechaFinContrato = LocalDate.parse(scanner.nextLine(), formatter);
                if (!fechaFinContrato.isBefore(fechaInicioContrato)) break;
                else System.out.println("Fecha fin no puede ser antes de inicio.");
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Debe ser dd/MM/yyyy.");
            }
        }

        Posicion posicion = null;
        while (true) {
            try {
                System.out.print("Posición (ARQUERO, DEFENSOR, MEDIO, DELANTERO): ");
                posicion = Posicion.valueOf(scanner.nextLine().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Posición inválida, intente de nuevo.");
            }
        }

        // Intento de agregar jugador, corrigiendo solo los campos que fallen
        while (true) {
            try {
                menuClub.club.getGestionJugadores().agregarJugador(
                        dni, nombre, apellido, fechaNacimiento, nacionalidad,
                        numeroCamiseta, valorJugador, salario,
                        fechaInicioContrato, fechaFinContrato, posicion
                );
                break; // si sale bien
            } catch (ElementoDuplicadoEx e) {
                System.out.println("Error: " + e.getMessage());
                System.out.print("Ingrese un nuevo DNI: ");
                dni = scanner.nextLine();
            } catch (FondoInsuficienteEx e) {
                System.out.println("Error: " + e.getMessage());
                while (true) {
                    try {
                        System.out.print("Ingrese un salario menor (o corrija fondos): ");
                        salario = Double.parseDouble(scanner.nextLine());
                        if (salario > 0) break;
                        else System.out.println("Salario debe ser mayor a 0.");
                    } catch (NumberFormatException ex) {
                        System.out.println("Número inválido, intente de nuevo.");
                    }
                }
            } catch (IngresoInvalido e) {
                System.out.println("Error: " + e.getMessage());

                // Según el mensaje de la excepción, reingresar solo el dato que falló
                String msg = e.getMessage().toLowerCase();
                if (msg.contains("numero de camiseta")) {
                    while (true) {
                        try {
                            System.out.print("Número de camiseta (1-99): ");
                            numeroCamiseta = Integer.parseInt(scanner.nextLine());
                            if (numeroCamiseta >= 1 && numeroCamiseta <= 99) break;
                            else System.out.println("Número debe estar entre 1 y 99.");
                        } catch (NumberFormatException ex) {
                            System.out.println("Número inválido, intente de nuevo.");
                        }
                    }
                } else if (msg.contains("fecha de nacimiento")) {
                    while (true) {
                        try {
                            System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
                            fechaNacimiento = LocalDate.parse(scanner.nextLine(), formatter);
                            break;
                        } catch (DateTimeParseException ex) {
                            System.out.println("Formato de fecha incorrecto. Debe ser dd/MM/yyyy.");
                        }
                    }
                } else if (msg.contains("valor del jugador")) {
                    while (true) {
                        try {
                            System.out.print("Valor del jugador: ");
                            valorJugador = Double.parseDouble(scanner.nextLine());
                            if (valorJugador > 0) break;
                            else System.out.println("Valor debe ser mayor a 0.");
                        } catch (NumberFormatException ex) {
                            System.out.println("Número inválido, intente de nuevo.");
                        }
                    }
                } else if (msg.contains("salario")) {
                    while (true) {
                        try {
                            System.out.print("Salario: ");
                            salario = Double.parseDouble(scanner.nextLine());
                            if (salario > 0) break;
                            else System.out.println("Salario debe ser mayor a 0.");
                        } catch (NumberFormatException ex) {
                            System.out.println("Número inválido, intente de nuevo.");
                        }
                    }
                } else if (msg.contains("fecha de fin contrato")) {
                    while (true) {
                        try {
                            System.out.print("Fecha fin contrato (dd/MM/yyyy): ");
                            fechaFinContrato = LocalDate.parse(scanner.nextLine(), formatter);
                            if (!fechaFinContrato.isBefore(fechaInicioContrato)) break;
                            else System.out.println("Fecha fin no puede ser antes de inicio.");
                        } catch (DateTimeParseException ex) {
                            System.out.println("Formato de fecha incorrecto. Debe ser dd/MM/yyyy.");
                        }
                    }
                } else if (msg.contains("dni")) {
                    System.out.print("Ingrese un nuevo DNI: ");
                    dni = scanner.nextLine();
                } else if (msg.contains("nombre")) {
                    System.out.print("Ingrese un nuevo nombre: ");
                    nombre = scanner.nextLine();
                } else if (msg.contains("apellido")) {
                    System.out.print("Ingrese un nuevo apellido: ");
                    apellido = scanner.nextLine();
                } else if (msg.contains("nacionalidad")) {
                    System.out.print("Ingrese una nueva nacionalidad: ");
                    nacionalidad = scanner.nextLine();
                }
            }
        }

        System.out.println("Jugador agregado correctamente.");
    }


    private void eliminarJugadorMenu() {
        try {
            System.out.print("DNI del jugador a eliminar: ");
            String dni = scanner.nextLine();

            menuClub.club.getGestionJugadores().eliminarElemento(dni);

        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarJugadorMenu() {
        try {
            System.out.print("DNI del jugador: ");
            String dni = scanner.nextLine();

            String info = menuClub.club.getGestionJugadores().mostrarJugador(dni);
            System.out.println(info);

        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void actualizarEstadisticasMenu() {
        try {
            System.out.print("DNI del jugador: ");
            String dni = scanner.nextLine();

            System.out.print("Goles: ");
            int goles = Integer.parseInt(scanner.nextLine());

            System.out.print("Asistencias: ");
            int asistencias = Integer.parseInt(scanner.nextLine());

            System.out.print("Vallas invictas (solo arqueros): ");
            int vallas = Integer.parseInt(scanner.nextLine());

            menuClub.club.getGestionJugadores().actualizarEstadisticas(dni, goles, asistencias, vallas);

            System.out.println("Estadísticas actualizadas correctamente.");

        } catch (ElementoInexistenteEx | IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Dato inválido: debe ingresar un número entero.");
        }
    }

    private void listarJugadoresMenu() {
        ArrayList<String> infoJugadores = menuClub.club.getGestionJugadores().listarJugadoresInfo();
        if (infoJugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
            return;
        }
        for (String info : infoJugadores) {
            System.out.println(info);
        }
    }

    public void agregarPartido() {
        LocalDate fecha = pedirFecha("Fecha del partido (yyyy-mm-dd): ");
        boolean esLocal = pedirBoolean("Es local (true/false): ");
        String rival = pedirString("Nombre del rival: ");
        int golesAFavor = pedirEntero("Goles a favor: ", 0, Integer.MAX_VALUE);
        int golesEnContra = pedirEntero("Goles en contra: ", 0, Integer.MAX_VALUE);

        int entradasVendidas = 0;
        double precioEntrada = 0;
        if (esLocal) {
            entradasVendidas = pedirEntero("Entradas vendidas: ", 0, Integer.MAX_VALUE);
            precioEntrada = pedirDouble("Precio de entrada: ", 0, Double.MAX_VALUE);
        }

        boolean agregado = false;
        while (!agregado) {
            try {
                menuClub.club.getGestorPartidos().agregarPartido(
                        fecha, esLocal, rival,
                        golesAFavor, golesEnContra,
                        entradasVendidas, precioEntrada,
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
                );
                System.out.println("Partido agregado correctamente");
                agregado = true;
            } catch (AccionImposible e) {
                System.out.println("Error: esta accion no se puede realizar");

                String msg = e.getMessage().toLowerCase();
                if (msg.contains("rival")) rival = pedirString("Nombre del rival: ");
                else if (msg.contains("goles a favor")) golesAFavor = pedirEntero("Goles a favor: ", 0, Integer.MAX_VALUE);
                else if (msg.contains("goles en contra")) golesEnContra = pedirEntero("Goles en contra: ", 0, Integer.MAX_VALUE);
                else if (msg.contains("entradas")) entradasVendidas = pedirEntero("Entradas vendidas: ", 0, Integer.MAX_VALUE);
                else if (msg.contains("precio")) precioEntrada = pedirDouble("Precio de entrada: ", 0, Double.MAX_VALUE);
                else if (msg.contains("fecha")) fecha = pedirFecha("Fecha del partido (yyyy-mm-dd): ");
            }
        }
    }

    // Helpers robustos
    private LocalDate pedirFecha(String mensaje) {
        LocalDate fecha = null;
        while (fecha == null) {
            System.out.print(mensaje);
            try {
                fecha = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Error: formato de fecha invalido");
            }
        }
        return fecha;
    }

    private boolean pedirBoolean(String mensaje) {
        Boolean valor = null;
        while (valor == null) {
            System.out.print(mensaje);
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("true")) valor = true;
            else if (input.equalsIgnoreCase("false")) valor = false;
            else System.out.println("Error: ingrese true o false");
        }
        return valor;
    }

    private String pedirString(String mensaje) {
        String valor = null;
        while (valor == null || valor.isBlank()) {
            System.out.print(mensaje);
            String input = scanner.nextLine();
            if (!input.isBlank()) valor = input;
            else System.out.println("Error: valor no puede estar vacio");
        }
        return valor;
    }

    private int pedirEntero(String mensaje, int min, int max) {
        Integer valor = null;
        while (valor == null) {
            System.out.print(mensaje);
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) valor = input;
                else System.out.println("Error: valor fuera de rango");
            } catch (NumberFormatException e) {
                System.out.println("Error: numero invalido");
            }
        }
        return valor;
    }

    private double pedirDouble(String mensaje, double min, double max) {
        Double valor = null;
        while (valor == null) {
            System.out.print(mensaje);
            try {
                double input = Double.parseDouble(scanner.nextLine());
                if (input >= min && input <= max) valor = input;
                else System.out.println("Error: valor fuera de rango");
            } catch (NumberFormatException e) {
                System.out.println("Error: numero invalido");
            }
        }
        return valor;
    }


    public void eliminarPartido() {
            try {
                System.out.print("Fecha del partido a eliminar (yyyy-mm-dd): ");
                LocalDate fecha = LocalDate.parse(scanner.nextLine());

                menuClub.club.getGestorPartidos().eliminarElemento(fecha);
                System.out.println("Partido eliminado correctamente");
            } catch (AccionImposible e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Datos invalidos. Intente de nuevo");
            }
        }

        public void existePartido() {
            try {
                System.out.print("Fecha del partido a consultar (yyyy-mm-dd): ");
                LocalDate fecha = LocalDate.parse(scanner.nextLine());

                boolean existe =menuClub.club.getGestorPartidos().existe(fecha);
                System.out.println("El partido " + (existe ? "existe" : "no existe"));
            } catch (ElementoInexistenteEx e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Datos invalidos. Intente de nuevo");
            }
        }

    public void listarPartidos() {
        ArrayList<String> lista = menuClub.club.getGestorPartidos().listarPartidosInfo();
        if (lista.isEmpty()) {
            System.out.println("No hay partidos registrados");
            return;
        }

        System.out.println("Lista de partidos");
        for (String info : lista) {
            System.out.println("- " + info);
        }
    }


}
