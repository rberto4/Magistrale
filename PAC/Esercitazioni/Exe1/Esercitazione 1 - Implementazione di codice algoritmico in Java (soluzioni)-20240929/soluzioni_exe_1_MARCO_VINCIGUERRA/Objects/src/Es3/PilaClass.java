package Es3;

import java.util.Vector;

public class PilaClass extends Vector implements PilaInterface {

	@Override
	public <T> void push(T elem) {
		super.addElement(elem);

	}

	@Override
	public <T> T pop() {
		T elem = (T) super.lastElement();
		super.remove(elem);
		return elem;
	}

	@Override
	public <T> T top() {
		return (T) super.lastElement();
	}

}
