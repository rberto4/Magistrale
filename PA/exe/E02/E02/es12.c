// 3.1
#include <stdio.h>
#define ARRAY_SIZE(a) (sizeof(a) / sizeof((a)[0]))


// conta quanti sono i pari
int contapari(const int *s, int n) {
    if (!s) // NULL CHECK
        return 0;
    int result = 0;
    for (int i = 0; i<n; i++){
      //  if (*(s+i)%2 == 0) result ++;
       printf(" ------------ %d° Iterazione ------------ \n", i+1);
        if (s[i] %2 == 0) {
            result ++;
            printf("Il numero %d è pari\n", s[i]);
        }else{
            printf("Il numero %d è dispari\n", s[i]);
        }
    }
    return result;
}

int main(void) {
    int s[6] = {1,2,3,4,5,6};
    int l = contapari(s,ARRAY_SIZE(s));
    printf("i pari sono %d\n", l);
}
