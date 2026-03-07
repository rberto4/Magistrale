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
