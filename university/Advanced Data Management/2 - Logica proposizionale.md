Quando progettiamo DB è fondamentale sapere interpretare e valutare le informazioni 
Un db è corretto se 
- Rappresenta davvero la realtà che stiamo modellando
- Una tupla, lo è se soddisfa alcune regole logiche che abbiamo definito.
Nell'SQL si usa la logica formale per definire le clausole comuni come :
- WHERE

Nella logica formale, si fanno delle conclusioni in base alle premesse, esempio:
- Se fabio mangia un dolce, allora è felice
- fabio stà mangiando un dolce
conclusione : fabio è felice.
abbiamo "calcolato" se un fatto "proposizione" è vera o falsa, in base ai fatti che sono certamentente veri o falsi, dalle premesse.

#### variabili proposizionali 
Invece di scrivere tutta la proposizione, usiamo delle variabili come notazione:
**C** = (fabio mangia un dolce) 
**H** = (Fabio è felice) 
- Se (fabio mangia un dolce), allora (Fabio è felice) 
	- Se **C** allora **H**
- (fabio stà mangiando un dolce)
	- **C**
conclusione : (fabio è felice) -> **H**
Ogni proposizione assume un valore di verità:
- VERO
- FALSO
non esistono forse o più o meno 
#### connettivi 
Per capire come valutare le premesse tra di loro, abbiamo bisogno di connettivi, come le premesse sono collegate tra loro:
- AND = "e" = **∧**
- OR = "o/oppure" = **∨**
- NOT  = "no/non" = **¬**

#### Alfabeti - simboli - parole
Alfabeto = simboli con cui componiamo le parole (stringhe)
Parole (o stringhe) = insieme d simboli contenuti nell'alfabeto
Formule = composizioni di simboli e connettivi logici che rispettano una grammatica 

#### Tabelle di verità

| OR    | V   | F   |
| ----- | --- | --- |
| **V** | V   | V   |
| **F** | V   | F   |

| AND   | V   | F   |
| ----- | --- | --- |
| **V** | V   | F   |
| **F** | F   | F   |

| OR    | **¬** |
| ----- | ----- |
| **V** | F     |
| **F** | V     |
Per valutare se una formula è vera o falsa, dobbiamo valuatare una sua interpretazione:
ovvero una funzione I che determina come valuti gli atomi

#### Esercizio 
```
I(A) = V
I(B) = F
I(C) = V
I(D) = F

formula (A OR B) AND (¬C OR D)
(A OR B) con A vero e B falso = vero
(¬C OR D) con ¬C falso e D falso = falso
quindi vero AND falso = falso

risultato falso

```

#### Conseguenza logica - modelli e teorie

– 𝐼 is a **model** of a formula 𝒇 if 𝐼(𝑓) = 𝒕𝒓𝒖𝒆 (written 𝑰 ⊨ 𝒇).
– 𝐼 is a **model** of a theory 𝑭 if 𝐼 𝑓 = 𝒕𝒓𝒖𝒆, 𝑓𝑜𝑟 𝑒𝑣𝑒𝑟𝑦 𝑓 ∈ 𝑭 (written 𝑰 ⊨ 𝑭)**.**
• A formula 𝑓 is the **consequence** of a theory 𝐺 **(**𝑮 ⊨ 𝒇**)** if for every interpretation
𝐼 tht is a **model** of 𝐺 we have that 𝐼 is a **model** of 𝑓
• **Consequences** of a set of formulae 𝐴 are often called the **theorems** of 𝐴.

Tabella verità implicazione logica

#### implicazione

| A implica B | V   | F   |
| ----------- | --- | --- |
| **V**       | V   | F   |
| **F**       | V   | V   |
Si scrive A → B ed è equivalente a ¬A OR B
(𝑓 → 𝑔) ≡ (¬ 𝑓 ∨ 𝑔)

#### Regole di De Morgan
Queste regole sono equivalenze logiche fondamentali.

– De Morgan’s Law 1: **¬ (𝑓 ∧ 𝑔) ≡ (¬𝑓 ∨ ¬𝑔)**
– De Morgan’s Law 2: **¬ (𝑓 ∨ 𝑔) ≡ (¬𝑓 ∧ ¬𝑔)**
– Double Negation: **¬¬ 𝑓 ≡ 𝑓**
