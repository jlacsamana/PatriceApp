package model;

//represents a part of a logical circuit
public abstract class CircuitComponent {
    protected String componentName;
    protected ComponentTypeIdentifier componentIdentifier;
    protected boolean outputSignal;
    protected CircuitComponent outputConnection;

    //an enum representing all the types of circuit components
    public enum ComponentTypeIdentifier {
        VARIABLE,
        NOT,
        AND,
        OR,
        OUTPUT
    }

    //EFFECT: creates a new circuit component and sets its output connection to null,
    // and outPutSignal to false
    public CircuitComponent() {
        outputSignal = false;
        outputConnection = null;
    }

    //REQUIRES: that newConnection not be null
    //MODIFIES: this
    //EFFECT: changes outgoingConnection's reference to newConnection's
    public void changeOutputConnection(CircuitComponent newConnection) {
        outputConnection = newConnection;
    }

    //EFFECT: returns output signal
    public boolean getOutputSig() {
        return outputSignal;
    }

    //EFFECTS: returns output connection
    public CircuitComponent getOutputConnection() {
        return outputConnection;
    }

}
