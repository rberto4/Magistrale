## Indice cap 19-20-21
1. [Introduzione al Reinforcement Learning](#1-introduzione-al-reinforcement-learning)
2. [Terminologie e Framework del RL](#2-terminologie-e-framework-del-rl)
3. [Reward Hypothesis e Ritorno Scontato](#3-reward-hypothesis-e-ritorno-scontato)
4. [Trade-off Esplorazione/Sfruttamento e Policy](#4-trade-off-esplorazionesfruttamento-e-policy)
5. [Funzioni di Valore e Equazione di Bellman](#5-funzioni-di-valore-e-equazione-di-bellman)
6. [Apprendimento delle Funzioni di Valore: Monte Carlo e TD](#6-apprendimento-delle-funzioni-di-valore-monte-carlo-e-td)
7. [Q-Learning](#7-q-learning)
8. [Deep Q-Learning](#8-deep-q-learning)
9. [Policy Gradient e REINFORCE](#9-policy-gradient-e-reinforce)
10. [Metodi Actor-Critic](#10-metodi-actor-critic)
11. [Advantage Actor-Critic (A2C)](#11-advantage-actor-critic-a2c)
12. [Proximal Policy Optimization (PPO)](#12-proximal-policy-optimization-ppo)
13. [Reinforcement Learning from Human Feedback (RLHF)](#13-reinforcement-learning-from-human-feedback-rlhf)
14. [RLHF in Pratica](#14-rlhf-in-pratica)
15. [AI ed Etica](#15-ai-ed-etica)
---
## 1. Introduzione al Reinforcement Learning
### Motivazione
Molti problemi reali non riguardano la predizione di etichette, ma il **prendere decisioni**: un robot che naviga un edificio, un agente che gioca a scacchi, un sistema di guida autonoma. In questi contesti le azioni influenzano lo stato futuro del mondo, il feedback è spesso ritardato o sparso, e non esiste un'"etichetta corretta" per ogni azione. Serve quindi un framework per imparare ad agire quando gli esiti dipendono da una **sequenza di decisioni**: questo framework è il **Reinforcement Learning (RL)**.
### Cos'è il Reinforcement Learning
Il RL è un paradigma di apprendimento in cui un **agente** impara interagendo con un **ambiente** tramite prove ed errori, ricevendo **ricompense** (positive o negative) come unico feedback. L'agente non riceve istruzioni esplicite su cosa fare: deve scoprire da solo quali azioni portano alla massima ricompensa cumulativa nel lungo periodo.
Un'analogia efficace è quella di un giocatore che affronta un videogioco per la prima volta senza conoscere le regole: prova un comando, osserva il risultato (guadagna o perde punti), e nel tempo impara la strategia ottimale.
### Applicazioni
Il RL viene applicato in moltissimi ambiti: addestramento di agenti che giocano a scacchi e Go, guida autonoma (accelerare, frenare, cambiare corsia), pricing dinamico (tariffe ride-sharing, biglietti aerei), robotica di magazzino (ottimizzazione dei percorsi pick-and-deliver) e **fine-tuning di LLM** come ChatGPT, per allinearne le risposte alle preferenze umane.
### Confronto con altri paradigmi
A differenza dell'apprendimento supervisionato (dove una rete confronta le predizioni con le etichette corrette) e dell'apprendimento non supervisionato (dove non esiste un segnale esplicito), nel RL l'agente interagisce con un ambiente, sceglie azioni e riceve ricompense. Non esistono etichette, ma solo un segnale di reward che guida l'apprendimento.
---
## 2. Terminologie e Framework del RL
### Componenti fondamentali
- **Agente (Agent)**: il decision-maker, l'entità che apprende e sceglie le azioni. Può essere un robot, un programma, un modello di linguaggio.
- **Ambiente (Environment)**: tutto ciò che è esterno all'agente e con cui esso interagisce (un videogioco, un labirinto, il mondo reale).
- **Azione (Action)**: le scelte disponibili per l'agente (muoversi a sinistra, saltare, non fare nulla).
- **Stato (State)**: una "fotografia" della situazione corrente dell'agente (posizione nel labirinto, livello di batteria, frame del gioco).
- **Ricompensa (Reward)**: un feedback numerico che indica quanto bene l'agente ha agito. Reward positivo = buona azione, reward negativo = cattiva azione.
### Interazione agente-ambiente
L'interazione avviene su **time step discreti** $t = 0, 1, 2, 3, \ldots$ Ad ogni time step $t$:
- L'agente osserva lo stato corrente $S_t$ dall'ambiente
- L'agente seleziona un'azione $A_t$ in base a $S_t$
Al time step successivo $t+1$:
- L'agente riceve una ricompensa numerica $R_{t+1}$
- L'agente si trova in un nuovo stato $S_{t+1}$
### Traiettoria
La sequenza di interazioni genera una **traiettoria** (o episodio):
$$
S_0, A_0, R_1, S_1, A_1, R_2, S_2, A_2, R_3, \ldots
$$
Ad esempio, in un videogioco l'agente riceve il frame $S_t$, prende l'azione "muovi a destra" $A_t$, il gioco avanza al frame $S_{t+1}$ e restituisce una ricompensa $R_{t+1} = +1$ (il personaggio non è morto).
---
## 3. Reward Hypothesis e Ritorno Scontato
### Reward Hypothesis
L'obiettivo dell'agente è massimizzare la **ricompensa cumulativa** nel lungo periodo, chiamata **ritorno atteso (expected return)**. L'idea centrale del RL è la **reward hypothesis**: tutti gli obiettivi possono essere descritti come la massimizzazione del ritorno atteso cumulativo. Il ritorno cumulativo ad un dato time step $t$ è:
$$
G_t = R_{t+1} + R_{t+2} + R_{t+3} + \cdots = \sum_{k=0}^{\infty} R_{t+k+1}
$$
### Ritorno scontato (Discounted Return)
Non tutte le ricompense hanno lo stesso peso: una ricompensa immediata è generalmente più certa e quindi più "preziosa" di una futura. Per questo si introduce il **fattore di sconto** $\gamma \in [0, 1]$ che pesa le ricompense future:
$$
G_t = R_{t+1} + \gamma R_{t+2} + \gamma^2 R_{t+3} + \cdots = \sum_{k=0}^{\infty} \gamma^k R_{t+k+1}
$$
- **$\gamma$ vicino a 0**: l'agente è "miope", preferisce ricompense immediate. Le potenze $\gamma^1, \gamma^2, \gamma^3, \ldots$ scendono a zero molto rapidamente.
- **$\gamma$ vicino a 1**: l'agente tiene conto anche delle ricompense a lungo termine.
### Esempio
Un topolino in un labirinto vuole mangiare più formaggio possibile evitando il gatto. Il formaggio vicino al gatto è di maggior valore, ma il rischio è alto: il fattore di sconto $\gamma$ vicino a zero causa un rapido decadimento dei reward futuri, rendendo meno attraente il formaggio lontano/rischioso. Se $\gamma$ è vicino a 1, l'agente valuta anche le ricompense future e potrebbe rischiare di più.
---
## 4. Trade-off Esplorazione/Sfruttamento e Policy
### Exploration vs Exploitation
L'**exploration/exploitation trade-off** è un aspetto centrale del RL. L'analogia è la scelta di un ristorante:
- **Exploitation (sfruttamento)**: andare sempre nello stesso ristorante dove il cibo è buono, ma rischiare di perdere un ristorante potenzialmente migliore.
- **Exploration (esplorazione)**: provare ristoranti nuovi, con il rischio di un'esperienza negativa ma anche la possibilità di una scoperta eccezionale.
Un agente RL deve **bilanciare** esplorazione e sfruttamento per massimizzare la ricompensa attesa cumulativa: sfruttare le conoscenze acquisite e al contempo esplorare per scoprire azioni potenzialmente migliori.
### Policy (Politica)
La **policy** $\pi$ è il "cervello" dell'agente: una funzione che determina quale azione intraprendere dato lo stato corrente. Definisce il comportamento dell'agente in ogni momento. Formalmente è un mapping dagli stati alle probabilità di selezionare ogni azione possibile: $\pi(a \mid s) = P[A_t = a \mid S_t = s]$. La policy viene appresa durante il training.
Esistono due tipi di policy:
- **Deterministica**: dato uno stato, restituisce sempre la stessa azione: $a = \pi(s)$. Esempio: "vedi una moneta a destra" → "muovi a destra".
- **Stocastica**: restituisce una distribuzione di probabilità sulle azioni: $\pi(a \mid s) = P[A \mid s]$. Esempio: "moneta a destra ma nemico in avvicinamento" → distribuzione: sinistra 0.1, destra 0.3, salta 0.6.
### Approcci per trovare la policy ottimale $\pi^*$
Risolvere un problema di RL significa trovare una policy che massimizza la ricompensa cumulativa. Due famiglie di approcci:
- **Metodi value-based**: insegnano all'agente quali stati/azioni sono più "preziosi". L'agente apprende una funzione di valore e poi sceglie l'azione che porta agli stati con il valore più alto. Esempio: Q-learning.
- **Metodi policy-based**: insegnano direttamente all'agente quale azione prendere in ogni stato, senza passare per una funzione di valore. Esempio: Policy Gradient.
---
## 5. Funzioni di Valore e Equazione di Bellman
### Funzioni di Valore
Gli algoritmi RL stimano **funzioni di valore** che valutano quanto è buono trovarsi in un certo stato (o compiere una certa azione in un certo stato), in termini di ricompense future attese. Esistono due tipi:
**State-value function** $v_\pi(s)$: quanto ritorno totale possiamo aspettarci dallo stato $s$ seguendo la policy $\pi$:
$$
v_\pi(s) = \mathbb{E}_\pi[G_t \mid S_t = s]
$$
Esempio: in un videogioco, una posizione vicina al goal ha valore alto, una trappola ha valore basso.
**Action-value function** $q_\pi(s, a)$: quanto è buono compiere l'azione $a$ nello stato $s$ e poi seguire la policy $\pi$:
$$
q_\pi(s, a) = \mathbb{E}_\pi[G_t \mid S_t = s, A_t = a]
$$
Esempio: girare a sinistra potrebbe essere meglio che girare a destra → Q-value più alto per "sinistra".
Nei metodi value-based, la policy ottimale si ricava direttamente dalla funzione di valore ottimale:
$$
\pi^*(s) = \arg\max_a q^*(s, a)
$$
Trovare una funzione di valore ottimale equivale a trovare una policy ottimale.
### Equazione di Bellman
L'**equazione di Bellman** è una relazione ricorsiva che semplifica il calcolo del valore di uno stato, evitando di sommare tutte le ricompense future. Anziché calcolare $v(s) = R_{t+1} + R_{t+2} + \cdots$, si decompone il valore in ricompensa immediata più valore scontato dello stato successivo:
$$
v(s) = \mathbb{E}_\pi\big[R_{t+1} + \gamma \, v(S_{t+1}) \mid S_t = s\big]
$$
Questa è la chiave di volta del RL: il valore di uno stato dipende dalla ricompensa immediata e dal valore (scontato) dello stato successivo. Esempio numerico: se un agente ha una sequenza di reward tutti pari a $-1$ e $\gamma = 1$, allora $v(S_t) = -1 + 1 \cdot v(S_{t+1})$, il che evita di ricalcolare tutta la somma da capo.
---
## 6. Apprendimento delle Funzioni di Valore: Monte Carlo e TD
### Monte Carlo
Il metodo **Monte Carlo** attende la fine di un episodio completo, calcola il ritorno effettivo $G_t$ e lo usa come target per aggiornare la stima del valore:
$$
v(S_t) \leftarrow v(S_t) + \alpha \big[G_t - v(S_t)\big]
$$
dove $\alpha$ è il learning rate. Si aspetta la fine dell'episodio per avere il ritorno completo, poi si aggiorna la stima. Esempio: un topolino che vaga nel labirinto termina l'episodio con un ritorno $G_t = 3$; se la stima precedente era $v(0) = 0$ e $\alpha = 0.1$, il nuovo valore diventa $v(0) = 0 + 0.1 \cdot 3 = 0.3$.
### Temporal Difference (TD) Learning
Il metodo **TD** aggiorna la stima del valore **ad ogni singolo step**, senza aspettare la fine dell'episodio. Si usa l'equazione di Bellman come approssimazione:
$$
v(S_t) \leftarrow v(S_t) + \alpha \big[R_{t+1} + \gamma \, v(S_{t+1}) - v(S_t)\big]
$$
Il termine $R_{t+1} + \gamma \, v(S_{t+1})$ è detto **TD target**, e la differenza $R_{t+1} + \gamma \, v(S_{t+1}) - v(S_t)$ è il **TD error**. Rispetto a Monte Carlo, TD impara più velocemente (aggiorna ad ogni step) ma ha un bias maggiore (usa una stima $v(S_{t+1})$ invece del ritorno reale). Esempio: con la stessa situazione del topolino, dopo un solo step con reward $R_1=1$ e $v(1)=0$, si ottiene $v(0) = 0 + 0.1 \cdot [1 + 1 \cdot 0 - 0] = 0.1$.
---
## 7. Q-Learning
### Concetto
Il **Q-Learning** è un metodo value-based che usa l'approccio **TD** per addestrare una **action-value function** (funzione Q). Internamente, la funzione Q è una **tabella (Q-table)** in cui ogni cella corrisponde al valore di una coppia stato-azione. Data una Q-table ottimale, l'agente sa esattamente quale azione è la migliore in ogni stato.
### Epsilon-Greedy Policy
Prima di descrivere l'algoritmo, serve una policy per bilanciare esplorazione e sfruttamento. La **epsilon-greedy** ($\varepsilon$-greedy) funziona così:
- Con probabilità $1 - \varepsilon$: **exploitation** (azione greedy), l'agente sceglie l'azione con il Q-value più alto
- Con probabilità $\varepsilon$: **exploration**, l'agente sceglie un'azione casuale
Durante il training si parte con un valore alto $\varepsilon = 1.0$ (massima esplorazione). Col passare del tempo $\varepsilon$ viene ridotto esponenzialmente, man mano che la Q-table diventa più precisa.
### Regola di aggiornamento
Ad ogni step, dopo aver osservato la transizione $(S_t, A_t, R_{t+1}, S_{t+1})$, la Q-table si aggiorna con:
$$
q(S_t, A_t) \leftarrow q(S_t, A_t) + \alpha \Big[R_{t+1} + \gamma \max_{a'} q(S_{t+1}, a') - q(S_t, A_t)\Big]
$$
Il termine $R_{t+1} + \gamma \max_{a'} q(S_{t+1}, a')$ è il **TD target**: la ricompensa ottenuta più il miglior Q-value raggiungibile dallo stato successivo (scontato di $\gamma$). La differenza tra TD target e stima corrente guida l'aggiornamento.
### Esempio: il topolino nel labirinto
Un topolino ha a disposizione un piccolo formaggio (+1), un veleno (-10) e una grande quantità di formaggio (+10). L'episodio termina se il topolino mangia il veleno, il grande formaggio, oppure supera 5 step. Parametri: $\alpha = 0.1$, $\gamma = 0.99$.
- **Step 1**: la Q-table è inizializzata a zero.
- **Step 2**: $\varepsilon = 1.0$, azione casuale "destra", il topolino mangia il piccolo formaggio ($R = +1$). $q(\text{Stato 1}, \text{destra}) = 0 + 0.1 \cdot [1 + 0.99 \cdot 0 - 0] = 0.1$.
- **Step 3**: $\varepsilon$ diminuisce ($\varepsilon = 0.99$), azione "giù", il topolino muore ($R = -10$). $q(\text{Stato 2}, \text{giù}) = 0 + 0.1 \cdot [-10 + 0.99 \cdot 0 - 0] = -1$.
- A training concluso, la Q-table è ottimale e l'agente esegue l'**inferenza** così: dato $s$, sceglie $a^* = \arg\max_a q(s, a)$.
---
## 8. Deep Q-Learning
### Motivazione
Il Q-Learning con Q-table funziona quando lo spazio degli stati è **discreto e piccolo**. In giochi complessi come Atari, il numero di stati possibili è astronomico: in Space Invaders, con immagini $160 \times 210 \times 3$ a 256 livelli di intensità, gli stati possibili sono $256^{160 \times 210 \times 3} = 256^{100800}$, un numero superiore al numero di atomi nell'universo ($\approx 10^{80}$). Costruire una Q-table per questi spazi è impossibile.
### Idea chiave
Il **Deep Q-Learning** sostituisce la Q-table con una **rete neurale profonda** parametrizzata che approssima la funzione Q:
$$
q(s, a; \theta) \approx \text{ritorno futuro atteso}
$$
La rete (DQN, Deep Q-Network) prende in input uno stato e restituisce i Q-value per tutte le azioni possibili.
### Architettura DQN
Per giochi come Space Invaders:
- **Input**: uno stack di **4 frame** consecutivi, che costituisce lo stato. Si usano 4 frame perché un singolo frame non fornisce informazioni sul moto (direzione, velocità).
- **Rete**: strati **convoluzionali** (per estrarre feature spaziali) seguiti da strati **fully connected**
- **Output**: un vettore di Q-value, uno per ogni azione possibile (sinistra, destra, spara, nulla)
I Q-value stimati dalla DQN vengono usati dalla policy epsilon-greedy per scegliere l'azione.
### Due reti: Online e Target
Il DQN utilizza **due reti** con la stessa architettura:
- **Online network** $q(s, a; \theta)$: la rete effettivamente addestrata (con SGD). Stima i Q-value allo stato corrente $s$.
- **Target network** $q(s', a'; \theta^-)$: una copia congelata della online network, aggiornata lentamente tramite **EMA (Exponential Moving Average)** (come in DINO). Stima i Q-value di tutte le azioni nello stato successivo $s'$.
Il **TD target** è calcolato con la target network:
$$
y = r + \gamma \max_{a'} q(s', a'; \theta^-)
$$
Se $s'$ è uno stato terminale, il target diventa semplicemente $y = r$.
### Loss e addestramento
La loss del DQN è un semplicissimo **MSE** tra il TD target e la predizione della online network:
$$
L(\theta) = \mathbb{E}\Big[\big(y - q(s, a; \theta)\big)^2\Big]
$$
La online network osserva i frame $s = [\text{frame}_{t-3}, \text{frame}_{t-2}, \text{frame}_{t-1}, \text{frame}_t]$, mentre la target network osserva lo stato successivo $s' = [\text{frame}_{t-2}, \text{frame}_{t-1}, \text{frame}_t, \text{frame}_{t+1}]$. La separazione tra le due reti stabilizza il training: se la stessa rete fosse usata sia per la predizione sia per il target, il target cambierebbe continuamente causando instabilità.
---
## 9. Policy Gradient e REINFORCE
### Metodi Policy-Based
Nei metodi **policy-based** si apprende direttamente la policy ottimale $\pi^*$ senza passare per una funzione di valore (a differenza del Q-learning). La policy è parametrizzata da una rete neurale $\pi_\theta$ che restituisce una distribuzione di probabilità sulle azioni:
$$
\pi_\theta(s) = P[A \mid s; \theta]
$$
L'obiettivo è massimizzare la performance (ritorno atteso) della policy parametrizzata usando tecniche di ottimizzazione. I metodi **Policy Gradient** sono un sottoinsieme dei metodi policy-based che cercano direttamente la policy ottimale ottimizzando $\theta$ con **gradient ascent**.
### Esempio: CartPole
Un classico ambiente di test è **CartPole**: l'agente deve bilanciare un palo verticale su un carrello mobile su un binario 1D, applicando forze verso sinistra o destra. Lo stato è un vettore di 4 valori continui: posizione del carrello, velocità del carrello, angolo del palo, velocità angolare del palo. Le azioni sono due: spingere a sinistra (0) oppure a destra (1). Il reward è +1 per ogni time step in cui il palo resta in piedi (angolo $< 15°$). L'episodio termina quando il palo cade troppo o il carrello esce dal campo.
La rete è un fully-connected network: prende in input i 4 valori dello stato, e tramite un softmax finale produce la distribuzione sulle azioni $\pi_\theta(a \mid s)$. L'obiettivo è controllare questa distribuzione in modo che le azioni buone vengano campionate più frequentemente.
### Raccolta delle traiettorie
L'addestramento inizia con la **raccolta di traiettorie**:
- Si parte dallo stato iniziale $s_0$
- Si campiona un'azione $a_t \sim \pi_\theta(\cdot \mid s_t)$ dalla policy corrente
- Si applica l'azione, ottenendo lo stato successivo $s_{t+1}$ e la ricompensa $r_t$
- Si memorizza la tupla $(s_t, a_t, r_t)$
- Si ripete fino alla fine dell'episodio
Si ottiene una traiettoria: $(s_0, a_0, r_0), (s_1, a_1, r_1), (s_2, a_2, r_2), \ldots$
Per ogni time step $t$ si calcola anche il ritorno:
$$
G_t = r_t + \gamma r_{t+1} + \gamma^2 r_{t+2} + \cdots
$$
### Algoritmo REINFORCE (Monte Carlo Policy Gradient)
L'algoritmo **REINFORCE** è un metodo policy-gradient di tipo Monte Carlo:
1. Usare la policy $\pi_\theta$ per raccogliere un episodio (traiettoria) $\tau$
2. Usare l'episodio per stimare il gradiente $g = \nabla_\theta J(\theta)$
3. Aggiornare i pesi della policy: $\theta \leftarrow \theta + \alpha g$
La **funzione obiettivo** per una data traiettoria è:
$$
J(\theta) = \sum_{t=0}^{T} \log \pi_\theta(a_t \mid s_t) \, G_t
$$
dove $\log \pi_\theta(a_t \mid s_t)$ è la log-probabilità dell'azione presa e $G_t$ è il ritorno a partire dal time step $t$. L'intuizione è:
- Se il ritorno $G_t$ è alto → **aumenta** la probabilità della coppia (stato, azione)
- Se il ritorno $G_t$ è basso → **diminuisci** la probabilità della coppia (stato, azione)
Anziché massimizzare $J(\theta)$, in pratica si minimizza la loss (aggiungendo un segno negativo):
$$
L = -\sum_{t=0}^{T} \log \pi_\theta(a_t \mid s_t) \, G_t
$$
L'algoritmo viene ripetuto su diverse traiettorie per ottenere una policy ben addestrata.
---
## 10. Metodi Actor-Critic
### Problema della varianza in REINFORCE
REINFORCE è affetto da **alta varianza**: le traiettorie raccolte possono variare enormemente a causa della stocasticità dell'ambiente (eventi casuali durante un episodio) e della stocasticità della policy (soprattutto all'inizio, quando la policy è quasi casuale). Di conseguenza, il ritorno calcolato a partire dallo stesso stato può essere molto diverso tra episodi diversi — è come mirare a un bersaglio mobile. Per ridurre la varianza e accelerare il training, si combinano i metodi policy-based e value-based: i **metodi Actor-Critic**.
### Architettura Actor-Critic
L'apprendimento viene suddiviso in due componenti che collaborano:
- **Actor (la policy)** $\pi_\theta(a \mid s)$: decide quale azione compiere. Viene aggiornato con metodi policy-gradient. L'obiettivo è massimizzare la ricompensa attesa.
- **Critic (lo stimatore del valore)**: apprende la funzione di valore $v(s)$ o la funzione Q $q(s,a)$. Usa il TD learning come nel Deep Q-Learning.
L'analogia è quella di un videogiocatore (Actor) affiancato da un amico (Critic) che gli dice "quella mossa è stata buona/cattiva", guidandolo senza aspettare la fine della partita.
### Meccanismo
L'actor seleziona un'azione $A_t = a$ nello stato $S_t = s$. L'ambiente restituisce la ricompensa $R_{t+1} = r$ e il nuovo stato $S_{t+1} = s'$. Il critic calcola il **TD error**:
$$
\delta_t = r + \gamma \max_{a'} q(s', a'; w^-) - q(s, a; w)
$$
dove $w$ e $w^-$ sono i parametri della online e target critic network. Questo errore TD:
- **Aggiorna il critic** (per migliorare le stime del valore)
- **Guida l'actor** (per aggiustare la policy)
L'actor aggiorna la policy con la funzione obiettivo:
$$
J(\theta) = \mathbb{E}\big[\log \pi_\theta(a_t \mid s_t) \, \delta_t\big]
$$
### Confronto con REINFORCE
In REINFORCE la funzione obiettivo usa il **ritorno completo** $G_t$ (Monte Carlo):
$$
J(\theta) = \mathbb{E}\big[\log \pi_\theta(a_t \mid s_t) \, G_t\big]
$$
Non c'è un critic, si usa solo il ritorno dopo la fine dell'episodio. Nell'Actor-Critic, l'actor usa il **TD error** $\delta_t$ del critic al posto del ritorno completo $G_t$. Questo porta a **varianza più bassa** (il TD error è meno rumoroso perché ha un solo step di randomness) ma **bias più alto** (perché si usa un'approssimazione one-step TD anziché il ritorno reale).
---
## 11. Advantage Actor-Critic (A2C)
### Funzione Advantage
L'**Advantage Actor-Critic (A2C)** stabilizza ulteriormente il training usando la **funzione Advantage** come segnale per il critic. Intuitivamente, l'Advantage misura "quanto è meglio compiere l'azione $a$ nello stato $s$ rispetto al valore medio di quello stato":
$$
A(s, a) = q(s, a) - v(s)
$$
dove $q(s, a)$ è il Q-value dell'azione $a$ nello stato $s$ e $v(s)$ è il valore medio dello stato. Se $A > 0$, l'azione è migliore della media; se $A < 0$, è peggiore.
### Funzione obiettivo A2C
La funzione obiettivo dell'actor diventa:
$$
J(\theta) = \mathbb{E}\big[\log \pi_\theta(a_t \mid s_t) \, A(s_t, a_t)\big]
$$
Usare l'Advantage come segnale di guida riduce ulteriormente la varianza rispetto al semplice TD error, perché centra il segnale rispetto al valore medio dello stato: le azioni vengono valutate in relazione a ciò che è "normale" per quel dato stato.
---
## 12. Proximal Policy Optimization (PPO)
### Motivazione
La **Proximal Policy Optimization (PPO)** è un framework che migliora la stabilità del training limitando la dimensione degli aggiornamenti della policy ad ogni epoca. Il problema è che aggiornamenti troppo grandi possono essere catastrofici: un passo troppo grande nello spazio dei parametri può far "cadere da una scogliera" la performance. PPO garantisce che le modifiche alla policy restino piccole e controllate.
### Clipped Surrogate Objective
PPO introduce una nuova funzione obiettivo chiamata **Clipped Surrogate Objective**:
$$
L^{CLIP}(\theta) = \mathbb{E}\Big[\min\big(r_t(\theta) A_t, \; \text{clip}(r_t(\theta), 1-\varepsilon, 1+\varepsilon) A_t\big)\Big]
$$
dove:
- $\theta$: parametri della policy corrente
- $\theta_{old}$: parametri della policy usata per raccogliere i dati (fissi durante l'aggiornamento)
- $A_t$: stima dell'Advantage
- $\varepsilon$: parametro di clipping (tipicamente 0.1–0.3)
### Probability Ratio
Il **rapporto di probabilità** tra policy corrente e vecchia è:
$$
r_t(\theta) = \frac{\pi_\theta(a_t \mid s_t)}{\pi_{\theta_{old}}(a_t \mid s_t)}
$$
Questo rapporto quantifica quanto la policy è cambiata:
- $r_t > 1$: l'azione $a_t$ nello stato $s_t$ è **più probabile** nella policy corrente rispetto alla vecchia
- $0 < r_t < 1$: l'azione è **meno probabile** nella policy corrente
Il rapporto è un modo semplice per stimare la divergenza tra vecchia e nuova policy.
### Operatore di Clipping
L'operatore $\text{clip}(r_t(\theta), 1-\varepsilon, 1+\varepsilon)$ restringe il rapporto nell'intervallo $[1-\varepsilon, 1+\varepsilon]$:
- Se $r_t < 1-\varepsilon$: $\text{clip}(r_t) = 1-\varepsilon$
- Se $r_t > 1+\varepsilon$: $\text{clip}(r_t) = 1+\varepsilon$
Il clipping limita quanto la policy può migliorare o peggiorare in un singolo aggiornamento, impedendo cambiamenti troppo aggressivi. Esempio: con $\varepsilon = 0.2$, il rapporto è ristretto a $[0.8, 1.2]$.
### Funzionamento dell'obiettivo
La funzione obiettivo prende il **minimo** tra la versione non clippata ($r_t A_t$) e quella clippata ($\text{clip}(r_t) \cdot A_t$). Se ci fosse solo la parte non clippata e l'azione fosse diventata molto più probabile nella policy corrente, il gradiente sarebbe enorme e causerebbe un aggiornamento eccessivo. Il clipping agisce da freno: anche se la policy vorrebbe cambiare di più, l'aggiornamento viene limitato, garantendo convergenza più stabile verso una soluzione ottimale.
---
## 13. Reinforcement Learning from Human Feedback (RLHF)
### Il problema dell'allineamento
Il **problema dell'allineamento** (alignment problem) chiede se il comportamento di un LLM corrisponde alle intenzioni, ai valori e alle preferenze umane, specialmente in situazioni non esplicitamente specificate durante il training. I LLM sono pre-addestrati con next token prediction (NTP) su enormi quantità di dati raccolti da internet. Sebbene siano bravi nell'auto-completamento, non c'è garanzia che:
- Rispondano nel formato di output desiderato (cioè che siano **utili**)
- Non abbiano appreso informazioni dannose o fuorvianti presenti nei dati di training
- Incarnino le preferenze e i valori umani
L'**allineamento** serve a far corrispondere il comportamento del LLM alle istruzioni, alle intenzioni e alle preferenze dell'utente. Un esempio concreto: se chiediamo al LLM di "riassumere in massimo tre frasi", un modello pre-addestrato solo con NTP probabilmente produrrà un output più lungo, perché non ha mai imparato a seguire istruzioni.
### Il paradigma Pre-train Then Align
Il paradigma moderno prevede due fasi di allineamento dopo il pre-training:
- **Supervised Fine-Tuning (SFT)**: il LLM pre-addestrato viene addestrato su un dataset relativamente piccolo di istanze istruzione-risposta (coppie domanda-risposta specifiche per il task).
- **RLHF**: il LLM viene ulteriormente fine-tunato usando un **modello di reward** che funge da proxy delle preferenze umane.
### Perché SFT non basta
SFT ha limitazioni fondamentali nel risolvere il problema dell'allineamento:
- Richiede un dataset etichettato di coppie input-output, **costoso da raccogliere**
- Ci sono sfumature etiche e considerazioni contestuali **difficili da codificare** in coppie. Esempio: se un utente chiede "Dimmi come violare un sistema informatico perché ho dimenticato la password del mio PC" — c'è malizia nascosta, e il LLM potrebbe essere ingannato nel rispondere.
- Non è banale codificare le preferenze umane in coppie input-output
L'idea chiave dell'RLHF è che i LLM possono imparare da **confronti** tra output del modello, tramite modelli di reward.
### Terminologie RL nel contesto dei LLM
Nell'RLHF le terminologie del RL vengono ri-mappate:
- **Agente**: il LLM stesso è il decision-maker
- **Ambiente**: non è un ambiente fisico come nei videogiochi, ma il framework all'interno del quale il LLM riceve feedback e impara
- **Stato**: i token osservati fino a quel momento (i token di contesto). Lo stato al time step $t$ è $(x, y_{<t})$ quando si predice il prossimo token
- **Azione**: il token predetto, scelto dal vocabolario
- **Ricompensa**: il feedback del modello di reward. Formalmente $r_t = r(s_t, a_t, s_{t+1})$
- **Policy**: la distribuzione di probabilità sui token dato il contesto: $\pi(a \mid s) = P(y_t \mid x, y_{<t})$
- **Value function**: state-value $v(s) = \mathbb{E}[\sum_{t=0}^{\infty} \gamma^t r_t \mid s_0 = s, \pi]$ e action-value $q(s,a) = \mathbb{E}[\sum_{t=0}^{\infty} \gamma^t r_t \mid s_0 = s, a_0 = a, \pi]$
### Addestramento del Reward Model
Un **reward model** è un LLM pre-addestrato che viene fine-tunato per mappare una coppia (input, output) in uno scalare che misura quanto bene l'output si allinea al comportamento desiderato. Per addestrarlo si raccoglie **feedback umano**.
### Raccolta del Feedback Umano
Dato un input $x$, si genera un insieme di output candidati $\{y_1, \ldots, y_N\}$ dal LLM. Esperti umani indicano le loro preferenze tra coppie (o più) output. I tipi di feedback possibili sono:
- **Pairwise Comparison (ranking a coppie)**: dati due output, l'esperto indica quale è migliore — **più economico** che scrivere coppie input-output da zero
- **Rating**: l'esperto assegna un punteggio a ciascun output
- **Listwise Ranking**: l'esperto ordina l'intero insieme di output
Assumendo dati di confronto a coppie $(x, y^+, y^-)$ dove $y^+ \succ y^-$ (l'umano preferisce $y^+$), il reward model viene addestrato con una loss simile alla binary cross-entropy:
$$
L_{RM}(\phi) = -\mathbb{E}_{(x, y^+, y^-) \sim \mathcal{D}}\big[\log \sigma\big(r_\phi(x, y^+) - r_\phi(x, y^-)\big)\big]
$$
Questa loss spinge il reward model ad assegnare un punteggio più alto all'output preferito $y^+$ rispetto a quello non preferito $y^-$.
---
## 14. RLHF in Pratica
### Training della Policy
Ricordando il metodo Actor-Critic, si addestra la policy con:
$$
J(\theta) = \mathbb{E}\big[\log \pi_\theta(a_t \mid s_t) \, A(s_t, a_t)\big]
$$
Nel contesto del fine-tuning dei LLM, la funzione obiettivo diventa:
$$
J(\theta) = \mathbb{E}\big[\log \pi_\theta(y_t \mid x, y_{<t}) \, A(x, y_{<t}, y_t)\big]
$$
dove $\theta$ sono i parametri del LLM.
### Pipeline RLHF
Il flusso operativo è il seguente:
1. Dato un prompt $x$ dal dataset, la **policy** (il LLM fine-tunato) genera una risposta $y$
2. Si concatena $x$ con $y$ e si passa al **reward model**, che restituisce un reward (scalare) che indica la qualità della risposta
3. Si confronta la probabilità per-token della policy corrente con quella di un **language model iniziale congelato** e si penalizza il modello fine-tunato se devia troppo dal modello originale. Questo viene fatto con la **divergenza KL** — impedisce al modello di dimenticare le competenze generali apprese durante il pre-training
4. L'aggiornamento dei parametri avviene con un algoritmo policy-gradient RL, tipicamente **PPO** o **A2C**, che massimizza la metrica di reward sul batch corrente
### Reward Sparso
A differenza degli ambienti videoludici dove i reward sono frequenti, nell'RLHF il reward è **sparso**: l'ambiente fornisce un singolo scalare alla fine dell'intera risposta generata. Questo comporta sfide significative:
- Il modello non sa quali **singoli token** erano buoni o cattivi: riceve solo un giudizio complessivo, limitando il controllo fine-grained
- Rischio di **reward hacking**: il modello può imparare a ripetere disclaimer di sicurezza per gonfiare artificialmente il reward
- I reward sparsi possono causare **gradienti ad alta varianza**
Esistono tecniche di mitigazione come **DPO (Direct Preference Optimization)**, che evita del tutto il reward model esplicito, ma non è trattata nel corso.
---
## 15. AI ed Etica
### Panoramica
L'etica nell'IA esplora opportunità, rischi e responsabilità legate all'uso dei LLM. Le domande chiave riguardano: come i LLM impattano verità, bias e disinformazione? Quali sono le preoccupazioni etiche su privacy, sicurezza e consenso? Chi è responsabile dei contenuti generati dall'IA? Come i LLM influenzano creatività, lavoro e processi decisionali umani?
Le problematiche etiche si dividono in due categorie: **problemi di lunga data** (privacy, copyright, fairness) e **problemi emergenti** (truthfulness, norme sociali/tossicità).
### Privacy
- **Memorizzazione**: la memorizzazione aumenta con modelli più grandi. Si riduce con dataset di training più ampi e riducendo le duplicazioni nei dati. I modelli più grandi memorizzano sia pattern generali che dati specifici, incluse informazioni sensibili.
- **Membership Inference Attack (MIA)**: attacco in cui si cerca di determinare se un dato specifico faceva parte del training set. I LLM sono particolarmente vulnerabili a questo tipo di attacco.
- **Estrazione di dati di training**: dati sensibili possono essere estratti da modelli pre-addestrati con prompt appositamente costruiti. Ricerche hanno dimostrato che è possibile estrarre informazioni private (numeri di telefono, indirizzi, ecc.) da GPT-2 e modelli simili.
- **Inferenza di attributi personali**: i LLM possono essere usati per inferire attributi personali degli utenti a partire da testi generici.
### Copyright
- **Campioni avvelenati (Poisoned Samples)**: l'attaccante costruisce campioni avvelenati aggiungendo un **trigger definito dall'attaccante** e modificando le etichette associate a una specifica classe target. La presenza del trigger induce il modello a predire l'etichetta target corrispondente, significando l'ownership del modello.
- **Watermark (filigrane invisibili)**: si nascondono **firme invisibili** (impercettibili all'uomo) nel testo generato, che possono essere estratte per futura verifica dell'origine e della proprietà del modello.
### Fairness e Bias
I LLM ereditano e potenzialmente **amplificano** i bias sociali presenti nei dati di training, perpetuando danni verso comunità marginalizzate. Esempio: maggiore presenza di dati in inglese rispetto a lingue meno parlate.
- **Group Fairness**: si focalizza sulle disparità tra gruppi sociali
- **Individual Fairness**: individui simili in un task dovrebbero essere trattati in modo simile
- **Strategie di mitigazione**:
  - **Pre-processing**: estendere la rappresentazione di gruppi sociali sotto-rappresentati nei dati
  - **In-training**: integrare moduli di debiasing (adapter di debiasing) durante l'addestramento
  - **Post-processing**: identificare token biased nell'output e sostituirli con alternative non biased
### Truthfulness (Veridicità)
- **Allucinazione (Hallucination)**: i LLM possono generare informazioni plausibili ma false, presentandole con sicurezza. Questo è un problema fondamentale perché mina la fiducia nei contenuti generati.
- **Sycophancy (adulazione)**: tendenza a lusingare gli utenti confermando le loro convinzioni e misconcezioni, invece di correggerli. Problematicamente, la sycophancy **aumenta** con la dimensione del modello (modelli più grandi sono più adulatori) e può essere **peggiorata dall'RLHF** (che ottimizza per risposte gradite all'utente).
### Norme Sociali e Tossicità
I LLM possono generare contenuti **tossici**, offensivi o che violano le norme sociali. Questo è un problema particolarmente rilevante quando i modelli sono usati in contesti pubblici o interattivi, dove output inappropriati possono causare danni reali a individui e comunità.
### Tecniche di Mitigazione
Lo sviluppo responsabile dell'IA richiede interventi a più livelli: mitigazione dei bias nei dati e nei modelli, meccanismi di tutela della privacy, sistemi di verifica dei fatti (per ridurre le allucinazioni), filtri per la tossicità, e framework di governance e accountability che definiscano chi è responsabile dei contenuti generati.