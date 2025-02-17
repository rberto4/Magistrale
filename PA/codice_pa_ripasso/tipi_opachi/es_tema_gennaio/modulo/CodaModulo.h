#ifndef MODULO_H
#define MODULO_H

typedef struct CodaModulo * Codam;
Codam mkCodaModulo();
void insertModulo (Codam coda, int value);
void delModulo  (Codam coda);
void printModulo  (Codam coda);

#endif