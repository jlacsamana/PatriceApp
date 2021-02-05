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
    public void testGateLogicCalcTrueInput(){
        super.testGateLogicCalcTrueInput();
        testCircuitOut.gateLogicCalc();
        assertTrue(testCircuitOut.getOutputSig());
    }

    @Test
    @Override
    public void testGateLogicCalcFalseInput(){
        super.testGateLogicCalcFalseInput();
        testCircuitOut.gateLogicCalc();
        assertFalse(testCircuitOut.getOutputSig());
    }




}
