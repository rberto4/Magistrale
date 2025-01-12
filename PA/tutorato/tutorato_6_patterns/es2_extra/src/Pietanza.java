public abstract class Pietanza implements Comparable<Pietanza> {
    double wkcal;
    Pietanza(double kcal){
        wkcal = kcal;
    }
    abstract double getKcal();
    abstract void accept (Visitor visitor);
    public int compareTo(Pietanza o) {
        return Double.compare(wkcal, o.getKcal());
    }
}
