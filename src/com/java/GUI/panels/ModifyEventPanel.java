package com.java.GUI.panels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyEventPanel extends JPanel {
    private JTextField eventTextField;
    private JButton modifyButton;
    private JButton cancelButton;

    public ModifyEventPanel() {
        JLabel eventLabel = new JLabel("Event:");
        eventTextField = new JTextField(20);

        modifyButton = new JButton("Modify");
        cancelButton = new JButton("Cancel");

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modifiedEvent = eventTextField.getText();
                // Perform the necessary actions to update the event
                updateEvent(modifiedEvent);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel the modification?", "Cancel Modification", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    // Return to the list of registered events without making any changes
                    returnToListOfEvents();
                }
            }
        });

        add(eventLabel);
        add(eventTextField);
        add(modifyButton);
        add(cancelButton);
    }

    private void updateEvent(String modifiedEvent) {
        // Logic to update the event with the modified data
        // ...
        // Display a confirmation message
        JOptionPane.showMessageDialog(null, "Event modified successfully!");
    }

    private void returnToListOfEvents() {
        // Logic to return to the list of registered events without making any changes
        // ...
    }
}
