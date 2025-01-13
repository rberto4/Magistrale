public class AccessoTribunaVip implements visitor {

    @Override
    public void visit(Tifoso tifoso) {
       System.out.println("Tifoso standard, NON puo' accedere alla tribuna vip");
       System.out.println(tifoso.getPosto());
    }

    @Override
    public void visit(Vip vip) {
        System.out.println("VIP, puo' accedere alla tribuna vip");
    }

    @Override
    public void visit(Giornalista giornalista) {
        System.out.println("Giornalista, puo' accedere alla tribuna vip");
        System.out.println(giornalista.getTestata());

    }
    
}
