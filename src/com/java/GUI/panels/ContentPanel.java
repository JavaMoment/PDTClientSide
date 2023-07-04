package com.java.GUI.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ContentPanel extends JPanel {

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        
        Color skyBlue = new Color(135, 206, 235, 250);
        
        g2d.setPaint(skyBlue);
        g2d.fillRect(0, 0, panelWidth, panelHeight);
        
        g2d.dispose();
    }
	
}
