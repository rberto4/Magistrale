public class stampaMenu implements Visitor{

    private Lingue wLingua;
    public stampaMenu(Lingue lingua) {
        this.wLingua = lingua;
    }

    @Override
    public void visit(Torta torta) {
        if (wLingua.equals(Lingue.ITALIANO)){
            System.out.println(" - Torta " );
            System.out.println(" - Tipologia : " + torta.getTipologia().name());
            System.out.println(" ---------------------------------------------- ");
        }

        if (wLingua.equals(Lingue.INGLESE)){
            System.out.println(" - Cake " );
            System.out.println(" - Typology : " + torta.getTipologia().name());
            System.out.println(" ---------------------------------------------- ");
        }
    }

    @Override
    public void visit(Cornetto cornetto) {
        if (wLingua.equals(Lingue.ITALIANO)){
            System.out.println(" - Cornetto " );
            System.out.println(" - Ripieno : " + cornetto.getRipieno());
            System.out.println(" ---------------------------------------------- ");
        }

        if (wLingua.equals(Lingue.INGLESE)){
            System.out.println(" - Croissant " );
            System.out.println(" - Filling : " + cornetto.getRipieno());
            System.out.println(" ---------------------------------------------- ");
        }
    }

    @Override
    public void visit(Pasticcino pasticcino) {
        if (wLingua.equals(Lingue.ITALIANO)){
            System.out.println(" - Pasticcino " );
            System.out.println(" - Ripieno : " + pasticcino.getRipieno());
            System.out.println(" ---------------------------------------------- ");
        }

        if (wLingua.equals(Lingue.INGLESE)){
            System.out.println(" - Pastry " );
            System.out.println(" - Filling : " + pasticcino.getRipieno());
            System.out.println(" ---------------------------------------------- ");
        }
    }
}
