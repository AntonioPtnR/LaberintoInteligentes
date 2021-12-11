/* Nombre de la clase: Frontera
 * Nombre del autor o autores: Julio Molina Diaz, Alvaro Pardo Benito, Antonio Paton Rico
 * Fecha de lanzamiento|creacion: 30/11/2020 | 17/11/2020
 * Version de clase: 1.6
 * Descripcion de la clase: Esta clase define al objeto Frontera que almacena los nodos en la generacion del laberinto
 */

import java.util.PriorityQueue;

public class Frontera {

	private static PriorityQueue<Nodo> pq;

	public Frontera(){
		pq  = new PriorityQueue<Nodo>();
	}

	public Nodo peek() {
		return pq.peek();
	}

	public void push(Nodo nuevonodo){
		pq.add(nuevonodo);
	}

	public Nodo pop(){
		return pq.remove();
	}

	public boolean EsVacia(){
		return pq.isEmpty();
	}

	public PriorityQueue<Nodo> getFrontera() {
		return pq;

	}

}
// Fin clase Frontera
