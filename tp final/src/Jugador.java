import java.util.StringJoiner;

public class Jugador extends Persona {
    private EstadisticaJugador estadisticaJugador;
    private Posicion posicion;
    private Contrato contrato;
    private double valorJugador;
    private int numeroCamiseta;

    public Jugador(String dni, String nombre, String apellido, String fechaNacimeiento, String nacionalidad, int numeroCamiseta, Contrato contrato, Posicion posicion) {
        super(dni, nombre, apellido, fechaNacimeiento, nacionalidad);
        this.estadisticaJugador = new EstadisticaJugador();
        this.numeroCamiseta = numeroCamiseta;
        this.valorJugador = valorJugador;
        this.contrato = contrato;
        this.posicion = posicion;
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

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Jugador{");
        sb.append(super.toString());
        sb.append(", ");
        sb.append("estadisticaJugador=").append(estadisticaJugador);
        sb.append(", posicion=").append(posicion);
        sb.append(", contrato=").append(contrato);
        sb.append(", valorJugador=").append(valorJugador);
        sb.append(", numeroCamiseta=").append(numeroCamiseta);
        sb.append('}');
        return sb.toString();
    }
}
