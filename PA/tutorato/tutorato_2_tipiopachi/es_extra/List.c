#include <stdlib.h>
#include <stdio.h>
#include "List.h"

struct ListType{
    int *array;
    int len;
};

list costruttore(int a[], int len){
    list l = malloc(sizeof(list));
    l->array = malloc(sizeof(int) * len);
    l->len = len;
    for (int i = 0; i<len; i++){
        l->array[i] = a[i];
    }
    return l;
}

void cancella(list l){
    free(l->array);
    free(l);
}

void stampa(list l){
    printf("%s", "Array :");
    for (int i = 0; i<l->len; i++){
        printf("%d ", l->array[i] );
    }
    printf("\n");
}

list concatena(list l1, list l2){
    int lenTemp = l1->len + l2->len;
    list temp = malloc(sizeof(list));
    temp->array = malloc(sizeof(int) * lenTemp);
    temp-> len = lenTemp;
    
    while (lenTemp > 0){
        if(lenTemp >= l1->len){
            temp->array[lenTemp] = l2->array[lenTemp - l1->len];
            lenTemp--;
        }else{
            temp->array[lenTemp] = l1->array[lenTemp];
            lenTemp--;
        }
    }
    temp->array[0] = l1->array[0];
    return temp;
}