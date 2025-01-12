public class OrderedSet<T>{
    private Object[] wArray;
    private int wSize;

    public OrderedSet(T[] array){
        this.wArray = new Object[0];
        this.wSize = 0;

        for (int i = 0; i < array.length; i++) {
            if (!contains(array[i])) {
                Object[] temp = new Object[wSize + 1];
                temp[temp.length - 1] = array[i];
                System.arraycopy(wArray, 0, temp, 0, wSize);
                wArray = temp;
                wSize += 1;
            } else {
                System.out.println("ATTENZIONE, "+ array[i] + " è  gia duplicato, non lo aggiungo ..");
            }
        }

    }

    public boolean contains(T item){
        for (int i = 0; i < wSize; i++){
            if (wArray[i].equals(item)){
                return true;
            }
        }
        return false;
    }

    public void orderObjects (){
        for (int i = 0; i < wSize - 1; i++) {
            for (int j = 0; j < wSize - i - 1; j++) {
                // Confronta gli elementi tramite Comparable
                Comparable<Object> current = (Comparable<Object>) wArray[j];
                Comparable<Object> next = (Comparable<Object>) wArray[j + 1];
                if (current.compareTo(next) > 0) {
                    // Scambia gli elementi
                    Object temp = wArray[j];
                    wArray[j] = wArray[j + 1];
                    wArray[j + 1] = temp;
                }
            }
        }
    }



    public void printSet (){
        for (int i = 0; i < wSize; i++){
            System.out.print(wArray[i] + " ");
        }
    }
}
