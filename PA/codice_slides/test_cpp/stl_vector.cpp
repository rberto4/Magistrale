//
// Created by mitic on 29/01/2025.
//

#include <iostream>
#include <vector>
using namespace std;

int main() {
    vector<int> A;
    if ( A.empty() ) cout << "A has size zero. " << endl;
    A.push_back(3); // A: 3
    A.push_back(-25); // A: 3 -25
    cout << "Size of A: " << A.size() <<endl; // size 2
    A.pop_back(); // A: 3
    cout << "Size of A: " << A.size()<<endl;
    A.push_back(780); // A: 3 780
    A.push_back(43); // A: 3 780 43

    for (int a : A) {
        cout << a << endl;
    }

    A.clear(); // toglie tutto
    A.resize(0); // idem

    A.push_back(3); // A: 3
    A.push_back(-25); // A: 3 -25

    for (int a : A) {
        cout << a << endl;
    }
    A.at(A.size() - 1 ) = 7; // A: 3 7
    for (int a : A) {
        cout << a << endl;
    }
}