/**
* @file QueryDepot.java
* @brief Contiene la interfaz QueryDepot
*
* Este archivo contiene la interfaz QueryDepot 
*/

public interface QueryDepot {

	/* 
	* Devuelve el numero de consultas diferentes almacenadas sin contar
	* repeticiones
	* @returns numero de consultas almacenadas
	*/
	public int numQueries();

	/*
	* Devuelve la frecuencia con la que aparece una consulta especifica
	* en el deposito
	* @param q texto de la consulta
	* @returns frecuencia de la consulta
	*/ 
	public int getFreqQuery (String q);

	/*
	* Toma un parametro como prefijo y devuelve una lista de consultas
	* almacenadas que pueden generarse a partir del prefijo, ordenadas
	* por orden de frecuencia
	* @returns lista de consultas ordenada por frecuencias
	* @param prefix el prefijo dado
	*/
	public ListIF<Query> listOfQueries (String prefix);

	/*
	* Incrementa en uno la frecuencia de una consulta almacenada. Si la 
	* consulta no existe, se anade al deposito
	* @param q texto de la consulta
	*/
	public void incFreqQuery (String q);

	/* 
	* Decrementa en uno la frecuencia de una consulta almacenada,
	* eliminandola si la frecuencia es 0
	* @param q texto de la consulta
	*/
	public void decFreqQuery (String q);
}
