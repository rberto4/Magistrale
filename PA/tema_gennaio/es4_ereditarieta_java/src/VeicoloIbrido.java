
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

    // mia assunzione, il veicolo ibrido cerca di percorre il tragitto con il motore elettrico, 
    // se la batteria si scarica, allora usa il motore a combustione

    @Override
    public void percorri(float km) {
        if (km > wVeicoloACombustione.getAutonomia() + wVeicoloElettrico.getAutonomia()){
            System.out.println("Autonomia insufficiente");
            return;
        }
        if (wVeicoloElettrico.getAutonomia() >= km){
            wVeicoloElettrico.percorri(km);
        }else{
            km = km - wVeicoloElettrico.getAutonomia();
            wVeicoloElettrico.percorri(wVeicoloElettrico.getAutonomia());
            wVeicoloACombustione.percorri(km);
        }
    }

}
