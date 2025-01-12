public class stampaCalorie implements Visitor{

    private Lingue wLingua;
    public stampaCalorie(Lingue lingua){
        this.wLingua = lingua;
    }

    @Override
    public void visit(Torta torta) {
        if (wLingua.equals(Lingue.ITALIANO)){
            System.out.println("Calorie torta : " + torta.getKcal());
        }else{
            System.out.println("Cake calories: " + torta.getKcal());
        }
    }

    @Override
    public void visit(Cornetto cornetto) {
        if (wLingua.equals(Lingue.ITALIANO)){
            System.out.println("Calorie cornetto : " + cornetto.getKcal());
        }else{
            System.out.println("Croissant calories: " + cornetto.getKcal());
        }    }

    @Override
    public void visit(Pasticcino pasticcino) {
        if (wLingua.equals(Lingue.ITALIANO)){
            System.out.println("Calorie torta : " + pasticcino.getKcal());
        }else{
            System.out.println("Pastry calories: " + pasticcino.getKcal());
        }    }
}
