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
import java.time.Period;

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
            System.out.println("12. Mostrar partidos");
            System.out.println("13. Ver estadsiticas de los partidos");
            System.out.println("14. Salir del menu Plantel");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero valido");
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
                case 12 -> listarPartidos();
                case 13 -> verEstadisticas();
                case 14 -> salir = true;
                default -> System.out.println("Opcion invalida");
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
            System.out.print("DNI ");
            String input = scanner.nextLine();
            if (input.matches("\\d+")) dni = input;
            else System.out.println("DNI invalido solo numeros");
        }

        while (nombre == null) {
            System.out.print("Nombre ");
            String input = scanner.nextLine();
            if (input.matches("[a-zA-Z]+")) nombre = input;
            else System.out.println("Nombre invalido solo letras");
        }

        while (apellido == null) {
            System.out.print("Apellido ");
            String input = scanner.nextLine();
            if (input.matches("[a-zA-Z]+")) apellido = input;
            else System.out.println("Apellido invalido solo letras");
        }

        while (fechaNacimiento == null) {
            System.out.print("Fecha de nacimiento dd/MM/yyyy ");
            String input = scanner.nextLine();
            try {
                fechaNacimiento = LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto");
            }
        }

        while (nacionalidad == null) {
            System.out.print("Nacionalidad ");
            String input = scanner.nextLine();
            if (input.matches("[a-zA-Z ]+")) nacionalidad = input;
            else System.out.println("Nacionalidad invalida solo letras y espacios");
        }

        while (salario <= 0) {
            System.out.print("Salario ");
            try {
                double input = Double.parseDouble(scanner.nextLine());
                if (input > 0) salario = input;
                else System.out.println("Salario debe ser mayor que cero");
            } catch (NumberFormatException e) {
                System.out.println("Salario invalido debe ser un numero");
            }
        }

        while (fechaInicio == null) {
            System.out.print("Fecha inicio contrato dd/MM/yyyy ");
            try {
                LocalDate input = LocalDate.parse(scanner.nextLine(), formatter);
                if (!input.isAfter(LocalDate.now())) fechaInicio = input;
                else System.out.println("Fecha inicio no puede ser futura");
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto");
            }
        }

        while (fechaNacimiento.plusYears(18).isAfter(fechaInicio)) {
            System.out.println("Debe tener mas de 18 anos al inicio del contrato");
            System.out.print("Fecha de nacimiento dd/MM/yyyy ");
            try {
                fechaNacimiento = LocalDate.parse(scanner.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto");
            }
        }

        int edadActual = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        int maxExp = edadActual - 18;

        while (fechaFin == null) {
            System.out.print("Fecha fin contrato dd/MM/yyyy ");
            try {
                LocalDate input = LocalDate.parse(scanner.nextLine(), formatter);
                if (!input.isBefore(fechaInicio)) fechaFin = input;
                else System.out.println("Fecha fin no puede ser anterior a inicio");
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto");
            }
        }

        while (puesto == null) {
            System.out.print("Puesto DT AYUDANTE PREPARADOR FISIOTERAPEUTA ");
            try {
                puesto = Puesto.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Puesto invalido");
            }
        }

        while (aniosExp < 0 || aniosExp > maxExp) {
            System.out.print("Anios de experiencia max " + maxExp + " ");
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= 0 && input <= maxExp) aniosExp = input;
                else System.out.println("Anios de experiencia no validos");
            } catch (NumberFormatException e) {
                System.out.println("Numero invalido");
            }
        }

        try {
            menuClub.club.getGestionCuerpoTecnico().agregarElemento(dni, nombre, apellido, fechaNacimiento, nacionalidad, salario, fechaInicio, fechaFin, puesto, aniosExp);
            menuClub.club.getGestionCuerpoTecnico().guardarJSON();
            System.out.println("Miembro agregado correctamente");

        } catch (ElementoDuplicadoEx | FondoInsuficienteEx | IngresoInvalido e) {
            System.out.println("Error al agregar miembro " + e.getMessage());
        }
    }


    private void eliminarMiembroCT() {
        try {
            System.out.print("DNI del miembro a eliminar: ");
            String dni = scanner.nextLine();

            menuClub.club.getGestionCuerpoTecnico().eliminarElemento(dni);
            menuClub.club.getGestionCuerpoTecnico().guardarJSON();
            System.out.println("Miembro eliminado correctamente");

        } catch (AccionImposible e) {
            System.out.println("Error al eliminar miembro: " + e.getMessage());
        }
    }

    private void listarMiembrosCT() {
        ArrayList<CuerpoTecnico> lista = menuClub.club.getGestionCuerpoTecnico().listar();
        if (lista.isEmpty()) {
            System.out.println("No hay miembros del cuerpo tecnico");
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
            CuerpoTecnico ctActual = menuClub.club.getGestionCuerpoTecnico().getCuerpoTecnico().get(dni);
            if (ctActual == null) {
                System.out.println("No existe el miembro con DNI: " + dni);
                return;
            }

            System.out.print("Nuevo nombre (Enter para mantener actual): ");
            String nombre = scanner.nextLine();
            if (nombre.isEmpty()) nombre = null;
            else if (!nombre.matches("[a-zA-Z]+")) {
                System.out.println("Nombre invalido");
                return;
            }

            System.out.print("Nuevo apellido (Enter para mantener actual): ");
            String apellido = scanner.nextLine();
            if (apellido.isEmpty()) apellido = null;
            else if (!apellido.matches("[a-zA-Z]+")) {
                System.out.println("Apellido invalido");
                return;
            }

            System.out.print("Nueva fecha de nacimiento (dd/MM/yyyy, Enter para mantener actual): ");
            String fechaStr = scanner.nextLine();
            LocalDate fechaNacimiento = null;
            if (!fechaStr.isEmpty()) {
                try {
                    fechaNacimiento = LocalDate.parse(fechaStr, formatter);
                    if (fechaNacimiento.isAfter(LocalDate.now())) {
                        System.out.println("Fecha de nacimiento invalida");
                        return;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Formato incorrecto de fecha");
                    return;
                }
            }

            System.out.print("Nueva nacionalidad (Enter para mantener actual): ");
            String nacionalidad = scanner.nextLine();
            if (nacionalidad.isEmpty()) nacionalidad = null;
            else if (!nacionalidad.matches("[a-zA-Z ]+")) {
                System.out.println("Nacionalidad invalida");
                return;
            }

            System.out.print("Nuevo puesto (Enter para mantener actual): ");
            String puestoStr = scanner.nextLine();
            Puesto puesto = null;
            if (!puestoStr.isEmpty()) {
                try {
                    puesto = Puesto.valueOf(puestoStr.trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Puesto invalido");
                    return;
                }
            }

            System.out.print("Nueva experiencia años (Enter para mantener actual): ");
            String expStr = scanner.nextLine();
            int aniosExp = -1;
            if (!expStr.isEmpty()) {
                try {
                    aniosExp = Integer.parseInt(expStr);
                    if (aniosExp < 0) {
                        System.out.println("Anios de experiencia invalidos");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido");
                    return;
                }
            }
            LocalDate fechaBase = (fechaNacimiento != null) ? fechaNacimiento : ctActual.getFechaNacimiento();
            if (aniosExp >= 0) {
                int edadActual = Period.between(fechaBase, LocalDate.now()).getYears();
                int maxExp = edadActual - 18;
                if (aniosExp > maxExp) {
                    System.out.println("La experiencia supera la edad permitida");
                    return;
                }
            }

            menuClub.club.getGestionCuerpoTecnico().modificarCuerpoTecnico(dni, nombre, apellido, fechaNacimiento, nacionalidad, puesto, aniosExp);

            System.out.println("Miembro modificado correctamente");

        } catch (ElementoInexistenteEx | IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void agregarJugadorMenu() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            String dni;
            while (true) {
                System.out.print("DNI: ");
                dni = scanner.nextLine();
                if (!dni.isBlank() && dni.matches("\\d+")) break;
                System.out.println("DNI invalido. Debe contener solo numeros");
            }

            String nombre;
            while (true) {
                System.out.print("Nombre: ");
                nombre = scanner.nextLine();
                if (!nombre.isBlank() && nombre.matches("[a-zA-Z]+")) break;
                System.out.println("Nombre invalido. Solo letras permitidas");
            }

            String apellido;
            while (true) {
                System.out.print("Apellido: ");
                apellido = scanner.nextLine();
                if (!apellido.isBlank() && apellido.matches("[a-zA-Z]+")) break;
                System.out.println("Apellido invalido. Solo letras permitidas");
            }

            LocalDate fechaNacimiento;
            while (true) {
                try {
                    System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
                    fechaNacimiento = LocalDate.parse(scanner.nextLine(), formatter);
                    if (!fechaNacimiento.isAfter(LocalDate.now())) break;
                    else System.out.println("Fecha de nacimiento invalida.");
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha incorrecto");
                }
            }

            String nacionalidad;
            while (true) {
                System.out.print("Nacionalidad: ");
                nacionalidad = scanner.nextLine();
                if (!nacionalidad.isBlank() && nacionalidad.matches("[a-zA-Z ]+")) break;
                System.out.println("Nacionalidad invalida");
            }

            int numeroCamiseta;
            while (true) {
                try {
                    System.out.print("Numero de camiseta (1-99): ");
                    numeroCamiseta = Integer.parseInt(scanner.nextLine());
                    if (numeroCamiseta >= 1 && numeroCamiseta <= 99) break;
                    System.out.println("Debe ser un numero entre 1 y 99");
                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido");
                }
            }

            double valorJugador;
            while (true) {
                try {
                    System.out.print("Valor del jugador: ");
                    valorJugador = Double.parseDouble(scanner.nextLine());
                    if (valorJugador > 0) break;
                    System.out.println("Debe ser mayor a 0");
                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido");
                }
            }

            double salario;
            while (true) {
                try {
                    System.out.print("Salario: ");
                    salario = Double.parseDouble(scanner.nextLine());
                    if (salario > 0) break;
                    System.out.println("Debe ser mayor a 0");
                } catch (NumberFormatException e) {
                    System.out.println("Numero invalido");
                }
            }

            LocalDate fechaInicioContrato;
            while (true) {
                try {
                    System.out.print("Fecha inicio contrato (dd/MM/yyyy): ");
                    fechaInicioContrato = LocalDate.parse(scanner.nextLine(), formatter);
                    int edadEnContrato = Period.between(fechaNacimiento, fechaInicioContrato).getYears();
                    if (edadEnContrato >= 18) break;
                    System.out.println("El jugador debe tener al menos 18 anos al iniciar el contrato");
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha incorrecto");
                }
            }

            LocalDate fechaFinContrato;
            while (true) {
                try {
                    System.out.print("Fecha fin contrato (dd/MM/yyyy): ");
                    fechaFinContrato = LocalDate.parse(scanner.nextLine(), formatter);
                    if (!fechaFinContrato.isBefore(fechaInicioContrato)) break;
                    System.out.println("Fecha fin no puede ser antes de inicio");
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha incorrecto");
                }
            }

            Posicion posicion;
            while (true) {
                try {
                    System.out.print("Posicion (ARQUERO, DEFENSOR, MEDIO, DELANTERO): ");
                    posicion = Posicion.valueOf(scanner.nextLine().toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Posicion invalida");
                }
            }

            menuClub.club.getGestionJugadores().agregarJugador(dni, nombre, apellido, fechaNacimiento, nacionalidad, numeroCamiseta, valorJugador, salario, fechaInicioContrato, fechaFinContrato, posicion);

            System.out.println("Jugador agregado correctamente.");

        } catch (ElementoDuplicadoEx | FondoInsuficienteEx | IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        }
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
        String dni;
        int goles = -1, asistencias = -1, vallas = -1;
        while (true) {
            System.out.print("DNI del jugador: ");
            dni = scanner.nextLine();
            if (dni.isBlank()) {
                System.out.println("DNI no puede estar vacio");
                continue;
            }
            if (!dni.matches("\\d+")) {
                System.out.println("DNI invalido, solo numeros permitidos.");
                continue;
            }

            if (!menuClub.club.getGestionJugadores().existeJugador(dni)) {
                System.out.println("No existe jugador con ese DNI");
                continue;
            }


            break;
        }

        while (true) {
            try {
                System.out.print("Goles: ");
                goles = Integer.parseInt(scanner.nextLine());
                if (goles < 0) {
                    System.out.println("Goles no puede ser negativo");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Dato invalido: debe ingresar un numero entero");
            }
        }
        while (true) {
            try {
                System.out.print("Asistencias: ");
                asistencias = Integer.parseInt(scanner.nextLine());
                if (asistencias < 0) {
                    System.out.println("Asistencias no puede ser negativo");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Dato invalido: debe ingresar un numero entero");
            }
        }

        while (true) {
            try {
                System.out.print("Vallas invictas: ");
                vallas = Integer.parseInt(scanner.nextLine());
                if (vallas < 0) {
                    System.out.println("Vallas no puede ser negativo");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Dato invalido: debe ingresar un numero entero");
            }
        }

        try {
            menuClub.club.getGestionJugadores().actualizarEstadisticas(dni, goles, asistencias, vallas);
            System.out.println("Estadisticas actualizadas correctamente");
        } catch (ElementoInexistenteEx | IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void listarJugadoresMenu() {
        ArrayList<String> infoJugadores = menuClub.club.getGestionJugadores().listarJugadoresInfo();
        if (infoJugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados");
            return;
        }
        for (String info : infoJugadores) {
            System.out.println(info);
        }
    }
    public void agregarPartido() {
        LocalDate fecha = null;
        boolean esLocal = false;
        String rival = null;
        int golesAFavor = 0;
        int golesEnContra = 0;
        int entradasVendidas = 0;
        double precioEntrada = 0;

        while (fecha == null) {
            try {
                System.out.print("Ingrese fecha del partido (dd/MM/yyyy): ");
                String input = scanner.nextLine().trim();
                fecha = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (fecha.isAfter(LocalDate.now())) {
                    System.out.println("Error: la fecha no puede ser futura");
                    fecha = null;
                    continue;
                }
                if (menuClub.club.getGestorPartidos().existeInterno(fecha)) {
                    System.out.println("Error: ya existe un partido en esa fecha");
                    fecha = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error: formato de fecha invalido (dd/MM/yyyy)");
            }
        }

        while (true) {
            System.out.print("Es local? (si/no): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("si")) { esLocal = true; break; }
            if (input.equals("no")) { esLocal = false; break; }
            System.out.println("Error: responda si o no");
        }

        while (rival == null) {
            System.out.print("Nombre del rival: ");
            String input = scanner.nextLine().trim();
            if (!input.isBlank()) rival = input;
            else System.out.println("Error: valor no puede estar vacio");
        }

        while (true) {
            try {
                System.out.print("Goles a favor: ");
                String input = scanner.nextLine().trim();
                golesAFavor = Integer.parseInt(input);
                if (golesAFavor < 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero entero >= 0");
            }
        }

        while (true) {
            try {
                System.out.print("Goles en contra: ");
                String input = scanner.nextLine().trim();
                golesEnContra = Integer.parseInt(input);
                if (golesEnContra < 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un numero entero >= 0");
            }
        }

        if (esLocal) {
            int capacidad = menuClub.club.getGestionEstadios().getEstadio().getCapacidad();

            while (true) {
                try {
                    System.out.print("Entradas vendidas: ");
                    String input = scanner.nextLine().trim();
                    entradasVendidas = Integer.parseInt(input);
                    if (entradasVendidas < 0 || entradasVendidas > capacidad)
                        throw new NumberFormatException();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: valor invalido, debe estar entre 0 y " + capacidad);
                }
            }

            while (true) {
                try {
                    System.out.print("Precio de entrada: ");
                    String input = scanner.nextLine().trim();
                    precioEntrada = Double.parseDouble(input);
                    if (precioEntrada < 0) throw new NumberFormatException();
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: ingrese un numero >= 0");
                }
            }
        }
        try {
            menuClub.club.getGestorPartidos().agregarPartido(
                    fecha, esLocal, rival,
                    golesAFavor, golesEnContra,
                    entradasVendidas, precioEntrada
            );
            System.out.println("Partido agregado correctamente");
        } catch (AccionImposible e) {
            System.out.println("Error inesperado al agregar partido: " + e.getMessage());
        }
    }

    public void eliminarPartido() {
        LocalDate fecha = null;
        while (fecha == null) {
            System.out.print("Ingrese fecha del partido a eliminar (dd/MM/yyyy): ");
            String input = scanner.nextLine().trim();
            try {
                fecha = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                menuClub.club.getGestorPartidos().eliminarElemento(fecha);
                System.out.println("Partido eliminado correctamente");
            } catch (DateTimeParseException e) {
                System.out.println("Error: formato invalido (dd/MM/yyyy)");
            } catch (AccionImposible e) {
                System.out.println("Error: " + e.getMessage());
                fecha = null;
            }
        }
    }

    public void listarPartidos() {
        ArrayList<String> lista = menuClub.club.getGestorPartidos().listarPartidosInfo();
        if (lista.isEmpty()) {
            System.out.println("No hay partidos registrados");
            return;
        }
        System.out.println("Lista de partidos:");
        for (String info : lista) System.out.println("- " + info);
    }


    public void verEstadisticas() {
        System.out.println("Estadisticas del club:");
        System.out.println("Ganados: " + menuClub.club.getGestorPartidos().getGanados() + " (" + menuClub.club.getGestorPartidos().getPorcentajeGanados() + "%)");
        System.out.println("Empatados: " + menuClub.club.getGestorPartidos().getEmpatados() + " (" + menuClub.club.getGestorPartidos().getPorcentajeEmpatados() + "%)");
        System.out.println("Perdidos: " + menuClub.club.getGestorPartidos().getPerdidos() + " (" + menuClub.club.getGestorPartidos().getPorcentajePerdidos() + "%)");
    }

}