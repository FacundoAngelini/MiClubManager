import java.util.ArrayList;
import java.util.List;

public class FichaDelPartido {
    private int golesLocal;
    private int golesVisitante;
    private String resumen;
    private List<String> sanciones;

    public FichaDelPartido(int golesLocal, int golesVisitante, String resumen, List sanciones) {
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.resumen = resumen;
        this.sanciones = new ArrayList<>();
    }
  //EL STRING RESUMEN LLEVA: LESIONADOS
}
