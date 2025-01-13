#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "Set.h"

struct Set {
  int* wArray;
  int wLen;
};

int * ordinaArray (int * array, int len) {
  for (int i = 0; i < len; i++) {
    for (int j = 0; j < len; j++) {
      if (array[i] < array[j]) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
      }
    }
  }
  return array;
}

bool isAlredyPresent (int * array, int len, int value){
 	 for (int i = 0; i < len; i++) {
  		 if (array[i] == value) {
     		 return true;
 		 }
	}
        return false;
}

set mkSet (int * array, int len) {
  set s = (set) malloc (sizeof(set));
  s->wArray = (int*) malloc (sizeof(int)*1);
  s->wLen = 0;

  for (int i = 0 ; i<len ; i++){
    if(!isAlredyPresent(s->wArray,len,array[i])){
      s->wLen += 1;
      s->wArray = (int*) realloc(s->wArray, sizeof(int) * (s->wLen));
      s->wArray[s->wLen - 1] = array[i];
    }
  }
 // s->wArray = ordinaArray(s->wArray, s->wLen);
  return s;
}


set unionSet(set s1, set s2) {
  set s = (set) malloc (sizeof(set));
  s->wArray = (int*) malloc (sizeof(int) * (s1->wLen));
  s->wLen = s1->wLen;
  // il primo set lo carico tutto, non posso avere copie al suo interno per come viene istanziato.
  for (int i = 0; i < s1->wLen; i++) {
    s->wArray[i] = s1->wArray[i];
  }

  for (int i = 0; i < s2->wLen; i++) {
    if (!isAlredyPresent(s->wArray,s->wLen,s2->wArray[i])) {
      s->wLen += 1;
      s->wArray = (int*) realloc(s->wArray, sizeof(int) * (s->wLen));
      s->wArray[s->wLen - 1] = s2->wArray[i];
    }
  }
  return  s;
}

void printSet(set s) {
  printf("Dimensioni array : %d\n" , s->wLen);
  printf("Elementi array : ");
  for (int i = 0; i < s->wLen; i++) {
    printf("%d ", s->wArray[i]);
  }
  printf("\n");
}

void deleteSet (set s) {
  free(s->wArray);
  free(s);
  printf("Set deallocato...\n");
}

// modifco traccia per esercizio -> lo faccio anche ordinato l'array

