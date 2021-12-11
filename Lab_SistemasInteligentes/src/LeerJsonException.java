/* Nombre de la clase: LeerJsonException
 * Nombre del autor o autores: Julio Molina Diaz, Alvaro Pardo Benito, Antonio Paton Rico
 * Fecha de lanzamiento|creacion: 30/11/2020 | 28/11/2020
 * Version de clase: 1.0
 * Descripcion de la clase: Esta clase define la excepcion del json al leer unas celdas no existentes en el laberinto
 */

public class LeerJsonException extends Exception {
	String mensaje;
	
	public LeerJsonException() {	
		super();
	}
	
	public String getMensaje() {
		mensaje = "Error, celdas no permitidas";
		return mensaje;
	}
	
}
// Fin clase LeerJsonException 