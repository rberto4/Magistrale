package Es3;

import java.util.Vector;

public class PilaMethods implements PilaInterface {
	Vector list;

	public PilaMethods(Vector list) {
		this.list = list;
	}

	@Override
	public <T> boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public <T> void push(T elem) {
		this.list.addElement(elem);
	}

	@Override
	public <T> T pop() {
		T elem = (T) this.list.lastElement();
		this.list.remove(elem);
		return elem;
	}

	@Override
	public <T> T top() {
		return (T) this.list.lastElement();
	}

	public String toString() {
		return this.list.toString();

	}
}
