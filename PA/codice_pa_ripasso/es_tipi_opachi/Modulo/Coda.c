#include <stdio.h>

int * array;
int size;

void mkCoda(){
  array = (int*)malloc(size*sizeof(int));
  size = 0;
}

void insert (int value){
  size++;
  array = (int*) realloc(array, size);
  printf("Insert ");
}

void print(){
  for (int i = 0; i < size; i++){
    printf("%d ", array[i]);
  }
}