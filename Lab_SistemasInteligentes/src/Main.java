/* Nombre de la clase: Main
 * Nombre del autor o autores: Julio Molina Diaz, Alvaro Pardo Benito, Antonio Paton Rico
 * Fecha de lanzamiento|creacion: 30/11/2020 | 30/10/2020
 * Version de clase: 1.8
 * Descripcion de la clase: En esta clase encontramos el programa principal referido a la practica de la asignatura SSII
 */

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main{
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws IOException{
		ArrayList<Nodo> soluccion = new ArrayList<Nodo>();
		Frontera front = new Frontera();
		Visitados estadosVisitados = new Visitados();
		estadosVisitados.crear_vacio();
		Celda[][] laberinto = null;

		try {
			laberinto = menu(laberinto, soluccion, front, estadosVisitados);
		}catch(IOException e) {
			System.out.println(Constantes.MSJ_ERROR_ENTSAL + e.toString());
		}

		if(laberinto != null) {
			mostrarLaberinto(laberinto); 
			System.out.println(Constantes.GENERANDO_IMAGEN); 
			String nombre = sc.next();
			drawMaze(laberinto, nombre, soluccion, front, estadosVisitados);
			System.out.println(Constantes.ESTADO_FINAL_LABERINTO);
			mostrarLaberinto(laberinto); 
			System.out.println(Constantes.FINALIZACION);
		}
	}

	private static Celda[][] menu(Celda[][] laberinto, ArrayList<Nodo> soluccion, Frontera front, Visitados estadosVisitados) throws IOException {
		Laberinto l1 = null;
		String nombre = "";
		int eleccion = 0;
		boolean valido = false;
		do {
			try {
				System.out.println(Constantes.MENU);
				eleccion = sc.nextInt();
				switch (eleccion) {

				case Constantes.OP_GENERAR_ALEAT:
					valido = true;
					l1 = CrearLaberinto();
					l1.WilsonAlgorithm();
					laberinto = l1.getMaze();
					System.out.println(Constantes.INTRODUCE_NOM_ARCHIVOJSON);
					nombre = sc.next();
					crearJson(laberinto, nombre);
					break;

				case Constantes.OP_LEER_JSON:
					valido = true;
					System.out.println(Constantes.INTRODUCE_NOM_ARCHIVO);
					nombre = sc.next();
					try {
						l1 = leerJson(nombre);
						if (!consistenciaLaberinto(l1.getMaze())) {
							System.out.println(Constantes.MSJ_ERROR_JSON_INC);
							valido = false;
						}
						laberinto = l1.getMaze();
						System.out.println(Constantes.MSJ_INFO_JSON_LEIDO);

					} catch (NullPointerException s) {
						System.out.println(Constantes.MSJ_ERROR_OPC);
						valido = false;
					}
					break;

				case Constantes.OP_GENERAR_PROBLEMA:
					valido = true;
					generarProblema();
					System.out.println(Constantes.MSJ_INFO_PROB_GENER);
					break;

				case Constantes.OP_LEER_PROBLEMA:
					valido = true;
					try{
						Problema prob = leerProblema();
						System.out.println(prob.toString());
						laberinto = prob.getMaze();

						if(algoritmoBusqueda(prob, "BREADTH", Constantes.MAX_PROFUNDIDAD, soluccion, front, estadosVisitados)) {
							System.out.println(Constantes.MSJ_INFO_SOL_ENC);
							String nombre_f = sc.next();
							FileWriter fichero=new FileWriter(nombre_f+".txt");
							fichero.write(Constantes.REPRESENT_NODO + soluccion.get(soluccion.size()-1).toStringOrigen()+"\n"); // Nodo padre
							System.out.println(Constantes.REPRESENT_NODO + soluccion.get(soluccion.size()-1).toStringOrigen()); // Nodos hijos
							for(int i=soluccion.size()-2;i>=0;i--) {
								fichero.write(soluccion.get(i).toString()+"\n");
								System.out.println(soluccion.get(i).toString());
							}
							fichero.close();
						}
						else
							System.out.println(Constantes.MSJ_INFO_NO_SOL);

						break;
					}catch (NullPointerException e) {
					}

				case Constantes.OP_SALIR:
					valido = true;
					System.out.println(Constantes.SALIR);
					break;

				default:
					System.out.println(Constantes.MSJ_ERROR_LIM_OPC);
					break;
				}
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println(Constantes.MSJ_ERROR_TIP_DATO);
			}

		} while (!valido);

		return laberinto;
	}

	private static Laberinto CrearLaberinto() {
		System.out.println(Constantes.INTRODUCE_FILAS);
		int filas = pedirFilasColumnas();
		System.out.println(Constantes.INTRODUCE_COLUMNAS);
		int columnas = pedirFilasColumnas();
		Laberinto l1 = new Laberinto(filas, columnas);
		return l1;
	}

	private static int pedirFilasColumnas() {
		int numero;
		numero = sc.nextInt();
		while (numero <= 0) {
			System.out.println(Constantes.MSJ_ERROR_FILCOL);
			numero = sc.nextInt();
		}
		return numero;
	}

	private static void drawMaze(Celda[][] laberinto, String nombre, ArrayList<Nodo> soluccion, Frontera front, Visitados estadosVisitados) {
		int seg_anchura = (Constantes.N_PIXELS_FONDO) / laberinto.length;
		int seg_altura = (Constantes.N_PIXELS_FONDO) / laberinto[0].length;
		boolean[] vecinos = null;
		BufferedImage dibujo = new BufferedImage(seg_anchura * laberinto[0].length + 2,
				seg_altura * laberinto.length + 2, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D lapiz = dibujo.createGraphics();

		int valor=0;
		for (int f = 0; f < laberinto.length; f++) {
			for (int c = 0; c < laberinto[f].length; c++) {
				valor = laberinto[f][c].getValue();
				Color col=null;
				if(valor==0)
					col=Color.WHITE;
				else if(valor==1)
					col=new Color(175,116,71);
				else if(valor==2)
					col=Color.GREEN;
				else if(valor==3)
					col=Color.CYAN;
				lapiz.setColor(col);
				lapiz.fillRect(c * seg_anchura, f * seg_altura, seg_anchura, seg_altura);
			}
		}
		if(!front.EsVacia()) {
			lapiz.setColor(Color.BLUE);
			Celda estadoAux = null;
			while(!front.EsVacia()) {
				estadoAux = front.getFrontera().remove().getEstado();
				lapiz.fillRect(estadoAux.getCordx() * seg_anchura, estadoAux.getCordy() * seg_altura, seg_anchura, seg_altura);
			}
		}

		if(!estadosVisitados.getVisitados().isEmpty()) {
			lapiz.setColor(Color.GREEN);
			Celda estadoAux = null;
			for(int i=0; i<estadosVisitados.getVisitados().size(); i++) {
				estadoAux=estadosVisitados.getVisitados().get(i);
				lapiz.fillRect(estadoAux.getCordx() * seg_anchura, estadoAux.getCordy() * seg_altura, seg_anchura, seg_altura);
			}
		}

		if(!soluccion.isEmpty()) {
			lapiz.setColor(Color.RED);
			Celda estadoAux = null;
			for(int i=0; i<soluccion.size(); i++) {
				estadoAux = soluccion.get(i).getEstado();
				lapiz.fillRect(estadoAux.getCordx() * seg_anchura, estadoAux.getCordy() * seg_altura, seg_anchura, seg_altura);
			}
		}

		lapiz.setStroke(new BasicStroke(3));
		lapiz.setColor(Color.BLACK);
		for (int f = 0; f < laberinto.length; f++) {
			for (int c = 0; c < laberinto[f].length; c++) {
				vecinos = laberinto[f][c].getVecinos();
				if (vecinos[Constantes.VECINO_NORTE] == false)
					lapiz.drawLine(c * seg_anchura, f * seg_altura, (c * seg_anchura) + seg_anchura, f * seg_altura);
				if (vecinos[Constantes.VECINO_ESTE] == false)
					lapiz.drawLine((c * seg_anchura) + seg_anchura, f * seg_altura, (c * seg_anchura) + seg_anchura,
							(f * seg_altura) + seg_altura);
				if (vecinos[Constantes.VECINO_SUR] == false)
					lapiz.drawLine(c * seg_anchura, (f * seg_altura) + seg_altura, (c * seg_anchura) + seg_anchura,
							(f * seg_altura) + seg_altura);
				if (vecinos[Constantes.VECINO_OESTE] == false)
					lapiz.drawLine(c * seg_anchura, f * seg_altura, c * seg_anchura, (f * seg_altura) + seg_altura);
			}
		}
		try {
			ImageIO.write(dibujo, "png", new File(nombre + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void mostrarLaberinto(Celda[][] laberinto) {
		for (int f = 0; f < laberinto.length; f++) {
			for (int c = 0; c < laberinto[f].length; c++) {
				System.out.println(laberinto[f][c].toString());
			}
		}
	}

	private static boolean consistenciaLaberinto(Celda[][] laberinto) {
		boolean consistente = true;
		boolean[] vecinos = null;
		for (int f = 0; f < laberinto.length; f++) {
			for (int c = 0; c < laberinto[0].length; c++) {
				vecinos = laberinto[f][c].getVecinos();
				if ((laberinto[f][c].getCordy() == 0) && vecinos[0])
					consistente = false;
				else if ((laberinto[f][c].getCordx() == laberinto[0].length - 1) && vecinos[1])
					consistente = false;
				else if ((laberinto[f][c].getCordy() == laberinto.length - 1) && vecinos[2])
					consistente = false;
				else if ((laberinto[f][c].getCordx() == 0) && vecinos[3])
					consistente = false;
				else if (vecinos[Constantes.VECINO_NORTE]
						&& laberinto[f + Constantes.MOV[0][0]][c + Constantes.MOV[0][1]]
								.getVecinos()[Constantes.VECINO_SUR] == false)
					consistente = false;
				else if (vecinos[Constantes.VECINO_ESTE]
						&& laberinto[f + Constantes.MOV[1][0]][c + Constantes.MOV[1][1]]
								.getVecinos()[Constantes.VECINO_OESTE] == false)
					consistente = false;
				else if (vecinos[Constantes.VECINO_SUR] && laberinto[f + Constantes.MOV[2][0]][c + Constantes.MOV[2][1]]
						.getVecinos()[Constantes.VECINO_NORTE] == false)
					consistente = false;
				else if (vecinos[Constantes.VECINO_OESTE]
						&& laberinto[f + Constantes.MOV[3][0]][c + Constantes.MOV[3][1]]
								.getVecinos()[Constantes.VECINO_ESTE] == false)
					consistente = false;
			}
		}
		return consistente;
	}

	private static Laberinto leerJson(String archivo) {
		JsonInterface j=new JsonInterface();
		Laberinto l = null;
		try {
			l=j.leerJson(archivo);
		} catch (FileNotFoundException fn) {
			System.out.println(Constantes.MSJ_ERROR_FNF_EXCP);
		} catch (Exception e) {
			System.out.println(Constantes.MSJ_ERROR_EXCP + e);
		}
		return l;

	}

	private static void crearJson(Celda[][] laberinto, String nombre) {
		JsonInterface j=new JsonInterface();
		try {
			j.crearJson(laberinto, nombre);
		} catch (IOException e) {
			System.out.println(Constantes.MSJ_ERROR_JSON + e.getMessage());
		}
	}

	public static void generarProblema() {
		String idEstadoInicial="";
		String idEstadoObjetivo="";
		String nombLaberinto="";
		String nombreJson="";
		sc.nextLine();
		System.out.println(Constantes.INTRODUCE_ID_EST_INICIAL);
		idEstadoInicial=sc.nextLine();
		System.out.println(Constantes.INTRODUCE_ID_EST_OBJETIVO);
		idEstadoObjetivo=sc.nextLine();
		System.out.println(Constantes.INTRODUCE_NOM_FICH_JSON_CONTIENE_LAB);
		nombLaberinto = sc.nextLine();
		System.out.println(Constantes.INTRODUCE_NOM_FICH_JSON_CONTENDRA_PROB);
		nombreJson = sc.nextLine();
		JsonInterface j = new JsonInterface();
		try {
			j.generarProblema(idEstadoInicial, idEstadoObjetivo, nombreJson, nombLaberinto);
		} catch (IOException e) {
			System.out.println(Constantes.MSJ_ERROR_JSON + e.getMessage());
		}
	}

	public static Problema leerProblema() {
		JsonInterface j = new JsonInterface();
		Problema prob = null;
		int[] estInicial = new int[2];
		int[] estObjetivo = new int[2];
		Laberinto maze = null;
		System.out.println(Constantes.INTRODUCE_NOM_FICH_JSON_CONTIENE_PROB);
		String archivo=sc.next();
		try {
			prob=j.leerProblema(archivo, estInicial, estObjetivo, maze, prob);
		}catch (FileNotFoundException fn) {
			System.out.println(Constantes.MSJ_ERROR_FNF_EXCP);
		}catch(LeerJsonException js) {
			System.out.println(Constantes.MSJ_ERROR_ESTADOS_NO_VALIDOS);
		}catch (Exception e) {
			System.out.println(Constantes.MSJ_ERROR_EXCP + e);
		}
		return prob;
	}

	public static boolean algoritmoBusqueda(Problema prob, String estrategia, int profundidad,ArrayList<Nodo> soluccion, Frontera front, Visitados estadosVisitados){
		Celda [][] laberinto=prob.getMaze();
		boolean solEnc = false;
		estadosVisitados.crear_vacio();
		ArrayList<Nodo> listaNodosHijos=new ArrayList<Nodo>();
		int id = 0;

		Celda estadoActual = prob.getCeldaInicio(); 
		Celda estadoObjetivo = prob.getCeldaObjetivo();
		Nodo actual = new Nodo(id,estrategia,estadoActual,estadoObjetivo);
		front.push(actual);
		while(!solEnc && !front.EsVacia()) {
			actual = front.pop();
			if(actual.getEstado().equals(estadoObjetivo)) {
				solEnc = true;
				soluccion.add(actual);
				while(actual.getPadre()!=null) {
					actual = actual.getPadre();
					soluccion.add(actual);
				}
			}else {
				if(!estadosVisitados.pertenece(actual.getEstado()) && actual.getProfundidad() < profundidad) {
					estadosVisitados.insertar(actual.getEstado());
					listaNodosHijos = expandirNodos(laberinto, actual, id, estrategia, estadoObjetivo); 
					for(int i=0;i<listaNodosHijos.size();i++) {
						front.push(listaNodosHijos.get(i));
						id++;
					}
				}
			}
		}
		return solEnc;
	}

	public static ArrayList<Nodo> expandirNodos( Celda [][] laberinto, Nodo actual, int id, String estrategia, Celda estadoObjetivo ) {
		ArrayList<Nodo> listNodos=new ArrayList<Nodo>();
		actual.getEstado().generarSucesores(laberinto);
		ArrayList<Sucesor> sucesoresNodo = actual.getEstado().getSucesores(); 
		Nodo aux = null;
		for(int i=0; i<sucesoresNodo.size(); i++) {
			aux = new Nodo(++id, actual, estrategia, sucesoresNodo.get(i), estadoObjetivo);
			listNodos.add(aux);
		}
		return listNodos;
	}

}
// Fin clase Main