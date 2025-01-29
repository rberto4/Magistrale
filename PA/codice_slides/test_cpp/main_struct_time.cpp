#include <iostream>
using namespace std;

struct Time {
    int hour;
    int minute;
    int second;
};

void printStandard (const Time &t) {
    cout << (t.hour < 10 ? "0" : "") << t.hour << ":";
    cout << (t.minute < 10 ? "0" : "") << t.minute << ":";
    cout << (t.second < 10 ? "0" : "") << t.second;
}


int main() {

    Time t;
    t.hour = 12;
    t.minute = 10;
    t.second = 14;
    printStandard(t);
    return 0;
}
