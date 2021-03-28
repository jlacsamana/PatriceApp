package ui.gui.circuitgui;

import model.CircuitComponent;
import model.CircuitOutput;
import model.CircuitVariable;
import model.gates.AndGate;
import model.gates.NotGate;
import model.gates.OrGate;

import javax.swing.*;
import java.awt.*;

// a representation of a part in a circuit with a GUI component
public class CircuitComponentGUI {
    protected CircuitComponent attachedCircComponent;
    protected JPanel attachedUIElement;
    private final ImageIcon var = new ImageIcon("./data/UI Assets/test.png");
    private final ImageIcon not = new ImageIcon("./data/UI Assets/test.png");
    private final ImageIcon and = new ImageIcon("./data/UI Assets/test.PNG");
    private final ImageIcon or = new ImageIcon("./data/UI Assets/test.png");
    private final ImageIcon output = new ImageIcon("./data/UI Assets/test.png");

    //EFFECTS: creates a new Circuit component with an attached GUI element, the type of component is dependent
    // on the given type
    public CircuitComponentGUI(CircuitComponent.ComponentTypeIdentifier circuitType) {
        attachedUIElement = new JPanel();
        if (circuitType == CircuitComponent.ComponentTypeIdentifier.VARIABLE) {
            attachedCircComponent = new CircuitVariable();
            attachedUIElement.add(new JLabel(var));
        } else if (circuitType == CircuitComponent.ComponentTypeIdentifier.NOT) {
            attachedCircComponent = new NotGate();
            attachedUIElement.add(new JLabel(not));
        } else if (circuitType == CircuitComponent.ComponentTypeIdentifier.AND) {
            attachedCircComponent = new AndGate();
            attachedUIElement.add(new JLabel(and));
        } else if (circuitType == CircuitComponent.ComponentTypeIdentifier.OR) {
            attachedCircComponent = new OrGate();
            attachedUIElement.add(new JLabel(or));
        } else if (circuitType == CircuitComponent.ComponentTypeIdentifier.OUTPUT) {
            attachedCircComponent = new CircuitOutput();
            attachedUIElement.add(new JButton(output));
        }

    }

    //MODIFIES: this, CircuitComponentGUIs
    //EFFECTS: removes this circuitComponentGUI from CircuitComponentGUIs and unrenders it, then removes its associated
    //circuit part from local circuit
    public void deleteCircPart(){

    }

    //MODIFIES: this
    //EFFECTS: moves this object around the display area
    public void moveGuiPart() {

    }


    //EFFECTS: returns the attached UI element
    public JPanel getAttachedUIElement() {
        return attachedUIElement;
    }

    //EFFECTS: returns the attached circuit component
    public CircuitComponent getAttachedCircComponent() {
        return attachedCircComponent;
    }




}
