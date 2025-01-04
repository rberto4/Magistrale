#include<stdio.h>
#include<stdlib.h>  
#include<string.h>
#define ARRAY_SIZE(a) (sizeof(a) / sizeof((a)[0]))
#define MIN(a, b) (a < b ? a : b)

char *mixStrings(char *, int, char *, int);
char *mixStringsRec(char *, int, char *, int, int,int, char *);
char *mixStringsTailRec(char *, int, char *, int, int, int ,char *);


int main(int argc, char const *argv[]) {

    // non ricorsiva
    char s1[] = "cane";
    char s2[] = "nero";
    char stringaRisultatoParziale[] = "";
    int dim1 = strlen(s1);
    int dim2 = strlen(s2);
    char *result_1 = mixStrings(s1, dim1, s2, dim2);
    printf("Stringa mixata (iterativo)-> %s\n", result_1);  
    free(result_1);

    // ricorsiva
    char *temp_r = (char *)malloc((2* (MIN(dim1,dim2))) * sizeof(char));
    char *result_2 = mixStringsRec(s1, dim1, s2, dim2, 0, 0, temp_r);
    printf("Stringa mixata (recorsivo)-> %s\n", result_2);
    free(result_2);


      // ricorsiva tail
    char *temp_tr = (char *)malloc((2* (MIN(dim1,dim2))) * sizeof(char));
    char *result_3 = mixStringsTailRec(s1, dim1, s2, dim2, 0, 0, temp_tr);
    printf("Stringa mixata (recorsivo tail)-> %s\n", result_2);
    free(result_3);

    
    return 0;
}


char *mixStrings(char *s1, int dim1, char *s2, int dim2){
    // spazio stringa di ritorno + 1 per il carattere terminatore
    int size = (2 * MIN(dim1, dim2)) + 1;
    char *result = (char *)malloc(size * sizeof(char));
    int i = 0;
    for (int j = 0; j < MIN(dim1, dim2); j++)
    {
        // Primo carattere da s1
        result[i] = s1[j];
        i += 1;

        // Secondo carattere da s2
        result[i] = s2[j];
        i += 1;
    }

    result[size] = '\0';
    return result;
}

char *mixStringsRec(char *s1, int dim1, char *s2, int dim2, int idxOut, int idxStrs, char *out){
     // Passo base: mi fermo quando incontro la fine della stringa più corta
  if (idxStrs > MIN(dim1, dim2))
  {
    // Terminatore
    out[idxOut] = '\0';
    return out;
  } 

  // Chiamata ricorsiva
  out = mixStringsRec(s1, dim1, s2, dim2, idxOut + 2, idxStrs + 1, out);

  // Popolo la stringa quando "riemergo" dalla ricorsione, in questo modo rendo esplicito che la funzione non è tail-recursive
  out[idxOut] = s1[idxStrs];
  out[idxOut + 1] = s2[idxStrs];

  return out;
}

char *mixStringsTailRec(char *s1, int dim1, char *s2, int dim2, int idxOut, int idxStrs, char *out){
   
  // Passo base: mi fermo quando incontro la fine della stringa più corta
  if (idxStrs > MIN(dim1, dim2))
  {
    out[idxOut] = '\0';
    return out;
  }

  // Passo iterativo: popolo la stringa
  out[idxOut] = s1[idxStrs];
  out[idxOut + 1] = s2[idxStrs];

  // Chiamata ricorsiva tail recursive
  return mixStringsTailRec(s1, dim1, s2, dim2, idxOut + 2, idxStrs + 1, out);
}