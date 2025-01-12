#include<iostream>
#include<string>

using namespace std;

class Auto {
  public:
    string wTarga;
    Auto(string targa){
        this -> wTarga = targa;
        cout <<"istanziata auto con targa : " +  wTarga << endl;
    }
 };