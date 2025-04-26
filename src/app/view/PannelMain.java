package app.view;

import app.Thread.ThUpdate;
import app.controller.Controller;
import app.mycomponent.MyJButton;

import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Container;


public class PannelMain extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private FrameLogin frame;
	private JPanel panelCartelleCanzoni;
	private JPanel panelCanzoni;
	private JScrollPane scrollPaneCanzoni;
	private JButton btnAvanti;
	private  JButton btnIndietro;
	private  JButton btnPlay;
	private JButton btnPausa;
	private JLabel lbltit;
	private JSlider SliderTimeSong;
    private JLabel lblTimeSong;
	private JPanel panelCodaCanzoni;
	private JScrollPane scrollPaneCodaCanzoni;
    
    private boolean isSopraSlider;
    private boolean isSliderClicked;
    
	public PannelMain( Controller c , FrameLogin frame) {
		
		controller=c;
		this.frame=frame;
		
		frame.setBounds(0, 0, 720, 580);
		setBounds(0, 0, 700, 600);
		setLayout(null);
		
		controller.setCartellaPrincipale(controller.getElementoFileLogin(t-> t.equals("nome")));
		
		JLabel lblTitolo = new JLabel("Bentornato "+controller.getElementoFileLogin(t->t.equals("nome")),SwingConstants.CENTER);
		lblTitolo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitolo.setBounds(10, 11, 680, 31);
		add(lblTitolo);
		
		JScrollPane scrollPaneCartelleUtente = new JScrollPane();
		scrollPaneCartelleUtente.setBounds(20, 57, 133, 372);
		add(scrollPaneCartelleUtente);
		
		JLabel lblCartelle = new JLabel("Cartelle Utente",SwingConstants.CENTER);
		lblCartelle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPaneCartelleUtente.setColumnHeaderView(lblCartelle);
		
		panelCartelleCanzoni = new JPanel();
		panelCartelleCanzoni.setLayout((LayoutManager) new BoxLayout(panelCartelleCanzoni, BoxLayout.Y_AXIS));
		scrollPaneCartelleUtente.setViewportView(panelCartelleCanzoni);
		
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogout.setBounds(593, 11, 97, 31);
		btnLogout.addActionListener(e -> onBtnLogout());
		add(btnLogout);
		
		JButton btnCaricaMp3 = new JButton("Carica Mp3");
		btnCaricaMp3.setBounds(20, 466, 133, 27);
		btnCaricaMp3.addActionListener(e -> onBtnCaricaMp3());
		add(btnCaricaMp3);
		
		btnPlay = new JButton("Play");
        btnPlay.setBounds(438, 468, 71, 27);
        btnPlay.addActionListener(e -> onBtnPlay() );
        add(btnPlay);
        
        btnPausa = new JButton("Pausa");
        btnPausa.setBounds(519, 468, 71, 27);
        btnPausa.addActionListener(e -> onBtnPausa() );
        add(btnPausa);
        
        btnAvanti = new JButton(">>");
        btnAvanti.setBounds(604, 468, 71, 27);
        btnAvanti.addActionListener(e -> onBtnAvanti() );
        add(btnAvanti);
        
        btnIndietro = new JButton("<<");
        btnIndietro.setBounds(357, 468, 71, 27);
        btnIndietro.addActionListener(e -> onBtnIndietro() );
        add(btnIndietro);
        
        lbltit = new JLabel("",SwingConstants.CENTER);
        lbltit.setBounds(357, 440, 318, 17);
        add(lbltit);
		
        SliderTimeSong = new JSlider();
        SliderTimeSong.setBounds(357, 495, 318, 26);
        SliderTimeSong.setMinimum(0);
        SliderTimeSong.addChangeListener(e -> onSliderTimeSong());
        SliderTimeSong.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				isSliderClicked=false;
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				isSliderClicked=true;	
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				isSopraSlider=false;				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				isSopraSlider=true;
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		add(SliderTimeSong);
		
		lblTimeSong = new JLabel("",SwingConstants.CENTER);
		lblTimeSong.setBounds(357, 519, 308, 17);
		add(lblTimeSong);
		
		scrollPaneCodaCanzoni = new JScrollPane();
		scrollPaneCodaCanzoni.setBounds(530, 53, 160, 376);
		add(scrollPaneCodaCanzoni);
		
		JLabel lblCoda = new JLabel("Coda", SwingConstants.CENTER);
		lblCoda.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPaneCodaCanzoni.setColumnHeaderView(lblCoda);
		
		panelCodaCanzoni = new JPanel();
		panelCodaCanzoni.setLayout((LayoutManager) new BoxLayout(panelCodaCanzoni, BoxLayout.Y_AXIS));
		scrollPaneCodaCanzoni.setViewportView(panelCodaCanzoni);
				
		
		new ThUpdate(this).start();
	}
	//update
	public void addCartelleUtenteButton() {			//genera i bottoni delle cartelle e delle canzoni
		panelCartelleCanzoni.removeAll();
		for(String s:controller.getElencoCartelleUtente()) {
			JButton btnNewButton = new JButton(s);
			panelCartelleCanzoni.add(btnNewButton);
			
			btnNewButton.addActionListener(e -> {				
				controller.setPlaylistRiprodotta(s);
				
				if (scrollPaneCanzoni != null)
					remove(scrollPaneCanzoni);

				scrollPaneCanzoni = new JScrollPane();
				scrollPaneCanzoni.setBounds(151, 57, 160, 372);
				add(scrollPaneCanzoni);

				JLabel lblCanzoni = new JLabel("Canzoni - " + s, SwingConstants.CENTER);
				lblCanzoni.setFont(new Font("Tahoma", Font.PLAIN, 15));
				scrollPaneCanzoni.setColumnHeaderView(lblCanzoni);

				panelCanzoni = new JPanel();
				panelCanzoni.setLayout(new BoxLayout(panelCanzoni, BoxLayout.Y_AXIS));
				scrollPaneCanzoni.setViewportView(panelCanzoni);
				
				addCanzoniPanelCanzoni();
			});
		}
		panelCartelleCanzoni.revalidate();
		panelCartelleCanzoni.repaint();
	}
	
	public void addCanzoniPanelCanzoni() {	//da finire, non si aggiornano le canzoni in tempo reale
		panelCanzoni.removeAll();
		
		for(String s: controller.getElencoCanzoniCartellaRiprodotta()) {
			JButton btnNewButton = new JButton(controller.togliTXTtoCanzone(s));
			panelCanzoni.add(btnNewButton);
			btnNewButton.addActionListener(e -> controller.AccodaCanzone(s));	// fa partire il thread per l'usica audio - da finire
		}
		
		panelCanzoni.revalidate();
		panelCanzoni.repaint();
	}
	private int variabileDiCiclo;
	public void addCodaCanzoni() {		
		panelCodaCanzoni.removeAll();
		ArrayList<String> songs=controller.getCodaCanzoni();
		
		for(variabileDiCiclo=0;variabileDiCiclo<songs.size();variabileDiCiclo++) {
			
			MyJButton btnNewButton = new MyJButton(songs.get(variabileDiCiclo));
			btnNewButton.setIdx(variabileDiCiclo);
			if(variabileDiCiclo==controller.getCurrentSong())
				btnNewButton.setBackground(Color.green);
			
			panelCodaCanzoni.add(btnNewButton);
			
			btnNewButton.addActionListener(e -> {				
				controller.setCurrentSong(btnNewButton.getIdx());
			});
		}
		panelCodaCanzoni.revalidate();
		panelCodaCanzoni.repaint();
	}
	
	public void setVisibileScrollCoda(boolean flag) {
		scrollPaneCodaCanzoni.setVisible(flag);
	}
	
	private void setLblTit() {
		lbltit.setText(controller.getStatoCanzone());
	}
	private void setLblTimeSong() {
		lblTimeSong.setText(controller.getTimeSong()+" secondi");
	}
	private void setSliderMaxTimeSong() {
		SliderTimeSong.setMaximum(controller.getLenghtSong());
	}
	
	private int varibailePerFarFuzionarePiuOMenoLoSlider=0;
	private void setSliderValue() {
		varibailePerFarFuzionarePiuOMenoLoSlider++;
		if(varibailePerFarFuzionarePiuOMenoLoSlider%2==0)
			SliderTimeSong.setValue(SliderTimeSong.getValue()+1);
	}
	
	//bottoni:
	private void onBtnLogout() {
		controller.logout();
		frame.dispose();
	}
	private void onBtnAvanti() {
		controller.SkipCanzone();
	}
	
	private void onBtnCaricaMp3() {
		showFileChooser();
	}
	private void onBtnIndietro() {
		controller.ScalaCanzone();
	}
	private void onBtnPlay() {
		controller.playSong();
	}
	private void onBtnPausa() {
		controller.stopSong();
	}
	
	private void onSliderTimeSong() {
		if(isSopraSlider&isSliderClicked)
			controller.setTimeSong(SliderTimeSong.getValue());
	}
	
	
	private void showFileChooser() {
        JFileChooser selettore = new JFileChooser();
        selettore.setMultiSelectionEnabled(true);
        selettore.setFileFilter(new FileNameExtensionFilter("File audio (.mp3)", "mp3"));	

        int risultato = selettore.showOpenDialog(frame);
        
        if (risultato == JFileChooser.APPROVE_OPTION) 
        	controller.AddFileMp3(selettore.getSelectedFiles());
        else 
            Allert("Errore nella selezione dei file");   
	}
	private void Allert(String allertString) {
	    JOptionPane.showOptionDialog(
	            this, 
	            allertString, // Messaggio di errore
	            "Errore Login", // Titolo della finestra
	            JOptionPane.DEFAULT_OPTION, // Tipo di finestra
	            JOptionPane.ERROR_MESSAGE, // Icona dell'errore
	            null, // Non ci sono icone personalizzate
	            new Object[] { "OK" }, // Pulsante OK per continuare
	            "OK" // Default button
	        );
	}
	public void update() {	//eseguita ogni mezzo secondo fino alla chiusura
		addCartelleUtenteButton(); 
		
		if(controller.IsCodaExist()) {
			setVisibileScrollCoda(true);
			addCodaCanzoni();
		}else
			setVisibileScrollCoda(false);
		
		setLblTit();
		setLblTimeSong();
		setSliderMaxTimeSong();
		setSliderValue();
	}
}