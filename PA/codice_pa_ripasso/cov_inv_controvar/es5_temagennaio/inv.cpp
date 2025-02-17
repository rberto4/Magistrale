#include<iostream>
using namespace std;


//L'invarianza del tipo di ritorno significa che un metodo ridefinito in una
// classe derivata
// deve restituire esattamente lo stesso tipo della versione nella classe base.
class A {
  public:
    virtual int metodo (int n){
        return n*2;
    }
};
class B:public A {
public:
    double metodo (int n) override{
        return n*3.1;
    }
};

int main (){
  A a;
  B b;
  A * ptr;
  ptr = &b;
  cout<<ptr->metodo(5)<<endl;
    ptr = &a;
    cout<<ptr->metodo(5)<<endl;
// non abbiamo polimorfismo a runtime in quanto non è presente la keyword Virtual e override
  return 0;
}