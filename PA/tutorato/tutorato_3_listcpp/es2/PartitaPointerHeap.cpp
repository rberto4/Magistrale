#include<iostream>
#include<string>
#include "Nazionale.cpp"

using namespace std;

class PartitaPointerHeap{
    private :
        Nazionale *n1;
        Nazionale *n2;
    public:
        PartitaPointerHeap(string nome1, string nome2) : n1(new Nazionale(nome1)), n2(new Nazionale(nome2)){
            this -> n1 = new Nazionale(nome1);
            this -> n2 = new Nazionale(nome2);
            cout << "Partita tra " << nome1 << " e " << nome2 << endl;
        }
};

/**
 * Implementazione con initializer list : 
 * (non richiesta perchè un puntatore all'heap può essere nullo)
 * PartitaPointerHeap(string nome1, string nome2) : n1(new Nazionale(nome1)), n2(new Nazionale(nome2)){
 *    cout << "Partita tra " << nome1 << " e " << nome2 << endl;
 * }
 * 
 */

