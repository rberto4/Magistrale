public class Giornalista extends Spettatore {

    private String wTestata;

    public Giornalista (String nome, String cognome, String testata ){
        super(nome, cognome);
        this.wTestata = testata;
    }

    public String getTestata(){
        return "Testata : "+wTestata;
    }

    @Override
    void accept(visitor visitor) {
       visitor.visit(this);
    }
}

