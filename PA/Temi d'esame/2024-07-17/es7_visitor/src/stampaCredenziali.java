public class stampaCredenziali implements visitor{

    @Override
    public void visit(Tifoso tifoso) {
       System.out.println("Tifoso");
       System.out.println(" - "+tifoso.getCredenziali());
    }

    @Override
    public void visit(Vip vip) {
        System.out.println("Vip");
       System.out.println(" - "+vip.getCredenziali());
    }

    @Override
    public void visit(Giornalista giornalista) {
        System.out.println("Giornalista");
        System.out.println(" - "+giornalista.getCredenziali());
    }
    
}
