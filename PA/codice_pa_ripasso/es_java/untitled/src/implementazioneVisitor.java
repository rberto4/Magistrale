public class implementazioneVisitor implements Visitor{

    @Override
    public void printCredenziali(Tifoso tifoso) {
        System.out.println("Tifoso ---------------------");
        System.out.println(tifoso.getNome() + " " + tifoso.getCognome());
    }

    @Override
    public void printCredenziali(Vip vip) {
        System.out.println("Vip ---------------------");
        System.out.println(vip.getNome() + " " + vip.getCognome());

    }
}