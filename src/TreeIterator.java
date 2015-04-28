/**
* @file TreeIterator.java
* @brief Contiene la clase TreeIterator
*
* Este archivo contiene la implementacion de la clase TreeIterator
*/


/**
* @class TreeIterator
* Clase que describe iterador generico sobre arboles
*/
public class TreeIterator<T> implements IteratorIF<T>
{
	private IteratorIF<T> iterator;
	
	/**
	* Constructor
	* @param type el tipo de recorrido.
	* @param handler el manejador de colas.
	*/
	public TreeIterator (TreeIF<T> handler, int type)
	{
		QueueIF<T> traverse=null;
		switch (type)
		{
			case TreeIF.PREORDER: traverse = preorder (handler); 
			break;
			case TreeIF.POSTORDER: traverse = postorder (handler); 
			break;
			case TreeIF.LRBREADTH: traverse = lrBreadth (handler); 
			break;
			case TreeIF.RLBREADTH: traverse = rlBreadth (handler); 
			break;	
		}
		this.iterator = new QueueIterator<T> (traverse);	
	}
	
	/**
	* Devuelve el siguiente elemento de la iteracion.
	* @return el siguiente elemento de la iteracion.
	*/
	@Override
	public T getNext ()
	{
		return iterator.getNext();
	}
	
	/**
	* Devuelve cierto si existen mas elementos a iterar.
	* @return cierto si existen mas elementos a iterar.
	*/
	@Override
	public boolean hasNext ()
	{
		return iterator.hasNext ();
	}
	
	/**
	* Restablece el iterador para volver iterar.
	*/
	@Override
	public void reset ()
	{
		iterator.reset();
	}

	/**
	* Devuelve los elementos del arbol recorrido en preorden
	*/
	private QueueIF<T> preorder (TreeIF<T> tree)
	{
		QueueIF<T> traverse = new QueueDynamic<T> ();
		T element = tree.getRoot ();
		traverse.add (element);
		IteratorIF <TreeIF <T>> childrenIt = tree.getChildren().getIterator();
		while (childrenIt.hasNext())
		{
			TreeIF <T> aChild = childrenIt.getNext();
			QueueIF <T> aTraverse = preorder (aChild);
			addAll (traverse, aTraverse);
		}
		return traverse;
	}

	/**
	* Devuelve los elementos del arbol recorrido en postorden
	*/
	private QueueIF<T> postorder (TreeIF<T> tree)
	{
		QueueIF<T> traverse = new QueueDynamic<T> ();
		T element = tree.getRoot ();
		IteratorIF <TreeIF <T>> childrenIt = tree.getChildren().getIterator();
		while (childrenIt.hasNext())
		{
			TreeIF <T> aChild = childrenIt.getNext();
			QueueIF <T> aTraverse = postorder (aChild);
			addAll (traverse, aTraverse);
		}
		traverse.add(element);
		return traverse;
	}

	/**
	* Devuelve los elementos del arbol recorrido en anchura
	*/
	private QueueIF<T> lrBreadth (TreeIF<T> tree)
	{
		QueueIF<T> traverse = new QueueDynamic<T> ();
		QueueIF<TreeIF<T>> aux = new QueueDynamic<TreeIF<T>> ();
		aux.add(tree);
		while (!aux.isEmpty())
		{
			TreeIF<T> aTree = aux.getFirst();
			T element = aTree.getRoot();
			IteratorIF <TreeIF<T>> childrenIt = aTree.getChildren().getIterator();
			while (childrenIt.hasNext())
			{
				TreeIF <T> aChild = childrenIt.getNext();
				aux.add (aChild);
			}
			traverse.add(element);
			aux.remove();
		}
		return traverse;
	}

	/**
	* Devuelve los elementos del arbol recorrido en anchura inversa
	*/
	private QueueIF<T> rlBreadth (TreeIF<T> tree)
	{
		QueueIF<T> traverse = lrBreadth(tree);
		StackIF<T> aux= new StackDynamic<T>();
		while (!traverse.isEmpty())
		{
			T element = traverse.getFirst();
			aux.push (element);
			traverse.remove();
		}
		while (!aux.isEmpty())
		{
			T element = aux.getTop();
			traverse.add (element);
			aux.pop();
		}
		return traverse;
	}

	private void addAll (QueueIF<T> q, QueueIF<T> p)
	{
		while (!p.isEmpty())
		{
			T element = p.getFirst();
			q.add (element);
			p.remove();
		}		
	}
}
