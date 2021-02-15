package model.UnaryGateTests;

import model.CircuitVariable;
import model.gates.CircuitGate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public abstract class CircuitGateTest {
    CircuitGate testCircuitGate;
    CircuitVariable testInput1;

    @BeforeEach
    public void setupTests(){
        testInput1 = new CircuitVariable();

    }

    @Test
    //changes a unary circuit's only input connection
    public void testChangeInputConnection1(){
        CircuitVariable testInput2 = new CircuitVariable();
        testCircuitGate.changeInputConnection1(testInput2);
        assertEquals(testInput2, testCircuitGate.getInputConnection1());
    }

    //sets the test circuit variable's output to true
    public void testGateLogicCalcTrueInput() {
        testInput1.setOutputSignal(true);
    }

    //sets the circuit variable's output to false
    public void testGateLogicCalcFalseInput() {
        testInput1.setOutputSignal(false);
    }

}


