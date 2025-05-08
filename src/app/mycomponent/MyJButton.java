package app.mycomponent;

import javax.swing.JButton;

/**
 * {@code MyJButton} è una sottoclasse personalizzata di {@link JButton}
 * che aggiunge un campo intero {@code idx} per identificare il bottone
 * o associarlo a un indice specifico.
 * <p>
 * Può essere utilizzato ad esempio in una lista di bottoni per rappresentare
 * elementi indicizzati come canzoni, playlist, righe di una tabella, ecc.
 * </p>
 * 
 */
public class MyJButton extends JButton {

    /** Indice associato al bottone */
    private int idx;

    /**
     * Costruisce un nuovo {@code MyJButton} con il testo specificato.
     *
     * @param nome il testo del bottone
     */
    public MyJButton(String nome) {
        super(nome);
    }

    /**
     * Restituisce l'indice associato a questo bottone.
     *
     * @return l'indice del bottone
     */
    public int getIdx() {
        return idx;
    }

    /**
     * Imposta l'indice associato a questo bottone.
     *
     * @param idx l'indice da assegnare
     */
    public void setIdx(int idx) {
        this.idx = idx;
    }
}
