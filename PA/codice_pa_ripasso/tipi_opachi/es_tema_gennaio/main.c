#include <stdio.h>
#include "typedef/CodaTypedef.h"
#include "modulo/CodaModulo.h"

int main() {
    Codatd * c_typedef = mkCodaTypedef();
    printf("Valore size, preso direttamente dalla struct : %d\n", c_typedef->size);
    insert(c_typedef,10);
    printf("Valore size, preso direttamente dalla struct : %d\n", c_typedef->size);
    print(c_typedef);


    Codam c_modulo = mkCodaModulo();
    // ERRORE NON POSSO ACCEDERE DIRETTAMENTE- INCAPSULAMENTO MIGLIORE
    //  printf("Valore size, preso direttamente dalla struct : %d\n", c_modulo->size);
    printModulo(c_modulo);
    insertModulo(c_modulo,14);
    insertModulo(c_modulo,15);
    insertModulo(c_modulo,16);
    printModulo(c_modulo);
   // del(c_modulo);
    return 0;
}

