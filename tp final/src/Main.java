public class Main {
    public static void main(String[] args) {
        double presupuesto_inicial =0;
        Club club = new Club(presupuesto_inicial);
        club.getGestionPresupuesto().agregar_fondos(500000, "Sponsor ropa", "1/2/2025");

    }

}
