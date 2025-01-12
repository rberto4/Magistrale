public class stampaFermate implements Visitor{

    @Override
    public void visit(Locale locale) {
        System.out.println(" ---- Treno Locale -----");
        System.out.println(" - Nome : " + locale.getNome());
       // System.out.println(" - In partenza da : "+"L-"+locale.wPartenza.getNomeStazione());
        if (!locale.getFermateIntermedie().isEmpty())
            System.out.println(" -- Fermate intermedie");

        //System.out.println(" - Destinazione : "+"L-"+locale.wDestinazione.getNomeStazione());
    }

    @Override
    public void visit(Interregionali interregionale) {
        System.out.println(" ---- Treno interregionale -----");
        System.out.println(" - Nome : " + interregionale.getNome());
      //  System.out.println(" - In partenza da : "+"I-"+interregionale.wPartenza.getNomeStazione());
        if (!interregionale.getFermateIntermedie().isEmpty())
            System.out.println(" -- Fermate intermedie");

       // System.out.println(" - Destinazione : "+"I-"+interregionale.wDestinazione.getNomeStazione());
    }

    @Override
    public void visit(AltaVelocita altaVelocita) {
        System.out.println(" ---- Treno alta velocita -----");
        System.out.println(" - Nome : " + altaVelocita.getNome());
       // System.out.println(" - In partenza da : "+"AV-"+altaVelocita.wPartenza.getNomeStazione());
        // System.out.println(" - Destinazione : "+"AV-"+altaVelocita.wDestinazione.getNomeStazione());
    }
}
