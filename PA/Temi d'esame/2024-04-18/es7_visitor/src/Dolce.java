public abstract class Dolce implements Comparable<Dolce> {
    private int wKCal;

    public Dolce(int kCal) {
        this.wKCal = kCal;
    }

    public int getKcal() {
        return wKCal;
    }

    abstract void accept(Visitor v);
}
