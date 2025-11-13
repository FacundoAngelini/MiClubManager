import enums.Tiposocio;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double presupuesto_inicial =0;
        Scanner sc = new Scanner(System.in);
        Club club = new Club(presupuesto_inicial);
        club.getGestionPresupuesto().agregar_fondos(3000000, "Sponsor ropa", "1/2/2025"); // agrego un presupuesto y recibe eso por sponsor de camiseta
        club.getGestionEstadios().agregarEstadio("Libertad", 50000, "Buenos aires", 100000); // ageego estadio
        club.getGestionSocios().agregarSocio("65489321","Ramiro", "perez", "13/06/2019","Argentina", true, "13/10/2021", Tiposocio.JUVENIL);


    }

}
