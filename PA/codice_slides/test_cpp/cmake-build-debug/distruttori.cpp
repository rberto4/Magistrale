//
// Created by mitic on 28/01/2025.
//
#include <iostream>
using namespace std;


class Automobile {
  public:
    Automobile(int c): cilindrata(c) {
      cout << "Automobile con motore "<<c<<" cc istanziata" << endl;
    }
    ~Automobile(){
      cout << "Automobile con motore "<<cilindrata<<" cc eliminata" << endl;
    }
  private:
    int cilindrata;

};

int main() {

  {
    Automobile a1 = Automobile(2000);
  }
  // distruttore chiamto in automatico, in uscita dallo scope
  {
    // memoria allocata dinamicamente con new
    // serve chiamare delete
    Automobile * aptr = new Automobile(3000);
    delete aptr;
    aptr = nullptr; // non punta a memoria non valida
    delete aptr; // non succede nulla perchè p è nullptr

    /**
     * fare delete aptr 2 volte, causa un comportamento indefinito
     * in quanto dangling pointer
     *
     *
     */
  }

  return 0;
}