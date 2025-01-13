//
// Created by mitic on 13/01/2025.
//
#include <iostream>
using namespace std;

class Motore {
private:
    int wCilindrata;

public:
    Motore(int cilindrata) {
        this->wCilindrata = cilindrata;
        cout << "Istanziato motore di cilindrata " << this->wCilindrata << "cc" << endl;
    }

    int getCilindrata() {
        return this->wCilindrata;
    }
    ~Motore(){
        cout<<"Motore distrutto..."<<endl;
    };
};
