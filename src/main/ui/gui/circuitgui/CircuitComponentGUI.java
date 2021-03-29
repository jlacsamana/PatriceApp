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
    private final ImageIcon var = new ImageIcon("./data/UI Assets/VARIABLE.png");
    private final ImageIcon not = new ImageIcon("./data/UI Assets/NOT.png");
    private final ImageIcon and = new ImageIcon("./data/UI Assets/AND.PNG");
    private final ImageIcon or = new ImageIcon("./data/UI Assets/OR.png");
    private final ImageIcon output = new ImageIcon("./data/UI Assets/OUTPUT.png");
    protected InteractableCircuitArea parent;

    //EFFECTS: creates a new Circuit component with an attached GUI element, the type of component is dependent
    // on the given type
    public CircuitComponentGUI(CircuitComponent.ComponentTypeIdentifier circuitType,
                               InteractableCircuitArea parent) {
        this.parent = parent;
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
            //this is unreachable
        }
        attachedUIElement.setOpaque(false);
    }

    //EFFECTS: a special gui shell initializer for the head part of the Logical circuit specifically
    public CircuitComponentGUI(CircuitOutput output, InteractableCircuitArea parent) {
        this.parent = parent;
        attachedCircComponent = output;
        attachedUIElement = new JPanel();
        attachedUIElement.add(new JLabel(this.output));
        attachedUIElement.setOpaque(false);
    }

    //MODIFIES: this, CircuitComponentGUIs
    //EFFECTS: removes this circuitComponentGUI from CircuitComponentGUIs and unrenders it, then removes its associated
    //circuit part from local circuit. But does nothing if circuit part is an output
    public void deleteCircPart() {
        if (!(attachedCircComponent instanceof CircuitOutput)) {
            parent.getGuiComponents().remove(this);
            parent.setCurrentlySelected(null);
            parent.remove(this.attachedUIElement);
            parent.localCircuit.removeCircuitPart(this.attachedCircComponent);
            parent.disconnectFromAll(this);
            parent.validate();
            parent.repaint();
        }
    }

    //MODIFIES: this
    //EFFECTS: moves this object around the display area up by 10px
    public void moveGuiPartUp() {
        Point pos = attachedUIElement.getLocation();
        attachedUIElement.setLocation(pos.x, pos.y - 10);
        parent.revalidate();
        parent.repaint();
    }

    //MODIFIES: this
    //EFFECTS: moves this object around the display area down by 10px
    public void moveGuiPartDown() {
        Point pos = attachedUIElement.getLocation();
        attachedUIElement.setLocation(pos.x, pos.y + 10);
        parent.revalidate();
        parent.repaint();
    }

    //MODIFIES: this
    //EFFECTS: moves this object around the display area right by 10px
    public void moveGuiPartRight() {
        Point pos = attachedUIElement.getLocation();
        attachedUIElement.setLocation(pos.x + 10, pos.y);
        parent.revalidate();
        parent.repaint();
    }

    //MODIFIES: this
    //EFFECTS: moves this object around the display area left by 10px
    public void moveGuiPartLeft() {
        Point pos = attachedUIElement.getLocation();
        attachedUIElement.setLocation(pos.x - 10, pos.y);
        parent.revalidate();
        parent.repaint();
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
