#include <stdlib.h>
#include <stdio.H>
#include "mcomputer.h"
#define NUMBER_COMPUTER 10
#define ARRAY_SIZE(a) (sizeof(a) / sizeof(a[0]))

static char *names[NUMBER_COMPUTER];

void setComputerData(char *n, int c)
{
    if (names[c] == NULL)
    {
        names[c] = malloc(sizeof(char) * ARRAY_SIZE(n) + 1);
        for (int i = 0; i < ARRAY_SIZE(n); i++)
        {
            names[i] = n[i];
        }
    }
}

void printComputer(int c)
{
    printf("Computer %d %s", c, names[c]);
}
