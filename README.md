# A* Pathfinding Visualizer

Un'applicazione Java interattiva per visualizzare in tempo reale il funzionamento dell'algoritmo di ricerca **A* (A-Star)**. Applicazione pratica di algoritmi di graph traversal

## Caratteristiche

* **Visualizzazione Real-Time:** Osserva l'algoritmo mentre esplora i nodi (Open Set, Closed Set) e trova il percorso ottimale.
* **Editor Interattivo:**
    * Disegna muri e ostacoli trascinando il mouse.
    * Posiziona dinamicamente i punti di Start e End.
    * Generatore automatico di labirinti (Recursive Backtracker).
* **Configurazione:** Slider per regolare il peso dell'euristica in tempo reale.
* **Performance:** Esecuzione fluida grazie all'uso di `SwingWorker` per il calcolo in background (multithreading), mantenendo la UI sempre reattiva.

## Tecnologia e Architettura

Sviluppato in **Java (Swing)** seguendo il pattern architetturale **MVC (Model-View-Controller)** per garantire modularità e manutenibilità.

* **Model:** Gestione della griglia logica e implementazione pura degli algoritmi (A*, DFS per labirinti).
* **View:** Componenti Swing personalizzati con rendering grafico ottimizzato (`paintComponent`).
* **Controller:** Gestione degli eventi utente e coordinamento tra UI e logica di business.

### Prerequisiti
* Java JDK 17 o superiore.

### Compilazione e Avvio
```bash
# Compila tutti i file sorgente
javac -d bin src/*.java src/model/*.java src/controller/*.java src/view/*.java

# Avvia l'applicazione
java -cp bin Main
```
