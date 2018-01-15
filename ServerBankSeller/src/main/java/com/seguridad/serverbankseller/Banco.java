/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.serverbankseller;

/**
 *
 * @author Elberg
 */
public class Banco {
    private String _cliente;
    private int  _saldo;
    private int _transferencia;

    public Banco() {
    }

    public Banco(String _cliente, int _saldo, int _transferencia) {
        this._cliente = _cliente;
        this._saldo = _saldo;
        this._transferencia = _transferencia;
    }

    public String getCliente() {
        return _cliente;
    }

    public void setCliente(String _cliente) {
        this._cliente = _cliente;
    }

    public int getSaldo() {
        return _saldo;
    }

    public void setSaldo(int _saldo) {
        this._saldo = _saldo;
    }

    public int getTransferencia() {
        return _transferencia;
    }

    public void setTransferencia(int _transferencia) {
        this._transferencia = _transferencia;
    }
    
    
    
}
