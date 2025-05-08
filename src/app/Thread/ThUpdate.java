package app.Thread;

import javax.swing.JPanel;

import app.view.PannelMain;

/**
 * La classe {@code ThUpdate} estende {@link Thread} e gestisce l'aggiornamento periodico di un pannello 
 * {@link PannelMain} in un'interfaccia grafica. Il thread esegue un ciclo in cui aggiorna il pannello a intervalli regolari.
 * 
 */
public class ThUpdate extends Thread {
    private PannelMain x;

    /**
     * Costruttore per creare un nuovo thread che aggiorna il pannello {@link PannelMain}.
     * 
     * @param x Il pannello principale {@link PannelMain} che deve essere aggiornato periodicamente.
     */
    public ThUpdate(PannelMain x) {
        this.x = x;
    }

    /**
     * Esegue il thread che aggiorna il pannello {@code PannelMain} periodicamente.
     * <p>
     * Il ciclo del thread continua finché il pannello è visibile. In ogni iterazione, il metodo {@code update()} del pannello
     * viene chiamato per eseguire l'aggiornamento. Dopo ogni aggiornamento, il thread si sospende per 500 millisecondi prima di eseguire
     * il prossimo aggiornamento.
     * </p>
     * Se si verifica un'eccezione durante l'esecuzione, viene stampato un messaggio di errore.
     */
    public void run() {
        try {
            while (x.isVisible()) {
                x.update();
                sleep(500);  // Pausa di 500 millisecondi tra ogni aggiornamento
            }
        } catch (Exception e) {
            // Gestione delle eccezioni durante il ciclo
            e.printStackTrace();
        }
    }
}
