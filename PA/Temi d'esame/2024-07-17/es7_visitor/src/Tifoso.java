public class Tifoso extends Spettatore implements Comparable<Tifoso>{
    private int wTribuna;
    private int wPosto;

    public Tifoso (String nome, String cognome, int posto, int tribuna){
        super(nome, cognome);
        this.wPosto = posto;
        this.wTribuna = tribuna;
    }

    public void setPosto (int posto, int tribuna){
        wTribuna = tribuna;
        wPosto = posto;
    }

    public String getPosto(){
        return "Posto : "+wPosto+"\n"+"Tribuna :"+wTribuna;
    }

    @Override
    void accept(visitor visitor) {
       visitor.visit(this);
    }

    @Override
    public int compareTo(Tifoso o) {
        if (wTribuna == o.wTribuna) return Integer.compare(wPosto, o.wPosto);
        else return Integer.compare(wTribuna, o.wTribuna); 
    }
}
