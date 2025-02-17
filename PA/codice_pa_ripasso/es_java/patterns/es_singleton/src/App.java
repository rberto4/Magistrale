public class App {
    public static void main(String[] args) throws Exception {
        Spettatore p1 = new Spettatore("Mario", "Rossi");
        Spettatore p2 = new Spettatore("Luigi", "Verdi");
        Spettatore p3 = new Spettatore("Giovanni", "Bianchi");

        PrenotazioneService ps = new PrenotazioneService();
        ps.stampaPosti();

        ps.prenotaPosto(p1);
        ps.stampaPosti();

        ps.prenotaPosto(p3, 1);
        ps.prenotaPosto(p3, 9);
        ps.stampaPosti();


    }
}
