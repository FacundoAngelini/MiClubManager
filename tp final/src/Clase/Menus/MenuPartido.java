package Clase.Menus;

import java.util.ArrayList;
import java.util.Scanner;

import Clase.Partidos.Partido;
import Clase.Persona.Jugador;
import exeptions.*;

public class MenuPartido {
    private final MenuClub menuClub;
    private final Scanner scanner;

    public MenuPartido(MenuClub menuClub) {
        this.menuClub = menuClub;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuPartidos() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n----- MENU PARTIDOS -----");
            System.out.println("1. Agregar partido");
            System.out.println("2. Eliminar partido");
            System.out.println("3. Listar partidos");
            System.out.println("4. Registrar gol");
            System.out.println("5. Registrar tarjeta");
            System.out.println("6. Registrar lesión");
            System.out.println("7. Resumen resultados");
            System.out.println("8. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1 -> agregarPartido();
                case 2 -> eliminarPartido();
                case 3 -> listarPartidos();
                case 4 -> registrarGol();
                case 5 -> registrarTarjeta();
                case 6 -> registrarLesion();
                case 7 -> System.out.println(menuClub.club.getGestorPartidos().resumenResultados());
                case 8 -> salir = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void agregarPartido() {
        try {
            System.out.print("Fecha (dd/mm/yyyy): ");
            String fecha = scanner.nextLine().trim();

            System.out.print("¿Es local? (true/false): ");
            boolean esLocal = Boolean.parseBoolean(scanner.nextLine().trim());

            System.out.print("Rival: ");
            String rival = scanner.nextLine().trim();

            System.out.print("Goles a favor: ");
            int golesAFavor = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Goles en contra: ");
            int golesEnContra = Integer.parseInt(scanner.nextLine().trim());

            int entradasVendidas = 0;
            double precioEntrada = 0;
            if (esLocal) {
                System.out.print("Entradas vendidas: ");
                entradasVendidas = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("Precio entrada: ");
                precioEntrada = Double.parseDouble(scanner.nextLine().trim());
            }

            menuClub.club.getGestorPartidos().agregarPartido(fecha, esLocal, rival,
                    golesAFavor, golesEnContra, entradasVendidas, precioEntrada);

            System.out.println("Partido agregado y guardado correctamente.");

        } catch (IngresoInvalido | ElementoDuplicadoEx e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: valor numérico inválido.");
        } catch (Exception e) {
            System.out.println("Error inesperado al agregar el partido: " + e.getMessage());
        }
    }

    private void eliminarPartido() {
        try {
            System.out.print("Fecha del partido a eliminar: ");
            String fecha = scanner.nextLine().trim();
            menuClub.club.getGestorPartidos().eliminarElemento(fecha);
            System.out.println("Partido eliminado correctamente.");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al eliminar partido: " + e.getMessage());
        }
    }

    private void listarPartidos() {
        ArrayList<Partido> lista = menuClub.club.getGestorPartidos().listar();
        if (lista.isEmpty()) {
            System.out.println("No hay partidos registrados.");
            return;
        }
        for (Partido p : lista) {
            System.out.println("Fecha: " + p.getFecha() +
                    " | Rival: " + p.getRival() +
                    " | Local: " + p.isEsLocal() +
                    " | Resultado: " + (p.gano() ? "Victoria" : p.empato() ? "Empate" : "Derrota") +
                    " | Recaudación: " + p.calcularRecaudacion());
        }
    }

    private Jugador buscarJugadorPorDNI(String dni) {
        try {
            return menuClub.club.getGestionJugadores().devuelveElemento(dni);
        } catch (AccionImposible e) {
            return null;
        }
    }

    private void registrarGol() {
        try {
            System.out.print("Fecha del partido: ");
            String fecha = scanner.nextLine().trim();
            Partido partido = menuClub.club.getGestorPartidos().buscarPorFecha(fecha);
            if (partido == null) throw new ElementoInexistenteEx("Partido no encontrado.");

            System.out.print("DNI del jugador: ");
            String dni = scanner.nextLine().trim();
            Jugador jugador = buscarJugadorPorDNI(dni);
            if (jugador == null) throw new ElementoInexistenteEx("Jugador no encontrado.");

            menuClub.club.getGestorPartidos().registrarGol(fecha, jugador, partido.isEsLocal());
            System.out.println("Gol registrado correctamente.");

        } catch (Exception e) {
            System.out.println("Error inesperado al registrar gol: " + e.getMessage());
        }
    }

    private void registrarTarjeta() {
        try {
            System.out.print("Fecha del partido: ");
            String fecha = scanner.nextLine().trim();
            Partido partido = menuClub.club.getGestorPartidos().buscarPorFecha(fecha);
            if (partido == null) throw new ElementoInexistenteEx("Partido no encontrado.");

            System.out.print("DNI del jugador: ");
            String dni = scanner.nextLine().trim();
            Jugador jugador = buscarJugadorPorDNI(dni);
            if (jugador == null) throw new ElementoInexistenteEx("Jugador no encontrado.");

            System.out.print("Tipo de tarjeta (Amarilla/Roja): ");
            String tipo = scanner.nextLine().trim();

            menuClub.club.getGestorPartidos().registrarTarjeta(fecha, jugador, tipo);
            System.out.println("Tarjeta registrada correctamente.");

        } catch (Exception e) {
            System.out.println("Error inesperado al registrar tarjeta: " + e.getMessage());
        }
    }

    private void registrarLesion() {
        try {
            System.out.print("Fecha del partido: ");
            String fecha = scanner.nextLine().trim();
            Partido partido = menuClub.club.getGestorPartidos().buscarPorFecha(fecha);
            if (partido == null) throw new ElementoInexistenteEx("Partido no encontrado.");

            System.out.print("DNI del jugador lesionado: ");
            String dni = scanner.nextLine().trim();
            Jugador jugador = buscarJugadorPorDNI(dni);
            if (jugador == null) throw new ElementoInexistenteEx("Jugador no encontrado.");

            menuClub.club.getGestorPartidos().registrarLesion(fecha, jugador);
            System.out.println("Lesión registrada correctamente.");

        } catch (Exception e) {
            System.out.println("Error inesperado al registrar lesión: " + e.getMessage());
        }
    }
}
