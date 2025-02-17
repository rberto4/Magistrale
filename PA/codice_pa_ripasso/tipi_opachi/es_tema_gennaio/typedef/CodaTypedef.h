#ifndef CodaTypedef_H
#define CodaTypedef_H

typedef struct CodaTypedef{
  int * array;
  int size;
} Codatd;

Codatd* mkCodaTypedef ();
void insert (Codatd*c, int value);
void print (Codatd* c);
void del(Codatd* c);

#endif