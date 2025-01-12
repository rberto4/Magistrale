#include <stdio.h>
#include <stdlib.h>
#include "OrderedSet.h"
#define ARRAY_SIZE(a) (sizeof(a) / sizeof((a)[0]))



int main() {
  int a1[] = {1,4,5,7};
  int size1 = ARRAY_SIZE(a1);

  int a2[] = {8,4,3,1};
  int size2 = ARRAY_SIZE(a2);

  orderedSet os1 = mkOrderedSet(a1,size1);
  orderedSet os2 = mkOrderedSet(a2,size2);

  printSet(os1);
  printSet(os2);

  printf(" --- unione ---\n");

  printSet(unionSet(os1,os2));

  deleteSet(os1);
  deleteSet(os2);
}