import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Treno> corse = new ArrayList<>();


       /**
        Interregionali treno_interregionale_1 = new Interregionali("TRIR123",
                new Fermata("Milano", 120, 0),
                new Fermata("Napoli", 0, 120),
                fermateIntermedie
        );

        Locale treno_locale_1 = new Locale("TRL456",
                new Fermata("Milano", 120, 0),
                new Fermata("Bergamo", 0, 120),
                new ArrayList<Fermata>()
        );

        AltaVelocita treno_altavelocita_1 = new AltaVelocita(
                "TRAV789",
                new Fermata("Ancona", 30, 0),
                new Fermata("Pescara", 0, 30)
        );

        corse.add(treno_interregionale_1);
        corse.add(treno_locale_1);
        corse.add(treno_altavelocita_1);
        stampaFermate stampaFermate = new stampaFermate();

        */
        for (int i = 0; i < corse.size(); i++) {
            //corse.get(i).accept(stampaFermate);
            System.out.println();
        }
    }
}