//
// Created by mitic on 28/01/2025.
//
#include <iostream>
using namespace std;


void modifyByValue(int &x) {
    x = 60;
};

void modifyByPtr(int* x) {
    *x = 10; // Modifica il valore originale
}

void nonmodify(int x) {
    x = 20;
}

int countCalls() {
    static int calls = 0; // Conserva il valore tra le chiamate
    return ++calls;
}

int main() {
    int a = 5;
    int b = 7;
    int c = 30;

    modifyByPtr(&a); // Passa l'indirizzo di 'a'
    cout << a <<endl; // Output: 10 (l'originale è modificato)

    nonmodify(b);
    cout << b <<endl; // stampa 5, non modifica nulla perchè x = 20 è una copia locale.

    modifyByValue(c);
    cout << c <<endl; // stampa 60, modifico e non devo gestire puntatori.

    for (int i = 0; i<10; i++) {
        cout << countCalls() << endl; // stampa 1,2,3,4... fino a 10, perchè la
        // variabile statica si mantiene tra le chiamate alla funzione.
    }
    return 0;
}