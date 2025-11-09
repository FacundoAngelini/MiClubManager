public enum ValorEntradas {
    POPULAR(12000),
    PLATEA(15000),
    PALCO(20000);
    private final double precio;
    ValorEntradas(int precio) {
        this.precio = precio;
    }
    public double getPrecio() {return precio;}
}
