package Clases_Manu;

import java.util.Scanner;

public class MenuClub {

    private final Club club;
    private final Scanner scanner;

    public MenuClub(Club club) {
        this.club = club;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuPrincipal() {
        boolean salir = false;

        while (!salir) {
            System.out.println("----- MENU PRINCIPAL DEL CLUB -----");
            System.out.println("1. Gestion de Socios");
            System.out.println("2. Gestion de Jugadores");
            System.out.println("3. Gestion de Partidos");
            System.out.println("4. Gestion de Cuerpo Tecnico");
            System.out.println("5. Gestion de Clases_Manu.Estadio");
            System.out.println("6. Gestion de Clases_Manu.Presupuesto");
            System.out.println("7. Gestion de Clases_Manu.Inventario");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    MenuSocios menuSocios = new MenuSocios(club.getGestionSocios());
                    menuSocios.mostrarMenuSocios();
                    break;
                case 2:
                    MenuJugadores menuJugadores = new MenuJugadores(club.getGestionJugadores());
                    menuJugadores.mostrarMenuJugadores();
                    break;
                case 3:
                    MenuPartido menuPartidos = new MenuPartido(club.getGestorPartidos(), club.getGestionPresupuesto(),club.getGestionJugadores());
                    menuPartidos.mostrarMenuPartidos();
                    break;
                case 4:
                    MenuCuerpoTecnico menuCuerpo = new MenuCuerpoTecnico(club.getGestionCuerpoTecnico());
                    menuCuerpo.mostrarMenuCuerpoTecnico();
                    break;
                case 5:
                    MenuEstadio menuEstadio = new MenuEstadio(club.getGestionEstadios());
                    menuEstadio.mostrarMenuEstadio();
                    break;
                case 6:
                    MenuPresupuesto menuPresupuesto = new MenuPresupuesto(club.getGestionPresupuesto());
                    menuPresupuesto.mostrarMenuPresupuesto();
                    break;
                case 7:
                    MenuInventario<Producto> menu = new MenuInventario<>();
                    menu.mostrarMenuInventario();
                    break;
                case 8:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida, intente de nuevo");
            }
        }
    }
}
