package model.BinaryGateTests;
import model.CircuitVariable;
import model.gates.BinaryCircuitGate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public abstract class BinaryGateTest {
    CircuitVariable testInput1;
    CircuitVariable testInput2;
    BinaryCircuitGate testBinaryGate;

    @BeforeEach
    public void setupTests(){
        testInput1 = new CircuitVariable();
        testInput2 = new CircuitVariable();
    }

    public void testGateLogicCalcTrueInputs(){
        testInput1.setOutputSignal(true);
        testInput2.setOutputSignal(true);
    };

    public void testGateLogicCalcFalseInputs(){
        testInput1.setOutputSignal(false);
        testInput2.setOutputSignal(false);
    };

    public void testGateLogicCalcInputOneTrue(){
        testInput1.setOutputSignal(true);
        testInput2.setOutputSignal(false);
    };

    public void testGateLogicCalcInputTwoTrue(){
        testInput1.setOutputSignal(false);
        testInput2.setOutputSignal(true);
    };

    @Test
    public void testChangeInputConnection2(){
        CircuitVariable testInput3 = new CircuitVariable();
        testBinaryGate.changeInputConnection2(testInput3);
        assertEquals(testBinaryGate.getInputConnection2(), testInput3);
    }
}
