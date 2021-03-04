package model.gates;

//represents an AND gate in a logical circuit
public class AndGate extends BinaryCircuitGate {

    //EFFECTS: creates a new AND gate and sets its component type identifier to AND
    public AndGate() {
        super();
        componentTypeIdentifier = ComponentTypeIdentifier.AND;
    }

    @Override
    //REQUIRES: input connections must not be null
    //MODIFIES: this
    //EFFECTS: sets this' outputSignal to true if it's input signals' outputSignals are both also true
    public void gateLogicCalc() {
        outputSignal = inputConnection1.getOutputSig() && inputConnection2.getOutputSig();
    }
}
