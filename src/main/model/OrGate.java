package model;

//represents a AND gate in a logical circuit; outputs a true signal if at least one of the input signals are true
public class OrGate extends BinaryGate {

    public OrGate(CircuitComponent input1, CircuitComponent input2) {
        super(input1, input2);
        this.componentIdentifier = CircuitCompType.OR;
    }

    //MODIFIES: THIS
    //switches outputSignal to true if at least one of the connected inputs are true, false otherwise
    @Override
    public void gateLogicCalc() {

    }
}
