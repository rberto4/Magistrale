package es_gpt;

/**
 * Esercizio 1.1: Creazione di una Classe e Oggetti
 * 🔹 Obiettivo: Creare una classe Persona con attributi e metodi di base.
 * 🔹 Compiti:
 * 
 * Definisci la classe Persona con i seguenti attributi:
 * nome (String)
 * età (int)
 * Implementa un costruttore per inizializzare questi valori.
 * Scrivi un metodo stampaInfo() che stampa le informazioni della persona.
 * Nel main, crea due oggetti e chiama il metodo stampaInfo().
 * 
 */

class Persona {
    String nome;
    int eta;

    // Costruttore
    Persona(String nome, int eta) {
        this.nome = nome;
        this.eta = eta;
    }

    void stampaInfo() {
        System.out.println("Nome: " + nome + ", Età: " + eta);
    }
}
