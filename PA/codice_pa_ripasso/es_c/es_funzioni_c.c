#include<stdio.h>
#define ARRAY_SIZE(a) (sizeof(a)/sizeof(a[0]))

void swap(int *array, int j) {
    int temp = array[j];
    array[j] = array[j + 1];
    array[j + 1] = temp;
}


int counterSwap(int *array, int size) {
    int count = 0;
    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - i - 1; j++) {
            if (array[j] > array[j + 1]) {
                swap(array, j);
                count++;
            }
        }
    }
    return count;
}


int counterSwapRecorsive(int *array, int size, int count, int c1, int c2) {
    if (c1 == size - 2) {
        // abbiamo fatto già size - 1iterazioni
        return count;
    }
    if (c2 == size - c1 - 2) {
        return counterSwapRecorsive(array, size, count, c1 + 1, 0);
    }

    if (array[c2] > array[c2 + 1]) {
        swap(array, c2);
        return counterSwapRecorsive(array, size, count + 1, c1, c2 + 1);
    }
        return counterSwapRecorsive(array, size, count, c1, c2 + 1);
}

int counterSwapTailRecorsive(int *array, int size, int count, int c1, int c2) {
    if (c1 == size - 2) {
        // abbiamo fatto già size - 1iterazioni
        return count;
    }
    if (c2 == size - c1 - 2) {
        c1 = c1 + 1;
        c2 = 0;
    }

    if (array[c2] > array[c2 + 1]) {
        swap(array, c2);
        count = count + 1;
    }

    c2 = c2 + 1 ;

    return counterSwapTailRecorsive(array, size, count, c1, c2);
}


void printArray(int *array, int size) {
    printf("Array : ");
    for (int i = 0; i < size; i++) {
        printf("%d ", array[i]);
    }
    printf("\n");
}


int main(void) {
    int a1[] = {2, 4, 1, 5, 6, 9};
    int size = ARRAY_SIZE(a1);
    printArray(a1, size);
    printf("Number of swaps : %d\n", counterSwapTailRecorsive(a1, size,0,0,0));
    printArray(a1, size);
    return 0;
}
