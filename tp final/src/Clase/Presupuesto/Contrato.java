package Clase.Presupuesto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Contrato {

    private final String dni;
    private final double salario;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private boolean contratoActivo;

    public Contrato(String dni, double salario, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaNacimiento) {

        if (dni == null || !dni.matches("\\d+")) {
            throw new IllegalArgumentException("dni invalido debe contener solo numeros");
        }
        if (salario <= 0) {
            throw new IllegalArgumentException("salario debe ser mayor a 0");
        }

        if (fechaInicio == null || fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("fecha de inicio invalida");
        }
        if (fechaFin == null || fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("fecha fin debe ser posterior a inicio");
        }
        if (fechaNacimiento != null && fechaInicio.isBefore(fechaNacimiento.plusYears(14))) {
            throw new IllegalArgumentException("no se puede contratar antes de los 14 anos");
        }
        this.dni = dni;
        this.salario = salario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.contratoActivo = true;
    }

    public String getDni() {
        return dni;
    }

    public double getSalario() {
        return salario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public boolean isContratoActivo() {
        return contratoActivo && !LocalDate.now().isAfter(fechaFin);
    }

    public void setContratoActivo(boolean contratoActivo) {
        this.contratoActivo = contratoActivo;
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Contrato" +
                "dni='" + dni + '\'' +
                ", salario=" + salario +
                ", fechaInicio=" + fechaInicio.format(formato) +
                ", fechaFin=" + fechaFin.format(formato) +
                ", contratoActivo=" + isContratoActivo();
    }
}
