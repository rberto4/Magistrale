package es_1;

import java.util.Iterator;

public class VerificaDup {
	public static void main(String args[]) {
		Pila<Studente> S = new Pila<Studente>();
		S.push(new Studente("1064586", "Matteo", "Vedovati", "22/08/2000"));
		S.push(new Studente("1064573", "Mario", "Rossi", "24/09/1999"));
		S.push(new Studente("1023423", "Luigi", "Verdi", "11/02/2001"));
		S.push(new Studente("1032486", "Salvatore", "Ciruzzo", "22/12/2000"));
		S.push(new Studente("1063242", "Matteo", "Rossi", "11/02/2001"));
		S.push(new Studente("1064586", "Matteo", "Vedovati", "22/08/2000"));
		
		System.out.println("La pila di studenti " + (verificaDup(S) ? "ha duplicati" : "non ha duplicati"));
	}
	
	static boolean verificaDup(Pila<Studente> S) {
		int count = 1;
		for (Iterator<Studente> i = S.iterator(); i.hasNext(); count++) {
			Studente s1 = i.next();
			if (count < S.n) {
				for (Iterator<Studente> j = S.iterator(count); j.hasNext(); ) {
					Studente s2 = j.next();
					if (s1.equals(s2)) {
						return true;
					}
				}
			}
			
		}
		return false;
	}
}
