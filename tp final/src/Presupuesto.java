public class Presupuesto {
    private double ingreso;
    private double egreso;

    public Presupuesto(double ingreso, double egreso) {
        this.ingreso = ingreso;
        this.egreso = egreso;
    }

    public void agregarBeneificos(double beneificos) throws IngresoInvalido
    {
        if(beneificos < 0)
        {
            throw new IngresoInvalido("No se puede agregar un numero negativo al ingreso");
        }
        ingreso += beneificos;
    }

    public void agregarPerdidas(double perdida) throws IngresoInvalido
    {
        if(perdida < 0)
        {
            throw new IngresoInvalido("El numero ingresado de la perdida debe ser positivo");
        }
        egreso += perdida;
    }

    public void reiniciarPresupuesto()
    {
        egreso = 0;
        ingreso = 0;
        System.out.println("El presupuesto esta de nuevo en 0");
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
        if (resultado < 0) {
            System.out.println("El balance es negativo :$ " + resultado);
        } else {
            System.out.println("El balance es positivo :$ " + resultado);
        }
    }

    }
