import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        Spettatore testvip = new Vip("Gerry", "Scotti");
        Spettatore testtifoso = new Tifoso("Roberto", "Bertocchi", 2, 67);
        Spettatore testgiornalista = new Giornalista("Marco", "Travaglio", "Il Fatto Quotidiano");

        // Controllo accesso VIP
        AccessoTribuna accessoVisitor = new AccessoTribuna();
        testvip.accept(accessoVisitor);
        System.out.println("Accesso VIP per VIP: " + accessoVisitor.isAccessoVIP());

        testtifoso.accept(accessoVisitor);
        System.out.println("Accesso VIP per Tifoso: " + accessoVisitor.isAccessoVIP());
        testgiornalista.accept(accessoVisitor);
        System.out.println("Accesso VIP per Giornalista: " + accessoVisitor.isAccessoVIP());

        // Stampa nome e cognome
        stampaCredenziali stampaVisitor = new stampaCredenziali();
        testvip.accept(stampaVisitor);
        testtifoso.accept(stampaVisitor);
        testgiornalista.accept(stampaVisitor);

        // Creazione della lista di tifosi
        List<Tifoso> tifosi = new ArrayList<>();
        tifosi.add(new Tifoso("Mario", "Rossi", 2, 10));
        tifosi.add(new Tifoso("Luca", "Bianchi", 1, 5));
        tifosi.add(new Tifoso("Anna", "Verdi", 2, 3));
        tifosi.add(new Tifoso("Giorgio", "Neri", 1, 15));

        System.out.println("Prima dell'ordinamento:");
        for (Tifoso tifoso : tifosi) {
            System.out.println(tifoso);
        }

        // Ordinamento dei tifosi
        Collections.sort(tifosi);

        System.out.println("\nDopo l'ordinamento:");
        for (Tifoso tifoso : tifosi) {
            System.out.println(tifoso);
        }
    }

    public static void stampaSpettatori(List<? extends Spettatore> spettatori) {
        for (Spettatore spettatore : spettatori) {
            System.out.println(spettatore.nome + " " + spettatore.cognome);
        }
    }

    /**
     * Posso creare una lista di oggetti di tipo tifoso e rimepirla con oggetti di tipo Spettatore?
     * non direttamente per la mancanza di covarianza tra collezioni generiche.
     * List<Tifoso> non è sottoclasse di List<Spettatore>
     *
     * Soluzione -> Uso dei wildcard (? extends Spettatore)
     *   public static void stampaSpettatori(List<? extends Spettatore> spettatori) {
     *         for (Spettatore spettatore : spettatori) {
     *             System.out.println(spettatore.nome + " " + spettatore.cognome);
     *         }
     *     }
     */


}

