import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ArrayList<Pietanza> menu = new ArrayList();

        Carota carota1 = new Carota(40);
        carota1.setPrezzemolo(true);
        menu.add(carota1);

        Carota carota2 = new Carota(35);
        carota2.setPrezzemolo(false);
        menu.add(carota2);


        Agnello agnello = new Agnello(250, Cotture.MEDIA);
        agnello.configuraCottura(Cotture.BENCOTTO);
        menu.add(agnello);

        stampaMenu stampaItaliano = new stampaMenu(Lingue.ITALIANO);
        stampaMenu stampaInglese = new stampaMenu(Lingue.INGLESE);


        System.out.println(" --------------- Menu in Italiano --------------- ");
        for (int i = 0; i < menu.size(); i++) {
            menu.get(i).accept(stampaItaliano);
        }

        System.out.println();

        System.out.println(" --------------- Menu in Inglese --------------- ");
        for (int i = 0; i < menu.size(); i++) {
            menu.get(i).accept(stampaInglese);
        }
        Collections.sort(menu);
        System.out.println("Ho ordinato in base alle kCals, ristampo il menu nelle varie lingue");
        System.out.println(" --------------- Menu in Italiano --------------- ");
        for (int i = 0; i < menu.size(); i++) {
            menu.get(i).accept(stampaItaliano);
        }

        System.out.println();

        System.out.println(" --------------- Menu in Inglese --------------- ");
        for (int i = 0; i < menu.size(); i++) {
            menu.get(i).accept(stampaInglese);
        }

    }
}