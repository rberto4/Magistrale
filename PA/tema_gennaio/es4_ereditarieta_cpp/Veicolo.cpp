//
// Created by mitic on 25/01/2025.
//

class Veicolo {
    public :
      float km = 0;
      virtual void rifornisci(float q);
      virtual float getAutonia();
      virtual float getLivelloAlimentazione();
      virtual void percorri(float km);
      virtual ~Veicolo();
};

//~
