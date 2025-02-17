import java.util.List;

public class WildcardExample {
    // Metodo che accetta qualsiasi tipo di lista
    public static void stampaLista(List<?> lista) {
        for (Object elem : lista) {
            System.out.println(elem);
        }
    }

    public static void stampaListaStatica (List<? extends Number> lista){
        for (Number elem : lista) {
            System.out.println(elem);
        }
    }

    public static void main(String[] args) {
        List<Integer> numeri = List.of(1, 2, 3, 4);
        List<String> parole = List.of("Ciao", "Mondo");
        List<? extends Number> numeri_statica = List.of(1,2,3);
       // numeri_statica.add(80); -> Errore di compilazione

        stampaLista(numeri);  // Output: 1 2 3 4
        stampaLista(parole);  // Output: Ciao Mondo
        stampaListaStatica(numeri_statica); // Output: 1 2 3
    }
}
