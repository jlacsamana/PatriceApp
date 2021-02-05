package model.BinaryGateTests;
import model.gates.AndGate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AndGateTest extends BinaryGateTest{
AndGate testAndGate;

    @Override
    @BeforeEach
    public void setupTests(){
        super.setupTests();
        testBinaryGate = new AndGate();
        testBinaryGate.changeInputConnection1(testInput1);
        testBinaryGate.changeInputConnection2(testInput2);
        testAndGate = (AndGate) testBinaryGate;
    }

    @Test
    @Override
    public void testGateLogicCalcTrueInputs(){
        super.testGateLogicCalcTrueInputs();
        testBinaryGate.gateLogicCalc();
        assertTrue(testBinaryGate.getOutputSig());
    }

    @Test
    @Override
    public void testGateLogicCalcFalseInputs(){
        super.testGateLogicCalcFalseInputs();
        testBinaryGate.gateLogicCalc();
        assertFalse(testBinaryGate.getOutputSig());
    }

    @Test
    @Override
    public void testGateLogicCalcInputOneTrue(){
        super.testGateLogicCalcInputOneTrue();
        testBinaryGate.gateLogicCalc();
        assertFalse(testBinaryGate.getOutputSig());
    }

    @Test
    @Override
    public void testGateLogicCalcInputTwoTrue(){
        super.testGateLogicCalcInputTwoTrue();
        testBinaryGate.gateLogicCalc();
        assertFalse(testBinaryGate.getOutputSig());
    }




}
