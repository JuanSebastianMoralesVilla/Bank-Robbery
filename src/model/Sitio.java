package model;

public class Sitio implements Comparable<Sitio>{
	
	// Constantes -----------------------------------------------------------------------
	
	/**
	 * Representa el tipo de sitio banco.
	 */
	public final static char BANCO = 'B';
	
	/**
	 * Representa el tipo de sitio estación de policía.
	 */
	public final static char POLICIA = 'P';
	
	/**
	 * Representa el tipo de sitio que no es ni banco, ni estación de policía.
	 */
	public final static char NEUTRO = 'N';
	
	// Atributos -------------------------------------------------------------------------
	
	/**
	 * Representa la identificación del sitio.
	 */
	private int ID;
	
	/**
	 * Representa el tipo de sitio. Puede ser de tres tipos: banco, estación de policía
	 * o neutro.
	 */
	private char tipo;
	
	/**
	 * Atributo que tiene validez en los sitios que son tipo banco que determina si es 
	 * o no parte de la solución al problema.
	 */
	private boolean esSolucion;
	
	// Constructor ------------------------------------------------------------------------
	
	/**
	 * Construye un sitio de la ciudad con el ID dado, con su tipo en NEUTRO y sin
	 * participación en la solución del problema.
	 * @param ID - Es el número que diferencia a este sitio de los demás.
	 */
	public Sitio(int ID){
		this.ID = ID;
		tipo = NEUTRO;
		esSolucion = false;
	}
	
	// Métodos fundamentales ----------------------------------------------------------------
	
	/**
	 * Se encarga de dar el ID del sitio.
	 * @return Un número entero que representa el ID.
	 */
	public int darID(){
		return ID;
	}
	
	/**
	 * Se encarga de dar el tipo de sitio.
	 * @return Un caracter que representa el tipo de sitio. Puede ser B,P ó N.
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
	 * Método que se encarga de modificar si el sitio hace o no parte de la solución.
	 * @param esSolucion - Valor lógico que si es verdadero, el sitio hace parte de la 
	 * solución; si es falso, no hace parte de la solución.
	 */
	public void cambiarParticipacion(boolean esSolucion) {
		this.esSolucion = esSolucion;
	}
	
	/**
	 * Método que se encarga de dar el valor lógico que determina si el sitio hace o no 
	 * parte de la solución del problema.
	 * @return Verdadero si el sitio es solución al problema; falso de lo contrario.
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
