#include "CounterModulo.h"
void reset (CounterModulo* pc){
    *pc = 0;
}
void inc (CounterModulo *pc){
    (*pc)++;
}
int getValue(CounterModulo c){
    return c;
}