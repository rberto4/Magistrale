import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


class Animale {
    void verso (){
        System.out.println("Animale verso");
    }
}

class Cane extends Animale {

    @Override
    void verso(){
        System.out.println("Bau Bau");
    }
}

// metodo covariante per il tipo di parametri.
 class metodiCovarianti {
    public static void versoCovariante(List<? super Cane> listaAnimale){
       listaAnimale.add(new Cane());
    }
}

// Java tratta i generics in modo invariante per garantire la sicurezza del tipo.
// infatti non è possibile :

/**
 * List<Animale> listAnimale = new ArrayList();
 * List<Cane> listCani = new ArrayList();
 * listAnimale = listCani;
 */


public class Main {
    public static void main(String[] args) {

        Animale a = new Animale();
        Animale b = new Cane();

        List<Animale> listaAnimale = new ArrayList<Animale>();
        listaAnimale.add(a);
        listaAnimale.add(b);
        listaAnimale.add(b);

        metodiCovarianti.versoCovariante(listaAnimale);

        Integer[] num_array = {1,2,2,3,4};
        Set set = new Set(num_array);
        set.printSet();

    }
}