public class GestionEstadio {
    private Estadio estadio;
    private GestionPresupuesto  presupuestoCentral;

    public GestionEstadio(Estadio estadio, GestionPresupuesto presupuestoCentral) {
        this.estadio = estadio;
        this.presupuestoCentral = presupuestoCentral;
    }

    public void pagarMantenimiento(String fecha) throws FondoInsuficienteEx{
        double monto= estadio.getCostoMantenimiento();
        this.presupuestoCentral.agregar_fondos(monto,"Costo de mantenimiento del estadio.",fecha);
    }
    public void modificar_capacidad(int nuevaCapacidad){
        this.estadio.setCapacidad(nuevaCapacidad);
    }
    public void actualizar_costo_mantenimiento(int nuevoCosto){
      this.estadio.setCostoMantenimiento(nuevoCosto);
    }}
