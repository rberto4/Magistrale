package esercizi_gpt;
import java.util.ArrayList;
import java.util.List;

public class Wildcard {

     public static void stampaLista (List<?> lista){

        for (Object elem : lista) {
            System.out.println(elem);
        }
    }

    public static Number sommaLista (List<? extends Number> lista){
        double somma = 0;
        for (Number num : lista) {
            somma = somma + num.doubleValue();
        }
        return somma;
    }

    public static void aggiungiNumero(int n, List<? super Integer> lista){
        lista.add(n);
    }

    public static void main(String[] args) {
       List<Object> listaNumeri = List.of(10, 20.3, 30, "ciao", true);
       stampaLista(listaNumeri);
         List<Integer> listaInt = new ArrayList<>();
         aggiungiNumero(999, listaInt);
         stampaLista(listaInt);
    }
}
