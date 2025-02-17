package esercizi_gpt;

/**
 * Esercizio 2 - Coppia di valori generici
 * Crea una classe generica Coppia<K, V> che rappresenta due valori di tipi
 * diversi.
 * 🔹 Obiettivo: Usare più parametri generici in una classe.
 * 
 * Suggerimento: Implementa i metodi getSinistro() e getDestro().
 */

public class Coppia<K, V>{
    private K sinistro;
    private V destro;

    public Coppia(K sinistro, V destro) {
        this.sinistro = sinistro;
        this.destro = destro;
    }

    public K getSinistro() {
        return sinistro;
    }
    public V getDestro() {
        return destro;
    }

    public void setSinistro(K sinistro) {
        this.sinistro = sinistro;
    }   

    public void setDestro(V destro) {
        this.destro = destro;
    }

    public static void main(String[] args) {
        Coppia<Integer, String> coppia = new Coppia<>(10, "Ciao");
        System.out.println("Sinistro: " + coppia.getSinistro()); // Output: 10
        System.out.println("Destro: " + coppia.getDestro()); // Output: Ciao
    }
}