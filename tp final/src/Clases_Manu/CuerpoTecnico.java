package Clases_Manu;

import enums.Puesto;

public class CuerpoTecnico extends Persona {
    private Contrato contrato;
    private Puesto puesto;
    private int aniosExp;

    public CuerpoTecnico(String dni, String nombre, String apellido, String fechaNacimeiento, String nacionalidad, Contrato contrato, Puesto puesto, int aniosExp) {
        super(dni, nombre, apellido, fechaNacimeiento, nacionalidad);
        this.contrato = contrato;
        this.puesto = puesto;
        this.aniosExp = aniosExp;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public int getAniosExp() {
        return aniosExp;
    }

    public void setAniosExp(int aniosExp) {
        this.aniosExp = aniosExp;
    }

    public String obtenerDatos() {
        return "Clases_Manu.CuerpoTecnico{" +
                "contrato=" + contrato +
                ", puesto=" + puesto +
                ", aniosExp=" + aniosExp +
                "} " + super.toString();
    }

    @Override
    public String toString() {
        return obtenerDatos();
    }


}
