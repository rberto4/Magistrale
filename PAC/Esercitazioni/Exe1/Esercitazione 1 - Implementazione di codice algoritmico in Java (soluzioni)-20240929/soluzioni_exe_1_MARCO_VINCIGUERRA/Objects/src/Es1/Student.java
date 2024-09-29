package Es1;

//Only student's status information
public class Student implements Comparable<Student> {

	String name;
	String surname;
	String university;
	String birthday;
	int code;
	boolean Exchange;

	public Student(String name, String surname, String university, String birthday, int code, boolean Exchange) {
		this.name = name;
		this.surname = surname;
		this.university = university;
		this.birthday = birthday;
		this.code = code;
		this.Exchange = Exchange;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Student))
			return false;
		Student temp = (Student) o;
		return name.equals(temp.name) && surname.equals(temp.surname) && university.equals(temp.university)
				&& birthday.equals(temp.birthday) && code == temp.code && Exchange == temp.Exchange;
	}

	@Override
	public int compareTo(Student o) {
		if (this.surname.compareTo(o.surname) > 0)
			return +1;
		else if (this.surname.compareTo(o.surname) < 0)
			return -1;
		else if (this.surname.compareTo(o.surname) == 0 && this.name.compareTo(o.name) == 0
				&& this.birthday.compareTo(o.birthday) == 0)
			return 0;
		else
			return 1;
	}

	public String toString() {
		return "nome: " + this.name + ", cognome: " + this.surname + ", università: " + this.university + ", birthday: "
				+ this.birthday + ", exchange? " + this.Exchange;

	}

}
