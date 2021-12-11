/* Nombre de la clase: Visitados
 * Nombre del autor o autores: Julio Molina Diaz, Alvaro Pardo Benito, Antonio Paton Rico
 * Fecha de lanzamiento|creacion: 30/11/2020 | 18/10/2020
 * Version de clase: 1.5
 * Descripcion de la clase: Esta clase define a los estados visitados del laberinto
 */

import java.util.ArrayList;

public class Visitados {
	private ArrayList<Celda> estadosVisitados;

	Visitados(){
	}

	public void crear_vacio() {
		estadosVisitados = new ArrayList<Celda>();
	}

	public void insertar(Celda estado) {
		estadosVisitados.add(estado);
	}

	public ArrayList<Celda> getVisitados() {
		return estadosVisitados;
	}

	public boolean pertenece(Celda estado) {
		boolean pertenece = false;
		if(estadosVisitados.isEmpty()) {
			return pertenece;
		}
		for(int i=0; i<estadosVisitados.size(); i++) {
			if(estadosVisitados.get(i).getCordy() == estado.getCordy() && estadosVisitados.get(i).getCordx() == estado.getCordx()) {
				pertenece = true;
			}
		}
		return pertenece;
	}

}
// Fin clase Visitados