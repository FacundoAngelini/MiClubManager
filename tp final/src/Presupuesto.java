public class Presupuesto {
    private double ingreso;
    private double egreso;

    public Presupuesto(double ingreso, double egreso) {
        this.ingreso = ingreso;
        this.egreso = egreso;
    }

    public void agregarGananciaCamisetas(double ganancia)
    {
        ingreso += ganancia;
    }

    public void  agregarEgresoCamisetas(double perdida)
    {
        egreso += perdida;
    }

    public void agregarGananciaSocios(double ganancia)
    {
        ingreso += ganancia;
    }

    public void agregarEgresoEstadio (double perdida)
    {
        egreso += perdida;
    }

    public void agregarGananciaEstadio(double ganancia)
    {
        ingreso += ganancia;
    }

    public void agregarEgresosJugadores(double perdida) // mas adelante se vera si es solo de compras o tmb sueldo, luego flta hacer el de dt
    {
        egreso += perdida;
    }

    public void agregarGananciaJugadores(double ganancia)
    {
        ingreso += ganancia;
    }

    public void agregarBeneificos(double beneificos)
    {
        ingreso += beneificos;
    }

    public void agregarPerdidas(double perdida)
    {
        egreso += perdida;
    }


    public double calcularResultados()
    {
        return ingreso - egreso;
    }

    public void mostrarResumen() {
        System.out.println("----- Presupuesto del club ------");
        System.out.println("Total de ingresos: " + ingreso);
        System.out.println("Total de egresos: " + egreso);
          double resultado = calcularResultados();


    }
