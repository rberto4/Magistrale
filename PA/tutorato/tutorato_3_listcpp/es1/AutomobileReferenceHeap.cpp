#include <iostream>
using namespace std;    

class AutomobileReferenceHeap 
{
    public:
        Motore &motore;
        AutomobileReferenceHeap(int cilindrata) : motore(*(new Motore(cilindrata))) {
            cout << "Creata automobile con motore di cilindrata " << cilindrata << "cc\n" << endl;
        }
};