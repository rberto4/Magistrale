package Es2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public interface duplicati {
	// Using arrays
	<T> boolean verificaDupForLoop(T[] array);

	// Using arraylist
	<T> boolean verificaDupForLoopArrayList(ArrayList<T> arrayList);

	//Using linkedList
	<T> boolean verificaDupForLoopLinkedList(LinkedList<T> linkedList);

	//Using Iterator
	<T> boolean verificaDupIterator(ArrayList<T> arrayList);

	//Using List Iterator
	<T> boolean verificaDupListIterator(ArrayList<T> arrayList);
}
