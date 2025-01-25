public class VeicoloACombustione extends Veicolo implements Alimentazione {

    private float wSerbatoio = 0;
    
    public float getSerbatoio() {
        return wSerbatoio;
    }

    public VeicoloACombustione(float serbatoio) {
        this.wSerbatoio = serbatoio;
    }

    // mia assunzione, il veicolo a combustione percorre 10 km con un litro di carburante

    @Override
    public void percorri(float km) {
        if ((wSerbatoio - km/10) >= 0){
            this.km = km;
            wSerbatoio = wSerbatoio - km/10;
        }else{
            System.out.println("Serbatoio insufficiente");
        }
       
    }
    @Override
    public void rifornisci(float quantita) {
        wSerbatoio += quantita;
    }

} 