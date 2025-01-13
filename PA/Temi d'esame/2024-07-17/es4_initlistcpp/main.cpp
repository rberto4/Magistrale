#include <iostream>
#include "AutomobileStack.cpp"
#include "AutomobileHeap.cpp"
#include "AutomobileReferenceHeap.cpp"

using namespace std;

int main() {

    AutomobileStack a1 = AutomobileStack("Fiat panda", 60, 1000);
    AutomobileHeap a2 = AutomobileHeap("Suzuki swift", 90, 1200);
    AutomobileReferenceHeap a3 = AutomobileReferenceHeap("Porche 911", 40, 4200);

    cout << "Hello, World!" << endl;
}