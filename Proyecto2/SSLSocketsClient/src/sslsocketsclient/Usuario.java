/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sslsocketsclient;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author karol
 */
public class Usuario implements Serializable{
    String _nombre;
    String _pass;
    Double _saldo;
    ArrayList<Usuario> _listaClientes;

    public Usuario() {        
    }

    public Usuario(String _nombre, String _pass, Double _saldo) {
        this._nombre = _nombre;
        this._pass = _pass;
        this._saldo = _saldo;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String getPass() {
        return _pass;
    }

    public void setPass(String _pass) {
        this._pass = _pass;
    }

    /**
     * Retorna la lista de Clientes
     */
    public ArrayList<Usuario> getListaClientes() {
        return _listaClientes;
    }

     /**
     * Asigna la lista de Clientes
     */
    public void setListaClientes(ArrayList<Usuario> _listaClientes) {
        this._listaClientes = _listaClientes;
    }   
    
    /**
     * Imprime la lista de Clientes
     */
    public void imprimirLista(){
        for (Usuario nodoActual : this._listaClientes)
        {
            System.out.println("Nombre: " + nodoActual.getNombre());
            System.out.println("Pass: "+ nodoActual.getPass());
            System.out.println("Saldo: "+ nodoActual.getSaldo());
        }
    }

    public Double getSaldo() {
        return _saldo;
    }

    public void setSaldo(Double _saldo) {
        this._saldo = _saldo;
    }
    
    
}
