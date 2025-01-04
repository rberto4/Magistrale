#ifndef List
#define List

typedef struct ListType *list;
list costruttore(int a[], int len);
void cancella(list l);
void stampa(list l);
list concatena(list l1, list l2);

#endif
