package ui.gui.circuitgui;

import model.LogicalCircuit;
import model.LogicalExpression;
import ui.utility.KeyboardInputListener;
import ui.utility.SelectedCircPartListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//represents the area that allows the user to interact with a visual representation of the circuit
public class InteractableCircuitArea extends JPanel {
    LogicalCircuit localCircuit;
    LogicalExpression localExpression;
    CircuitComponentGUI currentlySelected = null;
    SelectedCircPartListener listener;
    KeyboardInputListener kbListener;
    ArrayList<CircuitComponentGUI> guiComponents;

    //EFFECTS: creates a new area that allows the user to interact with the circuit (a visual representation of it)
    public InteractableCircuitArea() {
        localCircuit = new LogicalCircuit();
        localExpression = new LogicalExpression();
        guiComponents = new ArrayList<>();
        listener = new SelectedCircPartListener(this);
        kbListener = new KeyboardInputListener(this);

        setLayout(null);
        setBackground(new Color(216, 235, 255));
        setBounds(400, 40, 1520, 1080);
    }

    //MODIFIES: this
    //EFFECTS: tries to add the CircuitComponentGUI's circuit part to the local Circuit, if successful,
    //adds the CircuitComponentGUI's UI component to the render queue, and the CircuitComponentGUI to currentlySelected
    //creates it in the middle of the view
    public void addNewGuiCircComp(CircuitComponentGUI toAdd) {
        if (localCircuit.addCircuitPart(toAdd.getAttachedCircComponent())) {
            toAdd.getAttachedUIElement().setBounds(960, 540, 150, 150);
            add(toAdd.getAttachedUIElement());
            toAdd.getAttachedUIElement().revalidate();
            toAdd.getAttachedUIElement().repaint();
            toAdd.getAttachedUIElement().addMouseListener(listener);
            guiComponents.add(toAdd);
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

}
