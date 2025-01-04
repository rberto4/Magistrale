#include<iostream>
#include<string>
#include "Nazionale.cpp"

using namespace std;

class PartitaReferenceHeap{
    private :
        Nazionale &n1;
        Nazionale &n2;
    public:
       PartitaReferenceHeap(string nome1, string nome2) : n1(*(new Nazionale(nome1))), n2(*(new Nazionale(nome2))){
            cout << "Partita tra " << nome1 << " e " << nome2 << endl;
        }
};

/**
 * Devo inizializzare le nazioni prima di PartitaReferenceHeap,
 * altrimenti non posso passare i riferimenti, in quanto sono puntatori
 * che non sono mai nulli.
 * 
 */