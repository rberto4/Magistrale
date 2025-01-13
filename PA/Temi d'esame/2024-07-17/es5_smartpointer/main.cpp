#include <iostream>
#include <memory>
#include "Automobile.cpp"


int main () {
    // raw pointer, memoria non gestita automaticamente, quando esco dal blocco {}
    // devo chiamare delete a1

    {
        Automobile * a1 = new Automobile("Fiat panda", 1200,60);
       delete a1;
    }
    // unique pointer, memoria gestita automaticamente
    {
        unique_ptr<Automobile> a2 (new Automobile("Porche 911", 4200,40));
    }
    // shared pointer
    {
        shared_ptr<Automobile> a3 (new Automobile("Ferrari 458", 5200,35));
        shared_ptr<Automobile> copia_di_a3 (a3);
        cout << "Al momento ci sono " << copia_di_a3.use_count() << " copie dell'auto \n";

    }
}