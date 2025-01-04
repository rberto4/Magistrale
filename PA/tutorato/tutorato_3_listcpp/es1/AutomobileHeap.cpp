#include<iostream>
using namespace std;

class AutomobileHeap 
{
    public:
        Motore *motore;
        AutomobileHeap(int cilindrata) {
            this->motore = new Motore(cilindrata);
            cout << "Creata automobile con motore di cilindrata " << cilindrata << "cc\n" << endl;
        }
};