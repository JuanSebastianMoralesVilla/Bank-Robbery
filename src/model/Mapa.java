package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Mapa {
	
	// Atributos ----------------------------------------------------------------------
	
	/**
	 * Representa la contenedora que tiene las ciudades.
	 */
	private ArrayList<Ciudad> ciudades;
	
	// Constructor ---------------------------------------------------------------------
	
	/**
	 * Construye el mapa con la contenedora de ciudades vac�a.
	 */
	public Mapa() {
		ciudades = new ArrayList<>();
	}
	
	// M�todos fundamentales ------------------------------------------------------------
	
	/**
	 * M�todo que se encarga de dar la contenedora de ciudades que tiene el mapa.
	 * @return Un arraylist que contiene las ciudades que tiene el mapa.
	 */
	public ArrayList<Ciudad> darCiudades(){
		return ciudades;
	}
	
	// M�todos y servicios ---------------------------------------------------------------
	
	/**
	 * M�todo que se encarga de cargar la ciudad cuya disposici�n es la descrita en el
	 * archivo que se pasa por par�metro.
	 * @param archivo - Es el archivo el cual describe la ciudad a ser construida.
	 * @throws IOException - Si hay problemas al cargar el archivo.
	 */
	public void cargarDatos(File archivo) throws Exception{
		BufferedReader lector = new BufferedReader(new FileReader(archivo));
		String linea = lector.readLine();
		int identificador = 1;
		while(linea != null){
			Ciudad ciudadActual = new Ciudad(identificador);
			String descripcionCiudad = "";
			descripcionCiudad += linea+"\n";
			String[] split = linea.split(" ");
			int numeroSitios = Integer.parseInt(split[0]);
			int numeroCarreteras = Integer.parseInt(split[1]);
			int numeroPolicias = Integer.parseInt(split[3]);
			
			// Crea los sitios de la ciudad.
			for (int i = 0; i < numeroSitios; i++) {
				ciudadActual.agregarSitio(i);
			}
			linea = lector.readLine();
			descripcionCiudad += linea+"\n";
			split = linea.split(" ");
			
			// Crea las carreteras de la ciudad.
			for (int i = 1; i <= numeroCarreteras; i++) {
				int sitioA = Integer.parseInt(split[0]);
				int sitioB = Integer.parseInt(split[1]);
				int distancia = Integer.parseInt(split[2]);
				ciudadActual.agregarCarretera(sitioA, sitioB, distancia);
				linea = lector.readLine();
				descripcionCiudad += linea+"\n";
				split = linea.split(" ");
			}
			
			// Asignaci�n de bancos
			if(!linea.equals("")) {
				for (int i = 0; i < split.length; i++) {
					int IDSitio = Integer.parseInt(split[i]);
					ciudadActual.modificarTipoSitio(IDSitio, Point.BANCO);
				}
			}
			if(numeroPolicias > 0) {
				linea = lector.readLine();
				descripcionCiudad += linea+"\n";
				split = linea.split(" ");
				
				// Asignaci�n de polic�as
				for (int i = 0; i < split.length; i++) {
					int IDSitio = Integer.parseInt(split[i]);
					ciudadActual.modificarTipoSitio(IDSitio, Point.POLICIA);
				}
			}
			ciudadActual.modificarFormatoTexto(descripcionCiudad);
			ciudades.add(ciudadActual);
			identificador++;
			linea = lector.readLine();
		}
		lector.close();
	}
	
	/**
	 * M�todo que se encarga de gestionar la soluci�n a la ciudad que se ha pasado
	 * por par�metro.
	 * @param ciudad - Es la ciudad que se quiere resolver.
	 * @return Una cadena representando el n�mero de bancos y los bancos m�s alejados de cualquier
	 * estaci�n de polic�a, pero que toma menos tiempo en recorrer.
	 */
	public String solucionarCiudad(Ciudad ciudad){
		String respuesta = "";
		respuesta = ciudad.darSolucion();
		return respuesta;
	}
	
//	/**
//	 * M�todo auxiliar que se encarga de buscar en la contenedora de ciudades la ciudad
//	 * que se ha pasado por par�metro. El criterio de b�squeda es de acuerdo a su ID.
//	 * @param ciudad - Es la ciudad que se quiere buscar.
//	 * @return La ciudad que se quiere encontrar o null si no existe.
//	 */
//	private Ciudad buscarCiudad(Ciudad ciudad){
//		Ciudad respuesta = null;
//		int idDeseado = ciudad.darIdentificador();
//		boolean encontre = false;
//		for (int i = 0; i < ciudades.size() && !encontre; i++) {
//			Ciudad actual = ciudades.get(i);
//			int id = actual.darIdentificador();
//			if(idDeseado == id){
//				respuesta = actual;
//				encontre = true;
//			}
//		}
//		return respuesta;
//	}
}
