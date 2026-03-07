## Indice cap 4-5-6-7

1. [Vettorizzazione nella Regressione Lineare](#1-vettorizzazione-nella-regressione-lineare)
2. [Gradient Descent: Batch, Stocastico e Mini-batch](#2-gradient-descent-batch-stocastico-e-mini-batch)
3. [Problema di Classificazione](#3-problema-di-classificazione)
4. [Il Problema della Generalizzazione](#4-il-problema-della-generalizzazione)
5. [Regressione Polinomiale e Overfitting/Underfitting](#5-regressione-polinomiale-e-overfittingunderfitting)
6. [Trade-off Bias-Varianza](#6-trade-off-bias-varianza)
7. [Regolarizzazione](#7-regolarizzazione)
8. [Tecniche di Validazione](#8-tecniche-di-validazione)
9. [Neuroni Biologici e Artificiali](#9-neuroni-biologici-e-artificiali)
10. [Percettrone e Classificazione Lineare](#10-percettrone-e-classificazione-lineare)
11. [Multilayer Perceptron (MLP)](#11-multilayer-perceptron-mlp)
12. [Backpropagation](#12-backpropagation)
13. [Training di una Rete Neurale](#13-training-di-una-rete-neurale)
14. [Vettorizzazione nel MLP e Classificazione Multiclasse](#14-vettorizzazione-nel-mlp-e-classificazione-multiclasse)
15. [Deep Neural Networks (DNN)](#15-deep-neural-networks-dnn)
16. [Fuzioni di Attivazione](#16-funzioni-di-attivazione)
17. [Inizializzazione dei Pesi](#17-inizializzazione-dei-pesi)
18. [Algoritmi di Ottimizzazione Avanzati](#18-algoritmi-di-ottimizzazione-avanzati)
19. [Funzioni di Loss](#19-funzioni-di-loss)
20. [Tecniche di Regolarizzazione per Reti Neurali](#20-tecniche-di-regolarizzazione-per-reti-neurali)
21. [Tecniche di Normalizzazione](#21-tecniche-di-normalizzazione)

---

## 1. Vettorizzazione nella Regressione Lineare

### Dal singolo dato all'intero dataset

Per un singolo input $x$ con una variabile, il modello di regressione lineare è:

$$

\hat{y} = f_\Theta(x) = \theta_0 + \theta_1 x

$$

Estendendo a un input con $d$ variabili $x = [x_1, x_2, \ldots, x_d] \in \mathbb{R}^d$:

$$

\hat{y} = f_\Theta(x) = \theta_0 + \theta_1 x_1 + \theta_2 x_2 + \cdots + \theta_d x_d

$$

Il problema è calcolare $\hat{y}$ in modo **efficiente**, evitando cicli for (sequenziali) e usando invece prodotti scalari, prodotti matrice-vettore e matrice-matrice.

### Prodotto scalare per un singolo dato

Per far funzionare il dot product tra $x \in \mathbb{R}^d$ e $\Theta \in \mathbb{R}^{d+1}$, si aggiunge un elemento fittizio al vettore input: $x = [1, x_1, \ldots, x_d] \in \mathbb{R}^{d+1}$. Ora:

$$

\hat{y} = x^\top \Theta = \theta_0 + \theta_1 x_1 + \cdots + \theta_d x_d

$$

### Prodotto matrice-vettore per N dati

Per calcolare $\hat{y}$ per tutti gli $N$ dati in una sola operazione, si impilano i vettori input in una matrice:

$$

X = \begin{bmatrix} 1 & x_1^{(1)} & \cdots & x_d^{(1)} \\ 1 & x_1^{(2)} & \cdots & x_d^{(2)} \\ \vdots & \vdots & \ddots & \vdots \\ 1 & x_1^{(N)} & \cdots & x_d^{(N)} \end{bmatrix}

$$

e si calcola $\hat{Y} = X\Theta$ con un singolo prodotto matrice-vettore. Nel deep learning, il compito è trovare operazioni che possano essere "de-loopificate" e sostituite con prodotti matriciali efficienti.

---

## 2. Gradient Descent: Batch, Stocastico e Mini-batch

### Batch Gradient Descent

La regola di aggiornamento dei pesi media i gradienti su **tutti** i dati di training:

$$

\Theta \leftarrow \Theta - \alpha \frac{1}{N} \sum_{i=1}^{N} \nabla_\Theta L^{(i)}

$$

È numericamente stabile, ma se $N$ è molto grande (milioni di campioni) riempie rapidamente la memoria CPU/GPU. Non viene mai usato nell'addestramento reale di reti neurali profonde.

### Stochastic Gradient Descent (SGD)

L'SGD aggiorna i pesi usando **un singolo campione** scelto a caso:

- Si sceglie un campione $i$ a caso: $(x^{(i)}, y^{(i)})$

- Si calcola la loss per quel campione: $L(\hat{y}^{(i)}, y^{(i)})$

- Si calcola il gradiente: $\nabla_\Theta L^{(i)}$

- Si aggiornano i pesi: $\Theta \leftarrow \Theta - \alpha \nabla_\Theta L^{(i)}$

Vantaggio: leggerissimo in termini di memoria (un singolo dot product). Svantaggio: il gradiente calcolato su un solo campione è molto **rumoroso**, e si perde il vantaggio della vettorizzazione.

### Mini-batch Gradient Descent

Invece di un singolo campione, si usa un gruppo di $M$ campioni (es. $M = 64, 128, 512$):

$$

\Theta \leftarrow \Theta - \alpha \frac{1}{M} \sum_{i=1}^{M} \nabla_\Theta L^{(i)}

$$

È il **miglior compromesso**: aggiornamenti meno rumorosi del SGD puro e possibilità di usare prodotti matrice-vettore per l'efficienza computazionale. Questa è la strategia di ottimizzazione più usata nel deep learning moderno.

---

## 3. Problema di Classificazione

### Differenza con la regressione

Nella regressione l'output è un valore scalare $y \in \mathbb{R}$ (es. "numero di persone in spiaggia"). Nella **classificazione** l'output è un'**etichetta di classe** $y$: il modello che esegue la classificazione si chiama **classificatore**.

### Rappresentazione delle classi: One-Hot Encoding

Le classi vengono codificate come vettori $K$-dimensionali tramite **one-hot encoding**: il vettore ha valore 1 nella posizione corrispondente alla classe e 0 ovunque altrove. Ad esempio con $K = 5$ classi (delfino, orso grizzly, pesce chitarra, elefante, camaleonte): delfino $= [1, 0, 0, 0, 0]$, orso grizzly $= [0, 1, 0, 0, 0]$, ecc.

### Funzione Softmax

Prima di poter considerare l'output del modello come probabilità, bisogna soddisfare due proprietà della funzione di massa di probabilità (pmf): $0 \leq \hat{y}_k \leq 1$ e $\sum_{k=1}^{K} \hat{y}_k = 1$. La **softmax** trasforma il vettore di logit $g = [g_1, \ldots, g_K]$ in probabilità:

$$

\hat{y}_k = \frac{e^{g_k}}{\sum_{i=1}^{K} e^{g_i}}

$$

### Cross-Entropy Loss

La loss usata per la classificazione è la **cross-entropy**:

$$

L(\hat{y}, y) = -\sum_{k=1}^{K} y_k \log \hat{y}_k

$$

Intuitivamente, $\hat{y}_k$ rappresenta la probabilità che il classificatore assegna alla classe $k$. Se la predizione assegna il 70% alla classe corretta, la loss è $-\log 0.7 \approx 0.357$: si è al 30% di distanza dalla predizione ideale. Per un mini-batch di $N$ campioni, si media:

$$

L = -\frac{1}{N} \sum_{i=1}^{N} \sum_{k=1}^{K} y_k^{(i)} \log \hat{y}_k^{(i)}

$$

---

## 4. Il Problema della Generalizzazione

### Obiettivo fondamentale

L'obiettivo primario di qualsiasi algoritmo di deep learning non è ottenere buone prestazioni solo durante il training, ma **generalizzare bene** su dati mai visti (test). Ad esempio, un classificatore per immagini mediche deve funzionare correttamente sui dati di nuovi pazienti.

Il problema della generalizzazione è una delle considerazioni più importanti nella progettazione e nel training di modelli DL. Un fallimento nella generalizzazione può derivare da due cause: **underfitting** (il modello è troppo semplice per catturare i pattern) e **overfitting** (il modello memorizza i dati di training, incluso il rumore, senza apprendere i pattern sottostanti).

---

## 5. Regressione Polinomiale e Overfitting/Underfitting

### Regressione polinomiale

La **regressione polinomiale** è simile a quella lineare, ma lo spazio delle ipotesi è composto da funzioni polinomiali:

$$

y = f_\Theta(x) = \sum_{k=0}^{K} \theta_k x^k

$$

dove $K$ è il grado del polinomio. Con $K = 1$ si ottiene la regressione lineare (caso speciale). Per $K = 2$:

$$

y = \theta_0 + \theta_1 x + \theta_2 x^2

$$

L'equazione è non-lineare in $x$ (perché $x$ ha potenze maggiori di 1), ma è **lineare nei parametri** $\theta$ (ogni $\theta$ ha potenza 1). Tramite una trasformazione di feature $\phi(x) = [1, x, x^2]^\top$, il problema si riconduce a una regressione lineare: $y = \Theta^\top \phi(x)$.

### Influenza di K sulla generalizzazione

- **$K$ troppo grande** (es. $K = 5$): la funzione si espande per includere tutti i punti di training, producendo un fit ondulato → **overfitting** (va benissimo sul training, pessimamente sul test — è come memorizzare prima di un esame)

- **$K$ troppo piccolo** (es. $K = 1$): la funzione è troppo semplice per catturare il pattern → **underfitting** (va male sia su training che su test — è come studiare poco)

- **$K$ giusto** (es. $K = 2$): il modello cattura il pattern sottostante e generalizza bene

### Errore di approssimazione e di generalizzazione

L'**errore di approssimazione** è il gap tra la funzione fittata e i dati di training:

$$

J_{\text{approx}} = \frac{1}{N} \sum_{i=1}^{N} L(f_\Theta(x_{\text{train}}^{(i)}), y_{\text{train}}^{(i)})

$$

Minimizzare questo errore è detto **Empirical Risk Minimization**. L'**errore di generalizzazione** è il gap tra la funzione fittata e la funzione vera, approssimato su un validation set:

$$

J_{\text{gen}} \approx \frac{1}{N} \sum_{i=1}^{N} L(f_\Theta(x_{\text{val}}^{(i)}), y_{\text{val}}^{(i)})

$$

All'aumentare di $K$, l'errore di approssimazione diminuisce, ma l'errore di generalizzazione (il **generalization gap**) aumenta. Questo fenomeno è prevalente anche nelle DNN: reti sempre più grandi riducono l'errore di training ma possono aumentare l'errore di generalizzazione.

---

## 6. Trade-off Bias-Varianza

### Decomposizione dell'errore

L'errore di generalizzazione atteso si decompone in tre termini:

$$

\text{errore atteso} = (\text{bias})^2 + \text{varianza} + \text{rumore}

$$

Tra varianza e bias esiste un **trade-off**: non è possibile minimizzare entrambi contemporaneamente. Comprendere bene questo trade-off permette di diagnosticare e costruire modelli che generalizzano bene.

### Definizioni formali

Dato un dataset $D = \{(x^{(i)}, y^{(i)})\}_{i=1}^{N}$ campionato da $p_{\text{data}}$, si addestra un modello $f_D$. Poiché $D$ è una variabile casuale, anche $f_D$ lo è. Si definiscono:

- **Etichetta attesa**: $\bar{y} = \mathbb{E}_{y|x}[Y]$ — il valore che ci aspetteremmo mediando su infinite osservazioni per lo stesso input $x$

- **Classificatore atteso**: $\bar{f} = \mathbb{E}_{D \sim P^N}[f_D]$ — la media delle predizioni di infiniti modelli addestrati su infiniti training set diversi ("miglior classificatore possibile")

- **Errore atteso di test**: $\mathbb{E}_{(x,y) \sim p_{\text{data}}, D \sim P^N}[(f_D(x) - y)^2]$

### I tre termini

- **Varianza**: $\mathbb{E}_{x,D}[(f_D(x) - \bar{f}(x))^2]$ — quanto varia il classificatore se addestrato su training set diversi. Modelli complessi (alto $K$) fittano ogni dataset molto bene, ma producono predizioni molto diverse tra un dataset e l'altro.

- **Bias²**: $\mathbb{E}_x[(\bar{f}(x) - \bar{y}(x))^2]$ — l'errore intrinseco del classificatore anche con infiniti dati di training. Modelli semplici (basso $K$) sono sempre "biasati" verso una soluzione troppo semplice (es. lineare per dati parabolici).

- **Rumore**: $\mathbb{E}_{x,y}[(\bar{y}(x) - y)^2]$ — la variabilità intrinseca dei dati, irriducibile.

### Analogia del bersaglio (darts)

- **Basso bias, bassa varianza**: predizioni sempre centrate sul bersaglio

- **Basso bias, alta varianza**: in media centrate, ma molto disperse

- **Alto bias, bassa varianza**: sempre fuori centro, ma raggruppate

- **Alto bias, alta varianza**: fuori centro e disperse

### Complessità del modello

All'aumentare di $K$, i pesi $\theta$ diventano più grandi (modello più complesso): piccole variazioni di $x$ producono grandi cambiamenti nella predizione. Con **modello troppo semplice**, l'errore è dominato dal bias; con **modello troppo complesso**, dall'errore di varianza. Il punto ottimale si trova nel mezzo.

### Come diagnosticare

- **Overfitting** (alta varianza): grande gap tra errore di training (basso) e errore di test (alto). Rimedi: regolarizzazione, più dati di training, bagging.

- **Underfitting** (alto bias): entrambi gli errori (training e test) sono alti. Rimedi: modelli più complessi, aggiungere feature.

---

## 7. Regolarizzazione

### Concetto

La **regolarizzazione** è un meccanismo che penalizza la complessità della funzione per evitare overfitting. Si aggiunge un termine alla loss function:

$$

J(\Theta) = \frac{1}{N} L(f_\Theta(x^{(i)}), y) + \lambda R(\Theta)

$$

dove $\lambda$ è un iperparametro che controlla la forza della regolarizzazione. Il termine regolarizzatore riduce la magnitudine dei pesi, favorendo soluzioni più semplici.

### Regolarizzazione $\ell_2$

Si penalizza la norma $\ell_2$ dei parametri:

$$

R(\Theta) = \frac{1}{2} \|\Theta\|_2^2 = \frac{1}{2} \sum_{k=0}^{K} \theta_k^2

$$

Il termine non dipende dai dati, ma solo dai parametri. Geometricamente, per un modello con due parametri $(\theta_0, \theta_1)$, la regolarizzazione è un cerchio centrato nell'origine: la soluzione si trova all'intersezione tra le curve di livello della loss originale e il cerchio. Sebbene non si raggiunge il minimo della loss pura, si ottiene una soluzione con pesi più piccoli e generalizzazione migliore.

---

## 8. Tecniche di Validazione

### Cross Validation

Per scegliere il modello giusto (es. il valore di $K$) e gli iperparametri (es. $\lambda$), si usa la **cross validation**: si divide il dataset in training set (per addestrare) e test set (per valutare la generalizzazione). Una strategia comune è la **suddivisione in tre parti**: training set (addestramento), validation set (scelta degli iperparametri) e test set (valutazione finale). Split tipici: 80%/20% o 60%/20%/20%.

### Grid Search

Il **Grid Search** è un metodo di tuning degli iperparametri: si provano esaustivamente tutte le combinazioni dei valori degli iperparametri, addestrando e valutando il modello su ciascuna combinazione tramite cross-validation. Si seleziona la combinazione migliore.

### K-fold Cross Validation

Il training set viene diviso in $k$ parti uguali (fold). Per ogni split: si addestra il modello su $k-1$ fold e si valida sul fold rimanente. La performance finale è la **media** delle performance su tutti gli split. Vantaggi: uso efficiente dei dati (specialmente con dataset piccoli) e riduzione del rischio di risultati fuorvianti dovuti a uno split sfortunato. Quando $k = N$ (dimensione del training set), si ottiene la **Leave-One-Out (LOO)** cross-validation.

### Early Stopping

L'**early stopping** è una tecnica di regolarizzazione che ferma il training quando la performance sul validation set inizia a peggiorare. Si monitora la validation loss ad ogni iterazione (o a intervalli predefiniti) e si interrompe il training se la loss non migliora, prevenendo l'overfitting.

---

## 9. Neuroni Biologici e Artificiali

### Il cervello umano come ispirazione

I **neuroni** sono le unità fondamentali del cervello umano. Il loro funzionamento:

- I segnali (impulsi elettrici) arrivano ai **dendriti**

- Il **corpo cellulare** (soma) integra i segnali in ingresso. Se l'input combinato supera una soglia, il neurone "spara" (fires)

- Il segnale viaggia lungo l'**assone** fino al neurone successivo attraverso le **sinapsi**

Reti di neuroni permettono al cervello di processare input sensoriali, riconoscere pattern e prendere decisioni.

### Analogia con il neurone artificiale

- **Dendriti** → nodi di input che ricevono valori numerici

- **Corpo cellulare** → somma pesata degli input

- **Soglia di attivazione** → funzione di attivazione che decide l'output

- **Assone** → valore di output passato allo strato successivo

Le reti neurali artificiali (ANN) non copiano il cervello esattamente, ma ne prendono in prestito l'idea fondamentale: **unità semplici + molte connessioni = computazione potente**.

---

## 10. Percettrone e Classificazione Lineare

### Il percettrone

Il **percettrone** è il neurone artificiale più semplice. Per un input $x = [x_1, x_2, \ldots, x_d]$:

$$

z = w^\top x + b \quad \text{(trasformazione affine)}

$$

$$

a = f(z) = \begin{cases} 0 & \text{se } z \leq 0 \\ 1 & \text{se } z > 0 \end{cases} \quad \text{(step function)}

$$

La **funzione di attivazione** è una regola matematica che decide se il neurone deve "attivarsi" o no. Nel percettrone originale di Rosenblatt l'attivazione era una step function, poi gradualmente sostituita dalla **sigmoid**.

### Il percettrone come classificatore lineare

Il **decision boundary** (confine decisionale) è dato da $z = w^\top x + b = 0$, che è una funzione lineare (un iperpiano). Il percettrone divide lo spazio in due regioni: classe 1 (sopra l'iperpiano) e classe 0 (sotto).

### Apprendimento del percettrone

Apprendere il percettrone significa trovare i pesi $w$ e il bias $b$ che minimizzano una loss di classificazione:

$$

w^*, b^* = \arg\min_{w, b} \frac{1}{N} \sum_{i=1}^{N} L(w^\top x^{(i)} + b, \; y^{(i)})

$$

Aggiustare $w$ e $b$ equivale a **spostare e ruotare** il decision boundary fino a separare le classi.

### Capacità del percettrone

Il percettrone può risolvere problemi **linearmente separabili**: OR, AND, NOT. Ma **non può risolvere XOR**, perché il problema non è linearmente separabile (dimostrato da Minsky & Papert, 1969). Questa limitazione motivò lo sviluppo del Multilayer Perceptron.

---

## 11. Multilayer Perceptron (MLP)

### Struttura

Il **Multilayer Perceptron (MLP)**, o rete neurale, risolve il problema XOR e altri problemi non-linearmente separabili. Inventato da Frank Rosenblatt nel 1958, contiene:

- **Strato di input**: riceve i dati

- **Uno o più strati nascosti** (hidden layers) con funzioni di attivazione non-lineari (es. sigmoid)

- **Strato di output**: produce la predizione

Ogni hidden unit impara un classificatore lineare. Combinando più classificatori lineari, l'MLP ottiene un **decision boundary non-lineare**.

### Risoluzione di XOR

Due neuroni nascosti creano ciascuno un iperpiano. L'output combina i due iperpiani (intersezione), producendo la corretta separazione non-lineare. **Risultato fondamentale**: combinando diversi iperpiani lineari si possono creare decision boundary arbitrariamente complessi.

### Profondità e larghezza

Non c'è limite al numero di hidden layer (**profondità**) né al numero di neuroni per strato (**larghezza**). Più si va in profondità e in larghezza, più complesso diventa il decision boundary. Il **Teorema di Approssimazione Universale** (Cybenko, 1989) dimostra che un MLP con un solo strato nascosto, un numero sufficiente di neuroni e attivazione non lineare può approssimare qualsiasi funzione continua.

### Primo inverno dell'IA (1960-1980)

Nonostante la potenza teorica, il progresso si bloccò per: mancanza di hardware (le moltiplicazioni matriciali erano troppo costose), la dimostrazione teorica che un solo layer era sufficiente (motivazione ridotta per reti profonde) e l'assenza di un algoritmo efficiente per addestrare gli strati nascosti. Questo portò a tagli ai finanziamenti: il **Primo Inverno dell'IA**.

---

## 12. Backpropagation

### Rinascita del deep learning

Nel 1986, Rumelhart, Hinton e Williams pubblicarono un articolo fondamentale mostrando che l'algoritmo di **backpropagation** poteva addestrare efficientemente le reti neurali profonde, superando la principale limitazione che aveva bloccato il progresso dagli anni '60.

### Regola della catena (chain rule)

Per la discesa del gradiente servono $\frac{\partial L}{\partial w}$ e $\frac{\partial L}{\partial b}$ per ogni parametro. Per una rete a due strati con parametri $w_1, b, w_2$:

$$

\frac{\partial L}{\partial w_2} = \frac{\partial L}{\partial y} \cdot z, \qquad \frac{\partial L}{\partial w_1} = \frac{\partial L}{\partial y} \cdot w_2 \cdot x, \qquad \frac{\partial L}{\partial b} = \frac{\partial L}{\partial y} \cdot w_2 \cdot 1

$$

La formula chiave è:

$$

\text{gradiente} = \text{gradiente locale} \times \text{gradiente upstream}

$$

Il gradiente calcolato diventa il gradiente upstream per i parametri dello strato precedente, come nel gioco del "telefono senza fili".

### Meccanismo

Si parte dallo strato di output (back), si calcola il gradiente ad ogni strato e si procede verso lo strato di input (front) — da qui il nome "backpropagation". Anche per un parametro $w_1$ che si trova in profondità nella rete, basta moltiplicare il gradiente locale $\frac{\partial z}{\partial w_1}$ per il gradiente upstream $\frac{\partial L}{\partial z}$ (già calcolato nel round precedente). La backpropagation è un modo **elegante ed efficiente** di calcolare i gradienti riutilizzando gradienti già calcolati.

### Ruolo della funzione di attivazione

La funzione di attivazione nello strato nascosto ha impatto sul calcolo dei gradienti: bisogna calcolare anche $\frac{\partial a}{\partial z}$ (la derivata dell'attivazione). Questo è il motivo per cui servono **funzioni di attivazione differenziabili**: senza derivata non c'è flusso di gradiente e senza gradiente non c'è aggiornamento dei pesi.

---

## 13. Training di una Rete Neurale

### Procedura ad alto livello

L'addestramento di una rete neurale prevede tre fasi iterative:

1. **Forward pass**: calcolare l'output della rete e la loss (errore)

2. **Backward pass** (backpropagation): propagare l'errore all'indietro attraverso gli strati usando la chain rule per calcolare i gradienti della loss rispetto a ogni peso

3. **Weight update**: aggiornare i pesi con gradient descent (o varianti come SGD)

Queste tre fasi vengono ripetute per molte iterazioni (epoche) fino a convergenza.

### Secondo inverno dell'IA (1990-2005)

Nonostante le innovazioni della fine degli anni '80, il successo fu di breve durata. Le **Kernel Machines** (es. SVM) diventarono molto popolari: accuratezza simile con meno euristiche e parametri, e belle dimostrazioni sulla generalizzazione. La potenza computazionale era ancora un collo di bottiglia per addestrare reti profonde e i grandi dataset annotati erano scarsi.

---

## 14. Vettorizzazione nel MLP e Classificazione Multiclasse

### Vettorizzazione delle trasformazioni affini

In ogni strato $i$-esimo di un MLP si eseguono due operazioni:

1. **Trasformazione affine**: $z_i = W_i a_{i-1} + b_i$

2. **Attivazione non-lineare**: $a_i = \sigma(z_i)$

Per lo strato nascosto con neuroni $h_1, h_2$, le due computazioni separate si combinano in un unico prodotto matrice-vettore:

$$

z = \begin{bmatrix} w_{11} & w_{12} \\ w_{21} & w_{22} \end{bmatrix} \begin{bmatrix} x_1 \\ x_2 \end{bmatrix} + \begin{bmatrix} b_1 \\ b_2 \end{bmatrix}, \quad a = \sigma(z)

$$

Il vettore $a$ è detto **feature vector**. Gli strati nascosti sono collettivamente il **feature extractor**, e lo strato di output è il **classificatore**.

### Classificazione multiclasse

Lo strato di output ha tanti output unit (logit) quante sono le classi. Per $K$ classi, i logit $g = [g_1, \ldots, g_K]$ sono calcolati con un unico prodotto matrice-vettore. Si applica poi la **softmax** per ottenere le probabilità:

$$

\hat{y} = \text{softmax}(g), \qquad \hat{y}_k = \frac{e^{g_k}}{\sum_{i=1}^{K} e^{g_i}}

$$

---

## 15. Deep Neural Networks (DNN)

### Struttura

Le **DNN** impilano il motivo lineare-nonlineare molte volte. Ogni strato è una funzione, e la DNN è una **composizione di funzioni**:

$$

f_{\text{DNN}}(x) = g(f_L(f_{L-1}(\cdots f_2(f_1(x)))))

$$

dove $f_i$ è la funzione dello strato $i$-esimo e $g$ è il classificatore (con softmax) all'output. Ogni strato $f_i$ è associato a pesi $W_i$ e bias $b_i$ (collettivamente: parametri), addestrati tramite gradient descent.

---

## 16. Funzioni di Attivazione

### Perché sono necessarie

Senza attivazione non-lineare, una rete profonda collassa in un modello lineare equivalente:

$$

f_{\text{NN}}(x) = w^\top(Wx + b) + b = \hat{w}^\top x + \hat{b}

$$

che è un singolo iperpiano (classificatore lineare). Le funzioni di attivazione introducono **non-linearità** nei decision boundary, permettendo di modellare funzioni complesse. Il **Teorema di Approssimazione Universale** garantisce che con attivazioni non-lineari, le reti possono approssimare qualsiasi funzione.

### Funzioni comuni

Le attivazioni più comuni sono: **sigmoid** $\sigma(x) = \frac{1}{1+e^{-x}}$ (usata storicamente), **tanh** $\tanh(x)$ (simile alla sigmoid, centrata sullo zero), **ReLU** $\max(0, x)$ (la più usata), e **LeakyReLU** $\max(\alpha x, x)$ con $\alpha$ piccolo.

### Problema del Vanishing Gradient

Sigmoid e tanh soffrono di **vanishing gradient**: nelle regioni sature (valori molto grandi o molto piccoli) la derivata è prossima a zero. Il gradiente backpropagato diventa:

$$

\frac{\partial L}{\partial z_{i-1}} = \frac{\partial L}{\partial z_i} \cdot \sigma'(w z_{i-1}) \cdot w

$$

La moltiplicazione ripetuta di gradienti piccoli ($\sigma'$ in regioni sature) fa sì che i gradienti nei primi strati tendano a zero → la rete smette di imparare.

### ReLU come soluzione

La **ReLU** $\max(0, x)$ risolve il problema perché **non ha saturazione** sul lato positivo: la derivata è costante e uguale a 1. Tuttavia, ReLU ha gradiente nullo sul lato negativo (neuroni "morti"). **LeakyReLU** risolve questo aspetto mantenendo un piccolo gradiente $\alpha$ anche per valori negativi. Nella pratica, ReLU è sufficiente per la maggior parte dei casi.

### Impatto sul decision boundary

- **Sigmoid**: decision boundary lisci e fluidi, ma convergenza lenta per vanishing gradient

- **ReLU**: decision boundary più "spigolosi", ma convergenza molto più veloce

---

## 17. Inizializzazione dei Pesi

### Perché è importante

L'**inizializzazione** dei pesi definisce il punto di partenza del training. Non si devono mai inizializzare tutti i pesi a **zero**, perché ciò causa il **problema della simmetria**: tutti i neuroni calcolano lo stesso output (stessi pesi → stesso dot product → stessa attivazione), ricevono gradienti identici durante la backpropagation e vengono aggiornati allo stesso modo — restando indistinguibili e imparando le stesse feature.

I pesi vanno inizializzati a **piccoli valori casuali** per rompere la simmetria. Ma:

- **Pesi troppo piccoli** → attivazioni piccole → gradienti piccoli → **vanishing gradient** → la rete non impara

- **Pesi troppo grandi** → output e gradienti crescono esponenzialmente → **exploding gradient** → instabilità numerica e divergenza

### Tecniche di inizializzazione

**Xavier Initialization**: i pesi vengono inizializzati in modo che la varianza delle attivazioni resti costante tra gli strati. Adatta per sigmoid/tanh:

$$

w \sim U\left(-\sqrt{\frac{6}{n_{in} + n_{out}}}, \; \sqrt{\frac{6}{n_{in} + n_{out}}}\right)

$$

dove $n_{in}$ e $n_{out}$ sono il numero di nodi in ingresso e in uscita dello strato.

**He Initialization**: ottimizzata per ReLU e varianti:

$$

w \sim U\left(-\sqrt{\frac{6}{n_{in}}}, \; \sqrt{\frac{6}{n_{out}}}\right)

$$

Con pochi neuroni di input il range dei pesi è ampio; il range cala rapidamente all'aumentare dei neuroni, poi si stabilizza.

---

## 18. Algoritmi di Ottimizzazione Avanzati

### Ruolo dell'ottimizzazione

L'obiettivo è minimizzare la loss function aggiornando i pesi iterativamente. La regola base del gradient descent è:

$$

w \leftarrow w - \alpha \nabla_w J

$$

### SGD con Momentum

Un problema dell'SGD è che può **oscillare** in loss landscape stretti (valli profonde e strette), causando convergenza lenta. Il **momentum** aggiunge un termine di velocità (inerzia) che accumula la direzione dominante degli aggiornamenti passati:

$$

v_t \leftarrow \beta v_t + (1 - \beta) \alpha \nabla_w J

$$

$$

w \leftarrow w - v_t

$$

dove $v_t$ è la velocità all'iterazione $t$ e $\beta$ è il coefficiente di momentum (tipicamente 0.9). Intuitivamente, $v_t$ cattura la direzione dominante tramite una **media mobile esponenziale** (90% gradienti passati + 10% gradiente corrente), smorzando le oscillazioni e accelerando nella direzione consistente.

### Panoramica degli ottimizzatori

- **SGD**: aggiornamento con gradiente del mini-batch — convergenza lenta

- **SGD con Momentum**: aggiunge inerzia — convergenza più veloce, meno oscillazioni

- **Adagrad**: adatta il learning rate per ogni parametro in base alla somma dei quadrati dei gradienti accumulati $G_t = (\nabla_w J)^2$; il learning rate diventa $\frac{\alpha}{\sqrt{G_t + \epsilon}}$ — non richiede tuning del learning rate ma il rate decade nel tempo

- **RMSProp**: media mobile esponenziale dei gradienti al quadrato: $G_t \leftarrow \beta G_{t-1} + (1-\beta) G_t$ — impedisce al learning rate di decrescere troppo velocemente

- **Adam**: combina Momentum + RMSProp; $v_t \leftarrow \beta_1 v_t + (1-\beta_1) \nabla_w J$ e $g_t \leftarrow \beta_2 g_t + (1-\beta_2)(\nabla_w J)^2$ — veloce, adattivo, richiede poco tuning. Tutte le reti neurali moderne vengono addestrate con mini-batch SGD e ottimizzatori come Adam.

---

## 19. Funzioni di Loss

### Ruolo

La **loss function** misura la differenza tra output predetto e target vero: quantifica "quanto sbaglia" il modello. Guida il gradient descent e quindi l'apprendimento. Una loss ben scelta si allinea con l'obiettivo del task.

### Loss per la classificazione

- **Binary Cross-Entropy (BCE)** per classificazione binaria:

$$

L_{\text{BCE}} = -(y \log \hat{y} + (1-y) \log(1-\hat{y}))

$$

dove $y \in \{0, 1\}$ è l'etichetta e $\hat{y} \in [0, 1]$ è la predizione. Per un mini-batch di $N$ campioni: $L_{\text{BCE}} = -\frac{1}{N} \sum_{i=1}^{N} (y^{(i)} \log \hat{y}^{(i)} + (1-y^{(i)}) \log(1-\hat{y}^{(i)}))$.

- **Cross-Entropy (CE)** per classificazione multiclasse:

$$

L_{\text{CE}} = -\sum_{k=1}^{K} y_k \log \hat{y}_k

$$

Per un mini-batch: $L_{\text{CE}} = -\frac{1}{N} \sum_{i=1}^{N} \sum_{k=1}^{K} y_k^{(i)} \log \hat{y}_k^{(i)}$.

### Loss per la regressione

- **MSE (Mean Squared Error)**: penalizza errori grandi (outlier): $L_{\text{MSE}} = \frac{1}{N} \sum_{i=1}^{N} (y^{(i)} - \hat{y}^{(i)})^2$

- **MAE (Mean Absolute Error)**: tratta tutti gli errori ugualmente: $L_{\text{MAE}} = \frac{1}{N} \sum_{i=1}^{N} |y^{(i)} - \hat{y}^{(i)}|$

- **Huber Loss**: combina MSE (per errori piccoli) e MAE (per errori grandi). Dato $a = y - \hat{y}$ e una soglia $\delta$:

$$

L_{\text{Huber}} = \begin{cases} \frac{1}{2} a^2 & \text{se } |a| \leq \delta \\ \delta(|a| - \frac{1}{2}\delta) & \text{altrimenti} \end{cases}

$$

---

## 20. Tecniche di Regolarizzazione per Reti Neurali

### Motivazione

Le reti neurali sono flessibili ma inclini all'**overfitting** — imparano il rumore e gli outlier, non il segnale. La regolarizzazione aiuta a **generalizzare meglio** su dati non visti.

### Weight Decay

Il **weight decay** penalizza i pesi grandi aggiungendo un termine alla loss proporzionale al quadrato dei pesi:

$$

J_{\text{reg}}(w) = J(x, w) + \frac{\lambda}{2} \sum_i w_i^2

$$

dove $\lambda$ controlla la forza della regolarizzazione. Ha lo stesso effetto della regolarizzazione $\ell_2$, ma in pratica viene incorporato direttamente nell'ottimizzatore (basta specificare il parametro `weight_decay`). Promuove decision boundary più smooth e semplici. Con **weight decay alto**: confini decisionali semplici e lisci. Con **weight decay nullo**: la rete può overfittare sugli outlier, producendo confini complessi e irregolari.

### Dropout

Il **dropout** disattiva casualmente neuroni durante il training. Ogni neurone viene mantenuto attivo con probabilità $p$ (keep probability, iperparametro) o posto a zero con probabilità $(1 - p)$, indipendentemente per ogni campione di training. Il dropout viene **disabilitato al test time**.

L'intuizione è impedire alla rete di diventare troppo dipendente da specifici neuroni: non affidarsi a un unico percorso, incoraggiando **ridondanza e robustezza**. Il dropout promuove una distribuzione più uniforme dei pesi tra i neuroni di input, evitando che singoli pesi diventino troppo grandi.

---

## 21. Tecniche di Normalizzazione

### Perché normalizzare

Se le feature di input hanno scale molto diverse, la loss landscape diventa stretta e profonda con valli difficili da navigare, causando oscillazioni e convergenza lenta. Effetto collaterale: apprendimento di pattern sensibili alla scala (overfitting).

### Normalizzazione dei dati

La **normalizzazione** riporta tutte le feature a scale comparabili: si sottrae la media (zero-centering) e si divide per la deviazione standard. Dopo la normalizzazione la loss landscape diventa più "liscia" e l'ottimizzazione più efficiente.

### Internal Covariate Shift

Nelle reti profonde, la distribuzione delle attivazioni varia notevolmente tra gli strati (le distribuzioni "shiftano" man mano che si va in profondità), rendendo il training instabile.

### Batch Normalization

La **Batch Normalization** risolve l'internal covariate shift standardizzando gli input di ogni strato per ogni mini-batch. L'algoritmo:

1. Per un dato mini-batch, calcola media $\mu$ e varianza $\sigma^2$ delle feature intermedie $x$

2. Normalizza ciascuna feature: $\hat{x}_i = \frac{x_i - \mu}{\sqrt{\sigma^2 + \epsilon}}$

3. Applica **scale** ($\gamma$) e **shift** ($\beta$), che sono **parametri apprendibili**: $y_i = \gamma \hat{x}_i + \beta$

I parametri $\gamma$ e $\beta$ permettono alla rete di imparare se e quanto la normalizzazione è utile per ogni strato, mantenendo la flessibilità espressiva.

### Considerazioni finali

Non esiste una singola scelta "migliore" per ogni componente: tutto dipende dai dati, dal tipo di task e dalla profondità dell'architettura. Il training di reti neurali richiede molta attenzione (baby-sitting): usare il validation set per trovare le configurazioni ottimali, monitorare overfitting e instabilità del training.