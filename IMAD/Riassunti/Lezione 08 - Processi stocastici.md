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
