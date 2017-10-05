/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import logica.Semaforo;
import logica.SocketCliente;

/**
 *
 * @author Estudiantes
 */
public class Modelo {
    private SocketCliente socketCliente;
    private Vista ventanaPrincipal;
    private VistaInicial ventanaInicial;
    
    private short cantSemaforosL1;
    private short cantSemaforosL2;
    private String idCliente;
    private String ipServidor;
    private int puerto;

    public Modelo() {
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
    
    public SocketCliente getSocketCliente() {
        return socketCliente;
    }

    public void setSocketCliente(SocketCliente socketCliente) {
        this.socketCliente = socketCliente;
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
        GridLayout layout = new GridLayout(0,Integer.max(cantSemaforosL2,cantSemaforosL1),1,1);
        getVentanaPrincipal().getPanelContenido().setLayout(layout);
        getVentanaPrincipal().getPanelContenido().setPreferredSize(new Dimension(73,98));
        if(cantSemaforosL1>cantSemaforosL2){
            for(int i=0; i < cantSemaforosL1; i++){
                Dimension d = new Dimension(73,98);
                VistaSemaforo vistaSemaforo = new presentacion.VistaSemaforo(i,1);
                vistaSemaforo.setName("sfln1-"+i);
                //vistaSemaforo.getAmarillo().setText("Semaforo L1: "+(cantSemaforosL1-i));
                vistaSemaforo.setMaximumSize(d);
                getVentanaPrincipal().getPanelContenido().add(vistaSemaforo);
            }
            for(int i=0; i < cantSemaforosL2; i++){
                Dimension d = new Dimension(73,98);
                VistaSemaforo vistaSemaforo = new presentacion.VistaSemaforo(i,2);
                vistaSemaforo.setName("sfln2-"+i);
                //vistaSemaforo.getAmarillo().setText("Semaforo L2: "+(cantSemaforosL2-i));
                vistaSemaforo.setPreferredSize(d);
                getVentanaPrincipal().getPanelContenido().add(vistaSemaforo);
            }
        }else{
            for(int i=0; i < cantSemaforosL2; i++){
                Dimension d = new Dimension(73,98);
                VistaSemaforo vistaSemaforo = new presentacion.VistaSemaforo(i,2);
                vistaSemaforo.setName("sfln2-"+i);
                //vistaSemaforo.getAmarillo().setText("Semaforo L2: "+(cantSemaforosL2-i));
                vistaSemaforo.setPreferredSize(d);
                getVentanaPrincipal().getPanelContenido().add(vistaSemaforo);
            }
            for(int i=0; i < cantSemaforosL1; i++){
                Dimension d = new Dimension(73,98);
                VistaSemaforo vistaSemaforo = new presentacion.VistaSemaforo(i,1);
                vistaSemaforo.setName("sfln1-"+i);
                //vistaSemaforo.getAmarillo().setText("Semaforo L1: "+(cantSemaforosL1-i));
                vistaSemaforo.setPreferredSize(d);
                getVentanaPrincipal().getPanelContenido().add(vistaSemaforo);
            }
        }
      
        getVentanaPrincipal().setVisible(true);
    }
    
    public void actualizarSemaforos(){
        List<Semaforo> linea1 = getSocketCliente().getLinea1();
        List<Semaforo> linea2 = getSocketCliente().getLinea2();
        
        Class classname = SocketCliente.class;
        ClassLoader loader = classname.getClassLoader();
        
        String pn = classname.getPackage().getName();
        String urlPathRoja = pn + "/img/luzRoja.png";
        String urlPathAmarilla = pn + "/img/luzAmarilla.png";
        String urlPathVerde = pn + "/img/luzVerde.png";
        String urlPathNegra = pn + "/img/luzNegra.png";
        
        java.net.URL imageURLRoja = loader.getResource(urlPathRoja);
        java.net.URL imageURLAmarilla = loader.getResource(urlPathAmarilla);
        java.net.URL imageURLVerde = loader.getResource(urlPathVerde);
        java.net.URL imageURLNegra = loader.getResource(urlPathNegra);
        
        ImageIcon imageRoja = new ImageIcon(imageURLRoja);
        ImageIcon imageAmarilla = new ImageIcon(imageURLAmarilla);
        ImageIcon imageVerde = new ImageIcon(imageURLVerde);
        ImageIcon imageNegra = new ImageIcon(imageURLNegra);
        //Recorrer linea 1
        for(int i=0; i < linea1.size(); i++){
            Semaforo tmp = linea1.get(i);
            for(int j=0; j < getVentanaPrincipal().getPanelContenido().getComponentCount(); j++){
                VistaSemaforo vistaSemaforo = (VistaSemaforo)getVentanaPrincipal().getPanelContenido().getComponent(j);
                if(vistaSemaforo.getName().contains("sfln1")){
                    if(tmp.getRojo() == 1){
                        vistaSemaforo.getRojo().setIcon(imageRoja);
                    }else{
                        vistaSemaforo.getRojo().setIcon(imageNegra);
                    }
                    
                    if(tmp.getAmarillo() == 1){
                        vistaSemaforo.getAmarillo().setIcon(imageAmarilla);
                    }else{
                        vistaSemaforo.getAmarillo().setIcon(imageNegra);
                    }
                    
                    if(tmp.getVerde() == 1){
                        vistaSemaforo.getjButton1().setIcon(imageVerde);
                    }else{
                        vistaSemaforo.getjButton1().setIcon(imageNegra);
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
            //respuestaConexion = getSocketCliente().inicializarSemaforos(idCliente, cantSemaforosL1, cantSemaforosL2, ipServidor, puerto);
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
    
    private void pintarMensajeError(String mensaje){
        JOptionPane.showMessageDialog(getVentanaInicial(), mensaje, "Semaforos", JOptionPane.ERROR_MESSAGE);
    }
    
    private void pintarMensajeAdvertencia(String mensaje){
        JOptionPane.showMessageDialog(getVentanaInicial(), mensaje, "Semaforos", JOptionPane.WARNING_MESSAGE);
    }
}
