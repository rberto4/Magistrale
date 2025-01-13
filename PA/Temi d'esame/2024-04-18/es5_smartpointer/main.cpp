#include <iostream>
#include <memory>
#include <string>
#include "Persona.cpp"

using namespace std;

int main() {

    /**
     * RAW POINTER
     * Operatori new e delete gestiamo l'allocazione e deallocazione della memoria
     * se esco dal blocco e non metto delete, la memoria non viene deallocata.
     */
    {
        Persona * p1 =new Persona("roby","bertocchi","H763HC");
        cout<<"Persona : \n";
        cout<<" - Credenziali : "<<p1->getNome()+" "+p1->getCognome()<<endl;
        cout<<" - Targa auto : "<<p1->getTarga()<<endl;
        delete p1;
    }
    cout << " ----------------------------------------------------- "<<endl;
    /*
     * Usando invece uno smart pointer la gestione della memoria avviene in modo automatico. Lo spazio viene automaticamente
     * allocato entrando nel blocco e deallocato uscendone.
     *
     * In questo primo caso usiamo uno unique pointer, la cui ownership NON può essere condivisa da più oggetti allo stesso momento
     */
    {
        unique_ptr<Persona> p2(new Persona("Mario","Rossi","TY68M9"));
        cout<<"Persona : \n";
        cout<<" - Credenziali : "<<p2->getNome()+" "+p2->getCognome()<<endl;
        cout<<" - Targa auto : "<<p2->getTarga()<<endl;
    }
    cout << " ---------------------------------------- ------------- "<<endl;

    {
        shared_ptr<Persona> p3(new Persona("Giuseppe","Verdi","GHIOL7"));
        shared_ptr<Persona> copia_di_p3(p3);
        cout <<"Copie di P3 : "<<p3.use_count()<<endl;
        cout <<"Faccio reset della copia ..."<<endl;
        copia_di_p3.reset();
        cout <<"Copie di P3 : "<<p3.use_count()<<endl;
        cout<<"Persona : \n";
        cout<<" - Credenziali : "<<p3->getNome()+" "+p3->getCognome()<<endl;
        cout<<" - Targa auto : "<<p3->getTarga()<<endl;
    }


}