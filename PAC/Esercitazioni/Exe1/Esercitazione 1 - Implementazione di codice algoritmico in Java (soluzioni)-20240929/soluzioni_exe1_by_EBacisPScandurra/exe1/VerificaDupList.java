import java.util.List;
import java.util.ListIterator;

/**
 * Verifica Duplicati.
 * @author Enrico Bacis and Patrizia Scandurra
 */
public class VerificaDupList implements DuplicateAlgorithm{
		
	public <T> boolean verificaDup(List<T> collection){
				 
			//Eseguiamo la scansione attraverso due listIterator
			//per confrontare gli elementi a due a due 
			
			ListIterator<T> iter1 = collection.listIterator();
			ListIterator<T> iter2;
			T t1;

			while (iter1.hasNext()){
				t1 = iter1.next();
				iter2 = collection.listIterator(iter1.nextIndex());
				while (iter2.hasNext()){
					if (t1.equals(iter2.next()))
						return true;
				}
			}
			return false;
		}
		
	}

