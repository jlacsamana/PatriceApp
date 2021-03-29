package ui.utility;

import ui.gui.circuitgui.CircuitComponentGUI;
import ui.gui.circuitgui.InteractableCircuitArea;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//listens for mouseclicks and saves the most recently clicked CircuitComponentGUI
public class SelectedCircPartListener extends MouseAdapter {
    InteractableCircuitArea oberserver;

    //EFFECTS: creates a new listener and sets its oberserver pairing
    public SelectedCircPartListener(InteractableCircuitArea icircArea) {
        super();
        oberserver = icircArea;
    }

    @Override
    //MODIFIES: observer.currentlySelected
    //EFFECTS: sets currentlySelected to the most recently clicked CircuitComponentGUI, and performs behavior
    //according to which mouse button was clicked
    //if left click, selects new circuit gui if selected
    //if right, attempts to establish a connection between the two (you can't establish a connection between
    // a circuit component GUi and itself)
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (e.getButton() == MouseEvent.BUTTON1) {

            for (CircuitComponentGUI gui: oberserver.getGuiComponents()) {
                if (gui.getAttachedUIElement() == e.getSource()) {
                    oberserver.setCurrentlySelected(gui);
                }
            }
        } else if (e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) {
            if (oberserver.getCurrentlySelected() != null) {
                for (CircuitComponentGUI gui: oberserver.getGuiComponents()) {
                    if (gui.getAttachedUIElement() == e.getSource()
                            && e.getSource() != oberserver.getCurrentlySelected().getAttachedUIElement()) {
                        if (e.getButton() == MouseEvent.BUTTON2) {
                            oberserver.establishConnection(oberserver.getCurrentlySelected(), gui, 1);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            oberserver.establishConnection(oberserver.getCurrentlySelected(), gui, 2);
                        }
                    }
                }
            }
        }


    }
}
