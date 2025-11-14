package Clase.Club;

import Clase.Gestiones.*;
import Clase.Productos.Inventario;
import Clase.Productos.Producto;

public class Club {
    private final GestionJugadores gestionJugadores;
    private final GestionCuerpoTecnico gestionCuerpoTecnico;
    private final GestionSocio gestionSocios;
    private final GestorPartido gestorPartidos;
    private final Inventario<Producto> inventario;
    private final GestionEstadio gestionEstadios;
    private final GestionPresupuesto gestionPresupuesto;

    public Club() {

        this.gestionPresupuesto = new GestionPresupuesto();

        this.gestionJugadores = new GestionJugadores(gestionPresupuesto);
        this.gestionCuerpoTecnico = new GestionCuerpoTecnico(gestionPresupuesto);
        this.gestionSocios = new GestionSocio(gestionPresupuesto);
        this.gestorPartidos = new GestorPartido (gestionJugadores,gestionPresupuesto);
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

}