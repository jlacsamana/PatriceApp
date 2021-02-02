package model;

//represents a AND gate in a logical circuit; outputs a true signal if both input signals are also true
public class AndGate extends BinaryGate {

    public AndGate(CircuitComponent input1, CircuitComponent input2) {
        super(input1, input2);
        this.componentIdentifier = CircuitCompType.AND;
    }

    //MODIFIES: THIS
    //switches outputSignal to true if both connected inputs are true, false otherwise
    @Override
    public void gateLogicCalc() {

    }
}
