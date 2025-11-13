package Clase.Menus;

import java.util.ArrayList;
import java.util.Scanner;

import Clase.Gestiones.GestionJugadores;
import Clase.Gestiones.GestionPresupuesto;
import Clase.Gestiones.GestorPartido;
import Clase.Partidos.Partido;
import Clase.Persona.Jugador;
import exeptions.AccionImposible;
import exeptions.ElementoDuplicadoEx;
import exeptions.IngresoInvalido;

public class MenuPartido {

    private final GestorPartido gestorPartido;
    private final GestionPresupuesto gestionPresupuesto;
    private final GestionJugadores gestionJugadores;
    private final Scanner scanner;

    public MenuPartido(GestorPartido gestorPartido, GestionPresupuesto gestionPresupuesto, GestionJugadores gestionJugadores) {
        this.gestorPartido = gestorPartido;
        this.gestionPresupuesto = gestionPresupuesto;
        this.gestionJugadores = gestionJugadores;
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
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarPartido();
                    break;
                case 2:
                    eliminarPartido();
                    break;
                case 3:
                    listarPartidos();
                    break;
                case 4:
                    registrarGol();
                    break;
                case 5:
                    registrarTarjeta();
                    break;
                case 6:
                    registrarLesion();
                    break;
                case 7:
                    System.out.println(gestorPartido.resumenResultados());
                    break;
                case 8:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }

    private void agregarPartido() {
        try {
            System.out.print("Fecha (dd/mm/yyyy): ");
            String fecha = scanner.nextLine();
            System.out.print("¿Es local? (true/false): ");
            boolean esLocal = Boolean.parseBoolean(scanner.nextLine());
            System.out.print("Rival: ");
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
                System.out.print("Precio entrada: ");
                precioEntrada = Double.parseDouble(scanner.nextLine());
            }

            gestorPartido.agregarPartido(fecha, esLocal, rival, golesAFavor, golesEnContra,
                    entradasVendidas, precioEntrada, gestionPresupuesto);

            gestorPartido.guardarJSON();
            System.out.println("Clases_Manu.Partido agregado correctamente.");

        } catch (IngresoInvalido | ElementoDuplicadoEx e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: valor numérico inválido.");
        }
    }

    private void eliminarPartido() {
        try {
            System.out.print("Fecha del partido a eliminar: ");
            String fecha = scanner.nextLine();
            gestorPartido.eliminarElemento(fecha);
            gestorPartido.guardarJSON();
            System.out.println("Clases_Manu.Partido eliminado correctamente.");
        } catch (AccionImposible e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarPartidos() {
        ArrayList<Partido> lista = gestorPartido.listar();
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
            return gestionJugadores.devuelveElemento(dni);
        } catch (AccionImposible e) {
            return null;
        }
    }

    private void registrarGol() {
        System.out.print("Fecha del partido: ");
        String fecha = scanner.nextLine();
        Partido partido = gestorPartido.buscarPorFecha(fecha);
        if (partido == null) {
            System.out.println("Clases_Manu.Partido no encontrado.");
            return;
        }

        System.out.print("DNI del jugador: ");
        String dni = scanner.nextLine();
        Jugador jugador = buscarJugadorPorDNI(dni);
        if (jugador == null) {
            System.out.println("Clases_Manu.Jugador no encontrado.");
            return;
        }

        System.out.print("¿Fue local? (true/false): ");
        boolean esLocal = Boolean.parseBoolean(scanner.nextLine());

        gestorPartido.registrarGol(fecha, jugador, esLocal);
        gestorPartido.guardarJSON();
        System.out.println("Gol registrado correctamente.");
    }

    private void registrarTarjeta() {
        System.out.print("Fecha del partido: ");
        String fecha = scanner.nextLine();
        Partido partido = gestorPartido.buscarPorFecha(fecha);
        if (partido == null) {
            System.out.println("Clases_Manu.Partido no encontrado.");
            return;
        }

        System.out.print("DNI del jugador: ");
        String dni = scanner.nextLine();
        Jugador jugador = buscarJugadorPorDNI(dni);
        if (jugador == null) {
            System.out.println("Clases_Manu.Jugador no encontrado.");
            return;
        }

        System.out.print("Tipo de tarjeta (Amarilla/Roja): ");
        String tipo = scanner.nextLine();

        gestorPartido.registrarTarjeta(fecha, jugador, tipo);
        gestorPartido.guardarJSON();
        System.out.println("Tarjeta registrada correctamente.");
    }

    private void registrarLesion() {
        System.out.print("Fecha del partido: ");
        String fecha = scanner.nextLine();
        Partido partido = gestorPartido.buscarPorFecha(fecha);
        if (partido == null) {
            System.out.println("Clases_Manu.Partido no encontrado.");
            return;
        }

        System.out.print("DNI del jugador lesionado: ");
        String dni = scanner.nextLine();
        Jugador jugador = buscarJugadorPorDNI(dni);
        if (jugador == null) {
            System.out.println("Clases_Manu.Jugador no encontrado.");
            return;
        }

        gestorPartido.registrarLesion(fecha, jugador);
        gestorPartido.guardarJSON();
        System.out.println("Lesión registrada correctamente.");
    }
}
