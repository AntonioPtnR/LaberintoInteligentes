/* Nombre de la clase: Constantes
 * Nombre del autor o autores: Julio Molina Diaz, Alvaro Pardo Benito, Antonio Paton Rico
 * Fecha de lanzamiento|creacion: 30/11/2020 | 08/10/2020
 * Version de clase: 1.9
 * Descripcion de la clase: Esta clase define las constantes que aparecen en todo el programa principal
 */

public class Constantes {
	final static int MAX_N = 4;
	final static int OP_GENERAR_ALEAT = 1;
	final static int OP_LEER_JSON = 2;
	final static int OP_GENERAR_PROBLEMA = 3;
	final static int OP_LEER_PROBLEMA = 4;
	final static int OP_SALIR = 5;
	final static int MENU_LIM_INF = 1;
	final static int MENU_LIM_SUP = 2;
	final static int MAX_PROFUNDIDAD = 1000000;

	final static String[] ID_MOV = {"N", "E", "S", "O"};
	final static int[][] MOV = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

	final static String MSJ_ERROR_MENU = "Error, debe elegir una opcion correcta:\n1. Generacion aleatoria\n2. Leer laberinto formato json\n3. Generar Problema\n4. leer Problema \n5. Salir";
	final static String MSJ_ERROR_FILCOL = "Error, introduce un numero entero positivo";
	final static String MSJ_ERROR_LIM_OPC = "Opción no valida. Vuelva a intentarlo.\n";
	final static String MSJ_ERROR_TIP_DATO = "Tipo de dato incorrecto. Vuelva a intentarlo.\n";
	final static String MSJ_ERROR_FNF_EXCP = "El nombre del fichero introducido no es correcto";
	final static String MSJ_ERROR_JSON = "Error en la creación del JSON ";
	final static String MSJ_ERROR_ENTSAL = "Error en la entrada/salida ";
	final static String MSJ_ERROR_EXCP = "Excepcion leyendo fichero de configuracion ";
	final static String MSJ_ERROR_OPC = "Vuelva a introducir una opción del menú.\n";
	final static String MSJ_ERROR_JSON_INC = "Archivo json inconsistente, vuelva a introducir un .json consistente";
	final static String MSJ_ERROR_ESTADOS_NO_VALIDOS = "Error, estados no incluidos en el laberinto";

	final static String MSJ_INFO_JSON_LEIDO = "JSON leído";
	final static String MSJ_INFO_PROB_GENER = "Problema generado";
	final static String MSJ_INFO_SOL_ENC = "Solución encontrada!\nIntroduce nombre del fichero";
	final static String MSJ_INFO_NO_SOL = "No hay solución";
	final static String REPRESENT_NODO = "[id][cost,state,father_id,action,depth,h,value]\n";

	final static String INTRODUCE_ID_EST_INICIAL = "1.Introduce el identificador del estado inicial (f,c):";
	final static String INTRODUCE_ID_EST_OBJETIVO = "2.Introduce el identificador del estado objetivo (f,c):";
	final static String INTRODUCE_NOM_FICH_JSON_CONTIENE_LAB = "3.Introduce el nombre del fichero json que contiene el laberinto:";
	final static String INTRODUCE_NOM_FICH_JSON_CONTENDRA_PROB = "4.Introduce el nombre del fichero json que va a contener el problema:";
	final static String INTRODUCE_NOM_FICH_JSON_CONTIENE_PROB = "Introduce el nombre del json que contiene el problema:";

	final static String INTRODUCE_FILAS = "Introduce el numero de filas del laberinto";
	final static String INTRODUCE_COLUMNAS = "Introduce el numero de columnas del laberinto";
	final static String GENERANDO_IMAGEN = "Generando imagen del laberinto; introduce el nombre que desea para el laberinto (sin extension .jpg)";
	final static String INTRODUCE_NOM_ARCHIVO = "Leyendo archivo Json; introduce el nombre del archivo:)";
	final static String INTRODUCE_NOM_ARCHIVOJSON = "Creando archivo Json del laberinto generado; introduce un nombre para el fichero ";
	final static String ESTADO_FINAL_LABERINTO = "##########################\n# Estado Final Laberinto #\n##########################";	
	final static String MENU = "Introduce la opción que desees:\n1. Generacion aleatoria\n2. Leer laberinto formato json\n3. Generar Problema\n4. leer Problema \n5. Salir ";
	final static String FINALIZACION = "\nPrograma finalizado.\nArchivo JSON (.json) e imagen (.jpg) generados correctamente.";
	final static String SALIR = "Saliendo...";

	final static int VECINO_NORTE = 0;
	final static int VECINO_ESTE = 1;
	final static int VECINO_SUR = 2;
	final static int VECINO_OESTE = 3;
	final static int NUM_VECINOS = 4;

	final static int N_PIXELS_FONDO = 2000;

	final static String[] ESTRATEGIA = {"BREADTH", "DEPTH", "UNIFORM", "A", "GREEDY"};

}
// Fin clase Constantes