public class Carota extends Pietanza {
    private boolean wConPrezzemolo;

    public Carota(double kcal) {
        super(kcal);
    }

    @Override
    double getKcal() {
        return wkcal;
    }

    public void setPrezzemolo(boolean conPrezzemolo) {
        wConPrezzemolo = conPrezzemolo;
    }
    public boolean isConPrezzemolo() {
        return wConPrezzemolo;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }


}
