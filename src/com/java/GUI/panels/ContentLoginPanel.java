package com.java.GUI.panels;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class ContentLoginPanel extends JPanel {
	
	public ContentLoginPanel() {

    }
	
    @Override
    protected void paintComponent(Graphics g) {
        
    	super.paintComponent(g);
    	Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Paint gp = new GradientPaint(getWidth() / 2, 0, new Color(146, 233, 251), getWidth() / 2, getHeight(), new Color(12, 137, 163));
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2.dispose();
    
    }
}