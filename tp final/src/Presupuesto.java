public class Presupuesto {
    private double presupuesto;

    public Presupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public void añadir_monto(double monto){
        this.presupuesto+=monto;
    }

    public void quitar_fondos(double monto)  throws FondoInsuficienteEx
    {
        if(this.presupuesto<monto){
           throw new FondoInsuficienteEx("Presupuesto insuficiente");
        }
        this.presupuesto-=monto;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Presupuesto{");
        sb.append("presupuesto=").append(presupuesto);
        sb.append('}');
        return sb.toString();
    }
}

