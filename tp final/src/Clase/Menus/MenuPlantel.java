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
        try {
            System.out.print("DNI: ");
            String dni = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
            LocalDate fechaNacimiento = LocalDate.parse(scanner.nextLine(), formatter);

            System.out.print("Nacionalidad: ");
            String nacionalidad = scanner.nextLine();

            System.out.print("Salario: ");
            double salario = Double.parseDouble(scanner.nextLine());

            System.out.print("Fecha inicio contrato (dd/MM/yyyy): ");
            LocalDate fechaInicio = LocalDate.parse(scanner.nextLine(), formatter);

            System.out.print("Fecha fin contrato (dd/MM/yyyy): ");
            LocalDate fechaFin = LocalDate.parse(scanner.nextLine(), formatter);

            System.out.print("Puesto (DT, AYUDANTE, PREPARADOR, FISIOTERAPEUTA): ");
            String inputPuesto = scanner.nextLine().trim().toUpperCase();
            Puesto puesto = Puesto.valueOf(inputPuesto);

            System.out.print("Años de experiencia: ");
            int aniosExp = Integer.parseInt(scanner.nextLine());

            menuClub.club.getGestionCuerpoTecnico().agregarElemento(dni, nombre, apellido, fechaNacimiento, nacionalidad, salario, fechaInicio, fechaFin, puesto, aniosExp);

            menuClub.club.getGestionCuerpoTecnico().aplicarGastoSalarios(fechaInicio);
            menuClub.club.getGestionCuerpoTecnico().guardarJSON();
            System.out.println("Miembro agregado correctamente.");

        } catch (ElementoDuplicadoEx | FondoInsuficienteEx | IngresoInvalido e) {
            System.out.println("Error al agregar miembro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error de datos: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha incorrecto. Debe ser dd/MM/yyyy.");
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

        } catch (ElementoInexistenteEx e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Datos inválidos: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha incorrecto, debe ser dd/MM/yyyy.");
        }
    }

    private void agregarJugadorMenu() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            System.out.print("DNI: ");
            String dni = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
            LocalDate fechaNacimiento = LocalDate.parse(scanner.nextLine(), formatter);

            System.out.print("Nacionalidad: ");
            String nacionalidad = scanner.nextLine();

            System.out.print("Número de camiseta (1-99): ");
            int numeroCamiseta = Integer.parseInt(scanner.nextLine());

            System.out.print("Valor del jugador: ");
            double valorJugador = Double.parseDouble(scanner.nextLine());

            System.out.print("Salario: ");
            double salario = Double.parseDouble(scanner.nextLine());

            System.out.print("Fecha inicio contrato (dd/MM/yyyy): ");
            LocalDate fechaInicioContrato = LocalDate.parse(scanner.nextLine(), formatter);

            System.out.print("Fecha fin contrato (dd/MM/yyyy): ");
            LocalDate fechaFinContrato = LocalDate.parse(scanner.nextLine(), formatter);

            System.out.print("Posición (ARQUERO, DEFENSOR, MEDIO, DELANTERO): ");
            Posicion posicion = Posicion.valueOf(scanner.nextLine().toUpperCase());

            menuClub.club.getGestionJugadores().agregarJugador(
                    dni, nombre, apellido, fechaNacimiento, nacionalidad,
                    numeroCamiseta, valorJugador, salario,
                    fechaInicioContrato, fechaFinContrato, posicion
            );

        } catch (ElementoDuplicadoEx | IngresoInvalido | FondoInsuficienteEx e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Dato inválido: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha incorrecto. Debe ser dd/MM/yyyy.");
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
            try {
                System.out.print("Fecha del partido (yyyy-mm-dd): ");
                LocalDate fecha = LocalDate.parse(scanner.nextLine());

                System.out.print("Es local (true/false): ");
                boolean esLocal = Boolean.parseBoolean(scanner.nextLine());

                System.out.print("Nombre del rival: ");
                String rival = scanner.nextLine();

                System.out.print("Goles a favor: ");
                int golesAFavor = Integer.parseInt(scanner.nextLine());

                System.out.print("Goles en contra: ");
                int golesEnContra = Integer.parseInt(scanner.nextLine());

                int entradasVendidas = 0;
                double precioEntrada = 0;
                if (esLocal) {
                    System.out.print("Entradas vendidas: ");
                    entradasVendidas = Integer.parseInt(scanner.nextLine());

                    System.out.print("Precio de entrada: ");
                    precioEntrada = Double.parseDouble(scanner.nextLine());
                }

                menuClub.club.getGestorPartidos().agregarPartido(fecha, esLocal, rival,
                        golesAFavor, golesEnContra,
                        entradasVendidas, precioEntrada,
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

                System.out.println("Partido agregado correctamente");

            } catch (AccionImposible e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Datos invalidos. Intente de nuevo");
            }
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
