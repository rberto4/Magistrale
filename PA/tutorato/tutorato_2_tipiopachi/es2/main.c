#include<stdlib.h>
#include<stdio.h>
#include "StringBuffer.c"

int main (){

    char stringa1[] = "ciao";
    char stringa2[] = "mario";
    stringBuffer sb1 = costruttore(stringa1, sizeof(stringa1)-1);
    stringBuffer sb2 = costruttore(stringa2, sizeof(stringa2)-1);

    printf("Stringa 1: %s\n", toString(sb1));
    accoda(sb1,sb2);
    printf("Stringa accodata: %s\n", toString(sb1));
    return 0;
} 