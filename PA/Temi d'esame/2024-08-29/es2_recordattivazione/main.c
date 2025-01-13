#include <stdio.h>
#include <stdlib.h>

int searchMinRec (int*array, int n) {
    if (n == 1) return array[0];
    int min = searchMinRec(array + 1, n - 1);
    if (min < array[0]) return min;
    else return array[0];
}

int main(void) {
    int array_main[2] = {12,13};
    int min = searchMinRec(array_main, 2);
    printf("Fine Programma %d" , min);
    return 0;
}