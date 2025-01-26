//
// Created by mitic on 25/01/2025.
//
#include "Veicolo.cpp"

class VeicoloElettrico : public Veicolo{
  private:
    float wBatteria;
    public:
    VeicoloElettrico(float batteria){
      this->wBatteria = batteria;
    }
};


