package ui.gui.submenus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClosableMenuItem extends JPanel {
    JLayeredPane contentFrame;
    JButton closeButton;
    JPanel container;

    //EFFECTS: renders a closable menu with a close button
    public ClosableMenuItem() {
        setBackground(Color.LIGHT_GRAY);
        contentFrame = new JLayeredPane();
        add(contentFrame);
        loadCloseButton();

        container = new JPanel();
        container.setBounds(80, 0, 1760, 1080);
        add(container);
    }

    //MODIFIES: this
    //EFFECTS: loads close button
    public void loadCloseButton() {
        closeButton = new JButton("Close");
        setLayout(null);
        closeButton.setBounds(0,0, 80,40);
        add(closeButton);
        closeButton.addActionListener(e -> closeThis());
    }

    //MODIFIES: this
    //EFFECTS: closes this submenu
    private void closeThis() {
        Container parent = getParent();
        parent.remove(this);

        parent.revalidate();
        parent.repaint();

    }
}
