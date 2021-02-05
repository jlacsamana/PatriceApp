package model;

import model.gates.AndGate;
import model.gates.OrGate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class LogicalCircuitTest {
    LogicalCircuit testLogicalCircuit;

    @BeforeEach
    public void setupTests(){
        testLogicalCircuit = new LogicalCircuit();
    }

    @Test
    public void testCircuitInitialization(){
        CircuitOutput circHead = testLogicalCircuit.headPart;
        ArrayList<CircuitComponent> comparitorPartList = new ArrayList<CircuitComponent>();
        comparitorPartList.add(circHead);
        assertEquals(CircuitOutput.class, circHead.getClass());
        assertEquals(comparitorPartList, testLogicalCircuit.getCircuitComponents());
    }

    @Test
    public void testGenerateExpression() {

        LogicalExpression generatedExpression = testLogicalCircuit.generateExpression();
        LogicalExpression comparitorExpression = new LogicalExpression();
        comparitorExpression.setLogicalExpression("(A ∧ B) ∨ (C ∧ D)");

        assertEquals(generatedExpression.getLogicalExpression(), comparitorExpression.getLogicalExpression());

    }

    @Test
    public void testAddCircuitPart(){
        CircuitVariable inputVar = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(inputVar);
        assertTrue(testLogicalCircuit.getCircuitComponents().contains(inputVar));
    }

    @Test
    public void testRemoveCircuitPart(){
        CircuitVariable a = new CircuitVariable();
        CircuitVariable b = new CircuitVariable();
        AndGate andGate = new AndGate();
        testLogicalCircuit.addCircuitPart(a);
        testLogicalCircuit.addCircuitPart(b);
        testLogicalCircuit.addCircuitPart(andGate);
        testLogicalCircuit.changeOutPutConnection(a, andGate, 1);
        testLogicalCircuit.changeOutPutConnection(b, andGate, 2);
        testLogicalCircuit.changeOutPutConnection(andGate, testLogicalCircuit.getHead(), 1);
        assertEquals(a.getOutputConnection(), andGate);
        assertEquals(b.getOutputConnection(), andGate);
        assertEquals(testLogicalCircuit.getHead().getInputConnection1(), andGate);
        testLogicalCircuit.removeCircuitPart(andGate);
        assertEquals(a.getOutputConnection(), null);
        assertEquals(b.getOutputConnection(), null);
        assertEquals(testLogicalCircuit.getHead().getInputConnection1(), null);


    }
    @Test
    public void testChangeOutPutConnectionInputOne() {
        CircuitVariable a = new CircuitVariable();
        OrGate orGate = new OrGate();
        testLogicalCircuit.addCircuitPart(a);
        testLogicalCircuit.addCircuitPart(orGate);
        testLogicalCircuit.changeOutPutConnection(a, orGate, 1);
        assertEquals(a.getOutputConnection(), orGate);
        assertEquals(orGate.getInputConnection1(), a);
    }

    @Test
    public void testChangeOutPutConnectionInputTwo() {
        CircuitVariable a = new CircuitVariable();
        OrGate orGate = new OrGate();
        testLogicalCircuit.addCircuitPart(a);
        testLogicalCircuit.addCircuitPart(orGate);
        testLogicalCircuit.changeOutPutConnection(a, orGate, 2);
        assertEquals(a.getOutputConnection(), orGate);
        assertEquals(orGate.getInputConnection2(), a);
    }

    @Test
    public void testChangeOutPutConnectionOverrideConnection() {
        CircuitVariable a = new CircuitVariable();
        CircuitVariable b = new CircuitVariable();
        OrGate orGate = new OrGate();
        testLogicalCircuit.addCircuitPart(a);
        testLogicalCircuit.addCircuitPart(b);
        testLogicalCircuit.addCircuitPart(orGate);
        testLogicalCircuit.changeOutPutConnection(a, orGate, 1);
        assertEquals(a.getOutputConnection(), orGate);
        assertEquals(orGate.getInputConnection1(), a);
        testLogicalCircuit.changeOutPutConnection(b, orGate, 1);
        assertEquals(b.getOutputConnection(), orGate);
        assertEquals(orGate.getInputConnection1(), b);
        assertEquals(a.getOutputConnection(), null);

    }


}
