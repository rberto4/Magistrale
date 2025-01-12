#include <stdio.h>
#include <stdlib.h>
#define ARRAY_SIZE(a) (sizeof(a)/sizeof(a[0]))
#define MIN(a,b) (((a)<(b))?(a):(b))


int *combina (int a1[], int a2[],int a3[], int iter) {
    if (iter % 2 == 0)
    {
        // pari
        a3[iter] = a1[iter] + a2[iter];
    }
    else
    {
        // dispari
        a3[iter] = a2[iter - 1];
    }
    return a3;
}

int *mix_array(int a1[], int size1, int a2[], int size2, int size)
{

    int *a3 = malloc(sizeof(int) * size);

    if (a3 == NULL)
    {
        printf("Errore di allocazione della memoria.\n");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < size; i++)
    {
        a3 = combina(a1, a2, a3, i);
    }
    return a3;
}

int *mix_array_r(int a1[], int a2[], int dim1, int dim2, int size, int iter, int a[])
{
    // passo 0 -> se ho sono alla iter 0, alloco spazio per a
    if (iter == 0) {
        // sono al passo 0 , oltre ad allocare, eseguo la prima combinazione nella posizione 0.

        a = malloc(sizeof(int) * size);
        a = combina(a1,a2,a,iter);
        return mix_array_r(a1,a2,dim1,dim2,size,iter+1,a);
    }

    a = combina(a1,a2,a,iter);
    if (iter  != size) {
        return mix_array_r(a1,a2,dim1,dim2,size,iter+1,a);
    }

    return  a;
}

// tail recorsive
int *mix_array_tr(int a1[], int a2[], int dim1, int dim2, int size, int iter, int a[])
{
    // passo 0 -> se ho sono alla iter 0, alloco spazio per a
    if (iter == 0) {
        a = malloc(sizeof(int) * size);
    }
    a = combina(a1,a2,a,iter);
    if (iter  == size) {
        return a;
    }
    return mix_array_tr(a1,a2,dim1,dim2,size,iter+1,a);
}

void stampa_array(int a[], int size)
{
    for (int i = 0; i < size; i++)
    {
        printf("%d", a[i]);
        if (i != size - 1)
        {
            printf(" - ");
        }
    }
    printf("\n");
}

int main()
{
    // inizializzo array da combinare e recupero dimensioni.
    int a1[] = {8, 5, 1, 2, 9, 6, 5, 3};
    int a2[] = {8, 4, 6, 3, 2, 5, 1};
    int size1 = ARRAY_SIZE(a1);
    int size2 = ARRAY_SIZE(a2);
    int size = MIN(size1, size2);

    // combino e metto in una array a
    int *a = mix_array(a1, size1, a2, size2, size);
    int *b = mix_array_tr(a1,a2,size1, size2, size, 0,b);
    int *c = mix_array_r(a1,a2,size1, size2, size, 0,b);

    // stampa
    printf("Array combinati:\n");
    stampa_array(a, size);
    stampa_array(b, size);
    stampa_array(c, size);

    free(a);
    free(b);
    free(c);
    return 0;

}

