package es_gpt;

/**
 * Esercizio 5.1: Creare un'eccezione personalizzata
 * 🔹 Obiettivo: Creare una classe SaldoInsufficienteException ed usarla in un
 * metodo di prelievo.
 * 🔹 Compiti:
 * 
 * Crea un'eccezione personalizzata SaldoInsufficienteException.
 * Definisci una classe ContoBancario con un attributo saldo.
 * Implementa un metodo preleva(double importo) che lancia l'eccezione se il
 * saldo è insufficiente.
 * Nel main, gestisci l'eccezione con try-catch.
 */

 public class SaldoInsufficienteException extends Exception {
     public SaldoInsufficienteException(String message) {
         super(message);
     }
 }