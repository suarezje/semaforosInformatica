/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import static java.lang.Thread.sleep;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import logica.MiSistemaSemaforo;
import logica.Semaforo;

/**
 *
 * @author SuarezJE
 */
public class HiloIntermitencia implements Runnable{
    private Vista vista;
    
    public HiloIntermitencia (Vista vista){
        this.vista = vista;
    }
    @Override
    public void run() {
        while(true){
            try {
                sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloIntermitencia.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            List<Semaforo> linea1 = vista.getModelo().getMiSistemaSemaforo().getLinea1();
            List<Semaforo> linea2 = vista.getModelo().getMiSistemaSemaforo().getLinea2();

            Class classname = MiSistemaSemaforo.class;
            ClassLoader loader = classname.getClassLoader();

            String pn = classname.getPackage().getName();
            String urlPathRoja = pn + "/img/luzRoja.png";
            String urlPathAmarilla = pn + "/img/luzAmarilla.png";
            String urlPathVerde = pn + "/img/luzVerde.png";
            String urlPathNegra = pn + "/img/luzNegra.png";
            String urlPathGris = pn + "/img/luzGris.png";

            java.net.URL imageURLRoja = loader.getResource(urlPathRoja);
            java.net.URL imageURLAmarilla = loader.getResource(urlPathAmarilla);
            java.net.URL imageURLVerde = loader.getResource(urlPathVerde);
            java.net.URL imageURLNegra = loader.getResource(urlPathNegra);
            java.net.URL imageURLGris = loader.getResource(urlPathGris);

            ImageIcon imageRoja = new ImageIcon(imageURLRoja);
            imageRoja.setDescription("ROJA");
            ImageIcon imageAmarilla = new ImageIcon(imageURLAmarilla);
            imageAmarilla.setDescription("AMARILLA");
            ImageIcon imageVerde = new ImageIcon(imageURLVerde);
            imageVerde.setDescription("VERDE");
            ImageIcon imageNegra = new ImageIcon(imageURLNegra);
            imageNegra.setDescription("NEGRA");
            ImageIcon imageGris = new ImageIcon(imageURLGris);
            imageGris.setDescription("GRIS");

            //Recorrer linea 1
            for(int i=0; i < linea1.size(); i++){
                for(int j=0; j < vista.getPanelContenido().getComponentCount(); j++){
                    VistaSemaforo vistaSemaforo = (VistaSemaforo)vista.getPanelContenido().getComponent(j);
                    if(((ImageIcon)vistaSemaforo.getRojo().getIcon()).getDescription().equals("ROJA")){
                        vistaSemaforo.getRojo().setIcon(imageNegra);
                    }else{
                        vistaSemaforo.getRojo().setIcon(imageRoja);
                    }

                    if(((ImageIcon)vistaSemaforo.getAmarillo().getIcon()).getDescription().equals("AMARILLA")){
                        vistaSemaforo.getAmarillo().setIcon(imageNegra);
                    }else{
                        vistaSemaforo.getAmarillo().setIcon(imageAmarilla);
                    }

                    if(((ImageIcon)vistaSemaforo.getVerde().getIcon()).getDescription().equals("VERDE")){
                        vistaSemaforo.getVerde().setIcon(imageNegra);
                    }else{
                        vistaSemaforo.getVerde().setIcon(imageVerde);
                    }
                }
            }

            //Recorrer linea 2
            for(int i=0; i < linea2.size(); i++){
                for(int j=0; j < vista.getPanelContenido().getComponentCount(); j++){
                    VistaSemaforo vistaSemaforo = (VistaSemaforo)vista.getPanelContenido().getComponent(j);
                    if(((ImageIcon)vistaSemaforo.getRojo().getIcon()).getDescription().equals("ROJA")){
                        vistaSemaforo.getRojo().setIcon(imageNegra);
                    }else{
                        vistaSemaforo.getRojo().setIcon(imageRoja);
                    }

                    if(((ImageIcon)vistaSemaforo.getAmarillo().getIcon()).getDescription().equals("AMARILLA")){
                        vistaSemaforo.getAmarillo().setIcon(imageNegra);
                    }else{
                        vistaSemaforo.getAmarillo().setIcon(imageAmarilla);
                    }

                    if(((ImageIcon)vistaSemaforo.getVerde().getIcon()).getDescription().equals("VERDE")){
                        vistaSemaforo.getVerde().setIcon(imageNegra);
                    }else{
                        vistaSemaforo.getVerde().setIcon(imageVerde);
                    }
                }
            }
        }
        
    }
    
}
