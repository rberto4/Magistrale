#ifndef OrderedSet
#define OrderedSet

typedef struct OrderedSetType* orderedSet;
orderedSet mkOrderedSet(int* arr, int size);
orderedSet unionSet(orderedSet s1, orderedSet s2);
void printSet(orderedSet s);
void deleteSet(orderedSet s);
#endif