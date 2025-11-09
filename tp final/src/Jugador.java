public class Jugador extends Persona {
    private EstadisticaJugador estadisticaJugador;
    private Posicion posicion;
    private Contrato contrato;
    private double valorJugador;
    private int numeroCamiseta;

    public Jugador(String dni, String nombre, String apellido, String fechaNacimeiento, String nacionalidad, EstadisticaJugador estadisticaJugador, int numeroCamiseta, double valorJugador, Contrato contrato, Posicion posicion) {
        super(dni, nombre, apellido, fechaNacimeiento, nacionalidad);
        this.estadisticaJugador = estadisticaJugador;
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

    public void actualizarEstadisticas(FichaDelPartido partido) {
        for(Gol gol : partido.getGoles()) {
            if(gol.getJugador().equals(this)) {
                estadisticaJugador.agregarGoles(1);
            }
        }

        for(TarjetaAplicada tarjeta : partido.getTarjetas()) {
            if(tarjeta.getJugador().equals(this)) {
                if(tarjeta.getTipoTarjeta() == TipoTarjeta.AMARILLA) {
                    estadisticaJugador.agregarTarjetaAmarilla();
                } else {
                    estadisticaJugador.agregarTarjetaRoja();
                }
            }
        }

        for (Jugador lesionado : partido.getLesionados()) {
            if (lesionado.equals(this)) {
                estadisticaJugador.agregarLesion();
                break;
            }
        }
    }

    public int getNumeroCamiseta() {
        return numeroCamiseta;
    }

    public void setNumeroCamiseta(int numeroCamiseta) {
        this.numeroCamiseta = numeroCamiseta;
    }

    @Override
    public void obtenerDatos() {
        System.out.println("Persona{" +
                "dni='" + getDni() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido()+ '\'' +
                ", fechaNacimeiento='" + getFechaNacimiento() + '\'' +
                ", nacionalidad='" + getNacionalidad() + '\'' +
                "Jugador{" +
                "estadisticaJugador=" + estadisticaJugador +
                ", posicion=" + posicion +
                ", contrato=" + contrato +
                "valor jugador" +valorJugador +
                "numero Camiseta" + numeroCamiseta+"}");
    }

}
