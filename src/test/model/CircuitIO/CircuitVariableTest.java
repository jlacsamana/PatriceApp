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
    //toggle a circuit variable's input from false to true
    public void testToggleOutputSignalTrue(){
        testCircuitVar.setOutputSignal(false);
        assertFalse(testCircuitVar.getOutputSig());
        testCircuitVar.setOutputSignal(true);
        assertTrue(testCircuitVar.getOutputSig());
    }

    @Test
    //toggle a circuit variable's input from true to false
    public void testToggleOutputSignalFalse(){
        testCircuitVar.setOutputSignal(true);
        assertTrue(testCircuitVar.getOutputSig());
        testCircuitVar.setOutputSignal(false);
        assertFalse(testCircuitVar.getOutputSig());
    }



}
