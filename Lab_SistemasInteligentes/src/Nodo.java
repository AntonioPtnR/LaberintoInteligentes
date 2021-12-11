/* Nombre de la clase: Nodo
 * Nombre del autor o autores: Julio Molina Diaz, Alvaro Pardo Benito, Antonio Paton Rico
 * Fecha de lanzamiento|creacion: 30/11/2020 | 30/10/2020
 * Version de clase: 1.5
 * Descripcion de la clase: Esta clase define al objeto Nodo que forma el arbol de busqueda
 */

import java.util.ArrayList;

public class Nodo implements Comparable<Nodo> {
	private int id_nodo;
	private Celda estado;
	private int profundidad;
	private double costo;
	private double heuristica;
	private double valor; 
	private char accion_previa; 
	private Nodo padre; 

	public Nodo(int id_nodo,Nodo padre,String estrategia, Sucesor estadoSucesor, Celda estadoObjetivo) { //pasamos el estado objetivo para calcular la función heurística
		this.id_nodo = id_nodo;
		this.padre = padre;
		this.estado = estadoSucesor.getEstado();
		this.costo = padre.getCosto()+estadoSucesor.getCosto_mov();
		this.accion_previa = estadoSucesor.getMove();
		this.profundidad = padre.getProfundidad()+1;
		this.heuristica = Math.abs(estado.getCordy()-estadoObjetivo.getCordy()) + Math.abs(estado.getCordx()-estadoObjetivo.getCordx());
		valor = estrategiaAlgoritmo(estrategia.toUpperCase());
	}

	public Nodo(int id_nodo,String estrategia, Celda estado, Celda estadoObjetivo) {
		this.padre = null;
		this.id_nodo=id_nodo;
		this.estado=estado;
		this.costo = 0;
		this.profundidad = 0;
		estrategia = estrategia.toUpperCase();
		heuristica = Math.abs(estado.getCordy()-estadoObjetivo.getCordy()) + Math.abs(estado.getCordx()-estadoObjetivo.getCordx());
		valor = estrategiaAlgoritmo(estrategia);

	}

	public double estrategiaAlgoritmo(String estrategia ) {
		double valor = 0;
		if(estrategia.equals("DEPTH"))
			valor = 1.0/(profundidad + 1);
		else if(estrategia.equals("BREADTH"))
			valor = profundidad;
		else if(estrategia.equals("UNIFORM"))
			valor = costo;
		else if(estrategia.equals("A")) 
			valor=costo + heuristica;
		else if(estrategia.equals("GREEDY")) 
			valor = heuristica;

		return valor;
	}

	public int getId_nodo() {
		return id_nodo;
	}

	public void setId_nodo(int id_nodo) {
		this.id_nodo = id_nodo;
	}

	public Celda getEstado() {
		return estado;
	}

	public void setEstado(Celda estado) {
		this.estado = estado;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public double getHeuristica() {
		return heuristica;
	}

	public void setHeuristica(int heuristica) {
		this.heuristica = heuristica;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public char getAccion_previa() {
		return accion_previa;
	}

	public void setAccion_previa(char accion_previa) {
		this.accion_previa = accion_previa;
	}

	public Nodo getPadre() {
		return padre;
	}

	public void setPadre(Nodo padre) {
		this.padre = padre;
	}

	@Override
	public int compareTo(Nodo n) {
		if(valor>n.getValor())
			return 1;
		else if(valor<n.getValor())
			return -1;
		else {
			if(estado.getCordy()>n.getEstado().getCordy())
				return 1;
			else if(estado.getCordy()<n.getEstado().getCordy())
				return -1;
			else {
				if(estado.getCordx()>n.getEstado().getCordx())
					return 1;
				else if(estado.getCordx()<n.getEstado().getCordx())
					return -1;
				else
					if(id_nodo>n.getId_nodo())
						return 1;
					else if(id_nodo<n.getId_nodo())
						return -1;
					else
						return 0;
			}
		}
	}

	public String toString() {
		return "["+id_nodo+"] ["+costo+",("+estado.getCordy()+","+estado.getCordx()+"),"+padre.id_nodo+","+accion_previa+","+profundidad+","+heuristica+" ,"+valor+"]";
	}

	public String toStringOrigen() {
		return "["+id_nodo+"] ["+costo+",("+estado.getCordy()+","+estado.getCordx()+"),None,None,"+profundidad+","+heuristica+","+valor+"]";
	}

}
//Fin clase Nodo