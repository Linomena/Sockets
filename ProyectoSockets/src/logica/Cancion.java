package logica;

import java.util.ArrayList;

import persistencia.Archivo;

public class Cancion {
	private Archivo archivo;
	private String nombre;
	private ArrayList<String> letra;

	
	public Cancion() {
		// TODO Auto-generated constructor stub
		letra=new ArrayList<String>();
		archivo=new Archivo(nombre);
	}
	
	public void cargarCancione(){
		archivo.leerArchivo();
		letra= archivo.getLetra();
	}

}
