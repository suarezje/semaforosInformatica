/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Estudiantes
 */
public class ControladorGUI implements ActionListener{
    
    private  Vista ventana;
    private  VistaInicial ventanaInicial;

    public ControladorGUI(Vista aThis) {
        ventana = aThis;
    }
    
    public ControladorGUI(VistaInicial aThis) {
        ventanaInicial = aThis;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        getVentanaInicial().getModelo().conectar();
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
