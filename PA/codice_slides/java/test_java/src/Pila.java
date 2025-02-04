import java.util.ArrayList;

class Stack<T> {
    private ArrayList<T> elements;

    public Stack() {
        elements = new ArrayList<>();
    }

    // Metodo per aggiungere un elemento nello stack
    public void push(T item) {
        elements.add(item);
    }

    // Metodo per rimuovere e restituire l'ultimo elemento inserito (LIFO)
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack vuoto!");
        }
        return elements.remove(elements.size() - 1);
    }

    // Metodo per vedere l'ultimo elemento senza rimuoverlo
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack vuoto!");
        }
        return elements.get(elements.size() - 1);
    }

    // Metodo per verificare se lo stack è vuoto
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    // Metodo per ottenere la dimensione dello stack
    public int size() {
        return elements.size();
    }

    // Metodo per stampare lo stack
    @Override
    public String toString() {
        return elements.toString();
    }
}
