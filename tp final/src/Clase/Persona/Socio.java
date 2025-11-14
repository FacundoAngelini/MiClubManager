package Clase.Persona;
import enums.Tiposocio;
import exeptions.AccionImposible;
import java.time.LocalDate;

public class Socio extends Persona {
    private static int contador = 1;
    private int numeroSocio;
    private Tiposocio tiposocio;
    private LocalDate fechaAlta;

    public Socio(String dni, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad,
                 LocalDate fechaAlta, Tiposocio tiposocio) {

        super(dni, nombre, apellido, fechaNacimiento, nacionalidad);

        if (fechaAlta == null) {
            throw new IllegalArgumentException("La fecha de alta no puede ser nula.");
        }

        if (fechaAlta.isBefore(fechaNacimiento)) {
            throw new IllegalArgumentException("La fecha de alta no puede ser anterior a la fecha de nacimiento.");
        }

        if (fechaAlta.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de alta no puede ser posterior a hoy.");
        }

        this.numeroSocio = contador++;
        this.tiposocio = (tiposocio != null) ? tiposocio : Tiposocio.ACTIVO;
        this.fechaAlta = fechaAlta;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public Tiposocio getTiposocio() {
        return tiposocio;
    }

    public void setTipoSocio(Tiposocio tipoSocio) {
        if (tipoSocio == null) {
            throw new IllegalArgumentException("El tipo de socio no puede ser null.");
        }
        this.tiposocio = tipoSocio;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        if (fechaAlta == null) {
            throw new IllegalArgumentException("La fecha de alta no puede ser nula.");
        }
        if (fechaAlta.isBefore(getFechaNacimiento())) {
            throw new IllegalArgumentException("La fecha de alta no puede ser anterior a la fecha de nacimiento.");
        }
        if (fechaAlta.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de alta no puede ser posterior a hoy.");
        }
        this.fechaAlta = fechaAlta;
    }

    public void cambiarAActivo() throws AccionImposible {
        if (tiposocio == Tiposocio.ACTIVO) {
            throw new AccionImposible("El socio ya está activo.");
        }
        this.tiposocio = Tiposocio.ACTIVO;
    }

    public void cambiarAVitalicio() throws AccionImposible {
        if (tiposocio == Tiposocio.VITALICIO) {
            throw new AccionImposible("El socio ya es vitalicio.");
        }
        this.tiposocio = Tiposocio.VITALICIO;
    }

    public void inactivarSocio() {
        this.tiposocio = Tiposocio.INACTIVO;
    }

    public double obtenerMontoRecaudado() {
        if (tiposocio == Tiposocio.INACTIVO) {
            return 0;
        }
        return tiposocio.getPrecio();
    }

    @Override
    public String toString() {
        return "Socio{" +
                "numeroSocio=" + numeroSocio +
                ", tiposocio=" + tiposocio +
                ", fechaAlta=" + fechaAlta +
                ", nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                '}';
    }
}
