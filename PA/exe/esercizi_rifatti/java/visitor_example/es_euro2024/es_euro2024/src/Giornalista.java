public class Giornalista extends Spettatore {
    String testata;

    Giornalista(String nome, String cognome, String testata) {
        super(nome, cognome);
        this.testata = testata;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }
} 