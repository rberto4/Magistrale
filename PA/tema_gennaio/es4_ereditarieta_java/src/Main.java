
public class Main {
    public static void main(String[] args) {

        // instanziato veicolo a combustione con 30 litri di carburante, posso fare 300km max
        VeicoloACombustione v1 = new VeicoloACombustione(30);
        v1.percorri(80);
        v1.rifornisci(25);

        System.out.println("--------------------------------------------------");
        // instanziato veicolo elettrico con 80 punti percentuale di batteria, posso fare 400km max
        VeicoloElettrico v2 = new VeicoloElettrico(80);
        v2.percorri(80);
        v2.rifornisci(30);

        System.out.println("--------------------------------------------------");
        // instanziato veicolo ibrido con 20 litri di carburante e 50 punti percentuale di batteria, posso fare 250km max
        VeicoloIbrido v3 = new VeicoloIbrido(45, 100);
        v3.percorri(320);
    }
}