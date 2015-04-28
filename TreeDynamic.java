/**
* @file TreeDynamic.java
* @brief Contiene la Implementacion de la interfaz TreeIF a traves de un
* arbol dinamico
*
* Este archivo contiene la implementacion de la interfaz TreeIF hecha
* utilizando un arbol dinamico. El archivo contiene la clase TreeDynamic
* junto con todos sus metodos 
*/

/**
* @class TreeDynamic
* Clase que describe un arbol QueueIF implementado como un arbol dinamico
*/
public class TreeDynamic<T> implements TreeIF<T>
{

	private T root;
	private QueueIF<TreeIF <T>> children;
	
	/** 
	* Constructor
	* Crea un arbol vacio
	*/
	public TreeDynamic()
	{
		this.root =null;
		this.children = new QueueDynamic <TreeIF <T>> ();
	}

	/**
	* Constructor
	* Crea un arbol con un elemento dado como raiz
	* @param Elemento raiz
	*/
	public TreeDynamic(T element)
	{
		this ();
		this.root = element;
	}

	/** 
	* Constructor
	* Crea un arbol con un arbol parametro como modelo
	* @param Arbol
	*/
	public TreeDynamic (TreeIF<T> tree)
	{
		this.root = tree.getRoot ();
		this.children = new QueueDynamic <TreeIF <T>> ();
		
		ListIF <TreeIF<T>> tChildren=tree.getChildren();
		while(!tChildren.isEmpty())
		{
			TreeIF <T> aChild =tChildren.getFirst();
			TreeIF <T> cChild =new TreeDynamic <T> (aChild);
			children.add (cChild);
			tChildren = tChildren.getTail();
		}
	}

	/**
	* Devuelve el elemento raiz del arbol.
	* @return el elemento raiz del arbol.
	*/
	@Override
	public T getRoot ()
	{
		return this.root;
	}

	/**
	* Establece el elemento raiz.
	* @param element El elemento a establecer.
	*/
	@Override
	public void setRoot(T element)
	{
		if (element!=null) this.root=element;
	}

	/**
	* Devuelve los hijos del nodo en curso.
	* @return los hijos del arbol
	*/
	@Override
	public ListIF<TreeIF <T>> getChildren()
	{
		ListIF <TreeIF <T>> lChildren = new ListDynamic <TreeIF <T>> ();
		StackIF <TreeIF <T>> sChildren = new StackDynamic <TreeIF <T>> ();
		IteratorIF<TreeIF<T>> childrenIt = children.getIterator ();
		while (childrenIt.hasNext())
		{
			TreeIF<T> aChild = childrenIt.getNext();
			sChildren.push (aChild);
		}
		while (!sChildren.isEmpty())
		{
			TreeIF<T> aChild = sChildren.getTop();
			lChildren.insert (aChild);
			sChildren.pop();
		}
		return lChildren;

	}

	/**
	* Inserta un subarbol como ultimo hijo.
	* @param child el hijo a insertar.
	*/
	@Override
	public void addChild (TreeIF<T> child)
	{
		if (this.root!=null) children.add (child);		
	}

	/**
	* Extrae un subarbol como hijo.
	* @param index el indice del subarbol con base en 0.
	*/
	@Override
	public void removeChild(int index)
	{
		QueueIF <TreeIF <T>> aux = new QueueDynamic <TreeIF<T>> ();
		for (int i =0; i<children.getLength (); i++)
		{
			TreeIF<T> aChild = children.getFirst ();
			if (i!=index) aux.add (aChild);
			children.remove ();
		}
		children = aux; 
	}

	/**
	* Devuelve cierto si el arbol es un nodo hoja.
	* @return cierto si el arbol es un nodo hoja
	*/
	@Override
	public boolean isLeaf()
	{
		return children.isEmpty();
	}

	/**
	* Devuelve cierto si el arbol esta vacio.
	* @return cierto si el arbol esta vacio.
	*/
	@Override
	public boolean isEmpty()
	{
		return this.root == null &&
			children.isEmpty();
	}
	/**
	* Devuelve cierto si la lista contiene el elemento.
	* @param element El elemento buscado.
	* @return cierto si la lista contiene el elemento.
	*/
	@Override
	public boolean contains (T element)
	{
		if (this.root.equals(element)) return true;
		else
		{
			IteratorIF <TreeIF<T>> childrenIt = children.getIterator ();
			boolean found = false;
			while (!found && childrenIt.hasNext ())
			{
				TreeIF <T> aChild= childrenIt.getNext();
				found = aChild.contains (element);
			}
			return found;
		}
	}

	/**
	* Devuelve un iterador para la lista.
	* @param trasversalType El tipo de recorrido.
	* @return un iterador para la lista.
	*/
	@Override 
	public IteratorIF<T> getIterator (int type)
	{
		TreeIF<T> handler = new TreeDynamic <T> (this);
		return new TreeIterator<T> (handler, type);		
	}
	
}
