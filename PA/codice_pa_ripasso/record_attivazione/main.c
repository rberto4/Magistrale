#include <stdlib.h>
#include <stdio.h>

int pow_n(int a, int ex) {
    if (ex == 0) return 1;
    if (ex == 1) return a;
    return a * (pow_n(a, ex - 1));
}


int main () {
    int result = pow_n(5, 3);
    printf("%d\n", result);
    return 0;
}

