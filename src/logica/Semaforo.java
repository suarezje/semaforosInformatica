/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import utils.Constantes;

/**
 *
 * @author Estudiantes
 */
public class Semaforo {
    private short rojo;
    private short amarillo;
    private short verde;
    private Boolean estaIntermitente;

    public Semaforo() {
        rojo = Constantes.ENCENDIDO;
        amarillo = Constantes.ENCENDIDO;
        verde = Constantes.ENCENDIDO;
        estaIntermitente = Boolean.TRUE;
    }

    public short getRojo() {
        return rojo;
    }

    public void setRojo(short rojo) {
        this.rojo = rojo;
    }

    public short getAmarillo() {
        return amarillo;
    }

    public void setAmarillo(short amarillo) {
        this.amarillo = amarillo;
    }

    public short getVerde() {
        return verde;
    }

    public void setVerde(short verde) {
        this.verde = verde;
    }

    public Boolean getEstaIntermitente() {
        return estaIntermitente;
    }

    public void setEstaIntermitente(Boolean estaIntermitente) {
        this.estaIntermitente = estaIntermitente;
    }
 
}
