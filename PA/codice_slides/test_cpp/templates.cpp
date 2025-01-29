#include <iostream>
using namespace std;

template<class T>
class Complex {
private:
    T re;
    T im;
public:
    Complex (T re, T im) : re(re), im(im) {}
    T getRe(){return re;}
    T getIm(){return im;}
};

int main() {
    Complex<int> c1 = Complex<int>(10, 45);
    cout <<"Istanziato numero complesso: "<< c1.getRe()<<"+j"<<c1.getIm()<< endl;
    Complex<float> c2 = Complex<float>(7.6, 3.91);
    cout <<"Istanziato numero complesso: "<< c2.getRe()<<"+j"<<c2.getIm()<< endl;
    Complex<char*> c3 = Complex<char*>("1.0", "6");
    cout <<"Istanziato numero complesso: "<< c3.getRe()<<"+j"<<c3.getIm()<< endl;
}