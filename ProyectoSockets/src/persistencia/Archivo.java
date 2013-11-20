package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Archivo {
	private BufferedReader bufferedReader;
	private File archivo;
	private String ruta;
	private String nombre;
	private FileReader fileReader;
	private ArrayList<String>letraCancion;
	
	

	public Archivo( String nombre) {
		// TODO Auto-generated constructor stub
		archivo=new File("");
		ruta= archivo.getAbsolutePath()+nombre+".txt";
		letraCancion =new ArrayList<String>();
	}
	
	public void abrirArchivo(){
		try {
			fileReader=new FileReader(ruta);
			bufferedReader=new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void leerArchivo(){
		String linea="";
		abrirArchivo();
		
		try {
			while ((linea=bufferedReader.readLine())!=null) {
				letraCancion.add(linea);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void cerrarArchivo(){
		
		try {
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<String> getLetra() {
		return letraCancion;
	}

	public void setLetra(ArrayList<String> letra) {
		this.letraCancion = letra;
	}


}
