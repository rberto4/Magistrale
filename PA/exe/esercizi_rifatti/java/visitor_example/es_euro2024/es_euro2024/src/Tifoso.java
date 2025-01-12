public class Tifoso extends Spettatore implements Comparable<Tifoso> {


    private int tribuna;
    private int posto;

    public Tifoso(String nome, String cognome, int tribuna, int posto) {
        super(nome, cognome);
        this.tribuna = tribuna;
        this.posto = posto;
    }

    public int getTribuna() {
        return tribuna;
    }

    @Override
    void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int compareTo(Tifoso o) {

        if(this.tribuna != o.tribuna){
            return Integer.compare(this.tribuna, o.tribuna);
        }
        return Integer.compare(this.posto, o.posto);
    }

    @Override
    public String toString() {
        return "Tifoso{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", tribuna=" + tribuna +
                ", posto=" + posto +
                '}';
    }
}
