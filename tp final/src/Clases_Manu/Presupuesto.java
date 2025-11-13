package Clases_Manu;

import exeptions.FondoInsuficienteEx;

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

    public void aniadir_monto(double monto){
        this.presupuesto+=monto;
    }

    public void quitar_fondos(double monto)  throws FondoInsuficienteEx
    {
        if(this.presupuesto<monto){
           throw new FondoInsuficienteEx("Clases_Manu.Presupuesto insuficiente");
        }
        this.presupuesto-=monto;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Clases_Manu.Presupuesto{");
        sb.append("presupuesto=").append(presupuesto);
        sb.append('}');
        return sb.toString();
    }
}

