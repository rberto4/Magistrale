#include<stdio.h>
#include<stdlib.h>

int* foo(int y){
  int* h=malloc(sizeof(int));
  *h=y;

  printf("%p\n",h);
  free(h);
  printf("%p\n",h);
  return h;
}

int main(void){
  int*p=foo(3);

  printf("%d\n",*p);
  foo(4);

  int b=*p;
  printf("%d\n",*p);
  printf("%d\n",b);
  return 0;
}
