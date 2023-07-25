package com.java.GUI.panels;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.java.GUI.Main;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ContentHomePanel extends JPanel {

	private JLabel lblUtecLogo;
	private JButton btnLogout;
	private GroupLayout groupLayout;

	/**
	 * Create the panel.
	 */
	public ContentHomePanel() {
		
		lblUtecLogo = new JLabel("New label");
		lblUtecLogo.setIcon(new ImageIcon(ContentHomePanel.class.getResource("/com/java/resources/images/06-isologotipo-para-fondo-blanco.png")));
		btnLogout = new JButton("");
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Main main = (Main) SwingUtilities.getWindowAncestor(ContentHomePanel.this);
		        main.initLogin();
				main.revalidate();
			}
		});
		btnLogout.setIcon(new ImageIcon(ContentHomePanel.class.getResource("/com/java/resources/images/cerrar-sesion.png")));
		btnLogout.setToolTipText("Cerrar sesión");
		btnLogout.setContentAreaFilled(false);
		btnLogout.setBorder(null);
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			    groupLayout.createParallelGroup()
			        .addGroup(groupLayout.createSequentialGroup()
			            .addComponent(lblUtecLogo, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
			            .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			            .addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			        )
			);
			groupLayout.setVerticalGroup(
			    groupLayout.createParallelGroup(Alignment.LEADING)
			        .addGroup(groupLayout.createSequentialGroup()
			            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
			                .addComponent(lblUtecLogo, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
			                .addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
			            )
			        )
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

	public JButton getBtnLogout() {
		return btnLogout;
	}

	public void setBtnLogout(JButton btn) {
		this.groupLayout.replace(btnLogout, btn);
		this.btnLogout = btn;
		this.btnLogout.revalidate();
		this.btnLogout.repaint();
		revalidate();
		repaint();
	}
}
