#include<iostream>
#include<string>
#include "Nazionale.cpp"
using namespace std;

class PartitaStack{
    private:
        Nazionale n1;
        Nazionale n2;
    public:
        PartitaStack(string nome1,string nome2) : n1(nome1), n2(nome2) {
            cout << "Partita tra " << nome1 << " e " << nome2 << endl;
        }
};