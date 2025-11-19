package exeptions;

public class AccionImposible extends Exception{
    public AccionImposible(String mensaje){
        super("Esta accion es imposible de realizar");
    }
}
