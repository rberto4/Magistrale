public class stampaMenu implements Visitor{

    private Lingue wLingua;

    public stampaMenu(Lingue lingua) {
        this.wLingua = lingua;
    }


    @Override
    public void visit(Carota carota) {
        if (wLingua.equals(Lingue.ITALIANO)) {
            if (carota.isConPrezzemolo()) {
                System.out.println("Carote con prezzemolo - " + carota.getKcal() + " calorie");
            }else{
                System.out.println("Carote - " + carota.getKcal() + " calorie");
            }
        }
        if (wLingua.equals(Lingue.INGLESE)) {
            //parsley
            if (carota.isConPrezzemolo()) {
                System.out.println("Carots with parsley - " + carota.getKcal() + " kcals");
            }else{
                System.out.println("Carots - " + carota.getKcal() + " kcals");
            }
        }

    }

    @Override
    public void visit(Agnello agnello) {
        if (wLingua.equals(Lingue.ITALIANO)) {
            System.out.println("Agnello - " + agnello.getKcal() + " calorie");
        }
        if (wLingua.equals(Lingue.INGLESE)) {
            System.out.println("Lamb - " + agnello.getKcal() + " kcals");
        }
    }
}
