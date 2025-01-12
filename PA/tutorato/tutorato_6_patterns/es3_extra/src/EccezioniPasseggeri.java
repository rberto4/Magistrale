public class EccezioniPasseggeri extends RuntimeException {
    public EccezioniPasseggeri(String message) {
        super(" --- ERRORE : " + message);
    }
}
