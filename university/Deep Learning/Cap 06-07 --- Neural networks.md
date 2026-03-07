sono modelli computazionali che Traggono ispirazione dai neuroni biologici
l'ispirazione arriva dal concetto di -> tante unita' semplici + molte connessioni = comportamento intelligente
#### perceptron Frank Rosenblatt
gli input sono numerici
ogni input ha un **peso** associato (che ne indica línmportanza)
si fa la somma pesata + un bias
il risultato della somma passa per una funzione di attivazione che ci restituisce l' output 
il **learning** consiste nel settare i pesi in modo che si minimizzi la funzione obbiettivo.
il settaggio dei pesi e del bias consiste nel variare il decision boundary
![[Pasted image 20260214161531.png]]
la funzione di attivazione  permette di decidere o quantificare il risultato
- step function :  0 e 1 usata nel perceptron
- sigmoide : tra 0 e 1
- ReLU : 0 se x<0 oppure 1 se x $≥$ 1 
Il perceptron puó risolvere operazioni come AND, OR, NOT
NON puo' risolvere XOR
#### Multilayer perceptron - MLP
XOR -> 1 quando gli input sono diversi.
il caso semplice, crea una separazione lineare del piano con un retta.
ma non posso usare una retta per dividere il decision boundary di XOR
- soluzione : 1 o N hidden layers
con 2 rette posso creare zone "a fette" 
la funzione di attivazione e' una sigmoide :
$$
\frac{1}{1+e^{-x}}
$$
Serve la non linearita' perche' altrimenti, avremmo ancora trasformazioni lineari
introduciamo quindi la sigmoide
![[Pasted image 20260214174503.png]]
Ci serve la chain rule, perche' i pesi dei layer nascosti influenzano indirettamente l'errore 
#### Back propagation
Ho bisogno di calcolare efficientemente i vari gradienti
Prima calcolo il forward pass, trovo l' errore quadratico medio L
e poi ho bisogno di aggiornare i pesi
La chain rule e' il metodo per comporre le derivate di funzioni annidate, cioe' coistrutre una dentro l'altra
- calcolo derivata della Loss rispetto all' output finale
- propago queste informazioni a ritroso moltiplicando per le derivate locali dei vari nodi
# Cap 07

Operazioni di vettorizzazione
![[Pasted image 20260214185203.png]]
Multiclass -> per classificare K classi, Layer di output ha K-neuroni -> K-Logits 
che entrano in una funzione Softmax, che trasforma i logits in probabilita'
Quindi non ho piu'necessita'di una distinzione binaria di vero o falso...ma ho bisogno di una vera e propria probabilitá -> softmax
#### Activation functions
Permettono alle DNN di imparare patterns complessi e approssimare qualsiasi funzione.
(Universal Approximation Theorem).
Una MLP con molti hidden layers diventa una DNN

#### Deep neural network
ci sono molte traformazioni successive
i primi livelli rilevano pattern semplici, i layer piu'profondi rilevano concetti piú specifici e complessi

Funzioni di attivazioni diverse, con la sigmoide cadiamo nel **vanishing gradient problem**
ovvero che i gradienti nelle zone piu' esterne della funzione (le zone di saturazione) diventano molto piccoli portano a moltiplicazioni ininfluenti a destra e a valori nulli a sinistra

#### relu
la relu risolve la saturazione a destra dello zero, nella parte positiva, perche' la derivata e' = 1 ed e'costante nel lato positivo
Per la zona sinistra, sul versante negativo 
- Leaky ReLU -> al posto di avere la parte negativa costante a zero, si ha un valore molto piccolo :
$$
f(x) = 0.01x, \space x<0
$$

la relu porta ad avere decision boundary piu' rugged, ma non soffre di vanishing problem a differenza della sigmoide (però é più smooth)
#### Weight initializzazion
Come imposto i pesi iniziali in una rete neurale ? (che dopo verranno aggiornati nel training via stochastic gradient descent)
NON possono essere settati a zero.
vengono settati a valori random piccoli, MA non troppo :
- Se troppo piccoli rischiamo di cadere nel Vanishing gradients perche' le derivate diventeranno troppo piccole durante la fase di backpropagation.
- Se troppo grandi possono portare a divergenze durante la fase di training
#### Tecniche particolari 
- Xavier weight init -> obbiettivo e' mantenere costante la varianza dei segnali attraverso i layer
$$
W \sim \mathcal{U}\left(-\sqrt{\frac{6}{n_{\text{in}} + n_{\text{out}}}}, \; \sqrt{\frac{6}{n_{\text{in}} + n_{\text{out}}}}\right)
$$
dove $n_{in}$ e $n_{out}$ sono i numeri di neuroni in ingresso e uscita dal layer
#### He Initialization 
Metodo di inizializzazione dei pesi ottimizzato per le ReLU e varianti
siccome con ReLU tutte i valori negativi sono a 0, la meta' dei neuroni si spengono e la varinza dell'output si dimezza
Stessa formula ma compare solo $n_{in}$ 
Se usassimo Xavier la varianza diminuirebbe layer dopo layer


#### Optimization 
Durante la fase di addestramento, aggiorniamo i pesi della rete.
la fase di ottimizzazione ci dice "come" cambiare i pesi, quanto calibrarli e garantisce che l' errore diminuisca
#### SGD con momentum
Il SGD varia molto da batch a batch, quindi si aggiunge un inzerzia
teniamo memoria del passo, se la direzione e' coerente si accelera altrimenti si rallenta smorzandone le oscillazioni
Una media mobile esponenziale dei gradienti

#### funzioni di costo per la classificazione
Le funzioni di costo misurano la differenza tra il predicted output e il valopre reale
quantificano "quanto male" va il modello
Nella classification, vogliamo avere una probabilitá di quanto le label siano pertinenti
- Binary cross entropy loss : usata per classification binarie  
- cross entropy loss : per classificazioni multiclasse con K classi.
![[Pasted image 20260215101343.png]]
Per regressione invece usiamo 
- MSE Mean Squared Errore : penalizza gli errori grandi
- MAE Mean Absolut Error : tratta gli errori nello stesso modo
- Huber Loss : combina entrambi, usando MSE per errori piccoli ed MAE per errori grandi

#### Regolarizzazione
La regolarizzazione aiuta i modelli  a generalizzare meglio sui dati non visti, e prevenire overfitting, inseguimento di noise e outlayers e non di segnali
#### Weight decay
ridurre i pesi durante il training, scoraggiando l'uso di parametri troppo elevati
viene implementata aggiungendo una penalita' alla perdita che aumenta con il quadrato dei pesi
il lambda indica quanto controllo sia ha sulla regolarizzazione

#### droupout 
Consiste nello spegnere alcuni neuroni durante il training(solo nel training, nel test si attivano tutti), vengono mantenuti attivi solo i neuroni con una certa probabilita' $p$ 
per fare in modo che non si dipenda troppo da certi pesi specifici




