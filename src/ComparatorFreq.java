/**
* @file ComparatorFreq.java
* @brief Contiene la clase ComparatorFreq
*
* Este archivo contiene la implementacion de un comparador de objetos Query
* que permite ordenarlos por orden de frecuencia en primer lugar y por orden
* lexicografico en segundo lugar.
* Implementa el interfaz ComparatorIF
*/

/**
* @class ComparatorFreq
* Clase que contiene los metodos para comparar dos objetos de tipo Query
* por orden creciente de frecuencia (1) y lexicografico (2)
*/
public class ComparatorFreq implements ComparatorIF<Query>
{
	public static int LESS = -1;
	public static int EQUAL = 0;
	public static int GREATER = 1;

	/**
	* Compara dos elementos para indicar si el primero es
	* menor, igual o mayor que el segundo elemento.
	* @param e1 el primer elemento.
	* @param e2 el segundo elemento.
	* @return el orden de los elementos.
	*/
	@Override
	public int compare (Query e1, Query e2){
		if (isGreater(e1,e2))
			return GREATER;
		else if (isLess(e1,e2))
			return LESS;
		else	return EQUAL;
			

	}

	/**
	* Indica si un elemento es menor que otro.
	* @param e1 el primer elemento.
	* @param e2 el segundo elemento.
	* @return true si un elemento es menor que otro.
	*/
	@Override
	public boolean isLess (Query e1, Query e2){
		if (e1.getFreq()<e2.getFreq())
			return true;
		else if (e1.getFreq()==e2.getFreq())
			if (e1.getText().compareTo(e2.getText())<0)
				return true;
		return false;
	}

	/**
	* Indica si un elemento es igual que otro.
	* @param e1 el primer elemento.
	* @param e2 el segundo elemento.
	* @return true si un elemento es igual que otro.
	*/
	@Override
	public boolean isEqual (Query e1, Query e2){
		if ( (e1.getFreq()==e2.getFreq()) && 
		(e1.getText().compareTo(e2.getText())==0) )
			return true;
		else return false;
	}

	/**
	* Indica si un elemento es mayor que otro.
	* @param e1 el primer elemento.
	* @param e2 el segundo elemento.
	* @return true si un elemento es mayor que otro.
	*/
	@Override
	public boolean isGreater (Query e1, Query e2){
		if (e1.getFreq()>e2.getFreq())
			return true;
		else if (e1.getFreq()==e2.getFreq())
			if (e1.getText().compareTo(e2.getText())>0)
				return true;
		return false;
	}
}
