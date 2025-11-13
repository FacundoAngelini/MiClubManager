package Clase.Club;

import Clase.Gestiones.*;
import Clase.Productos.Inventario;
import Clase.Productos.Producto;

public class Club {
    private GestionJugadores gestionJugadores;
    private GestionCuerpoTecnico gestionCuerpoTecnico;
    private GestionSocio gestionSocios;
    private GestorPartido gestorPartidos;
    private Inventario<Producto> inventario;
    private GestionEstadio gestionEstadios;
    private GestionPresupuesto gestionPresupuesto;

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