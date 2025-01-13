#include <stdio.h>
#include <stdlib.h>
#define ARRAY_SIZE(a) (sizeof(a) / sizeof(a[0]))

int calcolaMultipliTre (int * array, int len) {
    int count = 0;
    if (array == NULL) {
        return 0;
    }
    for (int i = 0; i < len; i++) {
        if (array[i] % 3 == 0) {
            count++;
        }
    }
    return count;
}

int calcolaMultipliTre_r (int * array, int len, int iter, int count) {
    if (iter != len ) {
        if (array[iter] % 3 == 0) {
            return calcolaMultipliTre_r(array,len,iter+1,count+1);
        }
            return calcolaMultipliTre_r(array,len,iter+1,count);
    }
    return count;
}


int calcolaMultipliTre_rt (int * array, int len, int iter, int count) {
    if (iter == len) {
        return count;
    }

    if (array[iter] % 3 == 0) {
        count++;
    }

    return calcolaMultipliTre_rt(array, len, iter + 1, count);
}


int main(void) {
    printf(" --- Multipli di 3 ---\n");
    int a[] = {1,2,4,5,6,7,8,9,12, 17,18,19,21,29,33,50};
    int size_a = ARRAY_SIZE(a);
    printf("FUNZIONE ITERATIVA - I multipli di 3 sono : %d\n" , calcolaMultipliTre(a, size_a));
    printf("FUNZIONE RICORSIVA - I multipli di 3 sono : %d\n" , calcolaMultipliTre_r(a, size_a,0,0));
    printf("FUNZIONE RICORSIVA TAIL - I multipli di 3 sono : %d\n" , calcolaMultipliTre_rt(a, size_a,0,0));
    return 0;
}