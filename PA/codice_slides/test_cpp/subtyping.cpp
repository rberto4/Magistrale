#include <iostream>
using namespace std;

class A {
public:
   virtual void saluto (){cout<<"Ciaooo"<<endl;}
   virtual  ~A(){cout<<"Distrutto un a"<<endl;}
};
class B : public A {
public:
    void saluto () override {cout<<"Hellooo"<<endl;}
};
int main (){
    A a;
    a.saluto(); // Ciaooooo

    B b;
    b.saluto(); // helloooo si fa override del metodo saluto
    // vengono chiamati automaticmanete i distruttori
    A* ptr = new B;
    ptr->saluto(); // hellooo, perchè nonostante sia di tipo A il puntatore,
                    // avviene upcasting e dynamic dispactch, con l'oggetto B
    delete ptr; // devo chiamare io il distruttore
    return 0;
};