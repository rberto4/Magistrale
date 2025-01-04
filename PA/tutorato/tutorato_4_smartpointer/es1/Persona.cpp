#include <iostream>
#include <memory>
#include <string>
using namespace std;

class Persona {
    private:
        string nome;
    public:
        Persona(string n){
            this -> nome = n;
            cout << "Creata persona di nome " <<n<< endl;
        }
        string getNome(){
            return this -> nome;
         }
        ~Persona(){
            cout << "Distrutta persona di nome " << this -> getNome() << endl;
        }
};

class Studente : public Persona {
    private:
        string matricola;
    public:
        Studente(string n, int m) : Persona(n){
            this -> matricola = m;
            cout << "Creata studente di nome " << n << " con matricola " << m << endl;
        }
    string getMatricola(){
        return this -> matricola;
    }
    ~Studente(){
        cout << "Distrutto studente di nome " << this -> getNome() << " con matricola " << this -> getMatricola() << endl;
    }
};


int main(int argc, char const *argv[]){

    // row pointer
    Studente *s1 = new Studente("Roberto", 1060957);
    delete s1;
    cout << endl;

    // smart pointer
   {
        unique_ptr<Studente> s2(new Studente("Roberto", 1060957));  
        cout << endl;
   }
   // s2 esce dallo scope e distrugge l'oggetto

   {
        shared_ptr<Studente> s3(new Studente("Roberto", 1060957));  
        cout << endl;
        shared_ptr<Studente> s4(s3);
        cout << "Esistono " << s3.use_count() << " puntatori a s3" << endl;
   }


    return 0;
}