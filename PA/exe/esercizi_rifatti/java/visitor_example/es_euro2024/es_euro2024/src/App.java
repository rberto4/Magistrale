public class App {
    public static void main(String[] args) throws Exception {
       Spettatore testvip = new Vip("Gerry", "Scotti");
       Spettatore testtifoso = new Tifoso("Roberto", "Bertocchi", 2,67);
       Spettatore testgiornalista = new Giornalista("Marco", "Travaglio", "Il Fatto Quotidiano");

       // Controllo accesso VIP
       AccessoTribuna accessoVisitor = new AccessoTribuna();
       testvip.accept(accessoVisitor);
       System.out.println("Accesso VIP per VIP: " + accessoVisitor.isAccessoVIP());

       testtifoso.accept(accessoVisitor);
       System.out.println("Accesso VIP per Tifoso: " + accessoVisitor.isAccessoVIP());
       testgiornalista.accept(accessoVisitor);
       System.out.println("Accesso VIP per Giornalista: " + accessoVisitor.isAccessoVIP());

       // Stampa nome e cognome
       stampaCredenziali stampaVisitor = new stampaCredenziali();
       testvip.accept(stampaVisitor);
       testtifoso.accept(stampaVisitor);
       testgiornalista.accept(stampaVisitor);

    }
}
