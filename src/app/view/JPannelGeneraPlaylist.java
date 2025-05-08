package app.view;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import app.controller.Controller;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * La classe JPannelGeneraPlaylist rappresenta l'interfaccia grafica (GUI)
 * per generare una playlist musicale in base ai generi selezionati dall'utente.
 * Offre un set di checkbox per permettere all'utente di scegliere i propri generi musicali preferiti,
 * un campo di testo per inserire il nome della playlist e un pulsante per generare la playlist.
 * 
 */
@SuppressWarnings("serial")
public class JPannelGeneraPlaylist extends JFrame {
    
    /** L'istanza del controller utilizzata per gestire le azioni dell'utente e aggiornare la vista. */
    private Controller controller;
    
    /** Lista delle checkbox che rappresentano i generi musicali disponibili. */
    private ArrayList<JCheckBox> checkboxs;
    
    /** Campo di testo per inserire il nome della playlist. */
    private JTextField textField;
    
    /** Etichetta che mostra il testo "Nome Playlist:" */
    private JLabel lblNomePLaylist;
    
    /**
     * Costruttore della classe JPannelGeneraPlaylist.
     * Inizializza l'interfaccia utente con i generi musicali disponibili,
     * il campo di testo per il nome della playlist e il pulsante per generare la playlist.
     * 
     * @param c Il controller utilizzato per gestire la logica dell'applicazione.
     */
    public JPannelGeneraPlaylist(Controller c) {
        JPanel pannel = new JPanel();
        checkboxs = new ArrayList<JCheckBox>();
        setContentPane(pannel);
        setResizable(false);
        setBounds(100, 100, 260, 414);
        getContentPane().setLayout(null);
        controller = c;
        
        JLabel lblTIT = new JLabel("Genera La tua Playlist", SwingConstants.CENTER);
        lblTIT.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblTIT.setBounds(10, 10, 231, 22);
        pannel.add(lblTIT);
        
        JLabel lblDestrizione = new JLabel("<html>Seleziona i tuoi generi musicali preferiti <br> e crea la tua playlist</html>", SwingConstants.CENTER);
        lblDestrizione.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDestrizione.setBounds(10, 42, 231, 26);
        pannel.add(lblDestrizione);
        
        JButton btnGeneraPlaylist = new JButton("Genera");
        btnGeneraPlaylist.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnGeneraPlaylist.setBounds(10, 346, 231, 21);
        btnGeneraPlaylist.addActionListener(e -> onBtnGeneraPlaylist());
        pannel.add(btnGeneraPlaylist);
        
        lblNomePLaylist = new JLabel("Nome Playlist:");
        lblNomePLaylist.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNomePLaylist.setBounds(10, 318, 86, 13);
        pannel.add(lblNomePLaylist);
        
        textField = new JTextField();
        textField.setBounds(106, 315, 130, 19);
        pannel.add(textField);
        textField.setColumns(10);
        
        int x = 10;
        int y = 80;
        int h = 21;
        int w = 115;
        int cont = 1;
        for (String nomeGenere : controller.getAllgeneriMusicali()) {
            JCheckBox chckbxNewCheckBox = new JCheckBox(nomeGenere);
            if (cont == 1)
                chckbxNewCheckBox.setBounds(x, y, w, h);
            else
                chckbxNewCheckBox.setBounds(x + 116, y, w, h);
            pannel.add(chckbxNewCheckBox);
            
            if (cont == 2) {
                cont = 1;
                y += 20; // altezza da modificare se si vuole
            } else
                cont++;
            checkboxs.add(chckbxNewCheckBox);
        }
    }

    /**
     * Gestisce l'evento di clic sul pulsante "Genera" per creare la playlist
     * con i generi musicali selezionati e il nome specificato.
     */
    private void onBtnGeneraPlaylist() {
        ArrayList<String> generiPlaylist = new ArrayList<String>();
        for (JCheckBox x : checkboxs)
            if (x.isSelected())
                generiPlaylist.add(x.getText());
        controller.generaPlaylist(generiPlaylist, textField.getText());
        this.dispose();
    }
}
