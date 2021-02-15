package model.UnaryGateTests;

import model.CircuitIO.CircuitComponentTest;
import model.CircuitOutput;
import model.UnaryGateTests.CircuitGateTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CircuitOutputTest extends CircuitGateTest {
    CircuitOutput testCircuitOut;

    @Override
    @BeforeEach
    public void setupTests(){
        super.setupTests();
        testCircuitGate = new CircuitOutput();
        testCircuitGate.changeInputConnection1(testInput1);
        testCircuitOut = (CircuitOutput) testCircuitGate;
    }

    @Test
    @Override
    //a circuit output is receiving a true input signal and outputting it
    public void testGateLogicCalcTrueInput(){
        super.testGateLogicCalcTrueInput();
        testCircuitOut.gateLogicCalc();
        assertTrue(testCircuitOut.getOutputSig());
    }

    @Test
    @Override
    //a circuit output is receiving a false input signal and outputting it
    public void testGateLogicCalcFalseInput(){
        super.testGateLogicCalcFalseInput();
        testCircuitOut.gateLogicCalc();
        assertFalse(testCircuitOut.getOutputSig());
    }




}
