package Clase.Persona;

public class EstadisticaJugador {

    private final int partidosJugados;
    private int goles;
    private int asistencias;
    private final int lesiones;
    private int vallasInvictas;

    public EstadisticaJugador() {
        this.partidosJugados = 0;
        this.goles = 0;
        this.asistencias = 0;
        this.lesiones = 0;
        this.vallasInvictas = 0;
    }


    public void agregarGoles(int cantidad) {
        this.goles += cantidad;
    }

    public void agregarAsistencias(int cantidad) {
        this.asistencias += cantidad;
    }

    public void agregarVallasInvictas(int cantidad) {
        this.vallasInvictas += cantidad;
    }


    @Override
    public String toString() {
        return "Partidos jugados = " + partidosJugados +
                ", Goles = " + goles +
                ", Asistencias = " + asistencias +
                ", Lesiones = " + lesiones +
                ", Vallas invictas = " + vallasInvictas;
    }
}
