package Clase.Persona;

public class EstadisticaJugador {

    private int partidosJugados;
    private int goles;
    private int asistencias;
    private int tarjetasAmarillas;
    private int tarjetasRojas;
    private int lesiones;
    private int vallasInvictas;

    public EstadisticaJugador() {
        this.partidosJugados = 0;
        this.goles = 0;
        this.asistencias = 0;
        this.tarjetasAmarillas = 0;
        this.tarjetasRojas = 0;
        this.lesiones = 0;
        this.vallasInvictas = 0;
    }

    public void agregarPartidos(int cantidad) {
        this.partidosJugados += cantidad;
    }

    public void agregarGoles(int cantidad) {
        this.goles += cantidad;
    }

    public void agregarAsistencias(int cantidad) {
        this.asistencias += cantidad;
    }

    public void agregarTarjetasAmarillas(int cantidad) {
        this.tarjetasAmarillas += cantidad;
    }

    public void agregarTarjetasRojas(int cantidad) {
        this.tarjetasRojas += cantidad;
    }

    public void agregarLesiones(int cantidad) {
        this.lesiones += cantidad;
    }

    public void agregarVallasInvictas(int cantidad) {
        this.vallasInvictas += cantidad;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public int getGoles() {
        return goles;
    }

    public int getAsistencias() {
        return asistencias;
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

    public int getVallasInvictas() {
        return vallasInvictas;
    }

    @Override
    public String toString() {
        return "Partidos jugados = " + partidosJugados +
                ", Goles = " + goles +
                ", Asistencias = " + asistencias +
                ", Tarjetas amarillas = " + tarjetasAmarillas +
                ", Tarjetas rojas = " + tarjetasRojas +
                ", Lesiones = " + lesiones +
                ", Vallas invictas = " + vallasInvictas;
    }
}
