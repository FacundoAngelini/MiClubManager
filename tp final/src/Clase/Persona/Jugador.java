package Clase.Persona;

import Clase.Presupuesto.Contrato;
import enums.Posicion;

public class Jugador extends Persona {

    private EstadisticaJugador estadisticaJugador;
    private Posicion posicion;
    private final Contrato contrato;
    private double valorJugador;
    private int numeroCamiseta;

    public Jugador(String dni, String nombre, String apellido, String fechaNacimiento, String nacionalidad, int numeroCamiseta, Contrato contrato, Posicion posicion, double valorJugador) {
        super(dni, nombre, apellido, fechaNacimiento, nacionalidad);

        if (contrato == null) {
            throw new IllegalArgumentException("El jugador debe tener un contrato asignado.");
        }

        this.estadisticaJugador = new EstadisticaJugador();
        this.numeroCamiseta = numeroCamiseta;
        this.posicion = posicion;
        this.contrato = contrato;
        this.valorJugador = valorJugador;
    }

    public EstadisticaJugador getEstadisticaJugador() {
        return estadisticaJugador;
    }

    public void setEstadisticaJugador(EstadisticaJugador estadisticaJugador) {
        this.estadisticaJugador = estadisticaJugador;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public double getValorJugador() {
        return valorJugador;
    }

    public void setValorJugador(double valorJugador) {
        this.valorJugador = valorJugador;
    }

    public int getNumeroCamiseta() {
        return numeroCamiseta;
    }

    public void setNumeroCamiseta(int numeroCamiseta) {
        this.numeroCamiseta = numeroCamiseta;
    }

    public String toString() {
        return "Jugador{" +
                "nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", dni='" + getDni() + '\'' +
                ", fechaNacimiento='" + getFechaNacimiento() + '\'' +
                ", nacionalidad='" + getNacionalidad() + '\'' +
                ", estadisticaJugador=" + estadisticaJugador +
                ", posicion=" + posicion +
                ", contrato=" + contrato +
                ", valorJugador=" + valorJugador +
                ", numeroCamiseta=" + numeroCamiseta +
                '}';
    }
}
