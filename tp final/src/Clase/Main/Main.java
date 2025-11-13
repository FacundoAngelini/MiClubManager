package Clase.Main;

import Clase.Club.Club;
import Clase.Menus.MenuClub;
import enums.Tiposocio;
import exeptions.ElementoDuplicadoEx;
import exeptions.IngresoInvalido;

import java.util.Scanner;
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Club club;

    public static void main(String[] args) {
        try {
            club.getGestionPresupuesto().agregar_fondos(3000000, "Sponsor ropa", "1/2/2025"); // presupuesto inicial
            club.getGestionEstadios().agregarEstadio("Libertad", 50000, "Buenos Aires", 100000); // estadio
            club.getGestionSocios().agregarSocio("65489321", "Ramiro", "Perez", "13/06/2019", "Argentina", true, "13/10/2021", Tiposocio.JUVENIL); // socio
        } catch (Exception e) {
            System.out.println("Error al agregar datos iniciales: " + e.getMessage());
        }

        MenuClub menu = new MenuClub();
        menu.mostrarMenuPrincipal();
    }

}
