#include <iostream>
using namespace std;

class Shape {
public:
    virtual void draw() = 0; // Funzione virtuale pura
    virtual ~Shape() {} // Distruttore virtuale
};

class Circle : public Shape {
public:
    void draw() override { cout << "Disegno un cerchio!" << endl; }
};

class Rectangle : public Shape {
public:
    void draw() override { cout << "Disegno un rettangolo!" << endl; }
};

int main() {
    Shape* s1 = new Circle();
    Shape* s2 = new Rectangle();

    s1->draw();  // ✅ "Disegno un cerchio!"
    s2->draw();  // ✅ "Disegno un rettangolo!"

    delete s1;
    delete s2;
    return 0;
}
