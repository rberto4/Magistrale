#include <stdio.h>
#include "CircularString.c"

int main() {
    
  char s[] = "abu";

  circularString cs = costruttore(s, sizeof(s)-1);
  
  printf("Originale: %s\n", toString(cs));


  for (int i=0; i<cs-> lenStringa; i++){
    gira(cs);
    if (nGira(cs) == 1)
        printf("%s, girata per %d volta\n", toString(cs), nGira(cs));
    else{
        printf("%s, girata per %d volte\n", toString(cs), nGira(cs));
    }
  }

  cancella(cs);
  return 0;
}