package app.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import app.controller.Controller;

public class PanelPlayer extends JPanel {
    private static final long serialVersionUID = 1L;
    private Controller controller;
    private JButton btnPlay;
    private JButton btnPause;
    private JButton btnNext;
    private JButton btnPrevious;
    private JLabel lblCurrentSong;

    public PanelPlayer(Controller controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 0));
        
        // Panel per i controlli
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        // Bottoni di controllo
        btnPrevious = new JButton("<<");
        btnPrevious.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnPrevious.addActionListener(e -> controller.previousSong());
        
        btnPlay = new JButton("Play");
        btnPlay.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnPlay.addActionListener(e -> controller.playSong());
        
        btnPause = new JButton("Pause");
        btnPause.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnPause.addActionListener(e -> controller.pauseSong());
        
        btnNext = new JButton(">>");
        btnNext.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNext.addActionListener(e -> controller.nextSong());
        
        // Etichetta per il nome della canzone corrente
        lblCurrentSong = new JLabel("Nessuna canzone in riproduzione", SwingConstants.CENTER);
        lblCurrentSong.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        // Aggiungi componenti al pannello di controllo
        controlPanel.add(btnPrevious);
        controlPanel.add(btnPlay);
        controlPanel.add(btnPause);
        controlPanel.add(btnNext);
        
        // Aggiungi pannello di controllo e etichetta canzone al pannello principale
        add(lblCurrentSong, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.CENTER);
    }
    
    public void updateCurrentSong(String songName) {
        if(songName != null && !songName.isEmpty()) {
            lblCurrentSong.setText("In riproduzione: " + songName);
        } else {
            lblCurrentSong.setText("Nessuna canzone in riproduzione");
        }
    }
}