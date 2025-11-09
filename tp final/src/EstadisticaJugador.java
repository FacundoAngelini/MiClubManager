public class EstadisticaJugador {
    private int goles;
    private int tarjetasAmarillas;
    private int tarjetasRojas;
    private int lesiones;

    public EstadisticaJugador() {
        this.goles = 0;
        this.tarjetasAmarillas = 0;
        this.tarjetasRojas = 0;
        this.lesiones = 0;
    }

    public void agregarGoles(int cantidad) {
        if(cantidad > 0) this.goles += cantidad;
    }

    public void agregarTarjetaAmarilla() {
        this.tarjetasAmarillas++;
    }

    public void agregarTarjetaRoja() {
        this.tarjetasRojas++;
    }

    public void agregarLesion() {
        this.lesiones++;
    }

    public int getGoles() {
        return goles;
    }

    public int getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }

    public int getTarjetasRojas() {
        return tarjetasRojas;
    }

    public int getLesiones() {
        return lesiones;
    }

    @Override
    public String toString() {
        return "Goles=" + goles +
                ", Amarillas=" + tarjetasAmarillas +
                ", Rojas=" + tarjetasRojas +
                ", Lesiones=" + lesiones;
    }
}
