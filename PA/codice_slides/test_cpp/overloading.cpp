#include <iostream>
using namespace std;

class A {
public:
    void print(int x) { cout << "Intero: " << x << endl; }
    void print(double x) { cout << "Double: " << x << endl; }
    void print(string x) { cout << "Stringa: " << x << endl; }
    int somma (int n1, int n2) {return n1+n2;}
    double somma (double d1,double d2){return d1+d2;}
};

int main() {
    A obj;
    obj.print(10);       // Chiamata alla print(int)
    obj.print(3.14);     // Chiamata alla print(double)
    obj.print("Hello");  // Chiamata alla print(string)

    cout << obj.somma(5,6) <<endl; // chiamata a somma (int, int)
    cout << obj.somma(12.9, 3.8) <<endl; // chiamata a somma(double,double)
    return 0;
}
