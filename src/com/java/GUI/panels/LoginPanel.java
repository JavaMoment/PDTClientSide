package com.java.GUI.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

public class LoginPanel extends JPanel {
	
	public LoginPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add login form components
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(passwordLabel, gbc);

        JTextField passwordField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(passwordField, gbc);

        // Add login button
        JButton loginButton = new JButton("Login");
        loginButton.setForeground(Color.BLACK);
        loginButton.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(loginButton, gbc);

        // Add register button
        JButton registerButton = new JButton("Register");
        registerButton.setForeground(new Color(135, 206, 235)); // Sky blue
        registerButton.setOpaque(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(registerButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }
	
    @Override
    protected void paintComponent(Graphics g) {
        /*super.paintComponent(g);
        
        // Cast Graphics object to Graphics2D for advanced rendering
        Graphics2D g2d = (Graphics2D) g;
        
        // Define the gradient colors
        Color startColor = new Color(135, 206, 235); // Sky blue
        Color endColor = new Color(0, 0, 128); // Navy blue
        
        // Create the gradient paint object
        GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
        
        // Apply the gradient paint to the panel background
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);*/
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Paint gp = new GradientPaint(getWidth() / 2, 0, Color.WHITE, getWidth() / 2, getHeight(), Color.WHITE);
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(getForeground());
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2.dispose();
        super.paintComponent(g);
    }
}