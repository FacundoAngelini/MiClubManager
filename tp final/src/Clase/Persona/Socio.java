package Clase.Persona;

import enums.Tiposocio;
import exeptions.AccionImposible;
import java.time.LocalDate;

public class Socio extends Persona {

    private static int contador = 1;
    private int numeroSocio;
    private Tiposocio tiposocio;
    private LocalDate fechaAlta;

    public Socio(String dni, String nombre, String apellido,
                 LocalDate fechaNacimiento, String nacionalidad,
                 LocalDate fechaAlta, Tiposocio tiposocio) {

        super(dni, nombre, apellido, fechaNacimiento, nacionalidad);

        if (fechaAlta == null)
            throw new IllegalArgumentException("fecha alta invalida");

        if (fechaAlta.isBefore(fechaNacimiento))
            throw new IllegalArgumentException("fecha alta anterior a nacimiento");

        if (fechaAlta.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("fecha alta futura");

        this.numeroSocio = contador++;
        this.tiposocio = tiposocio == null ? Tiposocio.ACTIVO : tiposocio;
        this.fechaAlta = fechaAlta;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public Tiposocio getTiposocio() {
        return tiposocio;
    }

    public void setTipoSocio(Tiposocio tipoSocio) {
        if (tipoSocio == null)
            throw new IllegalArgumentException("tipo socio invalido");
        this.tiposocio = tipoSocio;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        if (fechaAlta == null)
            throw new IllegalArgumentException("fecha alta invalida");

        if (fechaAlta.isBefore(getFechaNacimiento()))
            throw new IllegalArgumentException("fecha alta anterior a nacimiento");

        if (fechaAlta.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("fecha alta futura");

        this.fechaAlta = fechaAlta;
    }

    public void cambiarAActivo() throws AccionImposible {
        if (tiposocio == Tiposocio.ACTIVO)
            throw new AccionImposible("ya es activo");
        this.tiposocio = Tiposocio.ACTIVO;
    }

    public void cambiarAVitalicio() throws AccionImposible {
        if (tiposocio == Tiposocio.VITALICIO)
            throw new AccionImposible("ya es vitalicio");
        this.tiposocio = Tiposocio.VITALICIO;
    }

    public void inactivarSocio() {
        this.tiposocio = Tiposocio.INACTIVO;
    }

    public double obtenerMontoRecaudado() {
        if (tiposocio == Tiposocio.INACTIVO)
            return 0;
        return tiposocio.getPrecio();
    }
}
