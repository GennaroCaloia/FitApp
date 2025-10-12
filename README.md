# FitTrack API

Backend demo realizzato in **Spring Boot** per la gestione di utenti, esercizi, piani e workout.  
Progetto creato con l'obiettivo di mostrare best practice architetturali e tecniche durante i colloqui con recruiter e aziende.

## 📝 Stack
- Java 17
- Spring Boot 3.x
- PostgreSQL + Liquibase
- MinIO per la gestione media
- Spring Security + JWT
- Docker / Docker Compose
- Testcontainers

## 🧿 Obiettivi
- Dimostrare conoscenza di progettazione REST
- Implementare sicurezza, validazioni e test
- Documentare API con OpenAPI/Swagger

---

## Avvio rapido

### 🅰 Opzione A — senza DB (profilo `nodb`)
```bash
./mvnw spring-boot:run
```

### 🅱 Opzione B - con DB (profilo `dev`)
```bash
./mvnw spring-boot:run -D"spring-boot.run.profiles=dev"
docker compose up -d db
```

### 🚀 Accesso Rapido (Ambiente di dev)
Per accedere all'API e generare un token JWT, utilizzare l'utente demo pre-caricato:

| Campo | Valore |
|-------|---------|
| **Username** | `demo` |
| **Password** | `demo` |

---

### 🔑 Workflow di Autenticazione JWT (Swagger UI) ###
Swagger UI è configurato per utilizzare l'autenticazione Bearer Token. Seguire questi passaggi per autorizzare le richieste:

1. **Login**: Inviare una richiesta POST all'endpoint /auth/login utilizzando le credenziali demo/demo.

2. **Copiare il Token**: Copiare l'intero valore del campo accessToken dalla risposta 200 OK.

3. **Autorizza**: Cliccare sul pulsante Authorize (l'icona a forma di lucchetto 🔒 in alto a destra) e incollare il token nel campo Value. Non è necessario aggiungere il prefisso "Bearer".

4. **Fatto!** Ora si possono eseguire tutti gli endpoint protetti.

(Nota: Il token scadrà dopo l'intervallo configurato!)

---

## 🧭 Endpoint Principali dell'API ###
Questa sezione elenca i principali endpoint disponibili, raggruppati per funzionalità.

### 🔐 Autenticazione & Utenti
| Metodo | Endpoint | Descrizione |
|--------|-----------|-------------|
| `POST` | `/auth/login` | Esegue il login e restituisce un token JWT |
| `POST` | `/auth/register` | Registra un nuovo utente |
| `GET`  | `/api/users/me` | Recupera il profilo utente corrente |

### 💪 Gestione Esercizi (CRUD)
| Metodo | Endpoint | Descrizione |
|--------|-----------|-------------|
| `GET` | `/api/exercises` | Elenca tutti gli esercizi |
| `POST` | `/api/exercises` | Crea un nuovo esercizio |
| `PATCH` | `/api/exercises/{id}` | Aggiorna un esercizio |
| `DELETE` | `/api/exercises/{id}` | Elimina un esercizio |
### 🗓️ Piani di Allenamento
| Metodo | Endpoint | Descrizione |
|--------|-----------|-------------|
| `GET` | `/api/plans` | Elenca i piani dell’utente |
| `POST` | `/api/plans` | Crea un nuovo piano |
| `PATCH` | `/api/plans/{id}` | Aggiorna un piano |
| `DELETE` | `/api/plans/{id}` | Elimina un piano |
### 📊 Workout e Progresso
| Metodo | Endpoint | Descrizione |
|--------|-----------|-------------|
| `GET` | `/api/workouts/latest` | Recupera l’ultimo workout |
| `GET` | `/api/workouts/{id}` | Dettaglio di un workout specifico |

---

## 🐳 Avvio Completo con Docker Compose (app + DB)

### Istruzioni da eseguire:

```shell
docker compose up -d --build
# app su :8080 (profilo dev)
```

**🔍 Dettagli**:
- Servizi: Avvia l'immagine del Backend (con il profilo dev attivo) e il container PostgreSQL.

- Accesso App: L'applicazione è disponibile su: http://localhost:8080

- Swagger UI: Accessibile all'indirizzo http://localhost:8080/swagger-ui/index.html

