package app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextField extends JTextField {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int borderRadius;
    private Shape shape;

    public RoundedTextField(int columns, int borderRadius) {
        super(columns);
        this.borderRadius = borderRadius;
        setOpaque(false);
        setForeground(new Color(41, 41, 41)); 
        setCaretColor(new Color(0, 255, 0)); 
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
    
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
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
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        }
        return shape.contains(x, y);
    }
}
