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
    public void testAddCircuitPart() {
        AndGate andGate = new AndGate();
        testLogicalCircuit.addCircuitPart(andGate);
        assertTrue(testLogicalCircuit.getCircuitComponents().contains(andGate));
    }

    @Test
    public void testAddCircuitPartVariable() {
        CircuitVariable inputVar = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(inputVar);
        assertTrue(testLogicalCircuit.getCircuitComponents().contains(inputVar));
        assertEquals(inputVar.getVarID(), LogicalCircuit.VariableIdentifier.A);
    }

    @Test
    public void testAddCircuitPartVariableAllUsed() {
        CircuitVariable inputVar = new CircuitVariable();
        CircuitVariable inputVar2 = new CircuitVariable();
        CircuitVariable inputVar3 = new CircuitVariable();
        CircuitVariable inputVar4 = new CircuitVariable();
        CircuitVariable inputVar5 = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(inputVar);
        testLogicalCircuit.addCircuitPart(inputVar2);
        testLogicalCircuit.addCircuitPart(inputVar3);
        testLogicalCircuit.addCircuitPart(inputVar4);
        testLogicalCircuit.addCircuitPart(inputVar5);
        assertFalse(testLogicalCircuit.getCircuitComponents().contains(inputVar5));
    }

    @Test
    public void testRemoveCircuitPart() {
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
    public void testRemoveCircuitPartVariable() {
        CircuitVariable inputVar = new CircuitVariable();
        testLogicalCircuit.addCircuitPart(inputVar);
        assertTrue(testLogicalCircuit.getCircuitComponents().contains(inputVar));
        assertTrue(testLogicalCircuit.usedVarIDs.contains(LogicalCircuit.VariableIdentifier.A));
        testLogicalCircuit.removeCircuitPart(inputVar);
        assertFalse(testLogicalCircuit.getCircuitComponents().contains(inputVar));
        assertFalse(testLogicalCircuit.usedVarIDs.contains(LogicalCircuit.VariableIdentifier.A));

    }

    @Test
    public void testRemoveOutputConnectionBinaryCircuitFirstInput(){
        //todo
    }

    @Test
    public void testRemoveOutputConnectionBinaryCircuitSecondInput(){
        //todo
    }

    @Test
    public void testRemoveOutputConnectionUnaryCircuit(){
        //todo
    }

    @Test
    public void testRemoveInputConnectionBinaryGate() {
        CircuitVariable inputVar = new CircuitVariable();
        CircuitVariable inputVar2 = new CircuitVariable();
        AndGate AndGate = new AndGate();
        testLogicalCircuit.addCircuitPart(inputVar);
        testLogicalCircuit.addCircuitPart(AndGate);
        testLogicalCircuit.addCircuitPart(inputVar2);
        testLogicalCircuit.changeOutPutConnection(inputVar, AndGate, 1);
        testLogicalCircuit.changeOutPutConnection(inputVar2, AndGate, 2);
        assertEquals(inputVar.getOutputConnection(), AndGate);
        assertEquals(inputVar2.getOutputConnection(), AndGate);
        testLogicalCircuit.removeInputConnection(AndGate);
        assertEquals(inputVar.getOutputConnection(), null);
        assertEquals(inputVar2.getOutputConnection(), null);
    }

    @Test
    public void testRemoveInputConnectionUnaryGate() {
        CircuitVariable inputVar = new CircuitVariable();
        NotGate notGate = new NotGate();
        testLogicalCircuit.addCircuitPart(inputVar);
        testLogicalCircuit.addCircuitPart(notGate);
        testLogicalCircuit.changeOutPutConnection(inputVar, notGate, 1);
        assertEquals(inputVar.getOutputConnection(), notGate);
        testLogicalCircuit.removeInputConnection(notGate);
        assertEquals(inputVar.getOutputConnection(), null);

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

    @Test
    public void testSwitchConnection1BothNotNull() {
        CircuitVariable a = new CircuitVariable();
        CircuitVariable b = new CircuitVariable();
        NotGate notGate1 = new NotGate();
        NotGate notGate2 = new NotGate();
        testLogicalCircuit.addCircuitPart(a);
        testLogicalCircuit.addCircuitPart(b);
        testLogicalCircuit.addCircuitPart(notGate1);
        testLogicalCircuit.addCircuitPart(notGate2);
        testLogicalCircuit.changeOutPutConnection(a, notGate1, 1);
        testLogicalCircuit.changeOutPutConnection(b, notGate2, 2);
        assertEquals(a.getOutputConnection(), notGate1);
        assertEquals(b.getOutputConnection(), notGate2);
        assertEquals(notGate1.getInputConnection1(), a);
        assertEquals(notGate2.getInputConnection1(), b);
        testLogicalCircuit.switchConnection1(b, notGate1);
        assertEquals(a.getOutputConnection(), null);
        assertEquals(b.getOutputConnection(), notGate1);
        assertEquals(notGate1.getInputConnection1(), b);
        assertEquals(notGate2.getInputConnection1(), null);
    }

    @Test
    public void testSwitchConnection1InputNotNull() {
        //todo
    }

    @Test
    public void testSwitchConnection1OutputNotNull(){
        //todo
    }


    @Test
    public void testSwitchConnection2BothNotNull() {
        CircuitVariable a = new CircuitVariable();
        CircuitVariable b = new CircuitVariable();
        AndGate andGate = new AndGate();
        OrGate orGate = new OrGate();
        testLogicalCircuit.addCircuitPart(a);
        testLogicalCircuit.addCircuitPart(b);
        testLogicalCircuit.addCircuitPart(andGate);
        testLogicalCircuit.addCircuitPart(orGate);
        testLogicalCircuit.changeOutPutConnection(a, andGate, 2);
        testLogicalCircuit.changeOutPutConnection(b, orGate, 2);
        assertEquals(andGate, a.getOutputConnection());
        assertEquals(orGate, b.getOutputConnection());
        assertEquals(a, andGate.getInputConnection2());
        assertEquals(b, orGate.getInputConnection2());
        testLogicalCircuit.switchConnection2(b, andGate);
        assertEquals(null, a.getOutputConnection());
        assertEquals(andGate, b.getOutputConnection());
        assertEquals(b, andGate.getInputConnection2());
        assertEquals(null, orGate.getInputConnection2());
    }

    @Test
    public void testSwitchConnection2InputNotNull() {
        //todo
    }

    @Test
    public void testSwitchConnection2OutputNotNull(){

    }


}
