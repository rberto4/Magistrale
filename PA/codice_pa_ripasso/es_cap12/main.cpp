#include <algorithm>
#include<iostream>
#include<string>
#include <memory>
#include <vector>
#include <iterator>

using namespace std;

// es1
class Engine {
public:
    void start() {
        cout << " Engine start" << endl;
    }

    void stop() {
        cout << " Engine stop" << endl;
    }
};

class Wheels {
public:
    void rotate() {
        cout << " Wheels rotating..." << endl;
    }

    void stopRotation() {
        cout << " Wheels stop rotation..." << endl;
    }
};

class Car : public Engine, public Wheels {
public:
    Car() {
        cout << " Car created!" << endl;
    }

    void drive() {
        cout << "\n Starting drive..." << endl;
        this->start();
        this->rotate();
        cout << endl;
    }

    void brake() {
        cout << "\n Braking..." << endl;
        this->stopRotation();
        this->stop();
        cout << endl;
    }
};

// es2 smart pointer

class Person {
private:
    string name;
public:
    Person(string n) {
        this->name = n;
    }
    void sayHello() {
        cout << "Ciao, sono "<<this->name<<endl;
    }
    ~Person() {
        cout << this->name<<" deleted"<<endl;
    }
};

class Account {
protected:
    double balance;
    Account(double b): balance(b) {
        cout << "Conto creato"<<endl;
        count << "- Bilancio : "<<this->getBalance()<<endl;
    }
    public:
    double getBalance() {
        return this->balance;
    }
    void deposit(double amount) {
        this->balance += amount;
    }
    void withdraw(double amount) {
        if (amount <= this->balance) {
            this->balance -= amount;
        }else {
            cout << "Il prelievo supera il bilancio"<<endl;
        }
    }
};

class SavingAccount : private Account {
    public:
    SavingAccount(double b): Account(b) {}
    double addInterest(double rate) {
        this->balance += balance*rate;
    }
};

int main() {

    SavingAccount sa (1000);
    //sa.deposit(500);
    Car c;
    c.drive();
    c.brake();

    Person p("Roberto");
    p.sayHello();

    {
        shared_ptr<Person> person = make_shared<Person>("john");
        shared_ptr<Person> person2 = person;
        cout<<"contatore di persone : "<<person.use_count()<<endl;
    }

    Person * p1 = new Person("Eddy");
    p1->sayHello();
    delete p1;


    vector<int> numbers;
    srand(time(0)); // Inizializza il generatore di numeri casuali

    for (int i = 0; i < 10; i++) {
        numbers.push_back(rand() % 100 + 1);
    }

    // 2️⃣ Stampare il vettore originale
    cout  << "Numeri generati : ";
    for (int num : numbers) {
        cout << num << " ";
    }
    cout << endl;

    // 3️⃣ Ordinare il vettore
    sort(numbers.begin(), numbers.end());

    cout << " Dopo l'ordinamento: ";
    for (int num : numbers) {
        cout << num << " ";
    }
    cout << endl;

    // 4️⃣ Trovare il massimo e il minimo
    int maxValue = *max_element(numbers.begin(), numbers.end());
    int minValue = *min_element(numbers.begin(), numbers.end());

    cout << " Valore massimo: " << maxValue << endl;
    cout << " Valore minimo: " << minValue << endl;

    // 5️⃣ Rimuovere i duplicati
    numbers.erase(unique(numbers.begin(), numbers.end()), numbers.end());

    cout << " Dopo la rimozione dei duplicati: ";
    for (int num : numbers) {
        cout << num << " ";
    }
    cout << endl;


    vector<string> names = {"emy", "John", "rob"};
    for (auto s:names) {
        cout << s<<endl;
    }
    return 0;
}
