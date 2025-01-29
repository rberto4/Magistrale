//
// Created by mitic on 29/01/2025.
//

#include <iostream>
using namespace std;
class Animal {
public:
    virtual void makeSound() const { // Funzione virtuale
        cout << "Some generic animal sound" << endl;
    }
    virtual ~Animal() { // Distruttore virtuale
        cout << "Animal destroyed" << endl;
    }
};

class Dog : public Animal {
public:
    void makeSound() const override { // Override della funzione virtuale
        cout << "Woof! Woof!" << endl;
    }
};

class Cat : public Animal {
public:
    void makeSound() const override {
        cout << "Meow!" << endl;
    }
};

int main() {
    Animal* a1 = new Dog(); // dynamic dispatch
    Animal* a2 = new Cat();

    a1->makeSound(); // Output: Woof! Woof! (chiamata a runtime)
    a2->makeSound(); // Output: Meow!

    delete a1;
    delete a2;

    return 0;
}
