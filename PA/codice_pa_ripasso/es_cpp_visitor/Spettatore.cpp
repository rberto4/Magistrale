#include <iostream>
#include "Visitor.cpp"
using namespace std;


class Spettatore {
private:
    string nome;
    string cognome;
public:
    virtual ~Spettatore() = default;

    void setCredenziali (string n,string c){
       this->nome = n;
       this->cognome = c;
       }
    string getCredenziali() {
        return this->nome + " " + cognome;
    }
    virtual void accept (Visitor& v) = 0;
};

class Tifoso : public Spettatore {
private:
    int tribuna;
    int posto;
public:
    Tifoso(string nome, string cognome) : tribuna(0), posto(0) {
        setCredenziali(nome,cognome);
    }
    void setPosto(int t, int p) {
        this->posto = t;
        this->tribuna = p;
    }
    int getPosto() {
        return this->posto;
    }
    int getTribuna() {
        return this->tribuna;
    }
    void accept(Visitor & visitor) override {
        visitor.visit(*this);
    }
};

class Vip : public Spettatore {
public:
    Vip(string nome, string cognome) {
        setCredenziali(nome,cognome);
    };
    void accept(Visitor & visitor) {
        visitor.visit(*this);
    }
};


class Giornalista : public Spettatore {
private:
    string testata;
public:
    Giornalista(string nome, string cognome, string testata){
        setCredenziali(nome,cognome);
        this->testata = testata;
    };
    string getTestata() {
        return this->testata;
    }
    void accept(Visitor & visitor) override {
        visitor.visit(*this);
    }
};
