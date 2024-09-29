package Es1;

public class Person implements Comparable<Person> {
	String title;
	String fullName;
	int age;

	public Person(String title, String fullName, int age) {
		this.title = title;
		this.fullName = fullName;
		this.age = age;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Person))
			return false;
		Person temp = (Person) obj;
		return title.equals(temp.title) && title.equals(temp.title) && age == temp.age;
	}

	@Override
	public int compareTo(Person o) {
		if (this.fullName.compareTo(o.fullName) < 0)
			return -1;
		if (this.fullName.compareTo(o.fullName) > 0)
			return +1;

		return age - o.age;
	}
}
