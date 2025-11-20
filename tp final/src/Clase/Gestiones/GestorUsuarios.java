package Clase.Gestiones;

import Clase.Menus.UsuarioSistema;
import enums.Rol;
import exeptions.IngresoInvalido;
import org.json.JSONArray;
import org.json.JSONObject;
import Clase.Json.JSONUtiles;

import java.util.HashMap;

public class GestorUsuarios {

    private final HashMap<String, UsuarioSistema> usuarios = new HashMap<>();

    public GestorUsuarios() {
        cargarJSON();
    }

    public void registrar(String dni, String nombre, Rol rol) throws IngresoInvalido {

        if (dni == null || dni.isBlank()) {
            throw new IngresoInvalido("dni invalido");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IngresoInvalido("nombre invalido");
        }

        if (usuarios.containsKey(dni)) {
            throw new IngresoInvalido("el usuario ya existe");
        }

        usuarios.put(dni, new UsuarioSistema(dni, nombre, rol));
        guardarJSON();
    }

    public UsuarioSistema login(String dni) throws IngresoInvalido {
        UsuarioSistema u = usuarios.get(dni);
        if (u == null) {
            throw new IngresoInvalido("no existe usuario con ese dni");
        }
        return u;
    }

    public void guardarJSON() {
        JSONArray array = new JSONArray();
        for (UsuarioSistema u : usuarios.values()) {
            JSONObject obj = new JSONObject();
            obj.put("dni", u.getDni());
            obj.put("nombre", u.getNombre());
            obj.put("rol", u.getRol().name());
            array.put(obj);
        }
        JSONUtiles.uploadJSON(array, "usuarios");
    }

    public void cargarJSON() {
        String contenido = JSONUtiles.downloadJSON("usuarios");
        if (contenido.isBlank()) return;
        JSONArray array = new JSONArray(contenido);
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String dni = obj.getString("dni");
            String nombre = obj.getString("nombre");
            Rol rol = Rol.valueOf(obj.getString("rol"));

            usuarios.put(dni, new UsuarioSistema(dni, nombre, rol));
        }
    }

}

