package app.view;
import java.awt.*;
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
import javax.swing.border.LineBorder;

import java.awt.Color;

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
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    int w = getWidth(), h = getHeight();
	    Color color1 = new Color(41,41,41);
	    Color color2 = Color.black;
	    GradientPaint gp = new GradientPaint(0, 0, color1, 0,h*2 , color2); // Nero in alto, verde in basso
	    g2d.setPaint(gp);
	    g2d.fillRect(0, 0, w, h);
	}

	public PannelMain( Controller c , FrameLogin frame) {
		
		controller=c;
		this.frame=frame;
		
		
		
		frame.setBounds(0, 0, 720, 580);
		setBounds(0, 0, 700, 600);
		setLayout(null);
		
		controller.setCartellaPrincipale(controller.getElementoFileLogin(t-> t.equals("nome")));
		
		JLabel lblTitolo = new JLabel("Bentornato "+controller.getElementoFileLogin(t->t.equals("nome")),SwingConstants.CENTER);
		lblTitolo.setForeground(new Color(0, 255, 0));
		lblTitolo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitolo.setBounds(10, 11, 573, 31);
		add(lblTitolo);
		
		JScrollPane scrollPaneCartelleUtente = new JScrollPane();
		scrollPaneCartelleUtente.setBounds(10, 40, 133, 425);
		add(scrollPaneCartelleUtente);
		
		JLabel lblCartelle = new JLabel("Cartelle Utente",SwingConstants.CENTER);
		lblCartelle.setForeground(new Color(0, 255, 0));
		lblCartelle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPaneCartelleUtente.setColumnHeaderView(lblCartelle);
		
		panelCartelleCanzoni = new JPanel();
		panelCartelleCanzoni.setForeground(new Color(0, 255, 0));
		panelCartelleCanzoni.setLayout((LayoutManager) new BoxLayout(panelCartelleCanzoni, BoxLayout.Y_AXIS));
		scrollPaneCartelleUtente.setViewportView(panelCartelleCanzoni);
		
		
		JButton btnLogout = new RoundedButton("Logout",15);
		btnLogout.setForeground(new Color(0, 255, 0));
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogout.setBounds(593, 11, 97, 31);
		btnLogout.addActionListener(e -> onBtnLogout());
		add(btnLogout);
		
		JButton btnCaricaMp3 = new RoundedButton("Carica Mp3",15);
		btnCaricaMp3.setForeground(new Color(0, 255, 0));
		btnCaricaMp3.setBounds(10, 476, 133, 31);
		btnCaricaMp3.addActionListener(e -> onBtnCaricaMp3());
		add(btnCaricaMp3);
		
		btnPlay = new RoundedButton("Play",15);
		btnPlay.setForeground(new Color(0, 255, 0));
        btnPlay.setBounds(440, 478, 71, 27);

        btnPlay.addActionListener(e -> onBtnPlay() );
        add(btnPlay);
        
        btnPausa = new RoundedButton("Pausa",15);
        btnPausa.setForeground(new Color(0, 255, 0));
        btnPausa.setBounds(523, 478, 71, 27);
        btnPausa.addActionListener(e -> onBtnPausa() );
        add(btnPausa);
        
        btnAvanti = new RoundedButton(">>",15);
        btnAvanti.setForeground(new Color(0, 255, 0));
        btnAvanti.setBounds(606, 478, 71, 27);
        btnAvanti.addActionListener(e -> onBtnAvanti() );
        add(btnAvanti);
        
        btnIndietro = new RoundedButton("<<",15);
        btnIndietro.setForeground(new Color(0, 255, 0));
        btnIndietro.setBounds(357, 478, 71, 27);
        btnIndietro.addActionListener(e -> onBtnIndietro() );
        add(btnIndietro);
        
        lbltit = new JLabel("",SwingConstants.CENTER);
        lbltit.setBounds(359, 448, 318, 17);
        add(lbltit);
        setOpaque(false);
        scrollPaneCartelleUtente.setOpaque(false);
        scrollPaneCartelleUtente.getViewport().setOpaque(false);
        lblTitolo.setOpaque(false);
        lblCartelle.setOpaque(false);
        btnLogout.setOpaque(false);
        btnCaricaMp3.setOpaque(false);
        btnPlay.setOpaque(false);
        btnPausa.setOpaque(false);
        btnAvanti.setOpaque(false);
        btnIndietro.setOpaque(false);
        lbltit.setOpaque(false);
		new ThUpdate(this).start();//deve restare in fondo
	}
	//update
	public void addCartelleUtenteButton() {			//genera i bottoni delle cartelle e delle canzoni
		panelCartelleCanzoni.removeAll();
		for(String s:controller.getElencoCartelleUtente()) {
			JButton btnNewButton = new RoundedButton(s,15);
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
			JButton btnNewButton = new RoundedButton(controller.togliTXTtoCanzone(s),15);
			panelCanzoni.add(btnNewButton);
			btnNewButton.addActionListener(e -> controller.AccodaCanzone(s));	// fa partire il thread per l'usica audio - da finire
		}
		
		panelCanzoni.revalidate();
		panelCanzoni.repaint();
	}
	
	private void setLblTit() {
		lbltit.setText(controller.getStatoCanzone());
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
		controller.AddFileMp3(null);
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
		setLblTit();
	}
	

	
}