#include <stdio.h>
#include <stdlib.h>
#include "OrderedSet.h"
#include <stdbool.h>

struct OrderedSetType {
    int *arr;
    int size;
};


int *ordinaArray(int *arr, int size) {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            if (arr[i] < arr[j]) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    }
    return arr;
}

bool alredyPresent (int *a1, int value, int size) {
    for (int i = 0; i < size; i++) {
        if (a1[i] == value) {
            return true;
        }
    }
    return false;
}
orderedSet mkOrderedSet(int *arr, int size) {
    orderedSet ords = (orderedSet) malloc(sizeof(struct OrderedSetType));
    ords->arr = NULL; // Array inizialmente vuoto
    ords->size = 0;

    for (int i = 0; i < size; i++) {
        if (!alredyPresent(ords->arr, arr[i], ords->size)) {
            // Aggiungere un elemento unico
            ords->arr = realloc(ords->arr, sizeof(int) * (ords->size + 1));
            if (ords->arr == NULL) {
                printf("Errore: riallocazione memoria fallita.\n");
                free(ords);
                return NULL;
            }
            ords->arr[ords->size] = arr[i];
            ords->size++;
        }
    }

    // Ordina l'array risultante
    ordinaArray(ords->arr, ords->size);
    return ords;
}

orderedSet unionSet(orderedSet s1, orderedSet s2) {
    orderedSet ords = (orderedSet) malloc(sizeof(struct OrderedSetType));
    ords->arr = NULL;
    ords->size = 0;

    // Aggiungi elementi di s1
    for (int i = 0; i < s1->size; i++) {
        if (!alredyPresent(ords->arr, s1->arr[i], ords->size)) {
            ords->arr = realloc(ords->arr, sizeof(int) * (ords->size + 1));
            ords->arr[ords->size] = s1->arr[i];
            ords->size++;
        }
    }

    // Aggiungi elementi di s2
    for (int i = 0; i < s2->size; i++) {
        if (!alredyPresent(ords->arr, s2->arr[i], ords->size)) {
            ords->arr = realloc(ords->arr, sizeof(int) * (ords->size + 1));
            ords->arr[ords->size] = s2->arr[i];
            ords->size++;
        }
    }

    // Ordina l'array risultante
    ordinaArray(ords->arr, ords->size);
    return ords;
}


void printSet(orderedSet s) {
    for (int i = 0; i < s->size; i++) {
        printf("%d ", s->arr[i]);
    }
    printf("\n");
}

void deleteSet(orderedSet s) {
    free(s->arr);
    free(s);
}
