import java.time.LocalDate;

public class Contrato {
    private int idContrato;
    private double salario;
    private int mesesDuracion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Persona persona;
    private boolean contratoActivo;

    public Contrato(int idContrato, double salario, LocalDate fechaFin, Persona persona, boolean contratoActivo, LocalDate fechaInicio, int mesesDuracion) {
        this.idContrato = idContrato;
        this.salario = salario;
        this.fechaFin = fechaFin;
        this.persona = persona;
        this.contratoActivo = contratoActivo;
        this.fechaInicio = fechaInicio;
        this.mesesDuracion = mesesDuracion;
    }

    public boolean estaVigente()
    {
        LocalDate hoy = LocalDate.now();
        if(!hoy.isAfter(fechaFin) &&  hoy.isBefore(fechaInicio))
        {
            return false;
        }
        return true;

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
