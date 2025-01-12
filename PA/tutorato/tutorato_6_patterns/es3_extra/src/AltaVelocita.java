public class AltaVelocita extends Treno{

    final static int MAX_PASSEGGERI = 100;

    public AltaVelocita(String nome, String partenza, String destinazione) {
        super(nome, partenza, destinazione);
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

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
