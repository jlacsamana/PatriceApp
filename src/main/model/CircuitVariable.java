package model;

//represents a variable in a logical circuit; has no inputs, only an output that can be toggled
public class CircuitVariable extends CircuitComponent {
    private final VariableIdentifier variableID;

    //REQUIRES: that no VariableIdentifier already in use be assigned to a new object
    //EFFECTS: creates a new circuit variable that can be identified with varID, outputSignal is set to false
    public CircuitVariable(VariableIdentifier varID) {
        componentIdentifier = CircuitCompType.VARIABLE;
        variableID = varID;
        outputSignal = false;
    }

    //MODIFIES: this
    //EFFECTS: sets this' outputSignal
    public void setOutSignal(boolean newSignal) {
        this.outputSignal = newSignal;
    }

    //EFFECTS: returns this' variableID
    public VariableIdentifier getVariableID() {
        return variableID;
    }


}
