package Es3;

import java.util.Vector;

/**
 * 
 * @author marco vinciguerra
 * @author Gabriele Marchesi This exercise create 2 different examples of stack
 *         classes
 * 
 * @year 2022-2023
 */
public class main {

	public static void main(String[] args) {

		// Test the class that contains a vector
		Vector a = new Vector();
		PilaMethods pila = new PilaMethods(a);
		pila.push("Marco");
		pila.push("Franco");
		pila.push("Peppino");
		pila.push("Anna");
		System.out.println("Without removing" + pila.toString());

		pila.pop();
		pila.pop();
		System.out.println("With removing" + pila.toString());

		System.out.println("top: " + pila.top().toString());

		// Test the class which extends vector
		PilaClass pila2 = new PilaClass();
		pila2.push("Marco");
		pila2.push("Franco");
		pila2.push("Peppino");
		pila2.push("Anna");
		System.out.println(pila2.toString());
	}

}
