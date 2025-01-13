#include<iostream>
#include "Motore.cpp"
#include "Bagagliaio.cpp"

using namespace std;

class Automobile{
    private:
      string wNome;
      Motore* wMotore;
      Bagagliaio* wBagagliaio;
    public:
      Automobile (string nome , int cilindrata, int capacita){
          this->wNome = nome;
          this->wMotore = new Motore(cilindrata);
          this->wBagagliaio = new Bagagliaio(capacita);
          cout << "Automobile creata " << endl;
          cout <<" - Cilindrata motore : " << cilindrata<<"cc"<< endl;
          cout <<" - Capacita bagagliaio : " << capacita<<"L"<< endl;
      };
    ~Automobile(){
        this->wMotore->~Motore();
        this->wBagagliaio->~Bagagliaio();
        cout<<"Automobile distrutta..."<<endl;
    };
};