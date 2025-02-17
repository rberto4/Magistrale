//
// Created by mitic on 10/02/2025.
//


#include <iostream>
using namespace std;

class Account {
protected:
    double balance;

    Account(double b): balance(b) {
        cout << "Conto creato" << endl;
        cout << "- Bilancio : " << this->getBalance() << " Euro" << endl;
    }

public:
    double getBalance() {
        return this->balance;
        cout << "Saldo attuale :" << this->balance << "€" << endl;
    }

    void deposit(double amount) {
        this->balance += amount;
        cout << "Deposito di " << amount << " Euro" << endl;
    }

    void withdraw(double amount) {
        if (amount <= this->balance && this->balance > 0) {
            this->balance -= amount;
            cout << "Prelievo di " << amount << " Euro" << endl;
        } else {
            cout << "Saldo insufficente" << endl;
        }
    }
};

class SavingAccount : public Account {
private:
    int rate;

public:
    SavingAccount(double b, int r): Account(b), rate(r) {
        cout << "Tasso di interesse del conto : " << this->rate << "%" << endl;
    }

    double addInterest() {
        double interest = this->getBalance() * this->rate / 100;
        this->balance += interest;
        cout << "Interesse aggiunto (" << interest << ")" << ", saldo attuale : " << this->balance << " Euro" << endl;
    }
};

int main() {
    SavingAccount s1(1000.0, 5);
    s1.deposit(500);
    s1.withdraw(300);
    s1.addInterest();
    cout << s1.getBalance();
    return 0;
}
