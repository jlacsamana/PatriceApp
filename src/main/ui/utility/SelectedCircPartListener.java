package ui.utility;

import ui.gui.circuitgui.CircuitComponentGUI;
import ui.gui.circuitgui.InteractableCircuitArea;

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
    //EFFECTS: sets currentlySelected to the most recently clicked CircuitComponentGUI
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (e.getButton() == MouseEvent.BUTTON1) {
            oberserver.setCurrentlySelected((CircuitComponentGUI) e.getSource());
        } else if (e.getButton() == MouseEvent.BUTTON3) {

        }


    }
}
