#include <stdio.h>
#include <stdlib.h>

int f(int a, int b){
    if (b == 0) {
        return a;
    }

    int c=a%b;
    return f(b,c);
}

int main(){
    printf("%d\n" ,f(9,6));
    return 0;
}