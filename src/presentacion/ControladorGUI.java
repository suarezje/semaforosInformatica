/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Estudiantes
 */
public class ControladorGUI implements ActionListener{
    
    private  Vista ventana;
    private  VistaInicial ventanaInicial;
    private  VistaSemaforo semaforo;

    public ControladorGUI(Vista aThis) {
        ventana = aThis;
    }
    
    public ControladorGUI(VistaInicial aThis) {
        ventanaInicial = aThis;
    }
    
    public ControladorGUI(VistaSemaforo aThis, Vista vista) {
        semaforo = aThis;
        ventana = vista;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jbuttonTmp = (JButton)e.getSource();
        
        if(jbuttonTmp.getText().equals("CONECTARSE")){
            getVentanaInicial().getModelo().conectar();
        }else if(jbuttonTmp.getName().contains("sfln")){
            short linea = new Integer(jbuttonTmp.getName().split("-")[0].substring(4)).shortValue();
            short semaforo = new Integer(jbuttonTmp.getName().split("-")[1]).shortValue();
            short luz = new Integer(jbuttonTmp.getName().split("-")[2]).shortValue();
            getVentana().getModelo().reportarFallaSemaforo(linea, semaforo, luz);
        }
        
    }

    public Vista getVentana() {
        return ventana;
    }

    public VistaInicial getVentanaInicial() {
        return ventanaInicial;
    }

    public void setVentanaInicial(VistaInicial ventanaInicial) {
        this.ventanaInicial = ventanaInicial;
    }
   }
