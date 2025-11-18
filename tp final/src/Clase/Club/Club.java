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
        this.gestionEstadios = new GestionEstadio(gestionPresupuesto);
        this.gestorPartidos = new GestorPartido (0);
        this.inventario = new Inventario<>();

        this.gestionPresupuesto.guardarJSON();
        this.gestionEstadios.guardarJSON();
        this.gestionSocios.guardarJSON();
        this.inventario.guardarJSON();
        this.gestionJugadores.guardarJSON();
        this.gestionCuerpoTecnico.guardarJSON();


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