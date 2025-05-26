# capitolo 04 - Ricerca
Se l'azione da compiere non è chiara da priori, serve guardare in avanti il cammino, attuando quindi una strategia di ricerca. 
In AI, molti problemi sono di Ricerca, in uno spazio degli stati

### Spazio degli stati e problema di ricerca
l'insieme di tutti gli stati raggiungibili dallo stato iniziale, con una sequenza di operatori.
Caratteristiche :

* Stato iniziale in cui l'agente sa di trovarsi. (non noto a priori)
* Insieme di azioni possibili da parte dell'agente
* Cammino -> sequenza di azioni che portano da uno stato all'altro.

#### Processo di risoluzione del problema
* Formulazione dell'obiettivo
* Formulazione del problema -> descrizione degli stati e azioni per necessarie per raggiungere l'obiettivo (con un modello)
* Ricerca -> l'agente simula nel suo modello, la sequenza di azioni per raggiungere l'obiettivo (una **soluzione**, ovvero un cammino che porta dallo stato iniziale a quello finale)
* Esecuzione -> applica il simulato.

Se il modello è corretto, l'agente può ignorare le sue percezioni, dato che ha garanzia di raggiungere l'obiettivo.

#### Problema di ricerca
Formulato come segue :

1. Spazio degli stati
2. Stato iniziale
3. Stato obiettivo -> può essere anche più di uno o un'insieme di vari stati, con una proprietà che deve essere soddisfatta.
4. Azioni possibili -> dato uno stato S, le azioni applicabili in S sono **Azioni(S)**.
5. Modello di transizione -> **Risultato(S,A)** restituisce lo stato risultante dall'esecuzione dell'azione A nello stato S.
6. Funzione di costo -> **costo-azione(S,A,S')**,  costo numerico di applicare l'azione A nello stato S per raggiungere lo stato S'

### Algoritmi di ricerca
Un algoritmo di ricerca riceve in input un'problema di ricerca e restituisce una soluzione o un'indicazione di fallimento.
Si sovrappongono **alberi di ricerca** al grafo dello spazio degli stati, formando vari cammini a partire dallo stato iniziale o cercando di trovare uno che raggiunga uno stato obiettivo.
* Ciascun nodo dell'albero di ricerca corrisponde ad uno stato
* le azioni sono i rami.
* la radice dell'albero è lo stato iniziale

uso la funzione **Risultato(S,A)** a partire da un nodo, per vedere dove portano tali azioni e generare un nuovo nodo

C'è una netta differenza tra alberi di ricerca e spazio degli stati:

* **Spazio degli stati** -> insieme potenzialmente infinito di stati nel mondo e azioni che consentono le transizioni tra uno e l'altro.
* **Albero di ricerca** -> descrive cammini tra questi stati per raggiungere l'obiettivo.

#### Frontiera 
Insieme dei nodi (corrispondenti stati) che sono stati raggiunti ma non ancora espansi.

#### Quale nodo di frontiera espandere? 
serve una strategia di ricerca -> che specifichi quali nodi di frontiera da espandere.

![](/home/robtp/Immagini/Schermate/Screenshot%20From%202025-05-25%2018-50-29.png)

Questa è la struttura dati che rappresenta un nodo in un albero di ricerca.
L'azione di spostare il vuoto a sx, costa 6

#### struttura dati della frontiera

è una coda con cui possiamo svolgere 4 operazioni:

1. **Vuota?(frontiera)** -> return true se è una frontiera
2. **Pop(frontiera)** -> return e remove il nodo in cima alla frontiera
3. **Top(frontiera)** -> return il nodo in cima alla frontiera
4. **Aggiungi(Nodo, frontiera)** -> inserisce un nodo