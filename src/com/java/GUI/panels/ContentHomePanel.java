package com.java.GUI.panels;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class ContentHomePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public ContentHomePanel() {
		
		JLabel lblUtecLogo = new JLabel("New label");
		lblUtecLogo.setIcon(new ImageIcon(ContentHomePanel.class.getResource("/com/java/resources/images/06-isologotipo-para-fondo-blanco.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					//.addGap(43)
					.addComponent(lblUtecLogo, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					)//.addContainerGap(405, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					//.addGap(32)
					.addComponent(lblUtecLogo, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					)//.addContainerGap(496, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        
        Color bottomColor = new Color(135, 206, 250); // Sky Blue
        Color topColor = new Color(83, 190, 242); // Navy Blue
        
        GradientPaint gradient = new GradientPaint(
            0, 0, topColor,
            0, panelHeight, bottomColor
        );
        
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, panelWidth, panelHeight);
        
        g2d.dispose();
    }
}
