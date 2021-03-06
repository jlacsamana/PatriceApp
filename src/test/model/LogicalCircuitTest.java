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


public class LogicalCircuitTest {
    LogicalCircuit testLogicalCircuit;
    CircuitVariable a;
    CircuitVariable b;
    CircuitVariable c;
    CircuitVariable d;

    @BeforeEach
    public void setupTests() {
        testLogicalCircuit = new LogicalCircuit();
        a = new CircuitVariable();
        b = new CircuitVariable();
        c = new CircuitVariable();
        d = new CircuitVariable();
    }

    @Test
    //creates a new Circuit, makes sure the one circuit output is added properly and and is set as the head of the
    //circuit
    public void testCircuitInitialization() {
        CircuitOutput circHead = testLogicalCircuit.headPart;
        ArrayList<CircuitComponent> comparitorPartList = new ArrayList<>();
        comparitorPartList.add(circHead);
        assertEquals(CircuitOutput.class, circHead.getClass());
        assertEquals(comparitorPartList, testLogicalCircuit.getCircuitComponents());
    }

    @Test
    //generates a logical expression from 4 variables
    public void testGenerateExpression() {
        OrGate orGate = new OrGate();
        AndGate andGate1 = new AndGate(); 
        AndGate andGate2 = new AndGate();
        NotGate notGate = new NotGate();
        testLogicalCircuit.addCircuitPart(a);
        testLogicalCircuit.addCircuitPart(b);
        testLogicalCircuit.addCircuitPart(c);
        testLogicalCircuit.addCircuitPart(d);
        testLogicalCircuit.addCircuitPart(orGate);
        testLogicalCircuit.addCircuitPart(andGate1);
        testLogicalCircuit.addCircuitPart(andGate2);
        testLogicalCircuit.addCircuitPart(notGate);
        testLogicalCircuit.changeOutPutConnection(a, andGate1, 1);
        testLogicalCircuit.changeOutPutConnection(b, andGate1, 2);
        testLogicalCircuit.changeOutPutConnection(c, andGate2, 1);
        testLogicalCircuit.changeOutPutConnection(d, andGate2, 2);
        testLogicalCircuit.changeOutPutConnection(andGate1, orGate, 1);
        testLogicalCircuit.changeOutPutConnection(andGate2, notGate, 2);
        testLogicalCircuit.changeOutPutConnection(notGate, orGate, 2);
        testLogicalCircuit.changeOutPutConnection(orGate, testLogicalCircuit.getHead(), 1);
        LogicalExpression generatedExpression = testLogicalCircuit.generateExpression();
        LogicalExpression comparitorExpression = new LogicalExpression();
        comparitorExpression.setLogicalExpression("((A ∧ B) ∨ ~(C ∧ D))");

        assertEquals(comparitorExpression.getLogicalExpression(), generatedExpression.getLogicalExpression());
    }

    @Test
    //a circuit component is added to the circuit that isnt a circuit variable
    public void testAddCircuitPartNonVariable() {
        AndGate andGate = new AndGate();
        testLogicalCircuit.addCircuitPart(andGate);

        assertTrue(testLogicalCircuit.getCircuitComponents().contains(andGate));
    }

    @Test
    //adds a circuit variable to the circuit when there are still available variables
    public void testAddCircuitPartVariableAvailable() {
        CircuitVariable newCircVar = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(newCircVar);

        assertTrue(testLogicalCircuit.getCircuitComponents().contains(newCircVar));
        assertEquals(LogicalCircuit.VariableIdentifier.A, newCircVar.getVarID());
    }

    @Test
    //tries (and fails) to add a circuit variable to the circuit when there are no more variables available
    public void testAddCircuitPartVariableUnavailable() {
        CircuitVariable newCircVar5 = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(a);
        testLogicalCircuit.addCircuitPart(b);
        testLogicalCircuit.addCircuitPart(c);
        testLogicalCircuit.addCircuitPart(b);
        testLogicalCircuit.addCircuitPart(newCircVar5);

        assertFalse(testLogicalCircuit.getCircuitComponents().contains(newCircVar5));
    }

    @Test
    //removes a unary arity gate from the circuit
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
    //removes a binary arity gate from a circuit
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
    //removes a circuit component with no connections to other circuit components from a circuit
    public void testRemoveCircuitPartDisconnected(){
        AndGate andGate = new AndGate();
        testLogicalCircuit.addCircuitPart(andGate);
        assertTrue(testLogicalCircuit.getCircuitComponents().contains(andGate));

        testLogicalCircuit.removeCircuitPart(andGate);
        assertFalse(testLogicalCircuit.getCircuitComponents().contains(andGate));

    }

    @Test
    //removes a circuit variable from a circuit
    public void testRemoveCircuitPartCircVar() {
        CircuitVariable newCircVar = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(newCircVar);
        assertTrue(testLogicalCircuit.getUsedVarIDs().contains(LogicalCircuit.VariableIdentifier.A));

        testLogicalCircuit.removeCircuitPart(newCircVar);
        assertFalse(testLogicalCircuit.getUsedVarIDs().contains(LogicalCircuit.VariableIdentifier.A));
    }

    @Test
    //tests if all output connections that a circuit component has are discnnected after it is
    //removed from a circuit
    public void testRemoveCircuitPartClrOutputs() {
        CircuitVariable testCircVar = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(testCircVar);
        testLogicalCircuit.changeOutPutConnection(testCircVar, testLogicalCircuit.getHead(), 2);
        assertTrue(testCircVar.getOutputConnections().contains(testLogicalCircuit.getHead()));

        testLogicalCircuit.removeCircuitPart(testCircVar);
        assertEquals(testCircVar.getOutputConnections().size(), 0);
    }

    @Test
    //removes a variable identifier from the list of currently in-use variable identifiers
    public void testRemoveFromUsedVarIDs () {
        LogicalCircuit.VariableIdentifier testVarId = LogicalCircuit.VariableIdentifier.A;
        testLogicalCircuit.addToUsedVarIDs(testVarId);
        assertTrue(testLogicalCircuit.getUsedVarIDs().contains(testVarId));

        testLogicalCircuit.removeFromUsedVarIDs(testVarId);
        assertFalse(testLogicalCircuit.getUsedVarIDs().contains(testVarId));
    }

    @Test
    //adds a variable identifier to the list of currently in-use variable identifiers
    public void testAddToUsedVarIds() {
        LogicalCircuit.VariableIdentifier testVarId = LogicalCircuit.VariableIdentifier.A;
        assertFalse(testLogicalCircuit.getUsedVarIDs().contains(testVarId));

        testLogicalCircuit.addToUsedVarIDs(testVarId);
        assertTrue(testLogicalCircuit.getUsedVarIDs().contains(testVarId));
    }






}
