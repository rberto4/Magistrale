#include<iostream>
#include"Auto.cpp"

using namespace std;

class Persona
{
  private:
    string wNome;
    string wCognome;
    Auto wAuto;
  public:
    Persona(string nome, string cognome, string targa) : wNome(nome), wCognome(cognome), wAuto(targa) {
      cout << "Persona istanziata con auto targata "<<targa<< endl;
    }
    void setNome(string nome){
      this->wNome = nome;
    }
    void setCognome(string cognome){
      this->wCognome = cognome;
    }
    string getNome(){
      return this->wNome;
    }
    string getCognome(){
      return this->wCognome;
    }
    string getTarga(){
      return this->wAuto.getTarga();
    }
    ~Persona(){
      cout <<"Persona "<<this->wNome+" "+this->wCognome<<" distrutta ..."<<endl;
    }
};