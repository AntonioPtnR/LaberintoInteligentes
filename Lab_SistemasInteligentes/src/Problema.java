/* Nombre de la clase: Problema
 * Nombre del autor o autores: Julio Molina Diaz, Alvaro Pardo Benito, Antonio Paton Rico
 * Fecha de lanzamiento|creacion: 30/11/2020 | 18/11/2020
 * Version de clase: 1.1
 * Descripcion de la clase: Esta clase define al objeto Problema que vamos a leer
 */

import java.util.Arrays;

public class Problema {

	private int[] idEstado_inicial;
	private int[] idEstado_objetivo;
	private Celda[][] maze;

	Problema(int[] idEstado_inicial, int[] idEstado_objetivo, Celda[][] maze){
		this.idEstado_inicial = idEstado_inicial;
		this.idEstado_objetivo = idEstado_objetivo;
		this.maze = maze;
	}

	public Celda getCeldaInicio() {
		return maze[idEstado_inicial[0]][idEstado_inicial[1]];
	}

	public Celda getCeldaObjetivo() {
		return maze[idEstado_objetivo[0]][idEstado_objetivo[1]];
	}

	public int[] getIdEstado_inicial() {
		return idEstado_inicial;
	}

	public int[] getIdEstado_objetivo() {
		return idEstado_objetivo;
	}

	public Celda[][] getMaze() {
		return maze;
	}

	@Override
	public String toString() {
		return "Problema [idEstado_inicial=" + Arrays.toString(idEstado_inicial) + ", idEstado_objetivo="
				+ Arrays.toString(idEstado_objetivo) + ", maze=" + Arrays.toString(maze) + "]";
	}

}
//Fin clase Problema