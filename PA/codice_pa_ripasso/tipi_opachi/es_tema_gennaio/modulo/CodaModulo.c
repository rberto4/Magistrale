#include "CodaModulo.h"
#include <stdio.h>
#include <stdlib.h>

struct CodaModulo {
  int * array;
  int size;
};

Codam  mkCodaModulo(){
  Codam  coda = (Codam *)malloc(sizeof(struct CodaModulo));
  (coda)->size = 0;
  (coda)->array = NULL;
  return coda;
}

void insertModulo  (Codam coda, int value){
  coda->size += 1;
  coda->array = (int *) realloc(coda->array, coda->size * sizeof(int));
  coda->array[coda->size - 1] = value;
}

void delModulo  (Codam coda){
  free(coda->array);
  free(coda);
}

void printModulo (Codam coda){
  for (int i=0; i<coda->size; i++){
    printf("%d ", coda->array[i]);
  }
}