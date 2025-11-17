package Clase.Presupuesto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Contrato {

    private final String dni;
    private final double salario;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;

    public Contrato(String dni, double salario, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaNacimiento) {

        if (!dni.matches("\\d+")) {
            throw new IllegalArgumentException("El DNI debe contener solo numeros");
        }

        if (salario <= 0) {
            throw new IllegalArgumentException("El salario debe ser mayor a 0");
        }

        if (fechaInicio.isBefore(fechaNacimiento.plusYears(10))) {
            throw new IllegalArgumentException("No se puede contratar antes de los 10 años de edad");
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de finalizacion del contrato debe ser posterior a la fecha de inicio");
        }

        this.dni = dni;
        this.salario = salario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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
        LocalDate hoy = LocalDate.now();
        return !hoy.isAfter(fechaFin);
    }



    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Contrato{" +
                "dni='" + dni + '\'' +
                ", salario=" + salario +
                ", fechaInicio=" + fechaInicio.format(formato) +
                ", fechaFin=" + fechaFin.format(formato) +
                ", contratoActivo=" + isContratoActivo() +
                '}';
    }
}

