public class EstadisticaJugador {
    private int goles;
    private int asistencias;
    private int vallasInvictas;
    private int tarjetasAmarillas;
    private int tarjetasRojas;
    private int lesiones;

    public EstadisticaJugador() {
        this.goles = 0;
        this.asistencias = 0;
        this.vallasInvictas = 0;
        this.tarjetasAmarillas = 0;
        this.tarjetasRojas = 0;
        this.lesiones = 0;
    }

    public void agregarGol() {
        this.goles++;
    }

    public void agregarAsistencia() {
        this.asistencias++;
    }

    public void agregarVallaInvicta() {
        this.vallasInvictas++;
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

    public int getAsistencias() {
        return asistencias;
    }

    public int getVallasInvictas() {
        return vallasInvictas;
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
                ", Asistencias=" + asistencias +
                ", Vallas invictas=" + vallasInvictas +
                ", Amarillas=" + tarjetasAmarillas +
                ", Rojas=" + tarjetasRojas +
                ", Lesiones=" + lesiones;
    }
}