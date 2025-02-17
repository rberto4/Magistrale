package es_gpt;

/**
 * Esercizio 2.1: Estendere una Classe
 * 🔹 Obiettivo: Creare una sottoclasse Studente che eredita da Persona.
 * 🔹 Compiti:
 * 
 * Aggiungi alla classe Studente un nuovo attributo matricola (String).
 * Crea un costruttore che richiama quello della superclasse Persona e
 * inizializza matricola.
 * Sovrascrivi il metodo stampaInfo() per includere la matricola.
 * Nel main, crea un oggetto Studente e stampane le informazioni.
 */
public class Studente extends Persona {
    private String wMatricola;
    public Studente(String nome, int eta, String matricola) {
        super(nome, eta);
        this.wMatricola = matricola;
    }

    @Override
    public void stampaInfo() {
        System.out.println("Nome: " + nome + ", Età: " + eta + ", Matricola: " + wMatricola);
    }

}
