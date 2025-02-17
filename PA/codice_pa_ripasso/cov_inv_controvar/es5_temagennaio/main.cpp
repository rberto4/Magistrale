#include <iostream>
#include <iostream>
using namespace std;


class A {
    public:
    virtual void metodo () {
        cout<<"Metodo da classe A"<<endl;
    }
    // overloading
    void metodo(string value) {
        cout<<"Metodo che stampa quello che passo : "<<value<<endl;
    }

    A * metodoCovarianza () {
        cout << "Crea oggetto di tipo A" << endl;
        return new A();
    }
    virtual void metodoControvarianza (A * b){

    }
};

class B : public A {
    public:
    void metodo  ()  {
        cout <<"Metodo da classe B"<<endl;
    }
    B * metodoCovarianza () {
        cout << "Crea oggetto di tipo B" << endl;
        return new B();
    }



};
class C {

};

void metodoInvarianza(A * a) {
    cout <<"Test invarianza"<<endl;
}

int main () {
    A a;
    B b;
    C c;
    a.metodo();
    a.metodo("Roberto");
    A *ptr;
    ptr = &a;
    ptr->metodo();
    ptr = &b;
    ptr->metodo();
    ptr->metodo("ciao");
    ptr = &a;
    ptr->metodoCovarianza();
    ptr = &b;
    ptr->metodoCovarianza();

    return 0;
}