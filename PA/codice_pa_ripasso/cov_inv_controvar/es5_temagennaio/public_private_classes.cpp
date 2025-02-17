#include <iostream>
using namespace std;

// modificatori di visibilità public protected e private

class A {
  public:
    int a = 20;
protected:
  int b = 10;
private:
  int c = 30;
};

// con public -> Tutti i membri pubblici della classe base rimangono pubblici nella derivata.
// I membri privati non sono accessibili direttamente dalla derivata.

// con private -> Tutti i membri pubblici e protetti della classe base diventano privati nella derivata.
class B : public A {
  public:
    void getA (){
      cout << "Ottieni da A dalla super classe : "<<a<< endl;
    }
  void getB (){
      cout << "Ottieni da A dalla super classe : "<<b<< endl;
    }
};


int main (){
  B b;
  b.getA();
  b.getB();
  cout <<"accedo agli attributi e metodi della super classe di B, stampo a : "<<b.a<<endl;
  return 0;
}