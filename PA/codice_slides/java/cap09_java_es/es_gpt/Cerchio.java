package es_gpt;

public class Cerchio extends Forma {
    private double wRaggio;

    public Cerchio(double raggio) {
        this.wRaggio = raggio;
    }
    @Override
    public double calcolaArea() {
        return Math.PI * Math.pow(wRaggio, 2);
    }
    
    
}
