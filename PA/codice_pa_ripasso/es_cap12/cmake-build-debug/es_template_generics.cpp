//
// Created by mitic on 10/02/2025.
//

#include<iostream>
using namespace std;

template<class T>
T findMax(T t1, T t2){
    if (t1>t2) return t1;
    return t2;
}

int main(){

  cout<<findMax<int>(1,2)<<endl;
  cout<<findMax<double>(2.4,3.1)<<endl;
  cout<<findMax<string>("casa","elefante")<<endl;
  return 0;

}