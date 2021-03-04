package model.gates;

//represents a NOT gate in a logical circuit
public class NotGate extends CircuitGate {

    //EFFECTS: creates a new NOT gate and sets its component type identifier to NOT
    public NotGate() {
        super();
        componentTypeIdentifier = ComponentTypeIdentifier.NOT;
    }

    @Override
    //REQUIRES: input connection must not be null
    //MODIFIES: this
    //EFFECTS: sets this' outputSignal to true if it's input signal's outputSignal is false and vice versa
    public void gateLogicCalc() {
        outputSignal = !(inputConnection1.getOutputSig());
    }
}
