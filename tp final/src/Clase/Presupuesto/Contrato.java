package Clase.Presupuesto;

public class Contrato {
    private String dni;
    private final double salario;
    private final int mesesDuracion;
    private final String fechaInicio;
    private final String fechaFin;
    private boolean contratoActivo;

    public Contrato(String dni, double salario, String fechaFin, boolean contratoActivo, String fechaInicio, int mesesDuracion) {
        this.dni = dni;
        this.salario = salario;
        this.fechaFin = fechaFin;
        this.contratoActivo = contratoActivo;
        this.fechaInicio = fechaInicio;
        this.mesesDuracion = mesesDuracion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public double getSalario() {
        return salario;
    }

    public int getMesesDuracion() {
        return mesesDuracion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public boolean isContratoActivo() {
        return contratoActivo;
    }

    public void setContratoActivo(boolean contratoActivo) {
        this.contratoActivo = contratoActivo;
    }

    @Override
    public String toString() {
        return "Clases_Manu.Contrato{" +
                "dni='" + dni + '\'' +
                ", salario=" + salario +
                ", mesesDuracion=" + mesesDuracion +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", contratoActivo=" + contratoActivo +
                '}';
    }
}
