class Set<T> {
    private T[] array;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public Set(T t[]) {
        // Inizializzo l'array con la stessa lunghezza dell'array di input
        this.array = (T[]) new Object[t.length];

        // Aggiungo solo elementi unici
        for (T e : t) {
            if (!contains(e)) {
                array[size++] = e;
            }
        }
    }

    // Metodo per controllare se l'elemento è già presente nell'array
    private boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    public void printSet() {
        for (int i = 0; i < size; i++) {
            System.out.print(array[i] + " ");
        }
    }
}
