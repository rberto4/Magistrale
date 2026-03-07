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