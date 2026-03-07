Nell'apprendimento supervisionato, forniamo agli algoritmi, sia input X che output Y, dopodiché si valuta e minimizza la differenza tra Y reale e Y^ stimato
Apprendimento non supervisionato -> Non viene fornito Y 
Quindi -> **imparare dai dati senza etichette o target assegnati dall'uomo**

#### Autoencoder
Una rete neurale che impara a copiare nil proprio input in output 
formata da :
- Encoder : trasforma l'input in una rappresentazione interna "code vector"
- Decoder : Ricostruisce l'input originale o simile, a partire da quanto fatto nell'encoder
l'idea chiave e' la **ricostruzione dell'input**
Si usa mean square error per valutare il x^ (al posto di yˆ) sarebbe input stimato
$$
\mathrm{L_MSE}(x, \hat{x}) = \| x - \hat{x} \|_2^2 
= \sum_{i} (x_i - \hat{x}_i)^2
$$
Dopo che il AE e'stato addestrato, non si usa più la parte di decoder.
Il Encoder crea una versione compatta bottleneck dell'input che contiene già le informazioni essenziali per rappresentare l'input, ma non esattamente uguale, ad una dimensione minore.

#### Denoising Autoencoder
Un AE che viene addestrato per "ripulire" delle versioni contaminate degli input.
Aiuta a prevenire overfitting in quanto la rete non si abitua a prevedere dettagli inutili nel train
Aumenta la robustezza in quanto non si memorizzano input, ma si estraggono solo gli elementi utili alle features.

#### Convolutional Autoencoders
Per lavorare con immagini, non si usano AE fully connected perché ignorano la struttura spaziale delle immagini, se esse sono troppo grandi le connessioni diventano troppe
Devono imparare da zero strutture locali come pattern o texture
Idea :
- encoder : catena di convoluzioni, attivazioni ReLU e **polling** per compressione dell'output
- bottleneck : rappresentazione compatta , come mappa
- decoder : usa le tecniche di upsampling delle features **transposed convolution** per risalire alla dimensione originale.
Nella transposed convolution -> nel decoder si aumenta la risoluzione, fa il contrario rispetto alla convoluzione tradizionale. l'upsampling avviene attraverso zero padding...ovvero degli zeri come padding attorno ai pixel

Dopo la fase di pre-train non supervisionat dell'autoencoder, posso riutilizzare l'encoder per altre applicazioni
posso fare transfer learning -> aggiungendo layers di classificazione fully connected e fare fine-tuning su pochi dati mancanti.

Limiti :
- Gli autoencoder sono buoni per compressione e feature learning ma, in generale non lo sono per generazione di nuove esempi realistici.
#### Variational Autoencoder 
AE classico comprime input in un code vector chiamato bottleneck e lo ricostruisce
VAE produce i parametri di una distribuzione probabilistica con media μ e varianza σ²
lo spazio latente viene regolarizzato, si produce una distribuzione di probabilità gaussiana 
KL divergence
$$
 N(μ,σˆ2).
$$
Quindi e' in grado anche di generare dati realistic.
Quindi 
- Encoder : produce 2 output, media e deviazione standard per ogni input. 
- Decoder : funziona come prima
Come funziona il training ? con due funzioni di costo combinate 
- MSE Loss : come in un autoencoder normale
- KL Divergence : misura quanto una distribuzione probabilistica è diversa da un’altra.
Penalizza la distanza tra la distribuzione latente prodotta dall'encoder e una gaussiana standard.
Come si possono eseguire transizioni tra spazi latenti diversi ?
- interpolazione tra gli spazi latenti : 
$$
z_1 = Enc(x_1), z_2 = Enc(x_2)
$$
$$
z =  αz_1 + (1−α)z_2,α∈[0,1]
$$
#### Representation Learning
Consiste nel far scoprire automaticamente buone "feature" o rappresentazioni utili dei dati grezzi
Le reti CNN possono imparare ottime rappresentazioni se dispongono di tante etichette -> servono sistemi per imparare buone rappresentazioni limitando il numero di etichette.
#### Self-Supervised learning
e' una forma di apprendimento che non usa etichette umane, ma costruisce **task ausiliari**(pretext task) direttamente dai dati grezzi
Per imparare buone rappresentazioni, si risolvono problemi intermedi che si possono etichettare automaticamente.

#### Pretext Task 
Sono compiti "auto-etichettati" che non richiedono l'uso di etichette assegnate. sono problemi ausiliari
Esempi :
- Completamento di immagini
- Predizione di immagini
- Rotazioni o traslazioni
- Ordine dei patch
- Colorazioni
#### Downstream task 
Sono compiti "veri" per cui le etichette servono.

La distinzione tra i 2, riduce il numero di etichette richieste
Come funziona ? 
1. Prima fase : si addestra la rete su tanti dati non etichettati, su un pretext task (tipo indovinare la rotazione, fai imparare al modello a dire "quanto e'ruotata l'mmagine -> costringe la rete a imparare feature visive utili e generalizzabili ).
2. Seconda fase : si usa l'encoder come estrattore di features per una vera task, aggiungendo un classificatore MLP leggero 
3. terza fase : Valutazione

le feature self-supervised sono spesso migliori sulle applicazioni downstream (classificazione detection etc. in cui servono etichette) rispetto al supervisionato con pochi dati.

Esempi di applicazioni : Video coloring
L'obiettivo e'trovare pixel corrispondenti tra fotogrammi vicini di un video, se i pixel appartengono allo stesso oggetto, devono avere lo stesso valore di colore.
Serve per imparare a tracciare oggetti tra diversi frame, utile nella computer vision

Ogni pixel del target frame viene confrontato con il pixel del reference frame, usando una CNN che estrae embedding numerico di ciascun pixel
Si calcola una attention map, il colore predetto e' una media pesata dei colore dei pixel del frame di riferimento, secondo la mappa di attenzione
- La **loss di training** valuta quanto il colore predetto si avvicina al colore reale del frame target.

#### Contrastive Representation Learning
Quello che vogliamo e' che il risultato di una Score function applicata tra confronti tra reference ed esempio positivo, sia maggiore delle Score restituito dalla funzione nel confronto tra reference e ed esempio negativo

I pesi del output layer sono sostituiti dalle features di campioni dei mini batch.

# Cap 15 --- Vision Language Model
Oggi si presenta un nuovo modo di imparare rappresentazioni (features) che usa dati multi-modali -> ovvero testo e immagini associati insieme (caption)
Sono dati facili da ottenere 
Quindi, queste coppie immagine-testo  per addestrare modelli **Vision language**
Fino ora -> tutti gli input, hanno solo un tipo di input ammesso alla volta 
- CNN : solo immagini
- Transformer : solo testo o testo/immagini
Vantaggi di approccio nuovo : l **VLM** puó collegare concetti nel testo e caratteristichje vicive, utile per capire descrivere e classificare e generare.
2 tipi :
- Contrastive VLM :  e' una funzione di perdita che serve ad avvicinare tra loro rappresentazioni simili e ad allontanare rappresentazioni diverse nello spazio latente. (sono classificatori)
- Generative VLM : insegna al modello a predire il prossimo token, dato il contesto. (sono generatori/descrittori)
#### Contrastive vision language model - CLIP
Prima -> Si impara che Immagini simili vanno avvicinate nello spazio delle rappresentaizioni
dopo, con CLIP -> Si fa la stessa cosa ma tra immagini e testo associato caption
Il vantaggio e' che le caption associate alle immagini , "spiegano", collegano concetti astratti
Consente il cross-modal retrieval.
#### Training data
Si captano immagini dal web insieme alla loro caption
per CLIP, abbiamo coppie 
$$
(x,c)\space x=immagine, c=caption
$$
Per immagini dal web, si chiama Webly-supervised learnig

#### Architettura CLIP
Si hanno 2 encoder, uno per il testo con cui si codificano le caption e uno per le immagini 
Si costruisce una matrice N x N, del mini batch.
l'encoder usato per le immagini e' il **ViT** vision transformer che funziona cosi'
- Immagine suddivisa in patch 224x224 ad esempio
- ogni patch e' linearizzato in vettore
- si aggiunge il token CLS in testa e positional enbeddings \il token cls viene poi usato come rappresentazione globale della foto.
Encoder per il testo, aggiunge un token nella parte finale chiamato EOS end of sentence usato per la rappresentazione finale.
l'obbiettivo e'quello di allineare EOS con CLS usando cross entropy loss (ovvero una funzione di costo usata in classificazione per stabilire quanto ció che viene stimato, e' distante dalla realtà )
#### Training
Per ogni batch , Le rappresentazioni sono 2 vettori:
- $v_i$ : per l'immagine, ovvero l'output del Image encoder
- $t_j$ : per il testo, ovvero l'output del text encoder.
Le funzioni di costo sono le seguenti :
![[Pasted image 20260213212921.png]]
sono entrambe softmax/cross entropy che lavorano sulla matrice delle similarita' tra tutte le immagini e tutti i testi nel patch

#### inferenza
Si crea un classifier testuale, in cui pero ogni classe possibile si crea una frase modello "A photo of [class name]"
Si codificano le frasi con il text encoder e si ottiene embedding (una rappresentazione numerica vettoriale )
Si crea l'enbendding per l'immagine da classificare tramite il proprio encoder ViT
si confrontano i 2 vettori calcolando le simililarità.
La differenza rispetto ad un classificatore standard, e'che possiamo usare CLIP come Zero-shot, ovvero per classi mai viste durante il training.
in CLIP i pesi delle classi sono gli embenddings della parte text, ovvero di caption
![[Pasted image 20260213214407.png]]
Sul pratico, si puó quindi usare CLIP per cercare immagini attraverso query o prompt testuali.

### Generative Vision languages models
CLIP viene usato per rispondere a domande inerenti all'immagine come "La descrizione combacia?" perche' si limita a accoppiare etichette con immagine
NON la puó descrivere.
-> servono modelli che **generano** testo a partire dalle immagini.

#### Recap Encoder-Decoder Transformer
Nell'architettura a transormer abbiamo :
- input.
- Encoder : prende líntera sequenza di input in parallelo, ci fa self-attention (ognmi token della sequenza puo'guardare tutti gli altri, si cattura il contesto globale) ![[Pasted image 20260213215304.png]] Esempio, la parola Studente, puo' prestare attenzione alle parole "io, sono, uno"
- Decoder : Ogni token viene generato autoregressivamente un token alla volta, 2 attention :
	- Causal Self-Attention : attenzione consentita solo sui token in posizione precendente a quella corrente. (insomma guardo il passato)
	- Cross-attention : simile a Self attention ma Q viene dall' encoder, cioe' il decoder decide quali parti dell'input prestare attenzione. mentre si genera. (insomma guardo l'input)
- Output : frasi tradotte

Recap su masked self-attention : si possono guardare solo le posizioni :
- corrente
- precedenti a quella corrente. MAI il futuro.

#### BLIP – Bootstrapping Language-Image Pre-training
E' un modello Vision Language, ma viene addestrato a **generare** caption, non solo a fare matching.
#### Architettura
comprende feed-forward, self e cross attention sia nella fase di encoding sia in quella di decoding :
- Fase **image to text contrastive (= allineamento )** : fase simile a CLIP perche' bisogna allineare embedding immagine e testo corretti. La loss e' come CLIP, si massimizza la similarità' tra rappresentazioni embedding di immagini e le loro caption
- Fase **Image text matching** : capire se immagine e testo corrispondono, si usa cross attention. La sua loss e' una binary classification , perché atomica.
- Fase **LM Language modeling per generare la caption** : dopo aver ricevuto la embedding dall'immagine, genera token per token la caption
comprende vision encoder, text encoder e text decoder.

#### Recap - decoder only transformer
Usata nei moderni LLM autoregressivi come ChatGPT
Ogni layer contiene :
- Casual self-attention : permette generazione autoregressiva 
	- Esempio, se input : " The cat is", il modello predice "sleeping", poi ricalcola tutto includendo il token "The cat is sleeping"e predice il successivo.
	non c'é distinzione architetturale tra input e output
- feed-forward network MLP, 
- residual + layer normalizzation. 
#### BLIP2
Evolutiva di BLIP la cui idea e' : non riaddestriamo tutto da zero ma usiamo modelli pre-addestrati congelati.
Il problema di BLIP1 e' che la parte di image decoder ViT e Text decoder LM hanno spazi differenti 
Nuovo componente : **Q-Former** ovvero query transformer
collega i 2 encoder di BLIP, funge da ponte
Deriva da un transformer (Derivato di BERT), composto da query apprese e cross-attention 


 