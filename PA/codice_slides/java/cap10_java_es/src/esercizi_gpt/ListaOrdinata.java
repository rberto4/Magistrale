package esercizi_gpt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaOrdinata<T extends Comparable<T>> {
    private ArrayList<T> lista;

    public ListaOrdinata() {
        lista = new ArrayList<>();
    }

    public void aggiungiOrdinato(T t) {
        int index = Collections.binarySearch(lista, t); // Trova la posizione di inserimento
        if (index < 0) index = -index - 1; // Se non trovato, calcola l'indice corretto
        lista.add(index, t);
    }

    public void stampaLista() {
        for (T elem : lista) {
            System.out.println("- " + elem);
        }
    }

    public static void main(String[] args) {
        ListaOrdinata<Integer> listaInt = new ListaOrdinata<>();
        listaInt.aggiungiOrdinato(10);
        listaInt.aggiungiOrdinato(5);
        listaInt.aggiungiOrdinato(20);
        listaInt.aggiungiOrdinato(15);
        listaInt.aggiungiOrdinato(15);
        
        System.out.println("Lista Ordinata:");
        listaInt.stampaLista();

        ListaOrdinata<String> listaStr = new ListaOrdinata<>();
        listaStr.aggiungiOrdinato("Ciao");
        listaStr.aggiungiOrdinato("Mondo");
        listaStr.aggiungiOrdinato("Hello");
        listaStr.aggiungiOrdinato("World");
        listaStr.aggiungiOrdinato("Aaaaa");
        System.out.println("Lista Ordinata:");
        listaStr.stampaLista();
    }
}
