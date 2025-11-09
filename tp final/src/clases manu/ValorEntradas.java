public enum ValorEntradas {
    POPULAR(100),
    PLATEA(150),
    PALCO(200);
    private final double precio;
    ValorEntradas(int precio) {
        this.precio = precio;
    }
    public double getPrecio() {return precio;}
}
