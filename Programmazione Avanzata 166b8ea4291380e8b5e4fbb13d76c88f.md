# Programmazione Avanzata

# 01 - Computabilità

Dal punto di vista matematico un programma è una funzione matematica, tuttavia un programma può implementare solo funzioni computabili.

Ragioni per cui un programma non ritorna nulla :

1. Errore : quando c’è un problema (es. divisione per 0)
2. Non terminazione : la computazione procede all’infinito senza produrre risultati

Espressioni note : 

- java.lang.StackOverflowError
- -Xss515

Un programma può terminare solo per certi input, oppure una soluzione implementabile per certi linguaggi e per certi no.

una funzione sui numeri naturali può essere effettivamente calcolata se e solo se è  computabile da una macchina di Turing.

### Halting problem

dato un generico programma P, che riceve una stringa x come input, determinare se il programma P termina quando riceve la stringa x.

L’halting problem è indecidibile.

alcue funzioni sono :

- Computabili in principio → se una funzione è computabile
- Computabili in pratica → alcune funzioni potrebbero essere computabili, tutta via in un quantitativo di tempo infinito (pari alla durata della storia dell’intero universo)

### Compiler

- compilatore  :  traduce il programma in un’insieme di istruzioni che possono essere eseguite dalla macchina.
- interprete : combina la traduzione con l’esecuzione del programma

```java
public class test {
	public static int counter = 0;
	
	public static int check (int arg) {
		if (counter < 20){
			System.out.println(arg);
			counter++;
		}
		if (arg == 0) {
			return 0;
		}
		else return check (arg-2);
	}
}
```

### Struttura di un compilatore (struttura left to right e top to bottom)

- Lexical analyzer : simboli sintattici sono raggruppati in tokens
- Syntax analyzer : token sono raggruppati in espressioni, statements e
dichiarazioni in base alle regole della grammatica. attivitò eseguita dal parser con l’obbiettivo di creare un parser tree
- Semantic analyzer : applice regole e procedure che dipendono dal contesto, (es. controllare che i tipi siano corretti in temp = x + 1). Produce un augmented parse-tree
- intermediate code generator : produce una versione di codice intermedia
- Code optimizer : Tecniche per ottimizzare il codice (elimina codice morto, rimpiazza funzioni con il codice, elimina sottoespressioni)
- Code generator : converte il codice intermedio nel linguaggio el target program

### Grammatiche

forniscono un metodo per definire un’insieme finito di espressioni :

- simbolo iniziale
- non terminali
- terminali
- regole di produzione

Grammatica ambigua : quando posso avere più di un parse tree

```java
e ::= n | e + e | e - e
n ::= d | nd
d ::= 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
```

- e : espressione, posso avere un numero singolo, o un operazione tra espressioni
- n : numero, cifra singola o sequenza di cifre
- d : cifra, quali valori può contenere.

Parsing : costruzione del parse tree

### Lambda calculus

Sistema formale per lo studio dei concetti di funzione e di calcolo.

Concetti principali:

1. Lambda abstractions : rappresentano la definizione di funzioni, se M è una espressione,  $λx.M$ è la funzione che otteniamo, trattando $M$ come una funzione della variabile $x$.
2.