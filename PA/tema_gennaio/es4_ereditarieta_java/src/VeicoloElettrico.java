public class VeicoloElettrico extends Veicolo implements Alimentazione {

    private float wBatteria;
    private static final float kmPerPuntoPercentuale = 5;

    public VeicoloElettrico(float batteria) {
        this.wBatteria = batteria;
    }

    

    // mia assunzione, il veicolo elettrico percorre 5 km con 1 punto percentuale di batteria

    @Override
    public void percorri(float km) {
        if ((wBatteria - km/kmPerPuntoPercentuale) >= 0){
            this.km = km;
            wBatteria = wBatteria - km/kmPerPuntoPercentuale;
            System.out.println("Percorsi " + km + " km con motore elettrico");
            System.out.println(" - Batteria rimasta: " + wBatteria +" %");
        }else{
            System.out.println("Batteria insufficiente");
        }
       
    }

    @Override
    public void rifornisci(float quantita) {
        wBatteria += quantita;
        System.out.println("Batteria rifornita del " + quantita + " %");
        System.out.println("Autonomia massima: " + wBatteria*kmPerPuntoPercentuale + " km");
    }



    @Override
    public float getAutonomia() {
        return wBatteria * kmPerPuntoPercentuale;

    }



    @Override
    public float getLivelloAlimentazione() {
       return wBatteria;
    }

} 
