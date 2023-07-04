# CardManager

## Come nasce CardManager
Il progetto nasce sulla base della richiesta da parte del docente di realizzare una webApp per la gestione di carte di credito mediante 3 principali ruoli, ovvero venditori, clienti e amministratori.
Le specifiche di progetto vengono allegate al repository nel file "Progetto_WSDA.pdf"

## Svelte di sviluppo
Si è optato per un approccio che usasse spring come backend e il classico stack HTML, CSS e JS come frontend, il tutto collegato ad un classico database di tipo relazionale gestito in MySQL (di cui si allega il file "MySQL_DB.sql".

## Come posso testare?
Basta scaricare il progetto, importarlo su un IDE ed attendere che questo scarichi le dipendenze necessarie, poi importare il database in locale (si può usare un qualsiasi software, anche XAMPP) e infine interrogare l'indirizzo "http://localhost:8080" dopo aver lanciato la run del progetto tramite l'IDE.

N.B.: E' importante che si inseriscano le corrette credenziali di accesso al DB all'interno del file "application.properties" nel quale al momento sono settate su username: prova e password non valorizzata.

## Commenti del docente
- La gestione della sessione bastava farla con spring security non era necessario implementarla a mano.
- I riferimenti assoluti nel main.js dovevano essere relativi, così da non compromettere il deploy su una macchina nel caso in cui si facesse.
- Usare un solo oggetto di response dei controller è poco pratico perchè trasporti ogni volta molta roba che non ti serve.
