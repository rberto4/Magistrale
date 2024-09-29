package Es2;

import java.util.ArrayList;

import Es1.Student;

/**
 * This is an exercise of data structures for the research of duplicates with
 * different data structures using generics. In this exercise we use also the
 * Student class created in the package Es1
 * 
 * @author Marco Vinciguerra
 * @author Gabriele Marchesi
 * @year 2022-2023
 *
 */

public class main {

	public static void main(String[] args) {
		// First test
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(2);
		al.add(3);
		al.add(4);
		al.add(5);
		al.add(6);
		al.add(7);

		DuplicatedObject obj = new DuplicatedObject();

		boolean result = obj.verificaDupIterator(al);

		System.out.println("First test:  " + result);

		// Second Test
		ArrayList<Integer> al2 = new ArrayList<Integer>();
		al2.add(2);
		al2.add(3);
		al2.add(4);
		al2.add(4);
		al2.add(6);
		al2.add(7);

		boolean result2 = obj.verificaDupIterator(al2);

		System.out.println("Second test: " + result2);

		Student a = new Student("Marco", "Vinciguerra", "unibg", "01-06-2000", 1064889, false);
		Student b = new Student("Alessandro", "Belotti", "unibg", "01-06-2000", 1066721, false);
		Student c = new Student("Matteo", "Vedovati", "unibg", "01-06-2000", 1066721, false);

		ArrayList<Student> al3 = new ArrayList<Student>();
		al3.add(a);
		al3.add(b);
		al3.add(c);
		al3.add(c);

		boolean result3 = obj.verificaDupIterator(al3);

		System.out.println("Third test: " + result3);

	}

}
