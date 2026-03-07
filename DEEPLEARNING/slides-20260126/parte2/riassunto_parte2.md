## Indice
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
