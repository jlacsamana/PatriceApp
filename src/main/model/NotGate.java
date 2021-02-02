package model;

//represents a NOT gate in a logical circuit; inverts the signal received from its input
public class NotGate extends CircuitComponent {
    CircuitComponent connectedInput;
    CircuitComponent connectedOutput;

    //EFFECTS: creates a new NOT Gate and sets its input and output connections to other Gates.
    // sets its componentIdentifier to NOT
    public NotGate(CircuitComponent in, CircuitComponent out) {
        this.componentIdentifier = CircuitCompType.NOT;
        this.connectedInput = in;
        this.connectedOutput = out;
    }


}
