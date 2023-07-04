# CardManager
## Come nasce CardManager
Il progetto nasce sulla base della richiesta da parte del docente di realizzare una webApp per la gestione di carte di credito mediante 3 principali ruoli, ovvero venditori, clienti e amministratori.
Le specifiche di progetto vengono allegate al repository nel file "Progetto_WSDA.pdf"


## Commenti del docente
- La gestione della sessione bastava farla con spring security non era necessario implementarla a mano.
- I riferimenti assoluti nel main.js dovevano essere relativi, così da non compromettere il deploy su una macchina nel caso in cui si facesse.
- Usare un solo oggetto di response dei controller è poco pratico perchè trasporti ogni volta molta roba che non ti serve.
