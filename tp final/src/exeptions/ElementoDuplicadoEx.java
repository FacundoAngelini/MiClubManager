package exeptions;

public class ElementoDuplicadoEx extends Exception{
    public ElementoDuplicadoEx(String message) {
        super("El elemento se encuentra duplicado");
    }
}
