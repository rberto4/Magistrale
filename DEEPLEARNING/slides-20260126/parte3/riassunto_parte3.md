## Indice cap 16-17-18
1. [Modellazione Generativa](#1-modellazione-generativa)
2. [Modelli Autoregressivi](#2-modelli-autoregressivi)
3. [Modelli a Variabili Latenti e VAE](#3-modelli-a-variabili-latenti-e-vae)
4. [Generative Adversarial Networks (GAN)](#4-generative-adversarial-networks-gan)
5. [Modelli di Diffusione (DDPM)](#5-modelli-di-diffusione-ddpm)
6. [Adattamento dei Foundation Models](#6-adattamento-dei-foundation-models)
7. [Full Fine-Tuning e Adattamento di BERT](#7-full-fine-tuning-e-adattamento-di-bert)
8. [Prompting e In-Context Learning](#8-prompting-e-in-context-learning)
9. [Parameter Efficient Fine-Tuning (PEFT)](#9-parameter-efficient-fine-tuning-peft)
10. [Adattamento di Modelli di Diffusione](#10-adattamento-di-modelli-di-diffusione)
11. [Apprendimento Non Supervisionato Avanzato: DINO](#11-apprendimento-non-supervisionato-avanzato-dino)
12. [Masked Autoencoder (MAE)](#12-masked-autoencoder-mae)
13. [Joint-Embedding Predictive Architecture (JEPA)](#13-joint-embedding-predictive-architecture-jepa)
---
## 1. Modellazione Generativa
### Cos'è un modello generativo?
L'**apprendimento non supervisionato** è un paradigma di machine learning in cui il modello impara dai dati **senza etichette** (senza supervisione umana). Tra i suoi obiettivi principali troviamo: apprendimento di rappresentazioni (self-supervised learning), compressione dati (autoencoder), scoperta di strutture nascoste (clustering) e **generazione di nuovi esempi**. Quest'ultimo è il focus della modellazione generativa.
Dato un insieme di dati di training $x_i$ (immagini, testo, audio) estratti da una distribuzione sconosciuta $p_{\text{data}}$, l'obiettivo è **apprendere una distribuzione parametrizzata** $p_\theta(x)$ tale che:
$$
p_\theta(x) \approx p_{\text{data}}(x)
$$
Una volta appresa questa distribuzione, possiamo:
- **Generare nuovi dati** campionando dalla distribuzione appresa (es. nuove immagini, nuovi testi, nuova musica)
- **Stimare la densità** di un dato: se $p_\theta(x)$ è alta, il dato è compatibile con la distribuzione (utile per anomaly detection)
- **Apprendere rappresentazioni compatte** dei dati di training
### Tassonomia dei modelli generativi
I modelli generativi si dividono in due grandi famiglie:
- **Modelli espliciti**: possono calcolare direttamente $p(x)$. Appartengono a questa categoria i modelli autoregressivi e i VAE (Variational Autoencoder). Con questi modelli possiamo sia generare campioni sia valutare la probabilità di un dato.
- **Modelli impliciti**: **non possono** calcolare $p(x)$ direttamente, ma possono **campionare** da essa. Appartengono a questa categoria le GAN (Generative Adversarial Networks) e i modelli di diffusione. Generano campioni realistici senza mai esplicitare la distribuzione.
### Sfide fondamentali
Apprendere $p_{\text{data}}(x)$ è difficile per due ragioni:
- **Dati limitati**: disponiamo solo di campioni finiti dalla distribuzione vera, che forniscono un'approssimazione grossolana
- **Ragioni computazionali**: in molti casi la distribuzione non è trattabile (intractable), cioè non si può calcolare analiticamente in modo efficiente
### Applicazioni
I modelli generativi alimentano oggi una vasta gamma di applicazioni: scrittura creativa (ChatGPT), generazione di immagini e arte digitale (DALL-E, Stable Diffusion), scoperta di farmaci e molecole, realtà virtuale, creazione di videogiochi, generazione di video (Sora) e molto altro.
---
## 2. Modelli Autoregressivi
### Idea fondamentale
Qualsiasi dato strutturato può essere trasformato in una **sequenza**: un testo è una sequenza di parole, un'immagine può essere una sequenza di pixel (scansionati riga per riga). I **modelli autoregressivi (AR)** sfruttano questa intuizione: predicono il valore successivo in una sequenza basandosi sui valori precedenti.
Data una sequenza di $d$ variabili $x = (x_1, x_2, \ldots, x_d)$, la probabilità congiunta viene decomposta tramite la **regola della catena** in un prodotto di probabilità condizionate:
$$
p(x_1, \ldots, x_d) = p(x_1) \cdot p(x_2 \mid x_1) \cdot p(x_3 \mid x_1, x_2) \cdots p(x_d \mid x_1, \ldots, x_{d-1})
$$
In forma compatta:
$$
p_\theta(x) = \prod_{j=1}^{d} p_\theta(x_j \mid x_{1:j-1}; \theta)
$$
Ogni fattore è una distribuzione condizionale che dice: "dato tutto ciò che ho visto finora, qual è la probabilità del prossimo elemento?". Questa è esattamente l'idea dietro i language model come GPT.
### Maximum Likelihood Estimation (MLE)
L'addestramento avviene tramite **Maximum Likelihood Estimation**: si cercano i parametri $\theta$ che massimizzano la probabilità dei dati osservati. Equivalentemente, si minimizza la **negative log-likelihood**:
$$
\arg\min_\theta \; -\frac{1}{n} \sum_{i=1}^{n} \log p_\theta(x^{(i)})
$$
Se $p_\theta$ è una rete neurale, l'ottimizzazione avviene tramite backpropagation e SGD, come per qualsiasi altra rete. I modelli AR sono **espliciti** perché stimano direttamente la distribuzione parametrizzata $p_\theta(x)$ tramite MLE.
### PixelCNN
**PixelCNN** applica l'approccio autoregressivo alla generazione di immagini: predice il valore del pixel alla posizione $i$ guardando solo i pixel a sinistra e sopra (come in un raster scan). Usa **convoluzioni mascherate** (analoghe alla masked self-attention nei Transformer) per impedire al modello di vedere i pixel futuri. Il training è parallelizzabile grazie alla convoluzione e alla conoscenza dell'immagine completa nel training set, ma la **generazione è sequenziale** e quindi lenta: ogni pixel dipende dal precedente.
### GPT come modello autoregressivo
**GPT** (Generative Pre-trained Transformer) è un modello autoregressivo basato su Transformer decoder-only. È addestrato con **next token prediction** su testi su larga scala (apprendimento non supervisionato). Dopo il pre-training, può essere fine-tunato su task supervisionati (QA, riassunti, ecc.). **GPT-2** ha la stessa architettura e obiettivo di GPT-1 ma con scala maggiore: 1.5B parametri addestrati su 40GB di testo (WebText). Ha dimostrato che con sufficiente scala (modello + dati), i language model possono eseguire task senza fine-tuning.
### Image GPT (iGPT)
**Image GPT** applica l'addestramento tipo GPT alle immagini: le immagini vengono ridimensionate a bassa risoluzione e rimodellate in una sequenza 1D. Si usano due obiettivi di pre-training: predizione autoregressiva del prossimo pixel (come GPT) oppure predizione dei pixel mascherati (come BERT). L'obiettivo finale non è generare pixel ma **apprendere buone rappresentazioni** da usare per task downstream.
### VideoPoet
**VideoPoet** estende l'approccio autoregressivo alla generazione multimodale: invece di decodificare solo testo, decodifica immagini, video e audio. Permette generazione cross-modale (es. testo-to-video, video-to-audio), unificando diverse modalità in un unico framework autoregressivo basato su Transformer.
---
## 3. Modelli a Variabili Latenti e VAE
### Intuizione
I **modelli a variabili latenti** assumono che i dati osservati siano generati da variabili nascoste (latenti) che catturano i fattori sottostanti non direttamente visibili. Ad esempio, in un'immagine di un volto, le variabili latenti potrebbero rappresentare illuminazione, emozione e identità; in un testo, il topic o il sentiment.
L'assunzione fondamentale è che esista una **rappresentazione a dimensionalità inferiore** dei dati. Un'immagine complessa con milioni di pixel potrebbe essere descritta da poche variabili: posizione degli oggetti, classe, attributi. Se riuscissimo ad apprendere questa rappresentazione $z$, potremmo campionare nuovi $z$ e generare nuovi dati $x$.
### Formalizzazione
Sia $z$ la variabile latente (non osservata) e $x$ il dato osservato. Per generare un nuovo campione:
$$
z \sim p_Z(z), \quad x \sim p_\theta(x \mid z)
$$
La likelihood (verosimiglianza) si ottiene **marginalizzando** sulle variabili latenti:
$$
p_\theta(x) = \sum_z p_Z(z) \, p_\theta(x \mid z)
$$
L'addestramento richiede di massimizzare questa likelihood. Il problema è che il calcolo della marginalizzazione è **intrattabile** quando $z$ vive in uno spazio ad alta dimensionalità: non si possono enumerare tutti i possibili valori di $z$.
### Soluzioni al problema dell'intrattabilità
- **Opzione 1**: se $z$ assume pochi valori discreti, la somma è calcolabile → trattabile
- **Opzione 2**: campionamento casuale, ma è improbabile che un $z$ campionato a caso sia compatibile con un dato $x$ specifico
- **Opzione 3**: importance sampling, campionando da una distribuzione alternativa $q(z)$ più "informata"
- **Opzione 4** (la soluzione dei VAE): scegliere $q(z)$ come distribuzione semplice e apprendibile (es. Gaussiana) tale che $q_\phi(z \mid x) \approx p_\theta(z \mid x)$, dove $q_\phi$ è un'altra rete neurale (l'encoder)
### Variational Autoencoder (VAE)
Il **VAE** implementa l'opzione 4 con un'architettura encoder-decoder:
- **Encoder** $q_\phi(z \mid x)$: prende i dati $x$ e produce i parametri di una distribuzione Gaussiana ($\mu, \sigma^2$) nello spazio latente. L'output dell'encoder deve seguire una distribuzione semplice (prior), tipicamente una Gaussiana unitaria $\mathcal{N}(0, I)$.
- **Campionamento**: si campiona $z$ dalla distribuzione Gaussiana parametrizzata dall'encoder
- **Decoder** $p_\theta(x \mid z)$: prende $z$ e genera la ricostruzione $\hat{x}$ dei dati originali
La funzione obiettivo del VAE combina due termini:
$$
\underbrace{\|x - \hat{x}\|_2^2}_{\text{reconstruction loss}} + \underbrace{D_{\text{KL}}(q_\phi(z \mid x) \| p(z))}_{\text{regularizzazione}}
$$
- Il **primo termine** (reconstruction loss) penalizza l'errore di ricostruzione: l'output deve essere simile all'input. Corrisponde a massimizzare la likelihood $p_\theta(x \mid z)$.
- Il **secondo termine** (KL divergence) è un regolarizzatore che forza la distribuzione appresa $q_\phi(z \mid x)$ ad essere simile al prior $p(z) = \mathcal{N}(0, I)$. Questo garantisce che lo spazio latente sia "liscio" e continuo, permettendo la generazione di nuovi campioni.
### Generazione con il VAE
Dopo l'addestramento, per generare nuovi campioni si **scarta l'encoder** e si campiona direttamente dal prior:
$$
z \sim \mathcal{N}(0, I) \quad \rightarrow \quad \text{Decoder} \quad \rightarrow \quad \text{nuovo campione } x
$$
---
## 4. Generative Adversarial Networks (GAN)
### Modelli impliciti
Le GAN sono **modelli generativi impliciti**: non modellano esplicitamente la distribuzione $p_\theta(x)$, ma possono solo **campionare** da essa. Un generatore $q_\phi(z)$ produce campioni a partire da rumore $z \sim p(z)$ (es. distribuzione Gaussiana o uniforme). L'obiettivo è regolare i parametri $\phi$ in modo che i campioni generati sembrino provenire da $p_{\text{data}}$.
### Architettura: gioco a due giocatori
Le GAN consistono in due reti neurali che competono in un **gioco minimax**:
- **Generatore** ($G$): riceve rumore casuale $z$ e produce un campione "falso" $G(z)$. Il suo obiettivo è generare dati così realistici da ingannare il discriminatore.
- **Discriminatore** ($D$): riceve sia dati reali dalla distribuzione dei dati sia dati falsi dal generatore, e deve classificarli come "reale" o "falso". Il suo obiettivo è distinguere correttamente tra i due.
La funzione obiettivo è:
$$
\min_G \max_D \; \mathbb{E}_{x \sim p_{\text{data}}}[\log D(x)] + \mathbb{E}_{z \sim p(z)}[\log(1 - D(G(z)))]
$$
- Il **discriminatore** $D$ massimizza: vuole assegnare probabilità alta (vicino a 1) ai dati reali e bassa (vicino a 0) ai dati generati
- Il **generatore** $G$ minimizza: vuole che il discriminatore assegni probabilità alta anche ai dati falsi, cioè vuole che $D(G(z))$ sia vicino a 1
All'**equilibrio** (convergenza), il discriminatore non riesce più a distinguere dati reali da falsi: i campioni generati sono indistinguibili da quelli reali.
### Addestramento e campionamento
$D$ e $G$ sono reti neurali standard, addestrate con SGD e backpropagation. Tuttavia, l'addestramento delle GAN è notoriamente **instabile**: il gioco minimax può divergere, oscillare o collassare (mode collapse, dove il generatore produce sempre lo stesso output). Numerosi paper successivi hanno proposto soluzioni per stabilizzare il training.
Una volta addestrate, si **scarta il discriminatore** $D$. Per generare nuovi campioni, si campiona rumore $z \sim p(z)$ e lo si passa attraverso $G$:
$$
z \sim p(z) \quad \rightarrow \quad G(z) \quad \rightarrow \quad \text{campione generato}
$$
La qualità delle immagini generate è migliorata enormemente negli anni: dalle GAN originali del 2014 (immagini sfocate) a StyleGAN2 nel 2019 (volti fotorealistici indistinguibili da foto reali).
### GAN Condizionali (pix2pix)
Non è necessario partire sempre da rumore. Le **GAN condizionali** come **pix2pix** prendono un'immagine strutturata come input (es. una mappa di bordi, una mappa semantica) e generano l'immagine realistica corrispondente. Il generatore ha un'architettura encoder-decoder (come un autoencoder convoluzionale) o **U-Net**, poiché input e output devono avere le stesse dimensioni. Il discriminatore impara a distinguere tra coppie {input, output reale} e {input, output generato}.
---
## 5. Modelli di Diffusione (DDPM)
### Idea ispirata alla termodinamica
I **Denoising Diffusion Probabilistic Models (DDPM)** sono modelli generativi ispirati alla termodinamica. L'idea è elegante: si **aggiunge rumore gradualmente** ai dati (processo forward) fino a trasformarli in puro rumore Gaussiano, poi si **addestra una rete neurale a invertire il processo** (processo reverse), imparando a rimuovere il rumore passo per passo. Concettualmente simili alle GAN (modelli impliciti: non calcolano $p_\theta(x)$ direttamente), ma funzionano **molto meglio** e sono più stabili da addestrare.
### Processo Forward (Diffusione)
Dato un'immagine reale $x_0$, il processo forward produce una sequenza $x_1, x_2, \ldots, x_T$ aggiungendo rumore Gaussiano ad ogni passo. La quantità di rumore è controllata da uno schedule di varianze $\beta_1, \beta_2, \ldots, \beta_T$ (tipicamente $\beta_1 \approx 10^{-4}$, $\beta_T \approx 0.02$). La transizione da $x_{t-1}$ a $x_t$ è definita come:
$$
q(x_t \mid x_{t-1}) = \mathcal{N}(x_t; \sqrt{1 - \beta_t} \, x_{t-1}, \; \beta_t I)
$$
Una proprietà fondamentale è che si può saltare direttamente da $x_0$ a qualsiasi $x_t$ senza calcolare tutti i passi intermedi:
$$
x_t = \sqrt{\bar{\alpha}_t} \, x_0 + \sqrt{1 - \bar{\alpha}_t} \, \epsilon, \quad \text{dove } \epsilon \sim \mathcal{N}(0, I)
$$
Con $\alpha_t = 1 - \beta_t$ e $\bar{\alpha}_t = \prod_{s=1}^{t} \alpha_s$. Intuitivamente, dato un campione reale $x_0$, possiamo ottenere una versione rumorosa a qualsiasi passo $t$ semplicemente mescolando $x_0$ con rumore $\epsilon$, pesati da $\bar{\alpha}_t$. Più $t$ è grande, più l'immagine è rumorosa.
### Processo Reverse (Denoising)
Il processo reverse è parametrizzato da una rete neurale $\epsilon_\theta$ che predice **quanto rumore** è stato aggiunto all'immagine. Dato $x_t$ (l'immagine rumorosa) e il passo temporale $t$, la rete predice il rumore $\epsilon_\theta(x_t, t)$. Sottraendo il rumore predetto da $x_t$ si ottiene un'immagine meno rumorosa $x_{t-1}$.
### Addestramento
In pratica, non si inverte il processo in un solo passo (troppo difficile). Si campiona $t$ da una distribuzione uniforme e si minimizza l'errore quadratico medio tra il rumore predetto e quello reale:
$$
L = \mathbb{E}_{t, x_0, \epsilon}\left[\|\epsilon - \epsilon_\theta(x_t, t)\|^2\right]
$$
Il modello impara: "data un'immagine rumorosa e il livello di rumore $t$, predici il rumore che è stato aggiunto".
### Campionamento (Inferenza)
Per generare un nuovo campione:
- Si parte da puro rumore Gaussiano: $x_T \sim \mathcal{N}(0, I)$
- Per $t = T, T-1, \ldots, 1$: si predice il rumore $\epsilon_\theta(x_t, t)$ e si sottrae per ottenere $x_{t-1}$
- Il processo si ripete per un numero predefinito di passi fino a ottenere l'immagine finale $x_0$
### Architettura: U-Net
I modelli di diffusione usano tipicamente una **U-Net** come rete di predizione del rumore (la stessa architettura usata per la segmentazione di immagini). La U-Net predice il rumore $\epsilon$ dati l'input rumoroso $x_t$ e il passo temporale $t$. Il timestep $t$ viene convertito in una rappresentazione ad alta dimensionalità tramite un time encoder e iniettato nella rete.
### Modelli di diffusione condizionali
Il DDPM base è **incondizionato**: genera campioni casuali senza controllo. I modelli di diffusione **condizionali** incorporano informazione aggiuntiva nel processo:
- **Etichette di classe**: generare immagini di una classe specifica
- **Mappe semantiche**: generare immagini da layout
- **Keypoint**: generare pose specifiche
- **Testo**: generare immagini da descrizioni testuali
### Modelli Text-to-Image
Nei modelli **text-to-image** (T2I), il testo (caption) viene codificato da un text encoder (es. BERT, CLIP) e iniettato nella U-Net tramite **cross-attention layers**. In queste layer: le **query** ($Q$) provengono dalle feature dei pixel, mentre le **chiavi** ($K$) e i **valori** ($V$) provengono dagli embedding testuali. In inferenza, partendo da rumore Gaussiano e una caption, il modello genera immagini coerenti con il testo.
### ControlNet
**ControlNet** estende i modelli T2I permettendo condizionamento aggiuntivo, come sketch-to-image: dato un disegno a mano come input, il modello genera una versione realistica e colorata dello sketch.
### Latent Diffusion Models
I Latent Diffusion Models eseguono la diffusione in uno **spazio latente compresso** anziché nello spazio dei pixel, per efficienza. Un encoder comprime $x$ in $z$ nello spazio latente, il DDPM opera su $z$, e un decoder ricostruisce l'immagine ad alta risoluzione dal $z$ denoised. Questo riduce enormemente il costo computazionale.
### Diffusion Language Models
L'idea della diffusione è stata estesa anche al testo. Invece di predire una parola alla volta (come i modelli autoregressivi), i **Diffusion Language Models** generano testo **denoising** una frase corrotta in parallelo. A differenza delle immagini (rumore Gaussiano continuo), il linguaggio usa **corruzione discreta**: mascheramento di token ([MASK]), sostituzione con token casuali, cancellazione o riordino di parole. Ad ogni passo il modello predice una distribuzione per ogni posizione, raffinando progressivamente il testo.
---
## 6. Adattamento dei Foundation Models
### Cosa sono i Foundation Models?
I **Foundation Models (FM)** sono reti neurali profonde molto grandi (tipicamente basate su Transformer) addestrate su dataset di scala internet:
- CLIP: 400M coppie immagine-testo
- BERT: BooksCorpus (800M parole) + Wikipedia (2500M parole)
- GPT-3: miliardi di token da diverse fonti
Grazie al pre-training massiccio, i FM sono **general-purpose learners** con buona capacità di generalizzazione. Il pre-training è la fase più costosa nella vita di un modello.
### Perché adattarli?
Nonostante siano versatili, i FM hanno **limitazioni su task specifici**: un LLM addestrato con next token prediction è bravo a generare testo, ma il suo output potrebbe non seguire il formato desiderato dall'utente. Ad esempio, alla query "The capital of France is", LLaMA-base potrebbe generare un lungo paragrafo su Parigi quando l'utente vuole semplicemente "Paris".
L'**adattamento** consiste nello sterzare il comportamento del modello base verso le nostre esigenze specifiche (assistente utile, no output tossico, formato specifico, task specializzato) tramite prompting o tuning su dati curati.
---
## 7. Full Fine-Tuning e Adattamento di BERT
### Full Fine-Tuning
L'approccio più diretto è il **full fine-tuning**: si aggiornano **tutti** i parametri del modello pre-addestrato su un dataset etichettato del task downstream.
**Pro**: massima flessibilità di apprendimento e alte prestazioni sul task specifico.
**Contro**: richiede enorme memoria GPU e rischia il **catastrophic forgetting**, dove le informazioni generali apprese durante il pre-training vengono cancellate.
### Requisiti di memoria
Per il fine-tuning in full-precision (FP32) con ottimizzatore Adam, ogni parametro richiede:
- 4 byte per il peso del modello
- 4 byte per il gradiente
- 4 byte per il primo momento di Adam (momentum)
- 4 byte per il secondo momento di Adam (varianza)
Totale: **16 byte per parametro**. Per un LLM da 7B parametri: $7 \times 10^9 \times 16 = 112$ GB di VRAM. Le GPU consumer hanno tra 16-80 GB: **insufficienti** per il full fine-tuning di un LLM. Servono metodi più efficienti.
### Adattamento di BERT
BERT è un Transformer encoder-only pre-addestrato con Masked Language Modelling (MLM) e Next Sentence Prediction (NSP), entrambi obiettivi self-supervised. Dopo il pre-training, BERT non è direttamente pronto per task downstream: serve un **modello predittore** aggiuntivo:
$$
y = \text{Predict}_\omega(\text{BERT}_{\hat{\theta}}(x))
$$
dove $\hat{\theta}$ sono i parametri pre-addestrati (tipicamente congelati) e $\omega$ i parametri del predittore addestrati sul task specifico.
### Classificazione di testo singolo
Per la classificazione di sentimento (es. recensioni positive/negative), la rappresentazione $h_{\text{CLS}}$ del token [CLS] viene passata a un MLP + Softmax. Durante l'adattamento, si ottimizzano solo i parametri del predittore con un dataset etichettato.
### Classificazione di coppie di testi
Per task come il **Text Entailment Judgement** (data una premessa e un'ipotesi, determinare se c'è entailment, contraddizione o neutralità), si danno entrambe le frasi come input a BERT separate dal token [SEP] e si classifica usando il token [CLS].
### BERT come encoder in modelli generativi
BERT può essere usato per inizializzare l'encoder (e parzialmente il decoder) di modelli encoder-decoder per task generativi come la traduzione automatica.
---
## 8. Prompting e In-Context Learning
### Prompting
Il **prompting** consiste nel fornire un input specifico (prompt) a un LLM per guidare la generazione dell'output desiderato, **senza fine-tuning**. Un prompt ben progettato può guidare il modello a produrre risposte accurate e contestualmente appropriate. La disciplina di progettazione dei prompt si chiama **prompt engineering**.
Esempio tipico: "Translate the text from English to Chinese. Text: The early bird catches the worm. Answer:" → il modello genera la traduzione.
Non esiste un template universale per i prompt, ma un formato popolare è lo stile "name:content" con specifica chiara del task (Task, Source language, Target language, Style, Template, Answer). La ricerca del prompt giusto è un **processo iterativo**.
### In-Context Learning (ICL)
Quando specificare il task in modo chiaro non è sufficiente, si forniscono **esempi dimostrativi** (demonstrations) del task direttamente nel prompt. Il modello "impara" dal contesto degli esempi forniti, senza alcuna modifica dei pesi. Questo approccio si chiama **in-context learning** e consiste tipicamente in:
- Un messaggio **SYSTEM** che definisce il ruolo del modello
- Uno o più **DEMO** con esempi input-output del task
- L'input **USER** per cui si vuole l'output
### Chain-of-Thought Prompting (CoT)
Per problemi complessi (es. aritmetica, ragionamento logico), anche l'ICL con semplici coppie domanda-risposta può fallire. Il **Chain-of-Thought prompting** aggiunge passi di ragionamento intermedi nelle dimostrazioni: non solo la risposta finale, ma l'intero processo di risoluzione. Ad esempio, invece di mostrare solo "Q: media di 2, 4, 9 → A: 5", si mostra:
"Calcola 2 + 4 + 9 = 15. Ci sono tre numeri. Dividi la somma per il conteggio: 15/3 = 5. La risposta è 5."
I passi di ragionamento esplicitati insegnano al modello **come** pensare, non solo **cosa** rispondere.
### Learning to Prompt (Prompt Optimization)
Il prompt engineering manuale ha limiti:
- Progettare prompt di alta qualità è difficile e richiede competenza umana
- I prompt manuali possono essere ridondanti e lunghi, aumentando i costi computazionali
La **prompt optimization** automatizza il processo di progettazione dei prompt. Un approccio specifico è il **soft prompting**: mentre i prompt "hard" sono sequenze di token discreti (testo leggibile), i **soft prompt** sono **vettori continui apprendibili** nello spazio degli embedding, ottimizzati come normali parametri tramite backpropagation. Non sono interpretabili dall'uomo, ma sono più compatti ed efficaci.
---
## 9. Parameter Efficient Fine-Tuning (PEFT)
### Motivazione
Per superare i limiti sia del full fine-tuning (troppo costoso, catastrophic forgetting) sia del prompt engineering (manuale, limitato), il **Parameter Efficient Fine-Tuning (PEFT)** introduce un paradigma intermedio:
- Apprende solo una **piccola frazione** dei parametri del modello base, mantenendo la maggior parte congelati → **efficiente in memoria**
- Elimina la necessità di progettare prompt a mano
- Offre prestazioni **competitive** rispetto al full fine-tuning
- Riduce il rischio di catastrophic forgetting poiché la maggior parte dei pesi pre-addestrati resta inalterata
### Prefix Tuning
Nel **prefix tuning**, si aggiunge una serie di **vettori apprendibili** (prefissi o soft prompt) nel Transformer. Durante il fine-tuning, si apprendono **solo i prefissi** mentre tutti i parametri del modello e il layer di embedding restano congelati. L'addestramento usa una cross-entropy loss standard. Due varianti:
- **Variante 1**: i prefissi vengono inseriti all'input di **ogni layer** del Transformer
- **Variante 2**: i prefissi vengono inseriti come embedding apprendibili solo all'**inizio** del Transformer
I vettori apprendibili catturano l'essenza del task downstream.
### Adapter
Gli **Adapter** sono moduli aggiuntivi inseriti **tra i layer** di una rete pre-addestrata. Solo i parametri degli adapter vengono ottimizzati; tutto il resto è congelato. Un adapter layer ha una struttura a **bottleneck**:
- **Layer lineare di down-projection**: riduce la dimensionalità delle feature a una dimensione inferiore
- **Funzione di attivazione non lineare**
- **Layer lineare di up-projection**: riporta la dimensionalità a quella originale
- **Connessione residua**: l'input dell'adapter viene sommato all'output
Gli adapter possono essere usati sia con BERT che con LLM decoder-only.
### Low-Rank Adaptation (LoRA)
**LoRA** è una delle tecniche PEFT più popolari. Introduce matrici **low-rank** leggere per apprendere informazione specifica del task. Si basa su due idee chiave:
**Idea 1**: gli aggiornamenti dei pesi vengono appresi separatamente con un $\Delta W$ dedicato, mantenendo i pesi pre-addestrati $W_0$ congelati:
$$
W = \underbrace{W_0}_{\text{freeze}} + \underbrace{\Delta W}_{\text{learn}}
$$
**Idea 2**: invece di apprendere una matrice $\Delta W \in \mathbb{R}^{d \times d}$ di rango pieno, si apprendono due matrici di **rango basso**: $\Delta W = BA$, dove $B \in \mathbb{R}^{d \times r}$, $A \in \mathbb{R}^{r \times d}$ e $r \ll d$.
L'output finale è:
$$
h = W_0 x + \Delta W x = W_0 x + BAx = \underbrace{(W_0 + BA)}_{\text{merged}} x
$$
**Esempio numerico**: con $d = 1024$ e rango $r = 4$, la matrice originale $W$ ha $1024 \times 1024 \approx 1M$ parametri. Le matrici LoRA $A \in \mathbb{R}^{4 \times 1024}$ e $B \in \mathbb{R}^{1024 \times 4}$ hanno $4 \times 1024 \times 2 \approx 8k$ parametri: circa lo **0.8%** dei parametri originali.
**Dopo il fine-tuning**, le matrici low-rank vengono **fuse** (merged) nei pesi originali: $W = W_0 + BA$. A differenza degli adapter, LoRA **non aggiunge parametri extra** al modello finale: la dimensione resta identica.
### Requisiti di memoria di LoRA
Per LoRA allo 0.1% dei parametri totali su un modello da 7B:
- Pesi base: $4$ byte/param (congelati, nessun gradiente o stato optimizer)
- Pesi LoRA: $16 \times 0.001$ byte/param (peso + gradiente + momentum + varianza)
- Totale: $4 + 0.016 = 4.016$ byte/param
- Memoria: $7 \times 10^9 \times 4.016 \approx 28.11$ GB, contro i 112 GB del full fine-tuning
### Adattamento di Transformer per la visione
#### Textual Prompt Tuning (TPT) per CLIP
Si introducono $M$ vettori apprendibili $V = \{V_1, \ldots, V_M\}$ che vengono **concatenati ai nomi delle classi** e dati come input al text encoder di CLIP. Durante il fine-tuning si addestrano **solo** i vettori apprendibili, mantenendo congelati sia l'image encoder che il text encoder.
#### Visual Prompt Tuning (VPT) per ViT
Due varianti:
- **VPT-Deep**: prompt (prefissi) apprendibili inseriti come input in **ogni layer** del ViT
- **VPT-Shallow**: prompt apprendibili inseriti solo **all'input** del ViT
In entrambi i casi, si addestra anche un classificatore (MLP) sulla rappresentazione del token [CLS]. Tutto il resto è congelato.
### Considerazioni pratiche sull'adattamento
Quando si adatta un Foundation Model, bisogna bilanciare diversi fattori:
- **Costo vs. prestazioni**: full fine-tuning = massime prestazioni; PEFT = prestazioni quasi equivalenti a costo molto inferiore
- **Disponibilità dati**: grandi dataset → full o instruction tuning; piccoli dataset → PEFT o prompt tuning
- **Budget computazionale**: full fine-tuning richiede centinaia di GB di GPU; LoRA richiede molta meno memoria
- **Rischio di catastrophic forgetting**: il full fine-tuning può cancellare conoscenze generali; PEFT lo riduce
- **Estensibilità**: con il full fine-tuning, il modello va ri-addestrato quando il modello base cambia; PEFT permette hot-swapping e iterazione rapida
---
## 10. Adattamento di Modelli di Diffusione
### DreamBooth
**DreamBooth** è una tecnica per personalizzare un modello T2I: mentre i modelli T2I generano oggetti generici (es. "un cane qualsiasi"), DreamBooth permette di generare immagini dello **stesso oggetto specifico** (es. il tuo cane) in contesti diversi, facendo fine-tuning su poche immagini del soggetto.
L'approccio è simile al Textual Prompt Tuning: si sceglie un **token raro** nel vocabolario, denotato $[V]$ (es. "sks", poco frequente nei dati di training), e si creano prompt come "A [V] dog". Attraverso il fine-tuning dei parametri della U-Net e degli embedding di $[V]$, si crea un legame tra il soggetto specifico e il token speciale.
DreamBooth usa **due loss**:
- **Reconstruction loss**: $L_{\text{mse}} = \|\hat{x}_\theta(\alpha_t x + \sigma_t \epsilon, c) - x\|_2^2$, che forza il modello a ricostruire le immagini del soggetto
- **Prior preservation loss**: $L_{\text{prior}} = \|\hat{x}_\theta(\alpha_t x_{\text{pr}} + \sigma_t \epsilon, c_{\text{pr}}) - x_{\text{pr}}\|_2^2$, dove $x_{\text{pr}}$ è un'immagine generata dal modello pre-addestrato. Questa loss incoraggia la **diversità** nella generazione, impedendo che il modello dimentichi come generare oggetti generici della stessa categoria
### Textual Inversion
**Textual Inversion** ha un obiettivo simile a DreamBooth: personalizzare un modello T2I per generare immagini di un soggetto specifico. Si introduce un token speciale $S^*$ nel prompt "A photo of $S^*$" e si **apprendono solo gli embedding** $v^*$ di questo token, mantenendo congelati sia il text encoder che la U-Net. È un approccio ancora più leggero di DreamBooth, poiché modifica solo gli embedding di un singolo token.
### Prompt-to-Prompt
**Prompt-to-Prompt** permette di **editare dettagli** di un'immagine generata attraverso modifiche testuali, senza cambiare il layout della scena. Ad esempio, si può cambiare lo stile da dipinto realistico a disegno per bambini, oppure sostituire un oggetto ("bicycle" → "motorcycle") mantenendo la composizione identica.
#### Come funziona: Cross-Attention Control
Il meccanismo si basa sulle **mappe di cross-attention** della U-Net dei modelli T2I:
- Nella cross-attention, le **query** ($Q$) derivano dalle feature dei pixel e le **chiavi/valori** ($K$, $V$) dagli embedding testuali
- La mappa di attenzione $M = \text{softmax}\!\left(\frac{QK^\top}{\sqrt{d}}\right)$ indica quanto ogni pixel è "attratto" da ogni parola del prompt: i pixel dell'orso saranno correlati alla parola "bear", quelli dell'uccello alla parola "bird"
- Nel processo di denoising, il layout grossolano viene determinato nei **primi passi** ($t \approx T$), mentre i dettagli fini vengono definiti negli **ultimi passi** ($t \approx 1$)
Per l'editing **Word Swap** (es. "bicycle" → "motorcycle"): nei primi passi di denoising si usano le mappe di attenzione del prompt originale $P$ (per preservare il layout), negli ultimi passi si usano quelle del prompt modificato $P^*$ (per cambiare solo i dettagli fini).
Altre operazioni supportate:
- **Prompt refinement**: aggiungere frasi al prompt modificato
- **Fader control**: controllare l'intensità con cui un attributo influenza l'immagine generata
---
## 11. Apprendimento Non Supervisionato Avanzato: DINO
### Perché il representation learning visivo è importante?
Abbiamo enormi quantità di dati non etichettati (immagini, video) ma pochissimi dati di alta qualità per task downstream specifici (es. dati robotici con informazioni su azioni e stati). L'obiettivo è apprendere un **modello general-purpose** tramite self-supervised learning e poi trasferirlo al task downstream con fine-tuning minimale.
### DINO: Self-Distillation with No Labels
**DINO** è un algoritmo di self-supervised learning per Vision Transformer (ViT). La scoperta chiave è che, a differenza dell'apprendimento supervisionato, un ViT addestrato con DINO produce mappe di attenzione che contengono **informazione esplicita sulla segmentazione semantica** dell'immagine, anche senza etichette di pixel o di classe.
#### Algoritmo di training
- Data un'immagine $x$, si creano **due viste** $x_1$ e $x_2$ tramite trasformazioni fotometriche (cropping casuale, rotazione, jitter)
- Due reti: **student** $g_{\theta_s}$ e **teacher** $g_{\theta_t}$
- Ogni rete produce una predizione (distribuzione di probabilità su $K$ output) sulla propria vista
- Si calcola una **cross-entropy loss** dove la predizione del teacher ($p_2$) è il target per lo student ($p_1$)
- Il gradiente viene retropropagato **solo attraverso lo student**; il teacher non riceve gradienti (stop gradient)
#### Aggiornamento del teacher: Exponential Moving Average (EMA)
I pesi del teacher vengono aggiornati come media mobile esponenziale dei pesi dello student:
$$
\theta_t^{(i+1)} = \alpha \, \theta_t^{(i)} + (1 - \alpha) \, \theta_s^{(i)}, \quad \alpha \in [0, 1)
$$
Con $\alpha$ tipicamente vicino a 1, il teacher è una **versione lisciata** (smoothed) dello student.
#### Prevenzione del collasso
In DINO non ci sono coppie negative (a differenza di SimCLR). Senza precauzioni, le due reti potrebbero predire la stessa distribuzione costante, minimizzando la loss senza apprendere nulla. Per prevenire il collasso, si applicano all'output del teacher:
- **Centering**: sottrae la media del mini-batch $c$ dall'output, impedendo che una dimensione domini (rende la distribuzione più uniforme)
- **Sharpening**: divide per una temperatura piccola $\tau \to 0$, rendendo la distribuzione "peaked" (concentrata)
$$
p_2 = \text{softmax}\!\left(\frac{z_t - c}{\tau}\right), \quad c \leftarrow mc + (1 - m) \frac{1}{B} \sum_{i=1}^{B} g_{\theta_t}(x_i)
$$
#### Mappe di attenzione di DINO
Visualizzando le mappe di attenzione dell'ultimo layer corrispondenti al token [CLS], si osserva che diverse teste di attenzione (heads) si focalizzano su diverse parti dell'immagine, rappresentando diversi oggetti o parti di oggetti.
### DINOv2
**DINOv2** è l'evoluzione di DINO. Le migliorie principali che stabilizzano il training e migliorano le prestazioni sono:
- **Dati non etichettati di qualità superiore**
- **Modelli più grandi** (scaling laws: modelli più grandi → prestazioni migliori)
- **Loss di training migliorate**
Le scaling laws confermano che aumentando sia la dimensione del modello (da ViT-S con 5M parametri a ViT-Giant con 1.8B) sia la dimensione del dataset (da ImageNet-22k con 14M immagini a LVD-142M con 142M immagini), le prestazioni migliorano costantemente.
#### Knowledge Distillation
Modelli più grandi hanno prestazioni migliori ma richiedono più memoria in inferenza. Per deployment in ambienti con risorse limitate, la **Knowledge Distillation (KD)** permette di trasferire la conoscenza di un modello grande (teacher) in uno più piccolo (student) con prestazioni quasi equivalenti.
Il teacher è una rete più grande e ben addestrata; lo student è una rete più piccola inizializzata da zero. Il teacher produce logit che il student deve riprodurre (**logit matching**). La loss è la KL divergence tra le probabilità del teacher e dello student:
$$
\text{KL}(p_t \| p_s) = \sum_{i \in C} p_{t,i} \log \frac{p_{t,i}}{p_{s,i}}
$$
In DINOv2 si dimostra che un ViT-L/14 distillato da un ViT-g/14 ha prestazioni **migliori** di un ViT-L/14 addestrato da zero, nonostante abbiano la stessa dimensione.
### DINOv3
**DINOv3** è l'ultima evoluzione: dataset di training ancora più grande, modello fino a **7B parametri** (tipicamente gli image encoder sono sotto 1B), modifiche architetturali e loss migliorate. Le prestazioni sono impressionanti in classificazione, segmentazione e altri task di computer vision. Il backbone DINOv3 congelato funziona come **feature extractor universale** che spesso funziona out-of-the-box senza fine-tuning.
Le feature PCA di DINOv3 (ridotte a 3D e mappate come RGB) mostrano una notevole capacità di segmentare parti diverse degli oggetti senza alcuna etichetta. Le mappe di attenzione tra patch mostrano alta attivazione tra token simili (es. pixel appartenenti allo stesso oggetto).
---
## 12. Masked Autoencoder (MAE)
### Idea fondamentale
Il **Masked Autoencoder (MAE)** è un algoritmo di self-supervised learning che **maschera patch casuali** dell'immagine di input e addestra un modello a **ricostruire i pixel mancanti**. L'approccio è analogo al pre-training di BERT (dove si mascherano token nel testo), ma applicato alle immagini.
L'intuizione è che, per riuscire a ricostruire un'immagine da informazione parziale, il modello deve necessariamente **apprendere informazione semantica rilevante**: deve capire la struttura degli oggetti, le relazioni spaziali, le texture e il contesto.
### Architettura
Durante il pre-training, una grande percentuale casuale di patch (es. **75%**) viene mascherata. MAE ha due componenti:
- **Encoder** (es. un ViT): elabora **solo i patch visibili**, codificandoli in embedding. Questo è computazionalmente efficiente poiché l'encoder processa solo il 25% dei patch.
- **Decoder**: riceve gli embedding dei patch visibili più i **masked tokens** (token apprendibili che indicano le posizioni mascherate). Il suo compito è ricostruire l'immagine originale in pixel.
Dopo il pre-training, il **decoder viene scartato** e l'encoder viene applicato a immagini intere (non corrotte) per task di riconoscimento downstream.
### Applicazioni
Il pre-training MAE scala bene con più dati e modelli più grandi, ed è stato esplorato in molti domini con abbondanza di dati non etichettati:
#### SatMAE (Satellite MAE)
Applicato a immagini satellitari, dove i satelliti (es. Sentinel) acquisiscono centinaia di gigabyte al giorno. I satelliti producono dati con informazione aggiuntiva:
- **Informazione temporale**: immagini della stessa posizione geografica in periodi diversi dell'anno
- **Informazione multi-spettrale**: immagini in bande diverse (RGB, infrarosso, ecc.)
SatMAE codifica queste informazioni aggiuntive (tempo e bande spettrali) nell'encoder per apprendere rappresentazioni consapevoli del tempo e delle bande.
#### VideoMAE
Estende MAE ai video non etichettati. Il mascheramento viene applicato a **cubi** (insiemi di token nella stessa posizione spaziale ma attraverso frame diversi), con percentuali molto alte (90-95%). La ricostruzione dei cubi mascherati forza il modello ad apprendere **feature spazio-temporali**.
---
## 13. Joint-Embedding Predictive Architecture (JEPA)
### Framework generale
**JEPA** è un framework generale per il self-supervised representation learning. L'obiettivo è catturare le dipendenze tra due input compatibili $x$ e $y$ (es. frame video e audio corrispondente) **nello spazio delle rappresentazioni**, invece di generare $y$ da $x$ nello spazio degli input.
### Differenza rispetto ai modelli generativi
La differenza chiave tra architettura generativa e JEPA è che in JEPA la **loss viene applicata nello spazio degli embedding**, non nello spazio degli input. JEPA predice le **rappresentazioni** (embedding) di un segnale $y$ a partire da un segnale compatibile $x$, usando un predictor network condizionato su una variabile latente aggiuntiva $z$ che cattura l'informazione necessaria per la predizione non presente in $x$.
### I-JEPA (Image-based JEPA)
**I-JEPA** usa un singolo blocco contesto per predire le **rappresentazioni di vari blocchi target** provenienti dalla stessa immagine in posizioni specifiche. L'encoder produce rappresentazioni dei patch visibili, e il predictor produce le rappresentazioni dei patch mascherati. A differenza di MAE che ricostruisce pixel, I-JEPA predice embedding: questo lo rende meno sensibile ai dettagli di basso livello e più focalizzato sulla semantica.
### V-JEPA 2 (Video JEPA)
**V-JEPA 2** estende il framework JEPA ai video:
- Un **video clip** viene patchificato in una sequenza di token e una maschera viene applicata rimuovendo un sottoinsieme di token
- L'**encoder** elabora la sequenza video mascherata e produce un embedding per ogni token di input
- Gli output dell'encoder vengono **concatenati** con token mask apprendibili (che specificano le posizioni dei patch mascherati) e processati dal **predictor**
- Gli output del predictor vengono confrontati con i target di predizione tramite una **loss L1**
- I target vengono calcolati da un **ema-encoder** (i cui pesi sono una media mobile esponenziale dei pesi dell'encoder principale)
Dopo il pre-training, l'encoder video viene **congelato** e si apprende un nuovo predictor condizionato sulle azioni (**V-JEPA 2-AC**). Questo modello prevede rappresentazioni di frame video futuri condizionati su frame passati, azioni ed end-effector states, utile per task di pianificazione robotica.
### Panoramica dei paradigmi di apprendimento non supervisionato
- **Famiglia contrastiva (SimCLR)**: apprende avvicinando coppie positive e allontanando coppie negative
- **Paradigma emergente (DINO/DINOv2)**: usa self-distillation senza etichette
- **Approcci predittivi/ricostruttivi (MAE)**: apprende ricostruendo input mascherati
- **Modellazione latente generativa (JEPA)**: predice rappresentazioni, non pixel
Il pre-training è tipicamente costoso, richiedendo migliaia di ore di GPU.
