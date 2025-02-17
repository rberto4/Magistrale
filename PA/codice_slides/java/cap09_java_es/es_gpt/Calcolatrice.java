package es_gpt;

/**
 * Esercizio 3.1: Overloading di Metodi
 * 🔹 Obiettivo: Implementare il metodo somma() con overloading.
 * 🔹 Compiti:
 * 
 * Crea una classe Calcolatrice.
 * Implementa tre versioni del metodo somma() che accetta:
 * Due numeri interi.
 * Due numeri double.
 * Tre numeri interi.
 * Nel main, testa le diverse versioni.
 */
public class Calcolatrice {
    public static int somma(int a, int b) {
        return a + b;
    }

    public static double somma(double a, double b) {
        return a + b;
    }

    public static int somma(int a, int b, int c) {
        return a + b + c;
    }
}
