package Clase.Persona;

import enums.Tiposocio;
import exeptions.AccionImposible;

public class Socio extends Persona {
    private static int contador =1;
    private int numeroSocio;
    private Tiposocio tiposocio;
    private String fechaAlta;
    private boolean cuotaAlDia;

    public Socio(String dni, String nombre, String apellido, String fechaNacimiento, String nacionalidad, boolean cuotaAlDia, String fechaAlta, Tiposocio tiposocio) {
        super(dni, nombre, apellido, fechaNacimiento, nacionalidad);
        if (dni == null || dni.isEmpty())
            throw new IllegalArgumentException("El DNI no puede ser nulo o vacío.");
        if (fechaAlta == null || fechaAlta.isEmpty())
            throw new IllegalArgumentException("La fecha de alta no puede ser nula o vacía.");
        this.numeroSocio = contador++;
        this.tiposocio = (tiposocio != null) ? tiposocio : Tiposocio.ACTIVO;
        this.fechaAlta = fechaAlta;  // seria la fecha en cuando se hace socio
        this.cuotaAlDia = cuotaAlDia;
    }

    protected void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public Tiposocio getTiposocio() {
        return tiposocio;
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
        if (fechaAlta == null || fechaAlta.isEmpty()) {
            throw new IllegalArgumentException("La fecha de alta no puede ser vacía.");
        }
        this.fechaAlta = fechaAlta;
    }

    public Tiposocio getTipoSocio() {
        return tiposocio;
    }

    public void setTipoSocio(Tiposocio tipoSocio) {
        if (tipoSocio == null) {
            throw new IllegalArgumentException("El tipo de socio no puede ser null.");
        }
        this.tiposocio = tipoSocio;
    }

    public void cambiarAActivo() throws AccionImposible {
        if (tiposocio == Tiposocio.ACTIVO) {
            throw new AccionImposible("El socio ya está activo.");
        }
        this.tiposocio = Tiposocio.ACTIVO;
    }

    public void cambiarAVitalicio() throws AccionImposible {
        if (tiposocio == Tiposocio.VITALICIO) {
            throw new AccionImposible("El socio ya es vitalicio.");
        }
        this.tiposocio = Tiposocio.VITALICIO;
    }


    public double obtenerMontoRecaudado() {
        return cuotaAlDia ? tiposocio.getPrecio() : 0;
    }

    @Override
    public String toString() {
        return "Clases_Manu.Socio{" +
                "numeroSocio=" + numeroSocio +
                ", tiposocio=" + tiposocio +
                ", fechaAlta='" + fechaAlta + '\'' +
                ", cuotaAlDia=" + cuotaAlDia +
                "nombre y apellido " + getNombre() + getApellido()+
                '}';
    }

}
