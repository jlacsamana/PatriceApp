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
    // both of an AND gate's inputs are connected to a circuit component that's outputting true
    public void testGateLogicCalcTrueInputs(){
        super.testGateLogicCalcTrueInputs();
        testBinaryGate.gateLogicCalc();
        assertTrue(testBinaryGate.getOutputSig());
    }

    @Test
    @Override
    // both of an AND gate's inputs are connected to a circuit component that's outputting false
    public void testGateLogicCalcFalseInputs(){
        super.testGateLogicCalcFalseInputs();
        testBinaryGate.gateLogicCalc();
        assertFalse(testBinaryGate.getOutputSig());
    }

    @Test
    @Override
    // the first input of an AND gate is connected to a circuit component that outputs true, but the
    //other is connected to a component that's outputting false
    public void testGateLogicCalcInputOneTrue(){
        super.testGateLogicCalcInputOneTrue();
        testBinaryGate.gateLogicCalc();
        assertFalse(testBinaryGate.getOutputSig());
    }

    @Test
    @Override
    // the first input of an AND gate is connected to a circuit component that outputs false, but the
    //other is connected to a component that's outputting true
    public void testGateLogicCalcInputTwoTrue(){
        super.testGateLogicCalcInputTwoTrue();
        testBinaryGate.gateLogicCalc();
        assertFalse(testBinaryGate.getOutputSig());
    }




}
