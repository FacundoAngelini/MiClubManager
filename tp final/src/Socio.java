public class Socio extends Persona {
    private int numeroSocio;
    private Tiposocio tiposocio;
    private String fechaAlta;
    private boolean cuotaAlDia;

    public Socio(String dni, String nombre, String apellido, String fechaNacimiento, String nacionalidad, int numeroSocio, boolean cuotaAlDia, String fechaAlta, Tiposocio tiposocio) {
        super(dni, nombre, apellido, fechaNacimiento, nacionalidad);
        this.numeroSocio = numeroSocio;
        this.tiposocio = (tiposocio != null) ? tiposocio : Tiposocio.ACTIVO;        this.fechaAlta = fechaAlta;  // seria la fecha en cuando se hace socio
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

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Tiposocio getTiposocio() {
        return tiposocio;
    }

    public void setTiposocio(Tiposocio tiposocio) {
        if(tiposocio == null) {
            throw new IllegalArgumentException("Tiposocio no puede ser null");
        }
        this.tiposocio = tiposocio;
    }

   public void cambiarAactivo() throws AccionImposible{
        if(tiposocio == Tiposocio.ACTIVO){
            throw new  AccionImposible("El socio ya esta activo");
        }
        tiposocio = Tiposocio.ACTIVO;
   }

   public void cambiarAvitalicio() throws AccionImposible
   {
       if(this.tiposocio == Tiposocio.VITALICIO){
           throw new AccionImposible("El socio ya es vitalicio");
       }
       this.tiposocio= Tiposocio.VITALICIO;
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
