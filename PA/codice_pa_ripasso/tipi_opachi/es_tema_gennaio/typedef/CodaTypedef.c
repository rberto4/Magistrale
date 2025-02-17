#include "CodaTypedef.h"
#include <stdio.h>
#include <stdlib.h>

Codatd * mkCodaTypedef(){
  Codatd * c= (Codatd*)malloc(sizeof(struct CodaTypedef));
  c->size = 0;
  c->array = NULL;
  return c;
}

void insert(Codatd *c,int value){
  // aggiorno il riferimento alla capacità
  c->size += 1;
  // aggiorno lo spazio per il valore in piu
  c->array = (int*)realloc(c->array,c->size*sizeof(int));
  // nella posizione size - 1, nuova, metto il valore passato
  c->array[c->size-1] = value;
}

void print(Codatd * c){
  for(int i=0;i<c->size;i++){
    printf("%d ",c->array[i]);
  }
}

void del(Codatd *c){
  free(c->array);
  free(c);
}