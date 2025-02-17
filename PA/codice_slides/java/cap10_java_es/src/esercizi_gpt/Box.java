package esercizi_gpt;

/**
 * Esercizio 1 - Creare una classe generica semplice
 * Crea una classe generica Box<T> con i metodi set() e get(). Testala con
 * diversi tipi di dati.
 * 🔹 Obiettivo: Comprendere l'uso base dei generics.
 * 
 * Suggerimento: Usa un attributo privato T valore e metodi getter/setter.
 */
public class Box<T> {
    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public static void main(String[] args) {
        Box<Integer> boxInt = new Box<>();
        boxInt.set(10);
        System.out.println("Valore di boxInt: " + boxInt.get()); // Output: 10

        Box<String> boxString = new Box<>();
        boxString.set("Ciao");
        System.out.println("Valore di boxString: " + boxString.get()); // Output: Ciao
    }

}
