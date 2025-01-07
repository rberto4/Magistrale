//2.4
#include <stdio.h>

int main() {
    int x = 3;
    int *y = &x; // y punta alla cella di x
    *y = *y + 1;
    printf("%d\n",x);
    return 0;
}
