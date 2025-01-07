
#ifndef CORSO_H_
#define CORSO_H_
#include "studente.h"

typedef struct corso * corsoRef;

corsoRef mkCorso(char *n);

void addStudent(corsoRef c, studenteRef s);

#endif /* CORSO_H_ */
