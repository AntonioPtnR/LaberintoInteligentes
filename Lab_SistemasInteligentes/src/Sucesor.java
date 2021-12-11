/* Nombre de la clase: Sucesor
 * Nombre del autor o autores: Julio Molina Diaz, Alvaro Pardo Benito, Antonio Paton Rico
 * Fecha de lanzamiento|creacion: 30/11/2020 | 30/10/2020
 * Version de clase: 1.0
 * Descripcion de la clase: Esta clase define al objeto Sucesor que es expandido de los nodos explorados en el arbol de busqueda
 */

public class Sucesor {

	private char move;
	private Celda estado;
	private int costo_mov;

	public Sucesor(char move, Celda estado, int costo_mov) {
		this.move = move;
		this.estado = estado;
		this.costo_mov = costo_mov;
	}

	public char getMove() {
		return move;
	}

	public void setMove(char move) {
		this.move = move;
	}

	public Celda getEstado() {
		return estado;
	}

	public void setEstado(Celda estado) {
		this.estado = estado;
	}

	public int getCosto_mov() {
		return costo_mov;
	}

	public void setCosto_mov(int costo_mov) {
		this.costo_mov = costo_mov;
	}

}
//Fin clase Sucesor