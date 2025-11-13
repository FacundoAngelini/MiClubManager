package Clases_Manu;

import exeptions.AccionImposible;
import exeptions.ElementoInexistenteEx;

import java.util.ArrayList;

public interface MetodosComunes<T,K> {
    void eliminarElemento(K id) throws AccionImposible;
    T devuelveElemento(K key) throws AccionImposible;
    boolean existe(K id) throws ElementoInexistenteEx;
    ArrayList<T>listar();
    void guardarJSON();
}
