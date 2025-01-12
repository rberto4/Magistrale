public class stampaCalorie implements Visitor{

    @Override
    public void visit(Carota carota) {
        System.out.println("kcalorie delle carote : " + carota.getKcal());
    }

    @Override
    public void visit(Agnello agnello) {
        System.out.println("kcalorie dell' agnello : " + agnello.getKcal());

    }
}
