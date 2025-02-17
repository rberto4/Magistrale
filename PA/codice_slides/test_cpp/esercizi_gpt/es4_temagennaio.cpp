//
// Created by mitic on 09/02/2025.
//

#include <iostream>
using namespace std;

class Veicolo {
private :
    int wKm = 0;

public:
    virtual void percorri(int km) = 0;

    void setKm(int wKm) {
        this->wKm += wKm;
    }

    int getKm() {
        return this->wKm;
    }
};

class VeicoloElettrico : virtual public Veicolo {
private :
    double wBatteria;

public:
    VeicoloElettrico(double wBatteria) : wBatteria(wBatteria) {
    }

    void percorri(int km) override {
        // implementazione consumo batteria
        double consumo = km / 3;
        if (this->wBatteria - consumo >= 0) {
            this->setKm(this->getKm() + km);
            this->wBatteria = this->wBatteria - consumo;
            cout << "--- Viaggio completato ---"<<endl;
            cout <<"- Batteria residua: "<<this->wBatteria<<"%"<<endl;
            cout <<"- Km percorsi: "<<this->getKm()<<endl;

        }else {
            cout << "Batteria insufficente per percorrere "<<km<<" km"<<endl;
        }

    };

    void caricaBatteria() {
        this->wBatteria = 100;
    }
};

class VeicoloACombustione : virtual public Veicolo {
private :
    double wSerbatoio;

public:
    VeicoloACombustione(double wSerbatoio) : wSerbatoio(wSerbatoio) {
    }

    void percorri(int km) override {
        double consumo = km / 15;
        if (this->wSerbatoio - consumo >= 0) {
            this->setKm(this->getKm() + km);
            this->wSerbatoio = this->wSerbatoio - consumo;
            cout << "--- Viaggio completato ---"<<endl;
            cout <<"- Carburante residuo: "<<this->wSerbatoio<<"L"<<endl;
            cout <<"- Km percorsi: "<<this->getKm()<<endl;

        }else {
            cout << "Serbatoio insufficente per percorrere "<<km<<" km"<<endl;
        }
    };

    void caricaACombustione(double litri) {
        this->wSerbatoio += litri;
    }
};

class VeicoloIbrido : public VeicoloElettrico, VeicoloACombustione {
    private :
    double wSerbatoio;
    double wBatteria;
public:
    VeicoloIbrido(double wb, double ws): VeicoloElettrico(wb), VeicoloACombustione(ws) {
    }

    void percorri(int km) override {
        // ---
        double consumoBatteria = km / 3.0;
        double consumoCarburante = km / 15.0;

        cout << "[Ibrido] Tentativo di viaggio per " << km << " km..." << endl;

        if (this->wBatteria >= consumoBatteria) {
            VeicoloElettrico::percorri(km);
        } else if (this->wSerbatoio >= consumoCarburante) {
            VeicoloACombustione::percorri(km);
        } else {
            cout << "[Ibrido] Nessuna energia sufficiente per il viaggio!" << endl;
        }
        this->setKm(this->getKm() + km);
    }
};

class GestioneAlimentazione {
public:
};


int main() {

    VeicoloElettrico ve (90);
    ve.percorri(100);
    ve.percorri(20);
    ve.percorri(120);

    VeicoloACombustione vc (30);
    vc.percorri(450);

    VeicoloIbrido vi (10,40);
    vi.percorri(50);
    return 0;
}
