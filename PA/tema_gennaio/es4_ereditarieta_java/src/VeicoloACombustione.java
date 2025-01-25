public class VeicoloACombustione extends Veicolo implements Alimentazione {

    private float wSerbatoio = 0;
    private static final float kmPerLitro = 10;

    public VeicoloACombustione(float serbatoio) {
        this.wSerbatoio = serbatoio;
    }

    // mia assunzione, il veicolo a combustione percorre 10 km con un litro di carburante

    @Override
    public void percorri(float km) {
        if ((wSerbatoio - km/kmPerLitro) >= 0){
            this.km = km;
            wSerbatoio = wSerbatoio - km/kmPerLitro;
            System.out.println("Percorsi " + km + " km con motore a combustione");
            System.out.println(" - Serbatoio rimasto: " + wSerbatoio +" litri");
        }else{
            System.out.println("Serbatoio insufficiente");
        }
       
    }
    @Override
    public void rifornisci(float quantita) {
        wSerbatoio += quantita;
        System.out.println("Serbatoio rifornito di " + quantita + " litri");
        System.out.println("Autonomia massima: " + wSerbatoio*kmPerLitro + " km");

    }

    @Override
    public float getAutonomia() {
       return wSerbatoio * kmPerLitro;
    }

    @Override
    public float getLivelloAlimentazione() {
       return wSerbatoio;
    }

} 