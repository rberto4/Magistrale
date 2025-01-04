#include<iostream>
using namespace std;

// puntatore row
void setA (char *s){
   if (s == nullptr){
    return;
   }
    s[0] = 'A';
}

// puntatore shared
void setA(shared_ptr<char> sptr){
    if (sptr.get() == nullptr){
        return;
    }
    // puntatore wrappato dallo smart pointer
    sptr.get()[0] = 'A';
}

// puntatore unique
// lo passo by reference perchè se cosi non fosse, non sarebbe 
void setA(unique_ptr<char> &uptr){
    if (uptr.get() == nullptr){
        return;
    }
    // puntatore wrappato dallo smart pointer
    uptr.get()[0] = 'A';
}

void copyStringToArray(char *dst, string src)
{
  for (int i = 0; i < src.length(); i++)
    dst[i] = src[i];
}


int main (int argv, char* argc[]){
    
    
  string originalString = "Hello";
  int len = originalString.length();

  // In Cpp possiamo ancora usare malloc, ma non free()
  //  char *word = (char *)malloc(sizeof(char) * len);
  char *word = new char[len];
  copyStringToArray(word, originalString);
  setA(word);
  cout << "Raw pointer:\t" << word << endl;

  char *sWord = new char[len];
  copyStringToArray(sWord, originalString);
  shared_ptr<char> sWord_ptr(sWord);
  setA(sWord_ptr);
  cout << "Shared pointer:\t" << sWord << endl;

  char *uWord = new char[len];
  copyStringToArray(uWord, originalString);
  unique_ptr<char> uWord_ptr(uWord);
  setA(uWord_ptr);
  cout << "Unique pointer:\t" << uWord << endl;

    
    return 0;
}


