#include<iostream>
using namespace std;
/*
class Base {
  public:
    virtual Base* clona() {
      return new Base(*this);
    }
};

class Derivata : public Base {
public:
  Derivata* clona() override { return new Derivata(*this); }  // 🔥 Covarianza nel tipo restituito
};

int main(){
  Derivata d;
  Base* copia = d.clona();  // Copia ma il tipo restituito è Derivata*

  delete copia;
  return 0;
  }

  */

class Figura {
public:
    Figura() {
        cout << "Figura" << endl;
    };
    virtual Figura *get() { return new Figura(*this); };
};

class Cerchio : public Figura {
public:
    Cerchio() {
        cout << "Cerchio" << endl;
    }

    Cerchio *get() override { return new Cerchio(*this); }
};

int main() {
    Figura *ptr = new Cerchio();
    ptr->get();
    delete ptr;
    return 0;
}
