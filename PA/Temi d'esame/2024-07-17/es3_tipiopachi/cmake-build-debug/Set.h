//
// Created by roberto bertocchi on 13/01/2025.
//

#ifndef SET_H
#define SET_H

typedef struct Set* set;
set mkSet (int* array, int len);
set unionSet(set s1, set s2);
void printSet(set s);
void deleteSet(set s);
#endif
