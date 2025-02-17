#include <iostream>
#include "StampaSpettatoreVisitor.cpp"
using namespace std;

class Tifoso;
class Vip;
class Giornalista;

class StampaSpettatoreVisitor;
int main() {
    Tifoso t1 = Tifoso("Roby","Bertocchi");
    t1.setPosto(2,23);
    StampaSpettatoreVisitor spettatore_visitor = StampaSpettatoreVisitor();

    spettatore_visitor.visit(t1);
    return 0;
}
