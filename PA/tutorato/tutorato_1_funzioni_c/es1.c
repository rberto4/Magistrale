#include <stdio.h>
#include <stdlib.h>
#define ARRAY_SIZE(a) (sizeof(a) / sizeof((a)[0]))


// funzioni dichiarate

int COUNT_GT(int a1[], int a2[], int dim1, int dim2);
int COUNT_GT_RECORSIVE(int a1[], int a2[], int dim1 , int dim2, int index);
int COUNT_GT_TAILRECORSIVE(int a1[], int a2[], int dim1, int dim2, int index, int count);
int MIN (int a, int b);

// main
int main(int argc, char const *argv[]) {
    int a1[] = {2, 2, 3,6,8};
    int a2[] = {1, 6, 1, 4};
    int dim1 = ARRAY_SIZE(a1);
    int dim2 = ARRAY_SIZE(a2);
    // Chiamata alla funzione
    printf("Iterativa -> I valori di a1, maggiori di a2 sono : %d\n", COUNT_GT(a1, a2, dim1, dim2)); 
    printf("Ricorsiva -> I valori di a1, maggiori di a2 sono : %d\n", COUNT_GT_RECORSIVE(a1, a2, dim1, dim2, 0)); 
    printf("Ricorsiva  tail -> I valori di a1, maggiori di a2 sono : %d\n", COUNT_GT_TAILRECORSIVE(a1, a2, dim1, dim2, 0, 0)); 

    return 0;
}

// funzioni definite

// serve per ciclare sulla dimensione minima tra i due array, altrimenti si verificherebbe un errore


int MIN(int a, int b)
{
  return (a < b) ? a : b; 
}

int COUNT_GT(int a1[], int a2[], int dim1, int dim2){
    
    int count = 0;
    for (int i = 0; i < MIN(dim1, dim2); i++){
        if (a1[i] > a2[i]){
            count++;
        }
    }
    // aggiungo gli scarti tra le dimensioni del primo e del secondo array. 
    // se il secondo array è più lungo del primo, allora aggiungo la differenza tra le due dimensioni
    // al primo array.
    // se il valore manca, viene considerato come 0 e quindi viene contato minore.
    if (dim1 > dim2){
        count += dim1 - dim2;
    }

    return count;
}

int COUNT_GT_RECORSIVE(int a1[], int a2[], int dim1 , int dim2, int index){
    if (index == MIN(dim1, dim2)){
         if (dim2 < dim1){
            return dim1 - dim2;
        }else{
            return 0;
        }
    }
   
    if (a1[index] > a2[index]){
        return 1 + COUNT_GT_RECORSIVE(a1, a2, dim1, dim2, index + 1);
    }else{
         return 0 + COUNT_GT_RECORSIVE(a1, a2, dim1, dim2, index + 1);
    }
   
}

int COUNT_GT_TAILRECORSIVE(int a1[], int a2[], int dim1 , int dim2, int index, int count){
    if (index == MIN(dim1, dim2)){
         if (dim2 < dim1){
            return count + (dim1 - dim2);
        }else{
            return count;
        }
    }
   
    if (a1[index] > a2[index]){
        return COUNT_GT_TAILRECORSIVE(a1, a2, dim1, dim2, index + 1, count + 1);
    }else{
         return COUNT_GT_TAILRECORSIVE(a1, a2, dim1, dim2, index + 1, count);
    }
   
}