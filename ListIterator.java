/**
* @file ListIterator.java
* @brief Contiene la clase ListIterator generica
*
* Este archivo contiene la implementacion de la interfaz IteratorIF hecha
* en base a objetos genericos
*/

public class ListIterator<T> implements IteratorIF<T>
{
	private ListIF<T> handler;
	private ListIF<T> restart;

	/**
	* Constructor para ListIterator.
	* @param handler el manejador de listas.
	*/
	public ListIterator (ListIF<T> handler)
	{
		this.handler = handler;
		this.restart = handler;
	}

	/**
	* Devuelve el siguiente elemento de la iteracion.
	* @return el siguiente elemento de la iteracion.
	*/
	@Override
	public T getNext ()
	{
		T next = handler.getFirst ();
		handler = handler.getTail ();
		return next;
	}

	/**
	* Devuelve cierto si existen mas elementos a iterar.
	* @return cierto si existen mas elementos a iterar.
	*/
	@Override
	public boolean hasNext ()
	{
		return !handler.isEmpty ();
	}

	/**
	* Restablece el iterador para volver al inicio.
	*/
	@Override
	public void reset ()
	{
		handler = restart;
	}
	/*
	@Override
	public int hashCode () {}
	@Override
	public boolean equals (Object o){}
	@Override
	public String toString () {}
	*/
}
