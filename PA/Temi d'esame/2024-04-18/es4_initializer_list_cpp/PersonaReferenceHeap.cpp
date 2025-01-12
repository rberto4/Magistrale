#include<iostream>
#include<string>

using namespace std;

class PersonaReferenceHeap {

private:
    string wNome;
    string wCognome;
    Auto &wAuto;

public:
    PersonaReferenceHeap(string nome, string cognome, string targa): wNome(nome), wCognome(cognome), wAuto(*(new Auto(targa))) {
        cout << "Persona istanziata nello heap by reference, avente un auto targata : " << targa << endl;
    }
};


/**
*   In quest'ultimo caso usare le inizizer list è necessarie
*   in ogni caso per la natura delle reference, ovvero puntatori che
*   non possono mai avere valore nullo
 *
 *
 *
 *