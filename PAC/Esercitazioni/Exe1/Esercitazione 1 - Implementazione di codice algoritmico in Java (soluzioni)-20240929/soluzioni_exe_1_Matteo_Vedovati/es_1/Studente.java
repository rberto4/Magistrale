package es_1;


public class Studente implements Comparable<Studente> {

	String nMatricola;
	String nome;
	String cognome;
	String dataNascita;
	
	public Studente(String nMatricola, String nome, String cognome, String dataNascita) {
		this.nMatricola = nMatricola;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
	}
	
	@Override
	public int compareTo(Studente s) {
		if (cognome.compareTo(s.cognome)!=0) return cognome.compareTo(s.cognome);
		if (nome.compareTo(s.nome)!=0) return nome.compareTo(s.nome);
		if (dataNascita.compareTo(s.dataNascita)!=0) return dataNascita.compareTo(s.dataNascita);
		return 0;
	}
	
	
	public boolean equals(Object o) {
		if (this == o) {return true;}
		if (!(o instanceof Studente)) {return false;}
		Studente s = (Studente) o;
		return nMatricola.equals(s.nMatricola);
	}
	
	@Override
    public String toString() {
        return "[" + nMatricola + ", " + nome + ", " + cognome + ", " + dataNascita + "]";
    }
}
