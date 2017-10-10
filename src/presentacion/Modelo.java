/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.Dimension;
import java.awt.GridLayout;
import static java.lang.Thread.sleep;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import logica.Semaforo;
import logica.MiSistemaSemaforo;
import utils.Constantes;

/**
 *
 * @author Estudiantes
 */
public class Modelo implements Runnable{
    private MiSistemaSemaforo miSistemaSemaforo;
    private Vista ventanaPrincipal;
    private VistaInicial ventanaInicial;
    
    private short cantSemaforosL1;
    private short cantSemaforosL2;
    private String idCliente;
    private String ipServidor;
    private int puerto;

    private Thread hiloActualizacion;
    private Thread hiloIntermitencia;
    
    public Modelo() {
        hiloActualizacion = new Thread(this);
        hiloIntermitencia = new Thread(new HiloIntermitencia(getVentanaPrincipal()));
    }
    
    public Vista getVentanaPrincipal() {
        if(ventanaPrincipal == null){
            ventanaPrincipal = new Vista(this);
        }
        return ventanaPrincipal;
    }

    public void setVentanaPricipal(Vista ventanaPricipal) {
        this.ventanaPrincipal = ventanaPricipal;
    }
    
    public MiSistemaSemaforo getMiSistemaSemaforo() {
        if(miSistemaSemaforo == null){
            miSistemaSemaforo = new MiSistemaSemaforo();
        }
        return miSistemaSemaforo;
    }

    public void setMiSistemaSemaforo(MiSistemaSemaforo miSistemaSemaforo) {
        this.miSistemaSemaforo = miSistemaSemaforo;
    }

    public VistaInicial getVentanaInicial() {
        
        if(ventanaInicial == null){
            ventanaInicial = new VistaInicial(this);
        }
        
        return ventanaInicial;
    }

    public void setVentanaInicial(VistaInicial ventanaInicial) {
        this.ventanaInicial = ventanaInicial;
    }

    public void dibujarLineaSemaforo(){        
        getVentanaPrincipal().getLblTitulo().setText("ESTE ES EL PUNTO SEMAFÓRICO: "+this.idCliente);
        GridLayout layout = new GridLayout(0,Integer.max(cantSemaforosL2,cantSemaforosL1),1,50);
        getVentanaPrincipal().getPanelContenido().setLayout(layout);
        getVentanaPrincipal().getPanelContenido().setPreferredSize(new Dimension(73,98));
        if(cantSemaforosL1>cantSemaforosL2){
            for(int i=0; i < cantSemaforosL1; i++){
                Dimension d = new Dimension(73,98);
                VistaSemaforo vistaSemaforo = new presentacion.VistaSemaforo(i,1, getVentanaPrincipal());
                vistaSemaforo.setName("sfln1-"+i);
                vistaSemaforo.getRojo().setName("sfln1-"+i+"-"+Constantes.ROJO);
                vistaSemaforo.getAmarillo().setName("sfln1-"+i+"-"+Constantes.AMARILLO);
                vistaSemaforo.getVerde().setName("sfln1-"+i+"-"+Constantes.VERDE);

                //vistaSemaforo.getAmarillo().setText("Semaforo L1: "+(cantSemaforosL1-i));
                vistaSemaforo.setMaximumSize(d);
                getVentanaPrincipal().getPanelContenido().add(vistaSemaforo);
            }
            for(int i=0; i < cantSemaforosL2; i++){
                Dimension d = new Dimension(73,98);
                VistaSemaforo vistaSemaforo = new presentacion.VistaSemaforo(i,2,getVentanaPrincipal());
                vistaSemaforo.setName("sfln2-"+i);
                vistaSemaforo.getRojo().setName("sfln2-"+i+"-"+Constantes.ROJO);
                vistaSemaforo.getAmarillo().setName("sfln2-"+i+"-"+Constantes.AMARILLO);
                vistaSemaforo.getVerde().setName("sfln2-"+i+"-"+Constantes.VERDE);
                
                //vistaSemaforo.getAmarillo().setText("Semaforo L2: "+(cantSemaforosL2-i));
                vistaSemaforo.setPreferredSize(d);
                getVentanaPrincipal().getPanelContenido().add(vistaSemaforo);
            }
        }else{
            for(int i=0; i < cantSemaforosL2; i++){
                Dimension d = new Dimension(73,98);
                VistaSemaforo vistaSemaforo = new presentacion.VistaSemaforo(i,2,getVentanaPrincipal());
                vistaSemaforo.setName("sfln2-"+i);
                vistaSemaforo.getRojo().setName("sfln2-"+i+"-"+Constantes.ROJO);
                vistaSemaforo.getAmarillo().setName("sfln2-"+i+"-"+Constantes.AMARILLO);
                vistaSemaforo.getVerde().setName("sfln2-"+i+"-"+Constantes.VERDE);
                
                //vistaSemaforo.getAmarillo().setText("Semaforo L2: "+(cantSemaforosL2-i));
                vistaSemaforo.setPreferredSize(d);
                getVentanaPrincipal().getPanelContenido().add(vistaSemaforo);
            }
            for(int i=0; i < cantSemaforosL1; i++){
                Dimension d = new Dimension(73,98);
                VistaSemaforo vistaSemaforo = new presentacion.VistaSemaforo(i,1,getVentanaPrincipal());
                vistaSemaforo.setName("sfln1-"+i);
                vistaSemaforo.getRojo().setName("sfln1-"+i+"-"+Constantes.ROJO);
                vistaSemaforo.getAmarillo().setName("sfln1-"+i+"-"+Constantes.AMARILLO);
                vistaSemaforo.getVerde().setName("sfln1-"+i+"-"+Constantes.VERDE);
                
                //vistaSemaforo.getAmarillo().setText("Semaforo L1: "+(cantSemaforosL1-i));
                vistaSemaforo.setPreferredSize(d);
                getVentanaPrincipal().getPanelContenido().add(vistaSemaforo);
            }
        }
      
        getVentanaPrincipal().setVisible(true);
        hiloActualizacion.start();
        hiloIntermitencia.start();
        
    }
    
    public void actualizarSemaforos(){
        List<Semaforo> linea1 = getMiSistemaSemaforo().getLinea1();
        List<Semaforo> linea2 = getMiSistemaSemaforo().getLinea2();
        
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
            Semaforo tmp = linea1.get(i);
            for(int j=0; j < getVentanaPrincipal().getPanelContenido().getComponentCount(); j++){
                VistaSemaforo vistaSemaforo = (VistaSemaforo)getVentanaPrincipal().getPanelContenido().getComponent(j);
                if(vistaSemaforo.getName().contains("sfln1")){
                    System.out.println("Semaforo: "+vistaSemaforo.getName()+"-->"+tmp.getEstadoLuces());
                    switch (tmp.getRojo()) {
                        case Constantes.ENCENDIDO:
                            vistaSemaforo.getRojo().setIcon(imageRoja);
                            break;
                        case Constantes.DANADO:
                            vistaSemaforo.getRojo().setIcon(imageGris);
                            break;
                        default:
                            vistaSemaforo.getRojo().setIcon(imageNegra);
                            break;
                    }
                    
                    switch (tmp.getAmarillo()) {
                        case Constantes.ENCENDIDO:
                            vistaSemaforo.getAmarillo().setIcon(imageAmarilla);
                            break;
                        case Constantes.DANADO:
                            vistaSemaforo.getAmarillo().setIcon(imageGris);
                            break;
                        default:
                            vistaSemaforo.getAmarillo().setIcon(imageNegra);
                            break;
                    }
                    
                    switch (tmp.getVerde()) {
                        case Constantes.ENCENDIDO:
                            vistaSemaforo.getVerde().setIcon(imageVerde);
                            break;
                        case Constantes.DANADO:
                            vistaSemaforo.getVerde().setIcon(imageGris);
                            break;
                        default:
                            vistaSemaforo.getVerde().setIcon(imageNegra);
                            break;
                    }
                }
            }
        }
        
        //Recorrer linea 2
        for(int i=0; i < linea2.size(); i++){
            Semaforo tmp = linea2.get(i);
            for(int j=0; j < getVentanaPrincipal().getPanelContenido().getComponentCount(); j++){
                VistaSemaforo vistaSemaforo = (VistaSemaforo)getVentanaPrincipal().getPanelContenido().getComponent(j);
                if(vistaSemaforo.getName().contains("sfln2")){
                    System.out.println("Semaforo: "+vistaSemaforo.getName()+"-->"+tmp.getEstadoLuces());
                    switch (tmp.getRojo()) {
                        case Constantes.ENCENDIDO:
                            vistaSemaforo.getRojo().setIcon(imageRoja);
                            break;
                        case Constantes.DANADO:
                            vistaSemaforo.getRojo().setIcon(imageGris);
                            break;
                        default:
                            vistaSemaforo.getRojo().setIcon(imageNegra);
                            break;
                    }
                    
                    switch (tmp.getAmarillo()) {
                        case Constantes.ENCENDIDO:
                            vistaSemaforo.getAmarillo().setIcon(imageAmarilla);
                            break;
                        case Constantes.DANADO:
                            vistaSemaforo.getAmarillo().setIcon(imageGris);
                            break;
                        default:
                            vistaSemaforo.getAmarillo().setIcon(imageNegra);
                            break;
                    }
                    
                    switch (tmp.getVerde()) {
                        case Constantes.ENCENDIDO:
                            vistaSemaforo.getVerde().setIcon(imageVerde);
                            break;
                        case Constantes.DANADO:
                            vistaSemaforo.getVerde().setIcon(imageGris);
                            break;
                        default:
                            vistaSemaforo.getVerde().setIcon(imageNegra);
                            break;
                    }
                }
            }
        }
    }
    
    public void iniciarLineas(){
        getVentanaInicial().setVisible(true);
        getVentanaPrincipal();
    }
    
    public void conectar(){  
        idCliente = getVentanaInicial().getTxtIDCliente().getText();
        ipServidor = getVentanaInicial().getTxtIP().getText();
        try{
            puerto = Integer.valueOf(getVentanaInicial().getTxtPuerto().getText());
            cantSemaforosL1 = new Integer (getVentanaInicial().getTxtNumSemaforosL1().getText()).shortValue();
            cantSemaforosL2 = new Integer (getVentanaInicial().getTxtNumSemaforosL2().getText()).shortValue();
           
            if((idCliente == null || idCliente.equals("")) || (ipServidor == null || ipServidor.equals("")) || puerto == 0 || cantSemaforosL1 == 0 || cantSemaforosL1 == 0){
                pintarMensajeAdvertencia("Por favor diligencie todo el formulario");
                return;
            }
            
            String respuestaConexion = "000";
            respuestaConexion = getMiSistemaSemaforo().inscribirse(ipServidor, puerto, idCliente, cantSemaforosL1, cantSemaforosL2);
            //Se conecto
            switch (respuestaConexion) {
                case "000":
                    getVentanaInicial().setVisible(false);
                    dibujarLineaSemaforo();
                    break;
                //Cliente ya existe
                case "001":            
                    pintarMensajeAdvertencia("El cliente ya existe. Por favor indique un ID de cliente diferente");
                    break;
                //Error general
                default:
                    pintarMensajeError("Ha ocurrido un error al intentar conectarse al servidor");
                    break;
            }
        }catch (NumberFormatException e){
            pintarMensajeAdvertencia("Por favor ingrese un valor entero");
            System.out.println("Formato de número inválido");
        }
    }
    
    
    public void reportarFallaSemaforo(short linea, short semaforo, short luz){
        Class classname = MiSistemaSemaforo.class;
        ClassLoader loader = classname.getClassLoader();
        String pn = classname.getPackage().getName();
        String urlPathGris = pn + "/img/luzGris.png";
        java.net.URL imageURLGris = loader.getResource(urlPathGris);
        ImageIcon imageGris = new ImageIcon(imageURLGris);
        
        String nombreBoton = "sfln"+linea+"-"+semaforo+"-"+luz;
        for(int j=0; j < getVentanaPrincipal().getPanelContenido().getComponentCount(); j++){
            VistaSemaforo vistaSemaforo = (VistaSemaforo)getVentanaPrincipal().getPanelContenido().getComponent(j);
            if(vistaSemaforo.getRojo().getName().equals(nombreBoton)){
                vistaSemaforo.getRojo().setIcon(imageGris);
                return;
            }
            if(vistaSemaforo.getAmarillo().getName().equals(nombreBoton)){
                vistaSemaforo.getAmarillo().setIcon(imageGris);
                return;
            }
            if(vistaSemaforo.getVerde().getName().equals(nombreBoton)){
                vistaSemaforo.getVerde().setIcon(imageGris);
                return;
            }
        }
        //miSistemaSemaforo.reportarFallaSemaforo(linea, semaforo, luz);
    }
    
    private void pintarMensajeError(String mensaje){
        JOptionPane.showMessageDialog(getVentanaInicial(), mensaje, "Semaforos", JOptionPane.ERROR_MESSAGE);
    }
    
    private void pintarMensajeAdvertencia(String mensaje){
        JOptionPane.showMessageDialog(getVentanaInicial(), mensaje, "Semaforos", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void run() {
        while(true){
            try {
                sleep(20000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            }
            actualizarSemaforos();
        }
    }
    
}
