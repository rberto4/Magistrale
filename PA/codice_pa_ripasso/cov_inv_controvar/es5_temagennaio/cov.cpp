#include <iostream>

using namespace std;


class A {
public:
    A *metodo() {
        cout << "A" << endl;
        return new A();
    }
    void tentativoCovParametri(A * b) {};

};

class B : public A {
public:
    B *metodo() {
        cout << "B" << endl;
        return new B();
    }

    void tentativoCovParametri(B * b){};
    // è overloading in caso, senza virtual, ma perdo polimorfismo a runtime.
    // NON FUNZIONA perchè non posso usare la covarianza nei parametri in cpp
    // ovvero accettare un tipo piu specifico come parametro
};

int main() {
    A a;
    B b;

    // covarianza del tipo di ritorno -> funziona se faccio overload del metodo con virtual
    // altrimenti il binding avviene a compile time.
    // con virtual ho il late Binding.

    A *ptr;
    ptr = &a;
    ptr->metodo();
    ptr = &b;
    ptr->metodo();

    return 0;
}
