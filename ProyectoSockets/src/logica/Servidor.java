package logica;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor implements Runnable {
	
	private ServerSocket server;
	private ArrayList<Conexion> conexiones;
	private Socket socketAux;
	private String direccionIp;
	private int puerto;
	
	private boolean parar;
	private boolean pausar;
	private Thread thread;
	private long speed;

	public long getSpeed() {
		return this.speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	
	public Servidor() {
		// TODO Auto-generated constructor stub
		pausar = false;
		parar = false;
		puerto=4000;
		conexiones= new ArrayList<Conexion>();
		thread = new Thread(this);
		iniciarServidor();
		start();
		
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		 while (!parar) {
				
			 System.out.println("esperando conexion");
			 
			 try {
				socketAux=server.accept();
				conexiones.add( new Conexion(socketAux));
				System.out.println("nueva conexion aceptada");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				

				try {
					Thread.sleep(speed);
				} catch (Exception e) {
					e.printStackTrace();
				}

				synchronized (this) {
					while (pausar)

						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					if (parar)
						break;
				}
			}
	
		
		
		
		
		
	}
	
	public void iniciarServidor(){
		if (server==null) {
			try {
				server = new ServerSocket(puerto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void cerrarConexion(){
		for (int i = 0; i <conexiones.size(); i++) {
			conexiones.get(i).cerrarConexion();
		}
	}
	
	public void start() {
		thread.start();
	}

	synchronized void stop() {
		parar = true;
		pausar = false;
		notify();
	}
	synchronized void suspend() {
		pausar = true;

	}

	synchronized void resume() {
		pausar = false;
		notify();
	}

}
