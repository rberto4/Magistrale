

#ifndef BITVECTOR_H
#define BITVECTOR_H


typedef struct vettore* bitvector ;
bitvector make_vector(int);
void delete_vector(bitvector);
char * tostring(bitvector);
bitvector op_and(bitvector,bitvector);
bitvector op_or(bitvector, bitvector);
bitvector op_not(bitvector);


#endif