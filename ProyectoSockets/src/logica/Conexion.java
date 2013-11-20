package logica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.server.SocketSecurityException;

public class Conexion implements Runnable {

	private Socket socketConexion;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;

	private boolean parar;
	private boolean pausar;
	private int opccion;

	private Thread thread;
	private long speed;

	public long getSpeed() {
		return this.speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}


	public Conexion(Socket socket) {
		// TODO Auto-generated constructor stub
		pausar = false;
		parar = false;
		speed= 1000;
		

		this.socketConexion=socket;

		try {
			inputStream=new DataInputStream(socketConexion.getInputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("error creando el canal de entrada ");

		}

		try {
			outputStream=new DataOutputStream(socketConexion.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			System.out.println("error creando el canal de salida");
		}
		iniciarComunicacion();
		thread= new Thread(this);
		start();
	}

	@Override
	public void run() {
		while (!parar) {

			try {
				opccion = inputStream.readInt();
			} catch (IOException e1) {
				//e1.printStackTrace();
				System.out.println("no se recibio la opccion");
			}

			switch (opccion) {
			case 1:
				try {
					System.out.println(inputStream.readUTF());
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
				break;
			case 2:

				break;
			case 3:

				break;

			default:
				break;
			}

			synchronized (this) {
				while (pausar)

					try {
						wait();
					} catch (InterruptedException e) {
						//e.printStackTrace();
					}
				if (parar)
					break;
			}
		}


	}

	public void iniciarComunicacion(){
		try {
			outputStream.writeInt(1);
			outputStream.writeUTF(" mensaje");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	public void cerrarConexion(){
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		try {
			socketConexion.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
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
