#include <stdio.h>
#include <stdlib.h>
#include "StringBuffer.h"
// Definiamo il tipo che rappresenta la circularString

struct StringBufferType
{
  // Spazio dedicato alla stringa
  char *stringa;
  // Lunghezza della stringa salvata
  int len;

};

stringBuffer costruttore(char *s, int len)
{
  // Allochiamo lo spazio per la circularString
  stringBuffer cb = malloc(sizeof(stringBuffer));

  // Allochiamo lo spazio per la stringa
  cb->stringa = (char *)malloc(sizeof(char) * len);

  // Popoliamo la stringa dall'input
  for (int i = 0; i < len; i++)
    cb->stringa[i] = s[i];

  // Alternativa usando la funzione dedicata da string.h
  // strcpy(cs->stringa, s);

  // Inizializziamo gli altri campi
  cb->len = len;

  return cb;
}

char *toString(stringBuffer s)
{
    char * temp = malloc(sizeof(char) * (s->len) + 3);
    temp[0] = '"';
    for (int i = 0; i < s->len; i++)
        temp[i+1] = s->stringa[i];
    temp[s->len+1] = '"';
    temp[s->len+2] = '\0';
    return temp;
}

void cancella(stringBuffer s)
{
  // Liberiamo SEMPRE per primo lo spazio allocato per i field della struct
  free(s->stringa);
  // Poi liberiamo lo spazio allocato per la struct che rappresenta la circularString
  free(s);
}

void accoda(stringBuffer s1, stringBuffer s2){
    int len = s1->len + s2->len;
    s1->stringa = realloc(s1->stringa, len);
    for (int i = 0; i < s2->len; i++)
        s1->stringa[s1->len + i] = s2->stringa[i];
    s1->len = len;
}