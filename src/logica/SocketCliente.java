/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Estudiantes
 */
public class SocketCliente {
    private List<Semaforo> linea1;
    private List<Semaforo> linea2;

    public SocketCliente() {
        
        linea1 = new ArrayList<>();
        linea2 = new ArrayList<>();
        
    }
    
    public String inscribirse(){
    
        return "";
    }
    
    public String inicializarSemaforos(short cantSemaforoL1, short cantSemaforoL2){
        return "";
    }
    
    /* Reportar al servidor las luces buenas
       Actualizar la lista con las luces buenas
    */
    public void reportarFallaSemaforo(short linea, short semaforo, short luz){
    
    }

    public List getLinea1() {
        return linea1;
    }

    public void setLinea1(List linea1) {
        this.linea1 = linea1;
    }

    public List getLinea2() {
        return linea2;
    }

    public void setLinea2(List linea2) {
        this.linea2 = linea2;
    }
}
