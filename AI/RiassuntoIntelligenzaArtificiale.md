# capitolo 04 - Ricerca
Se l'azione da compiere non è chiara da priori, serve guardare in avanti il cammino, attuando quindi una strategia di ricerca. 
In AI, molti problemi sono di Ricerca, in uno spazio degli stati

### Spazio degli stati
l'insieme di tutti gli stati raggiungibili dallo stato iniziale, con una sequenza di operatori.
Caratteristiche :

* Stato iniziale in cui l'agente sa di trovarsi. (non noto a priori)
* Insieme di azioni possibili da parte dell'agente
* Cammino -> sequenza di azioni che portano da uno stato all'altro.

#### Processo di risoluzione del problema
* Formulazione dell'obiettivo
* Formulazione del problema -> descrizione degli stati e azioni per necessarie per raggiungere l'obiettivo (con un modello)
* Ricerca -> l'agente simula nel suo modello, la sequenza di azioni per raggiungere l'obiettivo (una soluzione)
* Esecuzione -> applica il simulato.

Se il modello è corretto, l'agente può ignorare le sue percezioni, dato che ha garanzia di raggiungere l'obiettivo.
