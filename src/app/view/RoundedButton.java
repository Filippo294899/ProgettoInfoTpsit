package app.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
	private static final long serialVersionUID = 1L;
	private int borderRadius;

	public RoundedButton(String text, int borderRadius) {
		super(text);
		this.borderRadius = borderRadius;
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorderPainted(false);
		setForeground(new Color(0, 255, 0));
		setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		setBackground(new Color(41, 41, 41));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Colore  sfondo
		if (getModel().isArmed()) {
			g2.setColor(getBackground().darker());
		} else {
			g2.setColor(getBackground());
		}
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);

		super.paintComponent(g);
		g2.dispose();
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(0, 255, 0));
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
		g2.dispose();
	}

	@Override
	public void updateUI() {
		setUI(new BasicButtonUI());
	}
}
