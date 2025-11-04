public class Camiseta extends Producto {
    private double precioFabricacion; // o precio que cuesta comprarlaa
    private double precioVenta;
    private String sponsor;
    private double pagoSpongor;// dinero que el sponsor paga para aparecer en la remera
    private int camisetasVendidas;

    public Camiseta(String nombre, String marca, int cantidad, double precioFabricacion, int camisetasVendidas, double pagoSpongor, String sponsor, double precioVenta) {
        super(nombre, marca, cantidad);
        this.precioFabricacion = precioFabricacion;
        this.camisetasVendidas = camisetasVendidas;
        this.pagoSpongor = pagoSpongor;
        this.sponsor = sponsor;
        this.precioVenta = precioVenta;
    }

    public double getPrecioFabricacion() {
        return precioFabricacion;
    }

    public void setPrecioFabricacion(double precioFabricacion) {
        this.precioFabricacion = precioFabricacion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public double getPagoSpongor() {
        return pagoSpongor;
    }

    public void setPagoSpongor(double pagoSpongor) {
        this.pagoSpongor = pagoSpongor;
    }

    private double calcularcostos()
    {
        return (precioFabricacion *camisetasVendidas) ;
    }

    public double calcularGanancia()
    {
        return (precioVenta*camisetasVendidas) + pagoSpongor;
    }

    @Override
    protected String muestraDatos() {
        return "Camiseta{" +
                "precioFabricacion=" + precioFabricacion +
                ", precioVenta=" + precioVenta +
                ", sponsor='" + sponsor + '\'' +
                ", pagoSpongor=" + pagoSpongor +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}