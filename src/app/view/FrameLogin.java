package app.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import app.controller.Controller;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;

public class FrameLogin extends JFrame {

	private Controller controller;	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordMainPassword;
	private JTextField textEmail;
	private JButton btnInviaDati;
	private Boolean btnNascondiPasswordPremuto;
	private JButton btnNascondiPassword;
	private JTextField textNomeUtente;
	private JLabel lbTitoloPaginaLogin;
	private JButton btnRegistrati;
	private JLabel lblRegistrazione;
	private boolean registrazione;

	public FrameLogin(Controller c) {
		controller=c;
		registrazione=false;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbEmailUtente = new JLabel("E-mail");
		lbEmailUtente.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbEmailUtente.setBounds(30, 150, 128, 13);
		contentPane.add(lbEmailUtente);
		
		JLabel lbPassword = new JLabel("Password");
		lbPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbPassword.setBounds(30, 260, 128, 13);
		contentPane.add(lbPassword);
		
		passwordMainPassword = new JPasswordField();
		passwordMainPassword.setBounds(157, 259, 162, 19);
		contentPane.add(passwordMainPassword);
		
		textEmail = new JTextField();
		textEmail.setBounds(157, 149, 162, 19);
		contentPane.add(textEmail);
		textEmail.setColumns(10);
		
		btnNascondiPassword = new JButton("");
		btnNascondiPasswordPremuto=false;
		btnNascondiPassword.addActionListener((ActionEvent e)-> onAddActionListener(e));
		btnNascondiPassword.setIcon(new ImageIcon("C:\\Users\\Matteo\\eclipse-workspace\\Music\\media\\buttonImage\\occhioAperto.png"));
		btnNascondiPassword.setBounds(335, 254, 21, 19);
		contentPane.add(btnNascondiPassword);
		
		lbTitoloPaginaLogin = new JLabel("Login",SwingConstants.CENTER);
		lbTitoloPaginaLogin.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbTitoloPaginaLogin.setBounds(10, 43, 346, 54);
		contentPane.add(lbTitoloPaginaLogin);
		
		btnInviaDati = new JButton("Accedi");
		btnInviaDati.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnInviaDati.addActionListener((ActionEvent e)-> onBtnInviaDati(e));
		btnInviaDati.setBounds(145, 323, 109, 21);
		contentPane.add(btnInviaDati);
		
		JLabel lblNomeUtente = new JLabel("Nome Utente");
		lblNomeUtente.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNomeUtente.setBounds(30, 205, 117, 13);
		contentPane.add(lblNomeUtente);
		
		textNomeUtente = new JTextField();
		textNomeUtente.setColumns(10);
		textNomeUtente.setBounds(157, 202, 162, 19);
		contentPane.add(textNomeUtente);
		
		btnRegistrati = new JButton("Registrati");
		btnRegistrati.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRegistrati.setBounds(145, 412, 109, 23);
		btnRegistrati.addActionListener(e -> onBtnRegistrati(e) );
		contentPane.add(btnRegistrati);
		
		lblRegistrazione = new JLabel("Non hai un profilo ?");
		lblRegistrazione.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRegistrazione.setBounds(138, 387, 134, 14);
		contentPane.add(lblRegistrazione);
		
		
		if(controller.coockieLogin())
			changeInPanelloMain();
	}
	
	private void onAddActionListener(ActionEvent e){
		if(!btnNascondiPasswordPremuto){
			passwordMainPassword.setEchoChar((char) 0);
			btnNascondiPassword.setIcon(new ImageIcon("C:\\Users\\Matteo\\eclipse-workspace\\Music\\media\\buttonImage\\occhioChiuso.png"));
		}else {
			passwordMainPassword.setEchoChar('●');
			btnNascondiPassword.setIcon(new ImageIcon("C:\\Users\\Matteo\\eclipse-workspace\\Music\\media\\buttonImage\\occhioAperto.png"));
		}
		btnNascondiPasswordPremuto=!btnNascondiPasswordPremuto;
	}
	private void onBtnInviaDati(ActionEvent e){
		if(!registrazione) {
			if(!controller.login(textNomeUtente.getText(),textEmail.getText(),passwordMainPassword.getText()))
				AllertErroreCredenziali();
			else 
				changeInPanelloMain();
		}else {
			controller.RegistrazioneUtente(textNomeUtente.getText(),textEmail.getText(),passwordMainPassword.getText());
			registrazione=false;
			lbTitoloPaginaLogin.setText("Login");
			btnInviaDati.setText("Login");
			textEmail.setText("");
			textNomeUtente.setText("");
			passwordMainPassword.setText("");
			lblRegistrazione.setText("Registrati");
			btnRegistrati.setVisible(true);
		}
	}
	private void onBtnRegistrati(ActionEvent e){
		lbTitoloPaginaLogin.setText("Registrazione");
		btnInviaDati.setText("Registrati");
		textEmail.setText("");
		textNomeUtente.setText("");
		passwordMainPassword.setText("");
		lblRegistrazione.setText("");
		btnRegistrati.setVisible(false);
		registrazione=true;		
	}

	
	private void AllertErroreCredenziali() {
	    JOptionPane.showOptionDialog(
	            contentPane, 
	            getStringaAllertErroreCredenziali(), // Messaggio di errore
	            "Errore Login", // Titolo della finestra
	            JOptionPane.DEFAULT_OPTION, // Tipo di finestra
	            JOptionPane.ERROR_MESSAGE, // Icona dell'errore
	            null, // Non ci sono icone personalizzate
	            new Object[] { "OK" }, // Pulsante OK per continuare
	            "OK" // Default button
	        );
	}
	
	private String getStringaAllertErroreCredenziali() {
		if(!textEmail.getText().equals("")&&!textNomeUtente.getText().equals("")&&!passwordMainPassword.getText().equals(""))	
			return "Errore, credenziali errate.";
		return "Errore, hai uno o più campi vuoti.";
		
	}

	
	private void changeInPanelloMain() {
		setContentPane(new PannelMain(controller,this)); // Mostra il nuovo pannello
        revalidate();             // Aggiorna il layout
        repaint();
	}
	
	public JPasswordField getPasswordMainPassword() {
		return passwordMainPassword;
	}

	public JTextField getTextEmail() {
		return textEmail;
	}
	
	public JTextField getTextNomeUtente() {
		return textNomeUtente;
	}
	public JPanel getPannelLogin() {
		return contentPane;
	}
	public JPanel getActualyPanel() {
		return (JPanel) this.getContentPane();
		
	}
}


