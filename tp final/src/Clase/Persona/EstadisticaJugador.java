package Clase.Persona;

public class EstadisticaJugador {
    private int partidosJugados;
    private int goles;
    private int asistencias;
    private int vallasInvictas;
    private int tarjetasAmarillas;
    private int tarjetasRojas;
    private int lesiones;

    public EstadisticaJugador() {
        this.partidosJugados = 0;
        this.goles = 0;
        this.asistencias = 0;
        this.vallasInvictas = 0;
        this.tarjetasAmarillas = 0;
        this.tarjetasRojas = 0;
        this.lesiones = 0;
    }

    public void agregarPartido() {
        this.partidosJugados++;
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

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public int getGoles() {
        return goles;
    }

      public int getTarjetasRojas() {
        return tarjetasRojas;
    }

    public int getLesiones() {
        return lesiones;
    }

    public double promedioGolesPorPartido() {
        return partidosJugados > 0 ? (double) goles / partidosJugados : 0;
    }

    public double porcentajeTarjetasAmarillas() {
        return partidosJugados > 0 ? (double) tarjetasAmarillas / partidosJugados * 100 : 0;
    }

    public double porcentajeTarjetasRojas() {
        return partidosJugados > 0 ? (double) tarjetasRojas / partidosJugados * 100 : 0;
    }

    @Override
    public String toString() {
        return "Partidos jugados=" + partidosJugados +
                ", Goles=" + goles +
                ", Asistencias=" + asistencias +
                ", Vallas invictas=" + vallasInvictas +
                ", Tarjetas amarillas=" + tarjetasAmarillas +
                ", Tarjetas rojas=" + tarjetasRojas +
                ", Lesiones=" + lesiones +
                ", Promedio de goles por partido=" + String.format("%.2f", promedioGolesPorPartido()) +
                ", % Tarjetas amarillas=" + String.format("%.2f", porcentajeTarjetasAmarillas()) +
                ", % Tarjetas rojas=" + String.format("%.2f", porcentajeTarjetasRojas());
    }
}
