/*
 ============================================================================
 Name        : CENTURELLI_VALENTINA_OPACHI.c
 Author      : Valentina Centurelli
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include "bitvector.h"

#include <stdio.h>
#include <stdlib.h>

// NOTA

/*
 * Implementazione dei dati
 */

struct vettore {
	int dimensione;
	int * contenuto; // ho scelto int come tipo primitivo per l'array -> 0 false, 1 -> true
};

bitvector make_vector(int n) {

	// alloco lo spazio per la struttura dati
	bitvector v = (bitvector) (malloc(sizeof(struct vettore)));

	// alloco lo spazio per l'array
	v->contenuto = malloc(sizeof(int) * n);
	// inizializzo il contenuto
	int i;
	for (i = 0; i < n; i++) {
		v->contenuto[i] = 0;
	}

	// salvo i dati della dimensione
	v->dimensione = n;

	return v;
}

char * tostring(bitvector v) {
	char* ris = malloc((v->dimensione +1)* sizeof(char));
	int i;
	for (i = 0; i < v->dimensione; i++) {
		if (v->contenuto[i] == 0)
			ris[i] = '0';
		else
			ris[i] = '1';
	}
	ris[i] = '\0';
	return ris;

}

void delete_vector(bitvector v) {

	// libero l'array
	free(v->contenuto);
	// libero tutto
	free(v);

}

// se sono uguali faccio la and, se sono diversi
bitvector and(bitvector b1, bitvector b2) {

	bitvector ris = make_vector(b1->dimensione);
	int i;

	if (b1->dimensione == b2->dimensione) {
		for (i = 0; i < ris->dimensione; i++) {
			ris->contenuto[i] = b1->contenuto[i] * b2->contenuto[i];
		}
	} else {
		printf("AND: Dimensioni non uguali \n");
	}

	return ris;

}

// se sono uguali faccio la and, se sono diversi ritorno il primo
bitvector or(bitvector b1, bitvector b2) {

	bitvector ris = make_vector(b1->dimensione);
	int i;

	if (b1->dimensione == b2->dimensione) {
		for (i = 0; i < ris->dimensione; i++) {
			// metto il modulo così vario tra 0 e 1
			ris->contenuto[i] = (b1->contenuto[i] + b2->contenuto[i]) % 2;
		}
	} else {
		printf("AND: Dimensioni non uguali \n");
	}

	return ris;

}

bitvector not(bitvector b) {

	// alloco lo spazio per la struttura dati
	bitvector ris = make_vector(b->dimensione);

	// inizializzo il contenuto -> se b aveva 0 allora ris ha 1 e viceversa
	int i;
	for (i = 0; i < b->dimensione; i++) {
		if (b->contenuto[i] == 0)
			ris->contenuto[i] = 1;
		else
			ris->contenuto[i] = 0;
	}

	return ris;

}

