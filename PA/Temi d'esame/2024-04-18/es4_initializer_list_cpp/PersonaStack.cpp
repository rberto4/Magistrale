#include<iostream>
#include<string>
#include"Auto.cpp"

using namespace std;

class PersonaStack{
  private:
    string wNome;
    string wCognome;
    Auto wAuto;
  public:
    PersonaStack(string nome, string cognome, string targa) : wNome(nome), wCognome(cognome), wAuto(targa)
    {
      cout<<"Persona istanziata nello stack, avente un auto targata : " << targa <<endl;
    };
};