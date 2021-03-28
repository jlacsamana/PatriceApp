package ui.utility;

import javafx.scene.input.KeyCode;
import ui.gui.circuitgui.InteractableCircuitArea;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//handles keyboard input
public class KeyboardInputListener extends KeyAdapter {
    InteractableCircuitArea interactableCircuitArea;

    //EFFECTS: creates a new key listener and sets its InteractableCircuitArea
    public KeyboardInputListener(InteractableCircuitArea interactableCircuitArea) {
        this.interactableCircuitArea = interactableCircuitArea;
    }

    @Override
    //checks if relevant input keys are pressed and does the appropriate action
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (interactableCircuitArea.getCurrentlySelected() != null) {
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

            }
        }

    }
}
