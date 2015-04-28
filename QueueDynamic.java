/**
* @file QueueDynamic.java
* @brief Contiene la Implementacion de la interfaz QueuIF a traves de una
* cola dinamica
*
* Este archivo contiene la implementacion de la interfaz QueueIF hecha
* utilizando una cola dinamica. El archivo contiene la clase QueuDynamic
* junto con todos sus metodos 
*/

/**
* @class QueueDynamic
* Clase que describe una cola QueueIF implementada como una cola dinamica
*/
public class QueueDynamic <T> implements QueueIF <T>
{
	private Node<T> first;
	private Node<T> last;
	
	/**
	* @class Node 
	* Clase privada que describe los nodos de la cola
	*/
	private class Node <T>{
		private T element;
		private Node<T> next;
	
		public Node ()
		{
			this.element = null;
			this.next = null;
		}
	
		public Node (T element) {
			this ();
			this.element = element;
		}
		public Node (T element, Node<T> next) {
			this ();
			this.element = element;
			this.next = next;
		}
	
		public T getElement (){
			return element;
		}
	
		public void setElement (T element)
		{
			this.element = element;
		}
	
		public Node<T> getNext ()
		{
			return next;
		}
	
		public void setNext (Node<T> next)
		{
			this.next = next;
		}
	}

	/**
	* Constructor
	* Crea una cola vacia
	*/
	public QueueDynamic () {
		first = null;
		last = null;
	}

	/**
	* Constructor
	* Crea una cola en base a otra dada como parametro
	* @param Cola 
	*/
	public QueueDynamic (QueueIF<T> queue) {
		this ();
		if (queue != null)
			for (int i = 0; i < queue.getLength (); i++) {
				T element = queue.getFirst ();
				add (element);
				queue.remove ();
				queue.add (element);
		}
	}
	/**
	* Constructor
	* Crea una cola en base a una lista dada como parametro
	* @param Lista
	*/
	public QueueDynamic (ListIF<T> list)
	{
		this ();
		if (list != null) {
			ListIF<T> l = list;
			while (!l.isEmpty ()){
				add (l.getFirst ());
				l = l.getTail ();
			}
		}
	}

	/**
	* Devuelve la cabeza de la cola.
	* @return la cabeza de la cola.
	*/
	@Override
	public T getFirst () {
		return first.getElement ();
	}

	/**
	* Inserta un nuevo elemento a la cola.
	* @param element El elemento a a�adir.
	* @return la cola incluyendo el elemento.
	*/	
	@Override
	public QueueDynamic<T> add (T element) {
		if (element != null) {
			if (isEmpty ()) {
				Node<T> aNode = new Node<T> (element);
				first = aNode;
				last = aNode;
			} else {
				Node<T> aNode = new Node<T> (element);
				last.setNext (aNode);
				last = aNode;
			}
		}
		return this;
	}

	/**
	* Borra la cabeza de la cola.
	* la cola excluyendo la cabeza.
	*/	
	@Override
	public QueueDynamic<T> remove () {
		if (getLength () == 1) {
			first = null;
			last = null;
		}
		if (getLength () > 1) {
			first = first.getNext ();
		}
		return this;
	}
	
	/**
	* Devuelve cierto si la cola esta vacia.
	* @return cierto si la cola esta vacia.
	*/
	@Override
	public boolean isEmpty () {
		return first == null &&
		last == null;
	}
	/**
	* Devuelve cierto si la cola esta llena.
	* @return cierto si la cola esta llena.
	*/	
	@Override
	public boolean isFull() {
		return !isEmpty();
	}

	/**
	* Devuelve el numero de elementos de la cola.
	* @return el numero de elementos de la cola.
	*/	
	@Override
	public int getLength () {
		int length = 0;
		Node<T> node = first;
		while (node != null) {
			length ++;
			node = node.getNext ();
		}
		return length;
	}

	/**
	* Devuelve cierto si la cola contiene el elemento.
	* @param element El elemento buscado.
	* @return cierto si la cola contiene el elemento.
	*/	
	@Override
	public boolean contains (T element){
		boolean found = false;
		Node<T> node = first;
		while (!found && node != null) {
			found = node.getElement ().equals (element);
			node = node.getNext ();
		}
		return found;
	}

	/**
	* Devuelve un iterador para la cola.
	* @return un iterador para la cola.
	*/
	@Override
	public IteratorIF<T> getIterator ()
	{
		QueueIF<T> handler = new QueueDynamic<T> (this);
		return new QueueIterator<T> (handler);
	}
/*	
	@Override
	public int hashCode ()
	{
		return 31 * ((first == null) ? 0 : first.hashCode ()) +
		((last == null) ? 0 : last.hashCode ());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals (Object o)
	{
		if (o == this) return true;
		if (o == null) return false;
		if (o.getClass () != this.getClass ()) return false;
		else {
			QueueDynamic<T> q = (QueueDynamic<T>) o;
			return q.first.equals (first) &&
			q.last.equals (last);
		}
	}
	
	@Override
	public String toString ()
	{
		StringBuffer buff = new StringBuffer ();
		buff.append ("QueueDynamic -[");
		IteratorIF<T> queueIt = getIterator ();
		while (queueIt.hasNext ()) {
			T element = queueIt.getNext ();
			buff.append (element);
			if (queueIt.hasNext ())
			buff.append (", ");
		}
		buff.append ("]");
		return buff.toString ();
	}*/
}
