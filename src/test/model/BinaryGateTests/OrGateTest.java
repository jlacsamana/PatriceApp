package model.BinaryGateTests;
import model.gates.OrGate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrGateTest extends BinaryGateTest{
    OrGate testOrGate;
    @Override
    @BeforeEach
    public void setupTests(){
        super.setupTests();
        testBinaryGate = new OrGate();
        testBinaryGate.changeInputConnection1(testInput1);
        testBinaryGate.changeInputConnection2(testInput2);
        testOrGate = (OrGate) testBinaryGate;
    }

    @Test
    @Override
    public void testGateLogicCalcTrueInputs(){
        super.testGateLogicCalcTrueInputs();
        testOrGate.gateLogicCalc();
        assertTrue(testOrGate.getOutputSig());
    }

    @Test
    @Override
    public void testGateLogicCalcFalseInputs(){
        super.testGateLogicCalcFalseInputs();
        testOrGate.gateLogicCalc();
        assertFalse(testOrGate.getOutputSig());
    }

    @Test
    @Override
    public void testGateLogicCalcInputOneTrue(){
        super.testGateLogicCalcInputOneTrue();
        testOrGate.gateLogicCalc();
        assertTrue(testOrGate.getOutputSig());
    }

    @Test
    @Override
    public void testGateLogicCalcInputTwoTrue(){
        super.testGateLogicCalcInputTwoTrue();
        testOrGate.gateLogicCalc();
        assertTrue(testOrGate.getOutputSig());
    }
}
