### Recap 11
La computer vision permette alle macchine di capire, vedere e agire sui dati "visivi" usando le reti neurali
Input immagine -> output predizione
#### CNN - reti neurali a convoluzione 
Sono composte da :
- **Feature extractor** : Convolution + ReLU e polling blocks
- **Flatter layter** : che serve a portare i risultati in un vettore ad una dimensione (anziché tensori)
- **Fully connected Neural network** : Rete che che si occupa della classificazione, con N neuroni quanti sono le N possibilità del classifier
- **Output probability Distribuition** : si usa `Softmax` come funzione di attivazione per ottenere valori di propabilita' della classificazione.
Esempi -> Alexnet, VGG, Resnet
#### Self Attention
Meccanismo che permette ad ogni token in input, di avere contesto su **qualsiasi** altro token facente parte dell'input.
Viene usato sia in encoder che in decoder.
I pesi vengono calcolati attraverso la formula :
$$
A = \mathrm{softmax}\left(\frac{QK^{T}}{\sqrt{d_k}}\right)
$$
Data la frase "The animal didn’t cross the street because it was too tired", se il modello sta processando la parola "it" la self-attention permette al modello di avere associazione con "animal" parametrizzandone l'importanza.
Alcune varianti :
##### Cross attention
viene usato nel decoder per funzioni di traduzione automatica, permette in fase di generazione (quindi decoder), di prestare attenzione alla parola di input. 
##### Masked Self attention
e' una modalita' di self attention in cui si vincola la sua visione solo fino alle parole appena generate senza poter guardare quelle future. Questo per non creare bias e distorsioni.
la funzione softmax restituisce 0 quando valori come -infinito come peso


### Transormer Encoder Block
I blocchi che compongono il transformer sono ripetuti N volte e sono composti da :
- Attention (self)
- Layer normalization : serve a normalizzare i vettori che potrebbero avere valori numericamente molto distanti tra di loro, vengono portati a
	- Media = 0
	- Varianza = 1
- MLP
- Layer normalizazion
- Residual connection : serve a far scorrere l'informazione trasformata con quella originale, e'utile in fase di addestramento per fare in modo che il layer apprenda una variazione su quanto elaborato. Migliora le situazioni di vanishing gradients 
- Positional encoding : Il transformer può sapere la posizione e l'ordine delle parole, ee'un vettore numerico che rappresenta la posizione del token nella sequenza

## Cap 12 - Vision transformer

L'architettura del trasformer può essere usata anche per classificare le immagini.
immagini RGB -> vettore a tre dimensioni per R,G e B diventerebbe gigantesco
Tutto in unico vettore monodimensionale, perderei la sequenza 
Soluzioni -> 
#### Patch embendding 
Per trattare un'immagine come una sequenza di parole visive, scomponiamo l'immagine in patch (16x16x3) = 768 valori -> in un unico vettore monodimensionale, lo facciamo per ogni patch
Tutti i vettori monodimensionali vanno a comporre una matrice :
$$
z=Wx+b
$$
Si aggiunge anche un token speciale **CLS** che serve per la classificazione finale, in testa.
Si aggiunge anche il **Positional embedding** che serve per mantenere l'informazione spaziale 

La sequenza adesso passa per il transformer  (con global attention)

#### Segmentation
La classificazione e' capace di predire una sola label per immagine 3xHx3
ma le immagini sono complesse e composte da più soggetti e/o dettagli
la segmentation prevede la label per ogni pixel, restituendo una segmentation map grande come l'immagine.

Approcci per fare segmentazione :
- **Sliding window approch** : si guarda la "Finestra o patch" attorno al pixel, per decidere cosa e', anche se potrebbe non essere sufficiente perché manca di contesto globale sul resto dell'immagine.
	- Contro -> Non si mantiene una cache delle caratteristiche calcolate tra aree vicine, e' lento come approccio.
- **Fully convolutional network (FCN)** : Non si prende l'immagine e la si divide in patch, la CNN fornisce una mappa (KxHxW, dove K e'il numero delle classi) di score, per ciascuna classe e per ogni posizione dell'immagine, si applica la funzione di attivazione `Softmax` per ciascun pixel, che ci restituisce la probabilità di appartenenza a ciascuna delle categorie della mappa.
	- Contro -> il polling fa ridurre le dimensioni (la risoluzione) rispetto all'originale.
	-  Soluzione -> Si applicano tecniche di Downsampling e Upsampling
#### Upsampling 
Cercare di portare delle feature da bassa risoluzione, ad alta.
3 tecniche principali :
- Nearest neighbour : ogni valore viene copiato nei suoi vicini da 2x2 a 4x4 con lo stesso valore
- Bed of Nails : propone il valore della cella in alto a sinistra di ogni Patch
- Max unpolling : si usa il valore massimo prima del polling, il max-polling ricorda la sua posizione.
- Transposed Convolution : convoluzione al contrario.
![[Pasted image 20260212232355.png]]
![[Pasted image 20260212232601.png]]

Example -> **U-net**
Inizialmente disegnata per immagini mediche.
Un problema delle reti FCN Fully convolutional network, e' che con le operazioni di polling si perdono informazioni spaziali.
U-Net copia le informazioni dalla parte di downsampling della rete, a quella di upsampling.

# Cap 13

#### Object detection 
l'obiettivo e' quello di localizzare soggetti all' interno dell'immagine e saper dire **dove** sono posizionati.
un algoritmo riceve l'immagine in input e ne restituisce 
-  la classificazione (le N classi)
- le ccordinate (x,y,h) delle bounding box (rettangoli con il soggetto istritto in essi)
Approccio semplice -> uso di una CNN standard, come AlexNet, con 
- **Classification head** : parte della rete che si occupa di capire a quale classe appartiene un oggetto, a partire dalle features estratte ai livelli precedenti
	- Alla fine usa, come sempre, una softmax e restituisce un vettore di probabilita' con soma = 1 di tutti gli elementi
- **Bounding box head** : partendo dalle stesse features, stima le coordinate della box e l'output e' un vettore con x,y,w,h con w = larghezza. Lo fa attraverso una regressione con MSE e non classificazione perche' non assegna etichette ma cerca qui 4 valori numerici
  Si usa la cross entropy per la classificazione e la mean squared error per la regressione.
  
Un problema di questo approccio e' quando arriviamo ad avere parecchi oggetti da distinguere -> vogliono dire tante bounding box, con ognuna un vettore di 4 numeri da gestire.

Prima soluzione debole -> **Sliding window approch**
Anziche' tante box, si sposta lungo l'immagine con diverse scale e posizioni una finestra o patch, ma richiede la riapplicazione della CNN molte volte.
Approcci migliorativi :

#### R-CNN
invece di provare tutte le posizioni, si cercano i potenziali oggetti con il **selective search algorithm** che ci restituisce le porzioni di immagine in cui e' probabile che ci siano oggetti.
Viene normalizzata la dimensione di ognuna delle proposte a 224x224 tipicamente
Si applica la classificazione con la CNN 
#### miglioria : Fast R-CNN
Il meccanismo di base rimane quello del R-CNN ma, invece di applicare l'algoritmo alle porzioni d immagini complete, si applica al quinto layer convoluzionale.
Non si arriva ai livelli piu' profondi, inutili al fine del riconoscimento.

#### Faster R-CNN
La selective search e' lenta -> si passa alla **Region Proposal Network**
una rete che impara a proporre le regioni di interesse 
- La RPN lavora sulle features estratte dalla CNN propone i potenizli riquadri dove potrebbero esserci oggetti
- Per ogni regione predetta, il sistema predice posizione della bounding box e anche classificazione

#### Single stage object detector YOLO
Riescono a prevedere in uninco passaggio, sia la posizione che la categoria di appartenenza
sono molto veloci
- Immagine divisa in una griglia
- per ogni griglia viene predetta una bounding box, con le 4 coordinate + 1 objectness score, ovvero la probabilitá che la box contenga un oggett
- La probabilita' di ogni classe per ogni box
per griglie 7x7 -> 7x7xB x (5 + C) con :
- B = Numero di box per cella
- C = numero di classi 
- 5 = sono le dimensioni delle coordinate dei box (x, y, h, w, objectiness)
YOLOv3 funziona allo stesso modo ma puó fare uso di griglie di dimensioni differenti anziché fisse.


