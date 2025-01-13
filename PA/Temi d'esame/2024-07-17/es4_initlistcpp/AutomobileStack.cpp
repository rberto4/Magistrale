//
// Created by mitic on 13/01/2025.
//
#include <iostream>
#include "Motore.cpp"
#include "Bagagliaio.cpp"
using namespace std;

class AutomobileStack {
private:
  string wNome;
  Motore wMotore;
  Bagagliaio wBagagliaio;
public:
    AutomobileStack(string nome, int capacita,int cilindrata) : wNome(nome), wMotore(cilindrata), wBagagliaio(capacita) {
        cout << "Istanziata automobile (s) "<<this->wNome<<" con motore "<< this->wMotore.getCilindrata()<< " e bagagliaio di capacita "<<this->wBagagliaio.getCapacita()<<"L "<< endl;
    };
};