package app.view;

import javax.swing.JFrame;
import javax.swing.JPanel;


import java.awt.BorderLayout;
import app.controller.Controller;
import javax.swing.JLabel;
import java.awt.Font;


public class PannelMain extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JFrame frame;
	
	
	public PannelMain( Controller c , JFrame frame) {
		
		controller=c;
		this.frame=frame;
		setBounds(100, 100, 380, 500);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bentornato "+controller.getElementoFileLogin(t->t.equals("nome")));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(103, 27, 267, 31);
		add(lblNewLabel);
		controller.setCartellaPrincipale(controller.getElementoFileLogin(t-> t.equals("nome")));

	}
}
