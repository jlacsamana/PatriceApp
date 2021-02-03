package model;

//represents a Gate on a logical circuit; necessarily has an output and input connection
public abstract class CircuitGate extends CircuitComponent {
    CircuitComponent inputConnection1;

    //EFFECT: creates a new circuit gate and assigns a connection to the input connection
    public CircuitGate(){

    }

    //MODIFIES: this
    //EFFECT: sets inputConnection1's reference to newConnection's
    public void changeInputConnection(CircuitComponent newConnection){

    }

    //MODIFIES: this
    //EFFECTS: sets this' outputSignal based on the outputSignals it receives from its input signal(s)
    //and type of gate that implements this method
    public abstract void gateLogicCalc();

}
