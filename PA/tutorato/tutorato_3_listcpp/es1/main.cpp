#include <iostream>
#include "AutomobileStack.cpp"
#include "AutomobileHeap.cpp"
#include "AutomobileReferenceHeap.cpp"
using namespace std;

int main(){
    AutomobileStack a = AutomobileStack(2000);
    cout << "Automobile - stack - instanziata " << endl;
    
    AutomobileHeap b = AutomobileHeap(1200);
    cout << "Automobile - heap - instanziata " << endl;
    
    AutomobileReferenceHeap c = AutomobileReferenceHeap(1500);
    cout << "Automobile - reference - instanziata " << endl;
    return 0;
}