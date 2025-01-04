#include <iostream>
using namespace std;

class Proiettile {
   
    public:
        int calibro;
        Proiettile (int c){
            this->calibro = c;
            cout << "Instanziato proiettile di calibro " << c << "mm" << endl;
        }
};

class PistolaStuck {
    public:
        Proiettile *p;
        PistolaStuck(int c, int n){
            
            for (int i = 0; i<n; i++){
                this -> p[i] = Proiettile(c);   
            }
            cout << "Instanziata pistola con " << n << " proiettili di calibro " << c << "mm" << endl;
        }
};

int main(){
    PistolaStuck p = PistolaStuck(9,12);
    return 0;
}