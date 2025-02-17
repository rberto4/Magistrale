#include <iostream>
#include "Spettatore.cpp"

using  namespace std;
class StampaSpettatoreVisitor : public Visitor {
public:
  void visit(Tifoso& t) override{
    cout<<"Informazioni tifoso : "<<t.getCredenziali()<<endl;
    cout <<"Posto : "<<t.getPosto()<<endl;
    cout <<"Tribuna : "<<t.getTribuna()<<endl;
  }
  void visit(Vip& v) override {
    cout<<"Informazioni tifoso : "<<v.getCredenziali()<<endl;

  }
  void visit(Giornalista& g)  override {
    cout<<"Informazioni giornalista : "<<g.getCredenziali()<<endl;
    cout<<"Giornale : "<<g.getTestata()<<endl;
  }
};