package model.UnaryGateTests;

import model.gates.NotGate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class NotGateTest extends CircuitGateTest{
    NotGate testNotGate;
    @Override
    @BeforeEach
    public void setupTests(){
        super.setupTests();
        testCircuitGate = new NotGate();
        testCircuitGate.changeInputConnection1(testInput1);
        testNotGate = (NotGate) testCircuitGate;
    }


    @Test
    @Override
    //a NOT gate receives a true input signal and outputs a false one
    public void testGateLogicCalcTrueInput(){
        super.testGateLogicCalcTrueInput();
        testNotGate.gateLogicCalc();
        assertFalse(testNotGate.getOutputSig());
    }

    @Test
    @Override
    //a NOT gate receives a false input signal and outputs a true one
    public void testGateLogicCalcFalseInput(){
        super.testGateLogicCalcFalseInput();
        testNotGate.gateLogicCalc();
        assertTrue(testNotGate.getOutputSig());
    }

}
