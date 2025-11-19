package Clase.Partidos;

import java.time.LocalDate;
import java.util.Objects;

public class Partido {
    private final LocalDate fecha;
    private final boolean esLocal;
    private final String rival;
    private final int golesAFavor;
    private final int golesEnContra;
    private final int entradasVendidas;
    private final double precioEntrada;
    private final FichaDelPartido fichaDelPartido;
    private final int capacidadEstadio;

    public Partido(LocalDate fecha, boolean esLocal, String rival,
                   int golesAFavor, int golesEnContra,
                   int entradasVendidas, double precioEntrada,
                   int capacidadEstadio) throws IllegalArgumentException {

        if (fecha == null) throw new IllegalArgumentException("La fecha no puede ser nula");
        if (fecha.isAfter(LocalDate.now())) throw new IllegalArgumentException("La fecha no puede ser futura");
        if (rival == null || rival.isBlank()) throw new IllegalArgumentException("Debe ingresar el nombre del rival");

        this.fecha = fecha;
        this.esLocal = esLocal;
        this.rival = rival;
        this.golesAFavor = golesAFavor;
        this.golesEnContra = golesEnContra;
        this.capacidadEstadio = capacidadEstadio;

        if (esLocal) {
            if (entradasVendidas > capacidadEstadio)
                throw new IllegalArgumentException("Entradas vendidas no pueden superar la capacidad del estadio");
            this.entradasVendidas = entradasVendidas;
            this.precioEntrada = precioEntrada;
        } else {
            this.entradasVendidas = 0;
            this.precioEntrada = 0;
        }

        this.fichaDelPartido = new FichaDelPartido();
    }

    public LocalDate getFecha() { return fecha; }
    public boolean isEsLocal() { return esLocal; }
    public String getRival() { return rival; }
    public int getGolesAFavor() { return golesAFavor; }
    public int getGolesEnContra() { return golesEnContra; }
    public int getEntradasVendidas() { return entradasVendidas; }
    public double getPrecioEntrada() { return precioEntrada; }
    public FichaDelPartido getFichaDelPartido() { return fichaDelPartido; }
    public int getCapacidadEstadio() { return capacidadEstadio; }

    public boolean gano() { return golesAFavor > golesEnContra; }
    public boolean empato() { return golesAFavor == golesEnContra; }
    public boolean perdio() { return golesAFavor < golesEnContra; }

    public double calcularRecaudacion() {
        return esLocal ? entradasVendidas * precioEntrada : 0;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "fecha=" + fecha +
                ", local=" + esLocal +
                ", rival='" + rival + '\'' +
                ", resultado=" + golesAFavor + "-" + golesEnContra +
                ", recaudacion=" + (esLocal ? calcularRecaudacion() : "No aplica") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return Objects.equals(fecha, partido.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha);
    }
}
