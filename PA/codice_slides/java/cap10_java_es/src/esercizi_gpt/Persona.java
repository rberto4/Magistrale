package esercizi_gpt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Persona implements Comparable<Persona>{ 
    private String nome;
    private String cognome;
    private int eta;

    public Persona (String nome,String cognome, int eta){
        this.nome = nome;
        this.eta = eta;
        this.cognome = cognome;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    // confronto per eta, se etta è uguale confronto per cognome, se cognome è uguale confronto per nome.
    @Override
    public int compareTo(Persona o) {
       if (this.eta == o.eta){
        if (this.cognome.equals(o.cognome)){
            return this.nome.compareTo(o.nome);

        }else{
            return this.cognome.compareTo(o.cognome);
        }
       }else{
        return Integer.compare(this.eta, o.eta);
       }
    }

    @Override
    public String toString() {
        return "- Nome : " + nome + " \n- Cognome : " + cognome + " \n- Eta : " + eta+ "\n";
    }

    public static void stampaPersone(List<Persona> lista){
        for (Persona p : lista) {
            System.out.println(p.toString());
        }
    }

    public static void main(String[] args) {
        List<Persona> listaPersone = new ArrayList<>();
        Persona p1 = new Persona("Mario", "Rossi", 30);
        Persona p2 = new Persona("Luca", "Bianchi", 30);
        Persona p3 = new Persona("Mario", "Rossi", 25);
        Persona p4 = new Persona("Fausto", "Bianchi", 19);
        listaPersone.add(p1);
        listaPersone.add(p2);
        listaPersone.add(p3);
        listaPersone.add(p4);

        System.out.println("Prima dell'ordinamento -----------------------");
        stampaPersone(listaPersone);
        Collections.sort(listaPersone);
        System.out.println("Dopo l'ordinamento -----------------------");
        stampaPersone(listaPersone);

    }
}
