package model;

//represents a variable on a logical circuit, only has an output, whose value can be toggled by the user
public class CircuitVariable extends CircuitComponent {
    VariableIdentifier variableIdentity;

    //represents all available variable identifiers
    public enum VariableIdentifier {
        A,
        B,
        C,
        D
    }

    //EFFECTS: creates a new variable and sets it's variableIdentity to an available variable, ComponentTypeIdentifier
    // to VARIABLE, and sets outputSignal to false
    public CircuitVariable() {
        super();
        componentIdentifier = ComponentTypeIdentifier.VARIABLE;
    }

    //MODIFIES: this
    //EFFECT: sets the value of outputSignal to newOut
    public void setOutputSignal(boolean newOut){

    }
}
