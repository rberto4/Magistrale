



#ifndef StringBuffer
#define StringBuffer


typedef struct StringBufferType *stringBuffer;
stringBuffer costruttore(char * s, int len);
void cancella(stringBuffer s);
char * toString(stringBuffer s);
void accoda(stringBuffer s1, stringBuffer s2);


#endif