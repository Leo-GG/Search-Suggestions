/**
* @mainpage PED Estrategias de Programacion y Estructuras de Datos
*
* <b>Función del programa</b>: <p>El programa devuelve una lista de consultas
* contenidas en un archivo de consultas previas que pueden formarse usando
* un parametro dado como prefijo. La lista de consultas se devuelve por orden
* decreciente de frecuencia y por orden lexicografico creciente para aquellas
* consultas con la misma frecuencia</p>
*
* <b>Datos de entrada</b>: <p>El programa lee 2 archivos: un archivo que 
* contiene el historial de busquedas y otro que contiene las operaciones a 
* realizar sobre el historial. Ademas se requiere un parametro que indica
* la estructura de datos que se quiere usar para implementar el deposito de
* consultas: una lista dinamica (L) o un arbol dinamico (T) </p>
*
* <b>Datos de salida</b>: <p>El programa imprime los datos de salida en 
* pantalla. La primera linea de salida indica cuantas consultas diferentes
* se han alamcenado en el fichero de consultas. Para cada linea del fichero
* de operaciones se indica el resultado y en la siguiente linea el coste de 
* la operacion</p>
*
* <b>Uso</b>: <p>$java -jar eped2015.jar [estructura] [consultas] [operaciones]
* \n[estructuras]: parametro que selecciona la estructura de datos que se 
* desea utilizar
* \n[consultas]: el nombre del fichero que contiene las consultas que han de 
* almacenarse en el deposito
* \n[operaciones]: el nombre del fichero que contiene las operaciones que se 
* han de realizar sobre las consultas almacenadas en el deposito
* </p>
*              
* @author Leonardo Garma
* @version 0.1.0 14/04/2015
*/


import java.util.Arrays;
import java.io.*;


/**
* @file eped2015.java
* @brief Contiene la clase eped2015 y sus metodos
*
* Implementa una clase para leer los parametros de entrada, instanciar las
* estructuras de datos correspondientes, invocar las operaciones requeridas e
* imprimir los resultados
*/
public class eped2015
{

	
	private QueryDepot depot;

	/**
	* Constructor 
	* Crea un objeto eped2015 
	*/
	public eped2015(String[] args)
	{
		if  (args.length!=3)
		{
			throw new IllegalArgumentException(
				"Numero de parametros incorrecto (!=3)");
		}
		if (args[0].equals("L"))
		{
			depot=new QueryDepotList();
		}
		else if (args[0].equals("T"))
		{
			depot=new QueryDepotTree();
		}
		else
		{
			throw new IllegalArgumentException(
				"Estructura de datos elegida incorrecta");
		}
		String dataFile=args[1];
		String operationsFile=args[2];
		processInput(dataFile);
		processOperations(operationsFile);
	}

	/**
	* Procesa el archivo con la lista de consultas
	* @param Archivo con la lista de consultas
	*/
	private void processInput(String inputFile)
	{
		String sCurrentLine;

 		try {
 
 			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			while ((sCurrentLine = br.readLine())!=null)
			{				
				depot.incFreqQuery(sCurrentLine);
			}
 		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Consultas almacenadas: "+depot.numQueries()+".");
	}	
	
	/**
	* Procesa el archivo con la lista de operaciones
	* @param Archivo con la lista de operaciones
	*/
	private void processOperations(String inputFile)
	{
		String sCurrentLine;
 		try {

 			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			while ((sCurrentLine = br.readLine())!=null)
			{
				String[] lineVals = sCurrentLine.split(" ");
				String option=lineVals[0];
				String param=sCurrentLine;
				param = param.replace(option+" ", "");
		
				if (option.equals("F"))
				{
					printFrequency(param);			
				}
				else if (option.equals("S"))
				{
					printSuggestions(param);
				}
			}
 		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	* Obtiene la frecuencia de una consulta del deposito, mide el tiempo 
	* medio de 100 operaciones iguales e imprime el resultado
	* @param Texto de la consulta
	*/
	private void printFrequency (String param)
	{
		int rep=100;
		int f =0;
		long tInicial= System.currentTimeMillis();
		for (int i=0; i<rep; i++)	
		{
			f = depot.getFreqQuery(param);
		}
		long tFinal= System.currentTimeMillis();
		double duracion = ((double)tFinal-(double)tInicial)  / (double)rep;
		System.out.println("La frecuencia de \""+param+
			"\" es "+f);
		System.out.println("-Tiempo: "+duracion);
	}

	/**
	* Obtiene la lista de sugerencias para un prefijo, mide el tiempo
	* medio de 100 operaciones iguales e imprime el resultado
	* @param Prefijo
	*/
	private void printSuggestions (String param)
	{
		int rep=100;
		ListIF<Query> suggestions = new ListDynamic<Query>();
		long tInicial= System.currentTimeMillis();
		for (int i=0; i<rep; i++)	
		{
			suggestions = depot.listOfQueries(param);
		}
		long tFinal= System.currentTimeMillis();
		double duracion = ( (double)tFinal-(double)tInicial )  / (double)rep;
		System.out.println("La lista de sugerencias para \""+param+
			"\" es: ");
		printList(suggestions);
		System.out.println("-Tiempo: "+duracion);
	}

	/**
	* Imprime una lista de consultas junto con su frecuencia
	* @param Lista de consultas (Query)
	*/
	public void printList(ListIF<Query> list)
	{
		if (!list.isEmpty()){
			StringBuffer buff = new StringBuffer ();
			IteratorIF<Query> listIt = list.getIterator ();
			while (listIt.hasNext ()) {
				Query element = listIt.getNext ();
				buff.append ("  \"");
				buff.append (element.getText());
				buff.append ("\"");
				buff.append (" con frecuencia ");
				buff.append (element.getFreq());
				buff.append (".\n");
			}
			System.out.print(buff.toString ());
		}

	}

	public static void main(String[] args) {
		eped2015 test = new eped2015(args);	    
	}
}
