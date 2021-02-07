package model.CircuitIO;

import model.CircuitComponent;
import model.CircuitOutput;
import model.CircuitVariable;
import model.gates.AndGate;
import model.gates.NotGate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public abstract class CircuitComponentTest {
    CircuitComponent testCircComp;

    @BeforeEach
    public void setupTests(){
        testCircComp = new CircuitVariable();
    }

    @Test
    public void testAddBinaryConnectionInput1(){
        AndGate andGate = new AndGate();
        testCircComp.addBinaryConnection(andGate, 1);

        assertTrue(testCircComp.getOutputConnections().contains(andGate));
        assertEquals(andGate.getInputConnection1(), testCircComp);
    }

    @Test
    public void testAddBinaryConnectionInput2(){
        AndGate andGate = new AndGate();
        testCircComp.addBinaryConnection(andGate, 2);

        assertTrue(testCircComp.getOutputConnections().contains(andGate));
        assertEquals(andGate.getInputConnection2(), testCircComp);
    }

    @Test void testAddBinaryConnectionInput1OverrideExisting(){
        AndGate andGate = new AndGate();
        AndGate andGate2 = new AndGate();
        testCircComp.addBinaryConnection(andGate, 1);
        assertTrue(testCircComp.getOutputConnections().contains(andGate));
        assertEquals(andGate.getInputConnection1(), testCircComp);
        andGate2.addBinaryConnection(andGate, 1);
        assertFalse(testCircComp.getOutputConnections().contains(andGate));

    }

    @Test
    public void testAddBinaryConnectionInput2OverrideExisting(){
        AndGate andGate = new AndGate();
        AndGate andGate2 = new AndGate();
        testCircComp.addBinaryConnection(andGate, 2);
        assertTrue(testCircComp.getOutputConnections().contains(andGate));
        assertEquals(andGate.getInputConnection2(), testCircComp);
        andGate2.addBinaryConnection(andGate, 2);
        assertFalse(testCircComp.getOutputConnections().contains(andGate));

    }


    @Test
    public void testAddUnaryConnection(){
        NotGate notGate = new NotGate();
        testCircComp.addUnaryConnection(notGate);

        assertTrue(testCircComp.getOutputConnections().contains(notGate));
        assertEquals(notGate.getInputConnection1(), testCircComp);
    }

    @Test
    public void testAddUnaryConnectionOverrideExisting(){
        NotGate notGate = new NotGate();
        CircuitVariable otherVar = new CircuitVariable();
        otherVar.addUnaryConnection(notGate);
        assertTrue(otherVar.getOutputConnections().contains(notGate));
        assertEquals(notGate.getInputConnection1(), otherVar);

        testCircComp.addUnaryConnection(notGate);
        assertTrue(testCircComp.getOutputConnections().contains(notGate));
        assertEquals(notGate.getInputConnection1(), testCircComp);
    }

    @Test
    public void testRemoveConnectionBinaryGateInput1(){
        AndGate andGate = new AndGate();
        AndGate andGate2 = new AndGate();
        testCircComp.addBinaryConnection(andGate, 1);
        andGate2.addBinaryConnection(andGate, 2);
        assertTrue(testCircComp.getOutputConnections().contains(andGate));
        assertEquals(andGate.getInputConnection1(), testCircComp);
        testCircComp.removeConnection(andGate);

        assertNull(andGate.getInputConnection1());
    }

    @Test
    public void testRemoveConnectionBinaryGateInput2(){
        AndGate andGate = new AndGate();
        testCircComp.addBinaryConnection(andGate, 2);
        assertTrue(testCircComp.getOutputConnections().contains(andGate));
        assertEquals(andGate.getInputConnection2(), testCircComp);
        testCircComp.removeConnection(andGate);

        assertNull(andGate.getInputConnection2());
    }

    @Test
    public void testRemoveConnectionUnaryGate(){
        NotGate notGate = new NotGate();
        testCircComp.addUnaryConnection(notGate);
        assertTrue(testCircComp.getOutputConnections().contains(notGate));
        assertEquals(notGate.getInputConnection1(), testCircComp);
        testCircComp.removeConnection(notGate);

        assertNull(notGate.getInputConnection1());
    }


}
