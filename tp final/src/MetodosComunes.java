import java.util.ArrayList;

public interface MetodosComunes<T> {
    void agregarElemento(T elemento) throws IngresoInvalido;
    void eliminarElemento(T elemento) throws AccionImposible;
    void modificarElemento(T elemento) throws AccionImposible;
    boolean existe(T elemento);
    ArrayList<T>listar();
    void guardarJSON();
}
