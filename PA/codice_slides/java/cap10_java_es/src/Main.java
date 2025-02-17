import java.util.List;

public class Main implements Comparable<Main>{
    
    private int value;

    public Main (int v){
        this.value = v;
    }
    public static <T> void stampaElemento(T elemento) {
        System.out.println(elemento);
    }

    public static <U, K> void stampaCoppia(U u, K k) {
        System.out.println(u + " " + k);
    }

    public <T extends Number> double sommaValori (List<T> lista){
        double somma = 0;
        for (T elem : lista) {
            somma += somma + elem.doubleValue();
        }
        return somma;
    }

    public static void main(String[] args) {
        stampaElemento(10); // Output: 10
        stampaElemento("Ciao"); // Output: Ciao
        stampaElemento(10.5); // Output: 10.5
        stampaCoppia(10.9, "ciao");
        Main m1 = new Main(10);
        Main m2 = new Main(20);
        System.out.println(m2.compareTo(m1)); // Output: -1
    }

    @Override
    public int compareTo(Main o) {
       return Integer.compare(this.value, o.value);
    }
}
