package Clases_Manu;

public class Club {
    private GestionJugadores gestionJugadores;
    private GestionCuerpoTecnico gestionCuerpoTecnico;
    private GestionSocio gestionSocios;
    private GestorPartido gestorPartidos;
    private Inventario<Producto> inventario;
    private GestionEstadio gestionEstadios;
    private GestionPresupuesto gestionPresupuesto;

    public Club(double saldoInicial) {

        this.gestionPresupuesto = new GestionPresupuesto(saldoInicial);

        this.gestionJugadores = new GestionJugadores(gestionPresupuesto);
        this.gestionCuerpoTecnico = new GestionCuerpoTecnico();
        this.gestionSocios = new GestionSocio();
        this.gestorPartidos = new GestorPartido(this.getGestionJugadores());
        this.inventario = new Inventario<>();
        this.gestionEstadios = new GestionEstadio(gestionPresupuesto);
    }

    public GestionJugadores getGestionJugadores() {
        return gestionJugadores;
    }

    public GestionCuerpoTecnico getGestionCuerpoTecnico() {
        return gestionCuerpoTecnico;
    }

    public GestionSocio getGestionSocios() {
        return gestionSocios;
    }

    public GestorPartido getGestorPartidos() {
        return gestorPartidos;
    }

    public Inventario<Producto> getInventario() {
        return inventario;
    }

    public GestionEstadio getGestionEstadios() {
        return gestionEstadios;
    }

    public GestionPresupuesto getGestionPresupuesto() {
        return gestionPresupuesto;
    }


    public void guardarDatosJSON() {
        gestionJugadores.guardarJSON();
        gestionCuerpoTecnico.guardarJSON();
        gestionSocios.guardarJSON();
        gestorPartidos.guardarJSON();
        inventario.guardarJSON();
        gestionEstadios.guardarJSON();
        gestionPresupuesto.guardarJSON();
    }
}