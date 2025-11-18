package Clase.Menus;

import enums.Rol;

public class UsuarioSistema {

    private final String dni;
    private final String nombre;
    private final Rol rol;

    public UsuarioSistema(String dni, String nombre, Rol rol) {
        this.dni = dni;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public Rol getRol() {
        return rol;
    }
}
