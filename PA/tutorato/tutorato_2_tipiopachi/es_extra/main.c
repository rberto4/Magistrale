#include <stdlib.h>
#include <stdio.h>
#include "List.c"
#define arraySize(x) (sizeof(x)/sizeof(x[0]))

int main (){

    int a[] = {7,2,8};
    int b[] = {1,13,2,4};
    int size_a = arraySize(a);
    int size_b = arraySize(b);
    list l1 = costruttore(a, size_a);
    list l2 = costruttore(b, size_b);
    stampa(l1);
    stampa(l2);
    list l3 = concatena(l1, l2);
    stampa(l3);

    return 0;
}