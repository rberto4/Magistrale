public class Agnello extends Pietanza{
    private double wPeso;
    private Cotture wCottura;

    public Agnello(double kcals, Cotture cottura){
       super(kcals);
       this.wCottura = cottura;
    }

    public void setPeso (double peso){
        wPeso = peso;
    }
    public double getPeso(){
        return wPeso;
    }

    public void configuraCottura (Cotture cotture){
        wCottura = cotture;
    }
    public Cotture getCottura(){
        return wCottura;
    }

    @Override
    public double getKcal() {
        return wkcal;
    }

    @Override
    public void accept (Visitor visitor){
        visitor.visit(this);
    };

}
