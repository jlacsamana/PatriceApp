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
    //both of an OR gate's input's are connected to circuit components that are outputting true
    public void testGateLogicCalcTrueInputs(){
        super.testGateLogicCalcTrueInputs();
        testOrGate.gateLogicCalc();
        assertTrue(testOrGate.getOutputSig());
    }

    @Test
    @Override
    //both of an OR gate's input's are connected to circuit components that are outputting false
    public void testGateLogicCalcFalseInputs(){
        super.testGateLogicCalcFalseInputs();
        testOrGate.gateLogicCalc();
        assertFalse(testOrGate.getOutputSig());
    }

    @Test
    @Override
    //the first of an OR gate's inputs are connected to a circuit component that outputs true, but the other
    //is connected to one that outputs false
    public void testGateLogicCalcInputOneTrue(){
        super.testGateLogicCalcInputOneTrue();
        testOrGate.gateLogicCalc();
        assertTrue(testOrGate.getOutputSig());
    }

    @Test
    @Override
    //the first of an OR gate's inputs are connected to a circuit component that outputs false, but the other
    //is connected to one that outputs true
    public void testGateLogicCalcInputTwoTrue(){
        super.testGateLogicCalcInputTwoTrue();
        testOrGate.gateLogicCalc();
        assertTrue(testOrGate.getOutputSig());
    }
}
