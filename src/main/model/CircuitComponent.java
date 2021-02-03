package model;

//represents a part of a logical circuit
public abstract class CircuitComponent {
    String componentName;
    ComponentTypeIdentifier componentIdentifier;
    boolean outputSignal;
    CircuitComponent outgoingConnection;

    //MODIFIES: this
    //EFFECT: changes outgoingConnection's reference to newConnection's
    public void changeOutputConnection(CircuitComponent newConnection) {

    }

}
