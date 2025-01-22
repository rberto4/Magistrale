//
// Created by robtp on 22/01/25.
//

#include<stdio.h>
#define ARRAY_SIZE(a) (sizeof(a) / sizeof((a)[0]))


void swap(int array[], int j) {
    int temp = array[j + 1];
    array[j + 1] = array[j];
    array[j] = temp;
}

int iterBubbleSort(int array[], int len) {
    int count = 0;
    for (int i = 0; i < len - 1; i++) {
        for (int j = 0; j < len - 1 - i; j++) {
            if (array[j + 1] < array[j]) {
                swap(array, j);
                count++;
            }
        }
    }
    return count;
}

int TailRecursiveBubbleSort(int array[], int len, int iter1, int iter2, int count) {
    if (iter1 == len - 1) {
        return count;
    }

    if (iter2 < len - 1 - iter1) {
        if (array[iter2 + 1] < array[iter2]) {
            swap(array, iter2);
            count++;
        }
        return TailRecursiveBubbleSort(array, len, iter1, iter2 + 1, count);
    }
        return TailRecursiveBubbleSort(array, len, iter1 + 1, 0, count);
}

int RecursiveBubbleSort(int array[], int len, int iter1, int iter2, int count) {
    // Base case: if we have completed all the passes, return the count
    // Recursive case: if not all elements are compared in the current pass
    if (iter2 < len - 1 - iter1) {
        if (array[iter2 + 1] < array[iter2]) {
            swap(array, iter2);
            count++;
        }
        return RecursiveBubbleSort(array, len, iter1, iter2 + 1, count);
    }
    if (iter1 != len - 1) {
        return RecursiveBubbleSort(array, len, iter1 + 1, 0, count);
    }

    return count;
}


void printArray(int array[], int len) {
    for (int i = 0; i < len; i++) {
        printf("%d ", array[i]);
    }
    printf("\n");
}


int serienumerica (int n) {

    int an = 0;

    // se è n<=3 restituisco semplicemente n+1
    if (n <= 3) {
        an = n + 1;
        return an;
    }

    // parto da n
    while (n > 3) {
        an = an + (n - 1) * ((n-2)-(n-3));
        n--;
    }

    return an;
}

int serienumerica_ricorsiva (int n, int an) {
    if (n <= 3 && an == 0) {
        return an + n + 1;
    }

    if (n > 3) {
        an = an + (n - 1) * ((n-2)-(n-3));
        return serienumerica_ricorsiva(n - 1,an);
    }

    return an;
}


int main() {

    int a = serienumerica(8);
    printf("it) valore an : %d\n", a);

    int b = serienumerica_ricorsiva(8, 0);
    printf("ric) valore an : %d\n", b);


    /**
    * int array[] = {2, 4, 1, 5, 7, 10};
    int len = ARRAY_SIZE(array);


    printArray(array, len);
    printf("Scambi : %d\n", RecursiveBubbleSort(array, len,0,0,0));
    printArray(array, len);
     *
     *
     */

    return 0;
}
