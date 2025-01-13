import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws Exception {
       ArrayList<Spettatore> spettatori = new ArrayList<>();

       Tifoso t1 = new Tifoso("mario", "rossi", 17, 6);
       Tifoso t2 = new Tifoso("giuseppe", "verdi", 16, 6);
       Tifoso t3 = new Tifoso("giovanni", "storti", 99, 5);

       Spettatore v1 = new Vip("Claudio", "Menghi");
       Spettatore g1 = new Giornalista("Stefano", "tigli", "Gazzetta dello sport");

       spettatori.add(t1);
       spettatori.add(t2);
       spettatori.add(v1);
       spettatori.add(g1);

       stampaCredenziali sc = new stampaCredenziali();
       AccessoTribunaVip atv = new AccessoTribunaVip();
       for (Spettatore s : spettatori){
        s.accept(sc);
        s.accept(atv);
       }
       System.out.println( " --------------------------------------------------------- ");
       ArrayList<Tifoso> tifosiStandard = new ArrayList<>();
       tifosiStandard.add(t1);
       tifosiStandard.add(t2);
       tifosiStandard.add(t3);
       Collections.sort(tifosiStandard);

       for (Tifoso s : tifosiStandard){
        s.accept(sc);
       }

    }
}
