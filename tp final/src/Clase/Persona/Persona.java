package Clase.Persona;
import java.time.LocalDate;
import java.util.Objects;

public abstract class Persona {
    private String dni;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String nacionalidad;

    public Persona(String dni, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad) {
        setDni(dni);
        setNombre(nombre);
        setApellido(apellido);
        setFechaNacimiento(fechaNacimiento);
        setNacionalidad(nacionalidad);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni == null || !dni.matches("\\d+")) {
            throw new IllegalArgumentException("El DNI debe contener solo numeros");
        }
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || !nombre.matches("[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios");
        }
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || !apellido.matches("[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+")) {
            throw new IllegalArgumentException("El apellido solo puede contener letras y espacios");
        }
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura ni nula");
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        if (nacionalidad == null || !nacionalidad.matches("[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+")) {
            throw new IllegalArgumentException("La nacionalidad solo puede contener letras y espacios");
        }
        this.nacionalidad = nacionalidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Persona persona)) {
            return false;
        }
        return Objects.equals(dni, persona.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    @Override
    public String toString() {
        return "Persona" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", nacionalidad='" + nacionalidad + '\'';
    }
}
