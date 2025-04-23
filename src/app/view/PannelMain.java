package app.view;

import app.Thread.ThUpdate;
import app.controller.Controller;
import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

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
		scrollPaneCartelleUtente.setBounds(10, 40, 133, 425);
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
		btnCaricaMp3.setBounds(10, 476, 133, 31);
		btnCaricaMp3.addActionListener(e -> onBtnCaricaMp3());
		add(btnCaricaMp3);
		
		btnPlay = new JButton("Play");
        btnPlay.setBounds(440, 478, 71, 27);
        add(btnPlay);
        
        btnPausa = new JButton("Pausa");
        btnPausa.setBounds(523, 478, 71, 27);
        add(btnPausa);
        
        btnAvanti = new JButton(">>");
        btnAvanti.setBounds(606, 478, 71, 27);
        btnAvanti.addActionListener(e -> onBtnAvanti() );
        add(btnAvanti);
        
        btnIndietro = new JButton("<<");
        btnIndietro.setBounds(357, 478, 71, 27);
        btnIndietro.addActionListener(e -> onBtnIndietro() );
        add(btnIndietro);
        
        JLabel lbltit = new JLabel("scrive lo sytato");
        lbltit.setBounds(359, 448, 318, 17);
        add(lbltit);
		
		new ThUpdate(this).start();//deve restare in fondo
	}
	
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
				scrollPaneCanzoni.setBounds(141, 40, 133, 425);
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
	
	//bottoni:
	private void onBtnLogout() {
		controller.logout();
		frame.dispose();
	}
	private void onBtnAvanti() {
		controller.SkipCanzone();
	}
	
	private void onBtnCaricaMp3() {
		controller.AddFileMp3();
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
	
	public void update() {	//eseguita ogni secondo fino alla chiusura
		addCartelleUtenteButton(); 
	}
	
}