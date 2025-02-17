
#include <iostream>
using namespace std;

/**
* ### **Dynamic Lookup of Virtual Functions in C++**

In C++, il concetto di **dynamic lookup** delle funzioni virtuali si riferisce al **meccanismo di risoluzione a runtime** del metodo da chiamare, basato sul **tipo effettivo dell'oggetto** a cui punta un puntatore o un riferimento.
Questo meccanismo consente il **polimorfismo a runtime**, cioè la capacità di chiamare metodi sovrascritti nelle classi derivate attraverso un puntatore o un riferimento alla classe base.

## **1. Come Funziona:**
- In C++, quando dichiari un metodo come `virtual` nella classe base, la funzione **non** viene legata al tipo della variabile a **compilazione**.
- Invece, la chiamata al metodo viene risolta a **runtime**, basandosi sul **tipo dell'oggetto a cui punta** il puntatore o riferimento.
- Questo processo utilizza una struttura chiamata **Virtual Table (vtable)**, che è una **tabella di puntatori a funzioni** mantenuta per ogni classe contenente metodi virtuali.

## **2. Esempio di Dynamic Lookup:**
```cpp
class Base {
public:
    // Dichiarazione di un metodo virtuale
    virtual void mostra() {
        cout << "Metodo di Base" << endl;
    }
};

class Derivata : public Base {
public:
    // Override del metodo virtuale
    void mostra() override {
        cout << "Metodo di Derivata" << endl;
    }
};

int main() {
    Base b;
    Derivata d;

    // Puntatore a Base
    Base *ptr;

    // Puntatore a un oggetto di Base
    ptr = &b;
    ptr->mostra();  // 🔴 Risolve a "Metodo di Base"

    // Puntatore a un oggetto di Derivata
    ptr = &d;
    ptr->mostra();  // 🔴 Risolve a "Metodo di Derivata" grazie alla dynamic lookup

    return 0;
}
```

### **Output:**
```
Metodo di Base
Metodo di Derivata
```

### **Cosa Succede:**
- La chiamata a `mostra()` viene risolta a **runtime**.
- Quando `ptr` punta a `Base`, viene chiamato `Base::mostra()`.
- Quando `ptr` punta a `Derivata`, viene chiamato `Derivata::mostra()`, anche se `ptr` è di tipo `Base*`.

---

## **3. Come Funziona la Virtual Table (vtable):**
- Quando dichiari una funzione come `virtual`, il compilatore crea una **vtable** per la classe.
- La vtable è una **tabella di puntatori** a funzioni virtuali.
- Ogni oggetto della classe contiene un **puntatore alla vtable** corrispondente al tipo effettivo dell'oggetto.
- Quando chiami una funzione virtuale, il compilatore:
  1. Accede al puntatore alla vtable dell'oggetto.
  2. Seleziona l'**entry corrispondente** al metodo chiamato.
  3. Salta alla funzione puntata dalla entry della vtable.

---

## **4. Polimorfismo a Runtime e Dynamic Lookup:**
- La **dynamic lookup** abilita il **polimorfismo a runtime**, cioè la possibilità di chiamare metodi derivati tramite puntatori o riferimenti alla base.
- Questo è utile nei contesti in cui non conosci il tipo effettivo dell'oggetto a **compilazione**.

### **Esempio:**
```cpp
void stampa(Base &ref) {
    ref.mostra();  // Dynamic lookup: chiama il metodo appropriato
}

int main() {
    Base b;
    Derivata d;

    stampa(b);  // 🔴 Stampa "Metodo di Base"
    stampa(d);  // 🔴 Stampa "Metodo di Derivata" grazie al polimorfismo a runtime

    return 0;
}
```

---

## **5. Importanza di `virtual` e `override`:**
- `virtual`: Dichiara il metodo come virtuale nella **classe base**, abilitando la dynamic lookup.
- `override`: Specifica che il metodo in **classe derivata** sovrascrive un metodo virtuale della base.
  - **Non è obbligatorio**, ma è **consigliato** perché consente al compilatore di verificare la corrispondenza con la firma del metodo della base.

### **Esempio Migliorato:**
```cpp
class Base {
public:
    virtual void mostra() {
        cout << "Metodo di Base" << endl;
    }
};

class Derivata : public Base {
public:
    void mostra() override {  // Uso di override per maggiore sicurezza
        cout << "Metodo di Derivata" << endl;
    }
};
```

---

## **6. Considerazioni sul Performance Overhead:**
- La **dynamic lookup** comporta un piccolo **overhead di performance** perché:
  - Implica un **accesso indiretto** tramite la vtable.
  - Richiede un **salto a un puntatore di funzione**.
- Tuttavia, il costo è generalmente **trascurabile** rispetto ai vantaggi del polimorfismo a runtime.

---

## **7. Riassunto e Regole Pratiche:**
- Usa `virtual` nei metodi della classe base che vuoi sovrascrivere nelle derivate.
- Usa `override` per migliorare la leggibilità e la sicurezza del codice.
- La **dynamic lookup** si applica **solo** a chiamate fatte tramite **puntatori** o **riferimenti**.
  - Le chiamate dirette su oggetti vengono risolte **staticamente a compilazione**.
- Se **non** dichiari `virtual`, la chiamata viene risolta **staticamente** basandosi sul tipo della variabile (non dell'oggetto).

---

## **8. Quando Usarlo:**
- Quando vuoi implementare un **comportamento polimorfico** tra classi derivate e la base.
- Quando vuoi chiamare **metodi derivati** tramite un **puntatore** o **riferimento** alla base.
- Utile nei **design pattern** come il **Factory Method**, **Strategy**, e **Template Method**.

---

Vuoi vedere altri esempi o chiarimenti su come funziona la vtable o altre applicazioni pratiche? 😊
*/


class Base {
public:
    // Dichiarazione di un metodo virtuale
    virtual void mostra() {
        cout << "Metodo di Base" << endl;
    }
};

class Derivata : public Base {
public:
    // Override del metodo virtuale
    void mostra() override {
        cout << "Metodo di Derivata" << endl;
    }
};

int main (){
   Base * ptr;
   ptr = new Derivata();
    ptr = new Base();
   ptr->mostra();

  return 0;
}