/**
* @file QueryDepotTree.java
* @brief Contiene la Implementacion de la interfaz QueryDepot a traves de un 
* arbol dinamico
*
* Este archivo contiene la implementacion de la interfaz QueryDepot hecha
* utilizando un arbol dinamico. El archivo contiene la clase QueryDepotTree
* junto con todos sus metodos
*/

/**
* @class QueryDepotTree
* Clase que describe un QueryDepot implementado con arboles dinamicos
*/
public class QueryDepotTree implements QueryDepot{

	// El arbol que almancena las Queries
	private TreeDynamic<Query> depot;
	// Numero de consultas almacenadas
	private int N;

	/**
	* Constructor
	* Crea un arbol con una Query con frecuencia 0 como raiz
	*/
	public QueryDepotTree ()
	{
		Query root = new Query (" ");
		root.setFreq(0);
		depot = new TreeDynamic<Query>(root);
		N=0;
	}

	/**
	* Devuelve el numero de Queries distintas y con frecuencia
	* mayor que 0 almacenadas hasta el momento.
	* @return numero de Queries almacenadas
	*/
	@Override
	public int numQueries()
	{
		return N;
	}

	/**
	* Incrementa la frecuencia de una consulta dada. Si la consulta
	* no se ha almacenado previamente, se crea una nueva Query que la 
	* contenga.
	* @param q Un string con la consulta cuya frecuencia se incrementara
	*/
	@Override
	public void incFreqQuery (String q)
	{

		int len=q.length();
		// Crea una nueva Query con la consulta y un arbol con la Query
		Query newElement= new Query(q);
		TreeDynamic<Query> newChild = 
					new TreeDynamic<Query>(newElement);

		//Crea una cola de arboles, cada uno con un substring de la 
		// consulta dada
		QueueDynamic <TreeIF <Query>> qChildren = 
					new QueueDynamic <TreeIF <Query>> ();
		for (int i=1; i<=len; i++)
		{
			Query newQueryChild= new Query (q.substring(0,i) );
			// Pone todas las frecuencias de los substrings a 0
			if (i!=(len)) newQueryChild.setFreq(0);
			TreeDynamic<Query> newQueryChildTree = 
					new TreeDynamic<Query>(newQueryChild);
			qChildren.add(newQueryChildTree);
		}

		// Crea una lista con todos los arboles hijos de la raiz 
		// principal
		ListIF<TreeIF <Query>> cChildren = depot.getChildren();
		IteratorIF<TreeIF <Query>> listIt = cChildren.getIterator ();

		// El nodo actual y el nodo padre se fijan en la raiz principal
		TreeDynamic<Query> cNode = depot;
		TreeDynamic<Query> pNode = depot;

		// Recorre el arbol a traves de nodos que son prefijos de la
		// consulta dada, actualizando el nodo actual y el nodo padre
		int treeLevel=1;
		while ( (listIt.hasNext ()) && (treeLevel<=len) )
		{
			cNode = (TreeDynamic<Query>)listIt.getNext();
			Query element=cNode.getRoot();
			// Si la consulta esta almacenada se aumenta su 
			// frecuencia
			if (element.getText().equals(q))
			{
				if (element.getFreq()==0) N++;
				element.setFreq(element.getFreq()+1);
				return;
			}
			// Si el nodo es prefijo de la consulta, actualiza la
			// lista de hijos y el nodo padre
			if (q.startsWith(element.getText()) )
			{
				cChildren = cNode.getChildren();
				listIt = cChildren.getIterator();
				// Se elimina el elemento en cabeza de la cola
				// de substrings creada con la consulta dada
				qChildren.remove();
				treeLevel++;	
				pNode=cNode;
			}
		}
		
		// Se añaden los elementos restantes en la cola de substrings
		// al ultimo nodo padre encontrado
		N++;
		IteratorIF<TreeIF <Query>> listNew = qChildren.getIterator();
		while (listNew.hasNext ())
		{
			TreeDynamic<Query> qChild = 
					(TreeDynamic<Query>)listNew.getNext();
			pNode.addChild(qChild);
			pNode=qChild;
		}
		return;

	}
	/**
	* Devuelve la frecuencia de una consulta dada. Si la consulta no esta
	* almacenada devuelve 0.
	* @param q Un string con la consulta cuya frecuencia se quiere 
	* consultar
	*/
	@Override
	public int getFreqQuery (String q)
	{
		int len=q.length();
		// Obtiene todos los hijos del nodo raiz
		ListIF<TreeIF <Query>> cChildren = depot.getChildren();
		IteratorIF<TreeIF <Query>> listIt = cChildren.getIterator ();

		// Fija la raiz del arbol principal como nodo actual
		TreeDynamic<Query> cNode = depot;

		while ( listIt.hasNext ()  )
		{
			cNode = (TreeDynamic<Query>)listIt.getNext();
			Query element=cNode.getRoot();
			// Si existe la consulta en una Query almacenada se
			// devuelve su frecuencia
			if (element.getText().equals(q))
			{
				return element.getFreq();
			}
			// Si un nodo del arbol es prefijo de la consulta se
			// actualiza la lista de hijos a recorrer
			if (q.startsWith(element.getText()) )
			{
				cChildren = cNode.getChildren();
				listIt = cChildren.getIterator();
			}
		}
		return 0;
	}
	
	/**
	* Devuelve la lista de consultas almacenadas que se pueden formar
	* a partir de un prefijo dado.
	* @param q Un string prefijo
	*/
	@Override
	public ListIF<Query> listOfQueries (String prefix)
	{
		// La lista que se va a devolver
		ListIF<Query> qList = new ListDynamic<Query>();

		int len=prefix.length();
		// Obtiene todos los hijos del nodo raiz del arbol
		ListIF<TreeIF <Query>> cChildren = depot.getChildren();
		IteratorIF<TreeIF <Query>> listIt = cChildren.getIterator();

		// Fija la raiz del arbol principal como nodo actual
		TreeDynamic<Query> cNode = depot;

		// Recorre el arbol a traves de nodos que son prefijos del
		// prefijo dado como parametro hasta que encuentra el nodo
		// que contiene una Query con el parametro como texto
		while ( listIt.hasNext () )
		{
			cNode = (TreeDynamic<Query>)listIt.getNext();
			Query element=cNode.getRoot();
			if (prefix.startsWith(element.getText()) )
			{
				cChildren = cNode.getChildren();
				listIt = cChildren.getIterator();
			}
			if (element.getText().equals(prefix))
			{
				// Todos los elementos en el arbol que tiene el prefijo
				// como raiz son consultas que se pueden generar a partir
				// del prefijo. Por tanto se copian en la lista a devolver
				IteratorIF<Query> listSug = cNode.getIterator(0);
				while (listSug.hasNext ()) 
				{
					Query nElement = listSug.getNext();
					if (nElement.getFreq()>0) qList.insert(nElement);
				}

				// Se ordena la lista por orden decreciente de frecuencias
				// primero y despues por orden lexicografico creciente
				ComparatorIF<Query> compInvFreq = new ComparatorInvFreq();
				qList=(ListDynamic<Query>)qList.sort(compInvFreq);
				return qList;
			}
		}
		return qList;


	}
	



	/**
	* Reduce la frecuencia de una consulta almacenada, eliminando la 
	* consulta si su frecuencia es menor que 1.
	* @param q La consulta cuya frecuencia se quiere reducir
	*/
	@Override
	public void decFreqQuery (String q)
	{
		int len=q.length();
		// Obtiene todos los hijos del nodo raiz del arbol
		ListIF<TreeIF <Query>> cChildren = depot.getChildren();
		IteratorIF<TreeIF <Query>> listIt = cChildren.getIterator ();

		// Fija la raiz del arbol principal como nodo actual
		TreeDynamic<Query> cNode = depot;
		// Fija la raiz del arbol principal como nodo del que cuelga la
		// consulta dada
		TreeDynamic<Query> rNode = depot;
		int rIndex=0;

		// Recorre el arbol a traves de nodos que son prefijos de la 
		// consulta dada como parametro hasta que encuentra el nodo
		// que contiene una Query con el parametro como texto. 
		// Si es necesario eliminar la consulta, se elimina a partir de
		// la ultima consulta con mas de un hijo o con frecuencia>1, de
		// modo que se eliminan los nodos intermedios y no solo la
		// consulta dada
		while ( listIt.hasNext () )
		{
			// Actualiza la posicion de la consulta buscada entre
			// los hijos de la ultima consulta almacenada de la que 
			// cuelga
			if ( (cChildren.getLength()>1) || 
				(rNode.getRoot().getFreq()>0)) rIndex++;

			cNode = (TreeDynamic<Query>)listIt.getNext();
			Query element=cNode.getRoot();

			// Si se encuentra la consulta parametro se reduce
			// su frecuencia
			if (element.getText().equals(q))
			{
				element.setFreq(element.getFreq()-1);
				// Si la frecuencia llega a cero se elimina
				if ( (element.getFreq()<1) && (cNode.isLeaf()))
				{ 		
					rNode.removeChild(rIndex);
					N--;		
				}
				// Si no es un nodo hoja la frecuencia no se
				// reduce a menos de 0
				else if (element.getFreq()<1) element.setFreq(0);
			}
			else if (q.startsWith(element.getText()) )
			{
				cChildren = cNode.getChildren();
				listIt = cChildren.getIterator();
				// Si el nodo visitado tiene mas de un hijo o
				// su frecuencia es >0, se almacena como ultimo
				// nodo valido del que cuelga la consulta
				// parametro
				if ( (cChildren.getLength()>1) || (element.getFreq()>0) )
				{
					rNode=cNode;
					rIndex=-1;
				}
			}
		}
		return;
	}
}
