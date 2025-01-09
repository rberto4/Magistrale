public abstract class Spettatore  {
    String nome;
    String cognome;

    Spettatore (String nome, String cognome){
        this.nome = nome;
        this.cognome = cognome;
    }

    abstract void accept(Visitor visitor);
}


