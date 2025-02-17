#include <iostream>

class Giornalista;
class Vip;
class Tifoso;
using namespace std;

class Visitor {
public:
    virtual void visit(Tifoso& tifoso) = 0;
    virtual void visit(Vip& vip) = 0;
    virtual void visit(Giornalista& giornalista) {
    }

    virtual ~Visitor() {}
};
