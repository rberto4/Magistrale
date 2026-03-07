Di base un deep learning system, prende un input in ingresso e restituisce un output
- Il Learning : l'obbiettivo del learning e'quello di estrarre "lezioni"dall'esperienza passata, in modo da risolvere problemi futuri.
Deve restituirci un algoritmo in fase di **training**, che usiamo poi in futuro per **testing**.
![[Pasted image 20260213234554.png]]
il **learner** produce un algoritmo / funzione che mappa gli input negli output
Quello che noi dobbiamo "imparare", sono i parametri da assegnare alla funzione  
fare **tuning** dei parametri.
#### Funzione di costo (o funzione obietivo) - Loss
Misurare quanto bene il modello ha fatto la predizione, abbiamo bisogno di quantificare il successo. ad esempio funzione di costo loss quad

![[Pasted image 20260214001220.png]]

Andiamo a **minimizzare la funzione di costo** cioé trovare i valori che rendono l'errore piu' piccolo possibile
Come ? -> **gradient descent** e' il metodo che ci dice come scendere al valore piu' basso sulla funzione
#### Gradient descent
Algoritmo iterativo che ci permette di minimizzare la funzione di costo
![[Pasted image 20260214002303.png]]
la derivata della loss function rispetto al parametro, ci dice se la funzione sta crescendo o descrendo. il nostro obbiettivo e' muoverci in posizione in cui descresce
Iter iniziale : il primo teta é random
il secondo sara' calcolato in base alla formula.
il learning rate ci dice " quanto ci sposteremo" dalla prossima iterazione
- troppo grande -> non raggiungiamo mai il minimo, osccilazione
- troppo piccolo -> tempo infinito e spostamenti infinitesimali
Ci fermiamo quando la derivata e' vicina allo zero, o per altri criteri

#### funzioni che non sono convesse
In base a dove parte teta zero randomico, si richia di cadere in minimi locali e non globali

#### Caso multiparametro
![[Pasted image 20260214004015.png]]

# Cap 04

#### Casi multivariabili con batch gradient descent
Se abbiamo N dati di trainini, vogliamo aggiornare i pesi solo per un punto alla volta. ci occorre la media dei gradienti di tutti i dai
aggiungendo 
$$
\frac{1}{N}
$$

nella formula di aggiornamento
questo e' il batch gradient descent perche' sto usando tutti i dati ad ogni aggiornamento
Il suo svantaggio principale e' che se N diventa molto grosso...diventa oneroso computazionalmente
#### better : stochastic G.D. and Mini-batch
Aggiorna i parametro usando un solo dato alla volta, risultando piú rumoroso e meno stabile ma molto piú efficiente.
Una soluzione alternitiva e' la via di mezzo tra le due : sfruttare dei mini batch, dei gruppi di training grandi m, di aggiornamenti con 
$$
M << n
$$

Ci permette di avere un sistema non rumoroso ma ugualmente capace di essere calcolato con prodotti tra matrici e vettori.
E' il milgior GD, usato anche oggi
#### Classification problems
nella regressione trattiamo con numeri, ma spesso ci servono classificazioni che non sono legate ai numeri come significato
Classifier -> produce come output una class label Y
#### One hot encoding
Invece di assegnare ad ogni classe, semplicemente un id numerico, usiamo un vettore K-dimensionale per K classi, tutto a zeri tranne un valore a uno, corrispondente alla classe.
Utilizziamo una funzione di costo detta **cross entropy loss**.
![[Pasted image 20260214102701.png]]
# Cap 05
l'obiettivo non e' solo fare bene nella fase di training, ma fare bene anche nella fase di test, dove si generalizza a input differenti 
se non si affronta bene il problema, si rischiano :
- Underfitting : il modello generalizza troppo, ed e' troppo semplice per catturare la struttura reale dei dati.
- Overfitting : il modello si adatta troppo ai dati di train.

#### Regressione polinomiale
Estensione della regressione lineare in cui mostriamo il modello in forma di polinomio 
di K-esimo grado, con K = 1 ottengo la regressione lineare (che ne e' un caso particolare)
$$
y=f_Θ​(x)=\sum_{k=0}^{K} θ_k​x^k
$$
Ci permette di valutare anche casi di relazioni non lineari
$$ 
y=θ_0​+θ_1​x+θ_2​x^2
$$
NON e' lineare rispetto a x, lo e' rispetto ai parametri
#### Features trasformation
Meccanismo per linearizzare trasformando il vettore delle features, e trasformare il problema in una regressione lineare classica
- Se K grande : Overfitting
- Se K piccolo : Underfitting 
![[Pasted image 20260214110742.png]]
#### errore di approssimazione
Misura di quanto il modello si discosta dai dati -> obbiettivo e'trovare i parametri theta che lo minimizzano 
$$
J_{approx} = \frac{1}{N}\sum_{i=1}^{K}L(fΘ​(x_{train}^i​),y_{train}^i​)​)
$$
#### Bias variance tradeoff
l'errore di generalizzazione mostra quanto bene il nostro modello generalizza i dati, ovvero come si comporta con dati che non ha mai visto prima
deconponiamo l'errore in 3 parti :
- Bias : errore sistemico
- Variance : variabilita' del modello
- Rumore irreducibile
$$
Errore \space totale=Bias^2+Variance+Rumore
$$
Siamo in regressione lineare, vogliamo predire Y usando features X (sono variabili casuali)
i dati si distribuiscono con una certa distribuzione di probabilità' $P_{data}$
Il valore medio che ci aspettiamo dato un certo x e' il valore atteso 
$$
\hat{y} = E_{y|x}[Y]
$$
in pratica osservando infinite volte la stessa situazione x, questa e' la media di tutti i risultati che otterremmo.
Il dataset e' casuale 
$$
D = [{(x^i),(y^i)}]^N _{i=1}
$$
Anche il modello $f_D​$ e' casuale perche' dipende da $D$ che e' casuale a sua volta.
Quindi abbiamo :
$$
\mathbb{E}_{D \sim LN} \mathbb{E}_{(x,y) \sim \text{Pdata}} \left[ (f_D
(x) - y)^2 \right]
$$
![[Pasted image 20260214133825.png]]
Alto bias -> Underfitting, soluzione -> aggiungere feature, modello piú complesso
Alta variance -> overfitting, soluzione -> regularization, Bagging, More training data

#### Regolarizzazione
Meccanismo per penalizzare la complessità' delle funzione (del modello), senza cambiarne la struttura e **diminuire l' overfitting**
Si aggiunge un regolarizer alla loss function :
$$
+ \space λR(Θ)
$$
#### L2 reg
Un modo e' penalizzare la norma dei parametri
$$
∥Θ∥_2​= \sqrt {θ_0^2​+θ_1^2​+⋯+θ_K^2} ​​
$$
$$
R(Θ) = \frac{1}{2}{∥Θ∥_2}​^2
$$
il fattore 1/2 e' solo a scopo di semplificazione della derivata
il termine $λ$ a gestire il "quanto" si regola
- λ = 0 → nessuna regolarizzazione
- λ piccolo → leggera penalizzazione
- λ grande → forte penalizzazione
- λ → infinito → tutti i pesi → 0
All’aumentare di λ:
- ↑ Bias
- ↓ Variance
#### Tecniche di validazione
Come faccio a scegliere il modello corretto ? esempio, il grado, quanti parametri, gli iperparametri come lambda ?
Serve un modo per stimare l'errore sui nuovi dati
#### Cross validation
Il metodo piu' semplice e' splittare il dataset in 2 parti :
- **Training set** : per addestrare il modello
- **Test set** : per valutare la generalizzazione
tipicamente 80 e 20
oppure :
- **Training set** → allenamento
- **Validation set** → scelta degli iperparametri come la K o la lambda della regolarizzazione
- **Test set** → valutazione finale
coe 60-20-20
#### K-fold 
Dividi il dataset in K parti uguali, per ogni fold aleni su k-1 parti, validi sulla parte rimanente
Uso tutti i dati per il training e la validation, ho una stima piu stabili dell'errore perche' riduce la dipendenza da uno split sfortunato.
#### Early stopping
E' una tecnica dinamica, invece di penalizzare i pesi, fermiamo l'allenamento prima quando l'errore smette di diminuire
