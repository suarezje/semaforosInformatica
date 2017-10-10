/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsemaforo;

import presentacion.Modelo;

/**
 *
 * @author Estudiantes
 */
public class launcher {

    private Modelo modelo;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       new launcher();
    }

    public launcher() { 
        this.modelo = new Modelo();
        this.modelo.iniciarLineas();
    }
     
}
