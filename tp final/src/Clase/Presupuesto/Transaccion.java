package Clase.Presupuesto;

import java.time.LocalDate;

public class Transaccion {
    private final String descripcion;
    private final double monto;
    private final String tipo;
    private final LocalDate fecha;

    public Transaccion(String descripcion, double monto, String tipo, LocalDate fecha) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede ser nula ni vacía.");
        }

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }

        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de transacción no puede ser nulo ni vacío.");
        }

        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }

        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser mayor al día de hoy.");
        }

        this.descripcion = descripcion;
        this.monto = monto;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "descripcion='" + descripcion + '\'' +
                ", monto=" + monto +
                ", tipo='" + tipo + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
