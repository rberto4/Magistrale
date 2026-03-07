# Lezione 03 – Regressione Lineare
## Outline
1. Stima a minimi quadrati
2. Funzione di costo
3. Gradient descent
4. Proprietà dello stimatore a minimi quadrati
5. Esercizi con codice
---
## 1. Stima a minimi quadrati (Least Squares)
### 1.1 Contesto
Nella lezione precedente abbiamo descritto i dati $\mathcal{D} = \{y(1), y(2), \ldots, y(N)\}$ in termini della loro media e varianza, fornendo gli stimatori:
$$
\hat{\mu} = \frac{1}{N} \sum_{i=1}^{N} y(i) \qquad\qquad S_{N-1}^2 = \frac{1}{N-1} \sum_{i=1}^{N} (y(i) - \hat{\mu})^2
$$
Ora supponiamo di voler descrivere i dati tramite una **relazione lineare** tra variabili di input (features/regressori) e una variabile di output.
### 1.2 Il modello lineare
> **Definizione – Modello lineare:** la relazione tra output $y(i)$ e input $\boldsymbol{\varphi}(i)$ è espressa come:
>
> $$y(i) = \theta_0 + \theta_1 \varphi_1(i) + \cdots + \theta_{d-1} \varphi_{d-1}(i) + \epsilon(i) = \sum_{j=0}^{d-1} \theta_j \varphi_j(i) + \epsilon(i) = \boldsymbol{\varphi}^\top(i) \boldsymbol{\theta} + \epsilon(i)$$
dove:
- $\boldsymbol{\theta} = [\theta_0, \theta_1, \ldots, \theta_{d-1}]^\top \in \mathbb{R}^{d \times 1}$ è il **vettore dei parametri** da stimare.
- $\boldsymbol{\varphi}(i) = [\varphi_0, \varphi_1, \ldots, \varphi_{d-1}]^\top \in \mathbb{R}^{d \times 1}$ è il **vettore delle features** per la $i$-esima osservazione, con $\varphi_0 = 1$ (termine di intercetta).
- $\epsilon(i) \in \mathbb{R}$ è l'**errore** (residuo) dovuto ad una non perfetta spiegazione di $y(i)$ tramite $\boldsymbol{\varphi}(i)$.
L'obiettivo è: dati $N$ dati $\mathcal{D} = \{(\boldsymbol{\varphi}(1), y(1)), \ldots, (\boldsymbol{\varphi}(N), y(N))\}$, trovare la relazione tra le features $\boldsymbol{\varphi}$ e l'output $y$ usando un modello lineare.
---
### 1.3 Interpretazione geometrica
- **Caso scalare** (un solo regressore $\varphi_1$, due parametri $\theta_0, \theta_1$): il modello descrive una **retta** nel piano $(\varphi_1, y)$.
$$
y(i) = \theta_0 + \theta_1 \varphi_1(i) + \epsilon(i)
$$
- **Caso con 2 regressori** ($\varphi_1, \varphi_2$, tre parametri $\theta_0, \theta_1, \theta_2$): il modello descrive un **piano** nello spazio $(\varphi_1, \varphi_2, y)$.
$$
y(i) = \theta_0 + \theta_1 \varphi_1(i) + \theta_2 \varphi_2(i) + \epsilon(i)
$$
In ogni caso, gli errori $\epsilon(i)$ rappresentano la distanza verticale tra il dato osservato e il valore predetto dal modello.
---
## 2. Funzione di costo
### 2.1 Definizione
> **Definizione – Regressione lineare:** la combinazione di un **modello lineare** con il criterio dei **minimi quadrati** (minimizzazione dell'errore quadratico).
Il metodo della regressione lineare stima i parametri $\boldsymbol{\theta}$ minimizzando l'**errore quadratico** tra output osservati e stimati dal modello:
$$
J(\boldsymbol{\theta}) = \frac{1}{N} \sum_{i=1}^{N} \left(y(i) - \boldsymbol{\varphi}^\top(i) \boldsymbol{\theta}\right)^2 = \frac{1}{N} \sum_{i=1}^{N} \epsilon(i)^2
$$
La stima ottima dei parametri è:
$$
\hat{\boldsymbol{\theta}} = \arg\min_{\boldsymbol{\theta}} J(\boldsymbol{\theta})
$$
---
### 2.2 Minimizzazione in forma chiusa
Poiché il modello è **lineare nei parametri** e la misura dell'errore è **quadratica**, la funzione di costo $J(\boldsymbol{\theta})$ è **convessa** e ammette un **minimo unico (globale)**.
Il minimo si trova ponendo il gradiente uguale a zero:
$$
\nabla J(\boldsymbol{\theta}) = \frac{\partial J(\boldsymbol{\theta})}{\partial \boldsymbol{\theta}} = \mathbf{0}
$$
Il calcolo porta a:
$$
\frac{2}{N} \sum_{i=1}^{N} \boldsymbol{\varphi}(i) \cdot \left(y(i) - \boldsymbol{\varphi}^\top(i) \boldsymbol{\theta}\right) = \mathbf{0}
$$
$$
\sum_{i=1}^{N} \boldsymbol{\varphi}(i) \boldsymbol{\varphi}^\top(i) \cdot \boldsymbol{\theta} = \sum_{i=1}^{N} \boldsymbol{\varphi}(i) y(i)
$$
Da cui si ottiene la soluzione in forma chiusa:
$$
\hat{\boldsymbol{\theta}} = \left(\sum_{i=1}^{N} \boldsymbol{\varphi}(i) \boldsymbol{\varphi}^\top(i)\right)^{-1} \cdot \sum_{i=1}^{N} \boldsymbol{\varphi}(i) y(i)
$$
---
### 2.3 Formulazione matriciale
Il problema può essere espresso in forma matriciale definendo:
$$
X = \begin{bmatrix} \boldsymbol{\varphi}^\top(1) \\ \boldsymbol{\varphi}^\top(2) \\ \vdots \\ \boldsymbol{\varphi}^\top(N) \end{bmatrix} = \begin{bmatrix} 1 & \varphi_1(1) & \cdots & \varphi_{d-1}(1) \\ 1 & \varphi_1(2) & \cdots & \varphi_{d-1}(2) \\ \vdots & \vdots & \ddots & \vdots \\ 1 & \varphi_1(N) & \cdots & \varphi_{d-1}(N) \end{bmatrix} \in \mathbb{R}^{N \times d}
$$
$$
\boldsymbol{\theta} = \begin{bmatrix} \theta_0 \\ \theta_1 \\ \vdots \\ \theta_{d-1} \end{bmatrix} \in \mathbb{R}^{d \times 1}, \qquad Y = \begin{bmatrix} y(1) \\ y(2) \\ \vdots \\ y(N) \end{bmatrix} \in \mathbb{R}^{N \times 1}, \qquad E = \begin{bmatrix} \epsilon(1) \\ \epsilon(2) \\ \vdots \\ \epsilon(N) \end{bmatrix} \in \mathbb{R}^{N \times 1}
$$
Il modello si scrive come:
$$
Y = X\boldsymbol{\theta} + E
$$
La funzione di costo diventa:
$$
J(\boldsymbol{\theta}) = \frac{1}{N} \|Y - X\boldsymbol{\theta}\|_2^2 = \frac{1}{N} (Y - X\boldsymbol{\theta})^\top (Y - X\boldsymbol{\theta})
$$
Espandendo:
$$
J(\boldsymbol{\theta}) = \frac{1}{N} \left(Y^\top Y - 2\boldsymbol{\theta}^\top X^\top Y + \boldsymbol{\theta}^\top X^\top X \boldsymbol{\theta}\right)
$$
### 2.4 Regole di derivazione matriciale utili
Per calcolare il gradiente, si usano le seguenti proprietà:
$$
\nabla_{\mathbf{x}} (\mathbf{x}^\top \mathbf{b}) = \mathbf{b}
$$
$$
\nabla_{\mathbf{x}} (\mathbf{x}^\top A \mathbf{x}) = (A + A^\top) \mathbf{x}
$$
### 2.5 Normal equations
Ponendo $\nabla J(\boldsymbol{\theta}) = \mathbf{0}$:
$$
\frac{1}{N} \left(-2X^\top Y + 2X^\top X \boldsymbol{\theta}\right) = \mathbf{0}
$$
Si ottengono le **equazioni normali** (normal equations):
$$
\boxed{\hat{\boldsymbol{\theta}} = (X^\top X)^{-1} X^\top Y}
$$
Questa è la **formula chiusa** per la stima ai minimi quadrati dei parametri di un modello lineare.
### 2.6 Invertibilità di $X^\top X$
Se $X^\top X$ **non è invertibile**, si usa la **pseudo-inversa**. In MATLAB: `theta_hat = pinv(X'*X)*X'*Y`.
Cause di non invertibilità:
- **Regressori ridondanti** (linearmente dipendenti), ad es. $\varphi_1 = $ altezza in metri e $\varphi_2 = $ altezza in feet.
- **Troppi regressori** rispetto alle osservazioni ($N \leq d$).
Soluzioni:
- Rimuovere regressori ridondanti.
- Usare **regolarizzazione** (trattata in lezioni successive).
**Nota:** il metodo delle normal equations è **lento** se $d$ è molto grande. Per risolvere questo problema si usano metodi iterativi come il **gradient descent**.
---
## 3. Gradient Descent
### 3.1 Definizione
> **Definizione – Gradient descent:** metodo iterativo per minimizzare funzioni differenziabili (funzioni in cui possiamo calcolare le derivate in ogni punto del dominio).
### 3.2 Caso scalare
Se abbiamo un solo parametro $\theta \in \mathbb{R}$ da stimare, dato un valore iniziale $\hat{\theta}^{(0)}$, la stima all'iterazione $k+1$ è:
$$
\hat{\theta}^{(k+1)} = \hat{\theta}^{(k)} - \alpha \cdot \frac{\partial J(\theta)}{\partial \theta} \bigg|_{\theta = \hat{\theta}^{(k)}}
$$
dove $\alpha \in \mathbb{R}_{>0}$ è il **learning rate** (tasso di apprendimento).
**Funzionamento intuitivo:**
- Se la derivata è **positiva** (costo crescente) nel punto corrente, la nuova stima si sposta verso **sinistra** (valore più piccolo), avvicinandosi al minimo.
- Se la derivata è **negativa** (costo decrescente) nel punto corrente, la nuova stima si sposta verso **destra** (valore più grande), avvicinandosi al minimo.
- L'anti-gradiente ($-\nabla J$) indica sempre la **direzione di massima discesa** della funzione.
### 3.3 Caso multivariabile
Nel caso generale, si stima un vettore $\boldsymbol{\theta} \in \mathbb{R}^{d \times 1}$ e la derivata scalare è sostituita dal **vettore gradiente** $\nabla J(\boldsymbol{\theta}) \in \mathbb{R}^{d \times 1}$:
$$
\hat{\boldsymbol{\theta}}^{(k+1)} = \hat{\boldsymbol{\theta}}^{(k)} - \alpha \cdot \nabla J(\boldsymbol{\theta}) \bigg|_{\boldsymbol{\theta} = \hat{\boldsymbol{\theta}}^{(k)}}
$$
### 3.4 Normalizzazione dei regressori (trick computazionale)
Quando sono presenti più regressori (caso multivariabile), è utile **normalizzarne i valori** affinché il gradient descent converga più velocemente e in modo più stabile.
**Procedura di normalizzazione** per ogni regressore $j = 1, \ldots, d-1$ (escluso quello dell'intercetta):
1. **Calcolo della media** di ogni regressore:
$$
\hat{\mu}_j = \frac{1}{N} \sum_{i=1}^{N} \varphi_j(i)
$$
2. **Calcolo della varianza** di ogni regressore:
$$
\hat{\sigma}_j^2 = \frac{1}{N} \sum_{i=1}^{N} (\varphi_j(i) - \hat{\mu}_j)^2
$$
3. **Normalizzazione** (sottrazione della media e divisione per la deviazione standard):
$$
\varphi_j(i) \leftarrow \frac{\varphi_j(i) - \hat{\mu}_j}{\hat{\sigma}_j}
$$
**Importante:** quando si normalizzano nuovi dati (non usati per la stima), si usano **la stessa media e la stessa varianza** calcolate sul dataset di addestramento.
**Effetto geometrico:** senza normalizzazione, le curve di livello della funzione di costo possono essere molto "allungate" (ellissi con eccentricità elevata), rendendo il gradient descent lento. Con la normalizzazione, le curve di livello diventano più circolari e il gradient descent converge più rapidamente.
---
## 4. Proprietà dello stimatore a minimi quadrati
### 4.1 Ipotesi sul sistema vero
Supponiamo che il sistema vero (che genera i dati) sia **effettivamente lineare**:
$$
y(i) = \boldsymbol{\varphi}^\top(i) \boldsymbol{\theta}_0 + \epsilon(i)
$$
dove $\epsilon(i)$ è una variabile casuale a **media nulla**, con varianza $\lambda^2$.
**Nota:** non si assume nessuna specifica distribuzione di probabilità su $\epsilon(i)$ (non si richiede che sia Gaussiano, ad esempio).
### 4.2 Proprietà
Con le ipotesi di cui sopra, lo stimatore a minimi quadrati gode delle seguenti proprietà:
> **Proprietà 1 – Correttezza:** lo stimatore è **corretto** (non distorto):
>
> $$\mathbb{E}[\hat{\boldsymbol{\theta}}] = \boldsymbol{\theta}_0$$
Lo stimatore ai minimi quadrati fornisce, in media, il valore vero dei parametri.
> **Proprietà 2 – Consistenza:** supponendo inoltre che i rumori siano **incorrelati** ($\mathbb{E}[\epsilon(i)\epsilon(j)] = 0, \; \forall \, i \neq j$), lo stimatore è **consistente** con matrice di covarianza:
>
> $$\text{Var}(\hat{\boldsymbol{\theta}}) = \lambda^2 \cdot (X^\top X)^{-1} = \lambda^2 \cdot P$$
dove $P = (X^\top X)^{-1}$. Al crescere del numero di dati $N$, la varianza tende a zero e la stima converge al valore vero.
---
## 5. Calcolo e implementazione del gradiente
### 5.1 Caso con un regressore
Supponiamo che il modello sia $y = \theta_0 + \theta_1 \cdot \varphi + \epsilon$, con funzione di costo:
$$
J(\theta_0, \theta_1) = \frac{1}{N} \sum_{i=1}^{N} \left(y(i) - \theta_0 - \theta_1 \cdot \varphi(i)\right)^2
$$
Il gradiente ha due componenti:
$$
\nabla J(\theta_0, \theta_1) = \begin{bmatrix} \frac{\partial J}{\partial \theta_0} \\ \frac{\partial J}{\partial \theta_1} \end{bmatrix}
$$
$$
\frac{\partial J}{\partial \theta_0} = \frac{2}{N} \sum_{i=1}^{N} \left(y(i) - \theta_0 - \theta_1 \varphi(i)\right) \cdot (-1) = -\frac{2}{N} X(:,1)^\top (Y - X\boldsymbol{\theta})
$$
$$
\frac{\partial J}{\partial \theta_1} = \frac{2}{N} \sum_{i=1}^{N} \left(y(i) - \theta_0 - \theta_1 \varphi(i)\right) \cdot (-\varphi(i)) = -\frac{2}{N} X(:,2)^\top (Y - X\boldsymbol{\theta})
$$
### 5.2 Caso generale (più regressori)
Con un vettore $\boldsymbol{\varphi} = [1, \varphi_1, \varphi_2, \ldots, \varphi_{d-1}]^\top \in \mathbb{R}^{d \times 1}$, l'aggiornamento di ogni parametro nel gradient descent è:
$$
\theta_j = \theta_j - \alpha \cdot \frac{2}{N} \sum_{i=1}^{N} \left(y(i) - \boldsymbol{\varphi}^\top(i) \boldsymbol{\theta}\right) \cdot (-\varphi_j(i)) \qquad j = 0, 1, \ldots, d-1
$$
In forma matriciale compatta, il gradiente è:
$$
\nabla J(\boldsymbol{\theta}) = -\frac{2}{N} X^\top (Y - X\boldsymbol{\theta})
$$
e l'aggiornamento diventa:
$$
\boldsymbol{\theta} \leftarrow \boldsymbol{\theta} + \alpha \cdot \frac{2}{N} X^\top (Y - X\boldsymbol{\theta})
$$
---
## 6. Esercizi applicativi
### 6.1 Esercizio 1 – Stima dei profitti di un ristorante
**Problema:** il CEO di un franchising di ristoranti valuta diverse città per l'apertura di un nuovo ristorante. Sono disponibili dati di profitti e popolazione per città esistenti.
- Feature: $\varphi_1$ = popolazione (in 10000 unità)
- Output: $y$ = profitto (in 10000$)
- Dataset: $N = 97$ città
Si applica la regressione lineare con un solo regressore per stimare la retta che mappa la popolazione nel profitto.
### 6.2 Esercizio 2 – Stima dei prezzi delle case
**Problema:** stimare il prezzo delle case a Portland, Oregon.
- Features: $\varphi_1$ = area in $\text{feet}^2$, $\varphi_2$ = numero di camere da letto
- Output: $y$ = prezzo
- Dataset: $N = 47$ case
$$
\boldsymbol{\varphi}(i) = [1, \; \varphi_1(i), \; \varphi_2(i)]^\top \in \mathbb{R}^{3 \times 1}
$$
$$
X \in \mathbb{R}^{47 \times 3}, \quad \boldsymbol{\theta} \in \mathbb{R}^{3 \times 1}, \quad Y \in \mathbb{R}^{47 \times 1}
$$
**Implementazione MATLAB:**
```matlab
% Lettura dati
data = csvread('ex2data.txt');
X = data(:, 1:2);   % Features
y = data(:, 3);      % Prezzo
N = length(y);       % Numero di dati
% Aggiunta del termine di intercetta
X = [ones(N, 1) X];
% Calcolo dei parametri con le normal equations
theta_hat = pinv(X'*X)*X'*y;
% Stima del prezzo di una casa di 1650 sq-ft, 3 camere
price_hat = [1 3 1650]*theta_hat;
```
Il modello stimato può poi essere usato per predire il prezzo di una casa con caratteristiche non presenti nel dataset (punto non visto durante la stima di $\boldsymbol{\theta}$).
