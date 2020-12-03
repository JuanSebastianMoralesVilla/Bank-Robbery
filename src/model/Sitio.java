package model;

public class Sitio implements Comparable<Sitio>{
	
	// Constantes -----------------------------------------------------------------------
	
	/**
	 * Representa el tipo de sitio banco.
	 */
	public final static char BANCO = 'B';
	
	/**
	 * Representa el tipo de sitio estaci�n de polic�a.
	 */
	public final static char POLICIA = 'P';
	
	/**
	 * Representa el tipo de sitio que no es ni banco, ni estaci�n de polic�a.
	 */
	public final static char NEUTRO = 'N';
	
	// Atributos -------------------------------------------------------------------------
	
	/**
	 * Representa la identificaci�n del sitio.
	 */
	private int ID;
	
	/**
	 * Representa el tipo de sitio. Puede ser de tres tipos: banco, estaci�n de polic�a
	 * o neutro.
	 */
	private char tipo;
	
	/**
	 * Atributo que tiene validez en los sitios que son tipo banco que determina si es 
	 * o no parte de la soluci�n al problema.
	 */
	private boolean esSolucion;
	
	// Constructor ------------------------------------------------------------------------
	
	/**
	 * Construye un sitio de la ciudad con el ID dado, con su tipo en NEUTRO y sin
	 * participaci�n en la soluci�n del problema.
	 * @param ID - Es el n�mero que diferencia a este sitio de los dem�s.
	 */
	public Sitio(int ID){
		this.ID = ID;
		tipo = NEUTRO;
		esSolucion = false;
	}
	
	// M�todos fundamentales ----------------------------------------------------------------
	
	/**
	 * Se encarga de dar el ID del sitio.
	 * @return Un n�mero entero que representa el ID.
	 */
	public int darID(){
		return ID;
	}
	
	/**
	 * Se encarga de dar el tipo de sitio.
	 * @return Un caracter que representa el tipo de sitio. Puede ser B,P � N.
	 */
	public char darTipo(){
		return tipo;
	}
	
	/**
	 * Se encarga de modificar el tipo de sitio.
	 */
	public void modificarTipo(char tipo){
		this.tipo = tipo;
	}
	
	/**
	 * M�todo que se encarga de modificar si el sitio hace o no parte de la soluci�n.
	 * @param esSolucion - Valor l�gico que si es verdadero, el sitio hace parte de la 
	 * soluci�n; si es falso, no hace parte de la soluci�n.
	 */
	public void cambiarParticipacion(boolean esSolucion) {
		this.esSolucion = esSolucion;
	}
	
	/**
	 * M�todo que se encarga de dar el valor l�gico que determina si el sitio hace o no 
	 * parte de la soluci�n del problema.
	 * @return Verdadero si el sitio es soluci�n al problema; falso de lo contrario.
	 */
	public boolean esSolucion() {
		return esSolucion;
	}

	@Override
	public int compareTo(Sitio sitio) {
		int respuesta = 0;
		if(ID < sitio.ID) {
			respuesta = -1;
		}else if(ID > sitio.ID) {
			respuesta = 1;
		}
		return respuesta;
	}
	
	@Override
	public String toString() {
		String respuesta = "";
		respuesta = ""+ID+", "+tipo+", "+esSolucion;
		return respuesta;
	}
}
