import java.time.LocalDate;

public class Socio extends Persona {
    private int numeroSocio;
    private Tiposocio tiposocio;
    private LocalDate fechaAlta;
    private boolean cuotaAlDia;

    public Socio(String dni, String nombre, String apellido, String fechaNacimeiento, String nacionalidad, int numeroSocio, boolean cuotaAlDia, LocalDate fechaAlta, Tiposocio tiposocio) {
        super(dni, nombre, apellido, fechaNacimeiento, nacionalidad);
        this.numeroSocio = numeroSocio;
        this.cuotaAlDia = true;
        this.fechaAlta = fechaAlta;  // seria la fecha en cuando se hace socio
        this.tiposocio = tiposocio;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public boolean isCuotaAlDia() {
        return cuotaAlDia;
    }

    public void setCuotaAlDia(boolean cuotaAlDia) {
        this.cuotaAlDia = cuotaAlDia;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Tiposocio getTiposocio() {
        return tiposocio;
    }

    public void setTiposocio(Tiposocio tiposocio) {
        this.tiposocio = tiposocio;
    }


    @Override
    public void obtenerDatos() {
        System.out.println("Socio{" +
                "numeroSocio=" + numeroSocio +
                ", tiposocio=" + tiposocio +
                ", fechaAlta=" + fechaAlta +
                ", cuotaAlDia=" + cuotaAlDia +
                '}');
    }

    public double obtenerMontoRecaudado() // para saber la cantidad de plata que le ingresa al club, se necesitan saber los socios que esatn cin la cuota al dia
    {
        if(cuotaAlDia){
            return tiposocio.getPrecio();
        }
        else {
            return 0;
        }
    }
}
