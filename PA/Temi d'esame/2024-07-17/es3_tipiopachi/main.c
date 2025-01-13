#include <stdio.h>
#include "cmake-build-debug/Set.h"
#define ARRAY_SIZE(a) sizeof(a)/sizeof(a[0])

int main (){
    int a1[] = {2,1,4,5,5,1,9};
    int a2[] = {2,3,7,1,1,2,1};

    int size_a1 = ARRAY_SIZE(a1);
    int size_a2 = ARRAY_SIZE(a2);

    set s1 = mkSet(a1,size_a1);
    set s2 = mkSet(a2,size_a2);

    printSet(s1);
    printSet(s2);
    printSet(unionSet(s1,s2));
    deleteSet(s1);
    deleteSet(s2);

    return 0;
}
