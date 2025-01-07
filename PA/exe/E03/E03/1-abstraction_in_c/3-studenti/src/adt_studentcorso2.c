/*
 ============================================================================
 Name        : adt_studentcorso2.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 
  Scrivi la definizione di un tipo di dato astratto
  Studente che rappresenta uno studente con un nome (max 30 caratteri), cognome e matricola.
  Scrivi le seguenti funzioni:
  - mkStudente(char *n, char *c) --> restituisce uno studente con nome n, cognome c e matricola pari a un numero progressivo che dipende dal numero di studenti esistenti
  - printStudente(studenteRef s) --> stampa cognome nome e matricola
  - deleteStudente(studenteRef* s) --> cancella uno studente
  Definire anche un tipo di dato astratto Corso che rappresenta un corso con nome (max 40 caratteri) e un elenco di studenti che frequentano il corso.
  Scrivi le seguenti funzioni:
  - mkCorso(char *n) --> restituisce un corso con lista studneti pari a NULL
  - addStudent(corsoRef c, studenteRef s) --> dato un corso e uno studente, aggiunge lo studente alla lista dei partecipanti al corso (il nuovo studente viene inserito in testa alla lista).
  Scrivere un main in cui si creano due studenti e un corso, si inserisce uno studente nel corso e si cancella uno studente
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include "studente.h"
#include "corso.h"

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
