import java.util.Objects;

public abstract class Persona {
    private String dni;
    private String nombre;
    private String apellido;
    private String fechaNacimeiento;
    private String nacionalidad;

    public Persona(String dni, String nombre, String apellido, String fechaNacimeiento, String nacionalidad) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimeiento = fechaNacimeiento;
        this.nacionalidad = nacionalidad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimeiento() {
        return fechaNacimeiento;
    }

    public void setFechaNacimeiento(String fechaNacimeiento) {
        this.fechaNacimeiento = fechaNacimeiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona persona)) return false;
        return Objects.equals(dni, persona.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaNacimeiento='" + fechaNacimeiento + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                '}';
    }

    public abstract void obtenerDatos();
}
