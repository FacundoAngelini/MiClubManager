package exeptions;

public class ElementoInexistenteEx extends Exception {
    public ElementoInexistenteEx(String message) {
        super("El elemento no existe");
    }
}
