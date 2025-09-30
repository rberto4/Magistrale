# Capitolo 06 — Fondamenti di Machine Learning  
Questo capitolo riassume i concetti, le formule e le ricette operative fondamentali per affrontare problemi di apprendimento supervisionato (regressione e classificazione), con particolare attenzione a: definizioni di errore, bias–variance, learning curves, overfitting, regolarizzazione, validazione (cross-validation), metodi di ottimizzazione (gradient descent), regressione logistica, e legami tra MLE e LS.

---

## 6.1 Problema di apprendimento supervisionato — notazione di base

- Dati di training: $\mathcal{D} = \{(\phi_i,y_i)\}_{i=1}^N$, con $\phi_i\in\mathbb{R}^d$ vettori di feature e $y_i$ target (reale per regressione, discreto per classificazione).
- Spazio delle ipotesi: $\mathcal{H}=\{h(\cdot;\theta)\}$, parametro $\theta\in\mathbb{R}^p$.
- Obiettivo: trovare $h\in\mathcal{H}$ che minimizzi l'errore **out-of-sample**.

**Errore in-sample (training)**  
$$
E_{\text{in}}(\theta) \;=\; \frac{1}{N}\sum_{i=1}^N \ell\big(y_i,\; h(\phi_i;\theta)\big)
$$

**Errore out-of-sample (generalizzazione attesa)**  
$$
E_{\text{out}}(h) \;=\; \mathbb{E}_{\phi,y}\big[\ell\big(y,\; h(\phi)\big)\big]
$$

> Scopo pratico: stimare $E_{\text{out}}$ tramite metodi di validazione e scegliere modello/iperparametri che minimizzino $E_{\text{out}}$.

---

## 6.2 Bias–Variance decomposition (intuito + formula)

Consideriamo la previsione $\hat g_{\mathcal D}(\phi)$ ottenuta da un algoritmo che usa dataset $\mathcal D$. Fissato un punto $\phi$, e assumendo $y=f(\phi)+\eta$ con rumore $\eta$ zero-mean e $\mathrm{Var}(\eta)=\sigma^2$, si ha:

**Decomposizione del MSE atteso (per un dato $\phi$)**
$$
\mathbb{E}_{\mathcal D,\eta}\big[\big(\hat g_{\mathcal D}(\phi)-y\big)^2\big]
=
\underbrace{\big(\mathbb{E}_{\mathcal D}[\hat g_{\mathcal D}(\phi)]-f(\phi)\big)^2}_{\text{bias}^2(\phi)}
\;+\;
\underbrace{\mathbb{E}_{\mathcal D}\big[\big(\hat g_{\mathcal D}(\phi)-\mathbb{E}_{\mathcal D}[\hat g_{\mathcal D}(\phi)]\big)^2\big]}_{\text{variance}(\phi)}
\;+\;
\underbrace{\sigma^2}_{\text{rumore irreducibile}}
$$

**Significato pratico**
- Bias: errore medio dovuto alla limitata capacità di $\mathcal H$ di approssimare $f$.
- Varianza: sensitività dell'algoritmo ai dati campionati.
- Rumore: non rimovibile con nessun modello.

**Regola operativa**: modelli più complessi tendono ad avere bias basso ma varianza alta; regolarizzazione e più dati riducono varianza.

---

## 6.3 Learning curves — interpretazione pratica

- Le learning curves mostrano $E_{\text{in}}$ e stima di $E_{\text{out}}$ in funzione della dimensione del training set $N$.
- Interpretazione:
  - Se $E_{\text{in}}\approx E_{\text{out}}$ e alto $\Rightarrow$ **alto bias** → servono modelli più complessi o features migliori.
  - Se $E_{\text{in}}\ll E_{\text{out}}$ → **alta varianza** → servono più dati, regolarizzazione o modelli meno complessi.

---

## 6.4 Overfitting / Underfitting — rimedi pratici

- **Overfitting**: buon fit su training, scarsa generalizzazione. Rimedi: regolarizzazione, ridurre complessità, più dati, data augmentation, ensemble (bagging).
- **Underfitting**: modello troppo semplice → aumentare complessità (features non lineari, polinomi, modelli più ricchi).

---

## 6.5 Regolarizzazione — formulazione e soluzioni chiuse

**Ridge regression ($L_2$)** — problema di regressione lineare:
- Modello lineare: $y = \Phi\theta + \varepsilon$, con $\Phi\in\mathbb{R}^{N\times d}$ matrice dei regressori.
- Funzione costo con penalità:
$$
J_{\text{ridge}}(\theta) \;=\; \frac{1}{N}\|y-\Phi\theta\|^2_2 \;+\; \lambda\|\theta\|^2_2
$$
- Soluzione chiusa:
$$
\hat\theta_{\text{ridge}} \;=\; \big(\Phi^\top\Phi + N\lambda I\big)^{-1}\Phi^\top y
$$
(N.B.: alcune notazioni assorbono $N$ dentro $\lambda$.)

**Lasso ($L_1$)**  
$$
J_{\text{lasso}}(\theta) \;=\; \frac{1}{N}\|y-\Phi\theta\|^2_2 \;+\; \lambda\|\theta\|_1
$$
- Non ha soluzione chiusa; si risolve con algoritmi iterativi (coordinate descent, proximal methods). Favorisce sparse solutions.

**Elastic-net**: combinazione $L_1$ e $L_2$:
$$
\Omega(\theta) = \alpha\|\theta\|_1 + (1-\alpha)\|\theta\|_2^2
$$

**Interpretazione bayesiana (utile per collegamenti)**  
Ridge = MAP con prior gaussiano $\theta\sim\mathcal{N}(0,\tau^2 I)$ (la penalità $L_2$ corrisponde a prior normale). Questo collega regolarizzazione e stima Bayesiana (vedi Capitolo 07).

---

## 6.6 Stima a massima verosimiglianza (MLE) e minimi quadrati (LS)

**Modello lineare Gaussiano**: $y=\Phi\theta+\varepsilon$, $\varepsilon\sim\mathcal N(0,\sigma^2 I)$.

**Likelihood**
$$
p(y|\theta) = (2\pi\sigma^2)^{-N/2}\exp\Big(-\frac{1}{2\sigma^2}\|y-\Phi\theta\|^2\Big)
$$

**MLE** (massimizza la likelihood) equivalgono ai minimi quadrati:
$$
\hat\theta_{ML} = \arg\min_\theta \|y-\Phi\theta\|^2
\quad\Rightarrow\quad
\hat\theta_{ML} = (\Phi^\top\Phi)^{-1}\Phi^\top y \quad (\text{se invertibile})
$$

**Varianza dello stimatore**
$$
\mathrm{Cov}(\hat\theta_{ML}) = \sigma^2 (\Phi^\top\Phi)^{-1}
$$

---

## 6.7 Algoritmi numerici: gradient descent e Newton

**Gradient descent (batch)** per $J(\theta)=\frac{1}{N}\|y-\Phi\theta\|^2$:
- Gradiente:
$$
\nabla_\theta J = -\frac{2}{N}\Phi^\top (y-\Phi\theta)
$$
- Aggiornamento iterativo:
$$
\theta^{(k+1)} = \theta^{(k)} - \eta\,\nabla_\theta J\big(\theta^{(k)}\big)
$$
(dove $\eta$ è il learning rate).

**Newton / IRLS (per funzioni non quadratiche)**: update basato su Hessiana:
$$
\theta^{(k+1)} = \theta^{(k)} - H^{-1}(\theta^{(k)}) \nabla J(\theta^{(k)})
$$
utile per problemi logistic / GLM (vedi sezione successiva).

---

## 6.8 Regressione logistica (classificazione binaria)

**Modello**
- $p(y=1|\phi;\theta) = \sigma(\phi^\top\theta)$, con $\sigma(a)=\frac{1}{1+e^{-a}}$ (sigmoide).
- Log-likelihood su dati i.i.d.:
$$
\ell(\theta) = \sum_{i=1}^N \big[ y_i\log\sigma(\phi_i^\top\theta) + (1-y_i)\log(1-\sigma(\phi_i^\top\theta))\big]
$$

**Gradiente**
$$
\nabla\ell(\theta) = \sum_{i=1}^N \big(y_i - \sigma(\phi_i^\top\theta)\big)\phi_i = \Phi^\top (y - \hat p)
$$
dove $\hat p_i=\sigma(\phi_i^\top\theta)$.

**Hessiana**
$$
H(\theta) = -\Phi^\top R \Phi, \qquad R=\operatorname{diag}\big(\hat p_i(1-\hat p_i)\big)
$$

**Aggiornamento Newton / IRLS**
$$
\theta^{(k+1)} = \theta^{(k)} + ( \Phi^\top R \Phi)^{-1}\Phi^\top (y-\hat p)
$$

**Regolarizzazione** si ottiene aggiungendo $\lambda\|\theta\|_2^2$ e alterando Hessiana/gradiente.

---

## 6.9 Validazione & Cross-validation (pratiche)

- **Hold-out**: dividere i dati in training / validation / test.
- **K-fold CV**: dividere in $K$ partizioni; per ogni fold si usa $K-1$ per training e 1 per validation; la stima CV è la media:
$$
\widehat{E}_{\text{CV}} = \frac{1}{K}\sum_{k=1}^K E_{\text{val}}^{(k)}
$$
- **LOOCV**: caso $K=N$ (leave-one-out); può essere molto costoso ma ha bassa varianza in certi casi.
- Scegli modello/iperparametri minimizzando $\widehat{E}_{\text{CV}}$.

---

## 6.10 Raccomandazioni pratiche (estratte e riassunte dalle slide)
- Sempre **depola** i dati (rimuovi media) quando appropriato.
- Esamina **ACF/PACF** per serie temporali (se rilevante).
- Usa **regularization** per combattere overfitting; scegli $\lambda$ via CV.
- Per modelli lineari, preferisci Ridge (chiuso) se vuoi stabilità numerica; Lasso per sparsity.
- Per classificazione, preferisci regolarizzazione L2 per stabilità; L1 per feature selection.

---

# Capitolo 07 — Fondamenti di stima Bayesiana  
Questo capitolo raccoglie il formalismo bayesiano: definizioni (prior, likelihood, posterior), stime puntuali (MAP, MMSE), esempi coniugati (Beta–Bernoulli), il caso gaussiano lineare (posteriors gaussiani), la stima ottima lineare e la connessione con il filtro di Kalman.

---

## 7.1 Teorema di Bayes — definizione centrale

Per parametri $\theta$ e dati osservati $Y$:
$$
p(\theta|Y) \;=\; \frac{p(Y|\theta)\,p(\theta)}{p(Y)}
\quad\text{dove}\quad
p(Y)=\int p(Y|\theta)\,p(\theta)\,d\theta
$$

- $p(\theta)$ = **prior** (informazione a-priori su $\theta$).  
- $p(Y|\theta)$ = **likelihood** (informazione portata dai dati).  
- $p(\theta|Y)$ = **posterior** (credence aggiornata su $\theta$).  
- $p(Y)$ = marginal likelihood (evidence) — utile per selezione modello.

---

## 7.2 Scelte puntuali dalla posterior (MAP, MMSE, mediana)

- **MAP (Maximum A Posteriori)**:
$$
\hat\theta_{\text{MAP}} = \arg\max_\theta p(\theta|Y) = \arg\max_\theta \big\{ \log p(Y|\theta) + \log p(\theta) \big\}
$$
- **MMSE (Bayes estimator sotto MSE)**: minimizza l'errore quadratico atteso, è la **media a-posteriori**:
$$
\hat\theta_{\text{MMSE}} = \mathbb{E}[\theta|Y]
$$
- **Nota**: se la posterior è gaussiana, media = moda = mediana, quindi MAP = MMSE.

---

## 7.3 Esempio conjugato: Beta–Bernoulli (moneta)

- Dati: $y_i\sim \text{Bernoulli}(\pi)$, $i=1,\dots,N$. Successi $S=\sum y_i$.
- Prior coniugato: $\pi\sim \mathrm{Beta}(\alpha,\beta)$ con pdf
$$
p(\pi) \propto \pi^{\alpha-1}(1-\pi)^{\beta-1}
$$
- Posterior (coniugata):
$$
\pi|y \sim \mathrm{Beta}(\alpha+S,\; \beta+N-S)
$$

**Stime**:
- Posterior mean (MMSE):
$$
\mathbb{E}[\pi|y] = \frac{\alpha+S}{\alpha+\beta+N}
$$
- MAP (modalità della Beta):
$$
\hat\pi_{\text{MAP}} = \frac{\alpha+S-1}{\alpha+\beta+N-2} \qquad \text{(se $\alpha+S>1$ e $\beta+N-S>1$)}
$$

**Interpretazione**: il prior “aggiunge” pseudo-successi e pseudo-fallimenti, regolarizzando la stima.

---

## 7.4 Caso gaussiano lineare (prior gaussiano + likelihood gaussiana) — formula chiave

Modello:
$$
y = \Phi\theta + \varepsilon, \qquad \varepsilon \sim \mathcal N(0,\sigma^2 I)
$$
Prior su $\theta$ gaussiano:
$$
\theta \sim \mathcal N(\mu_0,\Sigma_0)
$$

Allora la posterior è gaussiana: $\theta|y \sim \mathcal N(\mu_n,\Sigma_n)$ con

**Posterior precision / covarianza**
$$
\Sigma_n \;=\; \Big(\Sigma_0^{-1} + \frac{1}{\sigma^2}\Phi^\top\Phi\Big)^{-1}
$$

**Posterior mean**
$$
\mu_n \;=\; \Sigma_n\Big(\Sigma_0^{-1}\mu_0 + \frac{1}{\sigma^2}\Phi^\top y\Big)
$$

**Interpretazione**
- La precisione a-posteriori è la somma della precisione prior $\Sigma_0^{-1}$ e della precisione informativa dei dati $(1/\sigma^2)\Phi^\top\Phi$.
- MAP = $\mu_n$ (nel caso gaussiano la media coincide con la moda).

**Collegamento a Ridge**  
Se $\mu_0=0$ e $\Sigma_0 = \tau^2 I$, allora la MAP risolve:
$$
\hat\theta_{\text{MAP}} = \arg\min_\theta \frac{1}{2\sigma^2}\|y-\Phi\theta\|^2 + \frac{1}{2\tau^2}\|\theta\|^2,
$$
che è equivalente alla ridge con $\lambda=\sigma^2/\tau^2$.

---

## 7.5 Stimatori ottimi e perdite — perché scegliere la media o la moda

- **MSE (quadratico)** → minimizzatore: posterior mean (MMSE).
- **0–1 loss** (classificazione) → minimizzatore: posterior mode / Bayes decision rule appropriata.
- In pratica: scegli la regola di decisione coerente con il criterio di performance che userai (MSE, error rate, cost-sensitive, ecc.).

---

## 7.6 Stima ottima lineare — formula generale e connessione con Kalman

Caso generale lineare-gaussiano: supponiamo $\theta\sim \mathcal N(\mu_\theta,\Sigma_\theta)$ e osservazioni
$$
Y = H\theta + \eta, \qquad \eta\sim\mathcal N(0,\Sigma_\eta)
$$

**Stima condizionata (formule note)**
$$
\mathbb{E}[\theta|Y] = \mu_\theta + \Sigma_\theta H^\top \big(H\Sigma_\theta H^\top + \Sigma_\eta\big)^{-1}(Y - H\mu_\theta)
$$

**Forma equivalente (guadagno di Kalman)**:
- Definire guadagno $K = \Sigma_\theta H^\top (H\Sigma_\theta H^\top + \Sigma_\eta)^{-1}$, allora
$$
\hat\theta = \mu_\theta + K (Y - H\mu_\theta)
$$

**Connessione con il filtro di Kalman**:
- In problemi dinamici (stato che evolve nel tempo), le formule di aggiornamento della stima a posteriori quando arriva un nuovo dato sono ricorsive e usano esattamente un termine di guadagno (Kalman gain).
- Le equazioni del filtro nascono dal caso gaussiano lineare applicato in forma ricorsiva (predizione + aggiornamento).

---

## 7.7 Recursive update (intuizione operativa)

Dai risultati gaussiani, la precisione a-posteriori soddisfa:
$$
\Sigma_n^{-1} = \Sigma_0^{-1} + \frac{1}{\sigma^2}\Phi^\top\Phi
$$
Questa somma di precisioni è alla base degli aggiornamenti ricorsivi (si possono usare formule tipo Sherman–Morrison per aggiornare inversi in tempo reale). In pratica:
- appena arriva un nuovo dato $(\phi_{t},y_t)$ si aggiorna la media e la covarianza senza riesaminare tutti i dati: schema predizione + aggiornamento (Kalman-like).

---

## 7.8 Model evidence & selezione modello (nota rapida)

- Marginal likelihood (evidence):
$$
p(y) = \int p(y|\theta)p(\theta)d\theta
$$
- Permette confrontare modelli (Bayes factors). In pratica, il calcolo esatto può essere costoso; si usano approssimazioni (Laplace), criteri informativi (BIC), o cross-validation.

---

## 7.9 Raccomandazioni pratiche tratte dalle slide

- Se hai conoscenza pregressa, codificala in un prior informativo; altrimenti usa priors deboli/coniugati per stabilità.
- Per il modello lineare gaussiano, il calcolo è analitico e stabile: sfruttalo per interpretabilità.
- Per modelli complessi (non coniugati), usare MCMC o VB per approssimare la posterior.
- Ricorda il legame tra Bayes MAP e regolarizzazione: il bayesiano dà una motivazione principled per i termini di penalità (prior).

---

# Processi stocastici (Lez.08) + Famiglie di modelli (Lez.09)
> Questa versione espande il formulario precedente: ogni formula è seguita da
> spiegazione del significato, quando e come si usa, proprietà pratiche e
> riferimenti operativi tratti dalle slide.
## 1) Notazione e primi concetti

- Indicheremo con $v(t,s)$ il processo stocastico al tempo $t$ e per esito $s$; spesso scriviamo semplicemente $v(t)$ quando la dipendenza da $s$ è implicita.
- Una *realizzazione* (o *path*) è $v(t,s=\bar s)$: è la serie temporale osservata.

---

## 2) Valore atteso (media) e sua stima

**Definizione teorica (media di insieme)**
$$
m_v = \mathbb{E}_s\big[v(t,s)\big]
$$

**Significato**
- $m_v$ è la media (tendente centrale) del processo: valore atteso *alla stessa istante* su tutte le realizzazioni possibili (ensemble).
- È **indipendente da $t$** se il processo è stazionario (in senso debole).

**Stima pratica (media temporale su una realizzazione)**
$$
\bar v_N \;=\; \frac{1}{N}\sum_{t=0}^{N-1} v(t)
$$

**Quando usarla / proprietà**
- $\bar v_N$ è lo stimatore che usiamo quando abbiamo **una sola serie** lunga.  
- Se il processo è **ergodico nella media**, allora
  $$
  \lim_{N\to\infty}\bar v_N = m_v \quad\text{(quasi certamente)}.
  $$
- In pratica: si depolarizza la serie sottraendo $\bar v_N$ prima di calcolare autocovarianze/PSD.

---

## 3) Autocovarianza e autocorrelazione

**Autocovarianza (per pss, dipende solo dal lag $\tau$)**  
$$
\gamma_{vv}(\tau) = \mathbb{E}\big[(v(t)-m_v)\,(v(t+\tau)-m_v)\big]
$$

**Autocorrelazione normalizzata**
$$
\rho_{vv}(\tau) = \frac{\gamma_{vv}(\tau)}{\gamma_{vv}(0)}
$$

**Significato**
- Misura la dipendenza lineare del processo fra due istanti separati di $\tau$ campioni.
- $\gamma_{vv}(0)$ è la varianza del processo.
- Se $\gamma_{vv}(\tau)$ è alto per $\tau$ grandi -> il processo ha “memoria lunga”; se tende a 0 rapidamente -> “dimentica” velocemente.

**Proprietà utili**
- $\gamma_{vv}(0)\ge 0$.
- $|\gamma_{vv}(\tau)|\le \gamma_{vv}(0)$.
- $\gamma_{vv}(\tau)=\gamma_{vv}(-\tau)$ (pari).

---

## 4) Stimatori campionari della media e dell'autocovarianza (pratica)

**Media campionaria**
$$
\bar v_N = \frac{1}{N}\sum_{t=0}^{N-1} v(t)
$$
- Consistente sotto ergodicità.

**Stimatore "non distorto" dell'autocovarianza** (usato nelle teorie; denominatore $N-\tau$)
$$
\hat\gamma_{vv}(\tau) \;=\; \frac{1}{N-\tau}\sum_{t=0}^{N-\tau-1} \big(v(t)-\bar v_N\big)\,\big(v(t+\tau)-\bar v_N\big),\qquad 0\le\tau<N
$$

**Stimatore alternativo (più comune in calcolo numerico: denominatore $N$)**
$$
\hat\gamma'_{vv}(\tau) \;=\; \frac{1}{N}\sum_{t=0}^{N-\tau-1} \big(v(t)-\bar v_N\big)\,\big(v(t+\tau)-\bar v_N\big)
$$

**Differenze e proprietà**
- $\hat\gamma_{vv}(\tau)$ è *non distorto* (cioè $\mathbb{E}[\hat\gamma_{vv}(\tau)]=\gamma_{vv}(\tau)$) salvo effetti dovuti a $\bar v_N$; tuttavia la varianza di questo stimatore cresce per $\tau$ grande perché il numero di termini è $N-\tau$.
- $\hat\gamma'_{vv}(\tau)$ è **biased**:  
  $$
  \mathbb{E}[\hat\gamma'_{vv}(\tau)] = \frac{N-\tau}{N}\,\gamma_{vv}(\tau),
  $$
  ma ha **varianza più piccola** per $\tau$ grandi (utile in pratica).  
- Entrambi sono *consistenti* per $N\to\infty$ a $\tau$ fissato (sotto ipotesi di ergodicità).

**Pratica**
- Per stime robuste su dataset finiti, spesso si usa la versione con denominatore $N$ (più stabile numericamente), oppure si applicano finestre sui lag (Blackman–Tukey).

---

## 5) Ergodicità — cosa garantisce

**Ergodicità nella media**
$$
\lim_{N\to\infty} \frac{1}{N}\sum_{t=0}^{N-1} v(t) = m_v \quad \text{(q.c.)}
$$

**Condizioni sufficienti (slide)**
- $\gamma_{vv}(0)<\infty$ (varianza finita);
- $\lim_{\tau\to\infty}\gamma_{vv}(\tau)=0$ (la memoria si annulla).

**Significato pratico**
- Se il processo è ergodico, *una sola* realizzazione lunga è sufficiente per stimare i parametri ensemble (media, autocovarianza, PSD).
- Se non si può dimostrare l'ergodicità, spesso la si *assume* per procedere con stime temporali.

---

## 6) Trasformata 𝒵 e DTFT — definizioni e uso

**Trasformata 𝒵 bilatera**
$$
G(z) \;=\; \mathcal{Z}\{g(t)\} \;=\; \sum_{t=-\infty}^{+\infty} g(t)\, z^{-t}, \qquad z\in\mathbb{C}
$$

**DTFT (trasformata di Fourier a tempo discreto)**
$$
G(e^{j\omega}) \;=\; \sum_{t=-\infty}^{+\infty} g(t)\, e^{-j\omega t}
$$
- Si ottiene valutando la 𝒵 sulla circonferenza unitaria $z=e^{j\omega}$.

**Significato / uso**
- La 𝒵 è lo strumento per analizzare sistemi LTI discreti: si usano polinomi in $z^{-1}$ per rappresentare filtri e sistemi.
- La DTFT è usata per studiare la risposta in frequenza (modulo/fase) per segnali/processi discreti.

**Nota su ROC e stabilità**
- La 𝒵 converge solo in una regione di convergenza (ROC). Per sistemi causali e stabili, la ROC è esterna al polo più esterno (forma pratica: poli all'interno della circonferenza unitaria implicano stabilità).  
- In pratica, per processi stazionari ci interessa valutare su $|z|=1$ (DTFT).

---

## 7) Densità spettrale di potenza (PSD) — definizione e inversione

**Definizione (DTFT di $\gamma$)**
$$
\Gamma_{vv}(\omega) \;=\; \sum_{\tau=-\infty}^{+\infty} \gamma_{vv}(\tau)\, e^{-j\omega \tau}
$$

**Inversione (autocovarianza dalla PSD)**
$$
\gamma_{vv}(\tau) \;=\; \frac{1}{2\pi} \int_{-\pi}^{\pi} \Gamma_{vv}(\omega)\, e^{j\omega \tau}\, d\omega
$$

**Varianza come area sotto la PSD**
$$
\gamma_{vv}(0) \;=\; \frac{1}{2\pi}\int_{-\pi}^{\pi}\Gamma_{vv}(\omega)\, d\omega
$$

**Significato**
- La PSD dice come la varianza del processo è ripartita tra le frequenze: picchi della PSD indicano frequenze dominanti.
- Proprietà: $\Gamma_{vv}(\omega)$ è reale, pari e non-negativa.

**Esempio: rumore bianco**
- Se $e(t)\sim\text{WN}(0,\lambda^2)$ allora
  $$
  \gamma_{ee}(\tau) = \lambda^2 \delta_{\tau,0} \quad\Rightarrow\quad \Gamma_{ee}(\omega)=\lambda^2 \quad(\text{costante})
  $$
- Quindi il white noise ha PSD piatta (tutte le frequenze contengono la stessa potenza).

---

## 8) Stima della PSD: periodogramma e metodi avanzati

**Periodogramma (definizione)**
$$
I_N(\omega) \;=\; \frac{1}{N}\left|\sum_{t=0}^{N-1} v(t)\,e^{-j\omega t}\right|^2
$$

**Proprietà pratiche**
- $I_N(\omega)$ è uno **stimatore non consistente** in senso di varianza (la varianza non tende a 0 per $N\to\infty$), e può essere molto rumoroso.
- L'aspettazione $\mathbb{E}[I_N(\omega)]$ è una versione “smoothed” della PSD vera: quindi il periodogramma è **bias-variabile** — dipende dall'”effetto finestra” implicito.

**Strategie di miglioramento**
- **Welch / Bartlett**: dividere la serie in segmenti (con eventuale overlap e finestratura), calcolare periodogramma su ogni segmento e fare la media. Riduce la varianza (proporzionalmente al numero di segmenti) ma peggiora la risoluzione in frequenza.
- **Blackman–Tukey**: stimare prima $\hat\gamma(\tau)$, applicare una finestra sui lag (per tagliare l'incertezza sui lag lontani) e poi calcolare la DTFT di quel $\hat\gamma(\tau)$ finestrato. Permette di controllare esplicitamente lo smoothing in frequenza (trade-off bias/varianza).

**Leakage & finestratura**
- La scelta della finestra (rectangular, Hamming, Hann, Blackman, ecc.) governa il compromesso tra **main-lobe width** (risoluzione) e **side-lobe level** (leakage).

---

## 9) Sistemi LTI discreti e relazioni spettrali ingresso–uscita

**Convoluzione (tempo)**
$$
y(t) = \sum_{k=0}^{\infty} g_k\, u(t-k)
$$

**Funzione di trasferimento (𝒵)**
$$
G(z) = \sum_{k=0}^{\infty} g_k z^{-k},\qquad Y(z)=G(z)\,U(z)
$$

**Relazione PSD (ingresso stocastico)**
- Cross-spettro ingresso–uscita:
  $$
  \Gamma_{yu}(\omega) = G(e^{j\omega})\,\Gamma_{uu}(\omega)
  $$
- PSD di uscita:
  $$
  \Gamma_{yy}(\omega) = |G(e^{j\omega})|^2\,\Gamma_{uu}(\omega) \quad\text{(se ingresso e rumore sono non correlati)}
  $$

**Significato pratico**
- Il sistema “filtra” la potenza dell'ingresso: frequenze dove $|G|^2$ è grande vengono amplificate nella PSD di uscita.

---

## 10) Famiglie di modelli stocastici (Lezione 09) — definizioni, formule e significato

> Qui riportiamo le forme canoniche usate nelle slide e il loro ruolo nel descrivere processi e sistemi.

---

### 10.1) MA — Moving Average (ordine $n_c$)

**Definizione tempo**
$$
y(t) = \sum_{i=0}^{n_c} c_i\, e(t-i) \quad\text{(di norma $c_0=1$ per fissare scala)}
$$

**Forma 𝒵**
$$
Y(z) = C(z)\,E(z),\qquad C(z)=\sum_{i=0}^{n_c} c_i z^{-i}
$$

**Autocovarianza**
$$
\gamma_{yy}(\tau) = \sigma_e^2 \sum_{i=0}^{n_c-\tau} c_i\, c_{i+\tau},\qquad 0\le\tau\le n_c
$$
e $\gamma_{yy}(\tau)=0$ per $\tau>n_c$.

**Significato / uso**
- Modella processi "a memoria finita": l'autocovarianza si annulla dopo $n_c$ lag.  
- Utile per fenomeni dove solo pochi ritardi del rumore influenzano l'uscita.

---

### 10.2) AR — AutoRegressive (ordine $n_a$)

**Definizione tempo**
$$
y(t) = \sum_{i=1}^{n_a} a_i\, y(t-i) + e(t)
$$

**Forma 𝒵**
$$
\underbrace{\big(1 - a_1 z^{-1} - \dots - a_{n_a} z^{-n_a}\big)}_{A(z)}\,Y(z) = E(z)
\quad\Rightarrow\quad
Y(z) = \frac{1}{A(z)} E(z)
$$

**Condizione di stazionarietà / stabilità**
- Il processo è stazionario se **tutti i poli** (soluzioni di $A(z)=0$) hanno modulo $<1$ (cioè si trovano all'interno della circonferenza unitaria nel piano $z$).
- Esempio AR(1): $y(t)=a_1 y(t-1)+e(t)$ è stazionario se $|a_1|<1$.

**Equazioni di Yule–Walker (relazione tra $\gamma$ e $a_i$)**  
Per $\tau\ge 0$:
$$
\gamma_{yy}(\tau) = \sum_{i=1}^{n_a} a_i\,\gamma_{yy}(\tau-i) + \sigma_e^2\delta_{\tau,0}
$$

**Significato / uso**
- Modelli con memoria potenzialmente infinita (MA∞).  
- La PACF (partial autocorrelation) si annulla dopo ordine $n_a$, utile per selezione d'ordine.

---

### 10.3) ARMA (ordine $n_a,n_c$)

**Definizone**
$$
\underbrace{A(z)}_{AR}\,Y(z) = \underbrace{C(z)}_{MA}\,E(z)
\quad\Rightarrow\quad Y(z)=\frac{C(z)}{A(z)}E(z)
$$
dove $A(z)=1-\sum_{i=1}^{n_a} a_i z^{-i}$ e $C(z)=\sum_{i=0}^{n_c} c_i z^{-i}$.

**Significato**
- Combina le due dinamiche: parte autoregressiva e parte media mobile.
- Un ARMA stazionario può essere espresso come MA($\infty$) (espansione in serie geometrica), se $A(z)$ è invertibile.

---

### 10.4) Modelli ingresso–uscita (ARX, ARMAX, OE, BJ)

**ARX** (AutoRegressive with eXogenous input)  
$$
A(z)\,y(t) = z^{-k}B(z)\,u(t) + e(t)
$$
- $B(z)=\sum b_j z^{-j}$; $k$ = ritardo puro (dead-time).
- $H(z) = \dfrac{B(z)z^{-k}}{A(z)}$ è la dinamica ingresso→uscita, rumore additivo bianco $e(t)$.

**ARMAX**  
$$
A(z)\,y(t) = z^{-k}B(z)\,u(t) + C(z)\,e(t)
$$
- Qui il rumore è modellato con $C(z)$ (parte MA). Migliora modellazione se il rumore non è bianco.

**OE (Output Error)**  
$$
y(t) = \frac{B(z)}{F(z)}\, z^{-k} u(t) + e(t)
$$
- Il rumore $e(t)$ entra *dopo* la dinamica: utile quando l'unico errore è di misura in uscita.

**Box–Jenkins (BJ)**  
$$
y(t) = \frac{B(z)}{F(z)} z^{-k} u(t) \;+\; \frac{C(z)}{D(z)} e(t)
$$
- Massima flessibilità: dinamica ingresso-output e modello del rumore parametrizzati indipendentemente.

**Quando scegliere cosa**
- ARX: semplice, utile se rumore ≈ bianco. Stima lineare (LS) efficiente.
- ARMAX / BJ: se il rumore ha struttura, miglior accuratezza, ma identificazione più complessa (non sempre LS diretto).
- OE: preferito quando rumore è solo misurazione e non è filtrato dalla dinamica.

---

## 11) Relazioni tra PSD e modelli (uso operativo)

- Per un modello $Y(z)=\dfrac{C(z)}{A(z)}E(z)$ con $E$ white noise $\sigma_e^2$, la PSD è:
  $$
  \Gamma_{yy}(\omega) \;=\; \big|C(e^{j\omega})/A(e^{j\omega})\big|^2 \, \sigma_e^2
  $$
- Questo è utile per: riconoscere se i dati hanno una risonanza (poli vicini alla circonferenza unitaria), per scegliere ordine e progettare filtri.

---

## 12) Indicatori per scelta d'ordine (ACF, PACF)

- **ACF (autocorrelation function)**: per MA($n_c$) si annulla dopo $n_c$ lags → utile per sospettare un MA.
- **PACF (partial autocorrelation)**: per AR($n_a$) la PACF è zero per lag $>\!n_a$ → utile per sospettare AR.
- Regola pratica (Box-Jenkins): usare ACF + PACF per ipotizzare struttura AR/MA/ARMA.

---

## 13) Periodogramma: dettagli pratici (bias/varianza/leakage)

- Il periodogramma è semplicissimo da calcolare (DFT e modulo quadro) ma ha **alta varianza** e problemi di leakage.
- **Windowing** in tempo (prima di calcolare DFT) riduce leakage (side-lobes) ma allarga la main-lobe (peggiora risoluzione).
- **Welch**: riduce la varianza mediando periodogrammi di segmenti (trade-off risoluzione/varianza).
- **Blackman–Tukey**: stima autocovarianza fino a lag max e prende DTFT di quella finestrata → controlla smoothing in frequenza.

---

## 14) Note pratiche di identificazione / identifiability (richiami dalle slide)

- Esistono ridondanze: *es.* in MA si può moltiplicare $C(z)$ per $α$ e dividere $\sigma_e^2$ per $α^2$ lasciando il processo invariato. Per questo spesso si fissa $c_0=1$ per evitare sovraparametrizzazione.
- Stabilità (poli) e invertibilità (zeri nel MA) sono concetti importanti durante la scelta del modello e la stima.

---

## 15) Esempio rapido (MA1) — come si calcola la PSD da $\gamma$

Prendiamo $y(t)=e(t)+c_1 e(t-1)$ con $e(t)\sim WN(0,1)$:

- Autocovarianze:
  - $\gamma(0)=1+c_1^2$
  - $\gamma(1)=c_1$
  - $\gamma(\tau)=0$ per $|\tau|>1$.

- PSD:
  $$
  \Gamma_{yy}(\omega) = 1 + 2c_1\cos\omega + c_1^2 = |1 + c_1 e^{-j\omega}|^2.
  $$

Interpretazione: per $c_1>0$ si enfatizzano certe frequenze (basse), per $c_1<0$ si enfatizzano altre (alte).

---

## 16) Brevi raccomandazioni operative

- Prima di stimare un modello, **depola** (sottrai media) la serie se la media non è zero.
- Esamina ACF e PACF per ipotesi d'ordine.
- Per PSD preferisci stime con smoothing (Welch / Blackman–Tukey) per dataset finiti.
- Scegli ARX se vuoi stima via LS (semplice), ARMAX/BJ se rumore strutturato (ma servono metodi numerici diversi, es. PEM).

---
