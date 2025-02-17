//
// Created by mitic on 08/02/2025.
//
#include <iostream>
#include <string.h>
using namespace std;


class Indirizzo {
private:
    string via;
    int numero;
    string citta;

public:
    Indirizzo(string v, int n, string c): via(v), numero(n), citta(c) {
        cout << "Indirizzo creato" << endl;
    }

    void stampaIndirizzo() {
        cout << "--- Residenza ---" << endl;
        cout << " - Via : " << this->via << endl;
        cout << " - Numero : " << this->numero << endl;
        cout << " - Citta' : " << this->citta << endl;
    }
};


class Persona {
private:
    string nome;
    int eta;
    Indirizzo indirizzo;
    static int counter;

public:
    Persona(string n, int e, Indirizzo i): nome(n), eta(e), indirizzo(i) {
        cout << "Persona di " << nome << " e di " << eta << " anni instaziata" << endl;
        counter++;
    }

    Persona(Indirizzo i): indirizzo(i) {
        this->nome = "Sconosciuto";
        this->eta = 0;
        counter++;
    }

    Persona(string n, Indirizzo i): indirizzo(i) {
        this->nome = n;
        counter++;
    }

    virtual void stampaDati() {
        cout << " - " << this->nome << endl;
        cout << " - " << this->eta << endl;
        this->indirizzo.stampaIndirizzo();
    }

    void setNome(string n) {
        this->nome = n;
    }

    void setEta(int e) {
        if (e >= 0) {
            this->eta = e;
        } else {
            cout << "Eta non valida" << endl;
        }
    }

    string getNome() {
        return this->nome;
    }

    int getEta() {
        return this->eta;
    }

    static int getCounter();
    virtual void descrivi() = 0; // motod
};

int Persona::counter = 0; // Inizializzazione della variabile statica

int Persona::getCounter() {
    return counter;
}


class Studente : public Persona {
private:
    int matricola;
    string corsoDiStudio;

public:
    Studente(string n, int e, Indirizzo i, int m, string c): Persona(n, e, i), matricola(m), corsoDiStudio(c) {
    }

    void stampaDati() override {
        Persona::stampaDati();
        cout << "--- Iscrizione all'Universita' ---" << endl;
        cout << "- Corso di studi : " << this->corsoDiStudio << endl;
        cout << "- Matricola : " << this->matricola << endl;
    }

    void descrivi() override {
        cout <<"--- Descrizione studente ---" << endl;
        this->stampaDati();
    };

    ~Studente() {
        cout << "Studente deinizializzato"<<endl;
    }
};



class Lavoratore {
private:
    string lavoro;
public:
    Lavoratore(string l): lavoro(l) {}

    string getLavoro() {
        return this->lavoro;
    }
    void setLavoro(string l) {
        this->lavoro = l;
    }
    void stampaDati() {
        cout << "- Lavoro : " << this->getLavoro() << endl;
    }
};

class StudenteLavoratore : virtual public Studente, Lavoratore {
    public:
    StudenteLavoratore (string n, int e, Indirizzo i, int m, string c, string l) : Studente(n,e,i,m, c), Lavoratore(l) {
        cout << " -------- Studente lavoratore creato -------" << endl;

    }
    void descrivi() override {
        cout <<"--- Descrizione studente ---" << endl;
        Studente::stampaDati();
        Lavoratore::stampaDati();
    };
};

int main() {
    Indirizzo i = Indirizzo("Via san fermo", 5, "Pradalunga");
    Studente s1 ("Roberto", 25, i, 1060957,"Ingegneria informatica");
    s1.descrivi();
    cout << "Numero di persone instanziate: " << Persona::getCounter() << endl;
    StudenteLavoratore sl = StudenteLavoratore("Johnny", 20, i, 109056, "Lettere", "Commesso" );
    sl.descrivi();
}
