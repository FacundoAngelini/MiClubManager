public class Contrato {
    private String dni;
    private double salario;
    private int mesesDuracion;
    private String fechaInicio;
    private String fechaFin;
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

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getMesesDuracion() {
        return mesesDuracion;
    }

    public void setMesesDuracion(int mesesDuracion) {
        this.mesesDuracion = mesesDuracion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isContratoActivo() {
        return contratoActivo;
    }

    public void setContratoActivo(boolean contratoActivo) {
        this.contratoActivo = contratoActivo;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "dni='" + dni + '\'' +
                ", salario=" + salario +
                ", mesesDuracion=" + mesesDuracion +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", contratoActivo=" + contratoActivo +
                '}';
    }
}
