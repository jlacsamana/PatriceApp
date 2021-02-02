package model;

//represents gates on logical circuits that have an arity of two(takes in two inputs)
public abstract class BinaryGate extends CircuitComponent {
    private CircuitComponent connectedInput1;
    private CircuitComponent connectedInput2;
    private CircuitComponent connectedOutput;

    public BinaryGate(CircuitComponent input1, CircuitComponent input2) {
        connectedInput1 = input1;
        connectedInput2 = input2;
    }

    //MODIFIES: this
    //EFFECTS: changes outputSignal according to the input received from connectedInput1
    // and connectedInput2's outputSignal. Behavior is consistent with Gate type
    // (i.e AND gates will to a true outputSignal if both inputs are also true, etc)
    public abstract void gateLogicCalc();
}
