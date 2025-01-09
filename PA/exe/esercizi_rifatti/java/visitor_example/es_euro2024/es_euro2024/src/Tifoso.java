public class Tifoso extends Spettatore{

    private int tribuna;
    private int posto;

    public Tifoso (String nome, String cognome, int tribuna, int posto) {
        super(nome, cognome);
        this.tribuna = tribuna;
        this.posto = posto;
    }

    public int getTribuna (){
        return tribuna;
    }
    @Override
    void accept(Visitor visitor) {
       visitor.visit(this);
    }
     
}
