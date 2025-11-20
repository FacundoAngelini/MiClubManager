package Clase.Presupuesto;

import exeptions.FondoInsuficienteEx;
import exeptions.IngresoInvalido;

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

    public void aniadirMonto(double monto) throws IngresoInvalido {
        if(monto <= 0) {
            throw new IngresoInvalido("El monto a aniadir debe ser mayor que 0");
        }
        this.presupuesto += monto;
    }

    public void quitarFondos(double monto) throws FondoInsuficienteEx, IngresoInvalido {
        if(monto <= 0) {
            throw new IngresoInvalido("El monto a quitar debe ser mayor que 0");
        }
        if(this.presupuesto < monto) {
            throw new FondoInsuficienteEx("Presupuesto insuficiente");
        }
        this.presupuesto -= monto;
    }

    @Override
    public String toString() {
        return "Presupuesto" +
                "presupuesto=" + presupuesto;
    }
}
