package ui.gui.submenus;

import ui.gui.PatriceGuiMainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//a generic submenu with a close button and layered UI
public abstract class ClosableMenuItem extends JPanel {
    JLayeredPane contentFrame;
    JButton closeButton;
    JPanel container;

    //EFFECTS: renders a closable menu with a close button
    public ClosableMenuItem() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        contentFrame = new JLayeredPane();
        add(contentFrame);
        loadCloseButton();

        container = new JPanel();
        container.setBounds(80, 0, 1920 - 160, 1080);
        add(container);
    }

    //MODIFIES: this
    //EFFECTS: loads close button
    public void loadCloseButton() {
        closeButton = new JButton("Close");
        closeButton.setBounds(0,0, 80,40);
        add(closeButton);
        closeButton.addActionListener(e -> closeThis());
    }

    //MODIFIES: this
    //EFFECTS: closes this submenu
    public void closeThis() {
        Container parent = getParent();
        parent.remove(this);
        ((PatriceGuiMainMenu) parent).closeDialogue();
        parent.revalidate();
        parent.repaint();

    }
}
