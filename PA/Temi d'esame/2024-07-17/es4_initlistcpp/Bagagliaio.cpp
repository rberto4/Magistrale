//
// Created by mitic on 13/01/2025.
//

#include <iostream>
using namespace std;

class Bagagliaio {
private:
    int wCapacita;

public:
    Bagagliaio(int capacita) {
        this->wCapacita = capacita;
        cout << "Istanziato bagagliaio di capacita " << this->wCapacita << endl;
    };

    int getCapacita() {
        return this->wCapacita;
    }
};
