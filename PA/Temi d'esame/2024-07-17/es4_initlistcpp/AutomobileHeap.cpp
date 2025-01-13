//
// Created by mitic on 13/01/2025.
//
#include <iostream>
using namespace std;

class AutomobileHeap {
private:
    string wNome;
    Motore* wMotore;
    Bagagliaio* wBagagliaio;
public:
    AutomobileHeap(string nome, int capacita,int cilindrata) {
        this->wNome = nome;
        this->wMotore = new Motore(cilindrata);
        this->wBagagliaio = new Bagagliaio(capacita);
        cout << "Istanziata automobile (h) "<<nome<<" con motore "<< cilindrata<< " e bagagliaio di capacita "<<capacita<<"L"<< endl;
    };
};

/**
*  possibile anche con Init. list perchè è possibile avere puntatori a null
*  : wNome(nome), wMotore(cilindrata), wBagagliaio(capacita)
*/