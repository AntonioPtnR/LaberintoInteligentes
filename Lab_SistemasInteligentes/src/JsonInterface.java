/* Nombre de la clase: JsonInterface
 * Nombre del autor o autores: Julio Molina Diaz, Alvaro Pardo Benito, Antonio Paton Rico
 * Fecha de lanzamiento|creacion: 30/11/2020 | 28/11/2020
 * Version de clase: 1.1
 * Descripcion de la clase: Esta clase almacena los metodos relacionados con JSON
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonInterface {
	public JsonInterface() {

	}

	public Laberinto leerJson(String archivo) throws FileNotFoundException, IOException, ParseException {
		Laberinto labJson = null;
		HashMap celdas = new HashMap<String, Object>();
		JSONParser parser = new JSONParser();
		Celda[][] laberinto = null;

		Object obj = parser.parse(new FileReader(archivo));
		JSONObject jsonObject = (JSONObject) obj;
		long filas = (long) jsonObject.get("rows");
		long columnas = (long) jsonObject.get("cols");
		long max_n = (long) jsonObject.get("max_n"); 
		laberinto = new Celda[(int) filas][(int) columnas];
		String id_mov = jsonObject.get("id_mov").toString();
		String mov = jsonObject.get("mov").toString();
		celdas = (HashMap) jsonObject.get("cells");

		for (int f = 0; f < laberinto.length; f++) {
			for (int c = 0; c < laberinto[f].length; c++) {
				String key;
				key = "(" + f + ", " + c + ")";
				Celda aux = null;
				HashMap mapaux = new HashMap<String, HashMap>();
				mapaux = (HashMap) celdas.get(key);
				String value = mapaux.get("value").toString();
				String vecinosaux = mapaux.get("neighbors").toString().replace("]", "").replace("[", "");
				String[] parts = vecinosaux.split(",");
				boolean[] vecinos = new boolean[Constantes.NUM_VECINOS];
				for (int i = 0; i < parts.length; i++) {
					if (parts[i].equals("true")) {
						vecinos[i] = true;
					} else {
						vecinos[i] = false;
					}
				}
				aux = new Celda(f, c, Integer.parseInt(value), vecinos);
				laberinto[f][c] = aux;
			}
		}

		labJson = new Laberinto((int)filas, (int)columnas, laberinto);

		return labJson;
	}

	public void crearJson(Celda[][] laberinto, String nombre) throws IOException {
		FileWriter file = new FileWriter(nombre);
		JSONObject obj = new JSONObject();
		obj.put("rows", laberinto.length);
		obj.put("cols", laberinto[0].length);
		obj.put("max_n", Constantes.MAX_N);

		JSONArray norte = new JSONArray();
		norte.add(Constantes.MOV[0][0]);
		norte.add(Constantes.MOV[0][1]);

		JSONArray este = new JSONArray();
		este.add(Constantes.MOV[1][0]);
		este.add(Constantes.MOV[1][1]);

		JSONArray sur = new JSONArray();
		sur.add(Constantes.MOV[2][0]);
		sur.add(Constantes.MOV[2][1]);

		JSONArray oeste = new JSONArray();
		oeste.add(Constantes.MOV[3][0]);
		oeste.add(Constantes.MOV[3][1]);

		JSONArray movimientos = new JSONArray();
		movimientos.add(norte);
		movimientos.add(este);
		movimientos.add(sur);
		movimientos.add(oeste);

		JSONArray idmov = new JSONArray();
		idmov.add(Constantes.ID_MOV[0]);
		idmov.add(Constantes.ID_MOV[1]);
		idmov.add(Constantes.ID_MOV[2]);
		idmov.add(Constantes.ID_MOV[3]);

		obj.put("id_mov", idmov);
		obj.put("mov", movimientos);
		Map<String, Object> celdas = new HashMap<String, Object>();
		String key = "";
		for (int f = 0; f < laberinto.length; f++) {
			for (int c = 0; c < laberinto[f].length; c++) {
				int valor = laberinto[f][c].getValue();
				boolean[] vecinos = laberinto[f][c].getVecinos();
				key = "(" + f + ", " + c + ")";
				JSONArray vecinosbol = new JSONArray();
				JSONObject obj1 = new JSONObject();
				JSONObject obj3 = new JSONObject();
				vecinosbol.add(vecinos[0]);
				vecinosbol.add(vecinos[1]);
				vecinosbol.add(vecinos[2]);
				vecinosbol.add(vecinos[3]);
				obj3.put("neighbors", vecinosbol);
				obj3.put("value", valor);
				celdas.put(key, obj3);
			}
		}
		obj.put("cells", celdas);
		file.write(obj.toJSONString());
		file.flush();
		file.close();
	}

	public void generarProblema(String idEstadoInicial, String idEstadoObjetivo, String nombreJson, String nombLaberinto) throws IOException {		
		FileWriter file = new FileWriter(nombreJson);
		JSONObject obj = new JSONObject();
		obj.put("INITIAL", idEstadoInicial);
		obj.put("OBJETIVE", idEstadoObjetivo);
		obj.put("MAZE", nombLaberinto);
		file.write(obj.toJSONString());
		file.flush();
		file.close();
	}

	public Problema leerProblema(String archivo, int[] estInicial, int[] estObjetivo, Laberinto maze, Problema prob) throws FileNotFoundException, IOException, ParseException, LeerJsonException, NullPointerException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(archivo));
		JSONObject jsonObject= (JSONObject) obj;
		String idEstadoInicial = jsonObject.get("INITIAL").toString();
		String idEstadoObjetivo = jsonObject.get("OBJETIVE").toString();
		idEstadoInicial = idEstadoInicial.replace("(","").replace(")","").replaceAll(" ", "").replace(",", ", ");
		idEstadoObjetivo = idEstadoObjetivo.replace("(","").replace(")","").replaceAll(" ", "").replace(",", ", ");
		String[] auxinicial = idEstadoInicial.split(", ");
		String[] auxobjetivo = idEstadoObjetivo.split(", ");
		for(int i = 0; i<auxinicial.length; i++) {
			estInicial[i] = Integer.parseInt(auxinicial[i]);
			estObjetivo[i] = Integer.parseInt(auxobjetivo[i]);
		}

		String laberinto = jsonObject.get("MAZE").toString();
		maze = leerJson(laberinto);
		if((estInicial[0]<0 || estInicial[0]>maze.getMaze().length-1) || (estInicial[1]<0 || estInicial[1]>maze.getMaze()[0].length-1) || (estObjetivo[0]<0 || estObjetivo[0]>maze.getMaze().length-1) || (estObjetivo[1]<0 || estObjetivo[1]>maze.getMaze()[0].length-1)) {
			throw new LeerJsonException();
		}else {
			prob = new Problema(estInicial, estObjetivo, maze.getMaze());
		}

		return prob;
	}

}
// Fin clase JsonInterface