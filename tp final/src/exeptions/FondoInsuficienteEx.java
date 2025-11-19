package exeptions;
public class FondoInsuficienteEx extends Exception {
    public FondoInsuficienteEx(String message) {
        super("No hay suficientes fondos");
    }
}
