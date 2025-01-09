public class AccessoTribuna  implements Visitor{

    private boolean wVipAccess = false;

    @Override
    public void visit(Vip vip) {
        wVipAccess = true;
    }

    @Override
    public void visit(Tifoso tifoso) {
        wVipAccess = false;
    }

    @Override
    public void visit(Giornalista giornalista) {
        wVipAccess = true;
    }
    
    public boolean isAccessoVIP() {
        return wVipAccess;
    }

}
