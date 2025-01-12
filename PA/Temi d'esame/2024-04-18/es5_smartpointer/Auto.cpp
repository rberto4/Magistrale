#include<iostream>
#include<string>

using namespace std;

class Auto {
 private:
    string wTarga;
 public:

    Auto(string targa){
        this -> wTarga = targa;
        cout <<"istanziata auto con targa : " +  wTarga << endl;
    }
    string getTarga(){
      return this->wTarga;
    }
    void setTarga(string targa){
      this->wTarga = targa;
    }
    ~Auto(){
      cout<<"Auto targata "<< this->wTarga<< " distrutta..."<<endl;
    }
 };