import java.security.PublicKey;

public abstract class Spettatore {
    private String nome;
    private String cognome;
    public abstract void accept (Visitor visitor);
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
}