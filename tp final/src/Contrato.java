public class Contrato {
    private int idContrato;
    private double salario;
    private int mesesDuracion;
    private String fechaInicio;
    private String fechaFin;
    private boolean contratoActivo;

    public Contrato(int idContrato, double salario, String fechaFin, boolean contratoActivo, String fechaInicio, int mesesDuracion) {
        this.idContrato = idContrato;
        this.salario = salario;
        this.fechaFin = fechaFin;
        this.contratoActivo = contratoActivo;
        this.fechaInicio = fechaInicio;
        this.mesesDuracion = mesesDuracion;
    }

    public boolean estaVigente() {
        String hoy = java.time.LocalDate.now().toString();
        return hoy.compareTo(fechaInicio) >= 0 && hoy.compareTo(fechaFin) <= 0;
    }

    public double obtenerSalario()
    {
        if(estaVigente()) {
            return salario;
        }else {
            return 0;
        }
    }
}
