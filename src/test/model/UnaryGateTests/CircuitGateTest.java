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
    public void testChangeInputConnection1(){
        CircuitVariable testInput2 = new CircuitVariable();
        testCircuitGate.changeInputConnection1(testInput2);
        assertEquals(testInput2, testCircuitGate.getInputConnection1());
    }

    public void testGateLogicCalcTrueInput() {
        testInput1.setOutputSignal(true);
    }

    public void testGateLogicCalcFalseInput() {
        testInput1.setOutputSignal(false);
    }

}


