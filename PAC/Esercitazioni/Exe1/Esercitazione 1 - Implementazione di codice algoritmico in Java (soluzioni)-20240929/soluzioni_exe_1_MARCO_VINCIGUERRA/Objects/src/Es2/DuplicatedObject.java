package Es2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


public class DuplicatedObject implements duplicati {

	@Override
	public <T> boolean verificaDupForLoop(T[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i].equals(array[j]))
					return true;
			}
		}
		return false;
	}

	@Override
	public <T> boolean verificaDupForLoopArrayList(ArrayList<T> arrayList) {
		for (int i = 0; i < arrayList.size(); i++) {
			for (int j = i + 1; j < arrayList.size(); j++) {
				if (arrayList.get(i).equals(arrayList.get(j)))
					return true;
			}
		}
		return false;
	}

	@Override
	public <T> boolean verificaDupForLoopLinkedList(LinkedList<T> linkedList) {
		for (int i = 0; i < linkedList.size(); i++) {
			for (int j = i + 1; j < linkedList.size(); j++) {
				if (linkedList.get(i).equals(linkedList.get(j)))
					return true;
			}
		}
		return false;
	}

	@Override
	public <T> boolean verificaDupIterator(ArrayList<T> arrayList) {
		Iterator<T> it = arrayList.iterator();
		while (it.hasNext()) {
			T i = it.next();
			// Remove from the list the element that the iterator points to
			it.remove();
			// Starts from the next element
			Iterator<T> at = arrayList.iterator();
			while (at.hasNext()) {
				T j = at.next();
				if (i.equals(j)) {
					return true;
				}
			}

		}
		return false;
	}

	@Override
	public <T> boolean verificaDupListIterator(ArrayList<T> arrayList) {
		// Iterator is used for iterating the list,
		Iterator<T> it = arrayList.listIterator();
		while (it.hasNext()) {
			T i = it.next();
			// Remove from the list, not from the iterator
			it.remove();
			Iterator<T> at = arrayList.iterator();
			while (at.hasNext()) {
				T j = at.next();
				if (i.equals(j)) {
					return true;
				}
			}

		}
		return false;
	}

}
