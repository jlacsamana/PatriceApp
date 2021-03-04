package model;

//represents a variable on a logical circuit, only has an output, whose value can be toggled by the user
public class CircuitVariable extends CircuitComponent {
    LogicalCircuit.VariableIdentifier variableIdentity;

    //EFFECTS: creates a new variable and sets it's variableIdentity to NONE, and ComponentTypeIdentifier
    // to VARIABLE
    public CircuitVariable() {
        super();
        componentTypeIdentifier = ComponentTypeIdentifier.VARIABLE;
        variableIdentity = LogicalCircuit.VariableIdentifier.NONE;
    }

    //MODIFIES: this
    //EFFECT: sets the value of outputSignal to newOut
    public void setOutputSignal(boolean newOut) {
        outputSignal = newOut;
    }

    //EFFECT: returns this' variable ID
    public LogicalCircuit.VariableIdentifier getVarID() {
        return variableIdentity;
    }

    //MODIFIES: this
    //EFFECTS: sets this' variable ID to newVarID
    public void setVarID(LogicalCircuit.VariableIdentifier newVarID) {
        variableIdentity = newVarID;
    }

}
