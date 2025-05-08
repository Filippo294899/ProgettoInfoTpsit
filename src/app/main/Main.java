package app.main;

import app.controller.Controller;
import app.model.Model;

/**
 * Classe principale che avvia l'applicazione.
 * <p>
 * Inizializza il modello e il controller, e avvia l'interfaccia utente.
 */
public class Main {

    /**
     * Metodo principale (entry point) dell'applicazione.
     *
     * @param args argomenti da linea di comando (non utilizzati).
     */
    public static void main(String[] args) {
        Model model = new Model();               // Crea l'istanza del modello
        Controller controller = new Controller(model);  // Crea il controller con il modello
        controller.start();                      // Avvia l'applicazione
    }

}
