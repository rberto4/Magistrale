import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ArrayList<Dolce> menu = new ArrayList<>();
        Torta t1 = new Torta(2500);
        Torta t2 = new Torta(3000);

        t1.configuraTipologia(TipologiaTorta.MILLEFOGLIE);
        t2.configuraTipologia(TipologiaTorta.STRATI);

        Cornetto c1 = new Cornetto(350, "Crema");
        Pasticcino p1 = new Pasticcino(90,"Cioccolato");

        p1.setRipieno("Cioccolato Fondente");

        menu.add(t1);
        menu.add(t2);
        menu.add(c1);
        menu.add(p1);

        stampaMenu sm_ita = new stampaMenu(Lingue.ITALIANO);
        stampaMenu sm_eng = new stampaMenu(Lingue.INGLESE);
        System.out.println(" --- MENU ITA ---");

        for (int i = 0; i<menu.size(); i++) {
            menu.get(i).accept(sm_ita);
        }
        System.out.println(" --- MENU ENG ---");

        for (int i = 0; i<menu.size(); i++) {
            menu.get(i).accept(sm_eng);
        }

        Collections.sort(menu);
        System.out.println(" --- MENU ITA (ordinato)---");

        for (int i = 0; i<menu.size(); i++) {
            menu.get(i).accept(sm_ita);
        }

        stampaCalorie sc_ita = new stampaCalorie(Lingue.ITALIANO);
        stampaCalorie sc_eng = new stampaCalorie(Lingue.INGLESE);
        System.out.println(" --- CALORIE IT ---");

        for (int i = 0; i<menu.size(); i++) {
            menu.get(i).accept(sc_ita);
        }

        System.out.println(" --- CALORIES ENG ---");

        for (int i = 0; i<menu.size(); i++) {
            menu.get(i).accept(sc_eng);
        }
        System.out.println(" --- mescololo ---");

        Collections.shuffle(menu);
        for (int i = 0; i<menu.size(); i++) {
            menu.get(i).accept(sc_ita);
        }
        System.out.println(" --- ordino ---");

        Collections.sort(menu);
        for (int i = 0; i<menu.size(); i++) {
            menu.get(i).accept(sc_ita);
        }

    }


}
