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
    //connect a gate's output to a binary arity gate's first input
    public void testAddBinaryConnectionInput1(){
        AndGate andGate = new AndGate();
        testCircComp.addBinaryConnection(andGate, 1);

        assertTrue(testCircComp.getOutputConnections().contains(andGate));
        assertEquals(testCircComp, andGate.getInputConnection1());
    }

    @Test
    //connect a gate's output to a binary arity gate's second input
    public void testAddBinaryConnectionInput2(){
        AndGate andGate = new AndGate();
        testCircComp.addBinaryConnection(andGate, 2);

        assertTrue(testCircComp.getOutputConnections().contains(andGate));
        assertEquals(testCircComp, andGate.getInputConnection2());
    }


    @Test
    //connect a gate's output to a binary arity gate's first input, overriding an existing connection
    public void testAddBinaryConnectionInput1OverrideExisting(){
        AndGate andGate = new AndGate();
        AndGate andGate2 = new AndGate();
        testCircComp.addBinaryConnection(andGate, 1);
        assertTrue(testCircComp.getOutputConnections().contains(andGate));
        assertEquals(testCircComp, andGate.getInputConnection1());
        andGate2.addBinaryConnection(andGate, 1);
        assertFalse(testCircComp.getOutputConnections().contains(andGate));

    }

    @Test
    //connect a gate's output to a binary arity gate's second input, overriding an existing connection
    public void testAddBinaryConnectionInput2OverrideExisting(){
        AndGate andGate = new AndGate();
        AndGate andGate2 = new AndGate();
        testCircComp.addBinaryConnection(andGate, 2);
        assertTrue(testCircComp.getOutputConnections().contains(andGate));
        assertEquals(testCircComp, andGate.getInputConnection2());
        andGate2.addBinaryConnection(andGate, 2);
        assertFalse(testCircComp.getOutputConnections().contains(andGate));

    }


    @Test
    //connects a gate's output to a Unary arity circuit gate's first and only input
    public void testAddUnaryConnection(){
        NotGate notGate = new NotGate();
        testCircComp.addUnaryConnection(notGate);

        assertTrue(testCircComp.getOutputConnections().contains(notGate));
        assertEquals(testCircComp, notGate.getInputConnection1());
    }

    @Test
    //connects a gate's output to a Unary arity circuit gate's first and only input, overriding an existing connection
    public void testAddUnaryConnectionOverrideExisting(){
        NotGate notGate = new NotGate();
        CircuitVariable otherVar = new CircuitVariable();
        otherVar.addUnaryConnection(notGate);
        assertTrue(otherVar.getOutputConnections().contains(notGate));
        assertEquals(otherVar, notGate.getInputConnection1());

        testCircComp.addUnaryConnection(notGate);
        assertTrue(testCircComp.getOutputConnections().contains(notGate));
        assertEquals(testCircComp, notGate.getInputConnection1());
    }

    @Test
    //removes a circuit components output connection to the first input of a binary arity gate
    //makes sure that the other output is left untouched
    public void testRemoveConnectionBinaryGateInput1(){
        AndGate andGate = new AndGate();
        AndGate andGate2 = new AndGate();
        testCircComp.addBinaryConnection(andGate, 1);
        andGate2.addBinaryConnection(andGate, 2);
        assertTrue(testCircComp.getOutputConnections().contains(andGate));
        assertEquals(testCircComp, andGate.getInputConnection1());
        assertTrue(andGate2.getOutputConnections().contains(andGate));
        assertEquals(andGate2, andGate.getInputConnection2());

        testCircComp.removeConnection(andGate);

        assertNull(andGate.getInputConnection1());
        assertFalse(testCircComp.getOutputConnections().contains(andGate));
        assertTrue(andGate2.getOutputConnections().contains(andGate));
        assertEquals(andGate2, andGate.getInputConnection2());
    }

    @Test
    //removes a circuit components output connection to the second input of a binary arity gate
    public void testRemoveConnectionBinaryGateInput2(){
        AndGate andGate = new AndGate();
        testCircComp.addBinaryConnection(andGate, 2);
        assertTrue(testCircComp.getOutputConnections().contains(andGate));
        assertEquals(testCircComp, andGate.getInputConnection2());

        testCircComp.removeConnection(andGate);

        assertNull(andGate.getInputConnection2());
        assertFalse(testCircComp.getOutputConnections().contains(andGate));
    }

    @Test
    //removes a circuit components output connection to the second input of a binary arity gate
    //that isn't actually connected. Makes sure that nothing happens.
    public void testRemoveConnectionDisconnectedBinaryGate(){
        AndGate andGate = new AndGate();
        assertFalse(testCircComp.getOutputConnections().contains(andGate));
        assertNull(andGate.getInputConnection2());
        testCircComp.removeConnection(andGate);

        assertNull(andGate.getInputConnection2());
        assertFalse(testCircComp.getOutputConnections().contains(andGate));
    }

    @Test
    //removes a circuit components output connection to the first and only input of a unary arity gate
    public void testRemoveConnectionUnaryGate(){
        NotGate notGate = new NotGate();
        testCircComp.addUnaryConnection(notGate);
        assertTrue(testCircComp.getOutputConnections().contains(notGate));
        assertEquals(testCircComp, notGate.getInputConnection1());

        testCircComp.removeConnection(notGate);

        assertNull(notGate.getInputConnection1());
        assertFalse(testCircComp.getOutputConnections().contains(notGate));
    }

    @Test
    //removes a circuit components output connection to the first and only input of a unary arity gate
    //that isn't actually connected. Makes sure that nothing happens.
    public void testRemoveConnectionDisconnectedUnaryGate(){
        NotGate notGate = new NotGate();
        assertFalse(testCircComp.getOutputConnections().contains(notGate));
        assertNull(notGate.getInputConnection1());
        testCircComp.removeConnection(notGate);

        assertNull(notGate.getInputConnection1());
        assertFalse(testCircComp.getOutputConnections().contains(notGate));
    }

    @Test
    //assign a name to a circuit component
    public void testSetName() {
        String newName = "Something";
        assertEquals("", testCircComp.getComponentName());
        testCircComp.setName(newName);

        assertEquals(newName, testCircComp.getComponentName());
    }

    @Test
    //get the name of a circuit component
    public void testGetName(){
        String newName = "Something";
        testCircComp.setName(newName);

        assertEquals(newName, testCircComp.getComponentName());
    }



}
