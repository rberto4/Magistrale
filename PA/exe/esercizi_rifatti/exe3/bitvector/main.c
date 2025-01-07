#include<stdlib.h>
#include<stdio.h>
#include "bitvector.c"

int main(void) {

	bitvector v = make_vector(3);
	printf("Stampo il dato appena creato %s \n", tostring(v));

	// provo i metodi

	// not -> creo un vettore, poi lo converto subito a  not e lo stampo
	bitvector vNot = not(make_vector(3));
	printf("Provo il not. not(000) = %s \n", tostring(vNot));

	// provo and e or
	printf("Provo con and. and(000, 111) = %s \n", tostring(and(vNot, v)));
	printf("Provo con or. or(000, 111) = %s \n", tostring(or(vNot, v)));

	// provo con dimensioni diverse
	printf("Errore AND = %s \n", tostring(and(make_vector(5), v))); // stampa 5 zeri
	printf("Errore OR = %s \n", tostring(or(make_vector(5), v))); // stampa 5 zeri

	delete_vector(vNot);
	delete_vector(v);

	return EXIT_SUCCESS;
}
