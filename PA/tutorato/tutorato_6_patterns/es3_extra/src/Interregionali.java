import java.util.ArrayList;

public class Interregionali extends Treno{

    final static int MAX_PASSEGGERI = 150;
    private ArrayList<String> wFermateIntermedie;

    public Interregionali(String nome, String partenza, String destinazione, ArrayList<String> fermateIntermedie) {
        super(nome, partenza, destinazione);
        this.wFermateIntermedie = fermateIntermedie;
        /**
        if (partenza.getPasseggeriSaliti() > MAX_PASSEGGERI || destinazione.getPasseggeriScesi() > MAX_PASSEGGERI) {
            throw new EccezioniPasseggeri("Il numero di passeggeri eccede la capienza massima");
        }
        if (partenza.getPasseggeriScesi() > 0) {
            throw new EccezioniPasseggeri("Non possono scendere passeggeri, prima di partire");
        }
        if (destinazione.getPasseggeriSaliti() > 0) {
            throw new EccezioniPasseggeri("Non possono salire passeggeri alla destinazione");
        }
         */

    }

    public ArrayList<String> getFermateIntermedie (){
        return wFermateIntermedie;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
