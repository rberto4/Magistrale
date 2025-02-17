 #include <stdio.h>
#include <stdlib.h>
#include "CounterModulo.h"

int main (void){
    int v1;
    int v2;
    CounterModulo c1, c2;
    reset(&c1);
    reset(&c2);
    v1 = getValue(c1);
    v2 = getValue(c2);
    return EXIT_SUCCESS;
}