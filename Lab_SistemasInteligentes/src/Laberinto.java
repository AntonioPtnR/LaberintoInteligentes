/* Nombre de la clase: Laberinto
 * Nombre del autor o autores: Julio Molina Diaz, Alvaro Pardo Benito, Antonio Paton Rico
 * Fecha de lanzamiento|creacion: 30/11/2020 | 8/10/2020
 * Version de clase: 1.8
 * Descripcion de la clase: Esta clase define al laberinto creado en la ejecucion del programa
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Laberinto {
	private int filas;
	private int columnas;
	private Celda[][] maze;
	private int [][] mov = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
	private String archivo;
	
	Laberinto(int filas, int columnas){
		this.filas = filas;
		this.columnas = columnas;
		maze = inicializarMatriz();
	}
	Laberinto(int filas, int columnas, Celda[][] maze){
		this.filas = filas;
		this.columnas = columnas;
		this.maze = maze;
	}
	
	Laberinto(String archivo){
		this.archivo = archivo;
	}
	
	public int getFilas() {
		return filas;
	}

	public int getColumnas() {
		return columnas;
	}

	public int[][] getMov() {
		return mov;
	}

	public void setMov(int[][] mov) {
		this.mov = mov;
	}
	
	public void WilsonAlgorithm(){
		ArrayList<Celda> celdasNoVisitadas = new ArrayList<Celda>();
		ArrayList<Celda> camino = new ArrayList<Celda>();
		ArrayList<Celda> caminoAux = new ArrayList<Celda>();

		for (int f = 0; f < maze.length; f++) { 
			for (int c = 0; c < maze[f].length; c++) {
				celdasNoVisitadas.add(maze[f][c]);
			}
		}

		Celda inicio = generarCeldaAleatoria(celdasNoVisitadas);
		Celda fin = generarCeldaAleatoria(celdasNoVisitadas);
		Celda actual = maze[fin.getCordy()][fin.getCordx()];
		camino.add(0, fin);

		while ((inicio.getCordx() != actual.getCordx()) || (inicio.getCordy() != actual.getCordy())) 
			actual = caminoControlandoBucles(actual, camino, celdasNoVisitadas);	

		borrarCeldaLista(celdasNoVisitadas, inicio);

		while(!celdasNoVisitadas.isEmpty() ) {
			caminoAux.clear();
			actual=generarCeldaAleatoria(celdasNoVisitadas);
			caminoAux.add(0,actual);

			while(celdasNoVisitadas.contains(actual)) 
				actual = caminoControlandoBucles(actual, caminoAux, celdasNoVisitadas);	
		}
	}
	
	public Celda[][] getMaze() {
		return maze;
	}

	public void setMaze(Celda[][] maze) {
		this.maze = maze;
	}

	private Celda caminoControlandoBucles(Celda actual, ArrayList<Celda> camino, ArrayList<Celda> celdasNoVisitadas) {
		
		borrarCeldaLista(celdasNoVisitadas, actual);
		actual = desplazamiento(actual);
		Celda celdaAnterior = null;
		
		if (camino.indexOf(actual) != -1) { 
			while (camino.indexOf(actual) > 0) {
				int i = 0;
				boolean[] vecinos = new boolean[Constantes.NUM_VECINOS];
				camino.get(i).setVecinos(vecinos);
				celdasNoVisitadas.add(camino.remove(i));
			}
			boolean[] vecinos = new boolean[Constantes.NUM_VECINOS];
			camino.get(0).setVecinos(vecinos);
			if (camino.size() > 1) {
				boolean[]vecinosActual=new boolean[Constantes.NUM_VECINOS];
				celdaAnterior = camino.get(1);
				int restaX = camino.get(0).getCordx() - celdaAnterior.getCordx();
				int restaY = camino.get(0).getCordy() - celdaAnterior.getCordy();
				if (restaY == 1 && restaX == 0) 
					vecinosActual[Constantes.VECINO_NORTE] = true;
				else if (restaY == 0 && restaX == -1)
					vecinosActual[Constantes.VECINO_ESTE] = true;
				else if (restaY == -1 && restaX == 0)
					vecinosActual[Constantes.VECINO_SUR] = true;
				else if (restaY == 0 && restaX == 1 )
					vecinosActual[Constantes.VECINO_OESTE] = true;
				camino.get(0).setVecinos(vecinosActual);
			}
			celdasNoVisitadas.add(camino.get(0));
		} 
		else {
			camino.add(0, actual);
		}
		return actual;
	}
	
	private Celda desplazamiento(Celda actual) {
		boolean[] vecinosaux = null;
		boolean despvalido = false;
		while (!despvalido) {
			
			switch ((int) (Math.random() * Constantes.NUM_VECINOS)) {
			
			case Constantes.VECINO_NORTE: 
				if (actual.getCordy() != 0) {
					vecinosaux = actual.getVecinos();
					vecinosaux[Constantes.VECINO_NORTE] = true;
					actual.setVecinos(vecinosaux);
					actual = maze[actual.getCordy() + mov[0][0]][actual.getCordx() + mov[0][1]];
					vecinosaux = actual.getVecinos();
					vecinosaux[Constantes.VECINO_SUR] = true;
					actual.setVecinos(vecinosaux);
					despvalido = true;
				}
				break;
			
			case Constantes.VECINO_ESTE: 
				if (actual.getCordx() != maze[0].length - 1) {
					vecinosaux = actual.getVecinos();
					vecinosaux[Constantes.VECINO_ESTE] = true;
					actual.setVecinos(vecinosaux);
					actual = maze[actual.getCordy()+mov[1][0]][actual.getCordx() + mov[1][1]];
					vecinosaux = actual.getVecinos();
					vecinosaux[Constantes.VECINO_OESTE] = true;
					actual.setVecinos(vecinosaux);
					despvalido = true;
				}
				break;
				
			case Constantes.VECINO_SUR: 
				if (actual.getCordy() != maze.length - 1) {
					vecinosaux = actual.getVecinos();
					vecinosaux[Constantes.VECINO_SUR] = true;
					actual.setVecinos(vecinosaux);
					actual = maze[actual.getCordy() + mov[2][0]][actual.getCordx()+mov[2][1]];
					vecinosaux = actual.getVecinos();
					vecinosaux[Constantes.VECINO_NORTE] = true;
					actual.setVecinos(vecinosaux);
					despvalido = true;
				}
				break;
			
			case Constantes.VECINO_OESTE:
				if (actual.getCordx() != 0) {
					vecinosaux = actual.getVecinos();
					vecinosaux[Constantes.VECINO_OESTE] = true;
					actual.setVecinos(vecinosaux);
					actual = maze[actual.getCordy()+mov[3][0]][actual.getCordx() + mov[3][1]];
					vecinosaux = actual.getVecinos();
					vecinosaux[Constantes.VECINO_ESTE] = true;
					actual.setVecinos(vecinosaux);
					despvalido = true;
				}
				break;
			}
		}
		return actual;
	}
	
	private Celda[][] inicializarMatriz() {
		Celda aux = null;
		Celda [][] laberinto = new Celda[filas][columnas];
		for(int f = 0;f<laberinto.length;f++) {
			for(int c = 0;c<laberinto[f].length;c++) {
				aux = new Celda(f,c);
				laberinto[f][c] = aux;
			}
		}
		return laberinto;
	}
	
	private Celda generarCeldaAleatoria(ArrayList<Celda> celdasNoVisitadas) {
		Celda c = null;
		int faleat;
		int caleat;
		do {
			faleat = (int) (Math.random() * maze.length);
			caleat = (int) (Math.random() * maze[0].length);
			c = new Celda(faleat, caleat);
		} while (!ComprobarCeldaVisitada(celdasNoVisitadas, c));
		
		return maze[faleat][caleat];
	}
	
	private boolean ComprobarCeldaVisitada(ArrayList<Celda> celdasNoVisitadas, Celda c) {
		boolean novisitado = false;
		Iterator<Celda> ita = celdasNoVisitadas.iterator();
		Celda celdaaux = null;
		while (ita.hasNext()) {
			celdaaux = ita.next();
			if (celdaaux.getCordx() == c.getCordx() && celdaaux.getCordy() == c.getCordy()) {
				novisitado = true;
			}
		}
		return novisitado;
	}
	
	private void borrarCeldaLista(ArrayList<Celda> celdasNoVisitadas, Celda c) {
		Celda celdaaux = null;
		for (int i = 0; i < celdasNoVisitadas.size(); i++) { 
			celdaaux = celdasNoVisitadas.get(i);
			if ((celdaaux.getCordx() == c.getCordx()) && (celdaaux.getCordy() == c.getCordy())) {
				celdasNoVisitadas.remove(celdaaux);
			}
		}
	}
	
}
//Fin clase Laberinto