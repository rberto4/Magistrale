#include<iostream>
#include<string>

using namespace std;

class PersonaHeap {

private:
    string wNome;
    string wCognome;
    Auto *wAuto;

public:
    PersonaHeap(string nome, string cognome, string targa) {
        this->wNome = nome;
        this->wCognome = cognome;
        this->wAuto = new Auto(targa);
        cout << "Persona istanziata nello heap, avente un auto targata : " << targa << endl;
    };
};
