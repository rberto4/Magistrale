public class VeicoloElettrico extends Veicolo implements Alimentazione {

    private float wBatteria;

    public VeicoloElettrico(float batteria) {
        this.wBatteria = batteria;
    }

    public float getBatteria() {
        return wBatteria;
    }

    // mia assunzione, il veicolo elettrico percorre 5 km con 1 punto percentuale di batteria

    @Override
    public void percorri(float km) {
        if ((wBatteria - km/5) >= 0){
            this.km = km;
            wBatteria = wBatteria - km/5;
        }else{
            System.out.println("Batteria insufficiente");
        }
       
    }

    @Override
    public void rifornisci(float quantita) {
        wBatteria += quantita;
    }

} 
