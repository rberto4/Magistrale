#include <iostream>
using namespace std;

class Motore
{
    public:
        int cilindrata;
        Motore (int c){
            this -> cilindrata = c;
            cout << "Creato motore con cilindrata " << c << "cc\n" <<endl;
}
        
};

/**
 * 
 *  Initializzer list
 *  Motore(int c) : cilindrata(c) {
            cout << "Creato motore con cilindrata " << c << "cc\n" <<endl;
        };
 * Motore (int c){
 *    this -> cilindrata = c;
 *    cout << "Creato motore con cilindrata " << c << "cc\n" <<endl;
 * }
 * 
 */