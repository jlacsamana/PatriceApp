package ui.gui.popupdialogues;

import ui.gui.PatriceGuiMainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

//a generic popup dialogue
public abstract class Dialogue extends JFrame {
    protected JPanel container;
    protected Dimension viewPort;
    protected PatriceGuiMainMenu parentMenu;

    //EFFECTS: creates a propup dialogue prompting the user to enter a new name for a workspace
    public Dialogue(PatriceGuiMainMenu parent) {
        viewPort = Toolkit.getDefaultToolkit().getScreenSize();
        parentMenu = parent;
        container = new JPanel();

        setUndecorated(true);

        pack();
        centrePanel();

        add(container);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: centres the viewport on the screen
    // borrowed from:
    // https://github.students.cs.ubc.ca/CPSC210-2020W-T2/lab6_w9v2b.git
    // how to close a Jframe from:
    // https://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
    public void centrePanel() {
        setLocation(760, 440);
    }

    protected void closeDialogue() {
        parentMenu.closeDialogue();
        parentMenu.updateGUI();
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
