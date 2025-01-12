public class Torta extends Dolce{

    private TipologiaTorta wTipologiaTorta;
    public Torta(int kCal) {
        super(kCal);
    }

    public void configuraTipologia(TipologiaTorta wTipo) {
        wTipologiaTorta = wTipo;
    }
    public TipologiaTorta getTipologia() {
        return wTipologiaTorta;
    }
    @Override
    void accept(Visitor v) {
      v.visit(this);
    }

    @Override
    public int compareTo(Dolce o) {
        return Integer.compare(this.getKcal(), o.getKcal());
    }
}
