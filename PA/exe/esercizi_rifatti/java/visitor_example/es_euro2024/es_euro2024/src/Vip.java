public class Vip extends Spettatore{

    public Vip (String nome, String cognome){
       super(nome,cognome);
    }
    @Override
    void accept(Visitor visitor) {
       visitor.visit(this);
    }
     
}
