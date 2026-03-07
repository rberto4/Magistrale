## Indice
1. [Reti Neurali Ricorrenti (RNN)](#1-reti-neurali-ricorrenti-rnn)
2. [Modelli Sequence-to-Sequence (Seq2Seq)](#2-modelli-sequence-to-sequence-seq2seq)
3. [Il Meccanismo di Attenzione](#3-il-meccanismo-di-attenzione)
4. [Il Transformer](#4-il-transformer)
5. [Deep Learning per il Natural Language Processing (NLP)](#5-deep-learning-per-il-natural-language-processing-nlp)
6. [Word2Vec e Rappresentazioni delle Parole](#6-word2vec-e-rappresentazioni-delle-parole)
7. [Architettura del Transformer nel Dettaglio](#7-architettura-del-transformer-nel-dettaglio)
8. [Varianti del Transformer: BERT e GPT](#8-varianti-del-transformer-bert-e-gpt)
9. [Vision Transformer (ViT)](#9-vision-transformer-vit)
10. [Segmentazione di Immagini](#10-segmentazione-di-immagini)
---
## 1. Reti Neurali Ricorrenti (RNN)
### Perché servono le RNN?
Le **Reti Neurali Convoluzionali (CNN)** sono progettate per dati spaziali come le immagini: catturano pattern locali con filtri condivisi, ma **non sono adatte a gestire dati sequenziali** come testo, audio, serie temporali o video. Un'immagine ha una struttura spaziale fissa, ma una frase, un segnale audio o una serie temporale hanno un **ordine intrinseco** nel tempo: ogni elemento dipende dai precedenti.
Molti problemi reali coinvolgono **sequenze**, dove l'ordine dei dati è fondamentale. Ad esempio, consideriamo due frasi con la stessa parola "bank":
- *"I went to the bank to withdraw some money"* → qui "bank" significa banca
- *"I sat on the bank and watched the water flow by"* → qui "bank" significa riva del fiume
Per capire il significato corretto, il modello deve essere **consapevole del contesto circostante**, cioè deve "ricordare" le parole già lette. Le CNN non hanno questa capacità: trattano ogni input in modo indipendente, senza una nozione di tempo o di sequenza. Ecco perché servono le RNN.
### Come funziona una RNN
Una RNN è una rete neurale che mantiene uno **stato interno** (hidden state) che funge da "memoria compressa" di tutto ciò che ha elaborato fino a quel momento. Si pensi allo stato interno come a un riassunto continuo e aggiornato di ciò che la rete ha visto nei passi temporali precedenti. Ad ogni passo temporale $t$, la rete:
- **Riceve** un input $x_t$ (il dato corrente, es. una parola) e lo stato precedente $h_{t-1}$ (il "riassunto" di tutto ciò che è stato visto prima)
- **Aggiorna** il suo stato interno combinando le due informazioni:
$$
h_t = f_\theta(h_{t-1}, x_t)
$$
- **Produce** un output $y_t$ basato sullo stato aggiornato:
$$
y_t = f_\phi(h_t)
$$
Più concretamente, le equazioni interne di una semplice RNN sono:
$$
h_t = \tanh(W_{hh} \, h_{t-1} + W_{xh} \, x_t)
$$
$$
y_t = W_{hy} \, h_t
$$
Dove:
- $W_{hh}$ è una matrice di pesi che trasforma lo stato nascosto precedente $h_{t-1}$, determinando quanto e come il "ricordo" del passato influenza lo stato attuale
- $W_{xh}$ è una matrice che trasforma l'input corrente $x_t$, determinando come il dato nuovo viene incorporato
- $W_{hy}$ è una matrice che trasforma lo stato nascosto corrente $h_t$ per produrre l'output
- $\tanh$ è la funzione di attivazione non lineare, che introduce non-linearità nel calcolo (come in qualsiasi rete neurale)
- **Gli stessi pesi $W$ vengono riutilizzati ad ogni passo temporale**: questa condivisione dei parametri è una caratteristica fondamentale delle RNN, analoga alla condivisione dei filtri nelle CNN
### Differenza tra Feed-Forward e Recurrent
In una **rete feed-forward (FFN)**, ogni layer riceve input esclusivamente dal layer precedente, il flusso di informazione va solo in avanti e non c'è alcuna "memoria" di input passati. Ogni input viene elaborato in modo completamente indipendente dagli altri.
In una **rete ricorrente (RNN)**, invece, l'output di ogni passo dipende non solo dall'input corrente ma anche dallo **stato interno** del modello, che a sua volta dipende da tutti gli input precedenti. Graficamente, questo si manifesta come un **self-loop** (un arco che collega un nodo a sé stesso), che rappresenta il flusso di informazione dallo stato passato $h_{t-1}$ a quello presente $h_t$. Questo ciclo è ciò che dà alla rete la sua natura "ricorrente".
### Unrolling della RNN
Per comprendere e addestrare le RNN, si "srotolano" (**unrolling**) nel tempo: il self-loop viene espanso in una catena di copie della stessa rete, una per ogni passo temporale. Ogni copia riceve il suo input $x_t$ e lo stato dal passo precedente $h_{t-1}$, e produce il suo output $y_t$ e il nuovo stato $h_t$. Tutte le copie condividono gli stessi pesi $W_{hh}$, $W_{xh}$ e $W_{hy}$.
### Addestramento: Backpropagation Through Time (BPTT)
L'addestramento di una RNN avviene "srotolando" la rete nel tempo: si processa l'intera sequenza in avanti (forward pass) per calcolare una loss ad ogni passo temporale $L_t$, e la loss totale è la somma $L = \sum_t L_t$. Poi si retropropaga il gradiente **attraverso tutti i passi temporali** (da $t = T$ fino a $t = 1$), aggiornando i pesi condivisi. Questo è concettualmente identico alla backpropagation standard, ma applicato alla rete srotolata nel tempo.
### Applicazioni delle RNN
Le RNN gestiscono diverse tipologie di mapping tra sequenze, il che le rende estremamente versatili:
- **One-to-one** (un input → un output): come nella classificazione di immagini standard, dove una singola immagine produce una singola classe. Qui la RNN non è necessaria, ma può essere usata.
- **One-to-many** (un input → sequenza di output): un esempio è l'**image captioning**, dove una singola immagine produce una frase descrittiva. La CNN estrae le feature dell'immagine, che diventano lo stato nascosto iniziale della RNN, la quale genera poi una parola alla volta.
- **Many-to-one** (sequenza di input → un output): come nel **riconoscimento di azioni** da video, dove una sequenza di frame produce un'unica etichetta di classe (es. "lanciare un'ascia").
- **Many-to-many** (sequenza → sequenza): come nella **traduzione automatica** o nella generazione di sottotitoli video, dove una sequenza di input produce una sequenza di output.
### Language Model con RNN
Un esempio classico è il **character-level language model**: la rete viene addestrata a predire il prossimo carattere data la sequenza vista finora. Se la sequenza di training è "hello", il modello impara che: data "h" → predici "e", data "he" → predici "l", data "hel" → predici "l", data "hell" → predici "o". I caratteri vengono rappresentati con **one-hot encoding** e il vocabolario è l'insieme di tutti i caratteri unici. In fase di test, il modello **genera un carattere alla volta** e lo reinserisce come input per generare il successivo, creando testo nuovo mai visto prima (campionamento autoregressivo).
### Image Captioning con RNN
Un'altra applicazione è l'**image captioning**, dove si combina una CNN e una RNN in un modello ibrido. La CNN (es. VGG, ResNet) elabora l'immagine e produce un vettore di feature che diventa lo **stato nascosto iniziale** della RNN. La RNN poi genera una parola alla volta, usando come input speciali i token START (inizio frase) e END (fine frase).
### Limiti delle RNN e varianti
Le RNN soffrono di problemi seri:
- **Vanishing gradient**: durante la BPTT, i gradienti si moltiplicano ripetutamente attraverso i passi temporali. Se i valori sono minori di 1, il gradiente si azzera esponenzialmente, rendendo impossibile apprendere dipendenze a lungo termine. In pratica, la rete "dimentica" ciò che ha visto molti passi prima.
- **Perdita di informazione con sequenze lunghe**: man mano che la sequenza si allunga, le informazioni iniziali vengono progressivamente "diluite" nello stato nascosto, perché devono competere con tutte le informazioni successive.
Per superare questi limiti sono state sviluppate varianti più avanzate che mantengono lo stesso principio di funzionamento (stato nascosto aggiornato ricorsivamente) ma con meccanismi più sofisticati per gestire la memoria:
- **LSTM (Long Short-Term Memory)**: introduce tre "gate" (porte) — input gate, forget gate, output gate — che controllano selettivamente quali informazioni conservare, quali dimenticare e quali emettere. Grazie a una "cell state" separata, le LSTM possono mantenere informazioni per centinaia di passi temporali.
- **GRU (Gated Recurrent Unit)**: una versione semplificata dell'LSTM che combina input e forget gate in un unico "update gate", con meno parametri ma prestazioni spesso comparabili.
- **xLSTM (Extended LSTM)**: variante più recente che migliora ulteriormente le capacità di memoria a lungo termine.
---
## 2. Modelli Sequence-to-Sequence (Seq2Seq)
### Cos'è un modello Seq2Seq?
Un modello **Seq2Seq** (Sequence-to-Sequence) è un'architettura neurale che prende una sequenza di lunghezza variabile in input e produce una sequenza di lunghezza potenzialmente diversa in output. Questo è fondamentale per task dove input e output hanno dimensioni diverse:
- **Traduzione automatica**: "I am a student" (4 parole) → "Je suis étudiant" (3 parole)
- **Riassunto automatico**: un articolo lungo → un paragrafo breve
- **Chatbot**: una domanda → una risposta di lunghezza arbitraria
### Architettura Encoder-Decoder con RNN
Il modello Seq2Seq è composto da due RNN distinte:
- **Encoder**: una RNN che processa l'intera sequenza di input, un elemento alla volta, e produce un **vettore contesto** $c$. Questo vettore è tipicamente l'ultimo stato nascosto dell'encoder: $c = h_T$, dove $T$ è la lunghezza della sequenza di input. Il vettore $c$ "riassume" l'intera frase di input in un unico vettore a dimensione fissa.
- **Decoder**: una seconda RNN che genera la sequenza di output un elemento alla volta. Ad ogni passo $t$, il decoder riceve tre input: lo stato precedente del decoder $s_{t-1}$, il vettore contesto $c$ (costante) e l'ultimo token generato $y_t$. Con queste informazioni produce la parola successiva $y_{t+1}$.
Ad esempio, per tradurre "I am a student" in italiano: l'encoder elabora le 4 parole inglesi e produce $h_4$ (lo stato nascosto finale). Questo diventa il vettore contesto $c$, che viene passato al decoder. Il decoder, partendo dal token START, genera "Sono", poi "uno", poi "studente", fino a produrre il token END.
### Problema: collo di bottiglia dell'informazione
Il **principale limite** di questo approccio è che tutta l'informazione dell'intera sequenza di input viene forzata in un **singolo vettore** $c$ di dimensione fissa. Per frasi corte questo funziona bene, ma per sequenze lunghe il vettore contesto diventa un collo di bottiglia: le informazioni delle prime parole vengono progressivamente sovrascritte o diluite da quelle successive, proprio come nel problema del vanishing gradient delle RNN. Il risultato è che la qualità della traduzione degrada significativamente con frasi lunghe.
**Soluzione**: il meccanismo di **attenzione**, che permette al decoder di "guardare indietro" all'intera sequenza di input ad ogni passo, invece di dipendere da un singolo vettore riassuntivo.
---
## 3. Il Meccanismo di Attenzione
### Idea fondamentale
L'attenzione è uno degli ingredienti più importanti dell'intelligenza artificiale moderna. L'idea centrale è semplice ma potente: **non tutte le parole dell'input sono ugualmente importanti per generare ogni parola dell'output**. Invece di comprimere tutto in un singolo vettore, il modello dovrebbe poter "focalizzare lo sguardo" sulle parti rilevanti dell'input, come fa un essere umano quando traduce.
Ad esempio, per tradurre "we see the sky" → "vediamo il cielo": quando il modello deve generare "vediamo", dovrebbe prestare più attenzione a "we" e "see" (che portano il significato dell'azione), non a "the" e "sky". Quando invece genera "cielo", deve concentrarsi su "sky".
### Come funziona l'attenzione nel Seq2Seq
Invece di usare un singolo vettore contesto $c$ per tutti i passi del decoder, ora si calcola un **vettore contesto diverso** $c_t$ ad ogni passo di decodifica. Il procedimento è:
- **Calcolo dei punteggi di allineamento** $e_i$: per ogni stato nascosto dell'encoder $h_i$, si calcola quanto è "simile" o "rilevante" rispetto allo stato corrente del decoder $s$. Questo punteggio scalare dice al modello: "quanto è utile la parola $i$ dell'input per generare la parola corrente dell'output?"
- **Normalizzazione con softmax** per ottenere i pesi di attenzione $a_i$:
$$
a_i = \frac{\exp(e_i)}{\sum_j \exp(e_j)}, \quad \text{con} \quad 0 \le a_i \le 1 \quad \text{e} \quad \sum_i a_i = 1
$$
La softmax fa sì che i pesi siano tutti tra 0 e 1 e sommino a 1, trasformandoli in una distribuzione di probabilità: intuitivamente, l'attenzione "alloca" quanto guardare ciascuna parola dell'input.
- **Calcolo del vettore contesto** come media pesata degli stati dell'encoder:
$$
c = \sum_{i=1}^{N} a_i \, h_i
$$
Quindi, se per generare "vediamo" i pesi sono $a_1 = 0.45$ (we), $a_2 = 0.45$ (see), $a_3 = 0.05$ (the), $a_4 = 0.05$ (sky), il vettore contesto sarà dominato dalle rappresentazioni di "we" e "see".
I punteggi di allineamento vengono **appresi automaticamente** dalla rete durante l'addestramento tramite backpropagation: la rete impara da sola a quali parole prestare attenzione.
Ad ogni nuovo passo del decoder, si ricalcolano pesi di attenzione diversi. Per decodificare "il" dopo "vediamo", il modello assegnerà alta attenzione a "the" nell'input. Questo è visibile nelle mappe di attenzione: matrici dove le celle più luminose corrispondono a pesi più alti, mostrando ad esempio che "August" nella frase inglese si allinea con "août" nella traduzione francese.
### Generalizzazione dell'attenzione
A livello astratto, il meccanismo di attenzione è un'operazione generica che coinvolge tre tipi di vettori:
- **Query** ($Q$): rappresentano ciò che stiamo cercando (es. gli stati del decoder: "di cosa ho bisogno per generare la prossima parola?")
- **Key/Data** ($K$ o $X$): rappresentano ciò con cui confrontiamo la query (es. gli stati dell'encoder: "cosa offre ciascuna parola dell'input?")
- **Value** ($V$): l'informazione effettiva che viene estratta e combinata
Il calcolo generale, chiamato **dot-product attention**, è il seguente. Se abbiamo $N$ vettori di dati di dimensione $D$ e $M$ vettori query:
$$
E_{M \times N} = Q \, K^\top \quad \text{(punteggi di similitudine tramite prodotto scalare)}
$$
$$
A_{M \times N} = \text{softmax}(E, \text{dim}=1) \quad \text{(pesi di attenzione normalizzati per riga)}
$$
$$
Y_{M \times D} = A \, V \quad \text{(output: media pesata dei valori)}
$$
Per $M$ vettori query otteniamo $M$ vettori output. Tuttavia, in questa formulazione il prodotto scalare e la softmax **non sono apprendibili**: servono parametri.
### Cross-Attention
Per rendere il meccanismo apprendibile, si introducono matrici di pesi **apprendibili** $W_K$ e $W_V$ che trasformano i vettori dati prima di usarli:
$$
K = X \, W_K, \quad V = X \, W_V
$$
La formula diventa:
$$
Y = \text{softmax}(Q \, K^\top) \, V
$$
Nella cross-attention, le **query provengono da una sequenza** (es. il decoder: "cosa sto cercando?") e le **chiavi/valori da un'altra sequenza** (es. l'encoder: "cosa offro?"). Ogni query produce un output che è un mix pesato dell'informazione contenuta nei vettori dati, dove i pesi sono determinati dalla similarità query-chiave. Questo permette al decoder di "consultare" l'encoder in modo selettivo.
### Self-Attention
Nella **self-attention**, l'idea è applicata **all'interno della stessa sequenza**, cioè query, chiavi e valori provengono tutti dallo stesso input. Si introduce un'ulteriore matrice apprendibile $W_Q$:
$$
Q = X \, W_Q, \quad K = X \, W_K, \quad V = X \, W_V
$$
Intuitivamente, la self-attention permette a **ogni parola** di una frase di guardare e imparare da **tutte le altre parole** della stessa frase. Ogni token "chiede" (query) agli altri token (key) quanto siano rilevanti, e poi combina le loro informazioni (value) in proporzione.
Consideriamo la frase: *"A robot must obey the orders given to it by human beings except where such orders would conflict with the First Law."*
- La parola "it" deve capire che si riferisce a "robot" → la self-attention assegnerà un peso alto tra "it" e "robot"
- "such orders" deve capire che si riferisce a "the orders given it by human beings" → pesi alti verso quelle parole
- "The First Law" si riferisce all'intera Prima Legge → la self-attention cattura anche riferimenti così ampi
Ogni parola produce un output che è una combinazione pesata delle rappresentazioni di tutte le altre parole, dove i pesi riflettono la rilevanza contestuale.
### Codifica Posizionale (Positional Encoding)
Un problema importante della self-attention è che è **equivariante per permutazione**: se mescoli l'ordine delle parole nell'input, l'output cambia solo nell'ordine ma non nel contenuto. In pratica, la self-attention non sa se una parola è la prima, la terza o l'ultima della frase: non ha alcun senso dell'ordine intrinseco.
Per le sequenze di testo (o di qualsiasi tipo) l'ordine è fondamentale: "il gatto mangia il topo" è diverso da "il topo mangia il gatto". Per risolvere questo, si aggiunge un vettore di **codifica posizionale** $P_i$ a ogni input:
$$
\tilde{X}_i = X_i + P_i
$$
$P_i$ è un vettore che dipende unicamente dalla posizione $i$ nella sequenza. Così facendo, lo stesso token in posizioni diverse avrà rappresentazioni iniziali diverse, e la rete potrà distinguere l'ordine.
### Multi-Head Self-Attention
Invece di eseguire un singolo calcolo di self-attention, si eseguono **$H$ copie indipendenti** (dette "heads" o "teste") in parallelo. Ogni testa ha i **propri pesi** indipendenti ($W_Q^h$, $W_K^h$, $W_V^h$) e cattura **relazioni diverse** tra i token:
- Una testa potrebbe focalizzarsi sulle **relazioni grammaticali** (soggetto-verbo)
- Un'altra sulle **relazioni semantiche** (sinonimi, co-riferimenti)
- Un'altra ancora sulle **relazioni di prossimità** (parole vicine)
Gli output delle $H$ teste vengono **concatenati** e moltiplicati per una matrice di output $W_O$ che fonde le informazioni:
$$
O = \text{Concat}(Y_1, Y_2, \ldots, Y_H) \, W_O
$$
L'intuizione è che un singolo calcolo di attenzione potrebbe non catturare tutte le relazioni rilevanti; con più teste, il modello può catturarle in parallelo e poi fonderle in un'unica rappresentazione ricca.
### RNN vs Self-Attention
Le differenze tra RNN e Self-Attention sono fondamentali per capire perché i Transformer hanno sostituito le RNN:
- **Tipo di elaborazione**: le RNN elaborano un token alla volta in ordine sequenziale (il passo $t$ deve aspettare il risultato del passo $t-1$). La Self-Attention vede **tutti i token contemporaneamente** e li elabora in parallelo.
- **Dipendenze a lungo raggio**: nelle RNN, per collegare il primo e l'ultimo token di una sequenza lunga, l'informazione deve attraversare tutti i passi intermedi, degradandosi progressivamente (vanishing gradient). Nella Self-Attention, ogni token può "guardare direttamente" qualsiasi altro token, indipendentemente dalla distanza.
- **Parallelizzazione su GPU**: le RNN non sono parallelizzabili lungo la dimensione temporale (ogni passo dipende dal precedente). La Self-Attention è altamente parallelizzabile, sfruttando appieno la potenza delle GPU moderne.
- **Efficienza**: per sequenze lunghe, le RNN sono lente; la Self-Attention è molto più veloce in pratica grazie alla parallelizzazione, anche se ha complessità quadratica rispetto alla lunghezza della sequenza.
---
## 4. Il Transformer
### Introduzione
Il **Transformer** è stato introdotto nel 2017 da Vaswani et al. nel celebre paper *"Attention is All You Need"*. Il nome del paper è programmatico: il Transformer è basato **interamente** su self-attention e cross-attention, eliminando completamente la ricorrenza delle RNN.
Vantaggi fondamentali:
- **Elabora sequenze intere in parallelo** → addestramento molto più veloce e scalabile rispetto alle RNN
- **Modella dipendenze a lungo raggio** senza i problemi di vanishing gradient delle RNN, perché ogni token comunica direttamente con tutti gli altri
- **Fa assunzioni minime** sulla struttura dei dati di input: funziona con testo, immagini, audio e molto altro
- **Architettura unificata** utilizzabile con diverse modalità, il che lo rende estremamente versatile
Il Transformer ha rivoluzionato l'IA: alimenta chatbot (ChatGPT), traduttori, generatori di codice, generatori di immagini e molto altro.
### Architettura Encoder-Decoder
Il Transformer originale ha un'architettura encoder-decoder, simile concettualmente al Seq2Seq con RNN:
- L'**encoder** processa la sequenza di input (es. frase in inglese) e produce una rappresentazione contestualizzata
- Il **decoder** genera la sequenza di output (es. frase in italiano), un token alla volta, usando sia la propria sequenza parziale sia la rappresentazione dell'encoder tramite cross-attention
### Varianti
Dopo il paper originale, sono emerse tre varianti principali che si sono affermate per task diversi:
- **Encoder-Decoder** (Transformer originale): usato per traduzione automatica e task dove servono sia comprensione che generazione
- **Encoder-only** (es. BERT, Google): usato per la classificazione e la comprensione del testo. Non genera testo, ma produce rappresentazioni ricche del linguaggio utili per molti task
- **Decoder-only** (es. GPT, ChatGPT, OpenAI): usato per la generazione di testo. È l'architettura dominante nei Large Language Models (LLM) moderni
### Applicazioni
I Transformer sono diventati l'architettura dominante in praticamente tutti i campi dell'IA:
- Natural Language Processing (NLP): traduzione, riassunto, chatbot, generazione
- Computer Vision: classificazione, segmentazione, detection
- Elaborazione audio: riconoscimento vocale, generazione musicale
- Apprendimento multimodale: modelli che combinano testo, immagini, audio
La ragione di questa dominanza è che sono scalabili (più dati e più parametri → migliori prestazioni), paralleli (sfruttano le GPU), gestiscono dipendenze a lungo raggio e fanno poche assunzioni specifiche sul dominio.
---
## 5. Deep Learning per il Natural Language Processing (NLP)
### Cos'è l'NLP?
Il **Natural Language Processing** (Elaborazione del Linguaggio Naturale) comprende tutte le tecniche che permettono ai computer di elaborare, comprendere e generare linguaggio umano. È uno dei campi più importanti dell'IA perché il linguaggio è il modo principale con cui gli esseri umani comunicano e immagazzinano conoscenza. I task principali sono:
- **Sentiment Analysis**: dato un testo (es. una recensione), determinare l'emozione o l'opinione espressa (positiva, negativa, neutra)
- **Traduzione Automatica**: tradurre da una lingua all'altra (es. inglese → italiano)
- **Chatbot / Question Answering**: dato un input (domanda o istruzione), generare una risposta appropriata
- **Riassunto Automatico**: dato un documento lungo, produrre un riassunto que ne catturi l'essenza
- **Named-Entity Recognition (NER)**: identificare nel testo entità come nomi di persone, luoghi, organizzazioni
Tutti questi task condividono una necessità fondamentale: una **buona rappresentazione numerica** del testo.
### Sfide dell'NLP
L'NLP è difficile perché il linguaggio umano è intrinsecamente complesso e ambiguo:
- **Ambiguità**: la stessa parola può avere significati completamente diversi a seconda del contesto. *"bank"* può significare banca o riva del fiume. Solo il contesto disambigua.
- **Dipendenze a lungo raggio**: in frasi complesse come *"The book that the professor who I met yesterday recommended was fascinating"*, per capire che "fascinating" si riferisce a "book" bisogna collegare parole molto distanti nella frase.
- **Conoscenza del mondo**: per comprendere *"Paris is the capital of France"* servono informazioni fattuali non contenute nella frase stessa.
### Evoluzione degli approcci
- **Sistemi basati su regole**: grammatiche scritte a mano da esperti linguistici. Rigidi, fragili e impossibili da scalare a tutti i casi del linguaggio naturale.
- **NLP statistico**: modelli come n-grammi e Hidden Markov Models che usano probabilità calcolate su grandi corpora. Meglio delle regole, ma ancora limitati nel catturare relazioni complesse.
- **NLP con Deep Learning**: reti neurali profonde che imparano automaticamente dai dati, senza bisogno di feature ingegnerizzate a mano. È l'approccio moderno che ha rivoluzionato il campo.
### Il problema della rappresentazione
I computer non capiscono il testo direttamente: le parole sono sequenze di caratteri senza significato numerico. Servono **rappresentazioni numeriche** delle parole per poterle elaborare con reti neurali. Tre approcci principali, in ordine crescente di sofisticatezza:
#### a) One-Hot Encoding
Ogni parola del vocabolario viene rappresentata come un vettore con un solo 1 nella posizione corrispondente e tutti 0. Per un vocabolario [dog, cat, apple, car]:
- "dog" → [1, 0, 0, 0]
- "cat" → [0, 1, 0, 0]
**Problemi gravi**: i vettori di "dog" e "cat" sono **ortogonali** (prodotto scalare = 0), quindi il modello non vede alcuna relazione semantica tra di loro, nonostante siano entrambi animali. La dimensionalità è enorme (un vettore di milioni di dimensioni per vocabolari reali). I vettori sono sparsissimi (quasi tutti zeri). Non c'è nessuna nozione di contesto.
#### b) Bag of Words (BoW)
Rappresenta un **documento** (non una singola parola) contando le occorrenze delle parole. Per vocabolario [dog, cat, bone, eats]:
- "dog eats bone" → [1, 0, 1, 1]
**Problemi**: sparso (la maggior parte delle parole non appare in un singolo documento). Ignora completamente l'ordine: "dog eats bone" e "bone eats dog" producono lo stesso vettore, nonostante significhino cose diverse. Nessuna semantica: "dog" e "puppy" sono vettori totalmente diversi. Variante migliorata: **TF-IDF** che pesa le parole per la loro importanza nel documento rispetto al corpus intero, dando meno peso a parole comuni come "the" e più peso a parole discriminanti.
#### c) Rappresentazioni Neurali Continue (Embeddings)
Ogni parola è rappresentata da un **vettore denso** di numeri reali, tipicamente di 50-300 dimensioni (molto di più nei LLM moderni):
- "dog" → [0.21, -0.43, 0.05, 0.88, ...]
- "cat" → [0.19, -0.40, 0.07, 0.90, ...]
Il principio guida è l'**ipotesi distribuzionale**: *"parole che compaiono in contesti simili hanno significati simili"*. Poiché "dog" e "cat" appaiono in contesti simili (entrambi sono animali domestici), i loro vettori saranno vicini nello spazio. Questi vettori sono **appresi dai dati** tramite reti neurali, non costruiti a mano.
---
## 6. Word2Vec e Rappresentazioni delle Parole
### Cos'è Word2Vec?
**Word2Vec** (Mikolov et al., 2013, ricercatori di Google) è un metodo pionieristico che crea una rappresentazione vettoriale di lunghezza fissa per ogni parola del vocabolario, usando una rete neurale poco profonda (shallow). L'addestramento è **non supervisionato**: non servono etichette, basta un grande corpus di testo. Dopo l'addestramento, le parole semanticamente simili avranno vettori vicini nello spazio di rappresentazione: "king" sarà vicino a "queen", "dog" sarà vicino a "cat".
Esistono due approcci per implementare Word2Vec:
- **Continuous Bag-of-Words (CBOW)**: predice la parola centrale date le parole di contesto
- **Skip-gram**: predice le parole di contesto data la parola centrale
### Continuous Bag-of-Words (CBOW)
L'approccio CBOW assume che una **parola centrale** possa essere predetta dalle **parole di contesto** che la circondano in una finestra. Ad esempio, per la sequenza "the man loves his son" con finestra di 2:
- **Input** (contesto): "the", "man", "his", "son" (le 2 parole a sinistra e le 2 a destra)
- **Output** (target): "loves" (la parola centrale)
**Nota fondamentale**: non serve annotazione manuale! Si prende un qualsiasi grande corpus di testo (es. Wikipedia, libri, pagine web) e si generano automaticamente milioni di coppie input/output semplicemente scorrendo con una finestra. Questa è una forma di **self-supervised learning**: le etichette vengono dal testo stesso.
### Funzionamento della rete
- Ogni parola di contesto viene mappata al suo **vettore embedding** $W_i \in \mathbb{R}^d$ attraverso una matrice di embedding $W \in \mathbb{R}^{|V| \times d}$, dove $|V|$ è la dimensione del vocabolario e $d$ la dimensione dell'embedding. Questa matrice è **apprendibile**.
- Si calcola un **vettore contesto medio** facendo la media aritmetica degli embedding delle parole di contesto. Questo è il motivo del nome "bag-of-words": l'ordine delle parole di contesto viene perso nella media.
- Si applica un **layer lineare (output layer)** seguito da una **softmax** per ottenere una distribuzione di probabilità su tutte le parole del vocabolario.
- Si addestra con **Cross-Entropy Loss + SGD**: il modello deve assegnare alta probabilità alla parola target corretta.
### Dopo l'addestramento
Una volta addestrata la rete, si **scarta il layer di output**: non ci interessa il classificatore. Ciò che rimane e che ha valore è la **matrice di embedding** $W \in \mathbb{R}^{|V| \times d}$, i cui valori sono stati appresi con SGD durante l'addestramento. La riga $i$-esima di $W$ è la rappresentazione Word2Vec della $i$-esima parola del vocabolario. Ottenere il vettore di una parola è una semplice **operazione di lookup** (consultazione per indice).
### Limiti di Word2Vec
- **Embedding statico**: ogni parola ha un unico vettore indipendente dal contesto. La parola "bank" ha lo stesso vettore sia che significhi banca sia che significhi riva. I modelli moderni (es. BERT, GPT) producono embedding **contestualizzati** che cambiano in base alla frase.
- **Nessuna rappresentazione per parole fuori vocabolario (OOV)**: parole mai viste durante l'addestramento non hanno un vettore e non possono essere elaborate.
- **L'ordine delle parole è ignorato**: a causa dell'operazione di media nel CBOW, l'ordine delle parole di contesto non influenza il risultato.
---
## 7. Architettura del Transformer nel Dettaglio
### Componenti di un blocco Transformer
Un **blocco Transformer** prende in input una matrice di vettori (un vettore per ogni token della sequenza) e restituisce una matrice della **stessa dimensione**. Questo è importante: significa che i blocchi sono impilabili. Il blocco è composto da cinque componenti:
- **Attenzione**: il cuore del blocco. Può essere self-attention (nel encoder), masked self-attention (nel decoder), cross-attention (nel decoder per consultare l'encoder) o multi-head attention (più teste in parallelo).
- **Layer Normalization**: normalizza le attivazioni per stabilizzare il training.
- **MLP (Feed-Forward Network)**: una rete feed-forward standard che elabora ulteriormente le rappresentazioni.
- **Connessioni Residue (Skip Connections)**: scorciatoie che aiutano il flusso del gradiente.
- **Codifica Posizionale + Embedding**: il meccanismo che converte i token in vettori e aggiunge informazione sulla posizione.
Il blocco viene ripetuto $N$ volte nell'architettura completa. La profondità (il numero di blocchi $N$) è uno dei fattori che determinano la capacità del modello.
### Tokenizzazione
Prima di entrare nel Transformer, il testo deve essere convertito in numeri. Questo avviene in tre passi:
- **Tokenizzazione**: la frase viene spezzata in **token**, unità più piccole che possono essere parole intere, sotto-parole o anche singoli caratteri. Ad esempio, "Transformers are cool" potrebbe diventare ["Transform", "ers", " are", " cool"]. Un algoritmo popolare per questa fase è il **Byte Pair Encoding (BPE)**, che trova un buon compromesso tra dimensione del vocabolario e capacità di gestire parole rare.
- **Mappatura a Token ID**: ogni token unico viene associato a un **numero intero** (token ID). Es. "Transform" = 8746, "ers" = 388.
- **Conversione in vettori**: poiché le reti neurali lavorano meglio con vettori continui che con interi, ogni token ID viene convertito in un **vettore di embedding** tramite una matrice apprendibile, esattamente come in Word2Vec: ogni riga della matrice corrisponde al vettore di un token.
### Self-Attention con Scaled Dot-Product
La formula completa della self-attention, come usata nel Transformer, include una divisione per $\sqrt{d}$ che è cruciale per la stabilità numerica:
$$
\text{SA}(Q, K, V) = \text{softmax}\!\left(\frac{Q \, K^\top}{\sqrt{d}}\right) V
$$
Dove:
- $Q = X \, W_Q$ (query: "cosa cerco?")
- $K = X \, W_K$ (chiavi: "cosa offro?")
- $V = X \, W_V$ (valori: "quale informazione porto?")
- La divisione per $\sqrt{d}$ evita che i prodotti scalari diventino troppo grandi, il che causerebbe gradienti della softmax quasi nulli (saturazione). Senza questa normalizzazione, il training diventa instabile.
- Le matrici $W_Q$, $W_K$, $W_V$ sono **apprese via backpropagation**
- L'output è una nuova rappresentazione che cattura sia il significato di ogni parola sia il suo contesto nella sequenza
### Multi-Head Attention (dettaglio)
Con $H$ teste, ogni testa opera indipendentemente con i propri pesi su porzioni diverse dello spazio di rappresentazione:
- Si calcolano $H$ output indipendenti di self-attention, ciascuno con i propri $W_Q^h$, $W_K^h$, $W_V^h$
- Gli output vengono **concatenati** lungo la dimensione delle feature
- Si moltiplica il risultato per la matrice di proiezione $W_O$ che **fonde** le informazioni catturate da tutte le teste in un'unica rappresentazione coerente
Esempio: nella frase *"He swung the bat with incredible force"*, una testa potrebbe catturare la relazione sintattica "swung" → "bat" (azione-oggetto), un'altra la relazione descrittiva "incredible" → "force" (modificatore-modificato), un'altra ancora il soggetto "He" → "swung".
### Masked Self-Attention (nel Decoder)
Nel decoder, la self-attention è **mascherata** (masked): il modello **non può guardare i token futuri** durante la predizione. Questo è fondamentale durante la generazione di testo.
Esempio: nella frase "He swung the bat", per predire "swung" il modello può vedere solo "He", non "the bat". Se potesse vedere i token futuri, non imparerebbe mai a predirli: potrebbe semplicemente copiare l'informazione dal futuro, annullando l'intero scopo dell'addestramento. La maschera è implementata come una matrice triangolare inferiore che imposta a $-\infty$ i punteggi di attenzione verso i token futuri, cosicché la softmax li trasformi in pesi pari a zero.
### Cross-Attention (nel Decoder)
La cross-attention è il meccanismo che permette al decoder di **consultare l'output dell'encoder** durante la generazione. È strutturalmente identica alla self-attention, ma con una differenza cruciale nell'origine dei vettori:
- **Query** ($Q$): proviene dal decoder, cioè dalla sequenza di output generata finora ("cosa sto cercando nell'input?")
- **Key** ($K$) e **Value** ($V$): provengono dall'encoder, cioè dalla rappresentazione contestualizzata dell'input sorgente ("cosa offre l'input?")
Così il decoder può decidere, ad ogni passo, quali parti dell'input sono più rilevanti per generare il token successivo.
### Feed-Forward Network (FFN)
Dopo l'attenzione, l'informazione di ogni token passa attraverso una **rete feed-forward** (un MLP standard con due layer lineari e una non-linearità). Mentre l'attenzione cattura le **relazioni tra token** (contesto), la FFN elabora la **rappresentazione di ciascun token individualmente**, decidendo "cosa fare" con l'informazione contestuale raccolta dall'attenzione. Si può pensare all'attenzione come "raccolta di informazioni" e alla FFN come "elaborazione delle informazioni".
### Layer Normalization
I Transformer hanno miliardi di parametri e decine o centinaia di layer. I gradienti possono facilmente **esplodere** (diventare enormi) o **svanire** (diventare quasi zero) mentre si propagano attraverso tanti layer, rendendo impossibile il training. La **Layer Normalization** risolve questo problema normalizzando le feature di ogni token per avere **media zero e deviazione standard unitaria**, stabilizzando le attivazioni e permettendo un training efficace.
### Skip Connections (Connessioni Residue)
Come nelle ResNet per le CNN, si usano **connessioni skip** (o residue) per combattere il vanishing gradient nelle architetture molto profonde. L'idea è semplice ma potente: l'output di ogni sotto-blocco (attenzione o FFN) viene **sommato** al suo input:
$$
\text{output} = \text{SubLayer}(x) + x
$$
Questo crea un percorso diretto per il gradiente: anche se il sotto-blocco non impara nulla, il gradiente può fluire senza ostacoli attraverso la connessione skip. In pratica, il sotto-blocco deve imparare solo la "differenza" rispetto all'input, non l'intera trasformazione.
### Codifica Posizionale
I Transformer, a differenza delle RNN, vedono l'intero input in parallelo e quindi **non hanno un senso d'ordine intrinseco**. Per il linguaggio l'ordine è cruciale ("il gatto mangia il topo" ≠ "il topo mangia il gatto"). Le **codifiche posizionali** risolvono questo: sono vettori che dipendono solo dalla posizione del token nella sequenza e vengono **sommati** agli embedding dei token. Possono essere funzioni fisse (sinusoidi nel paper originale) o vettori apprendibili.
### Addestramento del Transformer
Per un task di traduzione (es. en → it):
- La frase sorgente intera viene data all'encoder, che la elabora tutta in parallelo
- Al decoder viene data solo una parte della frase target (ad esempio, per predire "studente", il decoder riceve "Sono uno")
- Il modello è addestrato a **predire il prossimo token** nella sequenza target, usando la cross-entropy loss che confronta la distribuzione predetta dal modello con il token corretto
- Si può usare anche la **KL Divergence** come funzione di loss
In output, il modello predice i logit (punteggi non normalizzati) per ogni token del vocabolario. Si applica una softmax per ottenere probabilità, si seleziona il token più probabile (o si campiona), e poi lo si **detokenizza** (converte da token ID a testo leggibile).
---
## 8. Varianti del Transformer: BERT e GPT
### BERT (Encoder-only)
**BERT** (*Bidirectional Encoder Representations from Transformers*, Google, 2018) è un Transformer **solo encoder**, progettato per task di **comprensione** del linguaggio. Il punto di forza di BERT è che usa **attenzione bidirezionale**: ogni token può vedere tutti gli altri token della frase, sia a sinistra che a destra. Questo gli permette di costruire rappresentazioni ricchissime in cui ogni parola è informata dal contesto completo.
#### Architettura
- Stack di blocchi encoder del Transformer (senza decoder): non genera testo, ma produce rappresentazioni del testo.
- Token speciale **[CLS]** ("classification") aggiunto all'inizio della sequenza. Grazie alla self-attention, il [CLS] raccoglie informazione da tutti gli altri token e diventa una **rappresentazione aggregata dell'intera frase**.
- Si aggiunge un **classificatore lineare** (MLP + softmax) sopra l'embedding del token [CLS] per risolvere task di classificazione.
#### Addestramento: Masked Language Modelling (MLM)
BERT viene addestrato con un task di self-supervised learning chiamato Masked Language Modelling:
- Si **mascherano casualmente il 15%** dei token dell'input, sostituendoli con un token speciale [MASK]
- Il modello deve **predire i token mascherati** basandosi sul contesto circostante (sia sinistro che destro)
- Esempio: "The man [MASK] his son" → il modello deve predire "loves"
- Non servono annotazioni manuali: le etichette sono le parole originali prima del mascheramento
#### Uso dopo il pre-training
Dopo il pre-training (non supervisionato su grandi corpora), BERT viene **fine-tunato** su task specifici con un piccolo dataset etichettato: classificazione di sentiment, NER, question answering, ecc. Il fine-tuning è molto efficiente perché le rappresentazioni pre-addestrate catturano già una comprensione profonda del linguaggio.
### GPT (Decoder-only)
**GPT** (*Generative Pre-trained Transformer*, OpenAI) è un Transformer **solo decoder**, progettato per la **generazione** di testo. A differenza di BERT, GPT usa **masked self-attention** (attenzione unidirezionale): ogni token può vedere solo i token **a sinistra**, mai quelli futuri. Questo è necessario per la generazione: quando il modello produce testo, i token futuri non esistono ancora.
#### Caratteristiche fondamentali
- È un **modello autoregressivo**: genera un token alla volta, da sinistra a destra. Ogni token generato viene aggiunto all'input per il passo successivo.
- Usa **masked self-attention**: la maschera impedisce di guardare i token futuri, costringendo il modello a basarsi solo sul contesto passato.
- Non ha encoder: riceve solo il prompt/input e genera la continuazione token per token.
#### Addestramento
GPT viene addestrato a **predire la parola successiva** in enormi quantità di testo non etichettato. L'obiettivo è massimizzare la probabilità del prossimo token data la sequenza precedente. Questo addestramento è completamente **self-supervised**: il testo stesso fornisce le etichette (ogni parola è la "risposta corretta" per la sequenza che la precede). Attraverso questo processo su miliardi di parole, il modello impara grammatica, semantica, fatti, ragionamento e molto altro.
#### Large Language Models (LLM)
Quando si prendono Transformer molto grandi (miliardi di parametri) e li si addestra su enormi corpora di testo (trilioni di token), si ottengono i **Large Language Models (LLM)**:
- I modelli stato dell'arte (es. ChatGPT, GPT-4, LLaMA) adottano l'architettura **decoder-only**
- In fase di inferenza, un token viene prodotto alla volta: il modello genera un token, lo aggiunge all'input e ricalcola per produrre il successivo. Questo ciclo continua fino a quando viene generato il token speciale **[EOS]** (End Of Sequence)
- Questa generazione autoregressiva è ciò che permette ai chatbot di produrre risposte di lunghezza arbitraria
### Confronto tra BERT e GPT
- **Focus**: BERT è per la comprensione (capire il testo), GPT è per la generazione (produrre testo)
- **Contesto**: BERT è bidirezionale (vede tutta la frase), GPT è unidirezionale (vede solo i token passati)
- **Task tipici**: BERT eccelle in classificazione, NER, question answering; GPT in scrittura, chatbot, generazione di codice
- **Self-Attention**: BERT usa self-attention standard (non mascherata) perché vede l'intera sequenza; GPT usa masked self-attention perché non deve "sbirciare" il futuro
---
## 9. Vision Transformer (ViT)
### Dal testo alle immagini
Il Transformer, nato per l'NLP, si è rivelato **sorprendentemente versatile** anche per la Computer Vision. Il paper *"An Image is Worth 16×16 Words"* (Dosovitskiy et al., 2020) ha dimostrato che un Transformer encoder-only, progettato per elaborare sequenze di token testuali, può essere usato con successo per la **classificazione di immagini**, senza alcuna convoluzione. L'unica modifica necessaria è nel modo in cui l'immagine viene convertita in una sequenza.
### Il problema: come trasformare un'immagine in una sequenza?
Il Transformer lavora con **sequenze di vettori**. Un'immagine è una griglia 2D di pixel. Come si colma il divario?
- **Opzione 1**: trattare ogni pixel come un vettore 3D (canali RGB). Per un'immagine $224 \times 224$, la sequenza avrebbe $224 \times 224 = 50.176$ token — troppo lungo, la self-attention ha complessità quadratica e richiederebbe risorse computazionali enormi.
- **Opzione 2**: appiattire tutta l'immagine in un unico vettore — ma così non si ha più una sequenza, si perde completamente la struttura spaziale e non si può applicare la self-attention.
- **Soluzione (ViT)**: dividere l'immagine in **patch** (pezzi rettangolari), un compromesso ottimale tra granularità e gestibilità.
### Patchification
L'immagine viene divisa in piccoli **patch** di dimensione fissa, tipicamente $16 \times 16$ pixel, ciascuno con 3 canali (RGB). Per un'immagine $224 \times 224$:
$$
\text{Numero di patch} = \left(\frac{224}{16}\right)^2 = 196
$$
La sequenza risultante ha 196 token: un numero gestibile per il Transformer, molto più ragionevole dei 50.176 della soluzione per-pixel.
### Pipeline completa del ViT
- **Patchification**: l'immagine viene divisa in una griglia di patch non sovrapposti (es. $16 \times 16$ pixel ciascuno)
- **Flattening**: ogni patch viene appiattito da una matrice $16 \times 16 \times 3$ a un vettore unidimensionale di $768$ dimensioni
- **Patch Embedding**: un MLP (layer fully-connected) trasforma il vettore appiattito in un embedding di dimensione fissa (es. $\mathbb{R}^{512}$). Questo è l'analogo dell'embedding dei token nel NLP.
- **Positional Embedding**: si aggiunge un embedding posizionale **apprendibile** a ogni patch embedding, per codificare la posizione spaziale del patch nell'immagine. Senza di esso, il modello non saprebbe se un patch viene dall'angolo in alto a sinistra o dal centro.
- **Token [CLS]**: come in BERT, si aggiunge un embedding apprendibile speciale [CLS] come input aggiuntivo. Questo token, attraverso la self-attention con tutti i patch, raccoglie informazione dall'intera immagine.
- **Transformer Encoder**: i patch embedding (+ [CLS] + posizioni) passano attraverso $N$ blocchi Transformer encoder standard, identici a quelli del NLP
- **Classificatore**: un MLP + softmax applicato all'output del token [CLS] per ottenere la predizione di classe
### Vantaggi del ViT
- Sfrutta l'architettura Transformer standard **senza alcuna convoluzione**: dimostra che le CNN non sono necessarie per la computer vision
- Eccelle con **grandi dataset** di addestramento: con pochi dati le CNN sono ancora competitive, ma con milioni di immagini il ViT supera le CNN
- **Altamente scalabile**: modelli più grandi (ViT-Large, ViT-Huge) con più dati producono risultati sempre migliori
### Apprendimento Multimodale
I **Vision Language Models (VLM)** combinano comprensione visiva e generazione testuale in un unico modello:
- Un **ViT** estrae feature dall'immagine (il "vedere")
- Un **LLM decoder-only** genera testo condizionato sulle feature visive + input testuale (il "parlare")
Questo permette task come:
- **Image captioning**: data un'immagine, generare una descrizione testuale
- **Visual Question Answering (VQA)**: data un'immagine e una domanda, generare una risposta
---
## 10. Segmentazione di Immagini
### Dalla classificazione alla segmentazione
La **classificazione** assegna una singola etichetta all'intera immagine (es. "gatto"). L'**object detection** localizza gli oggetti con bounding box e li classifica. La **segmentazione** va oltre: assegna una **classe a ogni singolo pixel** dell'immagine, producendo una mappa dettagliata di cosa c'è dove.
Nella segmentazione, i dati di training hanno il formato $(X, Y)$ dove $X \in \mathbb{R}^{3 \times H \times W}$ è l'immagine RGB e $Y \in \mathbb{R}^{H \times W}$ è la **mappa di segmentazione** (segmentation map), una matrice dove ogni elemento contiene l'etichetta di classe del pixel corrispondente. L'obiettivo è predire una mappa di output con le stesse dimensioni spaziali dell'immagine di input ($H \times W$).
### Approccio Sliding Window
Un approccio diretto ma poco pratico:
- Si estrae una piccola finestra (patch) centrata su ogni pixel dell'immagine
- Si usa una CNN (es. AlexNet) per classificare il **pixel centrale** di ciascuna finestra
- Si ripete per ogni posizione nell'immagine
La finestra fornisce **contesto** al singolo pixel (un pixel isolato non ha abbastanza informazione per essere classificato), ma il problema è l'enorme **inefficienza**: per un'immagine $224 \times 224$ servono circa 50.000 forward pass della CNN, uno per ogni pixel. Inoltre, le finestre sovrapposte condividono gran parte del contenuto, ma la rete ricalcola tutto da zero ogni volta, senza riutilizzare feature condivise.
### Fully Convolutional Network (FCN)
L'idea delle FCN è prendere l'**intera immagine** come input ed elaborarla in un **unico forward pass**, producendo direttamente la mappa di segmentazione.
#### Modifica chiave rispetto alle CNN di classificazione
Nelle CNN per classificazione (es. AlexNet, VGG), la parte finale è un MLP (fully-connected) che produce un unico vettore di predizione. Questo **distrugge l'informazione spaziale**: dopo il flattening non sappiamo più "dove" nell'immagine si trova cosa. Nelle FCN si **sostituisce il layer fully connected con un layer convoluzionale**, preservando la struttura spaziale dell'output. Si applica una convoluzione sulle feature map finali (shape: $[d \times h \times w]$) per ottenere score per ogni classe (shape: $[K \times h \times w]$, dove $K$ è il numero di classi), seguita da softmax + argmax per la predizione pixel-per-pixel.
#### Il problema della risoluzione
A causa delle operazioni di **pooling** nella CNN, le dimensioni spaziali delle feature map si riducono progressivamente (es. $h < H$, $w < W$). L'output ha risoluzione inferiore alla ground truth, rendendo impossibile la supervisione diretta (le dimensioni non coincidono). Rimuovere i layer di pooling risolverebbe il problema ma **aumenterebbe enormemente la complessità computazionale** e costringerebbe la rete a elaborare informazione ridondante ad alta risoluzione.
#### Soluzione: architettura downsampling + upsampling
La rete viene progettata con due parti complementari:
- **Parte di downsampling** (encoder): come una CNN classifica standard, riduce progressivamente la risoluzione estraendo feature di alto livello a bassa risoluzione (es. $D_3 \times H/4 \times W/4$)
- **Parte di upsampling** (decoder): riporta le feature alla risoluzione originale $H \times W$, "espandendo" le feature map
### Tecniche di Upsampling
Per aumentare la risoluzione delle feature map (upsampling), esistono diverse tecniche:
- **Nearest Neighbour**: ogni valore viene copiato nelle posizioni adiacenti secondo un kernel fisso (es. $2 \times 2$). Semplice ed efficiente, ma produce risultati "a blocchi" e non è apprendibile.
- **Bed of Nails**: il valore viene copiato nell'angolo in alto a sinistra del kernel, il resto viene riempito con zeri. Produce molti zeri nell'output.
- **Max Unpooling**: durante il max-pooling nella fase di downsampling si memorizza la **posizione** dell'elemento massimo. Nella fase di unpooling corrispondente, il valore viene reinserito esattamente in quella posizione (una sorta di "bed of nails" mirata). Questo preserva meglio l'informazione spaziale, ma richiede che ogni layer di pooling abbia un layer di unpooling corrispettivo.
- **Transposed Convolution (Convoluzione Trasposta)**: l'approccio più potente perché è **apprendibile**. A differenza della convoluzione regolare (che riduce la risoluzione), la convoluzione trasposta la **aumenta**. L'input fornisce i pesi per il filtro, si usa uno stride > 1 per espandere l'output, e i pesi del kernel sono appresi via backpropagation. Nelle regioni dove le uscite dei diversi pixel si sovrappongono, i valori vengono sommati. Es. un kernel $3 \times 3$ con stride 2 e padding 1 raddoppia la risoluzione.
### U-Net
**U-Net** è un'architettura fully convolutional progettata originariamente per la segmentazione di **immagini mediche** (Ronneberger et al., 2015), divenuta poi estremamente popolare per molti task di Computer Vision, inclusa la segmentazione generica e persino la generazione di immagini (nei modelli di diffusione).
#### Il problema che risolve
Nella FCN standard, la parte di downsampling **perde informazione spaziale** a causa delle ripetute operazioni di pooling: dettagli come bordi precisi, texture fini e posizioni esatte degli oggetti vengono progressivamente cancellati. Una volta che queste informazioni sono eliminate nel **bottleneck** (il punto di minima risoluzione della rete), la parte di upsampling **non può recuperarle**: non si può ricreare informazione che è stata distrutta.
#### Soluzione: Skip Connections (Connessioni Scorciatoia)
L'intuizione geniale di U-Net è **copiare le feature da ciascun livello del downsampling al livello corrispondente dell'upsampling**:
- Le feature ad alta risoluzione della parte di downsampling vengono **concatenate lungo la dimensione dei canali** (non sommate, ma affiancate, creando feature map più "larghe") alle feature upsampliate nel livello corrispondente
- Questo dà alla parte di upsampling accesso simultaneo a due tipi di informazione: le feature **ad alta risoluzione e basso livello semantico** (bordi, texture, dettagli spaziali precisi) dal downsampling e le feature **a bassa risoluzione e alto livello semantico** (comprensione degli oggetti, contesto globale) dal bottleneck
- L'architettura risultante ha una forma caratteristica a **"U"**, da cui il nome: il ramo sinistro scende (downsampling), il fondo è il bottleneck e il ramo destro sale (upsampling), con connessioni orizzontali tra i livelli corrispondenti
#### Struttura schematica
```
Input → [Conv-BN-ReLU-Pool] × N → Bottleneck → [UpConv-Cat-Conv-BN-ReLU] × N → Output
         ↓_______________↓_________↓______________↑_______________↑
                    Skip Connections (concatenazione)
```
Le skip connections della U-Net sono concettualmente simili a quelle delle ResNet, ma con una differenza: nelle ResNet i residui vengono **sommati**, nella U-Net vengono **concatenati**, preservando entrambe le fonti di informazione senza forzarne la combinazione.
