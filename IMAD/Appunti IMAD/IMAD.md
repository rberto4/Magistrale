# ==**02 - Richiamo di statistica**==

### Variabili casuali
---
Una variabile casuale $v$, è una variabile definita a partire da un esito $s$, di un esperimento casuale.

Es.
Se lancio una moneta, avrò due esiti possibili :
1. s = testa
2. s = croce
Quindi posso assegnare a $v$, il valore "testa" o "croce"
$$
v=
\begin{cases}
1 & s=testa \\
0 & s=croce
\end{cases}
$$
Indichiamo una variabile casuale v.c. come
$$
v(s)
$$

Come li descrivo ? -->  assegno una probabilità che ogni esito accada.
di conseguenza ottengo una distribuzione di probabilità.
### $v$ Variabile casuale discreta
---
Otteniamo una ==Funzione di probabilità di massa PMF==
$$
p(x) = P(v=x)
$$
ovvero, $p(x)$ è la probabilità che $v$ assuma il valore $x$
ovviamente:
$$
\sum_{i=1}^m p(x_i) = 1
$$
cioè la somma di tutte le probabilità date da tutti i valori che può assumere $v$, è uguale a 1
Es.
Se lancio un dado, ho 6 possibili esiti, quindi $m=6$, i valori possibili che possono essere assunti sono :
- $x_1=1$ 
- $x_2=2$
- $x_3=3$
- $x_4=4$
- $x_5=5$
- $x_6=6$
e le loro probabilità associata ad ogni valore di $x_i$ di $v$, è :
$$
p(x_i) = P(v=x_i) = 1/6 \quad, \forall i \in {1,2,3,4,5,6}
$$
### $v$ Variabile casuale continua
---
Otteniamo una ==Funzione di densità di probabilità PDF== $f_v(x)$
in questo caso parlare di probabilità che i singoli valori vengano assunti, non ha senso in quanto abbiamo infiniti valori, e quindi la probabilità che ognuno di essi venga assunto è praticamente zero
$$
P(v = x) \approx 0
$$
La probabilità che $v$ assuma un valore specifico, è $\frac {1}{\infty}$
La PDF definisce che la probabilità $v$ appartenga ad un intervallo di valori. $[a,b]$
$$
P(v \in [a,b])=\int_{a}^{b} f_v(x)\,dx
$$
dove l'integrale tra $+\infty$ e $-\infty$ è uguale a 1.
### Funzione di densità cumulata (cdf)
---
$$
F_v(z)=\int_{-\infty}^{z} f_v(x)\,dx = P(v\leq z)
$$
### Valore atteso
---
il valore atteso di una variabile casuale $v$ è la somma pesata di tutti i valori che $v$ può assumere, dove i pesi sono le probabilità che si possa assumere il valore $x$
$$
\mathbb{E}_s[v]=\int_{-\infty}^{\infty} x f_v(x)\,dx
$$
Gode della proprietà di linearità
Considera tutti i possibili esiti $s$ della variabile casuale $v$. Si considera implicita la dipendenza da $s$
### Varianza
---
La varianza indica quanto i valori si discostano dalla loro media.
$$
Var[v] = \int_{-\infty}^{\infty} (x-\mathbb{E}[v]^2)f_v(x)\,dx
$$
Se la varianza è piccola, allora $v$ assume valori molti simili tra loro.
Proprietà:
1. Se $Var[v]=0$ allora $v$ è deterministica e assume solo 1 valore.
2. $Var[v] = \mathbb{E}[v^2] - \mathbb{E}[v]^2$
### Deviazione standard
---
Deviazione standard è la radice della varianza, ed è una misura di dispersione: indica quanto i valori di una V.C, tendono a staccarsi dal proprio valore atteso.
$$
\sigma = \sqrt{Var[v]}
$$
- se $\sigma$ è piccola : bassa variabilità, valori vicini alla media
- se $\sigma$ è grande : alta variabilità, valori distanti dalla media
### Covarianza
---
é una misura che indica quanto 2 variabili casuali, variano insieme.
Indica quanto le variazioni dalla media di una v.c, seguano le variazioni dalla media di un altra v.c.
$$
Cov[v_1,v_2] = {\mathbb{E}[v_1-\mathbb{E}[v_1]] * \mathbb{E}[v_2-\mathbb{E}[v_2]]}
$$
Due variabili casuali  $v_1$ e $v_2$, si dicono scorrelate se $Cov[v_1,v_2]=0$
### Coefficiente di correlazione
---
Date due variabili casuali $v_1$ e $v_2$, il coefficiente di correlazione è definito come :
$$
\rho[v_1,v_2] = \frac {\mathbb{E}[v_1-\mathbb{E}[v_1]] * \mathbb{E}[v_2-\mathbb{E}[v_2]]} {\sigma[v_1]\sigma[v_2]}
$$
oppure usando la covarianza :
$$
\rho[v_1,v_2] = \frac {Cov[v_1,v_2]} {\sigma[v_1]\sigma[v_2]}
$$
Se $\rho=0$ allora le variabili sono scorrelate.
$\rho$ indica il grado di dipendenza lineare tra $v_1$ e $v_2$
### Estensione al caso multivariabile
---
Al posto di avere una sola v.c., abbiamo un vettore di variabili casuali 
$$
V=[v_1,v_2,v_3,...,v_d]^T \in \mathbb{R}^{d*1}
$$
la funzione di densità cumulata CDF
$$
F_v(z_1,z_2,z_3,...,z_d) = P(v_1\leq z_1,v_2\leq z_2,v_3\leq z_3,...,v_d\leq z_d) = 
$$
$$
= \int_{-\infty}^{z_1} \int_{-\infty}^{z_2} \int_{-\infty}^{z_3} ...\int_{-\infty}^{z_d} f_{v_1,v_2,v_3,...,v_d}(x_1,x_2,x_3,...,x_d) \,dx_1dx_2dx_3...dx_d
$$
### valore atteso (caso multivariabile)
---
Il valore atteso è un vettore colonna di $d$ componenti.
$$
\mathbb{E}[V] = [\mathbb{E}[v_1],\mathbb{E}[v_2],\mathbb{E}[v_3],...,\mathbb{E}[v_d]]^T \in \mathbb{R}^{d*1}
$$
### Varianza (caso multivariabile)
---
La varianza diventa una matrice $dxd$ semidefinita positiva, ovvero con tutti autovalori non negativi, e simmetrica
$$
Var[V] = \int_{\mathbb{R^d}} (X - \mathbb{E}[V])(X - \mathbb{E}[V])^T f_v(X)dX =
$$
$$
= \begin{bmatrix}
Var[v_1] & ... & Cov[v_1,v_d] \\
... & ... & ... \\
Cov[v_d,v_1] &...& Var[v_d]
\end{bmatrix}
$$
### funzione di probabilità congiunta e indipendenza
---
Date due variabili $v_1$ e $v_2$,  hanno $f$ di probabilità congiunta 
$$
f_{v_1,v_2} (x,y) = P(v_1=x, v_2=y)
$$
si dicono indipendenti se :
$$
f_{v_1,v_2} (x,y) = f_{v_1}(x)*f_{v_2}(y)
$$
se sono indipendenti allora sono anche incorrelate, ma non è necessariamente vero il contrario.
### Teoria della stima e stimatori

