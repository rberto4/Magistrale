

#include <stdio.h>
#include <stdlib.h>
#include "studente.c"
#include "corso.c"

int main(void) {
	studenteRef s1 = mkStudente("matteo pasquale mario giovanni","verdi"); //il nome viene stampato a met� perch� la lunghezza max � 10
	printStudente(s1);
	//deleteStudente(&s1);
	studenteRef s2 = mkStudente("mario","bianchi");
	printStudente(s2);
	deleteStudente(&s2);
	corsoRef c = mkCorso("info 3");
	addStudent(c,s1);
	return EXIT_SUCCESS;
}

