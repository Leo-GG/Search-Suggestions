/**
* @file QueryDepotList.java
* @brief Contiene la Implementacion de la interfaz QueryDepot a traves de una 
* lista dinamica
*
* Este archivo contiene la implementacion de la interfaz QueryDepot hecha
* utilizando una lista dinamica. El archivo contiene la clase QueryDepotList
* junto con todos sus metodos
*/

public class QueryDepotList implements QueryDepot{

	private ListDynamic<Query> depot;
	// Numero de consultas almacenadas
	private int N;

	/**
	* Constructor
	* Crea una lista dinamica vacia
	*/
	public QueryDepotList ()
	{
		depot = new ListDynamic<Query>();
		N=0;
	}

	/**
	* Devuelve el numero de Queries distintas almacenadas hasta el 
	* momento
	* @return numero de Queries almacenadas
	*/
	@Override
	public int numQueries()
	{
	/*	int count=0;
		//return depot.getLength();
		IteratorIF<Query> listIt = depot.getIterator ();
		while (listIt.hasNext ()) 
		{
			Query element = listIt.getNext ();
			count++;
		}
		return count;*/
		return N;
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
		// Iterador sobre los elementos de la lista
		IteratorIF<Query> listIt = depot.getIterator ();
		while (listIt.hasNext ()) 
		{
			Query element = listIt.getNext ();
			// Si se encuentra q, devuelve su frecuencia
			if (element.getText().equals(q))
				return element.getFreq();
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
		// La lista a devolver
		ListIF<Query> qList = new ListDynamic<Query>();
		IteratorIF<Query> listIt = depot.getIterator ();

		int prefixL=prefix.length();
		// Recorre la lista (ordenada) almacenando las consultas que
		// empiezan por el prefijo hasta que se alcanza un elemento 
		// con prefijo de orden lexicografico mayor que el prefijo
		// dado
		while (listIt.hasNext ()) 
		{
			Query element = listIt.getNext ();
			if (element.getText().length()>=prefixL)
			{
				if (element.getText().substring(0,prefixL).
					compareTo(prefix)>0)
				{
					break;
				}
				if (element.getText().startsWith(prefix))
				//if (prefix.regionMatches(0, element.
				//	getText(),0,prefixL))
				{
					qList.insert(element);
				}
			}
		}
		// Se ordena la lista por orden decreciente de frecuencia y se 
		// devuelve
		ComparatorInvFreq compInvFreq = new ComparatorInvFreq();
		qList=(ListDynamic<Query>)qList.sort(compInvFreq);
		return qList;
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
		IteratorIF<Query> listIt = depot.getIterator ();

		// Recorre la lista (ordenada) hasta que encuentra el elemento
		// buscado o hasta que alcanza un elemento alfabeticamente 
		// mayor. Si ocurre esto ultimo, se incluye la consulta como
		// elemento nuevo en la lista y esta se reordena
		while (listIt.hasNext ()) 
		{
			Query element = listIt.getNext ();
			// Si la consulta esta en la lista, incrementa la 
			// frecuencia y reordena la lista
			if (element.getText().equals(q))
			{
				element.setFreq(element.getFreq()+1);
				ComparatorLex compLex = new ComparatorLex();
				depot=(ListDynamic<Query>)depot.sort(compLex);
				return;
			}
			// Si se pasa el orden alfabetico, el elemento no
			// esta en la lista
			if(element.getText().compareTo(q)>0){
				break;
			}
		}
		// Crea una Query con la consulta dada y la inserta
		Query newElement= new Query (q);
		depot.insert(newElement);
		N++;
		// Reordena la lista
		ComparatorLex compLex = new ComparatorLex();
		depot=(ListDynamic<Query>)depot.sort(compLex);

	}

	/**
	* Reduce la frecuencia de una consulta almacenada, eliminando la 
	* consulta si su frecuencia es menor que 1.
	* @param q La consulta cuya frecuencia se quiere reducir
	*/
	@Override
	public void decFreqQuery (String q){
		// Iterador sobre la lista completa
		IteratorIF<Query> listIt = depot.getIterator ();
		// Lista temporal
		ListIF<Query> tempList = new ListDynamic<Query>();
		N=0;
		// Recorre la lista hasta encontrar la consulta
		while (listIt.hasNext ()) 
		{
			Query element = listIt.getNext ();
			// Si se encuentra la consulta se reduce su frecuencia
			if (element.getText().equals(q))
			{
				element.setFreq(element.getFreq()-1);
			}
			// Todos los elementos con frecuencia >1 se copian en 
			// una lista nueva
			if (element.getFreq()>0)
			{
				tempList.insert(element);
				N++;
			}
		}
		// Reordena la nueva lista y actualiza la lista principal
		ComparatorLex compLex = new ComparatorLex();
		depot=(ListDynamic<Query>)tempList.sort(compLex);
	}

}
