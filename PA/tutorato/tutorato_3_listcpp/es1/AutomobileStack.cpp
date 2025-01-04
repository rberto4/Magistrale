#include <iostream>
#include "Motore.cpp"
using namespace std;

class AutomobileStack{
    Motore motore;
    public:
        AutomobileStack(int cilindrata) : motore(cilindrata){
            cout << "Creata automobile con motore di cilindrata " << cilindrata << "cc\n" << endl;
        }
};