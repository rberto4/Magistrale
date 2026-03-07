## Indice cap 8-9
1. [Dai Pixel ai Concetti: Limiti degli MLP per le Immagini](#1-dai-pixel-ai-concetti-limiti-degli-mlp-per-le-immagini)
2. [Reti Neurali Convoluzionali (CNN)](#2-reti-neurali-convoluzionali-cnn)
3. [Filtri e Operazione di Convoluzione](#3-filtri-e-operazione-di-convoluzione)
4. [Feature Maps e Gerarchia delle Feature](#4-feature-maps-e-gerarchia-delle-feature)
5. [Stride, Padding e Pooling](#5-stride-padding-e-pooling)
6. [Architetture CNN Storiche](#6-architetture-cnn-storiche)
7. [ResNet e Skip Connections](#7-resnet-e-skip-connections)
8. [Visualizzazione di ciò che le CNN Apprendono](#8-visualizzazione-di-ciò-che-le-cnn-apprendono)
9. [Transfer Learning](#9-transfer-learning)
10. [Procedure di Fine-Tuning](#10-procedure-di-fine-tuning)
11. [Fine-Tuning in Pratica: Criteri di Scelta](#11-fine-tuning-in-pratica-criteri-di-scelta)
12. [Applicazioni del Transfer Learning](#12-applicazioni-del-transfer-learning)

---

## 1. Dai Pixel ai Concetti: Limiti degli MLP per le Immagini

### Cos'è un'immagine per un computer

Un **pixel** è l'unità più piccola di un'immagine digitale e rappresenta un singolo punto di colore o intensità. Un'immagine è una collezione di pixel: un'immagine in scala di grigi è semplicemente una matrice di numeri nell'intervallo $[0, 255]$. La sfida fondamentale della visione artificiale è: guardando i pixel, come può un computer rispondere alla domanda "chi è nell'immagine?"

### MLP (Fully Connected Neural Network) per la classificazione di immagini

Quando l'input è un'immagine 2D, il primo passo in un MLP è **appiattire** (flatten) l'immagine in un vettore colonna, che viene poi dato in input alla rete. Gli MLP sono composti da strati fully connected, dove ogni input è connesso a tutti i neuroni dello strato successivo. Per questo vengono spesso chiamati **Fully Connected Neural Network (FCNN)**.

### Tre limitazioni fondamentali degli MLP

**Limitazione 1 — Troppi parametri**: per un'immagine in scala di grigi di soli $100 \times 100$ pixel servono 10.000 neuroni di input. Con un singolo hidden layer da 1.000 unità, la matrice dei pesi ha $10.000 \times 1.000 = 10\text{M}$ parametri. Con reti più profonde o immagini ad alta risoluzione (es. $1024 \times 768 = 786\text{K}$ pixel) il numero di parametri diventa computazionalmente proibitivo.

**Limitazione 2 — Nessuna consapevolezza della struttura spaziale**: l'appiattimento distrugge la struttura 2D dell'immagine. L'MLP tratta ogni pixel indipendentemente e non sa che pixel vicini sono correlati. Ad esempio, un bordo tra il pixel $(10, 10)$ e il pixel $(12, 10)$ diventa due valori distanti nel vettore. La rete perde la nozione di pattern locali (bordi, forme) e deve apprenderli da zero, sprecando computazione.

**Limitazione 3 — Scarsa generalizzazione**: troppi parametri causano overfitting. Piccoli spostamenti nell'immagine (es. spostare una cifra di pochi pixel) producono input completamente diversi per l'MLP. Gli MLP non riconoscono che l'oggetto è lo stesso, solo spostato — mancano di **translation invariance** (invarianza per traslazione).

### Sfruttare la struttura locale delle immagini

Nelle immagini i **pixel vicini sono correlati**: gruppi locali di pixel formano bordi, angoli, texture, e questi pattern si ripetono in diverse zone dell'immagine. Non serve guardare l'intera immagine per rilevare feature significative: basta una piccola patch. L'idea chiave è:

- **Suddividere** l'immagine in piccole patch (es. $7 \times 7$), appiattirle e darle come input alla rete → si preserva la **connettività locale** e si risparmiano parametri

- **Riutilizzare** lo stesso set di pesi per tutte le patch → se i pesi hanno imparato a rilevare bordi orizzontali, li troveranno ovunque nell'immagine → ulteriore risparmio di parametri

Queste due idee — connettività locale e condivisione dei pesi — sono i principi fondanti delle **Reti Neurali Convoluzionali**.

---

## 2. Reti Neurali Convoluzionali (CNN)

### Principi fondamentali

Le **Convolutional Neural Networks (CNN)** si basano su due concetti chiave:

- **Local connectivity (connettività locale)**: ogni neurone in uno strato convoluzionale è connesso solo a una piccola patch dell'input, non all'intera immagine. Questo permette al modello di catturare **pattern locali** (bordi, texture).

- **Weight sharing (condivisione dei pesi)**: un singolo set di pesi (chiamato **filtro** o **kernel**) viene usato lungo tutta l'immagine. Lo stesso filtro rileva la stessa feature ovunque si trovi.

### Vantaggi delle CNN rispetto agli MLP

- **Enorme risparmio di parametri**: i pesi vengono condivisi anziché duplicati per ogni posizione

- **Translation invariance intrinseca**: spostando l'input di pochi pixel, la predizione del modello non cambia, perché lo stesso filtro scorre sull'intera immagine

- **Migliore generalizzazione**: meno parametri significano meno overfitting

Le CNN sono progettate specificatamente per dati immagine.

---

## 3. Filtri e Operazione di Convoluzione

### Cosa sono i filtri

Un **filtro** (o kernel) è una piccola matrice di pesi, tipicamente $3 \times 3$ o $5 \times 5$. Ad esempio un filtro $3 \times 3$:

$$

\begin{bmatrix} w_1 & w_2 & w_3 \\ w_4 & w_5 & w_6 \\ w_7 & w_8 & w_9 \end{bmatrix}

$$

Il filtro scorre (slide) sull'immagine e ad ogni posizione calcola il **prodotto scalare** tra il filtro e la patch dell'immagine corrispondente. Il prodotto sarà alto quando la patch contiene il pattern che il filtro sta cercando. I filtri possono rilevare specifici pattern locali: bordi, angoli, texture.

### Operazione di convoluzione

Il termine "convoluzionale" deriva dal fatto che l'operazione applicata è simile alla **convoluzione discreta** usata nell'elaborazione di segnali e immagini. L'implementazione ingenua (nested loop che scorre il filtro pixel per pixel) è sequenziale e proibitivamente lenta. L'implementazione efficiente sfrutta operazioni tensoriali ottimizzate per GPU.

### Esempio: filtro di Sobel

Il **filtro di Sobel** è un classico filtro **handcrafted** (non appreso) per il rilevamento dei bordi. Evidenzia le zone di rapido cambiamento di intensità. Il filtro $G_x$ rileva i **bordi verticali**: pesa negativamente i pixel a sinistra e positivamente quelli a destra, amplificando le transizioni di intensità. Nelle regioni piatte la risposta è zero, nelle transizioni è alta. Analogamente, il filtro $G_y$ rileva i bordi orizzontali. Combinando le due mappe si ottiene una mappa completa dei bordi.

### I filtri vengono appresi

I filtri delle CNN non sono handcrafted come Sobel: sono semplici set di pesi che vengono **appresi automaticamente** tramite gradient descent. Un filtro $3 \times 3$ disposto in matrice è identico a un vettore di 9 pesi — semplicemente arrangiato in 2D per comodità operativa.

---

## 4. Feature Maps e Gerarchia delle Feature

### Feature maps

Un singolo filtro può apprendere **un solo pattern locale** (es. bordi orizzontali). Ma in un'immagine ci sono molti pattern diversi (bordi verticali, bordi obliqui, angoli, texture). L'idea è apprendere un **insieme di filtri** dove ciascuno rileva un pattern diverso. Ogni filtro produce una **feature map** (o canale): se si usano 16 filtri, si ottengono 16 feature map.

### Implementazione efficiente

Più filtri vengono implementati come un singolo **tensore** con dimensioni:

$$

\text{dimensioni filtro} = \underbrace{K \times K}_{\text{kernel size}} \times \underbrace{C_{in}}_{\text{canali input}} \times \underbrace{C_{out}}_{\text{num. filtri}}

$$

Ad esempio, un tensore $9 \times 9 \times 3 \times 16$ rappresenta 16 filtri di dimensione $9 \times 9$ applicati a 3 canali di input (es. RGB).

### Gerarchia delle feature

Le CNN impilano più strati convoluzionali creando una **gerarchia di feature**:

- **Strati iniziali**: rilevano feature semplici e di basso livello — bordi, angoli, texture

- **Strati intermedi**: combinano le feature semplici in pattern più complessi — forme, parti di oggetti

- **Strati profondi**: catturano feature astratte e di alto livello — parti di oggetti, concetti semantici

Ogni strato costruisce sulle feature dello strato precedente, passando progressivamente dal pixel al concetto.

---

## 5. Stride, Padding e Pooling

### Stride

Lo **stride** indica di quanti pixel si sposta il filtro ad ogni operazione di convoluzione:

- **Stride = 1**: il kernel si sposta di 1 pixel alla volta, preservando le dimensioni spaziali (a meno dei bordi persi)

- **Stride = 2**: larghezza e altezza della feature map di output vengono dimezzate

La dimensione dell'output è calcolata come:

$$

H_{out} = \frac{H_{in} - K + 1}{\text{stride}}

$$

dove $H_{in}$ è la dimensione spaziale dell'input e $K$ è la dimensione del kernel.

### Padding

L'operazione di convoluzione causa la **perdita di pixel al perimetro** dell'immagine. Per un kernel $3 \times 3$ si perde 1 pixel per lato; questa perdita si accumula con strati convoluzionali successivi. Il **padding** aggiunge pixel extra al perimetro dell'input per preservare le dimensioni spaziali dell'output. Il padding più comune è lo **zero padding**, che aggiunge pixel con valore zero.

### Pooling

Il **pooling** è un'operazione di **downsampling** applicata tipicamente dopo uno strato convoluzionale. Esistono diversi tipi (max pooling, average pooling). Il pooling è definito da dimensione del kernel (es. $2 \times 2$) e stride (es. 2); a differenza della convoluzione, **il kernel del pooling non ha pesi** apprendibili. Il pooling viene applicato **per canale**: riduce solo le dimensioni spaziali, senza modificare il numero di canali.

Vantaggi del pooling:

- **Riduce le dimensioni** delle feature map → risparmio di memoria e computazione

- **Introduce translation invariance** → piccoli spostamenti dell'input non cambiano l'output

Intuitivamente, invece di guardare ogni pixel, il pooling **riassume** ciò che conta in una piccola regione. Da notare che una convoluzione con stride = 2 produce lo stesso effetto di un pooling $2 \times 2$ con stride 2.

---

## 6. Architetture CNN Storiche

### LeNet (1998)

**LeNet** è una CNN semplice che segue un pattern ricorrente: Conv → ReLU → Pooling → (ripeti) → Flatten → Fully Connected Layer(s) → Softmax → Output. Gli strati iniziali catturano pattern di basso livello (bordi) e quelli finali pattern di alto livello (forme). L'operazione di **flatten** converte le feature map 2D in un vettore colonna su cui applicare le trasformazioni affini degli strati fully connected (in PyTorch: `torch.flatten`).

### AlexNet (2012)

**AlexNet** è stata una delle prime grandi storie di successo del deep learning e ha segnato l'inizio dell'era del deep learning moderno. Ha vinto la sfida ImageNet ILSVRC 2012 con un miglioramento di circa l'11% rispetto al secondo classificato. AlexNet ha introdotto diverse innovazioni fondamentali:

- È possibile addestrare reti **più profonde** (8 strati) con successo

- Le **GPU** possono essere sfruttate per addestrare DNN su larga scala

- La funzione di attivazione **ReLU** velocizza il training rispetto a sigmoid/tanh

- La **regolarizzazione tramite dropout** previene l'overfitting nelle reti con molti parametri

- La **data augmentation** (crop casuali, flip) aumenta la dimensione effettiva dei dati con effetto regolarizzante

Queste tecniche rimangono ancora oggi fondamentali per l'addestramento di DNN molto profonde.

### VGG (2014)

**VGG** (Visual Geometry Group, Oxford University) ha dimostrato che si può andare ancora più in profondità usando **filtri piccoli** $3 \times 3$ in tutti gli strati. Ha superato AlexNet nella sfida ILSVRC 2014, basandosi sull'idea: **semplicità + profondità = rete più potente**. Caratteristiche principali:

- Filtri uniformi $3 \times 3$ in tutti gli strati convoluzionali (a differenza di AlexNet che mescolava filtri di dimensioni diverse)

- Due convoluzioni $3 \times 3$ impilate equivalgono a una convoluzione $5 \times 5$, ma con **meno parametri** e quindi meno overfitting

- Struttura ripetitiva e personalizzabile: VGG è disponibile in diverse profondità (da 11 a 19 strati)

VGG è diventata molto popolare ed è stata usata in moltissimi task (object detection, neural style transfer).

---

## 7. ResNet e Skip Connections

### Il problema della profondità

Le **Deep Residual Networks (ResNet, 2015)** hanno rivoluzionato la profondità delle reti neurali, arrivando fino a **152 strati** (8 volte VGG) senza incorrere in overfitting.

### Idea fondamentale: residual learning

Sia $H(x)$ il mapping che uno strato deve apprendere. Anziché apprendere direttamente $H(x)$, ResNet imposta il problema come:

$$

H(x) = F(x) + x

$$

dove $F(x)$ è il **residuo** (ciò che lo strato deve apprendere) e $x$ è la **skip connection** (connessione diretta dall'input all'output dello strato). Lo strato deve apprendere solo la differenza rispetto all'identità:

$$

F(x) = H(x) - x

$$

Se non c'è nulla da apprendere, $F(x)$ viene spinto verso zero e lo strato implementa semplicemente la funzione identità.

### Perché le skip connections risolvono il vanishing gradient

Senza skip connection, il gradiente attraverso uno strato è:

$$

\frac{\partial L}{\partial a_{i-1}} = \frac{\partial L}{\partial a_i} \cdot \sigma'(w \, a_{i-1}) \cdot w

$$

Questo prodotto può diventare molto piccolo (**vanishing gradient**), bloccando l'apprendimento degli strati iniziali. Con la skip connection, il gradiente totale diventa:

$$

\frac{\partial L}{\partial a_{i-1}} = \frac{\partial L}{\partial a_i} \cdot \sigma'(w \, a_{i-1}) \cdot w + \frac{\partial L}{\partial a_i}

$$

Il termine aggiuntivo $\frac{\partial L}{\partial a_i}$ (il **gradiente upstream**) fluisce **inalterato** attraverso la skip connection fino agli strati precedenti, anche quando il gradiente attraverso il ramo residuale è quasi nullo. Questo garantisce un **flusso di gradiente ininterrotto** e permette di addestrare reti con centinaia di strati. ResNet è disponibile in diverse varianti: ResNet-50, ResNet-101, ResNet-152.

---

## 8. Visualizzazione di ciò che le CNN Apprendono

### Cosa vedono le CNN

Visualizzare i filtri appresi dalle CNN rivela una chiara gerarchia:

- **Strati iniziali** → bordi, angoli, texture elementari

- **Strati intermedi** → pattern composti, forme

- **Strati profondi** → parti di oggetti, concetti astratti

Questa gerarchia conferma che le CNN costruiscono progressivamente rappresentazioni sempre più astratte e semantiche a partire dai pixel grezzi.

---

## 9. Transfer Learning

### Motivazione

Le reti grandi (AlexNet con 60M parametri, VGG16 con 138M) necessitano di **molti dati di training**. Quando i dati sono abbondanti (es. classificazione di animali comuni), addestrarle da zero (partendo da pesi inizializzati casualmente) è fattibile. Ma quando i dati sono **limitati** (es. animali rari, immagini mediche), addestrare da zero causa **overfitting severo** (troppi parametri per troppo pochi dati).

### Principio del Transfer Learning

Il **transfer learning** è una tecnica in cui la conoscenza appresa su un task viene **riutilizzata** per migliorare le prestazioni su un task correlato. L'intuizione sfrutta ciò che le CNN apprendono:

- Gli **strati iniziali** imparano feature generiche di basso livello (bordi, texture, angoli) **comuni a tutti i dataset**

- Solo gli **strati profondi** imparano feature specifiche del task (parti di animali, forme di oggetti)

Non serve reimparare tutto da zero: si **riutilizzano le fondamenta** (feature di basso livello) e si **riapprendono solo gli strati finali** per il nuovo task. È simile a come gli umani imparano: se sai pattinare sul ghiaccio, imparare lo snowboard è più rapido.

### Schema operativo

1. **Pre-training**: addestrare un grande modello su un ampio dataset sorgente (es. ImageNet con milioni di immagini). Una volta addestrato, salvare i parametri → si ottiene un **modello pre-addestrato**.

2. **Transfer**: usare i parametri del modello pre-addestrato come **punto di partenza** (anziché pesi casuali) per un dataset target più piccolo e un nuovo task.

3. **Fine-tuning**: aggiornare solo una parte dei parametri sul dataset target, **congelando** (freezing) la maggior parte del modello. "Congelare" significa impedire che i pesi vengano aggiornati durante il training.

4. L'intuizione è: **imparare ciò che è nuovo riutilizzando i pattern comuni**.

---

## 10. Procedure di Fine-Tuning

### Tipo 1 — Solo il classificatore

Si mantiene il **feature extractor** del modello pre-addestrato e si scarta il classificatore, sostituendolo con un nuovo classificatore inizializzato con pesi casuali. Si **congelano** tutti i pesi del feature extractor e si addestra (fine-tuna) **solo il classificatore** con SGD. È l'approccio più leggero e conservativo.

### Tipo 2 — Fine-tuning differenziale

Invece di congelare completamente il feature extractor, si applica un **fine-tuning differenziale** per strato:

- **Strati iniziali**: completamente congelati (le feature di basso livello sono già buone)

- **Strati intermedi**: fine-tunati con **learning rate basso** (es. 0.001) — si modificano lievemente

- **Strati profondi + classificatore**: fine-tunati con **learning rate alto** (es. 0.1) — si adattano al nuovo task

L'idea è non modificare troppo drasticamente le feature di basso livello.

### Tipo 3 — Fine-tuning completo

Si fine-tunano **sia il feature extractor sia il classificatore**, usando un learning rate più alto per il classificatore e più basso per il feature extractor. È l'approccio più aggressivo e richiede più dati e risorse.

---

## 11. Fine-Tuning in Pratica: Criteri di Scelta

### Come scegliere la strategia

La scelta tra Tipo 1, 2 e 3 dipende da tre fattori:

**Similarità tra dataset sorgente e target**:

- Dataset molto simili → **Tipo 1** (le feature del modello pre-addestrato sono già adatte)

- Dataset parzialmente simili → **Tipo 2**

- Dataset molto diversi → **Tipo 3** (servono modifiche più profonde)

**Quantità di immagini nel dataset target**:

- Pochissime immagini (es. 3 per categoria) → **Tipo 1** (fine-tunare poco per evitare overfitting)

- Molte immagini (es. centinaia per categoria) → **Tipo 3** (si può fine-tunare di più)

- Quantità intermedia → **Tipo 2**

**Dimensione del modello pre-addestrato**:

- Modelli molto grandi hanno già appreso buone rappresentazioni → **Tipo 1 o 2** (non serve fine-tunare molto). Inoltre, con troppi parametri e pochi dati target, il fine-tuning aggressivo causerebbe overfitting.

- Modelli di dimensione ragionevole → **Tipo 3** è praticabile

### Regola generale e bilanciamento

Non esiste una ricetta universale. La regola pratica è: **partire dal Tipo 1** e procedere gradualmente verso il Tipo 3, monitorando l'accuracy di validazione. Un fine-tuning troppo aggressivo rovina la conoscenza pre-appresa; uno troppo conservativo non è sufficiente per apprendere il nuovo task. Considerare anche tempo e risorse computazionali: il Tipo 1 è il più veloce ed economico, il Tipo 3 il più costoso.

---

## 12. Applicazioni del Transfer Learning

Il transfer learning è estremamente versatile e si applica in molti contesti:

- **Guida autonoma da simulazione**: pre-addestrare su dati simulati (economici da raccogliere) e fine-tunare su dati di guida reali (costosi da raccogliere)

- **Domain adaptation**: pre-addestrare su dati raccolti in condizioni frequenti (es. giornate soleggiate) e fine-tunare su dati raccolti in condizioni rare (es. giornate nebbiose)

- **NLP e lingue a basse risorse**: pre-addestrare un language model su lingue ad alta risorsa (inglese, spagnolo) e trasferire la conoscenza a lingue a bassa risorsa (es. nepalese)

L'idea del transfer learning va **oltre le immagini e le CNN**: è un principio generale applicabile a qualsiasi dominio in cui si possa riutilizzare conoscenza da un task sorgente ricco di dati verso un task target con dati limitati.
## Indice cap 10-11-12
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
## Indice cap 13-14-15

1. [Object Detection: cos'è e perché serve](#1-object-detection-cosè-e-perché-serve)
2. [Approccio semplice alla Detection](#2-approccio-semplice-alla-detection)
3. [R-CNN: Regions with CNN features](#3-r-cnn-regions-with-cnn-features)
4. [Fast R-CNN](#4-fast-r-cnn)
5. [Faster R-CNN e Region Proposal Network](#5-faster-r-cnn-e-region-proposal-network)
6. [YOLO: Single-Stage Object Detector](#6-yolo-single-stage-object-detector)
7. [Non-Maximum Suppression (NMS)](#7-non-maximum-suppression-nms)
8. [Apprendimento Non Supervisionato](#8-apprendimento-non-supervisionato)
9. [Autoencoder](#9-autoencoder)
10. [Denoising Autoencoder](#10-denoising-autoencoder)
11. [Autoencoder Convoluzionale](#11-autoencoder-convoluzionale)
12. [Variational Autoencoder (VAE)](#12-variational-autoencoder-vae)
13. [Representation Learning](#13-representation-learning)
14. [Self-Supervised Learning e Pretext Task](#14-self-supervised-learning-e-pretext-task)
15. [Contrastive Learning](#15-contrastive-learning)
16. [Multimodal Deep Learning e Vision-Language Models](#16-multimodal-deep-learning-e-vision-language-models)
17. [CLIP: Contrastive Language-Image Pretraining](#17-clip-contrastive-language-image-pretraining)
18. [BLIP: Bootstrapping Language-Image Pre-training](#18-blip-bootstrapping-language-image-pre-training)
19. [BLIP2](#19-blip2)
20. [LLaVA: Large Language and Vision Assistant](#20-llava-large-language-and-vision-assistant)
---

## 1. Object Detection: cos'è e perché serve

L'**object detection** è un task di computer vision il cui obiettivo è **localizzare** gli oggetti presenti in un'immagine e **classificarli** contemporaneamente. A differenza della classificazione (che produce una sola etichetta per immagine), la detection produce per ogni oggetto trovato una **bounding box** (rettangolo che racchiude l'oggetto) insieme alla **classe** di appartenenza. In pratica il modello risponde a due domande: *cosa* c'è nell'immagine e *dove* si trova.

I detector si dividono in due grandi famiglie:

- **Two-stage detector** (es. R-CNN, Fast R-CNN, Faster R-CNN): prima generano delle proposte di regioni che potrebbero contenere oggetti, poi le raffinano e classificano. Sono tipicamente più accurati ma più lenti.

- **Single-stage detector** (es. YOLO, SSD): producono direttamente le bounding box e le classi in un unico passaggio attraverso la rete, senza una fase separata di proposta regioni. Sono molto più veloci ma storicamente un po' meno precisi.

- **Detector basati su Transformer** (es. DETR): approccio più recente che applica l'architettura Transformer direttamente alla detection.

---

## 2. Approccio semplice alla Detection

L'approccio più elementare consiste nel prendere una CNN standard (come AlexNet) e aggiungerle due "teste" di output:

- **Classification head**: predice la distribuzione di probabilità sulle classi (risponde alla domanda "cosa")

- **Bounding box head**: predice 4 numeri $(x, y, w, h)$ che rappresentano le coordinate dell'angolo in alto a sinistra, la larghezza e l'altezza del rettangolo (risponde alla domanda "dove")

Il modello viene addestrato con la somma di due loss:

- **Cross-Entropy Loss** per la classificazione (predire la classe corretta)

- **Mean Squared Error Loss** per la regressione delle coordinate della bounding box

Il grosso **limite** di questo approccio è che può rilevare **un solo oggetto** per immagine. In un'immagine con 3 cani e 1 gatto, il modello dovrebbe produrre 4 set di coordinate e 4 classi, ma l'architettura ha un numero fisso di output. Ogni immagine può contenere un numero diverso di oggetti, quindi servono approcci più sofisticati.

Un tentativo di soluzione è lo **sliding window**: si scorrono piccole finestre a diverse scale e proporzioni sull'intera immagine, classificando ciascuna. Questo però è estremamente **costoso computazionalmente**, perché richiede migliaia di forward pass della CNN per una singola immagine.

---

## 3. R-CNN: Regions with CNN features

**R-CNN** (Regions with CNN features) è il primo detector two-stage di successo. Invece di provare tutte le possibili finestre, usa un approccio più intelligente in due fasi:

- **Fase 1 — Selective Search**: un algoritmo classico (non neurale) analizza l'immagine e propone circa **2000 regioni di interesse** (region proposals), cioè zone dell'immagine che hanno alta probabilità di contenere un oggetto, basandosi su colore, texture e forma.

- **Fase 2 — Classificazione CNN**: ciascuna delle ~2000 regioni viene ridimensionata (warped) a una dimensione fissa (es. $224 \times 224$) e passata singolarmente attraverso una CNN che predice la classe e le coordinate della bounding box.

Il vantaggio rispetto allo sliding window è che si analizzano solo ~2000 regioni invece di milioni. Tuttavia il **limite principale** è che la CNN deve essere eseguita **2000 volte separatamente**, una per ogni regione proposta. Questo rende R-CNN ancora molto lento in pratica.

---

## 4. Fast R-CNN

**Fast R-CNN** risolve il problema della lentezza di R-CNN con un'idea semplice ma potente: invece di applicare la CNN separatamente a ogni regione, si esegue la CNN **una sola volta sull'intera immagine** per estrarre una mappa di feature (le feature del layer conv5). Poi:

- Si applica Selective Search sulla mappa di feature (non sull'immagine originale) per individuare le regioni di interesse

- Si fa un **RoI Pooling** (Region of Interest Pooling): per ogni regione proposta si estrae un vettore di feature dalla mappa condivisa, ridimensionandolo a dimensione fissa

- Si classifica ciascuna regione e si predicono le coordinate della bounding box

Il guadagno è enorme: la parte più costosa (l'elaborazione con la CNN) viene fatta **una volta sola**, e il costo per ogni regione è solo quello del pooling e della classificazione. Tuttavia resta il problema che **Selective Search è un algoritmo separato**, non integrato nel training end-to-end della rete.

---

## 5. Faster R-CNN e Region Proposal Network

**Faster R-CNN** elimina l'ultimo collo di bottiglia sostituendo Selective Search con una **Region Proposal Network (RPN)**, cioè una piccola rete neurale che genera le proposte di regione direttamente dalle feature convoluzionali. Così l'intera pipeline diventa **end-to-end**: un'unica rete che al suo interno prima propone regioni e poi le classifica.

### Come funziona la RPN

La RPN scorre sulle feature convoluzionali del backbone e, per ogni posizione, considera $K$ **anchor box** predefinite con diverse scale e proporzioni (aspect ratio). Per ciascuna anchor box la RPN predice due cose:

- Un **objectness score**: la probabilità che l'anchor contenga un oggetto (classificazione binaria: oggetto sì/no)

- Le **correzioni alla bounding box**: piccoli aggiustamenti $(dx, dy, dw, dh)$ per far coincidere meglio l'anchor con l'oggetto reale

### Addestramento della RPN

- Le anchor box che hanno alta sovrapposizione (IoU) con un oggetto reale vengono etichettate come **positive** (in verde)

- Le anchor con bassa sovrapposizione sono **negative** (in rosso)

- Per gli anchor positivi si addestra anche la regressione delle coordinate

### Inferenza con RPN

- Si calcolano gli objectness score per tutte le $K$ anchor

- Si ordinano per punteggio e si prendono le **top ~300** proposte

- Queste proposte passano poi attraverso il RoI Pooling e la classificazione (come in Fast R-CNN) per ottenere la classe finale e le coordinate precise

---

## 6. YOLO: Single-Stage Object Detector

**YOLO** (You Only Look Once) è il detector single-stage più famoso. Il nome dice tutto: l'immagine viene analizzata **una sola volta** e il modello produce direttamente tutte le predizioni, senza una fase separata di proposta regioni. Il suo principio di funzionamento è il seguente:

- L'immagine viene divisa in una griglia, ad esempio $7 \times 7$ celle

- Per ogni cella della griglia si definiscono $B$ anchor box con scale e proporzioni diverse

- Per ciascuna anchor box il modello predice:

- **5 valori della bounding box**: coordinate $x, y, h, w$ e uno score di "objectness" (quanto è probabile che contenga un oggetto)

- **$C$ probabilità di classe** (una per ogni classe, incluso lo sfondo)

L'output complessivo per una griglia $7 \times 7$ con $B$ anchor e $C$ classi è un tensore di dimensione $7 \times 7 \times B \times (5 + C)$.

### Evoluzione di YOLO

- **YOLOv1**: griglia $7 \times 7$, 2 box per cella, 20 classi → $7 \times 7 \times 2 \times (5 + 20) = 2450$ box totali

- **YOLOv3**: introduce **scale multiple** di rilevamento ($13 \times 13$, $26 \times 26$, $52 \times 52$), 3 box per cella, 80 classi. La multi-scala permette di rilevare sia oggetti grandi che piccoli

### Confronto tra detector

- **Two-stage** (es. Faster R-CNN): più accurati, ma più lenti perché hanno due fasi

- **Single-stage** (es. YOLO): molto più veloci (real-time), ma storicamente leggermente meno precisi, anche se le versioni recenti hanno colmato il divario

---

## 7. Non-Maximum Suppression (NMS)

Poiché ogni detector genera moltissime bounding box (spesso sovrapposte per lo stesso oggetto), serve un meccanismo per eliminare i duplicati. La **Non-Maximum Suppression** (NMS) è una tecnica di post-processing che funziona così:

- Si ordinano tutte le bounding box predette in base al loro **confidence score** (punteggio di fiducia) in ordine decrescente

- Si seleziona la box con il punteggio più alto, chiamiamola $A$

- Si calcolano le sovrapposizioni (IoU) di $A$ con tutte le altre box. Si **eliminano** tutte le box che hanno una sovrapposizione alta con $A$ (tipicamente IoU > 0.5), perché probabilmente si riferiscono allo stesso oggetto

- Si ripete il procedimento con la box successiva a punteggio più alto tra quelle rimaste

Il risultato è un insieme pulito di bounding box dove ogni oggetto è rilevato una sola volta.

---

## 8. Apprendimento Non Supervisionato

Nell'**apprendimento supervisionato** il modello impara confrontando le sue predizioni con delle etichette fornite da un umano (es. "questa immagine è un gatto"). Nell'**apprendimento non supervisionato** il modello impara dalla struttura dei dati stessi, **senza etichette**. Questo è molto più economico perché non richiede il costoso processo di annotazione manuale.

L'apprendimento non supervisionato serve a diversi scopi:

- **Trovare strutture nascoste** nei dati (clustering)

- **Imparare rappresentazioni** utili (feature learning)

- **Ridurre la dimensionalità** (es. PCA)

- **Comprimere i dati** (data compression)

- **Stimare la densità** dei dati (density estimation)

- **Generare nuovi esempi** simili a quelli visti (generazione)

---

## 9. Autoencoder

Un **autoencoder (AE)** è una rete neurale addestrata a **ricostruire il proprio input**. Sembra inutile, ma il trucco sta nel fatto che la rete ha un **collo di bottiglia** (bottleneck) nel mezzo: l'informazione viene prima compressa in una rappresentazione di dimensione ridotta, poi ricostruita. Così la rete è costretta a imparare quali sono gli aspetti più importanti dell'input.

L'autoencoder ha due componenti:

- **Encoder**: prende l'input $x$ e lo mappa in una rappresentazione nascosta $z$ (chiamata anche **code vector** o **rappresentazione latente**), che ha dimensionalità molto inferiore all'input

- **Decoder**: prende $z$ e cerca di ricostruire l'input originale, producendo $\hat{x}$

La rete viene addestrata minimizzando l'**errore di ricostruzione** con il Mean Squared Error:

$$

\mathcal{L}_{\text{MSE}}(x, \hat{x}) = \| x - \hat{x} \|^2 = \sum_i (x_i - \hat{x}_i)^2

$$

### Riduzione di dimensionalità

Una volta addestrato l'AE, si **scarta il decoder** e si usa solo l'encoder come estrattore di feature. L'output dell'encoder è una rappresentazione compressa dell'input in dimensione ridotta, molto simile concettualmente alla PCA. Infatti, se non si usano funzioni di attivazione non lineari e si minimizza l'MSE, l'autoencoder è matematicamente equivalente alla PCA (con la differenza che le dimensioni latenti non sono necessariamente ortogonali tra loro).

### Limiti degli Autoencoder standard

- Lo spazio latente **non è regolarizzato**: solo alcune regioni specifiche dello spazio corrispondono a dati reali

- Grandi parti dello spazio latente producono output senza senso se passate al decoder

- Sono adatti alla **compressione**, ma **non alla generazione** di nuovi campioni realistici

---

## 10. Denoising Autoencoder

Il **denoising autoencoder** è una variante dell'AE in cui si fornisce come input una versione **corrotta** dell'immagine $\tilde{x}$ e il modello deve ricostruire l'input **pulito** $x$. I tipi di rumore usati sono:

- **Rumore gaussiano**: $\tilde{x} = x + \mathcal{N}(0, \sigma^2 I)$, si aggiungono perturbazioni casuali distribuite normalmente

- **Mascheramento casuale**: si impostano a zero alcune feature dell'input in modo casuale

- **Rumore sale-e-pepe**: si impostano casualmente alcuni valori al minimo o massimo possibile

L'idea chiave è che il processo di corruzione **obbliga la rete a imparare feature significative** che catturano l'essenza dell'input, impedendole di limitarsi a memorizzare i dati. Questo migliora la **robustezza** a perturbazioni dell'input e aiuta a prevenire l'**overfitting**.

---

## 11. Autoencoder Convoluzionale

Quando si lavora con immagini, un autoencoder fully-connected ha gli stessi limiti di un MLP applicato a immagini:

- Ignora la **struttura spaziale** 2D dell'immagine

- Non scala bene con immagini grandi (troppi parametri)

- Deve reimparare da zero pattern locali come bordi e texture

La soluzione è sostituire i layer fully-connected con **layer convoluzionali**, ottenendo un **Convolutional Autoencoder**:

- **Encoder**: Input → Conv → ReLU → Pooling (ripetuto più volte), fino a ottenere il code vector

- **Decoder**: Transposed Conv → ReLU (ripetuto più volte), per riportare la rappresentazione alla risoluzione originale

### Convoluzione trasposta (Transposed Convolution)

Nel decoder si usa la **convoluzione trasposta** per fare l'upsampling delle feature map, cioè aumentarne la risoluzione spaziale (es. da $7 \times 7$ a $14 \times 14$). Funziona inserendo zeri tra i pixel dell'input e poi applicando una convoluzione regolare. I pesi del kernel sono apprendibili via backpropagation.

Un problema noto è che la convoluzione trasposta può produrre **artefatti a scacchiera** (checkerboard artifacts) nell'output. Per evitarli, si consiglia di sostituirla con un'**interpolazione** (es. nearest neighbour o bilineare) seguita da una convoluzione regolare, tecnica nota come **resize-convolution**.

### Usi dell'autoencoder addestrato

Dopo il pre-training non supervisionato, l'encoder dell'AE può essere riutilizzato:

- Le feature latenti possono alimentare algoritmi di ML classici (KNN, SVM, ecc.)

- Si può fare **transfer learning**: si aggiunge un classificatore (layer FC) sopra l'encoder e si fa fine-tuning con un piccolo dataset etichettato

---

## 12. Variational Autoencoder (VAE)

Il **Variational Autoencoder (VAE)** risolve il problema principale dell'AE standard (spazio latente non regolarizzato) introducendo un approccio probabilistico. Invece di produrre un singolo vettore latente, l'encoder produce i **parametri di una distribuzione di probabilità**: il vettore media $\mu$ e il vettore varianza $\sigma^2$.

### Architettura

L'architettura è simile a un AE standard, ma con una differenza cruciale nel bottleneck:

- L'encoder produce due vettori: **media** $\mu$ e **varianza** $\sigma^2$

- Il vettore latente $z$ viene **campionato** dalla distribuzione gaussiana: $z \sim \mathcal{N}(\mu, \sigma)$

- In pratica si usa il **reparameterization trick** per rendere il campionamento differenziabile:

$$

z = \mu + \epsilon \cdot \sigma, \quad \epsilon \sim \mathcal{N}(0, I)

$$

- Il decoder riceve $z$ e ricostruisce l'input

### Addestramento

Il VAE è addestrato con la somma di **due loss**:

- **Reconstruction Loss** (come nell'AE): misura quanto bene l'input è ricostruito

$$

\mathcal{L}_{\text{MSE}}(x, \hat{x}) = \| x - \hat{x} \|^2

$$

- **Regularization Loss** (KL Divergence): forza la distribuzione latente predetta dall'encoder ad assomigliare a una distribuzione gaussiana standard $\mathcal{N}(0, I)$

$$

\mathcal{L}_{\text{reg}} = D_{\text{KL}}\big(\mathcal{N}(\mu, \sigma) \,\|\, \mathcal{N}(0, I)\big)

$$

La **KL Divergence** misura quanto una distribuzione di probabilità è diversa da un'altra. Minimizzarla significa costringere lo spazio latente ad essere distribuito come una gaussiana standard.

### Proprietà dello spazio latente del VAE

Il bilanciamento tra le due loss produce uno spazio latente con due proprietà fondamentali:

- **Continuità**: punti vicini nello spazio latente producono output simili quando decodificati. Non ci sono "salti bruschi" tra regioni dello spazio.

- **Completezza**: qualsiasi punto campionato dallo spazio latente produce un output sensato. Non ci sono "buchi" o regioni morte.

### Generazione di nuovi campioni

Una volta addestrato, il decoder del VAE diventa un **generatore**: si campiona un vettore $z \sim \mathcal{N}(0, I)$ dallo spazio latente e lo si passa al decoder per ottenere un nuovo campione $\hat{x} = \text{Dec}(z)$. Poiché lo spazio è continuo e regolarizzato, ogni punto campionato produce qualcosa di realistico.

### Interpolazione nello spazio latente

Una capacità interessante del VAE è la possibilità di **interpolare** tra due immagini in modo fluido:

- Si codificano due input: $z_1 = \text{Enc}(x_1)$ e $z_2 = \text{Enc}(x_2)$

- Si interpola linearmente: $z = \alpha \, z_1 + (1 - \alpha) \, z_2$ con $\alpha \in [0, 1]$

- Si decodifica: $\hat{x} = \text{Dec}(z)$

Questo produce transizioni fluide, ad esempio tra un volto con capelli neri e uno con capelli biondi, o tra un volto con e senza occhiali da sole.

---

## 13. Representation Learning

Il **representation learning** (apprendimento delle rappresentazioni) consiste nel far scoprire automaticamente alla rete feature utili a partire dai dati grezzi, in modo da migliorare le prestazioni su task successivi come la classificazione. Una buona rappresentazione deve avere queste qualità:

- **Compatta**: il minor numero di dimensioni necessarie per catturare l'informazione importante

- **Esplicativa**: sufficiente a descrivere e spiegare i dati

- **Disgiunta** (disentangled): i diversi fattori di variazione nei dati (es. forma, colore, posizione) sono catturati da dimensioni indipendenti

- **Interpretabile**: le dimensioni hanno un significato comprensibile

- **Utile**: rende facile risolvere il task successivo (es. classificazione)

Buone rappresentazioni si possono imparare con **molti dati etichettati** (milioni di immagini con annotazioni), ma questo è costoso. La domanda è: possiamo imparare rappresentazioni buone **senza** etichette manuali? La risposta è il self-supervised learning.

---

## 14. Self-Supervised Learning e Pretext Task

Il **self-supervised learning** è una forma di apprendimento non supervisionato in cui il modello impara feature utili risolvendo un compito ausiliario, detto **pretext task**, che **non richiede etichette manuali** perché le etichette vengono generate automaticamente dai dati stessi.

L'idea è questa: anche se non abbiamo etichette per il task vero (es. "questa è una foto di un gatto"), possiamo creare task "finti" la cui soluzione costringe la rete a imparare feature visive generali. Una volta apprese, queste feature si trasferiscono al **downstream task**, cioè il task reale che ci interessa (es. classificazione), dove tipicamente abbiamo pochi dati etichettati.

### Come si valuta

- Si addestra l'encoder con il pretext task (non supervisionato)

- Si congela l'encoder e si aggiunge sopra un piccolo classificatore (MLP o layer lineare)

- Si addestra solo il classificatore con un piccolo dataset etichettato

- Si valuta la qualità delle feature in base alla performance di classificazione

### Pretext Task: Predire le rotazioni (RotNet)

Si ruota l'immagine di 0°, 90°, 180° o 270° e la rete deve predire quale rotazione è stata applicata (problema di classificazione a 4 classi). L'**ipotesi** è che per riconoscere la rotazione corretta, il modello deve avere una comprensione del "senso comune visivo", cioè sapere come dovrebbe apparire un oggetto nella posizione normale. Questo lo costringe a imparare feature semantiche utili.

### Pretext Task: Predire la posizione relativa delle patch

Si estraggono due patch dall'immagine: una di riferimento e una tra 8 possibili vicine. La rete deve indovinare la **posizione relativa** della seconda rispetto alla prima (classificazione a 8 classi: sopra-sinistra, sopra, sopra-destra, ecc.). L'ipotesi è che per capire dove si trova una patch rispetto a un'altra, il modello deve aver imparato a riconoscere l'oggetto nell'immagine.

### Pretext Task: Risolvere puzzle (Jigsaw)

L'immagine viene divisa in 9 tessere (come un puzzle 3×3), le tessere vengono mescolate in modo casuale secondo una permutazione scelta da un set predefinito, e la rete deve **predire quale permutazione** è stata applicata (classificazione a 64 classi). Per ricostruire l'ordine corretto, il modello deve comprendere la struttura semantica dell'immagine.

### Pretext Task: Inpainting (predire pixel mancanti)

Si rimuove una porzione rettangolare dell'immagine e la rete deve **ricostruire i pixel mancanti**. L'architettura è simile ad un autoencoder: l'encoder estrae feature dall'immagine parziale, il decoder ricostruisce la regione rimossa. L'ipotesi è che per produrre pixel coerenti, il modello deve avere una comprensione semantica profonda della scena (es. sapere che dietro una finestra c'è probabilmente un muro).

### Pretext Task: Colorazione di immagini

Data un'immagine in scala di grigi (canale L dello spazio colore Lab), la rete deve predire i **canali di colore** (a e b). L'ipotesi è che per colorare correttamente un'immagine, il modello deve riconoscere gli oggetti: il cielo è blu, l'erba è verde, una coccinella è rossa. Anche questa architettura è simile a un autoencoder.

### Pretext Task: Colorazione video

Estensione al video: si prende un frame di riferimento (colorato) e un frame target (in scala di grigi) dello stesso video. Il modello deve colorare il frame target trovando le **corrispondenze** tra pixel dei due frame. Per fare ciò usa una mappa di attenzione:

$$

A_{ij} = \frac{\exp(f_i^\top f_j)}{\sum_k \exp(f_k^\top f_j)}

$$

Dove $f_i$ e $f_j$ sono le feature dei pixel. Il colore predetto del pixel $j$ nel frame target è una media pesata dei colori del frame di riferimento:

$$

\hat{y}_j = \sum_i A_{ij} \, c_i

$$

L'ipotesi è che imparando a colorare i frame, il modello impara a **tracciare oggetti** attraverso i fotogrammi, capacità utile per il tracking in computer vision.

### Limiti dei Pretext Task

- Sviluppare singoli pretext task è **laborioso**

- Le rappresentazioni apprese potrebbero **non essere generali**, ma specifiche al tipo di pretext task scelto

- Serve un approccio più universale che funzioni bene su immagini e video

---

## 15. Contrastive Learning

L'**apprendimento contrastivo** è un pretext task più generale che supera i limiti dei singoli pretext task specifici. L'idea di base è semplice ed elegante: **due crop diverse della stessa immagine (stesso oggetto) devono avere rappresentazioni simili, mentre crop da immagini diverse devono avere rappresentazioni distanti**.

Formalmente, dato un campione di riferimento $x$, un campione positivo $x^+$ (la stessa immagine trasformata, es. ritagliata diversamente) e campioni negativi $x^-$ (immagini diverse), si vuole che:

$$

\text{score}(f(x), f(x^+)) \gg \text{score}(f(x), f(x^-))

$$

Dove $f$ è l'encoder (rete neurale) che produce le rappresentazioni.

### Funzione di Loss

La loss per un campione positivo e $N-1$ campioni negativi è:

$$

\mathcal{L} = -\log \frac{\exp\big(s(f(x), f(x^+))\big)}{\exp\big(s(f(x), f(x^+))\big) + \sum_{j=1}^{N-1} \exp\big(s(f(x), f(x^-_j))\big)}

$$

Questa loss somiglia molto a una cross-entropy: il numeratore è lo score della coppia positiva, il denominatore è la somma di tutti gli score (positivo + negativi). Minimizzarla significa spingere lo score del positivo ad essere molto più alto di tutti i negativi.

### Analogia con il classificatore MLP

Il contrastive learning può essere visto come un **classificatore senza pesi fissi**: in un MLP classico si calcola il prodotto scalare tra la feature dell'immagine e i pesi delle classi. Nel contrastive learning i "pesi" vengono sostituiti dalle **feature degli altri campioni** nel mini-batch. Il campione positivo $z^+$ fa le veci della "classe corretta", i campioni negativi $\{z^-_1, \ldots, z^-_n\}$ fanno le veci delle "classi sbagliate".

### SimCLR

**SimCLR** (Simple Framework for Contrastive Learning) è uno dei framework contrastivi più noti. Le sue caratteristiche:

- Usa la **cosine similarity** come funzione di score:

$$

S(u, v) = \frac{u^\top v}{\|u\| \, \|v\|}

$$

- Usa una **rete di proiezione** $g(\cdot)$ che proietta le feature in uno spazio dove si applica la loss contrastiva, migliorando la qualità delle rappresentazioni

- Genera coppie positive attraverso **data augmentation**: random cropping, distorsione casuale del colore e random blur applicati alla stessa immagine per creare due viste diverse

### Altri framework contrastivi

L'idea di contrastare campioni positivi e negativi è usata in molti framework:

- **MoCo** e **MoCo V2**: usano una coda (queue) di campioni negativi e un encoder "momentum" per stabilizzare il training

- **Contrastive Predictive Coding**: applica il contrasto a livello di predizione temporale

- **DINO**: combina contrastive learning con una tecnica teacher-student senza campioni negativi espliciti

---

## 16. Multimodal Deep Learning e Vision-Language Models

Tutti i modelli visti finora sono **unimodali**: le CNN prendono solo immagini, i Transformer prendono solo testo o solo immagini. I **Vision-Language Models (VLM)** sono modelli **multimodali** che elaborano contemporaneamente **due modalità**: immagini e testo.

### Perché usare due modalità?

- I **dati di addestramento sono facili da raccogliere**: basta prendere immagini dal web, che spesso hanno già una didascalia, un tag o un alt-text associato. Non serve annotazione manuale.

- Il **linguaggio è una forma di supervisione più ricca** di una semplice etichetta di classe: una frase descrive sfumature, relazioni, attributi

- **Scala bene**: più dati + modelli più grandi → modelli migliori

- Produce modelli molto **versatili**, utili per molteplici applicazioni

### Due famiglie di VLM

- **VLM Contrastivi** (es. CLIP): addestrati solo con loss contrastiva. Servono per classificazione e retrieval (ricerca di immagini/testi). Funzionano come un MLP/CNN che "matcha" immagini con testo, ma non possono generare testo.

- **VLM Generativi** (es. BLIP, LLaVA): addestrati anche con loss di language modelling. Possono **generare testo** a partire da immagini (captioning, risposta a domande, dialogo).

---

## 17. CLIP: Contrastive Language-Image Pretraining

**CLIP** (Contrastive Language-Image Pretraining, OpenAI) è il VLM contrastivo più influente. Applica l'idea del contrastive learning non più tra due immagini, ma tra un'**immagine** e la sua **descrizione testuale**.

### Dati di addestramento

I dati vengono raccolti dal web: coppie $(x, c)$ dove $x$ è un'immagine e $c$ è la didascalia associata. CLIP è stato addestrato su **400 milioni** di coppie immagine-testo. Questo paradigma è detto **webly-supervised learning**.

### Architettura

CLIP ha due encoder separati che operano in parallelo:

- **Image encoder** (un ViT): prende l'immagine, la divide in patch, aggiunge posizioni e il token [CLS]. L'embedding del [CLS] è la rappresentazione dell'immagine.

- **Text encoder** (un Transformer): prende la didascalia tokenizzata, aggiunge il token [SOS] (start) e [EOS] (end). L'embedding del [EOS] è la rappresentazione del testo.

### Addestramento

CLIP massimizza la similarità tra l'immagine $I_1$ e la sua didascalia $T_1$, e minimizza la similarità con tutte le altre didascalie nel mini-batch. La loss è composta da due parti simmetriche:

$$

\mathcal{L}_{\text{img}\to\text{text}} = -\frac{1}{N}\sum_{i=1}^{N} \log \frac{\exp(v_i^\top t_i)}{\sum_{j=1}^{N} \exp(v_i^\top t_j)}

$$

$$

\mathcal{L}_{\text{text}\to\text{img}} = -\frac{1}{N}\sum_{i=1}^{N} \log \frac{\exp(v_i^\top t_i)}{\sum_{j=1}^{N} \exp(v_j^\top t_i)}

$$

La prima loss chiede: "data l'immagine $i$, quale tra tutte le didascalie è quella giusta?" La seconda chiede il viceversa: "data la didascalia $i$, quale immagine corrisponde?"

### Inferenza: classificazione zero-shot

CLIP ha una capacità straordinaria: può classificare **qualsiasi** dataset senza mai averci addestrato sopra. Funziona così:

- **Step 1**: per ogni classe del dataset si costruisce un embedding testuale usando un template come "a photo of a [NOME CLASSE]"

- **Step 2**: data un'immagine di test, si calcola la similarità tra il suo embedding e tutti gli embedding testuali delle classi

- **Step 3**: la classe predetta è quella con la similarità più alta

Questo è possibile perché CLIP ha visto 400 milioni di concetti durante l'addestramento. Può persino superare modelli come ResNet101 addestrati direttamente su ImageNet, il tutto **senza aver mai visto etichette di ImageNet**.

### Analogia con il classificatore MLP

In un MLP classico i pesi dell'ultimo layer corrispondono alle classi. In CLIP i "pesi" sono **sostituiti dagli embedding testuali** delle classi. La flessibilità è enorme: basta cambiare i template testuali per classificare qualsiasi cosa.

### Retrieval con CLIP

CLIP permette anche il **retrieval** (ricerca):

- **Text-to-image**: data una descrizione testuale, si cercano le immagini più simili in una galleria

- **Image-to-text**: data un'immagine, si cercano le descrizioni più rilevanti

Il meccanismo è sempre lo stesso: si calcolano le similarità nello spazio latente condiviso e si restituiscono i risultati più simili.

---

## 18. BLIP: Bootstrapping Language-Image Pre-training

**BLIP** estende CLIP aggiungendo la capacità di **generare testo**. CLIP sa solo "matchare" immagine e testo (risponde a "sono una coppia?"), ma non sa rispondere a domande come "cosa c'è nell'immagine?". BLIP invece può farlo grazie al language modelling.

### Tre obiettivi di addestramento

BLIP viene addestrato con **tre loss** contemporaneamente:

- **Image-Text Contrastive (ITC) Loss**: allinea le rappresentazioni di immagine e testo nello spazio latente, esattamente come CLIP. L'encoder dell'immagine e l'encoder del testo producono embedding che devono essere simili per coppie corrispondenti.

- **Image-Text Matching (ITM) Loss**: una classificazione binaria che distingue coppie immagine-testo positive (corrispondenti) da negative (non corrispondenti). A differenza della ITC, questa loss usa **cross-attention** tra le modalità: il testo viene codificato da un encoder "image-grounded", che aggiunge layer di cross-attention dove le feature testuali interagiscono con quelle visive.

- **Language Modelling (LM) Loss**: addestra un decoder a **generare la didascalia** data l'immagine, come un modello di linguaggio autoregressivo. Il testo viene processato con **causal self-attention** (masked), cosicché il modello non possa "sbirciare" i token futuri durante la generazione.

### Inferenza con BLIP

Per generare una didascalia:

- Si fornisce l'immagine e il token [DECODE] (o [SOS]), oppure le prime parole della frase

- Il decoder genera un token alla volta, autoregressivamente, fino al token [EOS]

---

## 19. BLIP2

**BLIP2** è l'evoluzione di BLIP con due innovazioni importanti:

- **Non addestra da zero** né l'image encoder né il LLM: usa modelli **pre-addestrati e congelati** (frozen), risparmiando enormi costi computazionali

- Introduce il **Q-Former** (Querying Transformer), un modulo leggero che funge da **ponte** tra l'image encoder e il LLM

### Il Q-Former

Il Q-Former ha un'architettura Transformer inizializzata da BERT, con due novità:

- **Learned queries**: un insieme fisso di 32 embedding apprendibili $Z \in \mathbb{R}^{32 \times 768}$ che "interrogano" l'immagine per estrarne le informazioni più rilevanti

- **Layer di cross-attention**: permettono alle query di interagire con le feature dell'immagine (es. $257 \times 1024$ dal ViT), producendo una rappresentazione compressa $32 \times 768$

### Addestramento in due stadi

**Stadio 1** — Il Q-Former impara a estrarre rappresentazioni visive rilevanti per il testo. Si usano le stesse tre loss di BLIP (ITC, ITM, LM), ognuna con una **maschera di attenzione** diversa nel layer di self-attention:

- **ITM**: maschera bidirezionale — query e token testuali possono vedersi reciprocamente

- **ITC**: maschera unimodale — le query vedono solo le altre query, il testo vede solo il testo. Le due rappresentazioni sono indipendenti per calcolare la similarità contrastiva

- **LM**: maschera causale multimodale — le query vedono solo le query, il testo vede le query e i token testuali precedenti (non quelli futuri)

**Stadio 2** — Il Q-Former viene connesso a un **LLM congelato** (es. LLaMA, Flan-T5). Le query apprese fungono da "prefisso visivo" per il LLM, che genera testo in forma libera. Ci sono due implementazioni:

- Le learned query come prefisso in un LLM **decoder-only**

- Le learned query + un prefisso testuale in un LLM **encoder-decoder**

### Capacità di BLIP2

BLIP2 può sostenere una **conversazione** su un'immagine, rispondendo a domande di tipo:

- Visivo ("cosa c'è nell'immagine?")

- Basato sulla conoscenza ("di che razza è questo cane?")

- Ragionamento di buon senso ("questa persona sembra felice?")

---

## 20. LLaVA: Large Language and Vision Assistant

**LLaVA** (Large Language and Vision Assistant) è un VLM generativo che collega un image encoder pre-addestrato con un LLM pre-addestrato, con l'obiettivo di ottenere un assistente visivo capace di generare didascalie e rispondere a domande sulle immagini.

### Componenti

LLaVA ha tre componenti:
- **Vision Encoder** (congelato): un ViT pre-addestrato che estrae feature dall'immagine
- **Projection Layer** (addestrato): un semplice layer di proiezione che converte le feature visive nello spazio di embedding del LLM
- **Language Model** (fine-tuned): un LLM pre-addestrato (es. LLaMA) che viene adattato al task
### Dati di addestramento
I dati sono nel formato: (immagine, domanda/istruzione, risposta). Ad esempio: immagine di una bambina con un gattino → domanda "What is the girl holding?" → risposta "A small kitten".
### Addestramento
LLaVA è addestrato con la **language modelling loss** (come BLIP), ma con una differenza: il modello deve generare **solo la risposta**, non autocompletare l'intero input. Le feature visive dell'immagine vengono proiettate nello spazio del LLM e concatenate con i token della domanda, poi il LLM genera la risposta autoregressivamente.
### Differenze rispetto a BLIP2
- LLaVA usa un **semplice layer di proiezione** lineare al posto del Q-Former, che è più complesso
- LLaVA fa **fine-tuning** del LLM, mentre BLIP2 lo mantiene congelato
- LLaVA è progettato specificamente come **assistente interattivo**, ottimizzato per seguire istruzioni e rispondere a domande visive
## Indice cap 16-17-18

1. [Modellazione Generativa](#1-modellazione-generativa)
2. [Modelli Autoregressivi](#2-modelli-autoregressivi)
3. [Modelli a Variabili Latenti e VAE](#3-modelli-a-variabili-latenti-e-vae)
4. [Generative Adversarial Networks (GAN)](#4-generative-adversarial-networks-gan)
5. [Modelli di Diffusione (DDPM)](#5-modelli-di-diffusione-ddpm)
6. [Adattamento dei Foundation Models](#6-adattamento-dei-foundation-models)
7. [Full Fine-Tuning e Adattamento di BERT](#7-full-fine-tuning-e-adattamento-di-bert)
8. [Prompting e In-Context Learning](#8-prompting-e-in-context-learning)
9. [Parameter Efficient Fine-Tuning (PEFT)](#9-parameter-efficient-fine-tuning-peft)
10. [Adattamento di Modelli di Diffusione](#10-adattamento-di-modelli-di-diffusione)
11. [Apprendimento Non Supervisionato Avanzato: DINO](#11-apprendimento-non-supervisionato-avanzato-dino)
12. [Masked Autoencoder (MAE)](#12-masked-autoencoder-mae)
13. [Joint-Embedding Predictive Architecture (JEPA)](#13-joint-embedding-predictive-architecture-jepa)

---

## 1. Modellazione Generativa

### Cos'è un modello generativo?

L'**apprendimento non supervisionato** è un paradigma di machine learning in cui il modello impara dai dati **senza etichette** (senza supervisione umana). Tra i suoi obiettivi principali troviamo: apprendimento di rappresentazioni (self-supervised learning), compressione dati (autoencoder), scoperta di strutture nascoste (clustering) e **generazione di nuovi esempi**. Quest'ultimo è il focus della modellazione generativa.

Dato un insieme di dati di training $x_i$ (immagini, testo, audio) estratti da una distribuzione sconosciuta $p_{\text{data}}$, l'obiettivo è **apprendere una distribuzione parametrizzata** $p_\theta(x)$ tale che:

$$

p_\theta(x) \approx p_{\text{data}}(x)

$$

Una volta appresa questa distribuzione, possiamo:

- **Generare nuovi dati** campionando dalla distribuzione appresa (es. nuove immagini, nuovi testi, nuova musica)

- **Stimare la densità** di un dato: se $p_\theta(x)$ è alta, il dato è compatibile con la distribuzione (utile per anomaly detection)

- **Apprendere rappresentazioni compatte** dei dati di training

### Tassonomia dei modelli generativi

I modelli generativi si dividono in due grandi famiglie:

- **Modelli espliciti**: possono calcolare direttamente $p(x)$. Appartengono a questa categoria i modelli autoregressivi e i VAE (Variational Autoencoder). Con questi modelli possiamo sia generare campioni sia valutare la probabilità di un dato.

- **Modelli impliciti**: **non possono** calcolare $p(x)$ direttamente, ma possono **campionare** da essa. Appartengono a questa categoria le GAN (Generative Adversarial Networks) e i modelli di diffusione. Generano campioni realistici senza mai esplicitare la distribuzione.

### Sfide fondamentali

Apprendere $p_{\text{data}}(x)$ è difficile per due ragioni:

- **Dati limitati**: disponiamo solo di campioni finiti dalla distribuzione vera, che forniscono un'approssimazione grossolana

- **Ragioni computazionali**: in molti casi la distribuzione non è trattabile (intractable), cioè non si può calcolare analiticamente in modo efficiente

### Applicazioni

I modelli generativi alimentano oggi una vasta gamma di applicazioni: scrittura creativa (ChatGPT), generazione di immagini e arte digitale (DALL-E, Stable Diffusion), scoperta di farmaci e molecole, realtà virtuale, creazione di videogiochi, generazione di video (Sora) e molto altro.

---

## 2. Modelli Autoregressivi

### Idea fondamentale

Qualsiasi dato strutturato può essere trasformato in una **sequenza**: un testo è una sequenza di parole, un'immagine può essere una sequenza di pixel (scansionati riga per riga). I **modelli autoregressivi (AR)** sfruttano questa intuizione: predicono il valore successivo in una sequenza basandosi sui valori precedenti.

Data una sequenza di $d$ variabili $x = (x_1, x_2, \ldots, x_d)$, la probabilità congiunta viene decomposta tramite la **regola della catena** in un prodotto di probabilità condizionate:

$$

p(x_1, \ldots, x_d) = p(x_1) \cdot p(x_2 \mid x_1) \cdot p(x_3 \mid x_1, x_2) \cdots p(x_d \mid x_1, \ldots, x_{d-1})

$$

In forma compatta:

$$

p_\theta(x) = \prod_{j=1}^{d} p_\theta(x_j \mid x_{1:j-1}; \theta)

$$

Ogni fattore è una distribuzione condizionale che dice: "dato tutto ciò che ho visto finora, qual è la probabilità del prossimo elemento?". Questa è esattamente l'idea dietro i language model come GPT.

### Maximum Likelihood Estimation (MLE)

L'addestramento avviene tramite **Maximum Likelihood Estimation**: si cercano i parametri $\theta$ che massimizzano la probabilità dei dati osservati. Equivalentemente, si minimizza la **negative log-likelihood**:

$$

\arg\min_\theta \; -\frac{1}{n} \sum_{i=1}^{n} \log p_\theta(x^{(i)})

$$

Se $p_\theta$ è una rete neurale, l'ottimizzazione avviene tramite backpropagation e SGD, come per qualsiasi altra rete. I modelli AR sono **espliciti** perché stimano direttamente la distribuzione parametrizzata $p_\theta(x)$ tramite MLE.

### PixelCNN

**PixelCNN** applica l'approccio autoregressivo alla generazione di immagini: predice il valore del pixel alla posizione $i$ guardando solo i pixel a sinistra e sopra (come in un raster scan). Usa **convoluzioni mascherate** (analoghe alla masked self-attention nei Transformer) per impedire al modello di vedere i pixel futuri. Il training è parallelizzabile grazie alla convoluzione e alla conoscenza dell'immagine completa nel training set, ma la **generazione è sequenziale** e quindi lenta: ogni pixel dipende dal precedente.

### GPT come modello autoregressivo

**GPT** (Generative Pre-trained Transformer) è un modello autoregressivo basato su Transformer decoder-only. È addestrato con **next token prediction** su testi su larga scala (apprendimento non supervisionato). Dopo il pre-training, può essere fine-tunato su task supervisionati (QA, riassunti, ecc.). **GPT-2** ha la stessa architettura e obiettivo di GPT-1 ma con scala maggiore: 1.5B parametri addestrati su 40GB di testo (WebText). Ha dimostrato che con sufficiente scala (modello + dati), i language model possono eseguire task senza fine-tuning.

### Image GPT (iGPT)

**Image GPT** applica l'addestramento tipo GPT alle immagini: le immagini vengono ridimensionate a bassa risoluzione e rimodellate in una sequenza 1D. Si usano due obiettivi di pre-training: predizione autoregressiva del prossimo pixel (come GPT) oppure predizione dei pixel mascherati (come BERT). L'obiettivo finale non è generare pixel ma **apprendere buone rappresentazioni** da usare per task downstream.

### VideoPoet

**VideoPoet** estende l'approccio autoregressivo alla generazione multimodale: invece di decodificare solo testo, decodifica immagini, video e audio. Permette generazione cross-modale (es. testo-to-video, video-to-audio), unificando diverse modalità in un unico framework autoregressivo basato su Transformer.

---

## 3. Modelli a Variabili Latenti e VAE

### Intuizione

I **modelli a variabili latenti** assumono che i dati osservati siano generati da variabili nascoste (latenti) che catturano i fattori sottostanti non direttamente visibili. Ad esempio, in un'immagine di un volto, le variabili latenti potrebbero rappresentare illuminazione, emozione e identità; in un testo, il topic o il sentiment.

L'assunzione fondamentale è che esista una **rappresentazione a dimensionalità inferiore** dei dati. Un'immagine complessa con milioni di pixel potrebbe essere descritta da poche variabili: posizione degli oggetti, classe, attributi. Se riuscissimo ad apprendere questa rappresentazione $z$, potremmo campionare nuovi $z$ e generare nuovi dati $x$.

### Formalizzazione

Sia $z$ la variabile latente (non osservata) e $x$ il dato osservato. Per generare un nuovo campione:

$$

z \sim p_Z(z), \quad x \sim p_\theta(x \mid z)

$$

La likelihood (verosimiglianza) si ottiene **marginalizzando** sulle variabili latenti:

$$

p_\theta(x) = \sum_z p_Z(z) \, p_\theta(x \mid z)

$$

L'addestramento richiede di massimizzare questa likelihood. Il problema è che il calcolo della marginalizzazione è **intrattabile** quando $z$ vive in uno spazio ad alta dimensionalità: non si possono enumerare tutti i possibili valori di $z$.

### Soluzioni al problema dell'intrattabilità

- **Opzione 1**: se $z$ assume pochi valori discreti, la somma è calcolabile → trattabile

- **Opzione 2**: campionamento casuale, ma è improbabile che un $z$ campionato a caso sia compatibile con un dato $x$ specifico

- **Opzione 3**: importance sampling, campionando da una distribuzione alternativa $q(z)$ più "informata"

- **Opzione 4** (la soluzione dei VAE): scegliere $q(z)$ come distribuzione semplice e apprendibile (es. Gaussiana) tale che $q_\phi(z \mid x) \approx p_\theta(z \mid x)$, dove $q_\phi$ è un'altra rete neurale (l'encoder)

### Variational Autoencoder (VAE)

Il **VAE** implementa l'opzione 4 con un'architettura encoder-decoder:

- **Encoder** $q_\phi(z \mid x)$: prende i dati $x$ e produce i parametri di una distribuzione Gaussiana ($\mu, \sigma^2$) nello spazio latente. L'output dell'encoder deve seguire una distribuzione semplice (prior), tipicamente una Gaussiana unitaria $\mathcal{N}(0, I)$.

- **Campionamento**: si campiona $z$ dalla distribuzione Gaussiana parametrizzata dall'encoder

- **Decoder** $p_\theta(x \mid z)$: prende $z$ e genera la ricostruzione $\hat{x}$ dei dati originali

La funzione obiettivo del VAE combina due termini:

$$

\underbrace{\|x - \hat{x}\|_2^2}_{\text{reconstruction loss}} + \underbrace{D_{\text{KL}}(q_\phi(z \mid x) \| p(z))}_{\text{regularizzazione}}

$$

- Il **primo termine** (reconstruction loss) penalizza l'errore di ricostruzione: l'output deve essere simile all'input. Corrisponde a massimizzare la likelihood $p_\theta(x \mid z)$.

- Il **secondo termine** (KL divergence) è un regolarizzatore che forza la distribuzione appresa $q_\phi(z \mid x)$ ad essere simile al prior $p(z) = \mathcal{N}(0, I)$. Questo garantisce che lo spazio latente sia "liscio" e continuo, permettendo la generazione di nuovi campioni.

### Generazione con il VAE

Dopo l'addestramento, per generare nuovi campioni si **scarta l'encoder** e si campiona direttamente dal prior:

$$

z \sim \mathcal{N}(0, I) \quad \rightarrow \quad \text{Decoder} \quad \rightarrow \quad \text{nuovo campione } x

$$

---

## 4. Generative Adversarial Networks (GAN)

### Modelli impliciti

Le GAN sono **modelli generativi impliciti**: non modellano esplicitamente la distribuzione $p_\theta(x)$, ma possono solo **campionare** da essa. Un generatore $q_\phi(z)$ produce campioni a partire da rumore $z \sim p(z)$ (es. distribuzione Gaussiana o uniforme). L'obiettivo è regolare i parametri $\phi$ in modo che i campioni generati sembrino provenire da $p_{\text{data}}$.

### Architettura: gioco a due giocatori

Le GAN consistono in due reti neurali che competono in un **gioco minimax**:

- **Generatore** ($G$): riceve rumore casuale $z$ e produce un campione "falso" $G(z)$. Il suo obiettivo è generare dati così realistici da ingannare il discriminatore.

- **Discriminatore** ($D$): riceve sia dati reali dalla distribuzione dei dati sia dati falsi dal generatore, e deve classificarli come "reale" o "falso". Il suo obiettivo è distinguere correttamente tra i due.

La funzione obiettivo è:

$$

\min_G \max_D \; \mathbb{E}_{x \sim p_{\text{data}}}[\log D(x)] + \mathbb{E}_{z \sim p(z)}[\log(1 - D(G(z)))]

$$

- Il **discriminatore** $D$ massimizza: vuole assegnare probabilità alta (vicino a 1) ai dati reali e bassa (vicino a 0) ai dati generati

- Il **generatore** $G$ minimizza: vuole che il discriminatore assegni probabilità alta anche ai dati falsi, cioè vuole che $D(G(z))$ sia vicino a 1

All'**equilibrio** (convergenza), il discriminatore non riesce più a distinguere dati reali da falsi: i campioni generati sono indistinguibili da quelli reali.

### Addestramento e campionamento

$D$ e $G$ sono reti neurali standard, addestrate con SGD e backpropagation. Tuttavia, l'addestramento delle GAN è notoriamente **instabile**: il gioco minimax può divergere, oscillare o collassare (mode collapse, dove il generatore produce sempre lo stesso output). Numerosi paper successivi hanno proposto soluzioni per stabilizzare il training.

Una volta addestrate, si **scarta il discriminatore** $D$. Per generare nuovi campioni, si campiona rumore $z \sim p(z)$ e lo si passa attraverso $G$:

$$

z \sim p(z) \quad \rightarrow \quad G(z) \quad \rightarrow \quad \text{campione generato}

$$

La qualità delle immagini generate è migliorata enormemente negli anni: dalle GAN originali del 2014 (immagini sfocate) a StyleGAN2 nel 2019 (volti fotorealistici indistinguibili da foto reali).

### GAN Condizionali (pix2pix)

Non è necessario partire sempre da rumore. Le **GAN condizionali** come **pix2pix** prendono un'immagine strutturata come input (es. una mappa di bordi, una mappa semantica) e generano l'immagine realistica corrispondente. Il generatore ha un'architettura encoder-decoder (come un autoencoder convoluzionale) o **U-Net**, poiché input e output devono avere le stesse dimensioni. Il discriminatore impara a distinguere tra coppie {input, output reale} e {input, output generato}.

---

## 5. Modelli di Diffusione (DDPM)

### Idea ispirata alla termodinamica

I **Denoising Diffusion Probabilistic Models (DDPM)** sono modelli generativi ispirati alla termodinamica. L'idea è elegante: si **aggiunge rumore gradualmente** ai dati (processo forward) fino a trasformarli in puro rumore Gaussiano, poi si **addestra una rete neurale a invertire il processo** (processo reverse), imparando a rimuovere il rumore passo per passo. Concettualmente simili alle GAN (modelli impliciti: non calcolano $p_\theta(x)$ direttamente), ma funzionano **molto meglio** e sono più stabili da addestrare.

### Processo Forward (Diffusione)

Dato un'immagine reale $x_0$, il processo forward produce una sequenza $x_1, x_2, \ldots, x_T$ aggiungendo rumore Gaussiano ad ogni passo. La quantità di rumore è controllata da uno schedule di varianze $\beta_1, \beta_2, \ldots, \beta_T$ (tipicamente $\beta_1 \approx 10^{-4}$, $\beta_T \approx 0.02$). La transizione da $x_{t-1}$ a $x_t$ è definita come:

$$

q(x_t \mid x_{t-1}) = \mathcal{N}(x_t; \sqrt{1 - \beta_t} \, x_{t-1}, \; \beta_t I)

$$

Una proprietà fondamentale è che si può saltare direttamente da $x_0$ a qualsiasi $x_t$ senza calcolare tutti i passi intermedi:

$$

x_t = \sqrt{\bar{\alpha}_t} \, x_0 + \sqrt{1 - \bar{\alpha}_t} \, \epsilon, \quad \text{dove } \epsilon \sim \mathcal{N}(0, I)

$$

Con $\alpha_t = 1 - \beta_t$ e $\bar{\alpha}_t = \prod_{s=1}^{t} \alpha_s$. Intuitivamente, dato un campione reale $x_0$, possiamo ottenere una versione rumorosa a qualsiasi passo $t$ semplicemente mescolando $x_0$ con rumore $\epsilon$, pesati da $\bar{\alpha}_t$. Più $t$ è grande, più l'immagine è rumorosa.

### Processo Reverse (Denoising)

Il processo reverse è parametrizzato da una rete neurale $\epsilon_\theta$ che predice **quanto rumore** è stato aggiunto all'immagine. Dato $x_t$ (l'immagine rumorosa) e il passo temporale $t$, la rete predice il rumore $\epsilon_\theta(x_t, t)$. Sottraendo il rumore predetto da $x_t$ si ottiene un'immagine meno rumorosa $x_{t-1}$.

### Addestramento

In pratica, non si inverte il processo in un solo passo (troppo difficile). Si campiona $t$ da una distribuzione uniforme e si minimizza l'errore quadratico medio tra il rumore predetto e quello reale:

$$

L = \mathbb{E}_{t, x_0, \epsilon}\left[\|\epsilon - \epsilon_\theta(x_t, t)\|^2\right]

$$

Il modello impara: "data un'immagine rumorosa e il livello di rumore $t$, predici il rumore che è stato aggiunto".

### Campionamento (Inferenza)

Per generare un nuovo campione:

- Si parte da puro rumore Gaussiano: $x_T \sim \mathcal{N}(0, I)$

- Per $t = T, T-1, \ldots, 1$: si predice il rumore $\epsilon_\theta(x_t, t)$ e si sottrae per ottenere $x_{t-1}$

- Il processo si ripete per un numero predefinito di passi fino a ottenere l'immagine finale $x_0$

### Architettura: U-Net

I modelli di diffusione usano tipicamente una **U-Net** come rete di predizione del rumore (la stessa architettura usata per la segmentazione di immagini). La U-Net predice il rumore $\epsilon$ dati l'input rumoroso $x_t$ e il passo temporale $t$. Il timestep $t$ viene convertito in una rappresentazione ad alta dimensionalità tramite un time encoder e iniettato nella rete.

### Modelli di diffusione condizionali

Il DDPM base è **incondizionato**: genera campioni casuali senza controllo. I modelli di diffusione **condizionali** incorporano informazione aggiuntiva nel processo:

- **Etichette di classe**: generare immagini di una classe specifica

- **Mappe semantiche**: generare immagini da layout

- **Keypoint**: generare pose specifiche

- **Testo**: generare immagini da descrizioni testuali

### Modelli Text-to-Image

Nei modelli **text-to-image** (T2I), il testo (caption) viene codificato da un text encoder (es. BERT, CLIP) e iniettato nella U-Net tramite **cross-attention layers**. In queste layer: le **query** ($Q$) provengono dalle feature dei pixel, mentre le **chiavi** ($K$) e i **valori** ($V$) provengono dagli embedding testuali. In inferenza, partendo da rumore Gaussiano e una caption, il modello genera immagini coerenti con il testo.

### ControlNet

**ControlNet** estende i modelli T2I permettendo condizionamento aggiuntivo, come sketch-to-image: dato un disegno a mano come input, il modello genera una versione realistica e colorata dello sketch.

### Latent Diffusion Models

I Latent Diffusion Models eseguono la diffusione in uno **spazio latente compresso** anziché nello spazio dei pixel, per efficienza. Un encoder comprime $x$ in $z$ nello spazio latente, il DDPM opera su $z$, e un decoder ricostruisce l'immagine ad alta risoluzione dal $z$ denoised. Questo riduce enormemente il costo computazionale.

### Diffusion Language Models

L'idea della diffusione è stata estesa anche al testo. Invece di predire una parola alla volta (come i modelli autoregressivi), i **Diffusion Language Models** generano testo **denoising** una frase corrotta in parallelo. A differenza delle immagini (rumore Gaussiano continuo), il linguaggio usa **corruzione discreta**: mascheramento di token ([MASK]), sostituzione con token casuali, cancellazione o riordino di parole. Ad ogni passo il modello predice una distribuzione per ogni posizione, raffinando progressivamente il testo.

---

## 6. Adattamento dei Foundation Models

### Cosa sono i Foundation Models?

I **Foundation Models (FM)** sono reti neurali profonde molto grandi (tipicamente basate su Transformer) addestrate su dataset di scala internet:

- CLIP: 400M coppie immagine-testo

- BERT: BooksCorpus (800M parole) + Wikipedia (2500M parole)

- GPT-3: miliardi di token da diverse fonti

Grazie al pre-training massiccio, i FM sono **general-purpose learners** con buona capacità di generalizzazione. Il pre-training è la fase più costosa nella vita di un modello.

### Perché adattarli?

Nonostante siano versatili, i FM hanno **limitazioni su task specifici**: un LLM addestrato con next token prediction è bravo a generare testo, ma il suo output potrebbe non seguire il formato desiderato dall'utente. Ad esempio, alla query "The capital of France is", LLaMA-base potrebbe generare un lungo paragrafo su Parigi quando l'utente vuole semplicemente "Paris".

L'**adattamento** consiste nello sterzare il comportamento del modello base verso le nostre esigenze specifiche (assistente utile, no output tossico, formato specifico, task specializzato) tramite prompting o tuning su dati curati.

---

## 7. Full Fine-Tuning e Adattamento di BERT

### Full Fine-Tuning

L'approccio più diretto è il **full fine-tuning**: si aggiornano **tutti** i parametri del modello pre-addestrato su un dataset etichettato del task downstream.

**Pro**: massima flessibilità di apprendimento e alte prestazioni sul task specifico.

**Contro**: richiede enorme memoria GPU e rischia il **catastrophic forgetting**, dove le informazioni generali apprese durante il pre-training vengono cancellate.

### Requisiti di memoria

Per il fine-tuning in full-precision (FP32) con ottimizzatore Adam, ogni parametro richiede:

- 4 byte per il peso del modello

- 4 byte per il gradiente

- 4 byte per il primo momento di Adam (momentum)

- 4 byte per il secondo momento di Adam (varianza)

Totale: **16 byte per parametro**. Per un LLM da 7B parametri: $7 \times 10^9 \times 16 = 112$ GB di VRAM. Le GPU consumer hanno tra 16-80 GB: **insufficienti** per il full fine-tuning di un LLM. Servono metodi più efficienti.

### Adattamento di BERT

BERT è un Transformer encoder-only pre-addestrato con Masked Language Modelling (MLM) e Next Sentence Prediction (NSP), entrambi obiettivi self-supervised. Dopo il pre-training, BERT non è direttamente pronto per task downstream: serve un **modello predittore** aggiuntivo:

$$

y = \text{Predict}_\omega(\text{BERT}_{\hat{\theta}}(x))

$$

dove $\hat{\theta}$ sono i parametri pre-addestrati (tipicamente congelati) e $\omega$ i parametri del predittore addestrati sul task specifico.

### Classificazione di testo singolo

Per la classificazione di sentimento (es. recensioni positive/negative), la rappresentazione $h_{\text{CLS}}$ del token [CLS] viene passata a un MLP + Softmax. Durante l'adattamento, si ottimizzano solo i parametri del predittore con un dataset etichettato.

### Classificazione di coppie di testi

Per task come il **Text Entailment Judgement** (data una premessa e un'ipotesi, determinare se c'è entailment, contraddizione o neutralità), si danno entrambe le frasi come input a BERT separate dal token [SEP] e si classifica usando il token [CLS].

### BERT come encoder in modelli generativi

BERT può essere usato per inizializzare l'encoder (e parzialmente il decoder) di modelli encoder-decoder per task generativi come la traduzione automatica.

---

## 8. Prompting e In-Context Learning

### Prompting

Il **prompting** consiste nel fornire un input specifico (prompt) a un LLM per guidare la generazione dell'output desiderato, **senza fine-tuning**. Un prompt ben progettato può guidare il modello a produrre risposte accurate e contestualmente appropriate. La disciplina di progettazione dei prompt si chiama **prompt engineering**.

Esempio tipico: "Translate the text from English to Chinese. Text: The early bird catches the worm. Answer:" → il modello genera la traduzione.

Non esiste un template universale per i prompt, ma un formato popolare è lo stile "name:content" con specifica chiara del task (Task, Source language, Target language, Style, Template, Answer). La ricerca del prompt giusto è un **processo iterativo**.

### In-Context Learning (ICL)

Quando specificare il task in modo chiaro non è sufficiente, si forniscono **esempi dimostrativi** (demonstrations) del task direttamente nel prompt. Il modello "impara" dal contesto degli esempi forniti, senza alcuna modifica dei pesi. Questo approccio si chiama **in-context learning** e consiste tipicamente in:

- Un messaggio **SYSTEM** che definisce il ruolo del modello

- Uno o più **DEMO** con esempi input-output del task

- L'input **USER** per cui si vuole l'output

### Chain-of-Thought Prompting (CoT)

Per problemi complessi (es. aritmetica, ragionamento logico), anche l'ICL con semplici coppie domanda-risposta può fallire. Il **Chain-of-Thought prompting** aggiunge passi di ragionamento intermedi nelle dimostrazioni: non solo la risposta finale, ma l'intero processo di risoluzione. Ad esempio, invece di mostrare solo "Q: media di 2, 4, 9 → A: 5", si mostra:

"Calcola 2 + 4 + 9 = 15. Ci sono tre numeri. Dividi la somma per il conteggio: 15/3 = 5. La risposta è 5."

I passi di ragionamento esplicitati insegnano al modello **come** pensare, non solo **cosa** rispondere.

### Learning to Prompt (Prompt Optimization)

Il prompt engineering manuale ha limiti:

- Progettare prompt di alta qualità è difficile e richiede competenza umana

- I prompt manuali possono essere ridondanti e lunghi, aumentando i costi computazionali

La **prompt optimization** automatizza il processo di progettazione dei prompt. Un approccio specifico è il **soft prompting**: mentre i prompt "hard" sono sequenze di token discreti (testo leggibile), i **soft prompt** sono **vettori continui apprendibili** nello spazio degli embedding, ottimizzati come normali parametri tramite backpropagation. Non sono interpretabili dall'uomo, ma sono più compatti ed efficaci.

---

## 9. Parameter Efficient Fine-Tuning (PEFT)

### Motivazione

Per superare i limiti sia del full fine-tuning (troppo costoso, catastrophic forgetting) sia del prompt engineering (manuale, limitato), il **Parameter Efficient Fine-Tuning (PEFT)** introduce un paradigma intermedio:

- Apprende solo una **piccola frazione** dei parametri del modello base, mantenendo la maggior parte congelati → **efficiente in memoria**

- Elimina la necessità di progettare prompt a mano

- Offre prestazioni **competitive** rispetto al full fine-tuning

- Riduce il rischio di catastrophic forgetting poiché la maggior parte dei pesi pre-addestrati resta inalterata

### Prefix Tuning

Nel **prefix tuning**, si aggiunge una serie di **vettori apprendibili** (prefissi o soft prompt) nel Transformer. Durante il fine-tuning, si apprendono **solo i prefissi** mentre tutti i parametri del modello e il layer di embedding restano congelati. L'addestramento usa una cross-entropy loss standard. Due varianti:

- **Variante 1**: i prefissi vengono inseriti all'input di **ogni layer** del Transformer

- **Variante 2**: i prefissi vengono inseriti come embedding apprendibili solo all'**inizio** del Transformer

I vettori apprendibili catturano l'essenza del task downstream.

### Adapter

Gli **Adapter** sono moduli aggiuntivi inseriti **tra i layer** di una rete pre-addestrata. Solo i parametri degli adapter vengono ottimizzati; tutto il resto è congelato. Un adapter layer ha una struttura a **bottleneck**:

- **Layer lineare di down-projection**: riduce la dimensionalità delle feature a una dimensione inferiore

- **Funzione di attivazione non lineare**

- **Layer lineare di up-projection**: riporta la dimensionalità a quella originale

- **Connessione residua**: l'input dell'adapter viene sommato all'output

Gli adapter possono essere usati sia con BERT che con LLM decoder-only.

### Low-Rank Adaptation (LoRA)

**LoRA** è una delle tecniche PEFT più popolari. Introduce matrici **low-rank** leggere per apprendere informazione specifica del task. Si basa su due idee chiave:

**Idea 1**: gli aggiornamenti dei pesi vengono appresi separatamente con un $\Delta W$ dedicato, mantenendo i pesi pre-addestrati $W_0$ congelati:

$$

W = \underbrace{W_0}_{\text{freeze}} + \underbrace{\Delta W}_{\text{learn}}

$$

**Idea 2**: invece di apprendere una matrice $\Delta W \in \mathbb{R}^{d \times d}$ di rango pieno, si apprendono due matrici di **rango basso**: $\Delta W = BA$, dove $B \in \mathbb{R}^{d \times r}$, $A \in \mathbb{R}^{r \times d}$ e $r \ll d$.

L'output finale è:

$$

h = W_0 x + \Delta W x = W_0 x + BAx = \underbrace{(W_0 + BA)}_{\text{merged}} x

$$

**Esempio numerico**: con $d = 1024$ e rango $r = 4$, la matrice originale $W$ ha $1024 \times 1024 \approx 1M$ parametri. Le matrici LoRA $A \in \mathbb{R}^{4 \times 1024}$ e $B \in \mathbb{R}^{1024 \times 4}$ hanno $4 \times 1024 \times 2 \approx 8k$ parametri: circa lo **0.8%** dei parametri originali.

**Dopo il fine-tuning**, le matrici low-rank vengono **fuse** (merged) nei pesi originali: $W = W_0 + BA$. A differenza degli adapter, LoRA **non aggiunge parametri extra** al modello finale: la dimensione resta identica.

### Requisiti di memoria di LoRA

Per LoRA allo 0.1% dei parametri totali su un modello da 7B:

- Pesi base: $4$ byte/param (congelati, nessun gradiente o stato optimizer)

- Pesi LoRA: $16 \times 0.001$ byte/param (peso + gradiente + momentum + varianza)

- Totale: $4 + 0.016 = 4.016$ byte/param

- Memoria: $7 \times 10^9 \times 4.016 \approx 28.11$ GB, contro i 112 GB del full fine-tuning

### Adattamento di Transformer per la visione

#### Textual Prompt Tuning (TPT) per CLIP

Si introducono $M$ vettori apprendibili $V = \{V_1, \ldots, V_M\}$ che vengono **concatenati ai nomi delle classi** e dati come input al text encoder di CLIP. Durante il fine-tuning si addestrano **solo** i vettori apprendibili, mantenendo congelati sia l'image encoder che il text encoder.

#### Visual Prompt Tuning (VPT) per ViT

Due varianti:

- **VPT-Deep**: prompt (prefissi) apprendibili inseriti come input in **ogni layer** del ViT

- **VPT-Shallow**: prompt apprendibili inseriti solo **all'input** del ViT

In entrambi i casi, si addestra anche un classificatore (MLP) sulla rappresentazione del token [CLS]. Tutto il resto è congelato.

### Considerazioni pratiche sull'adattamento

Quando si adatta un Foundation Model, bisogna bilanciare diversi fattori:

- **Costo vs. prestazioni**: full fine-tuning = massime prestazioni; PEFT = prestazioni quasi equivalenti a costo molto inferiore

- **Disponibilità dati**: grandi dataset → full o instruction tuning; piccoli dataset → PEFT o prompt tuning

- **Budget computazionale**: full fine-tuning richiede centinaia di GB di GPU; LoRA richiede molta meno memoria

- **Rischio di catastrophic forgetting**: il full fine-tuning può cancellare conoscenze generali; PEFT lo riduce

- **Estensibilità**: con il full fine-tuning, il modello va ri-addestrato quando il modello base cambia; PEFT permette hot-swapping e iterazione rapida

---

## 10. Adattamento di Modelli di Diffusione

### DreamBooth

**DreamBooth** è una tecnica per personalizzare un modello T2I: mentre i modelli T2I generano oggetti generici (es. "un cane qualsiasi"), DreamBooth permette di generare immagini dello **stesso oggetto specifico** (es. il tuo cane) in contesti diversi, facendo fine-tuning su poche immagini del soggetto.

L'approccio è simile al Textual Prompt Tuning: si sceglie un **token raro** nel vocabolario, denotato $[V]$ (es. "sks", poco frequente nei dati di training), e si creano prompt come "A [V] dog". Attraverso il fine-tuning dei parametri della U-Net e degli embedding di $[V]$, si crea un legame tra il soggetto specifico e il token speciale.

DreamBooth usa **due loss**:

- **Reconstruction loss**: $L_{\text{mse}} = \|\hat{x}_\theta(\alpha_t x + \sigma_t \epsilon, c) - x\|_2^2$, che forza il modello a ricostruire le immagini del soggetto

- **Prior preservation loss**: $L_{\text{prior}} = \|\hat{x}_\theta(\alpha_t x_{\text{pr}} + \sigma_t \epsilon, c_{\text{pr}}) - x_{\text{pr}}\|_2^2$, dove $x_{\text{pr}}$ è un'immagine generata dal modello pre-addestrato. Questa loss incoraggia la **diversità** nella generazione, impedendo che il modello dimentichi come generare oggetti generici della stessa categoria

### Textual Inversion

**Textual Inversion** ha un obiettivo simile a DreamBooth: personalizzare un modello T2I per generare immagini di un soggetto specifico. Si introduce un token speciale $S^*$ nel prompt "A photo of $S^*$" e si **apprendono solo gli embedding** $v^*$ di questo token, mantenendo congelati sia il text encoder che la U-Net. È un approccio ancora più leggero di DreamBooth, poiché modifica solo gli embedding di un singolo token.

### Prompt-to-Prompt

**Prompt-to-Prompt** permette di **editare dettagli** di un'immagine generata attraverso modifiche testuali, senza cambiare il layout della scena. Ad esempio, si può cambiare lo stile da dipinto realistico a disegno per bambini, oppure sostituire un oggetto ("bicycle" → "motorcycle") mantenendo la composizione identica.

#### Come funziona: Cross-Attention Control

Il meccanismo si basa sulle **mappe di cross-attention** della U-Net dei modelli T2I:

- Nella cross-attention, le **query** ($Q$) derivano dalle feature dei pixel e le **chiavi/valori** ($K$, $V$) dagli embedding testuali

- La mappa di attenzione $M = \text{softmax}\!\left(\frac{QK^\top}{\sqrt{d}}\right)$ indica quanto ogni pixel è "attratto" da ogni parola del prompt: i pixel dell'orso saranno correlati alla parola "bear", quelli dell'uccello alla parola "bird"

- Nel processo di denoising, il layout grossolano viene determinato nei **primi passi** ($t \approx T$), mentre i dettagli fini vengono definiti negli **ultimi passi** ($t \approx 1$)

Per l'editing **Word Swap** (es. "bicycle" → "motorcycle"): nei primi passi di denoising si usano le mappe di attenzione del prompt originale $P$ (per preservare il layout), negli ultimi passi si usano quelle del prompt modificato $P^*$ (per cambiare solo i dettagli fini).

Altre operazioni supportate:

- **Prompt refinement**: aggiungere frasi al prompt modificato

- **Fader control**: controllare l'intensità con cui un attributo influenza l'immagine generata

---

## 11. Apprendimento Non Supervisionato Avanzato: DINO

### Perché il representation learning visivo è importante?

Abbiamo enormi quantità di dati non etichettati (immagini, video) ma pochissimi dati di alta qualità per task downstream specifici (es. dati robotici con informazioni su azioni e stati). L'obiettivo è apprendere un **modello general-purpose** tramite self-supervised learning e poi trasferirlo al task downstream con fine-tuning minimale.

### DINO: Self-Distillation with No Labels

**DINO** è un algoritmo di self-supervised learning per Vision Transformer (ViT). La scoperta chiave è che, a differenza dell'apprendimento supervisionato, un ViT addestrato con DINO produce mappe di attenzione che contengono **informazione esplicita sulla segmentazione semantica** dell'immagine, anche senza etichette di pixel o di classe.

#### Algoritmo di training

- Data un'immagine $x$, si creano **due viste** $x_1$ e $x_2$ tramite trasformazioni fotometriche (cropping casuale, rotazione, jitter)

- Due reti: **student** $g_{\theta_s}$ e **teacher** $g_{\theta_t}$

- Ogni rete produce una predizione (distribuzione di probabilità su $K$ output) sulla propria vista

- Si calcola una **cross-entropy loss** dove la predizione del teacher ($p_2$) è il target per lo student ($p_1$)

- Il gradiente viene retropropagato **solo attraverso lo student**; il teacher non riceve gradienti (stop gradient)

#### Aggiornamento del teacher: Exponential Moving Average (EMA)

I pesi del teacher vengono aggiornati come media mobile esponenziale dei pesi dello student:

$$

\theta_t^{(i+1)} = \alpha \, \theta_t^{(i)} + (1 - \alpha) \, \theta_s^{(i)}, \quad \alpha \in [0, 1)

$$

Con $\alpha$ tipicamente vicino a 1, il teacher è una **versione lisciata** (smoothed) dello student.

#### Prevenzione del collasso

In DINO non ci sono coppie negative (a differenza di SimCLR). Senza precauzioni, le due reti potrebbero predire la stessa distribuzione costante, minimizzando la loss senza apprendere nulla. Per prevenire il collasso, si applicano all'output del teacher:

- **Centering**: sottrae la media del mini-batch $c$ dall'output, impedendo che una dimensione domini (rende la distribuzione più uniforme)

- **Sharpening**: divide per una temperatura piccola $\tau \to 0$, rendendo la distribuzione "peaked" (concentrata)

$$

p_2 = \text{softmax}\!\left(\frac{z_t - c}{\tau}\right), \quad c \leftarrow mc + (1 - m) \frac{1}{B} \sum_{i=1}^{B} g_{\theta_t}(x_i)

$$

#### Mappe di attenzione di DINO

Visualizzando le mappe di attenzione dell'ultimo layer corrispondenti al token [CLS], si osserva che diverse teste di attenzione (heads) si focalizzano su diverse parti dell'immagine, rappresentando diversi oggetti o parti di oggetti.

### DINOv2

**DINOv2** è l'evoluzione di DINO. Le migliorie principali che stabilizzano il training e migliorano le prestazioni sono:

- **Dati non etichettati di qualità superiore**

- **Modelli più grandi** (scaling laws: modelli più grandi → prestazioni migliori)

- **Loss di training migliorate**

Le scaling laws confermano che aumentando sia la dimensione del modello (da ViT-S con 5M parametri a ViT-Giant con 1.8B) sia la dimensione del dataset (da ImageNet-22k con 14M immagini a LVD-142M con 142M immagini), le prestazioni migliorano costantemente.

#### Knowledge Distillation

Modelli più grandi hanno prestazioni migliori ma richiedono più memoria in inferenza. Per deployment in ambienti con risorse limitate, la **Knowledge Distillation (KD)** permette di trasferire la conoscenza di un modello grande (teacher) in uno più piccolo (student) con prestazioni quasi equivalenti.

Il teacher è una rete più grande e ben addestrata; lo student è una rete più piccola inizializzata da zero. Il teacher produce logit che il student deve riprodurre (**logit matching**). La loss è la KL divergence tra le probabilità del teacher e dello student:

$$

\text{KL}(p_t \| p_s) = \sum_{i \in C} p_{t,i} \log \frac{p_{t,i}}{p_{s,i}}

$$

In DINOv2 si dimostra che un ViT-L/14 distillato da un ViT-g/14 ha prestazioni **migliori** di un ViT-L/14 addestrato da zero, nonostante abbiano la stessa dimensione.

### DINOv3

**DINOv3** è l'ultima evoluzione: dataset di training ancora più grande, modello fino a **7B parametri** (tipicamente gli image encoder sono sotto 1B), modifiche architetturali e loss migliorate. Le prestazioni sono impressionanti in classificazione, segmentazione e altri task di computer vision. Il backbone DINOv3 congelato funziona come **feature extractor universale** che spesso funziona out-of-the-box senza fine-tuning.

Le feature PCA di DINOv3 (ridotte a 3D e mappate come RGB) mostrano una notevole capacità di segmentare parti diverse degli oggetti senza alcuna etichetta. Le mappe di attenzione tra patch mostrano alta attivazione tra token simili (es. pixel appartenenti allo stesso oggetto).

---

## 12. Masked Autoencoder (MAE)

### Idea fondamentale

Il **Masked Autoencoder (MAE)** è un algoritmo di self-supervised learning che **maschera patch casuali** dell'immagine di input e addestra un modello a **ricostruire i pixel mancanti**. L'approccio è analogo al pre-training di BERT (dove si mascherano token nel testo), ma applicato alle immagini.

L'intuizione è che, per riuscire a ricostruire un'immagine da informazione parziale, il modello deve necessariamente **apprendere informazione semantica rilevante**: deve capire la struttura degli oggetti, le relazioni spaziali, le texture e il contesto.

### Architettura

Durante il pre-training, una grande percentuale casuale di patch (es. **75%**) viene mascherata. MAE ha due componenti:

- **Encoder** (es. un ViT): elabora **solo i patch visibili**, codificandoli in embedding. Questo è computazionalmente efficiente poiché l'encoder processa solo il 25% dei patch.

- **Decoder**: riceve gli embedding dei patch visibili più i **masked tokens** (token apprendibili che indicano le posizioni mascherate). Il suo compito è ricostruire l'immagine originale in pixel.

Dopo il pre-training, il **decoder viene scartato** e l'encoder viene applicato a immagini intere (non corrotte) per task di riconoscimento downstream.

### Applicazioni

Il pre-training MAE scala bene con più dati e modelli più grandi, ed è stato esplorato in molti domini con abbondanza di dati non etichettati:

#### SatMAE (Satellite MAE)

Applicato a immagini satellitari, dove i satelliti (es. Sentinel) acquisiscono centinaia di gigabyte al giorno. I satelliti producono dati con informazione aggiuntiva:

- **Informazione temporale**: immagini della stessa posizione geografica in periodi diversi dell'anno

- **Informazione multi-spettrale**: immagini in bande diverse (RGB, infrarosso, ecc.)

SatMAE codifica queste informazioni aggiuntive (tempo e bande spettrali) nell'encoder per apprendere rappresentazioni consapevoli del tempo e delle bande.

#### VideoMAE

Estende MAE ai video non etichettati. Il mascheramento viene applicato a **cubi** (insiemi di token nella stessa posizione spaziale ma attraverso frame diversi), con percentuali molto alte (90-95%). La ricostruzione dei cubi mascherati forza il modello ad apprendere **feature spazio-temporali**.

---

## 13. Joint-Embedding Predictive Architecture (JEPA)

### Framework generale

**JEPA** è un framework generale per il self-supervised representation learning. L'obiettivo è catturare le dipendenze tra due input compatibili $x$ e $y$ (es. frame video e audio corrispondente) **nello spazio delle rappresentazioni**, invece di generare $y$ da $x$ nello spazio degli input.

### Differenza rispetto ai modelli generativi

La differenza chiave tra architettura generativa e JEPA è che in JEPA la **loss viene applicata nello spazio degli embedding**, non nello spazio degli input. JEPA predice le **rappresentazioni** (embedding) di un segnale $y$ a partire da un segnale compatibile $x$, usando un predictor network condizionato su una variabile latente aggiuntiva $z$ che cattura l'informazione necessaria per la predizione non presente in $x$.

### I-JEPA (Image-based JEPA)

**I-JEPA** usa un singolo blocco contesto per predire le **rappresentazioni di vari blocchi target** provenienti dalla stessa immagine in posizioni specifiche. L'encoder produce rappresentazioni dei patch visibili, e il predictor produce le rappresentazioni dei patch mascherati. A differenza di MAE che ricostruisce pixel, I-JEPA predice embedding: questo lo rende meno sensibile ai dettagli di basso livello e più focalizzato sulla semantica.

### V-JEPA 2 (Video JEPA)

**V-JEPA 2** estende il framework JEPA ai video:

- Un **video clip** viene patchificato in una sequenza di token e una maschera viene applicata rimuovendo un sottoinsieme di token

- L'**encoder** elabora la sequenza video mascherata e produce un embedding per ogni token di input

- Gli output dell'encoder vengono **concatenati** con token mask apprendibili (che specificano le posizioni dei patch mascherati) e processati dal **predictor**

- Gli output del predictor vengono confrontati con i target di predizione tramite una **loss L1**

- I target vengono calcolati da un **ema-encoder** (i cui pesi sono una media mobile esponenziale dei pesi dell'encoder principale)

Dopo il pre-training, l'encoder video viene **congelato** e si apprende un nuovo predictor condizionato sulle azioni (**V-JEPA 2-AC**). Questo modello prevede rappresentazioni di frame video futuri condizionati su frame passati, azioni ed end-effector states, utile per task di pianificazione robotica.

### Panoramica dei paradigmi di apprendimento non supervisionato

- **Famiglia contrastiva (SimCLR)**: apprende avvicinando coppie positive e allontanando coppie negative

- **Paradigma emergente (DINO/DINOv2)**: usa self-distillation senza etichette

- **Approcci predittivi/ricostruttivi (MAE)**: apprende ricostruendo input mascherati

- **Modellazione latente generativa (JEPA)**: predice rappresentazioni, non pixel

Il pre-training è tipicamente costoso, richiedendo migliaia di ore di GPU.
## Indice cap 19-20-21
1. [Introduzione al Reinforcement Learning](#1-introduzione-al-reinforcement-learning)
2. [Terminologie e Framework del RL](#2-terminologie-e-framework-del-rl)
3. [Reward Hypothesis e Ritorno Scontato](#3-reward-hypothesis-e-ritorno-scontato)
4. [Trade-off Esplorazione/Sfruttamento e Policy](#4-trade-off-esplorazionesfruttamento-e-policy)
5. [Funzioni di Valore e Equazione di Bellman](#5-funzioni-di-valore-e-equazione-di-bellman)
6. [Apprendimento delle Funzioni di Valore: Monte Carlo e TD](#6-apprendimento-delle-funzioni-di-valore-monte-carlo-e-td)
7. [Q-Learning](#7-q-learning)
8. [Deep Q-Learning](#8-deep-q-learning)
9. [Policy Gradient e REINFORCE](#9-policy-gradient-e-reinforce)
10. [Metodi Actor-Critic](#10-metodi-actor-critic)
11. [Advantage Actor-Critic (A2C)](#11-advantage-actor-critic-a2c)
12. [Proximal Policy Optimization (PPO)](#12-proximal-policy-optimization-ppo)
13. [Reinforcement Learning from Human Feedback (RLHF)](#13-reinforcement-learning-from-human-feedback-rlhf)
14. [RLHF in Pratica](#14-rlhf-in-pratica)
15. [AI ed Etica](#15-ai-ed-etica)

---

## 1. Introduzione al Reinforcement Learning

### Motivazione

Molti problemi reali non riguardano la predizione di etichette, ma il **prendere decisioni**: un robot che naviga un edificio, un agente che gioca a scacchi, un sistema di guida autonoma. In questi contesti le azioni influenzano lo stato futuro del mondo, il feedback è spesso ritardato o sparso, e non esiste un'"etichetta corretta" per ogni azione. Serve quindi un framework per imparare ad agire quando gli esiti dipendono da una **sequenza di decisioni**: questo framework è il **Reinforcement Learning (RL)**.

### Cos'è il Reinforcement Learning

Il RL è un paradigma di apprendimento in cui un **agente** impara interagendo con un **ambiente** tramite prove ed errori, ricevendo **ricompense** (positive o negative) come unico feedback. L'agente non riceve istruzioni esplicite su cosa fare: deve scoprire da solo quali azioni portano alla massima ricompensa cumulativa nel lungo periodo.

Un'analogia efficace è quella di un giocatore che affronta un videogioco per la prima volta senza conoscere le regole: prova un comando, osserva il risultato (guadagna o perde punti), e nel tempo impara la strategia ottimale.

### Applicazioni

Il RL viene applicato in moltissimi ambiti: addestramento di agenti che giocano a scacchi e Go, guida autonoma (accelerare, frenare, cambiare corsia), pricing dinamico (tariffe ride-sharing, biglietti aerei), robotica di magazzino (ottimizzazione dei percorsi pick-and-deliver) e **fine-tuning di LLM** come ChatGPT, per allinearne le risposte alle preferenze umane.

### Confronto con altri paradigmi

A differenza dell'apprendimento supervisionato (dove una rete confronta le predizioni con le etichette corrette) e dell'apprendimento non supervisionato (dove non esiste un segnale esplicito), nel RL l'agente interagisce con un ambiente, sceglie azioni e riceve ricompense. Non esistono etichette, ma solo un segnale di reward che guida l'apprendimento.

---

## 2. Terminologie e Framework del RL

### Componenti fondamentali

- **Agente (Agent)**: il decision-maker, l'entità che apprende e sceglie le azioni. Può essere un robot, un programma, un modello di linguaggio.

- **Ambiente (Environment)**: tutto ciò che è esterno all'agente e con cui esso interagisce (un videogioco, un labirinto, il mondo reale).

- **Azione (Action)**: le scelte disponibili per l'agente (muoversi a sinistra, saltare, non fare nulla).

- **Stato (State)**: una "fotografia" della situazione corrente dell'agente (posizione nel labirinto, livello di batteria, frame del gioco).

- **Ricompensa (Reward)**: un feedback numerico che indica quanto bene l'agente ha agito. Reward positivo = buona azione, reward negativo = cattiva azione.

### Interazione agente-ambiente

L'interazione avviene su **time step discreti** $t = 0, 1, 2, 3, \ldots$ Ad ogni time step $t$:

- L'agente osserva lo stato corrente $S_t$ dall'ambiente

- L'agente seleziona un'azione $A_t$ in base a $S_t$

Al time step successivo $t+1$:

- L'agente riceve una ricompensa numerica $R_{t+1}$

- L'agente si trova in un nuovo stato $S_{t+1}$

### Traiettoria

La sequenza di interazioni genera una **traiettoria** (o episodio):

$$

S_0, A_0, R_1, S_1, A_1, R_2, S_2, A_2, R_3, \ldots

$$

Ad esempio, in un videogioco l'agente riceve il frame $S_t$, prende l'azione "muovi a destra" $A_t$, il gioco avanza al frame $S_{t+1}$ e restituisce una ricompensa $R_{t+1} = +1$ (il personaggio non è morto).

---

## 3. Reward Hypothesis e Ritorno Scontato

### Reward Hypothesis

L'obiettivo dell'agente è massimizzare la **ricompensa cumulativa** nel lungo periodo, chiamata **ritorno atteso (expected return)**. L'idea centrale del RL è la **reward hypothesis**: tutti gli obiettivi possono essere descritti come la massimizzazione del ritorno atteso cumulativo. Il ritorno cumulativo ad un dato time step $t$ è:

$$

G_t = R_{t+1} + R_{t+2} + R_{t+3} + \cdots = \sum_{k=0}^{\infty} R_{t+k+1}

$$

### Ritorno scontato (Discounted Return)

Non tutte le ricompense hanno lo stesso peso: una ricompensa immediata è generalmente più certa e quindi più "preziosa" di una futura. Per questo si introduce il **fattore di sconto** $\gamma \in [0, 1]$ che pesa le ricompense future:

$$

G_t = R_{t+1} + \gamma R_{t+2} + \gamma^2 R_{t+3} + \cdots = \sum_{k=0}^{\infty} \gamma^k R_{t+k+1}

$$

- **$\gamma$ vicino a 0**: l'agente è "miope", preferisce ricompense immediate. Le potenze $\gamma^1, \gamma^2, \gamma^3, \ldots$ scendono a zero molto rapidamente.

- **$\gamma$ vicino a 1**: l'agente tiene conto anche delle ricompense a lungo termine.

### Esempio

Un topolino in un labirinto vuole mangiare più formaggio possibile evitando il gatto. Il formaggio vicino al gatto è di maggior valore, ma il rischio è alto: il fattore di sconto $\gamma$ vicino a zero causa un rapido decadimento dei reward futuri, rendendo meno attraente il formaggio lontano/rischioso. Se $\gamma$ è vicino a 1, l'agente valuta anche le ricompense future e potrebbe rischiare di più.

---

## 4. Trade-off Esplorazione/Sfruttamento e Policy

### Exploration vs Exploitation

L'**exploration/exploitation trade-off** è un aspetto centrale del RL. L'analogia è la scelta di un ristorante:

- **Exploitation (sfruttamento)**: andare sempre nello stesso ristorante dove il cibo è buono, ma rischiare di perdere un ristorante potenzialmente migliore.

- **Exploration (esplorazione)**: provare ristoranti nuovi, con il rischio di un'esperienza negativa ma anche la possibilità di una scoperta eccezionale.

Un agente RL deve **bilanciare** esplorazione e sfruttamento per massimizzare la ricompensa attesa cumulativa: sfruttare le conoscenze acquisite e al contempo esplorare per scoprire azioni potenzialmente migliori.

### Policy (Politica)

La **policy** $\pi$ è il "cervello" dell'agente: una funzione che determina quale azione intraprendere dato lo stato corrente. Definisce il comportamento dell'agente in ogni momento. Formalmente è un mapping dagli stati alle probabilità di selezionare ogni azione possibile: $\pi(a \mid s) = P[A_t = a \mid S_t = s]$. La policy viene appresa durante il training.

Esistono due tipi di policy:

- **Deterministica**: dato uno stato, restituisce sempre la stessa azione: $a = \pi(s)$. Esempio: "vedi una moneta a destra" → "muovi a destra".

- **Stocastica**: restituisce una distribuzione di probabilità sulle azioni: $\pi(a \mid s) = P[A \mid s]$. Esempio: "moneta a destra ma nemico in avvicinamento" → distribuzione: sinistra 0.1, destra 0.3, salta 0.6.

### Approcci per trovare la policy ottimale $\pi^*$

Risolvere un problema di RL significa trovare una policy che massimizza la ricompensa cumulativa. Due famiglie di approcci:

- **Metodi value-based**: insegnano all'agente quali stati/azioni sono più "preziosi". L'agente apprende una funzione di valore e poi sceglie l'azione che porta agli stati con il valore più alto. Esempio: Q-learning.

- **Metodi policy-based**: insegnano direttamente all'agente quale azione prendere in ogni stato, senza passare per una funzione di valore. Esempio: Policy Gradient.

---

## 5. Funzioni di Valore e Equazione di Bellman

### Funzioni di Valore

Gli algoritmi RL stimano **funzioni di valore** che valutano quanto è buono trovarsi in un certo stato (o compiere una certa azione in un certo stato), in termini di ricompense future attese. Esistono due tipi:

**State-value function** $v_\pi(s)$: quanto ritorno totale possiamo aspettarci dallo stato $s$ seguendo la policy $\pi$:

$$

v_\pi(s) = \mathbb{E}_\pi[G_t \mid S_t = s]

$$

Esempio: in un videogioco, una posizione vicina al goal ha valore alto, una trappola ha valore basso.

**Action-value function** $q_\pi(s, a)$: quanto è buono compiere l'azione $a$ nello stato $s$ e poi seguire la policy $\pi$:

$$

q_\pi(s, a) = \mathbb{E}_\pi[G_t \mid S_t = s, A_t = a]

$$

Esempio: girare a sinistra potrebbe essere meglio che girare a destra → Q-value più alto per "sinistra".

Nei metodi value-based, la policy ottimale si ricava direttamente dalla funzione di valore ottimale:

$$

\pi^*(s) = \arg\max_a q^*(s, a)

$$

Trovare una funzione di valore ottimale equivale a trovare una policy ottimale.

### Equazione di Bellman

L'**equazione di Bellman** è una relazione ricorsiva che semplifica il calcolo del valore di uno stato, evitando di sommare tutte le ricompense future. Anziché calcolare $v(s) = R_{t+1} + R_{t+2} + \cdots$, si decompone il valore in ricompensa immediata più valore scontato dello stato successivo:

$$

v(s) = \mathbb{E}_\pi\big[R_{t+1} + \gamma \, v(S_{t+1}) \mid S_t = s\big]

$$

Questa è la chiave di volta del RL: il valore di uno stato dipende dalla ricompensa immediata e dal valore (scontato) dello stato successivo. Esempio numerico: se un agente ha una sequenza di reward tutti pari a $-1$ e $\gamma = 1$, allora $v(S_t) = -1 + 1 \cdot v(S_{t+1})$, il che evita di ricalcolare tutta la somma da capo.

---

## 6. Apprendimento delle Funzioni di Valore: Monte Carlo e TD

### Monte Carlo

Il metodo **Monte Carlo** attende la fine di un episodio completo, calcola il ritorno effettivo $G_t$ e lo usa come target per aggiornare la stima del valore:

$$

v(S_t) \leftarrow v(S_t) + \alpha \big[G_t - v(S_t)\big]

$$

dove $\alpha$ è il learning rate. Si aspetta la fine dell'episodio per avere il ritorno completo, poi si aggiorna la stima. Esempio: un topolino che vaga nel labirinto termina l'episodio con un ritorno $G_t = 3$; se la stima precedente era $v(0) = 0$ e $\alpha = 0.1$, il nuovo valore diventa $v(0) = 0 + 0.1 \cdot 3 = 0.3$.

### Temporal Difference (TD) Learning

Il metodo **TD** aggiorna la stima del valore **ad ogni singolo step**, senza aspettare la fine dell'episodio. Si usa l'equazione di Bellman come approssimazione:

$$

v(S_t) \leftarrow v(S_t) + \alpha \big[R_{t+1} + \gamma \, v(S_{t+1}) - v(S_t)\big]

$$

Il termine $R_{t+1} + \gamma \, v(S_{t+1})$ è detto **TD target**, e la differenza $R_{t+1} + \gamma \, v(S_{t+1}) - v(S_t)$ è il **TD error**. Rispetto a Monte Carlo, TD impara più velocemente (aggiorna ad ogni step) ma ha un bias maggiore (usa una stima $v(S_{t+1})$ invece del ritorno reale). Esempio: con la stessa situazione del topolino, dopo un solo step con reward $R_1=1$ e $v(1)=0$, si ottiene $v(0) = 0 + 0.1 \cdot [1 + 1 \cdot 0 - 0] = 0.1$.

---

## 7. Q-Learning

### Concetto

Il **Q-Learning** è un metodo value-based che usa l'approccio **TD** per addestrare una **action-value function** (funzione Q). Internamente, la funzione Q è una **tabella (Q-table)** in cui ogni cella corrisponde al valore di una coppia stato-azione. Data una Q-table ottimale, l'agente sa esattamente quale azione è la migliore in ogni stato.

### Epsilon-Greedy Policy

Prima di descrivere l'algoritmo, serve una policy per bilanciare esplorazione e sfruttamento. La **epsilon-greedy** ($\varepsilon$-greedy) funziona così:

- Con probabilità $1 - \varepsilon$: **exploitation** (azione greedy), l'agente sceglie l'azione con il Q-value più alto

- Con probabilità $\varepsilon$: **exploration**, l'agente sceglie un'azione casuale

Durante il training si parte con un valore alto $\varepsilon = 1.0$ (massima esplorazione). Col passare del tempo $\varepsilon$ viene ridotto esponenzialmente, man mano che la Q-table diventa più precisa.

### Regola di aggiornamento

Ad ogni step, dopo aver osservato la transizione $(S_t, A_t, R_{t+1}, S_{t+1})$, la Q-table si aggiorna con:

$$

q(S_t, A_t) \leftarrow q(S_t, A_t) + \alpha \Big[R_{t+1} + \gamma \max_{a'} q(S_{t+1}, a') - q(S_t, A_t)\Big]

$$

Il termine $R_{t+1} + \gamma \max_{a'} q(S_{t+1}, a')$ è il **TD target**: la ricompensa ottenuta più il miglior Q-value raggiungibile dallo stato successivo (scontato di $\gamma$). La differenza tra TD target e stima corrente guida l'aggiornamento.

### Esempio: il topolino nel labirinto

Un topolino ha a disposizione un piccolo formaggio (+1), un veleno (-10) e una grande quantità di formaggio (+10). L'episodio termina se il topolino mangia il veleno, il grande formaggio, oppure supera 5 step. Parametri: $\alpha = 0.1$, $\gamma = 0.99$.

- **Step 1**: la Q-table è inizializzata a zero.

- **Step 2**: $\varepsilon = 1.0$, azione casuale "destra", il topolino mangia il piccolo formaggio ($R = +1$). $q(\text{Stato 1}, \text{destra}) = 0 + 0.1 \cdot [1 + 0.99 \cdot 0 - 0] = 0.1$.

- **Step 3**: $\varepsilon$ diminuisce ($\varepsilon = 0.99$), azione "giù", il topolino muore ($R = -10$). $q(\text{Stato 2}, \text{giù}) = 0 + 0.1 \cdot [-10 + 0.99 \cdot 0 - 0] = -1$.

- A training concluso, la Q-table è ottimale e l'agente esegue l'**inferenza** così: dato $s$, sceglie $a^* = \arg\max_a q(s, a)$.

---

## 8. Deep Q-Learning

### Motivazione

Il Q-Learning con Q-table funziona quando lo spazio degli stati è **discreto e piccolo**. In giochi complessi come Atari, il numero di stati possibili è astronomico: in Space Invaders, con immagini $160 \times 210 \times 3$ a 256 livelli di intensità, gli stati possibili sono $256^{160 \times 210 \times 3} = 256^{100800}$, un numero superiore al numero di atomi nell'universo ($\approx 10^{80}$). Costruire una Q-table per questi spazi è impossibile.

### Idea chiave

Il **Deep Q-Learning** sostituisce la Q-table con una **rete neurale profonda** parametrizzata che approssima la funzione Q:

$$

q(s, a; \theta) \approx \text{ritorno futuro atteso}

$$

La rete (DQN, Deep Q-Network) prende in input uno stato e restituisce i Q-value per tutte le azioni possibili.

### Architettura DQN

Per giochi come Space Invaders:

- **Input**: uno stack di **4 frame** consecutivi, che costituisce lo stato. Si usano 4 frame perché un singolo frame non fornisce informazioni sul moto (direzione, velocità).

- **Rete**: strati **convoluzionali** (per estrarre feature spaziali) seguiti da strati **fully connected**

- **Output**: un vettore di Q-value, uno per ogni azione possibile (sinistra, destra, spara, nulla)

I Q-value stimati dalla DQN vengono usati dalla policy epsilon-greedy per scegliere l'azione.

### Due reti: Online e Target

Il DQN utilizza **due reti** con la stessa architettura:

- **Online network** $q(s, a; \theta)$: la rete effettivamente addestrata (con SGD). Stima i Q-value allo stato corrente $s$.

- **Target network** $q(s', a'; \theta^-)$: una copia congelata della online network, aggiornata lentamente tramite **EMA (Exponential Moving Average)** (come in DINO). Stima i Q-value di tutte le azioni nello stato successivo $s'$.

Il **TD target** è calcolato con la target network:

$$

y = r + \gamma \max_{a'} q(s', a'; \theta^-)

$$

Se $s'$ è uno stato terminale, il target diventa semplicemente $y = r$.

### Loss e addestramento

La loss del DQN è un semplicissimo **MSE** tra il TD target e la predizione della online network:

$$

L(\theta) = \mathbb{E}\Big[\big(y - q(s, a; \theta)\big)^2\Big]

$$

La online network osserva i frame $s = [\text{frame}_{t-3}, \text{frame}_{t-2}, \text{frame}_{t-1}, \text{frame}_t]$, mentre la target network osserva lo stato successivo $s' = [\text{frame}_{t-2}, \text{frame}_{t-1}, \text{frame}_t, \text{frame}_{t+1}]$. La separazione tra le due reti stabilizza il training: se la stessa rete fosse usata sia per la predizione sia per il target, il target cambierebbe continuamente causando instabilità.

---

## 9. Policy Gradient e REINFORCE

### Metodi Policy-Based

Nei metodi **policy-based** si apprende direttamente la policy ottimale $\pi^*$ senza passare per una funzione di valore (a differenza del Q-learning). La policy è parametrizzata da una rete neurale $\pi_\theta$ che restituisce una distribuzione di probabilità sulle azioni:

$$

\pi_\theta(s) = P[A \mid s; \theta]

$$

L'obiettivo è massimizzare la performance (ritorno atteso) della policy parametrizzata usando tecniche di ottimizzazione. I metodi **Policy Gradient** sono un sottoinsieme dei metodi policy-based che cercano direttamente la policy ottimale ottimizzando $\theta$ con **gradient ascent**.

### Esempio: CartPole

Un classico ambiente di test è **CartPole**: l'agente deve bilanciare un palo verticale su un carrello mobile su un binario 1D, applicando forze verso sinistra o destra. Lo stato è un vettore di 4 valori continui: posizione del carrello, velocità del carrello, angolo del palo, velocità angolare del palo. Le azioni sono due: spingere a sinistra (0) oppure a destra (1). Il reward è +1 per ogni time step in cui il palo resta in piedi (angolo $< 15°$). L'episodio termina quando il palo cade troppo o il carrello esce dal campo.

La rete è un fully-connected network: prende in input i 4 valori dello stato, e tramite un softmax finale produce la distribuzione sulle azioni $\pi_\theta(a \mid s)$. L'obiettivo è controllare questa distribuzione in modo che le azioni buone vengano campionate più frequentemente.

### Raccolta delle traiettorie

L'addestramento inizia con la **raccolta di traiettorie**:

- Si parte dallo stato iniziale $s_0$

- Si campiona un'azione $a_t \sim \pi_\theta(\cdot \mid s_t)$ dalla policy corrente

- Si applica l'azione, ottenendo lo stato successivo $s_{t+1}$ e la ricompensa $r_t$

- Si memorizza la tupla $(s_t, a_t, r_t)$

- Si ripete fino alla fine dell'episodio

Si ottiene una traiettoria: $(s_0, a_0, r_0), (s_1, a_1, r_1), (s_2, a_2, r_2), \ldots$

Per ogni time step $t$ si calcola anche il ritorno:

$$

G_t = r_t + \gamma r_{t+1} + \gamma^2 r_{t+2} + \cdots

$$

### Algoritmo REINFORCE (Monte Carlo Policy Gradient)

L'algoritmo **REINFORCE** è un metodo policy-gradient di tipo Monte Carlo:

1. Usare la policy $\pi_\theta$ per raccogliere un episodio (traiettoria) $\tau$

2. Usare l'episodio per stimare il gradiente $g = \nabla_\theta J(\theta)$

3. Aggiornare i pesi della policy: $\theta \leftarrow \theta + \alpha g$

La **funzione obiettivo** per una data traiettoria è:

$$

J(\theta) = \sum_{t=0}^{T} \log \pi_\theta(a_t \mid s_t) \, G_t

$$

dove $\log \pi_\theta(a_t \mid s_t)$ è la log-probabilità dell'azione presa e $G_t$ è il ritorno a partire dal time step $t$. L'intuizione è:

- Se il ritorno $G_t$ è alto → **aumenta** la probabilità della coppia (stato, azione)

- Se il ritorno $G_t$ è basso → **diminuisci** la probabilità della coppia (stato, azione)

Anziché massimizzare $J(\theta)$, in pratica si minimizza la loss (aggiungendo un segno negativo):

$$

L = -\sum_{t=0}^{T} \log \pi_\theta(a_t \mid s_t) \, G_t

$$

L'algoritmo viene ripetuto su diverse traiettorie per ottenere una policy ben addestrata.

---

## 10. Metodi Actor-Critic

### Problema della varianza in REINFORCE

REINFORCE è affetto da **alta varianza**: le traiettorie raccolte possono variare enormemente a causa della stocasticità dell'ambiente (eventi casuali durante un episodio) e della stocasticità della policy (soprattutto all'inizio, quando la policy è quasi casuale). Di conseguenza, il ritorno calcolato a partire dallo stesso stato può essere molto diverso tra episodi diversi — è come mirare a un bersaglio mobile. Per ridurre la varianza e accelerare il training, si combinano i metodi policy-based e value-based: i **metodi Actor-Critic**.

### Architettura Actor-Critic

L'apprendimento viene suddiviso in due componenti che collaborano:

- **Actor (la policy)** $\pi_\theta(a \mid s)$: decide quale azione compiere. Viene aggiornato con metodi policy-gradient. L'obiettivo è massimizzare la ricompensa attesa.

- **Critic (lo stimatore del valore)**: apprende la funzione di valore $v(s)$ o la funzione Q $q(s,a)$. Usa il TD learning come nel Deep Q-Learning.

L'analogia è quella di un videogiocatore (Actor) affiancato da un amico (Critic) che gli dice "quella mossa è stata buona/cattiva", guidandolo senza aspettare la fine della partita.

### Meccanismo

L'actor seleziona un'azione $A_t = a$ nello stato $S_t = s$. L'ambiente restituisce la ricompensa $R_{t+1} = r$ e il nuovo stato $S_{t+1} = s'$. Il critic calcola il **TD error**:

$$

\delta_t = r + \gamma \max_{a'} q(s', a'; w^-) - q(s, a; w)

$$

dove $w$ e $w^-$ sono i parametri della online e target critic network. Questo errore TD:

- **Aggiorna il critic** (per migliorare le stime del valore)

- **Guida l'actor** (per aggiustare la policy)

L'actor aggiorna la policy con la funzione obiettivo:

$$

J(\theta) = \mathbb{E}\big[\log \pi_\theta(a_t \mid s_t) \, \delta_t\big]

$$

### Confronto con REINFORCE

In REINFORCE la funzione obiettivo usa il **ritorno completo** $G_t$ (Monte Carlo):

$$

J(\theta) = \mathbb{E}\big[\log \pi_\theta(a_t \mid s_t) \, G_t\big]

$$

Non c'è un critic, si usa solo il ritorno dopo la fine dell'episodio. Nell'Actor-Critic, l'actor usa il **TD error** $\delta_t$ del critic al posto del ritorno completo $G_t$. Questo porta a **varianza più bassa** (il TD error è meno rumoroso perché ha un solo step di randomness) ma **bias più alto** (perché si usa un'approssimazione one-step TD anziché il ritorno reale).

---

## 11. Advantage Actor-Critic (A2C)

### Funzione Advantage

L'**Advantage Actor-Critic (A2C)** stabilizza ulteriormente il training usando la **funzione Advantage** come segnale per il critic. Intuitivamente, l'Advantage misura "quanto è meglio compiere l'azione $a$ nello stato $s$ rispetto al valore medio di quello stato":

$$

A(s, a) = q(s, a) - v(s)

$$

dove $q(s, a)$ è il Q-value dell'azione $a$ nello stato $s$ e $v(s)$ è il valore medio dello stato. Se $A > 0$, l'azione è migliore della media; se $A < 0$, è peggiore.

### Funzione obiettivo A2C

La funzione obiettivo dell'actor diventa:

$$

J(\theta) = \mathbb{E}\big[\log \pi_\theta(a_t \mid s_t) \, A(s_t, a_t)\big]

$$

Usare l'Advantage come segnale di guida riduce ulteriormente la varianza rispetto al semplice TD error, perché centra il segnale rispetto al valore medio dello stato: le azioni vengono valutate in relazione a ciò che è "normale" per quel dato stato.

---

## 12. Proximal Policy Optimization (PPO)

### Motivazione

La **Proximal Policy Optimization (PPO)** è un framework che migliora la stabilità del training limitando la dimensione degli aggiornamenti della policy ad ogni epoca. Il problema è che aggiornamenti troppo grandi possono essere catastrofici: un passo troppo grande nello spazio dei parametri può far "cadere da una scogliera" la performance. PPO garantisce che le modifiche alla policy restino piccole e controllate.

### Clipped Surrogate Objective

PPO introduce una nuova funzione obiettivo chiamata **Clipped Surrogate Objective**:

$$

L^{CLIP}(\theta) = \mathbb{E}\Big[\min\big(r_t(\theta) A_t, \; \text{clip}(r_t(\theta), 1-\varepsilon, 1+\varepsilon) A_t\big)\Big]

$$

dove:

- $\theta$: parametri della policy corrente

- $\theta_{old}$: parametri della policy usata per raccogliere i dati (fissi durante l'aggiornamento)

- $A_t$: stima dell'Advantage

- $\varepsilon$: parametro di clipping (tipicamente 0.1–0.3)

### Probability Ratio

Il **rapporto di probabilità** tra policy corrente e vecchia è:

$$

r_t(\theta) = \frac{\pi_\theta(a_t \mid s_t)}{\pi_{\theta_{old}}(a_t \mid s_t)}

$$

Questo rapporto quantifica quanto la policy è cambiata:

- $r_t > 1$: l'azione $a_t$ nello stato $s_t$ è **più probabile** nella policy corrente rispetto alla vecchia

- $0 < r_t < 1$: l'azione è **meno probabile** nella policy corrente

Il rapporto è un modo semplice per stimare la divergenza tra vecchia e nuova policy.

### Operatore di Clipping

L'operatore $\text{clip}(r_t(\theta), 1-\varepsilon, 1+\varepsilon)$ restringe il rapporto nell'intervallo $[1-\varepsilon, 1+\varepsilon]$:

- Se $r_t < 1-\varepsilon$: $\text{clip}(r_t) = 1-\varepsilon$

- Se $r_t > 1+\varepsilon$: $\text{clip}(r_t) = 1+\varepsilon$

Il clipping limita quanto la policy può migliorare o peggiorare in un singolo aggiornamento, impedendo cambiamenti troppo aggressivi. Esempio: con $\varepsilon = 0.2$, il rapporto è ristretto a $[0.8, 1.2]$.

### Funzionamento dell'obiettivo

La funzione obiettivo prende il **minimo** tra la versione non clippata ($r_t A_t$) e quella clippata ($\text{clip}(r_t) \cdot A_t$). Se ci fosse solo la parte non clippata e l'azione fosse diventata molto più probabile nella policy corrente, il gradiente sarebbe enorme e causerebbe un aggiornamento eccessivo. Il clipping agisce da freno: anche se la policy vorrebbe cambiare di più, l'aggiornamento viene limitato, garantendo convergenza più stabile verso una soluzione ottimale.

---

## 13. Reinforcement Learning from Human Feedback (RLHF)

### Il problema dell'allineamento

Il **problema dell'allineamento** (alignment problem) chiede se il comportamento di un LLM corrisponde alle intenzioni, ai valori e alle preferenze umane, specialmente in situazioni non esplicitamente specificate durante il training. I LLM sono pre-addestrati con next token prediction (NTP) su enormi quantità di dati raccolti da internet. Sebbene siano bravi nell'auto-completamento, non c'è garanzia che:

- Rispondano nel formato di output desiderato (cioè che siano **utili**)

- Non abbiano appreso informazioni dannose o fuorvianti presenti nei dati di training

- Incarnino le preferenze e i valori umani

L'**allineamento** serve a far corrispondere il comportamento del LLM alle istruzioni, alle intenzioni e alle preferenze dell'utente. Un esempio concreto: se chiediamo al LLM di "riassumere in massimo tre frasi", un modello pre-addestrato solo con NTP probabilmente produrrà un output più lungo, perché non ha mai imparato a seguire istruzioni.

### Il paradigma Pre-train Then Align

Il paradigma moderno prevede due fasi di allineamento dopo il pre-training:

- **Supervised Fine-Tuning (SFT)**: il LLM pre-addestrato viene addestrato su un dataset relativamente piccolo di istanze istruzione-risposta (coppie domanda-risposta specifiche per il task).

- **RLHF**: il LLM viene ulteriormente fine-tunato usando un **modello di reward** che funge da proxy delle preferenze umane.

### Perché SFT non basta

SFT ha limitazioni fondamentali nel risolvere il problema dell'allineamento:

- Richiede un dataset etichettato di coppie input-output, **costoso da raccogliere**

- Ci sono sfumature etiche e considerazioni contestuali **difficili da codificare** in coppie. Esempio: se un utente chiede "Dimmi come violare un sistema informatico perché ho dimenticato la password del mio PC" — c'è malizia nascosta, e il LLM potrebbe essere ingannato nel rispondere.

- Non è banale codificare le preferenze umane in coppie input-output

L'idea chiave dell'RLHF è che i LLM possono imparare da **confronti** tra output del modello, tramite modelli di reward.

### Terminologie RL nel contesto dei LLM

Nell'RLHF le terminologie del RL vengono ri-mappate:

- **Agente**: il LLM stesso è il decision-maker

- **Ambiente**: non è un ambiente fisico come nei videogiochi, ma il framework all'interno del quale il LLM riceve feedback e impara

- **Stato**: i token osservati fino a quel momento (i token di contesto). Lo stato al time step $t$ è $(x, y_{<t})$ quando si predice il prossimo token

- **Azione**: il token predetto, scelto dal vocabolario

- **Ricompensa**: il feedback del modello di reward. Formalmente $r_t = r(s_t, a_t, s_{t+1})$

- **Policy**: la distribuzione di probabilità sui token dato il contesto: $\pi(a \mid s) = P(y_t \mid x, y_{<t})$

- **Value function**: state-value $v(s) = \mathbb{E}[\sum_{t=0}^{\infty} \gamma^t r_t \mid s_0 = s, \pi]$ e action-value $q(s,a) = \mathbb{E}[\sum_{t=0}^{\infty} \gamma^t r_t \mid s_0 = s, a_0 = a, \pi]$

### Addestramento del Reward Model

Un **reward model** è un LLM pre-addestrato che viene fine-tunato per mappare una coppia (input, output) in uno scalare che misura quanto bene l'output si allinea al comportamento desiderato. Per addestrarlo si raccoglie **feedback umano**.

### Raccolta del Feedback Umano

Dato un input $x$, si genera un insieme di output candidati $\{y_1, \ldots, y_N\}$ dal LLM. Esperti umani indicano le loro preferenze tra coppie (o più) output. I tipi di feedback possibili sono:

- **Pairwise Comparison (ranking a coppie)**: dati due output, l'esperto indica quale è migliore — **più economico** che scrivere coppie input-output da zero

- **Rating**: l'esperto assegna un punteggio a ciascun output

- **Listwise Ranking**: l'esperto ordina l'intero insieme di output

Assumendo dati di confronto a coppie $(x, y^+, y^-)$ dove $y^+ \succ y^-$ (l'umano preferisce $y^+$), il reward model viene addestrato con una loss simile alla binary cross-entropy:

$$

L_{RM}(\phi) = -\mathbb{E}_{(x, y^+, y^-) \sim \mathcal{D}}\big[\log \sigma\big(r_\phi(x, y^+) - r_\phi(x, y^-)\big)\big]

$$

Questa loss spinge il reward model ad assegnare un punteggio più alto all'output preferito $y^+$ rispetto a quello non preferito $y^-$.

---

## 14. RLHF in Pratica

### Training della Policy

Ricordando il metodo Actor-Critic, si addestra la policy con:

$$

J(\theta) = \mathbb{E}\big[\log \pi_\theta(a_t \mid s_t) \, A(s_t, a_t)\big]

$$

Nel contesto del fine-tuning dei LLM, la funzione obiettivo diventa:

$$

J(\theta) = \mathbb{E}\big[\log \pi_\theta(y_t \mid x, y_{<t}) \, A(x, y_{<t}, y_t)\big]

$$

dove $\theta$ sono i parametri del LLM.

### Pipeline RLHF

Il flusso operativo è il seguente:

1. Dato un prompt $x$ dal dataset, la **policy** (il LLM fine-tunato) genera una risposta $y$

2. Si concatena $x$ con $y$ e si passa al **reward model**, che restituisce un reward (scalare) che indica la qualità della risposta

3. Si confronta la probabilità per-token della policy corrente con quella di un **language model iniziale congelato** e si penalizza il modello fine-tunato se devia troppo dal modello originale. Questo viene fatto con la **divergenza KL** — impedisce al modello di dimenticare le competenze generali apprese durante il pre-training

4. L'aggiornamento dei parametri avviene con un algoritmo policy-gradient RL, tipicamente **PPO** o **A2C**, che massimizza la metrica di reward sul batch corrente

### Reward Sparso

A differenza degli ambienti videoludici dove i reward sono frequenti, nell'RLHF il reward è **sparso**: l'ambiente fornisce un singolo scalare alla fine dell'intera risposta generata. Questo comporta sfide significative:

- Il modello non sa quali **singoli token** erano buoni o cattivi: riceve solo un giudizio complessivo, limitando il controllo fine-grained

- Rischio di **reward hacking**: il modello può imparare a ripetere disclaimer di sicurezza per gonfiare artificialmente il reward

- I reward sparsi possono causare **gradienti ad alta varianza**

Esistono tecniche di mitigazione come **DPO (Direct Preference Optimization)**, che evita del tutto il reward model esplicito, ma non è trattata nel corso.

---

## 15. AI ed Etica

### Panoramica

L'etica nell'IA esplora opportunità, rischi e responsabilità legate all'uso dei LLM. Le domande chiave riguardano: come i LLM impattano verità, bias e disinformazione? Quali sono le preoccupazioni etiche su privacy, sicurezza e consenso? Chi è responsabile dei contenuti generati dall'IA? Come i LLM influenzano creatività, lavoro e processi decisionali umani?

Le problematiche etiche si dividono in due categorie: **problemi di lunga data** (privacy, copyright, fairness) e **problemi emergenti** (truthfulness, norme sociali/tossicità).

### Privacy

- **Memorizzazione**: la memorizzazione aumenta con modelli più grandi. Si riduce con dataset di training più ampi e riducendo le duplicazioni nei dati. I modelli più grandi memorizzano sia pattern generali che dati specifici, incluse informazioni sensibili.

- **Membership Inference Attack (MIA)**: attacco in cui si cerca di determinare se un dato specifico faceva parte del training set. I LLM sono particolarmente vulnerabili a questo tipo di attacco.

- **Estrazione di dati di training**: dati sensibili possono essere estratti da modelli pre-addestrati con prompt appositamente costruiti. Ricerche hanno dimostrato che è possibile estrarre informazioni private (numeri di telefono, indirizzi, ecc.) da GPT-2 e modelli simili.

- **Inferenza di attributi personali**: i LLM possono essere usati per inferire attributi personali degli utenti a partire da testi generici.

### Copyright

- **Campioni avvelenati (Poisoned Samples)**: l'attaccante costruisce campioni avvelenati aggiungendo un **trigger definito dall'attaccante** e modificando le etichette associate a una specifica classe target. La presenza del trigger induce il modello a predire l'etichetta target corrispondente, significando l'ownership del modello.

- **Watermark (filigrane invisibili)**: si nascondono **firme invisibili** (impercettibili all'uomo) nel testo generato, che possono essere estratte per futura verifica dell'origine e della proprietà del modello.

### Fairness e Bias

I LLM ereditano e potenzialmente **amplificano** i bias sociali presenti nei dati di training, perpetuando danni verso comunità marginalizzate. Esempio: maggiore presenza di dati in inglese rispetto a lingue meno parlate.

- **Group Fairness**: si focalizza sulle disparità tra gruppi sociali

- **Individual Fairness**: individui simili in un task dovrebbero essere trattati in modo simile

- **Strategie di mitigazione**:

- **Pre-processing**: estendere la rappresentazione di gruppi sociali sotto-rappresentati nei dati

- **In-training**: integrare moduli di debiasing (adapter di debiasing) durante l'addestramento

- **Post-processing**: identificare token biased nell'output e sostituirli con alternative non biased

### Truthfulness (Veridicità)

- **Allucinazione (Hallucination)**: i LLM possono generare informazioni plausibili ma false, presentandole con sicurezza. Questo è un problema fondamentale perché mina la fiducia nei contenuti generati.

- **Sycophancy (adulazione)**: tendenza a lusingare gli utenti confermando le loro convinzioni e misconcezioni, invece di correggerli. Problematicamente, la sycophancy **aumenta** con la dimensione del modello (modelli più grandi sono più adulatori) e può essere **peggiorata dall'RLHF** (che ottimizza per risposte gradite all'utente).

### Norme Sociali e Tossicità

I LLM possono generare contenuti **tossici**, offensivi o che violano le norme sociali. Questo è un problema particolarmente rilevante quando i modelli sono usati in contesti pubblici o interattivi, dove output inappropriati possono causare danni reali a individui e comunità.

### Tecniche di Mitigazione

Lo sviluppo responsabile dell'IA richiede interventi a più livelli: mitigazione dei bias nei dati e nei modelli, meccanismi di tutela della privacy, sistemi di verifica dei fatti (per ridurre le allucinazioni), filtri per la tossicità, e framework di governance e accountability che definiscano chi è responsabile dei contenuti generati.