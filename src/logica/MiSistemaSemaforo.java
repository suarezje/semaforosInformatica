/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;

import utils.Constantes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Estudiantes
 */
public class MiSistemaSemaforo implements Runnable {

	private List<Semaforo> linea1;
	private List<Semaforo> linea2;
	private String idCliente;

	private Socket socketListener;
	private DataInputStream inListener;
	private DataOutputStream outListener;
	private Thread hiloListener;

	private ServerSocket serverSocketAlarmas;
	private Socket socketAlarmas;
	private DataOutputStream outAlarmas;

	public MiSistemaSemaforo() {

		linea1 = new ArrayList<>();
		linea2 = new ArrayList<>();
		hiloListener = new Thread(this);
	}

	public String inscribirse(String Ip, int puerto, String idcliente, short cantSemaforoL1, short cantSemaforoL2) {

		this.idCliente = idcliente;
		String rta = null;

		try {

			this.socketListener = new Socket(Ip, puerto);
			this.inListener = new DataInputStream(this.socketListener.getInputStream());
			this.outListener = new DataOutputStream(this.socketListener.getOutputStream());

			inicializarSemaforos(1, cantSemaforoL1);
			inicializarSemaforos(2, cantSemaforoL2);

			String comandoIncio = "C|" + idcliente + ":" + this.linea1.size() + "," + this.linea2.size();
			this.outListener.writeUTF(comandoIncio);

			String respuestaLogin = this.inListener.readUTF();

			String encabezado[] = respuestaLogin.split("[|]");

			if (encabezado[0].equals("R")) {
				rta = "000";
				crearSocketAlertas(105789);
			} else if (encabezado[0].equals("X")) {
				rta = "001";
			}

			this.hiloListener.start();
			this.outListener.close();

		} catch (Exception e) {
			return "999";
		}
		return rta;

	}

	private void inicializarSemaforos(int linea, short cantSemaforos) {

		if (linea == 1) {
			for (int i = 0; i < cantSemaforos; i++) {
				this.linea1.add(new Semaforo());
			}
		} else if (linea == 2) {
			for (int i = 0; i < cantSemaforos; i++) {
				this.linea2.add(new Semaforo());
			}
		}
	}

	private void crearSocketAlertas(int puerto) {

		this.serverSocketAlarmas = null;
		try {

			System.out.println("Inicia Alarmas");
			this.serverSocketAlarmas = new ServerSocket(puerto);
			this.socketAlarmas = this.serverSocketAlarmas.accept();

			this.outAlarmas = new DataOutputStream(this.socketAlarmas.getOutputStream());

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void reportarFallaSemaforo(short linea, short semaforo, short luz) {

		if (linea == 1) {
			if (luz == Constantes.ROJO) {
				this.linea1.get(semaforo).setRojo(Constantes.DANADO);
			}
			if (luz == Constantes.AMARILLO) {
				this.linea1.get(semaforo).setAmarillo(Constantes.DANADO);
			}
			if (luz == Constantes.VERDE) {
				this.linea1.get(semaforo).setVerde(Constantes.DANADO);
			}
		}

		if (linea == 2) {
			if (luz == Constantes.ROJO) {
				this.linea2.get(semaforo).setRojo(Constantes.DANADO);
			}
			if (luz == Constantes.AMARILLO) {
				this.linea2.get(semaforo).setAmarillo(Constantes.DANADO);
			}
			if (luz == Constantes.VERDE) {
				this.linea2.get(semaforo).setVerde(Constantes.DANADO);
			}
		}

		int l1roja = 0;
		int l1amarillo = 0;
		int l1verde = 0;

		int l2roja = 0;
		int l2amarillo = 0;
		int l2verde = 0;

		for (int i = 1; i <= this.linea1.size(); i++) {
			if (this.linea1.get(i).getRojo() != Constantes.DANADO) {
				l1roja++;
			}
			if (this.linea1.get(i).getAmarillo() != Constantes.DANADO) {
				l1amarillo++;
			}
			if (this.linea1.get(i).getVerde() != Constantes.DANADO) {
				l1verde++;
			}
		}

		for (int i = 1; i <= this.linea2.size(); i++) {
			if (this.linea2.get(i).getRojo() != Constantes.DANADO) {
				l2roja++;
			}
			if (this.linea2.get(i).getAmarillo() != Constantes.DANADO) {
				l2amarillo++;
			}
			if (this.linea2.get(i).getVerde() != Constantes.DANADO) {
				l2verde++;
			}
		}

		String reporteAlarmas = "B|" + this.idCliente + ":" + String.valueOf(l1roja) + "," + String.valueOf(l1amarillo)
				+ "," + String.valueOf(l1verde) + "-" + String.valueOf(l2roja) + "," + String.valueOf(l2amarillo) + ","
				+ String.valueOf(l2verde);

		try {
			this.outAlarmas.writeUTF(reporteAlarmas);
		} catch (IOException e) {
			System.out.println("Error reportando reporte de luces buenas");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		String response;

		while (true) {
			try {
				response = this.inListener.readUTF();

				String encabezado[] = response.split("[|]");

				if (encabezado[0].equals("E")) {
					String lineas[] = encabezado[1].split("[-]");
					String lucesSemaforoL1[] = lineas[0].split(",");
					prenderLuces(1, lucesSemaforoL1);
					String lucesSemaforoL2[] = lineas[1].split(",");
					prenderLuces(2, lucesSemaforoL2);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void prenderLuces(int linea, String[] luces) {

		if (linea == 1) {
			for (int i = 1; i <= this.linea1.size(); i++) {
				this.linea1.get(i).setRojo(Short.valueOf(luces[0]));
				this.linea1.get(i).setAmarillo(Short.valueOf(luces[1]));
				this.linea1.get(i).setVerde(Short.valueOf(luces[2]));

				if (luces[3].equals("1")) {
					this.linea1.get(i).setEstaIntermitente(Boolean.TRUE);
				}
			}
		} else if (linea == 2) {
			for (int i = 1; i <= this.linea1.size(); i++) {
				this.linea2.get(i).setRojo(Short.valueOf(luces[0]));
				this.linea2.get(i).setAmarillo(Short.valueOf(luces[1]));
				this.linea2.get(i).setVerde(Short.valueOf(luces[2]));

				if (luces[3].equals("1")) {
					this.linea2.get(i).setEstaIntermitente(Boolean.TRUE);
				}
			}
		}
	}

	public List<Semaforo> getLinea1() {
		return linea1;
	}

	public void setLinea1(List<Semaforo> linea1) {
		this.linea1 = linea1;
	}

	public List<Semaforo> getLinea2() {
		return linea2;
	}

	public void setLinea2(List<Semaforo> linea2) {
		this.linea2 = linea2;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
}
