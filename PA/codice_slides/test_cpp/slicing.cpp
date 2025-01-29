#include <iostream>
using namespace std;

class Veicolo {
public:
    int km = 40;
    /*virtual*/ void percorri() { cout << "Ho percorso " << km << " km" << endl; }
};

class Aereo : public Veicolo {
public:
    void percorri() /* override*/ { cout << "Ho percorso " << km << " km, volando" << endl; }
};

int main() {
    Aereo a;
    Veicolo v = a; // slicing, copia solo la parte Veicolo di Aereo
    a.percorri();
    v.percorri(); //percorso 40 km. Perdo la parte 'volando'

    //Soluzione -> puntatori, ma serve la keyword Virtual!!! avviene binding statico, quindi non ho comportamento polimorfico.
    Veicolo *a2 = new Aereo();
    a2->percorri(); // percorso 40 km, senza volando, perche non è virtual
    delete a2;
    return 0;
}
