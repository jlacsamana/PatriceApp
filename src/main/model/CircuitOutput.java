package model;

import model.gates.CircuitGate;

//represents an output in a logical circuit
public class CircuitOutput extends CircuitGate {

    //EFFECTS: creates a new output and sets it's input connection and ComponentTypeIdentifier to OUTPUT
    public CircuitOutput() {
        super();
        componentTypeIdentifier = ComponentTypeIdentifier.OUTPUT;
    }

    @Override
    //REQUIRES: input connection must not be null
    //MODIFIES: this
    //EFFECTS: sets this' output connection to match the output connection of its input
    public void gateLogicCalc() {
        outputSignal = inputConnection1.getOutputSig();
    }

}
