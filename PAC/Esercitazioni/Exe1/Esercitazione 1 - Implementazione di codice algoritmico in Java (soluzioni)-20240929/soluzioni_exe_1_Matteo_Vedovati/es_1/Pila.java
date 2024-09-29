package es_1;
import java.util.*;

public class Pila<E> implements Iterable<E> {
	ArrayList<E> S;
	int n;
	public Pila() {
		this.S = new ArrayList<E>();
		n = 0;
	}
	public Pila(ArrayList<E> S) {
		this.S = S;
		n = S.size();
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public void push(E elem) {
		S.add(n, elem);
		n++;
	} 
	
	public E pop() {
		if (n >= 1) {
			n--;
			return S.remove(n);
		}
		return null;
		
	}
	
	public E top() {
		return S.get(n-1);
	}
	
	@Override
	public Iterator<E> iterator() {
		Iterator<E> it = new Iterator<E>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < n - 1;
            }

            @Override
            public E next() {
                return S.get(currentIndex++);
            }
        };
		return it;
	}
	
	public Iterator<E> iterator(int x) {
		if (x < 0 || n < x) {
			throw new IndexOutOfBoundsException();
		}
		
		Iterator<E> it = new Iterator<E>() {

            private int currentIndex = x;

            @Override
            public boolean hasNext() {
                return currentIndex < n;
            }

            @Override
            public E next() {
                return S.get(currentIndex++);
            }
        };

		return it;
	}
	
	
	

}
