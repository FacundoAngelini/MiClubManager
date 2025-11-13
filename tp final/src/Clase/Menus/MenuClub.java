package Clase.Menus;

import Clase.Club.Club;
import java.util.Scanner;

public class MenuClub {

    public Club club;
    private final Scanner scanner;
    private final MenuSocios menuSocios;
    private final MenuJugadores menuJugadores;
    private final MenuPartido menuPartidos;
    private final MenuCuerpoTecnico menuCuerpo;
    private final MenuEstadio menuEstadio;
    private final MenuPresupuesto menuPresupuesto;
    private final MenuInventario menuInventario;

    public MenuClub(Club clubExistente) {
        this.club = clubExistente;
        this.scanner = new Scanner(System.in);

        this.menuSocios = new MenuSocios(this);
        this.menuJugadores = new MenuJugadores(this);
        this.menuPartidos = new MenuPartido(this);
        this.menuCuerpo = new MenuCuerpoTecnico(this);
        this.menuEstadio = new MenuEstadio(this);
        this.menuPresupuesto = new MenuPresupuesto(this);
        this.menuInventario = new MenuInventario(this);
    }

    public void mostrarMenuPrincipal() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n----- MENU PRINCIPAL DEL CLUB -----");
            System.out.println("1. Gestion de Socios");
            System.out.println("2. Gestion de Jugadores");
            System.out.println("3. Gestion de Partidos");
            System.out.println("4. Gestion de Cuerpo Tecnico");
            System.out.println("5. Gestion de Estadio");
            System.out.println("6. Gestion de Presupuesto");
            System.out.println("7. Gestion de Inventario");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opcion: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> menuSocios.mostrarMenuSocios();
                case "2" -> menuJugadores.mostrarMenuJugadores();
                case "3" -> menuPartidos.mostrarMenuPartidos();
                case "4" -> menuCuerpo.mostrarMenuCuerpoTecnico();
                case "5" -> menuEstadio.mostrarMenuEstadio();
                case "6" -> menuPresupuesto.mostrarMenuPresupuesto();
                case "7" -> menuInventario.mostrarMenuInventario();
                case "8" -> {
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                }
                default -> System.out.println("Opcion invalida, intente de nuevo");
            }
        }
    }
}
