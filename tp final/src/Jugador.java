public class Jugador extends Persona {
    private EstadisticaJugador estadisticaJugador;
    private Posicion posicion;
    private Contrato contrato;

    public Jugador(String dni, String nombre, String apellido, String fechaNacimeiento, String nacionalidad, EstadisticaJugador estadisticaJugador, Posicion posicion, Contrato contrato) {
        super(dni, nombre, apellido, fechaNacimeiento, nacionalidad);
        this.estadisticaJugador = estadisticaJugador;
        this.posicion = posicion;
        this.contrato = contrato;
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

    @Override
    public void obtenerDatos() {
        System.out.println("Persona{" +
                "dni='" + getDni() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido()+ '\'' +
                ", fechaNacimeiento='" + getFechaNacimeiento() + '\'' +
                ", nacionalidad='" + getNacionalidad() + '\'' +
                "Jugador{" +
                "estadisticaJugador=" + estadisticaJugador +
                ", posicion=" + posicion +
                ", contrato=" + contrato);
    }

}
