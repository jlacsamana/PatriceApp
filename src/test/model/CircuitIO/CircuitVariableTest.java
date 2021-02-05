package model.CircuitIO;

import model.CircuitOutput;
import model.CircuitVariable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CircuitVariableTest extends CircuitComponentTest{
    CircuitVariable testCircuitVar;

    @BeforeEach
    public void setupTests(){
        testCircComp = new CircuitVariable();
        testCircuitVar = (CircuitVariable) testCircComp;
    }

    @Test
    public void testToggleOutputSignalTrue(){
        testCircuitVar.setOutputSignal(false);
        testCircuitVar.setOutputSignal(true);
        assertTrue(testCircuitVar.getOutputSig());
    }

    @Test
    public void testToggleOutputSignalFalse(){
        testCircuitVar.setOutputSignal(true);
        testCircuitVar.setOutputSignal(false);
        assertFalse(testCircuitVar.getOutputSig());
    }

    @Test
    public void testSetOutputConnection(){
        CircuitOutput testOutput = new CircuitOutput();
        testCircuitVar.changeOutputConnection(testOutput);
        assertEquals(testOutput, testCircuitVar.getOutputConnection());
    }


}
