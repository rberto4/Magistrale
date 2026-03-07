# Lezione 01 – Introduzione

### Parte I – Sistemi statici

1. **Richiami di statistica**
2. **Teoria della stima** – proprietà degli stimatori
3. **Stima a minimi quadrati** – stima di modelli lineari, gradient descent
4. **Stima a massima verosimiglianza** – proprietà della stima, stima di modelli lineari
5. **Regressione logistica** – stima di un modello di regressione logistica
6. **Fondamenti di machine learning** – bias-variance tradeoff, overfitting, regolarizzazione, validazione
7. **Cenni di stima Bayesiana** – probabilità congiunte, marginali e condizionate, connessione con Filtro di Kalman

### Parte II – Sistemi dinamici

8. **Processi stocastici** – processi stocastici stazionari (PSS), rappresentazione spettrale, stimatori campionari media/covarianza, densità spettrale campionaria
9. **Famiglie di modelli a spettro razionale** – modelli per serie temporali (MA, AR, ARMA), modelli per sistemi input/output (ARX, ARMAX)
10. **Predizione** – filtro passa-tutto, forma canonica, teorema della fattorizzazione spettrale, soluzione al problema della predizione
11. **Identificazione** – identificazione di modelli ARX e ARMAX, metodo di Newton
12. **Identificazione: analisi e complementi** – analisi asintotica metodi PEM, identificabilità dei modelli, valutazione dell'incertezza di stima
13. **Identificazione: valutazione del modello**
### Schema concettuale del corso

Il corso affronta la **stima parametrica** $\hat{\boldsymbol{\theta}}$ in due contesti:

- **$\boldsymbol{\theta}$ deterministico**, senza assunzioni sulla distribuzione di probabilità (ddp) dei dati: stima parametri popolazione, stima modello lineare a minimi quadrati.

- **$\boldsymbol{\theta}$ deterministico**, con assunzioni sulla ddp dei dati: stima a massima verosimiglianza, regressione logistica.

- **$\boldsymbol{\theta}$ variabile casuale**, con assunzioni sulla ddp dei dati: stima Bayesiana.

---

## 2. Identificazione dei Modelli

In questo corso si trattano **modelli matematici** per descrivere fenomeni o sistemi.

> **Definizione – Sistema:** un meccanismo astratto che trasforma input (cause) in output (effetti).

> **Definizione – Modello:** una descrizione matematica di un sistema.

Esempi di sistemi:

- **Economici:** relazione tra reddito ed educazione.

- **Sociali:** relazione tra luogo di abitazione e criminalità.

- **Fisici:** relazione tra corrente e tensione.

Un sistema $S$ riceve un input e produce un output reale; un modello $M$ riceve lo stesso input e produce un output simulato/stimato.

---

### 2.1 Tre approcci di modellazione

#### 1) Modellazione White-Box

Basata su **leggi e principi della fisica** o conoscenza a priori. Si scrivono le equazioni del sistema conoscendo direttamente i parametri.

**Esempio:** sistema massa-molla-smorzatore:

$$

m\ddot{x}(t) = f(t) - c\dot{x}(t) - k \cdot x(t)

$$

dove $m$ è la massa, $c$ lo smorzamento e $k$ la costante elastica.

**Vantaggi:**

- Si conosce il significato fisico delle variabili.

- Il modello è generalizzabile.

**Svantaggi:**

- Richiede di conoscere tutte le leggi e il valore dei parametri.

- Approccio costoso in termini di tempo.

- Non fattibile per sistemi complessi, con molti componenti.

#### 2) Modellazione Black-Box

Basata esclusivamente su **dati sperimentali**. Si effettua un esperimento ingresso/uscita e si identificano (stimano) i parametri di un modello generico di ordine adeguato.

**Esempio:** per lo stesso sistema massa-molla-smorzatore, si identifica un modello digitale generico:

$$

\hat{G}(z) = \frac{b_0 + b_1 z^{-1} + b_2 z^{-2}}{1 + a_1 z^{-1} + a_2 z^{-2}}

$$

senza basarsi sulla conoscenza fisica del sistema.

**Vantaggi:**

- Prescindono dal particolare problema, caratterizzando solo la relazione ingresso-uscita.

- Veloci da costruire.

**Svantaggi:**

- Non interpretabili fisicamente.

- Non generali: se il sistema cambia, l'esperimento va ripetuto.

#### 3) Modellazione Gray-Box

Approccio **ibrido**: si conoscono le equazioni del sistema (dalla fisica), ma si **identificano** tutti o alcuni parametri dai dati sperimentali.

**Esempio:** si conosce la struttura dell'equazione $m\ddot{x}(t) = f(t) - c\dot{x}(t) - kx(t)$, ma i valori di $m$, $c$, $k$ vengono stimati dai dati.

**Vantaggi:**

- Conoscenza del significato fisico delle variabili.

- Più veloce rispetto al white-box.

**Svantaggi:**

- Più lento rispetto al black-box.

---

### 2.2 Bontà di un modello

> Un modello è "buono" se **l'errore di modellazione** (differenza tra output reale e output simulato dal modello) è piccolo.

Se gli output reali (misurati) e quelli simulati dal modello (calcolati) sono simili, il modello è in grado di replicare il fenomeno reale.

Il corso si concentra sulla stima di **modelli black-box**, considerando sia **sistemi statici** che **sistemi dinamici**.

In conclusione: **identificazione dei modelli = risolvere un problema di stima** (stima di un modello che descriva i dati).

---

## 3. Analisi dei Dati

L'analisi dei dati ha due obiettivi principali:

### Obiettivo 1 – Statistica Descrittiva

Determinare le **caratteristiche statistiche** dei dati e delle variabili misurate (affetti da rumore e incertezza):

- Media

- Varianza

- Correlazione

- Distribuzione di probabilità

### Obiettivo 2 – Machine Learning

Individuare **regolarità (pattern)** nei dati:

- I dati presentano pattern riconoscibili o sono random?

- Possiamo allenare algoritmi che, da soli, individuino questi pattern?

Le tematiche di identificazione e analisi dei dati sono **collegate**: l'analisi preliminare dà indicazioni sul modello migliore; tecniche di analisi valutano la bontà del modello; una rappresentazione probabilistica gestisce l'incertezza nelle misure e nella conoscenza della realtà.

---

## 4. Approccio supervisionato alla stima di un modello

Le tecniche di stima (apprendimento, identificazione) si classificano in:

### Apprendimento supervisionato (Supervised Learning)

> **Definizione:** stimare uno o più output $y$ sulla base di uno o più input $\boldsymbol{\varphi}$.

L'obiettivo è stimare (imparare, identificare) la **funzione ignota** $f(\boldsymbol{\varphi})$ che mappa gli input $\boldsymbol{\varphi}$ nell'output $y$, affinché $y = f(\boldsymbol{\varphi})$.

- L'**input** è rappresentato da un vettore $\boldsymbol{\varphi} = [\varphi_0, \varphi_1, \ldots, \varphi_{d-1}]^\top \in \mathbb{R}^{d \times 1}$, chiamato **vettore dei regressori** (o delle features).

- Ogni elemento $\varphi_j$ è chiamato **regressore** o **feature**.

L'**output** $y$ può essere:

- Un **numero** (output continuo), cioè $y \in \mathbb{R}$ → **Regressione**

- Una **categoria** (output discreto), cioè $y \in \{\text{Cat. 1}, \ldots, \text{Cat. C}\}$ → **Classificazione**

### Apprendimento non supervisionato (Unsupervised Learning)

Non c'è l'output. L'obiettivo è scoprire relazioni e strutture nel solo input.

Il corso considera **solo le tecniche supervisionate** (regressione e classificazione).

---

## 5. Sistemi (e modelli) statici

> **Definizione – Sistema statico:** un sistema per cui la sola conoscenza delle variabili di input è sufficiente a determinare il valore dell'output.

**Esempio:** legge di Ohm per un resistore:

$$

i(t) = \frac{V(t)}{R}

$$

L'uscita $i(t)$ all'istante $t$ dipende **solo** dall'ingresso $V(t)$ al medesimo istante $t$.

Nel corso, le tematiche di **Machine Learning** sono quelle tecniche che permettono di stimare (apprendere) sistemi statici.

### Esempio 1 – Stimare il prezzo delle case (Regressione)

- **Feature** $\varphi$: grandezza della casa in $m^2$.

- **Output** $y$: prezzo della casa.

- Si utilizza un **dataset** $\mathcal{D} = \{(\boldsymbol{\varphi}_i, y_i)\}_{i=1}^{N}$ di osservazioni.

- L'output $y$ è continuo ($y \in \mathbb{R}$), quindi è un problema di **regressione**.

- Il dataset può avere **più features** (area, numero camere da letto, ecc.) e il vettore dei regressori diventa multidimensionale: $\boldsymbol{\varphi} \in \mathbb{R}^{2 \times 1}$.

### Esempio 2 – Image Classification (Classificazione)

- **Input:** un'immagine.

- **Output:** una classe di appartenenza (es. "Gatto" / "Non gatto").

- L'output $y$ è una **categoria**, quindi è un problema di **classificazione**.

---

## 6. Sistemi (e modelli) dinamici

> **Definizione – Sistema dinamico:** un sistema per cui la sola conoscenza delle variabili di input (in un certo istante di tempo) **non è sufficiente** a determinare il valore dell'output al medesimo istante. Servono anche delle condizioni iniziali.

I sistemi dinamici coinvolgono il **tempo**: l'output $y(t)$ dipende da sé stesso a istanti passati $y(t-1), y(t-2), \ldots, y(t-n_a)$.

I modelli dinamici consentono di descrivere l'**evoluzione futura** delle variabili in funzione del loro andamento passato e delle variabili esterne (ingressi esogeni). Questa dipendenza dal passato conferisce al modello una **"memoria"** (la dinamica).

### Tempo continuo vs. tempo discreto

I fenomeni naturali e fisici sono intrinsecamente continui. In questo caso il sistema è descritto da **equazioni differenziali**:

$$

\dot{y}(t) = -2 \cdot y(t) + 3 \cdot u(t)

$$

La derivata rappresenta matematicamente il comportamento futuro di una funzione.

Tuttavia, il computer gestisce solo una quantità limitata di dati, quindi i segnali devono essere **campionati** con un tempo di campionamento $T_s$:

$$

y(t) = y(t \cdot T_s), \quad t = 1, \ldots, N

$$

L'evoluzione a tempo discreto è descritta da **equazioni alle differenze**:

$$

y(t) = -0.5 \cdot y(t-1) + 3 \cdot u(t)

$$

Con l'equazione alle differenze è evidente che $y(t)$ dipende dai suoi valori precedenti (e dall'input $u(t)$).

### Due set di dati

I dati vengono raccolti campionando i segnali a istanti temporali $t = 1, 2, \ldots, N$:

- Dati di input: $u(1), u(2), \ldots, u(N)$

- Dati di output: $y(1), y(2), \ldots, y(N)$

### Approccio alla stima di modelli dinamici

La stima di modelli dinamici è formulata come per i sistemi statici. L'unica differenza è nella definizione del **vettore dei regressori**. Poiché l'uscita dipende dai segnali di ingresso e di uscita, il vettore dei regressori in un determinato momento $t$ è:

$$

\boldsymbol{\varphi}(t) = \begin{bmatrix} y(t-1) & \cdots & y(t-n_a) & u(t) & \cdots & u(t-n_b) \end{bmatrix}^\top

$$

### Motivazioni per l'uso di modelli dinamici

1. **Progettazione del controllo:** conoscere la funzione di trasferimento $G(s)$ o $G(z)$ del sistema per tarare un controllore opportuno.

2. **Simulazione:** simulare al computer la risposta (output) di un modello a determinati input, per comprendere il comportamento del sistema.

3. **Diagnosi dei guasti:** confrontando segnali misurati con segnali simulati dal modello, è possibile individuare guasti su attuatori, sensori o sul processo.

### Esempio 1 – Controllo lettore laser CD

Obiettivo: posizionare la testina laser sulla traccia corretta tramite un braccio meccanico.

- Il modello white-box (doppio integratore $\hat{G}(s) = \frac{k}{Js^2}$) non è sufficientemente corretto.

- Tramite un esperimento di identificazione, si individua un modello che rivela **modi flessibili**, impossibili da modellare solo con leggi fisiche.

### Esempio 2 – Ricezione segnale telefonia mobile

Il segnale ricevuto è composto da versioni ritardate del segnale emesso e da rumore:

$$

y(t) = g_1 u(t-n_1) + g_2 u(t-n_2) + \cdots + v(t) = G_0(z) u(t) + v(t)

$$

Il modello del canale $G_0(z)$ non è noto perché dipende dalla posizione del telefono. Il software GSM **identifica** $\hat{G}(z)$ a ogni chiamata, usando un segnale noto, per poi ricostruire il segnale di interesse.

---

## 7. Riassunto

| | **Sistemi statici** | **Sistemi dinamici** |

|---|---|---|

| **Input** | Features del problema $\boldsymbol{\varphi}(i)$ | Valori passati di input e output $\boldsymbol{\varphi}(t)$ |

| **Indice** | Osservazioni indicizzate con $i$ | Osservazioni indicizzate con $t$ |

| **Terminologia** | Apprendimento (model learning) | Identificazione (system identification) |

| **Obiettivo** | Stimare $f(\cdot)$ dai dati | Stimare $f(\cdot)$ dai dati |

La **stima di sistemi dinamici** (system identification) è un problema di **apprendimento supervisionato**, nello specifico un problema di **regressione** (l'output è un valore continuo $y \in \mathbb{R}$).

# Lezione 02 – Richiami di Statistica

## Outline

1. Definizione e proprietà delle variabili casuali: caso scalare

2. Definizione e proprietà delle variabili casuali: caso multivariabile

3. Stima e stimatori

4. Proprietà degli stimatori

---

## 1. Variabili casuali – Caso scalare

### 1.1 Definizione di variabile casuale

> **Definizione – Variabile casuale (v.c.):** una variabile casuale $v$ è una variabile definita a partire dall'esito $s$ di un esperimento casuale. Formalmente, è una funzione definita sull'insieme degli esiti $\mathcal{S}$ che, ad ogni esito $s_i$, restituisce un numero reale:

>

> $$v(\cdot): \mathcal{S} \to \mathbb{R}$$

- Si indica una v.c. come $v(s)$.

- Il valore assunto da una v.c. $v$ a seguito di un particolare esito $\bar{s}$ è $v(\bar{s})$.

- Si assegna una **probabilità** che ogni esito accada. Questo influisce sulla probabilità che $v$ assuma i valori che può assumere (**distribuzione di probabilità**).

**Esempio:** l'esperimento è il lancio di una moneta. A seconda se l'esito è $s = \text{testa}$ o $s = \text{croce}$, la variabile $v$ assume un valore diverso:

$$

v = \begin{cases} 1 & s = \text{testa} \\ 0 & s = \text{croce} \end{cases}

$$

---

### 1.2 Variabili casuali discrete

> **Definizione – Funzione di probabilità di massa (pmf):** $p(x) = P(v = x)$.

La pmf associa ad ogni valore $x$ di $v$ una probabilità. Se $v$ può assumere $m$ diversi valori $x_1, x_2, \ldots, x_m$, allora:

$$

\sum_{i=1}^{m} p(x_i) = 1

$$

**Esempio:** lancio di un dado a 6 facce. La variabile casuale $v$ può assumere valori $x_1 = 1, x_2 = 2, \ldots, x_6 = 6$, con $m = 6$. Ogni valore ha probabilità $p(x_i) = \frac{1}{6}$.

---

### 1.3 Variabili casuali continue

Quando $v$ assume valori continui, dire $P(v = x)$ non ha senso: dato che $v$ può assumere infiniti valori, la probabilità che assuma esattamente un valore specifico è $P(v = x) = 0$.

> **Definizione – Funzione di densità di probabilità (pdf):** $f_v(x)$ è la funzione che definisce la probabilità che $v$ appartenga ad un intervallo di valori $[a, b]$:

>

> $$P(v \in [a, b]) = \int_a^b f_v(x) \, dx$$

Proprietà della pdf:

- $f_v(x) \geq 0$ per ogni $x$

- $\int_{-\infty}^{+\infty} f_v(x) \, dx = 1$

> **Definizione – Funzione di densità cumulata (cdf):**

>

> $$F_v(z) = \int_{-\infty}^{z} f_v(x) \, dx = P(v \leq z)$$

La cdf restituisce la probabilità che la variabile casuale $v$ assuma un valore minore o uguale a $z$.

---

### 1.4 Valore atteso

> **Definizione – Valore atteso:** il valore atteso di una variabile casuale $v$ è la somma pesata dei valori $x$ che $v$ può assumere, dove i pesi sono la probabilità di osservare il valore $x$:

>

> $$\mathbb{E}[v] = \int_{-\infty}^{+\infty} x \cdot f_v(x) \, dx$$

L'operatore valore atteso $\mathbb{E}_s[v]$ considera tutti i possibili esiti $s$ della variabile casuale $v$.

**Proprietà di linearità del valore atteso:**

$$

\mathbb{E}[\alpha \cdot v_1 + \beta \cdot v_2 + \gamma] = \alpha \cdot \mathbb{E}[v_1] + \beta \cdot \mathbb{E}[v_2] + \gamma \qquad \forall \, \alpha, \beta, \gamma \in \mathbb{R}

$$

Questa proprietà è fondamentale: il valore atteso di una combinazione lineare di variabili casuali è la combinazione lineare dei singoli valori attesi.

---

### 1.5 Varianza

> **Definizione – Varianza:** la varianza di una variabile casuale $v$ misura quanto i valori $x$ si discostano dalla loro media:

>

> $$\text{Var}(v) = \int_{-\infty}^{+\infty} (x - \mathbb{E}[v])^2 \cdot f_v(x) \, dx$$

Osservazioni importanti:

- Se la varianza è piccola, $v$ assume valori molto vicini fra loro.

- $\text{Var}(v) \geq 0$. Se $\text{Var}(v) = 0$, la variabile $v$ è **deterministica** (assume sempre un solo valore).

> **Definizione – Deviazione standard:** $\sigma_v = \sqrt{\text{Var}(v)}$.

**Formula alternativa della varianza** (utile nei calcoli):

$$

\text{Var}(v) = \mathbb{E}[v^2] - (\mathbb{E}[v])^2

$$

Dimostrazione:

$$

\text{Var}(v) = \mathbb{E}[(v - \mathbb{E}[v])^2] = \mathbb{E}[v^2 - 2\mathbb{E}[v] \cdot v + (\mathbb{E}[v])^2] = \mathbb{E}[v^2] - 2\mathbb{E}[v] \cdot \mathbb{E}[v] + (\mathbb{E}[v])^2 = \mathbb{E}[v^2] - (\mathbb{E}[v])^2

$$

**Proprietà di scala della varianza:**

$$

\text{Var}(\alpha \cdot v + \beta) = \alpha^2 \cdot \text{Var}(v) \qquad \forall \, \alpha, \beta \in \mathbb{R}

$$

Si noti che la costante additiva $\beta$ non influisce sulla varianza (la varianza misura la dispersione, non la posizione), mentre il fattore moltiplicativo $\alpha$ entra al quadrato.

---

### 1.6 Correlazione

> **Definizione – Coefficiente di correlazione:** date due variabili casuali $v_1$ e $v_2$, il coefficiente di correlazione è:

>

> $$\rho(v_1, v_2) = \frac{\mathbb{E}[(v_1 - \mathbb{E}[v_1]) \cdot (v_2 - \mathbb{E}[v_2])]}{\sigma_{v_1} \cdot \sigma_{v_2}}$$

- $\rho$ indica il **grado di dipendenza lineare** tra $v_1$ e $v_2$.

- Se $v_2 = \alpha v_1 + \beta$ (relazione lineare perfetta), si ha $\rho = 1$.

- Se $\rho = 0$, le due variabili si dicono **scorrelate** (nessuna relazione lineare).

---

### 1.7 Covarianza

> **Definizione – Covarianza:** date due variabili casuali $v_1$ e $v_2$:

>

> $$\text{Cov}(v_1, v_2) = \mathbb{E}[(v_1 - \mathbb{E}[v_1]) \cdot (v_2 - \mathbb{E}[v_2])]$$

La relazione tra covarianza e correlazione è:

$$

\rho(v_1, v_2) = \frac{\text{Cov}(v_1, v_2)}{\sigma_{v_1} \cdot \sigma_{v_2}}

$$

Le variabili casuali $v_1$ e $v_2$ sono **scorrelate** se $\text{Cov}(v_1, v_2) = 0$.

---

## 2. Variabili casuali – Caso multivariabile

Le definizioni precedenti si estendono al caso di un **vettore di variabili casuali**:

$$

\mathbf{v} = [v_1, v_2, \ldots, v_d]^\top \in \mathbb{R}^{d \times 1}

$$

### 2.1 Funzione di densità cumulata congiunta

> **Definizione – CDF congiunta:**

>

> $$F_{\mathbf{v}}(z_1, z_2, \ldots, z_d) = P(v_1 \leq z_1, v_2 \leq z_2, \ldots, v_d \leq z_d) = \int_{-\infty}^{z_1} \int_{-\infty}^{z_2} \cdots \int_{-\infty}^{z_d} f_{v_1, v_2, \ldots, v_d}(x_1, x_2, \ldots, x_d) \, dx_1 \, dx_2 \cdots dx_d$$

dove $f_{v_1, v_2, \ldots, v_d}$ è la **pdf congiunta**.

### 2.2 Valore atteso vettoriale

Il valore atteso è un **vettore colonna** di $d$ componenti:

$$

\mathbb{E}[\mathbf{v}] = [\mathbb{E}[v_1], \mathbb{E}[v_2], \ldots, \mathbb{E}[v_d]]^\top \in \mathbb{R}^{d \times 1}

$$

### 2.3 Matrice di varianza-covarianza

La varianza nel caso multivariabile è una **matrice** $d \times d$ **semidefinita positiva e simmetrica**:

$$

\text{Var}(\mathbf{v}) = \int_{\mathbb{R}^d} (\mathbf{x} - \mathbb{E}[\mathbf{v}])(\mathbf{x} - \mathbb{E}[\mathbf{v}])^\top f_{\mathbf{v}}(\mathbf{x}) \, d\mathbf{x} = \begin{bmatrix} \text{Var}(v_1) & \cdots & \text{Cov}(v_1, v_d) \\ \vdots & \ddots & \vdots \\ \text{Cov}(v_d, v_1) & \cdots & \text{Var}(v_d) \end{bmatrix}

$$

> **Definizione – Matrice semidefinita positiva:** una matrice reale e simmetrica $M$ è semidefinita positiva se $\mathbf{z}^\top M \mathbf{z} \geq 0$ per ogni $\mathbf{z} \in \mathbb{R}^d$. Equivalentemente, tutti i suoi autovalori sono $\geq 0$.

Sulla diagonale principale si trovano le varianze delle singole variabili; gli elementi fuori diagonale rappresentano le covarianze tra coppie di variabili.

---

### 2.4 Indipendenza

> **Definizione – Indipendenza:** due variabili casuali $v_1$ e $v_2$ con funzione di probabilità congiunta $f_{v_1, v_2}(x_1, x_2)$ si dicono **indipendenti** se:

>

> $$f_{v_1, v_2}(x_1, x_2) = f_{v_1}(x_1) \cdot f_{v_2}(x_2)$$

La pdf congiunta si fattorizza nel prodotto delle pdf marginali.

**Nota importante:** se due variabili sono **indipendenti**, allora sono anche **scorrelate**. Il viceversa **non vale** in generale: due variabili possono essere scorrelate ma dipendenti (ad esempio con dipendenza non lineare).

---

## 3. Stima e stimatori

### 3.1 Il problema della stima parametrica

Per gestire l'incertezza presente nei dati (es. rumore di misura), i dati vengono interpretati come **variabili casuali**. I dati osservati sono i valori assunti dalle variabili casuali.

Il problema della **stima parametrica** consiste nello stimare il vettore di parametri $\boldsymbol{\theta}_0$ che ha generato i dati $\mathcal{D} = \{y(1), \ldots, y(N)\}$.

I dati $\mathcal{D}$ dipendono sia dall'esito $s$ (l'aleatorietà), sia dai parametri $\boldsymbol{\theta}_0$:

$$

\mathcal{D} = \mathcal{D}(s, \boldsymbol{\theta}_0)

$$

I dati osservati dipendono da uno specifico esito $\bar{s}$: $\mathcal{D} = \mathcal{D}(\bar{s}, \boldsymbol{\theta}_0)$.

**Esempio:** lancio di una moneta. Osserviamo $N = 8$ dati $\mathcal{D} = \{1, 0, 0, 1, 1, 1, 0, 1\}$. Il parametro di interesse $\theta_0$ è la probabilità che esca testa.

---

### 3.2 Stimatore e stima

> **Definizione – Stimatore:** uno stimatore è una funzione $T(\mathcal{D}(s, \boldsymbol{\theta}_0))$ dei dati, ovvero una funzione di variabili casuali.

> **Definizione – Stima:** la stima è il risultato di uno stimatore su una specifica realizzazione dei dati:

>

> $$\hat{\boldsymbol{\theta}} = T(\mathcal{D}(\bar{s}, \boldsymbol{\theta}_0))$$

**Osservazione fondamentale:** poiché il risultato di $T$ dipende dall'esito $s$ (dal quale dipendono i dati), lo **stimatore è esso stesso una variabile casuale** che dipende da $s$. Ha quindi senso parlare di distribuzione di probabilità, valore atteso e varianza dello stimatore.

**Esempio:** stimare l'altezza media degli studenti del corso IMAD. Supponiamo di poter misurare solo $N = 10$ persone.

- Esito $s_1$: primi 10 studenti estratti → stima $\hat{\theta}(s_1)$

- Esito $s_2$: altri 10 studenti estratti → stima $\hat{\theta}(s_2) \neq \hat{\theta}(s_1)$

La stima dipende dall'esito, confermando che lo stimatore è una variabile casuale.

---

## 4. Proprietà degli stimatori

La "bontà" di uno stimatore non si giudica da una singola stima, ma dalle **caratteristiche della sua distribuzione di probabilità**.

### 4.1 Correttezza (non polarizzazione, unbiased)

> **Definizione – Stimatore corretto:** uno stimatore (scalare) $\hat{\theta}$ si dice corretto (non distorto, unbiased) se:

>

> $$\mathbb{E}[\hat{\theta}] = \theta_0$$

dove $\theta_0$ è il valore vero del parametro.

In altre parole, "in media" lo stimatore fornisce il valore vero del parametro.

Il **bias** (distorsione) di uno stimatore è definito come:

$$

\text{bias}(\hat{\theta}) = \mathbb{E}[\hat{\theta}] - \theta_0

$$

Per uno stimatore corretto, il bias è zero.

---

### 4.2 Correttezza asintotica

> **Definizione:** uno stimatore $\hat{\theta}$ si dice **asintoticamente corretto** se:

>

> $$\lim_{N \to +\infty} \mathbb{E}[\hat{\theta}] = \theta_0$$

È una proprietà **più debole** rispetto alla correttezza: lo stimatore converge al valore vero solo al crescere del numero di dati $N$.

---

### 4.3 Consistenza

> **Definizione – Consistenza:** uno stimatore $\hat{\theta}$ si dice **consistente** se, per $N \to +\infty$, $\hat{\theta}$ converge a $\theta_0$ in probabilità:

>

> $$\lim_{N \to +\infty} P\left(|\hat{\theta} - \theta_0| \geq \varepsilon\right) = 0, \qquad \forall \, \varepsilon > 0$$

Al crescere di $N$, la stima diventa sempre più precisa: la probabilità di commettere un errore $\geq \varepsilon$ tende a zero. La distribuzione dello stimatore si "restringe" attorno al valore vero.

**Convergenza in media quadratica** (proprietà più forte che implica la consistenza):

$$

\lim_{N \to +\infty} \mathbb{E}\left[(\hat{\theta} - \theta_0)^2\right] = 0

$$

---

### 4.4 Stimatore a minima varianza

Se due stimatori sono entrambi corretti, è migliore quello a **minima varianza** (più preciso, meno disperso). Questo criterio vale per $N$ finito (senza dover ricorrere a proprietà asintotiche).

---

### 4.5 Limite di Cramér-Rao

> **Definizione – Limite di Cramér-Rao:** dato uno stimatore corretto $\hat{\theta}$, la sua varianza non può essere resa più piccola di una certa quantità:

>

> **Caso scalare:**

> $$\text{Var}(\hat{\theta}) \geq \frac{1}{m}$$

>

> **Caso vettoriale:**

> $$\text{Var}(\hat{\boldsymbol{\theta}}) \succeq M^{-1}$$

>

> dove $\text{Var}(\hat{\boldsymbol{\theta}}) - M^{-1}$ è semidefinita positiva.

La quantità $m$ (scalare) o $M$ (matrice) è detta **informazione di Fisher**. Rappresenta la quantità di informazione che i dati contengono riguardo ai parametri.

**Intuizione:** esiste sempre un certo livello di incertezza nei dati (rumore) che non può essere rimosso. I dati non sono mai "informativi al 100%". Di conseguenza, esistono dei **limiti strutturali** alla precisione della stima.

---

### 4.6 Efficienza

> **Definizione – Stimatore efficiente:** uno stimatore $\hat{\theta}$ si dice **efficiente** se raggiunge il limite di Cramér-Rao:

>

> $$\text{Var}(\hat{\theta}) = \frac{1}{m}$$

> **Definizione – Stimatore asintoticamente efficiente:** uno stimatore $\hat{\theta}$ si dice **asintoticamente efficiente** se:

>

> $$\lim_{N \to +\infty} \text{Var}(\hat{\theta}) = \frac{1}{m}$$

> **Definizione – Stimatore a minima varianza:** uno stimatore corretto $\hat{\theta}_m$ si dice **a minima varianza** se $\text{Var}(\hat{\theta}_m) \leq \text{Var}(\hat{\theta})$ per qualsiasi altro stimatore corretto $\hat{\theta}$.

**Relazione:** se $\hat{\theta}$ è efficiente, allora è a minima varianza. Il viceversa **non vale**: ci sono casi in cui esistono stimatori a minima varianza che non sono efficienti (quando nessuno stimatore raggiunge il limite di Cramér-Rao).

---

### 4.7 Mean Squared Error (MSE)

Per stimatori **non corretti** (distorti), la varianza da sola non è sufficiente come criterio di bontà, perché uno stimatore potrebbe avere varianza piccola ma essere sistematicamente lontano dal valore vero.

> **Definizione – Errore quadratico medio (MSE):** indicatore globale che considera sia il bias sia la varianza:

>

> $$\text{MSE} = \mathbb{E}\left[(\hat{\theta} - \theta_0)^2\right]$$

**Proprietà fondamentale – Decomposizione Bias-Varianza:**

$$

\text{MSE} = (\text{bias}(\hat{\theta}))^2 + \text{Var}(\hat{\theta})

$$

Questa decomposizione è nota come **dilemma bias-varianza** (bias-variance tradeoff). L'MSE è la somma del quadrato del bias e della varianza: uno stimatore ottimale deve bilanciare queste due componenti.

Questa proprietà tornerà utile nella stima (identificazione) di modelli dai dati: in quel caso il "soggetto" non sarà un singolo parametro $\theta$, ma l'intero modello (che è di fatto uno stimatore di una funzione).

---

## 5. Esempi

### 5.1 Stimatore della media campionaria

Siano $\mathcal{D} = \{y(1), y(2), \ldots, y(N)\}$ variabili casuali con media $\mu$ e varianza $\sigma^2$. Lo stimatore della **media campionaria** è:

$$

\hat{\mu} = \frac{1}{N} \sum_{i=1}^{N} y(i)

$$

**Dimostrazione della correttezza** ($\mathbb{E}[\hat{\mu}] = \mu$):

$$

\mathbb{E}[\hat{\mu}] = \mathbb{E}\left[\frac{1}{N} \sum_{i=1}^{N} y(i)\right] = \frac{1}{N} \mathbb{E}\left[\sum_{i=1}^{N} y(i)\right] = \frac{1}{N} \cdot \mathbb{E}[y(1) + y(2) + \cdots + y(N)] = \frac{1}{N} \cdot N \cdot \mu = \mu

$$

Lo stimatore della media campionaria è **corretto e consistente**.

---

### 5.2 Stimatore della varianza campionaria

Siano $\mathcal{D} = \{y(1), y(2), \ldots, y(N)\}$ variabili casuali con media $\mu$ e varianza $\sigma^2$. Lo stimatore della **varianza campionaria** corretto è:

$$

S_{N-1}^2 = \frac{1}{N-1} \cdot \sum_{i=1}^{N} (y(i) - \hat{\mu})^2

$$

Si noti che si divide per $N-1$ (e non per $N$) per ottenere uno stimatore **corretto** della varianza. Questa quantità $N-1$ è nota come **gradi di libertà** e tiene conto del fatto che la media $\hat{\mu}$ è essa stessa stimata dai dati, "consumando" un grado di libertà.
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

X = data(:, 1:2); % Features

y = data(:, 3); % Prezzo

N = length(y); % Numero di dati

% Aggiunta del termine di intercetta

X = [ones(N, 1) X];

% Calcolo dei parametri con le normal equations

theta_hat = pinv(X'*X)*X'*y;

% Stima del prezzo di una casa di 1650 sq-ft, 3 camere

price_hat = [1 3 1650]*theta_hat;

```

Il modello stimato può poi essere usato per predire il prezzo di una casa con caratteristiche non presenti nel dataset (punto non visto durante la stima di $\boldsymbol{\theta}$).
# Lezione 08 – Processi stocastici

## Panoramica

Questa lezione introduce gli strumenti fondamentali per la **modellazione di sistemi dinamici** e **serie temporali**. Si parte dalla definizione di processo stocastico, si studiano le proprietà dei processi stazionari e la loro caratterizzazione sia nel dominio del tempo (autocovarianza, ergodicità) sia nel dominio della frequenza (trasformata $\mathcal{Z}$, DTFT, DFT, densità spettrale di potenza). Infine si collegano i processi stocastici ai sistemi dinamici lineari tempo-invarianti (LTI).

---

## 1. Introduzione alla stima di modelli dinamici

### 1.1 Serie temporali

> **Definizione:** Una **serie temporale** (discreta) è un insieme di dati $\mathcal{D} = \{y_1, y_2, \ldots, y_N\}$ indicizzati nel tempo, dove ogni dato è denotato $y_t$ con $t \in \mathbb{Z}$.

Esempi di serie temporali: valori di un titolo azionario, mm di pioggia caduti in una settimana, velocità del vento, moti ondosi.

### 1.2 Sistemi ingresso/uscita

I sistemi dinamici processano un segnale di input $u_t$ per generare un segnale di uscita $y_t$. Si dispone quindi di dati di input $\{u_1, u_2, \ldots, u_N\}$ e dati di output $\{y_1, y_2, \ldots, y_N\}$. Esempi: sistemi dinamici di varia natura (meccanici, economici, biologici).

### 1.3 Impostazione del problema

**Serie temporali:** La serie temporale $y_t$ viene modellata come l'uscita di un sistema dinamico con ingresso "remoto" non misurabile $e_t$.

**Sistemi I/O:** L'uscita $y_t$ è modellata come la somma di una componente esogena nota $u_t$ (passata attraverso un modello I/O) e una componente di **disturbo** $v_t$ ignota:

$$y_t = G(z) \cdot u_t + H(z) \cdot e_t$$

Il disturbo $v_t = H(z) \cdot e_t$ modella:

- Rumore di misura

- Disturbi di processo

- Effetto di segnali esogeni non misurabili

- Effetti di linearizzazioni del sistema

I modelli considerati saranno **LTI** (Lineari Tempo Invarianti) discreti.

### 1.4 Problemi da risolvere

1. **Predizione:** Predire l'uscita a istanti futuri $t+k$ in base alle informazioni al tempo $t$. Si indica la predizione come $\hat{y}_{t+1|t}$.

2. **Identificazione:** Stimare i modelli descritti per catturare le relazioni tra ingressi e uscita.

### 1.5 Step per la risoluzione

1. **Definizione delle classi di modelli** $\mathcal{M}$ di sistemi dinamici (funzioni di trasferimento razionali fratte).

2. **Predizione:** Dato un modello con parametri noti, qual è il predittore ottimo?

3. **Identificazione:** Come stimare i parametri del modello?

---

## 2. Processi stocastici

### 2.1 Definizione

> **Definizione:** Un **processo stocastico** $v(t, s)$ a tempo discreto è una successione infinita di variabili casuali, definite a partire dallo stesso esperimento casuale $s$ e ordinate secondo un indice temporale $t \in \mathbb{N}$:

$$v(1, s), \, v(2, s), \, \ldots, \, v(N, s), \quad N \in \mathbb{N}$$

**Tre modi di interpretare $v(t, s)$:**

- Fissato un esito $s = \bar{s}$: si ottiene una **realizzazione** $v(t, \bar{s})$ del processo stocastico, ovvero una serie di valori deterministici nel tempo (un segnale).

- Fissato un istante $t = \bar{t}$: si ottiene la **variabile casuale** $v(\bar{t}, s)$ al tempo $\bar{t}$.

- Fissati sia $s = \bar{s}$ che $t = \bar{t}$: si ottiene un **numero** $v(\bar{t}, \bar{s})$.

**Analogia:** Come una variabile casuale è una funzione che restituisce numeri reali, un processo stocastico è una funzione che restituisce **funzioni nel tempo** (segnali).

### 2.2 Esempio: random walk

Si consideri una sequenza di prove di Bernoulli con esito "successo" (probabilità $\pi$) e "insuccesso" (probabilità $1 - \pi$):

$$x_t = x_{t-1} + v_t, \quad v_t = \begin{cases} 1 & \text{successo} \\ -1 & \text{insuccesso} \end{cases}$$

Questo processo è chiamato **random walk** e può essere immaginato come la "camminata dell'ubriaco" che barcolla avanti e indietro, o come l'andamento dei beni di un giocatore d'azzardo.

### 2.3 Utilità dei processi stocastici

I processi stocastici sono utili quando è troppo complesso descrivere un fenomeno in modo deterministico. Ad esempio, la traiettoria di una palla di cannone ha una traiettoria media (cinematica), ma è "sporcata" dal vento, dalla densità dell'aria, dalla dilatazione termica. Si modella la traiettoria media in modo deterministico e gli scostamenti tramite un processo casuale.

### 2.4 Caratterizzazione completa vs. pratica

Un processo stocastico è **completamente caratterizzato** se, per ogni $n$-upla di variabili casuali $v_1, v_2, \ldots, v_n$, è nota la distribuzione congiunta. In pratica (tranne il caso Gaussiano), questo è impossibile. Ci si limita quindi alla **caratterizzazione del 2° ordine**: valore atteso e funzione di covarianza.

---

## 3. Caratterizzazione del secondo ordine

### 3.1 Valore atteso

> **Definizione:** Il **valore atteso** (momento del primo ordine) di un processo stocastico $v(t, s)$ è una funzione del tempo che rappresenta il valore atteso della v.c. al tempo $t$:

$$m_v(t) \equiv \mathbb{E}_s[v(t, s)]$$

È la **media di insieme** (media "in verticale"), non quella temporale (media "in orizzontale"). In generale $m_v(t_1) \neq m_v(t_2)$.

### 3.2 Funzione di autocorrelazione

> **Definizione:** La **funzione di autocorrelazione** (momento del secondo ordine) è:

$$R_{vv}(t_1, t_2) \equiv \mathbb{E}_s[v(t_1, s) \cdot v(t_2, s)]$$

Permette di capire come i valori del processo a un istante $t_1$ si relazionano con quelli a un istante $t_2$:

- $R_{vv}(t_1, t_2) > 0$: $v_{t_1}$ e $v_{t_2}$ hanno lo **stesso segno** in media.

- $R_{vv}(t_1, t_2) < 0$: $v_{t_1}$ e $v_{t_2}$ hanno **segno opposto** in media.

### 3.3 Funzione di autocovarianza

> **Definizione:** La **funzione di autocovarianza** è la covarianza tra $v(t_1, s)$ e $v(t_2, s)$:

$$\gamma_{vv}(t_1, t_2) \equiv \mathbb{E}_s\left[(v(t_1, s) - m_v(t_1)) \cdot (v(t_2, s) - m_v(t_2))\right]$$

Proprietà:

- Per $t_1 = t_2 = t$: $\gamma_{vv}(t, t) = \text{Var}(v(t, s))$ (varianza del processo al tempo $t$).

- $\gamma_{vv}(t_1, t_2) = R_{vv}(t_1, t_2) - m_v(t_1) \cdot m_v(t_2)$.

- Può essere vista come la funzione di autocorrelazione del processo **depolarizzato** $v(t, s) - m_v(t)$.

### 3.4 Funzione di autocovarianza normalizzata

> **Definizione:** L'**autocovarianza normalizzata** è una generalizzazione del coefficiente di correlazione:

$$\rho_{vv}(t_1, t_2) \equiv \frac{\gamma_{vv}(t_1, t_2)}{\sqrt{\gamma_{vv}(t_1, t_1) \cdot \gamma_{vv}(t_2, t_2)}}$$

Si ha che $|\rho_{vv}(t_1, t_2)| \leq 1$. È l'autocovarianza del processo normalizzato $\tilde{v}(t, s) = \frac{v(t, s) - m_v(t)}{\sqrt{\text{Var}(v(t, s))}}$.

### 3.5 Processi stocastici congiunti

Dati due processi stocastici $v(t, s)$ e $x(t, s)$:

> **Definizione:** La **cross-correlazione** è: $R_{vx}(t_1, t_2) \equiv \mathbb{E}_s[v(t_1, s) \cdot x(t_2, s)] = R_{xv}(t_2, t_1)$

> **Definizione:** La **cross-covarianza** è:

$$\gamma_{vx}(t_1, t_2) \equiv \mathbb{E}_s\left[(v(t_1, s) - m_v(t)) \cdot (x(t_2, s) - m_x(t))\right] = \gamma_{xv}(t_2, t_1)$$

Due processi sono **incorrelati** se $\gamma_{vx}(t_1, t_2) = 0$ per ogni $t_1, t_2$.

---

## 4. Processi stocastici stazionari

### 4.1 Stazionarietà in senso forte

> **Definizione:** Un processo stocastico $v_t$ è **stazionario in senso forte** se, per ogni $n \in \mathbb{N}$, scelti $t_1, t_2, \ldots, t_n$ istanti di tempo, le caratteristiche probabilistiche della $n$-upla $v_{t_1}, v_{t_2}, \ldots, v_{t_n}$ sono uguali a quelle della $n$-upla $v_{t_1+\tau}, v_{t_2+\tau}, \ldots, v_{t_n+\tau}$, per ogni $\tau \in \mathbb{N}$.

### 4.2 Stazionarietà in senso debole

> **Definizione:** Un processo stocastico $v_t$ è **stazionario in senso debole** se:

> 1. $m_v(t) = m$, costante per ogni $t$ (media costante).

> 2. $\gamma_{vv}(t_1, t_2) = \gamma_{vv}(t_3, t_4)$ quando $t_4 - t_3 = t_2 - t_1 = \tau$ (l'autocovarianza dipende solo dal **lag** $\tau$, non dai valori specifici degli istanti).

Se un processo è stazionario in senso forte, lo è anche in senso debole. Nel corso si supporrà la stazionarietà in senso debole.

Per un pss (processo stocastico stazionario) si scrive:

$$\gamma_{vv}(\tau) = \mathbb{E}_s\left[(v(t, s) - m) \cdot (v(t+\tau, s) - m)\right]$$

### 4.3 Decomposizione di processi non stazionari

Un processo non stazionario può essere decomposto in:

- **Trend** (componente deterministica a lungo termine)

- **Stagionalità** (componente periodica)

- **Processo stocastico stazionario** (componente residua)

> **Definizione:** Due pss $v_1(t)$ e $v_2(t)$ si dicono **equivalenti** se hanno lo stesso valore atteso $m$ e la stessa funzione di autocovarianza $\gamma(\tau)$.

### 4.4 Proprietà della funzione di autocovarianza di un pss

1. **Non-negatività della varianza:** $\gamma_{vv}(0) = \mathbb{E}[(v_t - m)^2] \geq 0$

2. **Funzione limitata:** $|\gamma_{vv}(\tau)| \leq \gamma_{vv}(0)$, per ogni $\tau$. Il legame tra $v_t$ e se stesso è più forte che tra $v_t$ e $v_{t+\tau}$.

3. **Funzione pari:** $\gamma_{vv}(\tau) = \gamma_{vv}(-\tau)$

### 4.5 Rumore bianco (White Noise)

> **Definizione:** Un pss $e_t \sim \text{WN}(\mu, \lambda^2)$ è detto **rumore bianco** se:

> 1. $\mathbb{E}[e_t] = \mu$

> 2. $\gamma_{ee}(0) = \mathbb{E}[(e_t - \mu)^2] = \lambda^2$, per ogni $t$

> 3. $\gamma_{ee}(\tau) = \mathbb{E}[(e_t - \mu)(e_{t+\tau} - \mu)] = 0$, per ogni $t$, per ogni $\tau \neq 0$

Poiché non vi è correlazione tra il valore a un istante $t$ e un valore all'istante $t + \tau$, il rumore bianco è un processo le cui realizzazioni variano in modo **impredicibile** da un istante all'altro.

**Nota:** Per i nostri scopi non è importante la distribuzione delle singole v.c. $e_{t_1}, e_{t_2}, \ldots$ del processo rumore bianco. Inoltre, spesso si considerano pss a media nulla, poiché il valore della media non modifica l'autocovarianza (e quindi le caratteristiche spettrali).

---

## 5. Momenti temporali ed ergodicità

### 5.1 Momenti temporali

Dato un pss $v(t, s)$, si definiscono:

**Media temporale su orizzonte finito:**

$$\langle v(t, s) \rangle_N \equiv \frac{1}{N} \sum_{t=0}^{N-1} v(t, s)$$

È calcolata su $N$ campioni temporali di una singola realizzazione del pss.

**Media temporale** (limite per $N \to +\infty$):

$$\langle v(t, s) \rangle \equiv \lim_{N \to +\infty} \langle v(t, s) \rangle_N$$

**Autocorrelazione temporale:**

$$\langle v(t, s) \cdot v(t+\tau, s) \rangle \equiv \lim_{N \to +\infty} \sum_{t=0}^{N-1} v(t, s) \cdot v(t+\tau, s)$$

**Osservazioni:**

- $\langle v(t, s) \rangle_N$ è una variabile casuale (dipende dall'esito $s$).

- **Teorema:** Se $v(t, s)$ è un pss e $\mathbb{E}_s[|v(t, s)|] < +\infty$, allora il limite $\langle v(t, s) \rangle$ converge **quasi certamente**.

- Si dimostra che: $\mathbb{E}_s[\langle v(t, s) \rangle] = m$ e $\mathbb{E}_s[\langle v(t, s) \cdot v(t+\tau, s) \rangle] = R_{vv}(\tau)$, cioè i momenti temporali sono **stimatori corretti** dei corrispondenti momenti d'insieme.

### 5.2 Processi stocastici ergodici

> **Definizione:** Un processo stocastico $v(t, s)$ è detto **ergodico** se:

> 1. $v(t, s)$ è stazionario.

> 2. Per $N \to +\infty$, i momenti temporali convergono quasi certamente ai rispettivi momenti di insieme.

> **Definizione:** Un pss è **ergodico nella media** (proprietà più debole) se: $\lim_{N \to +\infty} \langle v(t, s) \rangle_N = m$ quasi certamente.

**Teorema (condizioni sufficienti per l'ergodicità nella media):** Se $v(t, s)$ è un pss in senso debole e:

1. $|\gamma_{vv}(0)| < +\infty$ (varianza finita)

2. $\lim_{\tau \to +\infty} \gamma_{vv}(\tau) = 0$ (l'autocovarianza tende a zero)

allora $v(t, s)$ è ergodico nella media.

**Teorema (ergodicità completa per processi Gaussiani):** Se $v(t, s)$ è stazionario e Gaussiano, e valgono le condizioni 1 e 2 sopra, allora $v(t, s)$ è **ergodico** (non solo nella media).

**Proprietà chiave dell'ergodicità:**

- Se un processo è ergodico, **ogni singola realizzazione** è "rappresentativa" di tutte le possibili realizzazioni.

- Per essere rappresentativa, la realizzazione deve "dimenticare" i valori iniziali e "esplorare" tutto il dominio del processo.

- Il processo "dimentica" quando $\gamma_{vv}(\tau) \to 0$: l'autocovarianza decade rapidamente (il passato non influenza il futuro lontano).

**Nota pratica:** Nella pratica, l'ergodicità è spesso un'**ipotesi** che non si può dimostrare formalmente. Senza informazioni precise sul meccanismo di generazione dati, si ipotizza l'ergodicità e si stimano le caratteristiche del processo tramite momenti temporali.

---

## 6. Trasformata $\mathcal{Z}$ e trasformata di Fourier

### 6.1 Trasformata $\mathcal{Z}$

> **Definizione:** La **trasformata Zeta bilatera** di un segnale discreto deterministico $g(t)$, $t \in \mathbb{Z}$, è:

$$\mathcal{Z}\{g(t)\} = G(z) \equiv \sum_{t=-\infty}^{+\infty} g(t) \cdot z^{-t}, \quad z \in \mathbb{C}$$

- $g(t)$ è una funzione reale di variabile intera $t \in \mathbb{Z}$.

- $G(z)$ è una funzione complessa di variabile complessa $z \in \mathbb{C}$.

**Proprietà della trasformata $\mathcal{Z}$:**

- **Linearità:** $\mathcal{Z}\{\alpha g(t) + \beta h(t)\} = \alpha G(z) + \beta H(z)$

- **Anticipo:** $\mathcal{Z}\{g(t+1)\} = z \cdot G(z)$ — $z$ è l'**operatore di anticipo unitario**

- **Ritardo:** $\mathcal{Z}\{g(t-1)\} = z^{-1} \cdot G(z)$ — $z^{-1}$ è l'**operatore di ritardo unitario**

### 6.2 Convoluzione discreta

> **Definizione:** La **convoluzione** (discreta) tra due segnali $g(t)$ e $u(t)$ è:

$$y(t) = \sum_{i=-\infty}^{+\infty} g(i) \, u(t - i) = \sum_{i=-\infty}^{+\infty} g(t - i) \, u(i)$$

**Proprietà:** La trasformata $\mathcal{Z}$ della convoluzione è il **prodotto** delle trasformate: $Y(z) = G(z) \cdot U(z)$.

### 6.3 Trasformata di Fourier a Tempo Discreto (DTFT)

> **Definizione:** Sia $u(t)$ un segnale discreto, deterministico, **assolutamente sommabile** ($\sum_{t=-\infty}^{+\infty} |u(t)| < +\infty$). La **DTFT** è:

$$\mathcal{F}\{u(t)\} \equiv \sum_{t=-\infty}^{+\infty} u(t) \cdot e^{-j\omega t}$$

È una funzione **complessa** della variabile reale $\omega \in \mathbb{R}$, quindi è una funzione **continua**.

**Relazione con la trasformata $\mathcal{Z}$:**

$$\mathcal{F}\{u(t)\} = U(e^{j\omega})$$

La DTFT si ottiene valutando la trasformata $\mathcal{Z}$ sulla **circonferenza unitaria** $z = e^{j\omega}$ nel piano complesso.

**Proprietà della DTFT:**

- $2\pi$**-periodica:** $X(e^{j(\omega + 2k\pi)}) = X(e^{j\omega})$

- **Coniugata:** $\overline{X(e^{j\omega})} = X(e^{-j\omega})$

- Tutta l'informazione è contenuta nell'intervallo $[0, \pi]$.

### 6.4 Trasformata di Fourier Discreta (DFT)

> **Definizione:** Per un segnale discreto $u(t)$ di durata finita, definito su $t \in \{0, 1, \ldots, N-1\}$, la **DFT** è:

$$\tilde{U}(k) \equiv \sum_{t=0}^{N-1} u(t) \cdot e^{-j \cdot t \cdot k \cdot \phi}, \quad \phi = \frac{2\pi}{N}, \quad k = 0, 1, \ldots, N-1$$

Si parte da un vettore di $N$ numeri reali $u(t)$ e si ottiene un vettore di $N$ numeri complessi $\tilde{U}(k)$.

**Proprietà della DFT:**

- La DFT è un **campionamento** della DTFT: $\tilde{U}(k) = U(e^{j \cdot k \cdot 2\pi/N})$.

- Esiste la **DFT inversa** (IDFT): è possibile ricostruire $u(t)$ da $\tilde{U}(k)$ senza perdita di informazione.

- **Risoluzione in frequenza** (frequency bin): $\text{bin} = f_s / N$, dove $f_s$ è la frequenza di campionamento.

- Dato che la DFT è simmetrica, solo $N/2$ dati portano informazione; la $N/2$-esima frequenza ($k = N/2$) corrisponde alla **frequenza di Nyquist** $f_s / 2$.

- La DFT è sia discreta che periodica, e presuppone che il segnale nel tempo sia discreto e periodico.

---

## 7. Densità spettrale di potenza

### 7.1 Definizione

> **Definizione:** Dato un processo stocastico stazionario, si definisce **densità spettrale di potenza** $\Gamma_{vv}(\omega)$ come la DTFT della funzione di autocovarianza $\gamma_{vv}(\tau)$:

$$\Gamma_{vv}(\omega) \equiv \mathcal{F}\{\gamma_{vv}(\tau)\} = \sum_{\tau=-\infty}^{+\infty} \gamma_{vv}(\tau) \cdot e^{-j\omega\tau}$$

La corrispondente trasformata $\mathcal{Z}$ è:

$$\Phi_{vv}(z) \equiv \mathcal{Z}\{\gamma_{vv}(\tau)\} = \sum_{\tau=-\infty}^{+\infty} \gamma_{vv}(\tau) \cdot z^{-\tau}$$

con la relazione $\Gamma_{vv}(\omega) = \Phi_{vv}(e^{j\omega})$.

**Interpretazione:** La densità spettrale di potenza descrive come, **in media**, le componenti in frequenza delle realizzazioni del processo contribuiscono alla sua varianza. Indica come l'energia del processo si distribuisce alle varie frequenze.

### 7.2 Proprietà di $\Gamma_{vv}(\omega)$

1. **Reale:** poiché $\gamma_{vv}(\tau)$ è pari, i termini immaginari si elidono.

2. **Positiva:** $\Gamma_{vv}(\omega) \geq 0$ per ogni $\omega \in \mathbb{R}$.

3. **Pari:** $\Gamma_{vv}(\omega) = \Gamma_{vv}(-\omega)$ per ogni $\omega$.

4. **Periodica di periodo $2\pi$:** $\Gamma_{vv}(\omega) = \Gamma_{vv}(\omega + k \cdot 2\pi)$ per ogni $\omega$ e $k \in \mathbb{Z}$.

Per queste proprietà è sufficiente valutare $\Gamma_{vv}(\omega)$ nell'intervallo $[0, \pi]$.

### 7.3 Pulsazione normalizzata

A tempo discreto, la più grande pulsazione osservabile è quella di una cosinusoide che cambia valore a ogni istante $t$. Il più piccolo periodo osservabile è $T = 2T_s$ (due campioni). La pulsazione massima è:

$$\omega = \frac{2\pi}{T} = \frac{\pi}{T_s} = \pi \cdot f_s \quad \text{[rad/s]}$$

La pulsazione $\omega$ nella densità spettrale è **normalizzata** rispetto alla frequenza di campionamento $f_s$. $\pi$ rad/s corrisponde a $f_s / 2$ Hz (frequenza di Nyquist, **teorema del campionamento**).

### 7.4 Antitrasformata e varianza

È possibile risalire a $\gamma_{vv}(\tau)$ tramite l'antitrasformata:

$$\gamma_{vv}(\tau) = \frac{1}{2\pi} \int_{-\pi}^{+\pi} \Gamma_{vv}(\omega) \cdot e^{j\omega\tau} \, d\omega$$

La **varianza** del processo stazionario è l'area sottesa alla densità spettrale (a meno del fattore $2\pi$):

$$\gamma_{vv}(0) = \frac{1}{2\pi} \int_{-\pi}^{+\pi} \Gamma_{vv}(\omega) \, d\omega$$

### 7.5 Densità spettrale del rumore bianco

Per un rumore bianco $e_t \sim \text{WN}(0, \lambda^2)$:

$$\gamma_{ee}(\tau) = \begin{cases} \lambda^2 & \text{se } \tau = 0 \\ 0 & \text{se } \tau \neq 0 \end{cases} \implies \Gamma_{ee}(\omega) = \lambda^2$$

La densità spettrale è **costante** (piatta): tutte le frequenze hanno la stessa potenza media. Questo riflette l'**impredicibilità** del processo: non vi sono frequenze dominanti.

### 7.6 Esempi di processi e corrispondenti spettri

| Tipo di processo | Autocovarianza | Spettro | Andamento temporale |

|---|---|---|---|

| **Rumore bianco** | Impulsiva | Piatto | Impredicibile |

| **Processo regolare** | Decade lentamente | Dominano basse frequenze | Regolare (liscio) |

| **Processo alternante** | Cambia segno rapidamente | Dominano alte frequenze | Irregolare (oscillante) |

| **Processo con freq. dominante** | Oscillazioni smorzate di periodo $\approx T$ | Picco di risonanza in $\bar{\omega} \approx 2\pi/T$ | Oscillazioni irregolari |

### 7.7 Densità cross-spettrale

> **Definizione:** Dati due pss $v(t, s)$ e $x(t, s)$, la **densità di potenza cross-spettrale** è:

$$\Gamma_{vx}(\omega) \equiv \mathcal{F}\{\gamma_{vx}(\tau)\}, \quad \Phi_{vx}(z) \equiv \mathcal{Z}\{\gamma_{vx}(\tau)\}$$

**Proprietà:**

- $\gamma_{vx}(\tau) = \gamma_{xv}(-\tau)$

- $\Gamma_{vx}(\omega) = \Gamma_{xv}(-\omega)$

- $\Phi_{vx}(z) = \Phi_{xv}(z^{-1})$

---

## 8. Stima spettrale

### 8.1 Stima delle proprietà di un pss ergodico

**Ipotesi:** processo stazionario ergodico $v_t$ a media nulla (se $m_v \neq 0$, si stima $\hat{m}_v$ e si analizza $v_t - \hat{m}_v$). Si dispone di $N$ dati (una sola realizzazione).

**Media (temporale) campionaria:**

$$\hat{m}_v = \frac{1}{N} \sum_{t=0}^{N-1} v_t$$

Stimatore **corretto** del valore atteso di un pss ergodico.

### 8.2 Funzione di autocovarianza campionaria

**Stimatore corretto:**

$$\hat{\gamma}_{vv}(\tau) = \frac{1}{N - |\tau|} \sum_{t=0}^{N-|\tau|-1} v_t \, v_{t+|\tau|}, \quad |\tau| < N$$

Proprietà:

- Per $\tau = 0$: stima la varianza del processo.

- $\mathbb{E}[\hat{\gamma}_{vv}(\tau)] = \gamma_{vv}(\tau)$ (stimatore **corretto**).

- Per $\tau$ fissato, è **consistente** sotto le ipotesi di ergodicità.

- Per $\tau \approx N$: $\text{Var}(\hat{\gamma}_{vv}(\tau))$ è grande (pochi dati disponibili).

- Se $v_t$ è Gaussiano, è lo stimatore a **massima verosimiglianza**.

**Stimatore alternativo (distorto ma con minor varianza):**

$$\hat{\gamma}'_{vv}(\tau) = \frac{1}{N} \sum_{t=0}^{N-|\tau|-1} v_t \, v_{t+|\tau|}, \quad |\tau| < N$$

Proprietà:

- $\mathbb{E}[\hat{\gamma}'_{vv}(\tau)] = \frac{N - |\tau|}{N} \gamma_{vv}(\tau)$ — stimatore **distorto** ma **asintoticamente corretto**.

- $\hat{\gamma}'_{vv}(\tau) = \frac{N-|\tau|}{N} \hat{\gamma}_{vv}(\tau)$.

- Per $\tau \approx N$, il valore atteso viene "schiacciato verso il basso": peggiora il bias ma **riduce la varianza**. Questo è vantaggioso perché per molti processi $\lim_{\tau \to +\infty} \gamma_{vv}(\tau) = 0$.

### 8.3 Periodogramma

> **Definizione:** Il **periodogramma** è lo stimatore della densità spettrale di potenza:

$$I_N(\omega) \equiv \sum_{\tau=-(N-1)}^{N-1} \hat{\gamma}'_{vv}(\tau) \cdot e^{-j\omega\tau}$$

**Proprietà:**

- $I_N(\omega) = \frac{1}{N} |V(e^{j\omega})|^2$ (proporzionale al modulo quadro della DTFT della realizzazione).

- Per segnali di durata finita, si campiona il periodogramma con la DFT: $\tilde{I}_N(k) = \frac{1}{N} |V(e^{j \cdot k \cdot 2\pi/N})|^2$, per $k = 0, 1, \ldots, N-1$.

In MATLAB: `abs(fft(v)).^2 / N`

- $I_N(\omega)$ è **non corretto** ma **asintoticamente corretto**.

- $\text{Var}(I_N(\omega)) \approx \Gamma_{vv}^2(\omega)$: la varianza **non decresce** con $N$. Lo stimatore **non è consistente**.

- Per $N \to +\infty$, $I_N(\omega_1)$ e $I_N(\omega_2)$ tendono a diventare incorrelati per $\omega_1 \neq \omega_2$ (il periodogramma è una funzione "poco liscia", una sorta di "rumore bianco in frequenza").

### 8.4 Metodo di Bartlett

Per migliorare la stima dello spettro (riducendo la varianza a scapito del bias):

1. Dividere i $N$ dati in $K = N/M$ parti di lunghezza $M$.

2. Calcolare il periodogramma $I_{M,K}^{(i)}(\omega)$ per ciascuna parte $i = 1, 2, \ldots, K$.

3. Calcolare la media dei periodogrammi:

$$\bar{I}_{M,K}(\omega) = \frac{1}{K} \sum_{i=1}^{K} I_{M,K}^{(i)}(\omega)$$

**Proprietà:**

- Se $\gamma_{vv}(\tau) \to 0$ sufficientemente rapidamente, i $K$ periodogrammi sono circa indipendenti. In questo caso: $\text{Var}(\bar{I}_{M,K}(\omega)) = O\left(\frac{1}{K} \Gamma_{vv}^2(\omega)\right)$.

- Il bias è **maggiore** rispetto a $I_N(\omega)$, con conseguente perdita di risoluzione in frequenza.

- Se $\Gamma_{vv}(\omega)$ ha picchi molto stretti, usare $M$ grande per avere sufficiente risoluzione.

---

## 9. Sistemi dinamici LTI discreti deterministici

### 9.1 Obiettivo

L'obiettivo della seconda parte del corso è **identificare** (stimare) un modello di sistema dinamico. Ci si concentra su sistemi **LTI** (Lineari Tempo Invarianti) a tempo discreto, **SISO** (Single Input Single Output).

Un sistema dinamico può essere rappresentato in:

- **Spazio di stato:**

$$\begin{cases} \mathbf{x}_{t+1} = A \cdot \mathbf{x}_t + B \cdot u_t \\ y_t = C \cdot \mathbf{x}_t + D \cdot u_t \end{cases}$$

- **Funzione di trasferimento:** $G(z) = \frac{Y(z)}{U(z)}$

L'obiettivo è stimare la funzione di trasferimento $G(z)$.

### 9.2 Definizione di sistema LTI

> **Definizione:** Un sistema dinamico (causale) è **LTI** se la sua uscita $y_t$ può essere espressa tramite la **convoluzione** (discreta, causale) dell'input $u_t$ e della risposta all'impulso $g_t$:

$$y_t = \sum_{i=-\infty}^{t} g_{t-i} \, u_t = \sum_{j=0}^{\infty} g_j \, u_{t-j}$$

L'ipotesi $g_t = 0$ per $t < 0$ è un'ipotesi di **causalità**: l'ingresso $u_t$ può influenzare l'uscita solo a istanti $s \geq t$.

### 9.3 Funzione di trasferimento

> **Definizione:** La **funzione di trasferimento** $G(z)$ descrive la relazione tra ingresso e uscita (con condizioni iniziali nulle $\mathbf{x}_0 = \mathbf{0}$):

$$G(z) = \frac{\mathcal{Z}\{y_t\}}{\mathcal{Z}\{u_t\}} = \frac{Y(z)}{U(z)} = \sum_{t=0}^{+\infty} g_t \cdot z^{-t}$$

$G(z)$ è il rapporto di due polinomi razionali in $z$.

### 9.4 Dalla funzione di trasferimento alla forma ricorsiva

**Esempio:** Data $G(z) = \frac{3z - 0.3}{z^2 - 0.3z - 0.1}$:

$$Y(z) = G(z) \cdot U(z) = \frac{3z^{-1} - 0.3z^{-2}}{1 - 0.3z^{-1} - 0.1z^{-2}} \cdot U(z)$$

Antitrasformando:

$$y_t = 0.3 y_{t-1} + 0.1 y_{t-2} + 3 u_{t-1} - 0.3 u_{t-2}$$

**Abuso di notazione utile:** Si scrive $y_t = G(z) \cdot u_t$ per passare velocemente alla forma ricorsiva, ricordando che $z^{-1} \cdot y_t = y_{t-1}$.

### 9.5 Rappresentazioni equivalenti

Un sistema LTI discreto può essere rappresentato come:

1. **Spazio di stato** (rappresentazione più completa)

2. **Funzione di trasferimento** (rappresenta solo stati raggiungibili/osservabili)

3. **Forma ricorsiva** (o di filtraggio)

La relazione tra spazio di stato e funzione di trasferimento è data dalla **realizzazione**: $G(z) = C(zI_n - A)^{-1}B + D$.

### 9.6 Zeri e poli

> **Definizione:** Gli **zeri** di $G(z)$ sono le radici del numeratore; i **poli** sono le radici del denominatore.

> **Definizione:** Un sistema dinamico LTI a tempo discreto è **asintoticamente stabile** se tutti i suoi poli hanno modulo minore di 1: $|z_i| < 1$ per ogni polo $z_i$.

La stabilità asintotica implica che l'output abbia "energia limitata" dato un input di "energia limitata" (stabilità BIBO). Se il sistema è in equilibrio stabile, vi tornerà dopo una perturbazione.

### 9.7 Guadagno

Per un sistema asintoticamente stabile, la risposta all'impulso tende esponenzialmente a zero: $\lim_{t \to +\infty} g_t = 0$.

> **Definizione:** Il **guadagno** del sistema è:

$$\mu = \sum_{t=0}^{+\infty} g_t = G(1)$$

Se si applica un ingresso a gradino $u_t = \text{sca}(t)$ a un sistema stabile, l'uscita converge al valore $\mu$: $\lim_{t \to +\infty} y_t = \mu$.

### 9.8 Risposta in frequenza

Per un segnale sinusoidale campionato: $s_t = A \cdot \sin(2\pi f_0 \cdot T_s \cdot t + \varphi)$.

La frequenza di Nyquist è $f_{\text{Nyq}} = f_s / 2 = 1/(2T_s)$. La frequenza del segnale deve rispettare il **criterio di Nyquist** (teorema del campionamento).

Per un sistema LTI asintoticamente stabile con input sinusoidale $u_t = A \sin(2\pi T_s t \cdot f + \varphi)$, l'uscita a regime è:

$$y_t \approx \bar{A} \cdot \sin(2\pi T_s t \cdot f + \bar{\varphi})$$

dove:

- $\bar{A} = A \cdot |G(e^{j \cdot 2\pi T_s \cdot f})|$ — effetto del **guadagno** del sistema

- $\bar{\varphi} = \varphi + \angle G(e^{j \cdot 2\pi T_s \cdot f})$ — effetto dello **sfasamento** indotto dal sistema

Valutando $G(z)$ in $z = e^{j\omega T_s}$ si ottiene la **risposta in frequenza** (FRF):

- $|G(e^{j\omega})|$: modulo della FRF (amplificazione/attenuazione)

- $\angle G(e^{j\omega})$: fase della FRF

**Similitudine del "tendone del circo":** I poli sono come "pali" (alzano il modulo), gli zeri sono come "picchetti" (abbassano il modulo a zero).

---

## 10. Sistemi dinamici LTI discreti stocastici

### 10.1 Ingresso stocastico

Supponiamo che $u_t$ sia un pss in senso debole (media $m_u$, autocovarianza $\gamma_{uu}(\tau)$) e $G(z)$ una funzione di trasferimento razionale fratta, asintoticamente stabile con guadagno $\mu$.

**Valore atteso dell'uscita:**

$$\mathbb{E}[y_t] = \sum_{i=0}^{+\infty} g_i \, \mathbb{E}[u_{t-i}] = G(1) \cdot m_u = \mu \cdot m_u$$

Il valore atteso di $y_t$ **non dipende da $t$**.

**Cross-covarianza ingresso-uscita:**

$$\gamma_{uy}(\tau) = \sum_{i=0}^{+\infty} g_i \, \gamma_{uu}(\tau - i) \implies \Gamma_{uy}(\omega) = G(e^{j\omega}) \cdot \Gamma_{uu}(\omega)$$

**Autocovarianza dell'uscita:**

$$\gamma_{yy}(\tau) = \sum_{i=0}^{+\infty} g_i \, \gamma_{yu}(\tau - i) \implies \Gamma_{yy}(\omega) = G(e^{j\omega}) \cdot \Gamma_{yu}(\omega)$$

Anche la funzione di autocovarianza di $y_t$ **non dipende da $t$**.

### 10.2 Stazionarietà dell'uscita

> **Teorema:** Sia $u_t$ un processo stocastico stazionario che alimenta un sistema dinamico asintoticamente stabile $G(z)$. Allora, anche $y_t$ è un **processo stocastico stazionario**.

Questa è una condizione **necessaria e sufficiente**. Nella pratica, $u_t$ viene applicato da $t = 0$ (non da $t = -\infty$), per cui $y_t$ sarà stazionario **dopo un transitorio**.

### 10.3 Densità spettrale di potenza dell'uscita

> **Teorema:**

$$\Gamma_{yy}(\omega) = |G(e^{j\omega})|^2 \cdot \Gamma_{uu}(\omega)$$

$$\Phi_{yy}(z) = G(z) \cdot G(z^{-1}) \cdot \Phi_{uu}(z)$$

Il modulo quadro della FRF $|G(e^{j\omega})|^2$ **modula** la densità spettrale dell'ingresso per ottenere quella dell'uscita.

### 10.4 Rappresentazione dinamica di un pss

> **Risultato fondamentale:** Qualunque processo stocastico stazionario $y_t$ può essere interpretato come l'uscita di un sistema dinamico $G(z)$ asintoticamente stabile, alimentato da **rumore bianco** $e_t \sim \text{WN}(0, 1)$:

$$\Gamma_{yy}(\omega) = |G(e^{j\omega})|^2$$

Questo implica che ogni pss può essere espresso come **combinazione lineare** di infiniti campioni di rumore bianco:

$$y_t = \sum_{j=0}^{\infty} g_j \, e_{t-j} = g_0 e_t + g_1 e_{t-1} + g_2 e_{t-2} + \cdots$$

Questo modello è noto come **MA($\infty$)** (Moving Average di ordine infinito), che verrà approfondito nella Lezione 09.

### 10.5 Applicazione: simulazione di un pss

Se si conosce (o si stima) $\Gamma_{yy}(\omega)$ e si trova $G(z)$ asintoticamente stabile e causale tale che $\Gamma_{yy}(\omega) = |G(e^{j\omega})|^2$, è possibile **simulare** diverse realizzazioni del processo generando sequenze di variabili casuali incorrelate (rumore bianco) al computer.

**Esempio della simulazione del vento:** Dato lo spettro del vento (con picchi micro-meteorologici e macro-meteorologici e un gap spettrale), si devono trovare almeno 3 coppie di poli complessi coniugati (ordine $G(z) \geq 6$).

### 10.6 Modello generale

**Serie temporali:** $y_t = H(z) \cdot e_t$, dove $e_t$ è rumore bianco e $H(z)$ è la funzione di trasferimento da stimare.

**Sistemi I/O:** $y_t = G(z) \cdot u_t + H(z) \cdot e_t$, dove $G(z)$ rappresenta il sistema fisico e $H(z)$ modella il disturbo stocastico. $H(z)$ e $e_t$ non esistono fisicamente: sono strumenti matematici per modellare ciò che $G(z)$ non riesce a catturare.

---

## 11. Depolarizzazione

> **Definizione:** La **depolarizzazione** consiste nel rimuovere il valore atteso $m$ da un pss: $\tilde{v}_t = v_t - m$.

**Proprietà:**

- $\mathbb{E}[\tilde{v}_t] = 0$ (il processo depolarizzato ha media nulla)

- $\tilde{\gamma}_{vv}(\tau) = \gamma_{vv}(\tau)$ (l'autocovarianza non cambia)

I processi $v_t$ e $\tilde{v}_t$ hanno le stesse caratteristiche spettrali. Non si perde generalità nello studiare processi a media nulla.