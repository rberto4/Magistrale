//
// Created by mitic on 15/02/2025.
//


#include <stdio.h>
#define ARRAY_SIZE(a) (sizeof(a) / sizeof((a)[0]))


void print (int arr[], int size) {
  for (int i = 0; i < size; i++) {
    printf("%d ", arr[i]);
  }
}


void selectionSort (int arr[], int size) {


  int i, j, min;
  for (i = 0; i < size; i++) {
    min = i;
    for (j = i + 1; j < size; j++) {
      if (arr[min] > arr[j]) {
        min = j;
        int temp = arr[min];
        arr[j] = arr[min];
        arr[min] = temp;
      }
    }
  }

  print(arr, size);
}



int conterEven (int * array, int size){
  int count = 0;
  for (int i = 0; i<size; i++){
    if (array[i]%2==0){
      count += 1;
    }
  }
  return count;
}

int counterEvenRecorsive(int * array, int size, int counter, int cicle) {
  if (size == cicle) {
    return counter;
  }
  if (array[cicle]%2==0) {
    return counterEvenRecorsive(array, size, counter + 1, cicle + 1);
  }
  return counterEvenRecorsive(array, size, counter, cicle + 1);
}

int counterEvenRecorsiveTail(int * array, int size, int counter, int cicle) {
  if (size == cicle) {
    return counter;
  }
  if (array[cicle]%2==0) {
    counter += 1;
  }
  cicle += 1;

  return counterEvenRecorsive(array, size, counter, cicle);
}


int main (void){
  int a1[]={1,2,3,5,6,7,7,8};
  int size_a1= ARRAY_SIZE(a1);
  printf("Iter) Numero di elementi pari : %d\n", conterEven (a1, size_a1));
  printf("Rec) Numero di elementi pari : %d\n", counterEvenRecorsive (a1, size_a1, 0, 0));
  printf("TailRec) Numero di elementi pari : %d\n", counterEvenRecorsiveTail (a1, size_a1,0,0));


  int a2[] = {2,1,4,2,7,5};
  print(a2, ARRAY_SIZE(a2));
  printf("\n");
  selectionSort(a2, ARRAY_SIZE(a2));
  return 0;
}