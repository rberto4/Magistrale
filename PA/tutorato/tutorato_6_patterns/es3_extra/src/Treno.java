public abstract class Treno {
    String wNome;
    String wPartenza;
    String wDestinazione;

    public Treno(String nome, String partenza, String destinazione) {
        this.wNome = nome;
        this.wPartenza = partenza;
        this.wDestinazione = destinazione;
    }
    public String getNome() {
        return wNome;
    }

    abstract void accept(Visitor visitor);
}
