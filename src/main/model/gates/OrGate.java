package model.gates;

//represents an OR gate on a logical circuit
public class OrGate extends BinaryCircuitGate {

    //EFFECTS: creates a new OR gate and sets its component type identifier to OR
    public OrGate() {
        super();
        componentIdentifier = ComponentTypeIdentifier.OR;
    }

    @Override
    //REQUIRES: input connections must not be null
    //MODIFIES: this
    //EFFECTS: sets this' outputSignal to true if at least one of it's input signals' outputSignals are true
    public void gateLogicCalc() {
        outputSignal = inputConnection1.getOutputSig() || inputConnection2.getOutputSig();
    }
}
