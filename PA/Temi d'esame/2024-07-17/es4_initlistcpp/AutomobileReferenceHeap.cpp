//
// Created by mitic on 13/01/2025.
//
#include <iostream>


using namespace std;

class AutomobileReferenceHeap {
private:
    string wNome;
    Motore &wMotore;
    Bagagliaio &wBagagliaio;
public:
    AutomobileReferenceHeap(string nome, int capacita,int cilindrata) : wMotore(*(new Motore(cilindrata))), wBagagliaio(*(new Bagagliaio(capacita))) {
        this->wNome = nome;
        cout << "Istanziata automobile (rh) "<<this->wNome<<" con motore "<< this->wMotore.getCilindrata()<< " e bagagliaio di capacita "<<this->wBagagliaio.getCapacita()<<"L"<< endl;
    };
};
