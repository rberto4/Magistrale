//
// Created by mitic on 09/02/2025.
//


#include <stdio.h>
#define SIZE_ARRAY (sizeof(array)/sizeof(array[0]))

int function(int A[], int i){
  if (i==0)  return 0 ;
  return A[0] + function(A+1, i-1);
}


int main(){
  int V[] = {1,3,4,5,2};
  int z = function(V,5);
  printf("ris : %d\n" , z);

  printf("V+1 : %d" , function(V,2));

  int value = 24000;
  for (int i=0; i<20; i++) {
    value += value*0.05;
  }

  printf("ris : %d\n" , value);
  return 0;
}

