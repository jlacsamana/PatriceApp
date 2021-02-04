package model;

//represents a variable on a logical circuit, only has an output, whose value can be toggled by the user
public class CircuitVariable extends CircuitComponent {
    VariableIdentifier variableIdentity;

    //EFFECTS: creates a new variable and sets it's variableIdentity to an available variable, ComponentTypeIdentifier
    // to VARIABLE, and outgoing connection
    public CircuitVariable(){

    }

    //MODIFIES: this
    //EFFECT: toggles the value of outputSignal; sets to true if false and vice versa
    public void toggleOutputSignal(){

    }
}
