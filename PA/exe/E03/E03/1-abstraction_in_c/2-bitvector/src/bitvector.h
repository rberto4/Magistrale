/*
 ============================================================================
 Name        : CENTURELLI_VALENTINA_OPACHI.h
 Author      : Valentina Centurelli
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>

/*
 * In questo file definisco:
 *
 * - il tipo di dato opacho (con typedef)
 * - costruttore
 * - distruttore
 * - altre funzioni (and, or, not, tostring)
 */

// definisco il tipo
typedef struct vettore * bitvector;

// costruttore e distruttore
bitvector make_vector(int);
void delete_vector(bitvector);

// altri metodi
char * tostring(bitvector);
bitvector and(bitvector, bitvector);
bitvector or(bitvector, bitvector);
bitvector not(bitvector);
