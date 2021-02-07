package model;

import com.sun.org.apache.xpath.internal.operations.And;
import model.gates.AndGate;
import model.gates.CircuitGate;
import model.gates.NotGate;
import model.gates.OrGate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LogicalCircuitTest {
    LogicalCircuit testLogicalCircuit;

    @BeforeEach
    public void setupTests() {
        testLogicalCircuit = new LogicalCircuit();
    }

    @Test
    public void testCircuitInitialization() {
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
    public void testAddCircuitPartNonVariable() {
        AndGate andGate = new AndGate();
        testLogicalCircuit.addCircuitPart(andGate);

        assertTrue(testLogicalCircuit.getCircuitComponents().contains(andGate));
    }

    @Test
    public void testAddCircuitPartVariableAvailable() {
        CircuitVariable newCircVar = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(newCircVar);

        assertTrue(testLogicalCircuit.getCircuitComponents().contains(newCircVar));
        assertEquals(newCircVar.getVarID(),  LogicalCircuit.VariableIdentifier.A);
    }

    @Test
    public void testAddCircuitPartVariableUnavailable() {
        CircuitVariable newCircVar = new CircuitVariable();
        CircuitVariable newCircVar2 = new CircuitVariable();
        CircuitVariable newCircVar3 = new CircuitVariable();
        CircuitVariable newCircVar4 = new CircuitVariable();
        CircuitVariable newCircVar5 = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(newCircVar);
        testLogicalCircuit.addCircuitPart(newCircVar2);
        testLogicalCircuit.addCircuitPart(newCircVar3);
        testLogicalCircuit.addCircuitPart(newCircVar4);
        testLogicalCircuit.addCircuitPart(newCircVar5);

        assertFalse(testLogicalCircuit.getCircuitComponents().contains(newCircVar5));
    }

    @Test
    public void testRemoveCircuitPartUnaryGate() {
        NotGate notGate = new NotGate();
        CircuitVariable testCircVar = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(notGate);
        testLogicalCircuit.addCircuitPart(testCircVar);
        testCircVar.addUnaryConnection(notGate);
        assertTrue(testLogicalCircuit.getCircuitComponents().contains(notGate));
        assertTrue(testCircVar.getOutputConnections().contains(notGate));

        testLogicalCircuit.removeCircuitPart(notGate);
        assertFalse(testLogicalCircuit.getCircuitComponents().contains(notGate));
        assertFalse(testCircVar.getOutputConnections().contains(notGate));

    }

    @Test
    public void testRemoveCircuitPartBinaryGate() {
        AndGate andGate = new AndGate();
        CircuitVariable testCircVar = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(andGate);
        testLogicalCircuit.addCircuitPart(testCircVar);
        testCircVar.addBinaryConnection(andGate, 2);
        assertTrue(testLogicalCircuit.getCircuitComponents().contains(andGate));
        assertTrue(testCircVar.getOutputConnections().contains(andGate));

        testLogicalCircuit.removeCircuitPart(andGate);
        assertFalse(testLogicalCircuit.getCircuitComponents().contains(andGate));
        assertFalse(testCircVar.getOutputConnections().contains(andGate));
    }

    @Test
    public void testRemoveCircuitPartCircVar() {
        CircuitVariable newCircVar = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(newCircVar);
        assertTrue(testLogicalCircuit.getUsedVarIDs().contains(LogicalCircuit.VariableIdentifier.A));

        testLogicalCircuit.removeCircuitPart(newCircVar);
        assertFalse(testLogicalCircuit.getUsedVarIDs().contains(LogicalCircuit.VariableIdentifier.A));
    }

    @Test
    public void testRemoveCircuitPartClrOutputs() {
        CircuitVariable testCircVar = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(testCircVar);
        testLogicalCircuit.changeOutPutConnection(testCircVar, testLogicalCircuit.getHead(), 2);
        assertTrue(testCircVar.getOutputConnections().contains(testLogicalCircuit.getHead()));

        testLogicalCircuit.removeCircuitPart(testCircVar);
        assertTrue(testCircVar.getOutputConnections().size() == 0);
    }






}
