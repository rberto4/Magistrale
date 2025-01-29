//
// Created by mitic on 29/01/2025.
//
#include<iostream>
using namespace std;

class A {
public:
    void vi() { cout << "A::vi()" << endl; }
};

class B : public A {
public:
    void vi() { cout << "B::vi()" << endl; } // Ridefinizione (ma non override)
};

int main() {
    A* pa = new B();
    pa->vi(); // Output: "A::vi()" (binding statico!)

    A a;
    B b;
    A& ra = b;
    ra.vi();  // Output: "A::vi()" (binding statico!)

    delete pa;
    return 0;
}
