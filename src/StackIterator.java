/**
* @file StackIterator.java
* @brief Contiene la clase StackIterator
*
* Este archivo contiene la implementacion de la clase StackIterator
*/


/**
* @class StackIterator
* Clase que describe iterador generico sobre pilas
*/
class StackIterator<T> implements IteratorIF<T>
{
	private StackIF<T> handler;
	private StackIF<T> restart;
	
	/**
	* Constructor
	* @param handler el manejador de pilas.
	*/
	public StackIterator (StackIF<T> handler){
		this.handler = handler;
		this.restart = new StackDynamic<T> (handler);
	}
	
	/**
	* Devuelve el siguiente elemento de la iteracion.
	* @return el siguiente elemento de la iteracion.
	*/
	@Override
	public T getNext (){
		T top = handler.getTop ();
		handler.pop ();
		return top;
	}
	
	/**
	* Devuelve cierto si existen mas elementos a iterar.
	* @return cierto si existen mas elementos a iterar.
	*/
	@Override
	public boolean hasNext (){
		return !handler.isEmpty ();
	}
	
	/**
	* Restablece el iterador para volver al inicio
	*/
	@Override
	public void reset (){
		handler = new StackDynamic<T> (restart);
	}
	/*
	@Override
	public int hashCode () { ... }
	@Override
	public boolean equals (Object o){ ... }
	@Override
	public String toString () {...}
	*/
}
