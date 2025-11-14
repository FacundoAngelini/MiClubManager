package Clase.Persona;

import Clase.Presupuesto.Contrato;
import enums.Puesto;
import java.time.LocalDate;

public class CuerpoTecnico extends Persona {
    private Contrato contrato;
    private Puesto puesto;
    private int aniosExp;

    public CuerpoTecnico(String dni, String nombre, String apellido, LocalDate fechaNacimiento,
                         String nacionalidad, Contrato contrato, Puesto puesto, int aniosExp) {
        super(dni, nombre, apellido, fechaNacimiento, nacionalidad);

        if (contrato == null) {
            throw new IllegalArgumentException("El contrato no puede ser nulo.");
        }
        if (aniosExp < 0) {
            throw new IllegalArgumentException("Los años de experiencia no pueden ser negativos.");
        }

        this.contrato = contrato;
        this.puesto = puesto;
        this.aniosExp = aniosExp;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        if (contrato == null) {
            throw new IllegalArgumentException("El contrato no puede ser nulo.");
        }
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
        if (aniosExp < 0) {
            throw new IllegalArgumentException("Los años de experiencia no pueden ser negativos.");
        }
        this.aniosExp = aniosExp;
    }

    public String obtenerDatos() {
        return "CuerpoTecnico{" +
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
