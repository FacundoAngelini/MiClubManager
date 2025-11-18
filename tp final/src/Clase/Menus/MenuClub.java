package Clase.Menus;
import Clase.Club.Club;
import Clase.Gestiones.GestorUsuarios;
import enums.Rol;
import exeptions.IngresoInvalido;

import java.util.Scanner;
public class MenuClub {

    public Club club;
    private final Scanner scanner;

    private final MenuPlantel menuPlantel;
    private final MenuPresupuesto menuPresupuesto;
    private final MenuGeneralClub menuGeneralClub;
    private final GestorUsuarios gestorUsuarios;

    public MenuClub(Club clubExistente) {

        this.club = clubExistente;
        this.scanner = new Scanner(System.in);

        this.menuPlantel = new MenuPlantel(this);
        this.menuPresupuesto = new MenuPresupuesto(this);
        this.menuGeneralClub = new MenuGeneralClub(this);

        this.gestorUsuarios = new GestorUsuarios();
    }

    public void iniciarSistema() {

        while (true) {
            System.out.println("\n--- BIENVENIDO AL SISTEMA DEL CLUB ---");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesion");
            System.out.println("3. Salir");
            System.out.print("Opcion: ");

            String op = scanner.nextLine();

            switch (op) {
                case "1" -> registrarUsuario();
                case "2" -> loginUsuario();
                case "3" -> {
                    System.out.println("Saliendo...");
                    return;
                }
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private void registrarUsuario() {
        try {
            System.out.print("Ingrese DNI: ");
            String dni = scanner.nextLine();

            System.out.print("Ingrese nombre: ");
            String nombre = scanner.nextLine();

            System.out.println("Seleccione rol:");
            System.out.println("1. Plantel");
            System.out.println("2. Presupuesto");
            System.out.println("3. General del Club");
            System.out.print("Opcion: ");
            String r = scanner.nextLine();

            Rol rol;
            switch (r) {
                case "1" -> rol = Rol.PLANTEL;
                case "2" -> rol = Rol.PRESUPUESTO;
                case "3" -> rol = Rol.GENERAL_CLUB;
                default -> throw new IngresoInvalido("rol invalido");
            }

            gestorUsuarios.registrar(dni, nombre, rol);
            System.out.println("Usuario registrado correctamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void loginUsuario() {
        try {
            System.out.print("Ingrese DNI: ");
            String dni = scanner.nextLine();

            UsuarioSistema u = gestorUsuarios.login(dni);

            System.out.println("Bienvenido " + u.getNombre() + ". Rol: " + u.getRol());

            switch (u.getRol()) {
                case PLANTEL -> menuPlantel.mostrarMenuPlantel();
                case PRESUPUESTO -> menuPresupuesto.mostrarMenuPresupuesto();
                case GENERAL_CLUB -> menuGeneralClub.mostrarMenuGeneral();
            }

        } catch (IngresoInvalido e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
