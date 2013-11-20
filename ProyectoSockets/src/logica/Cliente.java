package logica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Cliente implements Runnable{

	private Socket socket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;

	private boolean parar;
	private boolean pausar;
	private boolean velocidad;
	private Thread thread;
	private long speed;
	private int opccion;

	public long getSpeed() {
		return this.speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}


	public Cliente() {

		pausar = false;
		parar = false;
		speed= 1000;


		try {
			socket=new Socket("localhost", 4000);
			//socket=new Socket("10.0.39.59", 3900);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("el host no existe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("el puerto no esta disponible");
		}


		try {
			inputStream=new DataInputStream(socket.getInputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("error creando el canal de entrada ");

		}

		try {
			outputStream=new DataOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			System.out.println("error creando el canal de salida");
		}
		thread = new Thread(this);
		start();
	}

	@Override
	public void run() {
		while (!parar) {

			try {
				opccion = inputStream.readInt();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				System.out.println("no se recibio la opccion");
			}
			switch (opccion) {
			case 1:
				try {
					System.out.println("se recibio "+inputStream.readUTF());
					outputStream.writeInt(1);
					outputStream.writeUTF(" hola");
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

			try {
				Thread.sleep(speed);
			} catch (Exception e) {
				//e.printStackTrace();
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
			socket.close();
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
