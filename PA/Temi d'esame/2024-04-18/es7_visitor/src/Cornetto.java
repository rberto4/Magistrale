public class Cornetto extends Dolce{

    private String wRipieno;
    public Cornetto(int kCal, String ripieno) {
        super(kCal);
        this.wRipieno = ripieno;
    }

    public String getRipieno() {
        return wRipieno;
    }

    public void setRipieno(String ripieno) {
        wRipieno = ripieno;
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
