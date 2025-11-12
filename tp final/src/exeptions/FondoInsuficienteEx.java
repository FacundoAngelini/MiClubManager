package exeptions;

public class FondoInsuficienteEx extends RuntimeException {
    public FondoInsuficienteEx(String message) {
        super(message);
    }
}
