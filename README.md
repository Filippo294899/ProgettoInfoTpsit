# 🎧 ProgettoInfoTpsit - Lettore MP3 Intelligente

## 📌 Obiettivo

Creare un lettore MP3 in Java con funzionalità avanzate:
- Riproduzione musicale con interfaccia grafica.
- Algoritmi di riconoscimento dei gusti musicali dell’utente.
- Generazione automatica di **playlist personalizzate** basate sui gusti rilevati.

## 🛠️ Strumenti Utilizzati

- **Linguaggio:** Java  
- **IDE:** Eclipse  
- **Librerie GUI:** Java Swing (eventualmente JavaFX o altre, da confermare con il prof. Zerilli)  
- **UI Builder:** WindowBuilder (in attesa di approvazione per alternative)  
- **Build Tool (opzionale):** Gradle  

## 🧠 Struttura del Progetto

### ✅ Fasi principali:

1. **Verifica API Spotify**  
   Capire se è possibile utilizzare l'API di Spotify per riconoscere automaticamente il genere musicale dei brani.

2. **Lettore multimediale (iniziale)**  
   - Implementare un lettore MP3 base con **Java Swing**.  
   - Uso delle funzioni dei file viste a lezione.  
   - Strutturazione secondo il **pattern MVC**.

3. **UI e Debug**  
   - Cura dell’interfaccia grafica.  
   - Gestione efficiente degli errori.  
   - Debugging completo del progetto.

4. **Algoritmo di classificazione**  
   - Analisi dei gusti musicali utente.  
   - Generazione dinamica di playlist personalizzate.

5. **(Opzionale) Portabilità**  
   - Rilasciare l’app come eseguibile semplice su diversi dispositivi.

6. **Fine tuning**  
   - Ritocchi finali, da definire in base all’evoluzione del progetto.

---

## 👥 Divisione dei Ruoli

| Nome | Compiti Assegnati |
|------|-------------------|
| @franz | Gestione del **Model**, salvataggio file MP3 e organizzazione in cartelle per **genere**. Le canzoni con più generi saranno duplicate in più cartelle. |
| @filippo | Creazione dello **schema grafico** dell’app: layout, design preliminare, flusso. (NO implementazione diretta all’inizio). |
| @òmoro | Implementazione della **grafica** e gestione della **Controller logic**. |
| @ivagnez | Affiancamento nella gestione della **grafica** e logica **Controller**. |

---

## 📂 Struttura Consigliata (esempio)

```
ProgettoInfoTpsit/
├── src/
│   ├── controller/
│   ├── model/
│   ├── view/
│   └── Main.java
├── lib/ (librerie esterne se approvate)
├── assets/ (canzoni di esempio)
└── README.md
```

---

## 🔗 Note Extra

- Attendere conferma da Zerilli per l'utilizzo di librerie esterne come JavaFX o Gradle.
- Se l’API Spotify non fosse utilizzabile, valutare alternative locali per il riconoscimento del genere.

---

## 🧪 TODO

- [ ] Verifica utilizzo API Spotify  
- [ ] Lettore MP3 base funzionante  
- [ ] Prototipo interfaccia  
- [ ] Algoritmo di classificazione  
- [ ] Debug completo  
- [ ] Packaging e distribuzione

---

> _"Coding like a rockstar 🎸 — ma con MVC ben fatto."_  
```
