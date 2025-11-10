import java.util.ArrayList;

public interface MetodosComunes<T,K> {
    void agregarElemento(T elemento) throws IngresoInvalido,AccionImposible;
    void eliminarElemento(K id) throws AccionImposible;
    void modificarElemento(T elemento) throws AccionImposible;
    T devuelveElemento(K key) throws AccionImposible;
    boolean existe(K id) throws ElementoInexistenteEx;
    ArrayList<T>listar();
    void guardarJSON();
}
