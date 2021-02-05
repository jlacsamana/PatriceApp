package model.gates;

public class AndGate extends BinaryCircuitGate {

    //EFFECTS: creates a new AND gate and sets its component type identifier to AND
    public AndGate() {
        super();
        componentIdentifier = ComponentTypeIdentifier.AND;
    }

    @Override
    //REQUIRES: input connections must not be null
    //MODIFIES: this
    //EFFECTS: sets this' outputSignal to true if it's input signals' outputSignals are both also true
    public void gateLogicCalc() {

    }
}
