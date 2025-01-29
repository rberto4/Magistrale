#include <iostream>
using namespace std;

int main() {

    int s = 2000;
    for (int i = 0; i<10; i++) {
        s = s + (0.05*s);
    }
    cout << s << endl;
    return 0;
}
