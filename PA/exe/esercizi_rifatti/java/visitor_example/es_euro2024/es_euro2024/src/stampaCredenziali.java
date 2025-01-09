public class stampaCredenziali implements Visitor {

    @Override
    public void visit(Vip vip) {
        // TODO Auto-generated method stub
        System.out.println("VIP: " + vip.nome + " " + vip.cognome);    }

    @Override
    public void visit(Tifoso tifoso) {
        // TODO Auto-generated method stub
        System.out.println("Tifoso: " + tifoso.nome + " " + tifoso.cognome);
    }

    @Override
    public void visit(Giornalista giornalista) {
        // TODO Auto-generated method stub
        System.out.println("Giornalista: " + giornalista.nome + " " + giornalista.cognome);
    }
    
}
