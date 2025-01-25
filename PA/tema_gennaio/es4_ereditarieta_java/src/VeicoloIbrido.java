
/**
 * Approccio simil facade, ho classi che si occupano di gestire le singole parti della macchina ibrida
 * 
 */

public class VeicoloIbrido extends Veicolo{

    private VeicoloACombustione wVeicoloACombustione;
    private VeicoloElettrico wVeicoloElettrico;

    public VeicoloIbrido(float litriCarburante, float livelloBatteria){
        wVeicoloACombustione = new VeicoloACombustione(litriCarburante);
        wVeicoloElettrico = new VeicoloElettrico(livelloBatteria);
    }
    public void rifornisci (float livelloBatteria, float litriCarburante){
        wVeicoloACombustione.rifornisci(litriCarburante);
        wVeicoloElettrico.rifornisci(livelloBatteria);
    }

    // mia assunzione, il veicolo ibrido percorre sempre con entrambi i motori, metà in elettrico e metà in combustione

    @Override
    public void percorri(float km) {
        wVeicoloACombustione.percorri(km/2);
        wVeicoloElettrico.percorri(km/2);
    }

}
