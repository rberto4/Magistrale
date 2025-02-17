package es_gpt;

public class Rettangolo extends Forma {
    private double wBase;
    private double wAltezza;

    public Rettangolo(double base, double altezza) {
        this.wBase = base;
        this.wAltezza = altezza;
    }

    @Override
    public double calcolaArea() {
        return wBase * wAltezza;
    }

   
}
