# Lezione 01 – Introduzione
## 1. Presentazione del corso
Il corso di **Identificazione dei Modelli e Analisi dei Dati (IMAD)** è tenuto dal Prof. Mirko Mazzoleni presso l'Università degli Studi di Bergamo, nell'ambito del Corso di Laurea Magistrale in Ingegneria Informatica.
Il corso si articola in **due parti principali**:
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
