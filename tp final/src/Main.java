import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char control = 's';
        int opcion;
        System.out.println("Bienvenido a la gestion del club.\n");
        while (control == 's') {
            System.out.println("MENU:" +
                    "\n1- SOCIOS" +
                    "\n2- PLANTEL" +
                    "\n3-PRESUPUESTO" +
                    "\n4-PARTIDOS\n");
            opcion=sc.nextInt();
            switch(opcion){
                case 1:
                    System.out.println("\n1-AGREGAR SOCIO" +
                            "\n2-QUITAR SOCIO" +
                            "\n3-MODIFICAR SOCIO" +
                            "\n4-VERIFICAR EXISTENCIA" +
                            "\n5-MOSTRAR SOCIOS" +");
            }
        }
    }

}
