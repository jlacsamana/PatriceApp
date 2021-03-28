package ui.gui.circuitgui;

import model.LogicalCircuit;
import model.LogicalExpression;
import ui.utility.SelectedCircPartListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

//represents the area that allows the user to interact with a visual representation of the circuit
public class InteractableCircuitArea extends JPanel {
    LogicalCircuit localCircuit;
    LogicalExpression localExpression;
    CircuitComponentGUI currentlySelected = null;
    SelectedCircPartListener listener;
    ArrayList<CircuitComponentGUI> guiComponents;

    //EFFECTS: creates a new area that allows the user to interact with the circuit (a visual representation of it)
    public InteractableCircuitArea() {
        localCircuit = new LogicalCircuit();
        localExpression = new LogicalExpression();
        guiComponents = new ArrayList<>();
        listener = new SelectedCircPartListener(this);
        setLayout(null);
        setBackground(new Color(216, 235, 255));
        setBounds(400, 40, 1520, 1080);
        initKeyListener();

    }

    //MODIFIES: this
    //EFFECTS: tries to add the CircuitComponentGUI's circuit part to the local Circuit, if successful,
    //adds the CircuitComponentGUI's UI component to the render queue, and the CircuitComponentGUI to currentlySelected
    //creates it in the middle of the view
    public void addNewGuiCircComp(CircuitComponentGUI toAdd) {
        if (localCircuit.addCircuitPart(toAdd.getAttachedCircComponent())) {
            toAdd.getAttachedUIElement().setBounds(960, 540, 150, 150);
            add(toAdd.getAttachedUIElement());
            toAdd.getAttachedUIElement().addMouseListener(listener);
            guiComponents.add(toAdd);
            toAdd.getAttachedUIElement().revalidate();
            toAdd.getAttachedUIElement().repaint();
        }
    }


    //EFFECTS: returns this circuit area's list of circuit parts
    public ArrayList<CircuitComponentGUI> getGuiComponents() {
        return guiComponents;
    }

    //EFFECTS: returns this circuit area's currently selected CircuitComponentGUI
    public CircuitComponentGUI getCurrentlySelected() {
        return currentlySelected;
    }

    //EFFECTS: sets the current selected circuitComponentGUI to currentlySelected
    public void setCurrentlySelected(CircuitComponentGUI currentlySelected) {
        this.currentlySelected = currentlySelected;
    }

    //EFFECTS: initialises keyboard listener
    //derived from:
    //https://stackoverflow.com/questions/26208179/keylistener-not-applying-to-jpanel-yes-it-is-focused
    private void initKeyListener() {
        InputMap keyBinds = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        keyBinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "deselect");
        keyBinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, false), "delete");
        keyBinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "moveUp");
        keyBinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "moveRight");
        keyBinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "moveDown");
        keyBinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "moveLeft");

        ActionMap keyActions = getActionMap();
        keyActions.put("deselect", new Deselect());
        keyActions.put("delete", new Delete());
        keyActions.put("moveUp", new MoveUp());
        keyActions.put("moveRight", new MoveRight());
        keyActions.put("moveDown", new MoveDown());
        keyActions.put("moveLeft", new MoveLeft());
    }

    //corresponds to deselect action
    private class Deselect extends AbstractAction {

        @Override
        //EFFECTS: deselects currently selected Circuit GUI
        public void actionPerformed(ActionEvent e) {
            currentlySelected = null;
        }
    }

    //corresponds to delete action
    private class Delete extends AbstractAction {

        @Override
        //EFFECTS: deletes currently selected Circuit GUI
        public void actionPerformed(ActionEvent e) {
            if (currentlySelected != null) {
                currentlySelected.deleteCircPart();
            }
        }
    }

    //corresponds to move up action
    private class MoveUp extends AbstractAction {

        @Override
        //EFFECTS: moves the currently selected Circuit GUI up
        public void actionPerformed(ActionEvent e) {
            if (currentlySelected != null) {
                currentlySelected.moveGuiPartUp();
            }
        }
    }

    //corresponds to move right action
    private class MoveRight extends AbstractAction {

        @Override
        //EFFECTS: moves the currently selected Circuit GUI right
        public void actionPerformed(ActionEvent e) {
            if (currentlySelected != null) {
                currentlySelected.moveGuiPartRight();
            }
        }
    }

    //corresponds to move down action
    private class MoveDown extends AbstractAction {

        @Override
        //EFFECTS: moves the currently selected Circuit GUI down
        public void actionPerformed(ActionEvent e) {
            if (currentlySelected != null) {
                currentlySelected.moveGuiPartDown();
            }
        }
    }

    //corresponds to move left action
    private class MoveLeft extends AbstractAction {

        @Override
        //EFFECTS: moves the currently selected Circuit GUI left
        public void actionPerformed(ActionEvent e) {
            if (currentlySelected != null) {
                currentlySelected.moveGuiPartLeft();
            }
        }
    }

}
