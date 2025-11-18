package Clase.Main;

import Clase.Club.Club;
import Clase.Menus.MenuClub;
import enums.Tiposocio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {

            Club club = new Club();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            try {
                LocalDate fechaFondos = LocalDate.of(2024, 10, 1); // Octubre 2024
                club.getGestionPresupuesto().agregarFondos(3_000_000, "Sponsor ropa", fechaFondos);
                club.getGestionEstadios().agregarEstadio("Libertad", 50_000, "Buenos Aires", 100_000);
                LocalDate fechaNacimiento = LocalDate.parse("13/06/2019", formatter);
                LocalDate fechaAlta = LocalDate.parse("13/10/2021", formatter);
                club.getGestionSocios().agregarSocio("65489321", "Ramiro", "Perez", fechaNacimiento, "Argentina",  fechaAlta, Tiposocio.JUVENIL);
            } catch (Exception e) {
                System.out.println("Error al agregar datos iniciales: " + e.getMessage());
            }
            MenuClub menu = new MenuClub(club);
            menu.iniciarSistema();
        }
    }
