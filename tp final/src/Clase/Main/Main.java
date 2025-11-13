package Clase.Main;

import Clase.Club.Club;
import Clase.Menus.MenuClub;
import enums.Tiposocio;

public class Main {

    public static void main(String[] args) {

        Club club = new Club();

        try {
            club.getGestionPresupuesto().agregar_fondos(3000000, "Sponsor ropa", "1/2/2025");
            club.getGestionEstadios().agregarEstadio("Libertad", 50000, "Buenos Aires", 100000);
            club.getGestionSocios().agregarSocio(
                    "65489321", "Ramiro", "Perez",
                    "13/06/2019", "Argentina",
                    true, "13/10/2021",
                    Tiposocio.JUVENIL
            );
        } catch (Exception e) {
            System.out.println("Error al agregar datos iniciales: " + e.getMessage());
        }

        MenuClub menu = new MenuClub(club);
        menu.mostrarMenuPrincipal();
    }
}
