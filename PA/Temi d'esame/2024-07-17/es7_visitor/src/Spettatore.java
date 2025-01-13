public abstract class Spettatore {
    private String wNome;
    private String wCognome;

    public Spettatore (String nome, String cognome){
        this.wNome = nome;
        this.wCognome = cognome;
    }
    public String getCredenziali(){
        return wNome+" "+wCognome;
    }

    abstract void accept(visitor visitor);
}
