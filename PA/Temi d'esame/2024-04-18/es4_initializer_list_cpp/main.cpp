#include<iostream>
#include"PersonaStack.cpp"
#include"PersonaHeap.cpp"
#include"PersonaReferenceHeap.cpp"

using namespace std;

int main() {

    cout <<" --- main ---\n"<<endl;
    PersonaStack p1 = PersonaStack("roby", "berto", "FW89IO");
    PersonaHeap p2 = PersonaHeap("mario", "rossi", "GT67S2");
    PersonaReferenceHeap p3 = PersonaReferenceHeap("giuseppe", "verdi", "YT78CV");

    return 0;
}
