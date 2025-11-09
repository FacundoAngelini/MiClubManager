import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        CuerpoTecnico cuerpoTecnico = new CuerpoTecnico("a","v","c","c","a",new Contrato(1, 2, LocalDate.now(),true,LocalDate.now(),2 ),Puesto.AYUDANTES,2);
        cuerpoTecnico.obtenerDatos();
        Sistema miSistema=new Sistema();
}


}
