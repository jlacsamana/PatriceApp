package ui.gui.circuitgui;

import model.LogicalCircuit;
import model.LogicalExpression;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//represents the area that allows the user to interact with a visual representation of the circuit
public class InteractableCircuitArea extends JPanel {
    LogicalCircuit localCircuit;
    LogicalExpression localExpression;
    CircuitComponentGUI currentlySelected = null;


    //EFFECTS: creates a new area that allows the user to interact with the circuit (a visual representation of it)
    public InteractableCircuitArea() {
        localCircuit = new LogicalCircuit();
        localExpression = new LogicalExpression();

        setLayout(null);
        setBackground(new Color(216, 235, 255));
        setBounds(400, 0, 1520, 1080);
    }

    //MODIFIES: this
    //EFFECTS: tries to add the CircuitComponentGUI's circuit part to the local Circuit, if successful,
    //adds the CircuitComponentGUI's UI component to the render queue
    //creates it in the middle of the view
    public void addNewGuiCircComp(CircuitComponentGUI toAdd) {
        if (localCircuit.addCircuitPart(toAdd.getAttachedCircComponent())) {
            toAdd.getAttachedUIElement().setBounds(960, 540, 340, 340);
            add(toAdd.getAttachedUIElement());
            toAdd.getAttachedUIElement().revalidate();
            toAdd.getAttachedUIElement().repaint();
        }
    }




}
