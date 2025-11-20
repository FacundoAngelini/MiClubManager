package Clase.Persona;

import Clase.Presupuesto.Contrato;
import enums.Posicion;
import java.time.LocalDate;

public class Jugador extends Persona {

    private final EstadisticaJugador estadisticaJugador;
    private final Posicion posicion;
    private final Contrato contrato;
    private final double valorJugador;
    private int numeroCamiseta;

    public Jugador(String dni, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, int numeroCamiseta, Contrato contrato, Posicion posicion, double valorJugador) {
        super(dni, nombre, apellido, fechaNacimiento, nacionalidad);
        if (contrato == null){
            throw new IllegalArgumentException("El jugador debe tener un contrato asignado");
        }
        if (numeroCamiseta <= 0) {
        throw new IllegalArgumentException("El numero de camiseta debe ser mayor a 0");
        }
        if (valorJugador <= 0) {
            throw new IllegalArgumentException("El valor del jugador debe ser mayor a 0");
        }

        this.estadisticaJugador = new EstadisticaJugador();
        this.numeroCamiseta = numeroCamiseta;
        this.posicion = posicion;
        this.contrato = contrato;
        this.valorJugador = valorJugador;
    }

    public Posicion getPosicion() {
        return posicion;
    }


    public Contrato getContrato() {
        return contrato;
    }

    public double getValorJugador() {
        return valorJugador;
    }

    public int getNumeroCamiseta() {
        return numeroCamiseta;
    }

    @Override
    public String toString() {
        return "Jugador" +
                "nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", dni='" + getDni() + '\'' +
                ", fechaNacimiento=" + getFechaNacimiento() +
                ", nacionalidad='" + getNacionalidad() + '\'' +
                ", estadisticaJugador=" + estadisticaJugador +
                ", posicion=" + posicion +
                ", contrato=" + contrato +
                ", valorJugador=" + valorJugador +
                ", numeroCamiseta=" + numeroCamiseta;
    }
}
