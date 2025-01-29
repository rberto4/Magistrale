//
// Created by mitic on 27/01/2025.
//
#include <iostream>
using namespace std;

class Time {
public:
    Time() {
        hour = 0;
        minute = 0;
        second = 0;
    };

    void setTime(int h, int m, int s) {
        hour = (h >= 0 && h < 24) ? h : 0;
        minute = (m >= 0 && m < 60) ? m : 0;
        second = (s >= 0 && s < 60) ? s : 0;
    };

    void printTime() {
        // scrivere senza this-> si fa accesso implicito, in questo caso
        // essendo che non ho variabili locali in scope, implicitamente si irferisce a hour
        // dell'oggetto
        cout << (this->hour < 10 ? "0" : "") << this->hour << ":";
        cout << (minute < 10 ? "0" : "") << minute << ":";
        cout << (second < 10 ? "0" : "") << second << endl;
    };

private:
    int hour;
    int minute;
    int second;
};


int main() {
    Time t = Time();
    t.printTime(); // 00:00:00
    t.setTime(19, 130, 56);
    t.printTime(); // 19:00:56
    return 0;
}
