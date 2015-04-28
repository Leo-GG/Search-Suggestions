/**
* @file StackDynamic.java
* @brief Contiene la implementacion de la interfaz StackIF a traves de una
* pila dinamica
*
* Este archivo contiene la implementacion de la interfaz StackIF hecha
* utilizando una pila dinamica. El archivo contiene la clase StackDynamic
* junto con todos sus metodos 
*/

/**
* @class StackDynamic
* Clase que describe una pila StackIF implementada como una pila dinamica
*/
public class StackDynamic <T> implements StackIF<T> 
{
	private T element;
	private StackDynamic<T> next;
	
	/**
	* Constructor
	* Crea una pila vacia
	*/
	public StackDynamic (){
		element = null;
		next = null;
	}

	/** 
	* Constructor
	* Crea una pila en base a otra dada como parametro
	* @param Pila 
	*/
	public StackDynamic (StackIF<T> stack){
		this ();
		if (stack != null)
			if (!stack.isEmpty ()){
				element = stack.getTop ();
				next = new StackDynamic<T> (stack.pop ());
				stack.push (element);
			}
	}
	
	/** 
	* Constructor
	* Crea una pila en base a una lista dada como parametro
	* @param Lista 
	*/
	public StackDynamic (ListIF<T> list){
		this ();
		if (list != null)
			if (!list.isEmpty()) {
				element = list.getFirst ();
				next = new StackDynamic<T> (list.getTail ());
			}
	}
	
	/**
	* Devuelve la cima de la pila.
	* @return la cima de la pila.
	*/
	@Override
	public T getTop (){
		return element;
	}

	/**
	* Inserta un elemento a la pila.
	* @param element El elemento a ser a�adido.
	* @return la pila incluyendo el elemento.
	*/	
	@Override
	public StackIF<T> push (T element){
		if (element != null) {
			StackDynamic<T> stack = new StackDynamic<T> ();
			stack.element = this.element;
			stack.next = this.next;
			this.element = element;
			this.next = stack;
		}
		return this;
	}

	/**
	* Extrae de la pila el elemento en la cima.
	* @return la pila excluyendo la cabeza.
	*/	
	@Override
	public StackIF<T> pop (){
		if (!isEmpty ()) {
			element = next.element;
			next = next.next;
		}
		return this;
	}

	/**
	* Devuelve cierto si la pila esta vacia.
	* @return cierto si la pila esta vacia.
	*/	
	@Override
	public boolean isEmpty (){
		return element == null && next == null;
	}

	/**
	* Devuelve cierto si la pila esta llena.
	* @return cierto si la pila esta llena.
	*/	
	@Override
	public boolean isFull(){
		return !isEmpty();
	}

	/**
	* Devuelve el numero de elementos de la pila.
	* @return el numero de elementos de la pila.
	*/	
	@Override
	public int getLength (){
		if (isEmpty ()) 
			return 0;
		return 1 + next.getLength ();
	}

	/**
	* Devuelve cierto si la pila contiene el elemento.
	* @param element El elemento buscado.
	* @return cierto si la pila contiene el elemento.
	*/	
	@Override
	public boolean contains (T element){
		if (isEmpty ()) 
			return false;
		else 
			return this.element.equals (element) || next.contains (element);
	}
	/**
	* Devuelve un iterador para la pila.
	* @return un iterador para la pila.
	*/	
	@Override
	public IteratorIF<T> getIterator (){
		StackIF<T> handler = new StackDynamic<T> (this);
		return new StackIterator<T> (handler);
	}
	
/*	@Override
	public int hashCode (){
		return 31 * ((element == null) ? 0 : element.hashCode ()) +
		((next == null) ? 0 : next.hashCode ());
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals (Object o) {
	
	}
	
	@Override
	public String toString () {
		StringBuffer buff = new StringBuffer ();
		buff.append ("StackDynamic -[");
		IteratorIF<T> listIt = getIterator ();
		while (listIt.hasNext ()) {
			T element = listIt.getNext();
			buff.append (element);
			if (listIt.hasNext ())
				buff.append (", ");
		}
		buff.append ("]");
		return buff.toString ();
	}
	
	public StackIF<T> inversa(){
		StackDynamic<T> inv = new StackDynamic<T>();
		if (!isEmpty())
			inv = (StackDynamic<T>) inversaAux(inv);
		return inv;
	}
	public StackIF<T> inversaAux (StackIF<T> inv){
		inv.push(getTop());
		if (!next.isEmpty())
			inv = next.inversaAux(inv);
		return inv;
	}*/
	
}
