/**
* @file Query.java
* @brief Contiene la clase Query
*
* Este archivo contiene la implementacion de la clase Query que describe
* consultas con una frecuencia asociada
*/
public class Query{

	private String q;
	private int freq;

	/** 
	* Construye una Query con el texto dado como parametro
	* @param text texto de la consulta
	*/
	public Query (String text){
		q=text;
		freq=1;
	}
	
	/**
	* Modifica la frecuencia de la consulta
	* @param f valor de la frecuencia
	*/
	public void setFreq(int f){
		freq=f;
	}

	/**
	* Devuelve el texto de la consulta
	* @returns texto de la consulta
	*/
	public String getText(){
		return q;
	}
	
	/**
	* Devuelve la frecuencia de la consulta
	* @returns frecuencia de la consulta
	*/
	public int getFreq(){
		return freq;
	}
}
