/**
* @file IteratorIF.java
* @brief Contiene interfaz de los iteradores
*
* Este archivo contiene la interfaz de los iteradores, descrita en base
* a objetos genericos
*/


public interface IteratorIF<T>
{
	/**
	* Devuelve el siguiente elemento de la iteracion.
	* @return el siguiente elemento de la iteracion.
	*/
	public T getNext ();
	/**
	* Devuelve el siguiente elemento de la iteracion.
	* @return el siguiente elemento de la iteracion.
	*/
	public boolean hasNext ();
	/**
	* Restablece el iterador para volver al inicio.
	*/
	public void reset ();
}
