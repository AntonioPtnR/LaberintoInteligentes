/* Nombre de la clase: Celda
 * Nombre del autor o autores: Julio Molina Diaz, Alvaro Pardo Benito, Antonio Paton Rico
 * Fecha de lanzamiento|creacion: 30/11/2020 | 08/10/2020
 * Version de clase: 1.5
 * Descripcion de la clase: Esta clase define al objeto Celda que forma el laberinto
 */

import java.util.ArrayList;
import java.util.Arrays;

public class Celda {

	private int cordy;
	private int cordx;
	private int value;
	private boolean[] vecinos;
	private ArrayList<Sucesor> sucesores = new ArrayList<Sucesor>();

	// Constructor para crear laberinto aleatorio
	public Celda(int cordy, int cordx) {
		this.cordy = cordy;
		this.cordx = cordx;
		vecinos = new boolean[Constantes.NUM_VECINOS];
		this.value = (int) (Math.random() * Constantes.NUM_VECINOS);
	}

	// Constructor para crear laberinto a partir del JSON
	public Celda(int cordy, int cordx, int value, boolean[] vecinos) {
		this.cordy = cordy;
		this.cordx = cordx;
		this.value = value;
		this.vecinos = vecinos;
		this.value = value;
	}

	public int getCordy() {
		return cordy;
	}

	public int getCordx() {
		return cordx;
	}

	public void setCordy(int cordy) {
		this.cordy = cordy;
	}

	public void setCordx(int cordx) {
		this.cordx = cordx;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean[] getVecinos() {
		return vecinos;
	}

	public void setVecinos(boolean[] vecinos) {
		this.vecinos = vecinos;
	}

	public ArrayList<Sucesor> getSucesores() {
		return sucesores;
	}

	public void setSucesores(ArrayList<Sucesor> sucesores) {
		this.sucesores = sucesores;
	}


	public void generarSucesores(Celda[][] laberinto) {
		Sucesor sucesor = null;
		int costo_mov = 0;
		if (vecinos[0] == true) {
			Celda aux = laberinto[cordy - 1][cordx];
			costo_mov = aux.getValue() + 1;
			sucesor = new Sucesor('N', aux, costo_mov);
			sucesores.add(sucesor);
		}

		if (vecinos[1] == true) {
			Celda aux = laberinto[cordy][cordx+1];
			costo_mov = aux.getValue() + 1;
			sucesor = new Sucesor('E', aux, costo_mov);
			sucesores.add(sucesor);
		}

		if (vecinos[2] == true) {
			Celda aux = laberinto[cordy + 1][cordx];
			costo_mov = aux.getValue() + 1;
			sucesor = new Sucesor('S', aux, costo_mov);
			sucesores.add(sucesor);
		}

		if (vecinos[3] == true) {
			Celda aux = laberinto[cordy][cordx - 1];
			costo_mov = aux.getValue() + 1;
			sucesor = new Sucesor('O', aux, costo_mov);
			sucesores.add(sucesor);
		}
	}

	@Override
	public String toString() {
		return "Celda [cordy=" + cordy + ", cordx=" + cordx + ", value=" + value + ", vecinos="
				+ Arrays.toString(vecinos) + "]";
	}

}
// Fin clase Celda