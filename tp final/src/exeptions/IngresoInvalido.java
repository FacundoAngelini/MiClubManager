package exeptions;

public class IngresoInvalido extends Exception {
    public IngresoInvalido(String message) {
        super("El ingreso es invalido");
    }
}
