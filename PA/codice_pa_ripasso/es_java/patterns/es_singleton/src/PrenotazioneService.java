public class PrenotazioneService {
    private Db db;
    public PrenotazioneService() {
        db = Db.getInstance();
    }

    public void stampaPosti() {
        db.stampaPosti();
    }
    public void prenotaPosto(Spettatore spettatore) {
        db.prenotaPosto(spettatore.getNome(), spettatore.getCognome());
    }
    public void prenotaPosto(Spettatore spettatore, int posto) {
        db.prenotaPosto(spettatore.getNome(), spettatore.getCognome(), posto);
    }
    
}
