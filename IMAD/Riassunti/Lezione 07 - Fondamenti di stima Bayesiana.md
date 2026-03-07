# Lezione 07 – Fondamenti di stima Bayesiana
## Panoramica
Questa lezione introduce l'approccio **Bayesiano** alla stima dei parametri. A differenza degli approcci classici (minimi quadrati, massima verosimiglianza), in cui il vettore di parametri $\boldsymbol{\theta}$ è considerato **deterministico**, nell'approccio Bayesiano $\boldsymbol{\theta}$ viene trattato come una **variabile casuale**, alla quale si assegna una distribuzione di probabilità che riflette le nostre conoscenze a priori.
---
## 1. Probabilità congiunte, marginali e condizionate
### 1.1 Distribuzione di probabilità congiunta
> **Definizione:** La distribuzione di probabilità congiunta $P(a, b)$ esprime la probabilità che entrambe le variabili casuali $a$ e $b$ assumano contemporaneamente un certo valore specifico.
Proprietà fondamentali:
- La somma su tutti i valori possibili è pari a 1:
$$\sum_{a} \sum_{b} P(a, b) = 1$$
- La congiunta è simmetrica: $P(a, b) = P(b, a)$.
**Esempio numerico:** Date due variabili casuali discrete binarie $a$ e $b$, con la tabella congiunta:
| $a \backslash b$ | 0 | 1 |
|---|---|---|
| 0 | 0.06 | 0.28 |
| 1 | 0.24 | 0.42 |
Si ha ad esempio $P(a=1, b=0) = 0.24$ e $P(a=0, b=1) = 0.28$.
### 1.2 Distribuzione di probabilità marginale
> **Definizione:** La distribuzione marginale è la distribuzione di probabilità di un sottoinsieme di variabili casuali, ottenuta sommando (o integrando, nel caso continuo) la distribuzione congiunta rispetto alle variabili che non sono di interesse.
Per variabili discrete, la marginale di $b$ si calcola come:
$$P(b) = \sum_{a} P(a, b)$$
**Esempio:** Partendo dalla tabella congiunta precedente:
- $P(b=0) = P(a=0, b=0) + P(a=1, b=0) = 0.06 + 0.24 = 0.30$
- $P(b=1) = P(a=0, b=1) + P(a=1, b=1) = 0.28 + 0.42 = 0.70$
Analogamente per $P(a)$:
- $P(a=0) = 0.06 + 0.28 = 0.34$
- $P(a=1) = 0.24 + 0.42 = 0.66$
La somma di ciascuna marginale vale 1.
### 1.3 Distribuzione di probabilità condizionata
> **Definizione:** La distribuzione condizionata $P(A | B)$ indica come la probabilità di un evento $A$ si ridistribuisce quando si restringe la popolazione a un particolare sottoinsieme determinato dall'evento $B$.
La formula fondamentale è:
$$P(A | B) = \frac{P(A, B)}{P(B)}$$
da cui segue la **regola del prodotto**:
$$P(A, B) = P(A | B) \cdot P(B)$$
**Interpretazione intuitiva:** Il condizionamento restringe la popolazione considerata. Ad esempio, se $A$ rappresenta "persona con capelli lunghi" e $B$ rappresenta "persona che ascolta i Black Sabbath", allora $P(A|B)$ è la frazione di ascoltatori dei Black Sabbath che hanno i capelli lunghi. L'universo di riferimento passa da $N$ persone totali a $N_B$ persone che ascoltano i Black Sabbath.
**Osservazioni importanti:**
- $P(A, B) = P(A) \cdot P(B)$ solo se $P(A|B) = P(A)$, cioè quando $A$ e $B$ sono **eventi indipendenti** (il verificarsi di $B$ non modifica la probabilità di $A$).
- Non c'è necessariamente una causalità temporale nella relazione $P(A, B) = P(A|B) \cdot P(B)$.
**Esempio del bersaglio da freccette:** Consideriamo un bersaglio con 20 cerchi, ciascuno equiprobabile. La probabilità di colpire il cerchio #5 è $P(\#5) = 1/20$. Se un amico ci dice che non si è colpito il cerchio #7, la probabilità si ridistribuisce:
$$P(\#5 \mid \text{NOT } \#7) = \frac{P(\#5, \text{NOT } \#7)}{P(\text{NOT } \#7)} = \frac{P(\#5) \cdot P(\text{NOT } \#7 | \#5)}{P(\text{NOT } \#7)} = \frac{\frac{1}{20} \cdot 1}{\frac{19}{20}} = \frac{1}{19}$$
Il condizionamento rende inaccessibili certi stati, e la probabilità viene ridistribuita su quelli accessibili.
**Esempio numerico della condizionata:** Dalla tabella congiunta:
- $P(a=1 | b=0) = \frac{P(a=1, b=0)}{P(b=0)} = \frac{0.24}{0.30} = 0.8$
- $P(a=0 | b=0) = \frac{0.06}{0.30} = 0.2$
- $P(a=1 | b=1) = \frac{0.42}{0.70} = 0.6$
- $P(a=0 | b=1) = \frac{0.28}{0.70} = 0.4$
---
## 2. Teorema di Bayes
> **Definizione (Teorema di Bayes):** Sapendo che $P(A, B) = P(B, A)$ e che $P(B, A) = P(B|A) \cdot P(A)$, si ottiene:
$$P(A|B) = \frac{P(B|A) \cdot P(A)}{P(B)}$$
**Interpretazione:** Il Teorema di Bayes permette di **ridistribuire** la probabilità: prima conoscevamo $P(A)$, dopo l'osservazione di $B$ conosciamo $P(A|B)$. L'informazione portata da $B$ ha modificato ciò che sappiamo su $A$.
La marginale $P(B)$ funge da **fattore di normalizzazione** e può essere calcolata come:
$$P(B) = \sum_{A} P(A, B) = \sum_{A} P(B|A) \cdot P(A)$$
**Esempio di indipendenza:** Lanciando un dado (evento $A$: esce "4") e una moneta (evento $B$: esce "TESTA"), anche se la moneta fosse uscita "CROCE", il dado avrebbe la stessa probabilità di risultare "4". Questo perché i due eventi sono indipendenti.
---
## 3. Introduzione alla stima Bayesiana
### 3.1 Motivazione
Negli approcci classici il vettore di parametri $\boldsymbol{\theta} \in \mathbb{R}^{d \times 1}$ è deterministico. Spesso, però, **ancora prima** di raccogliere i dati, disponiamo già di informazioni (o supposizioni) sui possibili valori di $\boldsymbol{\theta}$.
**Esempi:**
1. Stima della concentrazione di una sostanza nell'aria: si conosce già l'ordine di grandezza da studi precedenti.
2. Stima della probabilità che una moneta esca "TESTA": se supponiamo che la moneta non sia truccata, sappiamo che il valore sarà intorno a 0.5.
### 3.2 Parametro come variabile casuale
Ha quindi senso considerare $\boldsymbol{\theta}$ come una **variabile casuale**: in questo modo si assegna una **distribuzione di probabilità** $f_{\boldsymbol{\theta}}(\boldsymbol{\theta})$ che descrive i valori che crediamo $\boldsymbol{\theta}$ possa assumere.
- Si assegna **maggior probabilità** ai valori che si ritengono più plausibili.
- Si assegna **minor probabilità** ai valori che si ritengono poco plausibili.
**Esempio:** Se $\theta$ è la probabilità che una moneta esca "TESTA" e supponiamo che non sia truccata, la distribuzione $f_\theta(\theta)$ avrà il dominio $[0, 1]$, con il picco centrato in $\theta = 0.5$.
### 3.3 Stima a priori
Data $f_{\boldsymbol{\theta}}(\boldsymbol{\theta})$, si dispone già di una **stima del parametro prima di osservare i dati** (stima a-priori). Ad esempio, si può prendere il **valore atteso** della distribuzione a priori come valore puntuale della stima. L'**incertezza a-priori** è data dalla varianza di $\boldsymbol{\theta}$.
### 3.4 Effetto dell'osservazione dei dati
Con l'osservazione dei dati, ci si aspetta che:
1. La stima puntuale di $\boldsymbol{\theta}$ **cambi**.
2. L'incertezza sulla stima **decresca** (più informazioni portano a maggiore certezza).
Due elementi portano informazione:
1. La **distribuzione a-priori** $f_{\boldsymbol{\theta}}(\boldsymbol{\theta})$ sui possibili valori di $\boldsymbol{\theta}$.
2. L'informazione dai **dati**, espressa dalla **likelihood** $f_{Y|\boldsymbol{\theta}}(Y|\boldsymbol{\theta})$.
Ciò che veramente interessa è sapere quanto può valere $\boldsymbol{\theta}$ dato che si sono osservati i dati, ovvero la distribuzione **a-posteriori** $f_{\boldsymbol{\theta}|Y}(\boldsymbol{\theta}|Y)$.
---
## 4. Distribuzione a-posteriori
Applicando il **Teorema di Bayes** si uniscono le due fonti di informazione:
$$\underbrace{f_{\boldsymbol{\theta}|Y}(\boldsymbol{\theta}|Y)}_{\text{POSTERIOR}} = \frac{\overbrace{f_{Y|\boldsymbol{\theta}}(Y|\boldsymbol{\theta})}^{\text{LIKELIHOOD}} \cdot \overbrace{f_{\boldsymbol{\theta}}(\boldsymbol{\theta})}^{\text{PRIOR}}}{\underbrace{f_Y(Y)}_{\text{MARGINAL LIKELIHOOD}}}$$
**Osservazioni:**
- $f_{\boldsymbol{\theta}|Y}(\boldsymbol{\theta}|Y)$ è una distribuzione a-posteriori dei possibili valori di $\boldsymbol{\theta}$. Le probabilità, rispetto alla prior $f_{\boldsymbol{\theta}}(\boldsymbol{\theta})$, sono state **riallocate** dall'aver osservato i dati $Y$.
- Nel caso continuo: $f_Y(Y) = \int_{-\infty}^{+\infty} f_{Y|\boldsymbol{\theta}}(Y|\boldsymbol{\theta}) \, f_{\boldsymbol{\theta}}(\boldsymbol{\theta}) \, d\boldsymbol{\theta}$.
### 4.1 Forma della posterior
- In **generale**, non si può dire nulla sulla forma analitica della posterior $f_{\boldsymbol{\theta}|Y}(\boldsymbol{\theta}|Y)$.
- Un **caso fortunato** si verifica quando sia la prior $f_{\boldsymbol{\theta}}(\boldsymbol{\theta})$ che la likelihood $f_{Y|\boldsymbol{\theta}}(Y|\boldsymbol{\theta})$ sono **Gaussiane**: allora anche la posterior è Gaussiana.
- Quando la posterior ha la stessa **forma funzionale** della prior (es. entrambe Gaussiane), likelihood e prior si dicono **coniugate**.
- Se $f_Y(Y)$ non ha soluzione analitica (integrale intrattabile), si ricorre a tecniche numeriche come **Markov Chain Monte Carlo (MCMC)**.
- Un metodo alternativo (computazionalmente oneroso ma semplice) consiste nel **discretizzare** il range di $\boldsymbol{\theta}$ su una griglia di valori, valutando prior e likelihood su tale griglia. Questo metodo funziona bene solo se $\boldsymbol{\theta}$ ha pochi parametri (1 o 2).
---
## 5. Esempio: stima della probabilità di "TESTA" con approccio Bayesiano
### 5.1 Impostazione
Si vuole stimare la probabilità $\theta \equiv \pi$ che una moneta risulti "TESTA". Si lancia la moneta $N = 10$ volte ottenendo $N_s = 7$ "TESTA" e $N - N_s = 3$ "CROCE":
$$Y = \begin{pmatrix} 1 & 1 & 1 & 1 & 1 & 1 & 1 & 0 & 0 & 0 \end{pmatrix}^\top$$
I dati sono modellati come realizzazioni i.i.d. di una variabile casuale di Bernoulli:
$$y_i \sim \text{Bernoulli}(\pi), \quad f_y(y_i | \pi) = \pi^{y_i} \cdot (1 - \pi)^{1 - y_i}$$
La **likelihood** è:
$$f_{Y|\theta}(Y|\pi) = \prod_{i=1}^{N} \pi^{y_i} \cdot (1 - \pi)^{1 - y_i} = \pi^{\sum y_i} \cdot (1 - \pi)^{\sum (1 - y_i)} = \pi^{N_s} \cdot (1 - \pi)^{N - N_s}$$
### 5.2 Stima a massima verosimiglianza
La stima ML massimizza la likelihood: $\hat{\pi}_{ML} = N_s / N = 0.7$.
### 5.3 Informazione a priori
Se si ha buona confidenza che la moneta non sia truccata, si esprime questa credenza tramite una distribuzione $f_\theta(\pi)$ con picco su $\pi = 0.5$. La stima a priori sarà quindi $\hat{\pi}_{\text{PRIOR}} = 0.5$.
### 5.4 Stima a posteriori (MAP)
Unendo l'informazione di prior e likelihood, si ottiene la distribuzione a posteriori, che è un **compromesso** tra prior e likelihood.
> **Definizione:** La stima **MAP** (Maximum A Posteriori) è il valore $\hat{\boldsymbol{\theta}}_{\text{MAP}}$ che massimizza la distribuzione a posteriori $f_{\boldsymbol{\theta}|Y}(\boldsymbol{\theta}|Y)$.
Nell'esempio: $\hat{\pi}_{\text{MAP}} = 0.6$, valore intermedio tra la stima ML (0.7) e la prior (0.5). La procedura di stima Bayesiana **regolarizza** la stima.
La marginal likelihood si calcola come:
$$f_Y(Y) = \sum_{\pi} f_{Y|\theta}(Y|\pi) \cdot f_\theta(\pi) = 9.683 \times 10^{-4}$$
---
## 6. Stima ottima
### 6.1 Scelta del valore puntuale
Data la posterior $f_{\boldsymbol{\theta}|Y}(\boldsymbol{\theta}|Y)$, si hanno diverse scelte per ottenere un valore puntuale:
- **Stima MAP:** $\hat{\boldsymbol{\theta}} = \arg\max_{\boldsymbol{\theta}} f_{\boldsymbol{\theta}|Y}(\boldsymbol{\theta}|Y)$
- **Valore atteso a posteriori:** $\hat{\boldsymbol{\theta}} = \mathbb{E}[\boldsymbol{\theta} | Y]$, ovvero il valore atteso della posterior.
- Altre quantità come la mediana.
In generale, uno stimatore è una funzione $T$ dei dati: $\hat{\boldsymbol{\theta}} = T(\mathcal{D})$.
### 6.2 Stimatore ottimo di Bayes
Per il caso scalare, si vuole che $\hat{\theta}$ sia "vicino" a $\theta$. La distanza si quantifica tramite il **Mean Squared Error (MSE)**:
$$\text{MSE} = \mathbb{E}\left[(\hat{\theta} - \theta)^2\right] = \mathbb{E}\left[(T(\mathcal{D}) - \theta)^2\right]$$
> **Definizione:** Lo **stimatore ottimo di Bayes** è la funzione $T_{\text{opt}}$ tale che:
$$\mathbb{E}\left[(T_{\text{opt}}(\mathcal{D}) - \theta)^2\right] < \mathbb{E}\left[(T(\mathcal{D}) - \theta)^2\right], \quad \forall T(\mathcal{D})$$
Si dimostra che:
$$T_{\text{opt}}(Y) = \mathbb{E}[\boldsymbol{\theta} \mid \mathcal{D} = Y]$$
Ovvero lo stimatore che minimizza il MSE è il **valore atteso condizionato** (condizionato al fatto che i dati $\mathcal{D}$ abbiano assunto i valori osservati $Y$).
**Nota:** Nel caso vettoriale, il MSE diventa:
$$\text{MSE} = \text{tr}\left(\mathbb{E}\left[(\hat{\boldsymbol{\theta}} - \boldsymbol{\theta})(\hat{\boldsymbol{\theta}} - \boldsymbol{\theta})^\top\right]\right) = \mathbb{E}\left[\|\hat{\boldsymbol{\theta}} - \boldsymbol{\theta}\|_2^2\right]$$
---
## 7. Stima ottima: il caso Gaussiano
### 7.1 Impostazione
Supponiamo di avere un dato $y \sim \mathcal{N}(0, \lambda_{yy}^2)$ e un parametro ignoto scalare $\theta \sim \mathcal{N}(0, \lambda_{\theta\theta}^2)$. La distribuzione congiunta è ancora Gaussiana:
$$\begin{pmatrix} y \\ \theta \end{pmatrix} \sim \mathcal{N}\left(\begin{pmatrix} 0 \\ 0 \end{pmatrix}, \begin{pmatrix} \lambda_{yy}^2 & \lambda_{y\theta} \\ \lambda_{\theta y} & \lambda_{\theta\theta}^2 \end{pmatrix}\right)$$
con pdf congiunta:
$$f_{y\theta}(y, \theta) = \frac{1}{2\pi \sqrt{\det \Sigma}} \exp\left(-\frac{1}{2}(\mathbf{z} - \boldsymbol{\mu})^\top \Sigma^{-1} (\mathbf{z} - \boldsymbol{\mu})\right)$$
### 7.2 Posterior Gaussiana
Si dimostra che la posterior $f_{\theta|y}(\theta|y) = f_{y\theta}(y, \theta) / f_y(y)$ è ancora **Gaussiana** con:
- **Valore atteso a posteriori:**
$$\mu_{\theta|y} = \frac{\lambda_{\theta y}}{\lambda_{yy}^2} \cdot y$$
- **Varianza a posteriori:**
$$\lambda_{\theta|y}^2 = \lambda_{\theta\theta}^2 - \frac{\lambda_{\theta y}^2}{\lambda_{yy}^2}$$
**Osservazioni fondamentali:**
- Poiché $\frac{\lambda_{\theta y}^2}{\lambda_{yy}^2} > 0$, l'**incertezza a posteriori è sempre minore** di quella a priori: i dati riducono l'incertezza.
- Se $\lambda_{\theta y} = 0$ (il dato $y$ non porta informazioni su $\theta$), la stima di $\theta$ rimane quella a priori.
- Se $\lambda_{yy}^2$ è grande (dati molto incerti), la varianza diminuisce di poco.
### 7.3 Stima ottima nel caso Gaussiano
Dopo aver osservato il valore $y_1$ del dato $y$, la stima ottenuta dallo stimatore ottimo Bayesiano sarà:
$$\hat{\theta}_{\text{opt}} = \mathbb{E}[\theta | y = y_1] = \frac{\lambda_{\theta y}}{\lambda_{yy}^2} \cdot y_1$$
---
## 8. Stima ottima lineare
### 8.1 Motivazione
Non è sempre detto che $y$ e $\theta$ siano congiuntamente Gaussiane. Si vuole quindi trovare uno stimatore che **non faccia ipotesi** sulla distribuzione congiunta di $y$ e $\theta$, richiedendo solo l'esistenza di media e varianza.
### 8.2 Formulazione
Siano $y$ e $\theta$ due variabili casuali scalari con:
- $\mathbb{E}[y] = 0$, $\mathbb{E}[\theta] = 0$
- $\mathbb{E}[\theta^2] = \lambda_{\theta\theta}^2$, $\mathbb{E}[y^2] = \lambda_{yy}^2$, $\mathbb{E}[\theta y] = \lambda_{\theta y}$
Si vuole stimare $\theta$ tramite uno stimatore lineare:
$$\hat{\theta}_{\text{lin}} = \alpha \cdot y + \beta, \quad \alpha, \beta \in \mathbb{R}$$
### 8.3 Derivazione
Minimizzando il MSE $J(\alpha, \beta) = \mathbb{E}[(\alpha y + \beta - \theta)^2]$:
**Derivata rispetto a $\alpha$:**
$$\frac{\partial J}{\partial \alpha} = 0 \implies 2 \mathbb{E}[(\alpha y + \beta - \theta) \cdot y] = 0 \implies \alpha \lambda_{yy}^2 = \lambda_{\theta y} \implies \alpha = \frac{\lambda_{\theta y}}{\lambda_{yy}^2}$$
**Derivata rispetto a $\beta$:**
$$\frac{\partial J}{\partial \beta} = 0 \implies 2 \mathbb{E}[\alpha y + \beta - \theta] = 0 \implies \beta = 0$$
### 8.4 Risultato
> **Definizione:** Lo **stimatore lineare ottimo** (a media nulla) è dato da:
$$\hat{\theta}_{\text{opt}}^{\text{lin}} = \frac{\lambda_{\theta y}}{\lambda_{yy}^2} \cdot y$$
La varianza della stima è:
$$\text{Var}(\hat{\theta}_{\text{opt}}^{\text{lin}} - \theta) = \lambda_{\theta\theta}^2 - \frac{\lambda_{\theta y}^2}{\lambda_{yy}^2}$$
**Osservazione fondamentale:** Lo stimatore lineare ottimo **coincide** con lo stimatore ottimo di Bayes nel caso Gaussiano!
### 8.5 Proprietà
- Lo stimatore lineare ottimo **non fa ipotesi** sulla distribuzione di $y$ e $\theta$: assume solo che siano variabili casuali con certa media e varianza.
- Potrebbe esistere uno stimatore non lineare con MSE minore.
- **Tuttavia**, se $y$ e $\theta$ sono congiuntamente Gaussiani, **non esiste** nessuno stimatore migliore di quello lineare ottimo (poiché nel caso Gaussiano lo stimatore ottimo è già lineare).
### 8.6 Generalizzazione 1: valore atteso non nullo (caso scalare)
Se $\mathbb{E}[y] = \mu_y \neq 0$ e $\mathbb{E}[\theta] = \mu_\theta \neq 0$:
$$\hat{\theta}_{\text{opt}}^{\text{lin}} = \mu_\theta + \frac{\lambda_{\theta y}}{\lambda_{yy}^2} \cdot (y - \mu_y)$$
$$\text{Var}(\hat{\theta}_{\text{opt}}^{\text{lin}} - \theta) = \lambda_{\theta\theta}^2 - \frac{\lambda_{\theta y}^2}{\lambda_{yy}^2}$$
### 8.7 Generalizzazione 2: caso vettoriale
Con $Y \in \mathbb{R}^{N \times 1}$ e $\boldsymbol{\theta} \in \mathbb{R}^{d \times 1}$ vettoriali, e matrice di covarianza congiunta:
$$\text{Var}\begin{pmatrix} Y \\ \boldsymbol{\theta} \end{pmatrix} = \begin{pmatrix} \Lambda_{YY} & \Lambda_{Y\boldsymbol{\theta}} \\ \Lambda_{\boldsymbol{\theta} Y} & \Lambda_{\boldsymbol{\theta}\boldsymbol{\theta}} \end{pmatrix}$$
Lo stimatore lineare ottimo vettoriale è:
$$\hat{\boldsymbol{\theta}}_{\text{opt}}^{\text{lin}} = \boldsymbol{\mu}_{\boldsymbol{\theta}} + \Lambda_{\boldsymbol{\theta} Y} \cdot \Lambda_{YY}^{-1} \cdot (Y - \boldsymbol{\mu}_Y)$$
La varianza della stima:
$$\text{Var}(\hat{\boldsymbol{\theta}}_{\text{opt}}^{\text{lin}} - \boldsymbol{\theta}) = \Lambda_{\boldsymbol{\theta}\boldsymbol{\theta}} - \Lambda_{\boldsymbol{\theta} Y} \cdot \Lambda_{YY}^{-1} \cdot \Lambda_{Y\boldsymbol{\theta}}$$
---
## 9. Connessione con il Filtro di Kalman
Le formule dello stimatore lineare ottimo ammettono una **forma ricorsiva**: appena arriva un nuovo dato osservato, si aggiorna la stima corrente **senza ri-considerare** tutti i dati precedenti.
Queste espressioni ricorsive sono alla base del **Filtro di Kalman**, un algoritmo che stima lo stato $\mathbf{x}_t$ di un sistema dinamico:
- Lo stato $\mathbf{x}_t$ e l'uscita $y_t$ del sistema dinamico lineare sono trattati come **variabili casuali**.
- Si vuole stimare lo stato $\mathbf{x}_t$ (l'incognita $\boldsymbol{\theta}$) sulla base dello **stato stimato al tempo precedente** (stima a priori) e dei **dati che man mano arrivano** dalle misure dei sensori $y_t$ (dati osservati).
In sostanza, il Filtro di Kalman è un'applicazione ricorsiva dello stimatore lineare ottimo Bayesiano ai sistemi dinamici.
---
## Riepilogo delle formule principali
| Concetto | Formula |
|---|---|
| Teorema di Bayes | $P(A\|B) = \frac{P(B\|A) \cdot P(A)}{P(B)}$ |
| Posterior Bayesiana | $f_{\boldsymbol{\theta}\|Y} = \frac{f_{Y\|\boldsymbol{\theta}} \cdot f_{\boldsymbol{\theta}}}{f_Y}$ |
| Stimatore ottimo | $T_{\text{opt}}(Y) = \mathbb{E}[\boldsymbol{\theta} \mid Y]$ |
| Stimatore lin. ottimo (scalare) | $\hat{\theta}^{\text{lin}} = \frac{\lambda_{\theta y}}{\lambda_{yy}^2} \cdot y$ |
| Varianza posteriori (Gauss.) | $\lambda_{\theta\|y}^2 = \lambda_{\theta\theta}^2 - \frac{\lambda_{\theta y}^2}{\lambda_{yy}^2}$ |
| Stim. lin. ottimo (vettoriale) | $\hat{\boldsymbol{\theta}}^{\text{lin}} = \boldsymbol{\mu}_\theta + \Lambda_{\theta Y} \Lambda_{YY}^{-1}(Y - \boldsymbol{\mu}_Y)$ |
