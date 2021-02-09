package model;

import model.gates.AndGate;
import model.gates.NotGate;
import model.gates.OrGate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class LogicalExpressionTest {
    LogicalExpression testLogicalExpression;

    @BeforeEach
    public void setupTests(){
        testLogicalExpression = new LogicalExpression();
    }

    @Test
    public void testGenerateLogicalCircuit(){
        testLogicalExpression.setLogicalExpression("((A ∨ B) ∧ C)");
        LogicalCircuit generatedCirc = testLogicalExpression.generateCircuit();
        LogicalCircuit comparitorCirc = new LogicalCircuit();
        CircuitVariable a = new CircuitVariable();
        CircuitVariable b = new CircuitVariable();
        CircuitVariable c = new CircuitVariable();
        OrGate orGate = new OrGate();
        AndGate andGate = new AndGate();

        comparitorCirc.addCircuitPart(andGate);
        comparitorCirc.addCircuitPart(orGate);
        comparitorCirc.addCircuitPart(a);
        comparitorCirc.addCircuitPart(b);
        comparitorCirc.addCircuitPart(c);

        comparitorCirc.changeOutPutConnection(andGate, comparitorCirc.getHead(), 1);
        comparitorCirc.changeOutPutConnection(orGate, andGate, 1);
        comparitorCirc.changeOutPutConnection(c, andGate, 2);
        comparitorCirc.changeOutPutConnection(a, orGate, 1);
        comparitorCirc.changeOutPutConnection(b, orGate, 2);

        assertEquals(comparitorCirc.generateExpression().getLogicalExpression(),
                     generatedCirc.generateExpression().getLogicalExpression());
    }

    @Test
    public void testGenerateLogicalCircuitWithNegation(){
        testLogicalExpression.setLogicalExpression("((~(A ∨ B) ∨ C) ∧ D)");
        LogicalCircuit generatedCirc = testLogicalExpression.generateCircuit();
        LogicalCircuit comparitorCirc = new LogicalCircuit();

        CircuitVariable a = new CircuitVariable();
        CircuitVariable b = new CircuitVariable();
        CircuitVariable c = new CircuitVariable();
        CircuitVariable d = new CircuitVariable();
        NotGate notGate = new NotGate();
        AndGate andGate = new AndGate();
        OrGate orGate = new OrGate();
        OrGate orGate2 = new OrGate();

        comparitorCirc.addCircuitPart(a);
        comparitorCirc.addCircuitPart(b);
        comparitorCirc.addCircuitPart(c);
        comparitorCirc.addCircuitPart(d);
        comparitorCirc.addCircuitPart(notGate);
        comparitorCirc.addCircuitPart(andGate);
        comparitorCirc.addCircuitPart(orGate);
        comparitorCirc.addCircuitPart(orGate2);

        comparitorCirc.changeOutPutConnection(andGate, comparitorCirc.getHead(), 1);
        comparitorCirc.changeOutPutConnection(d, andGate, 2);
        comparitorCirc.changeOutPutConnection(orGate2, andGate, 1);
        comparitorCirc.changeOutPutConnection(c, orGate2, 2);
        comparitorCirc.changeOutPutConnection(notGate, orGate2, 1);
        comparitorCirc.changeOutPutConnection(orGate, notGate, 1);
        comparitorCirc.changeOutPutConnection(a, orGate, 1);
        comparitorCirc.changeOutPutConnection(b, orGate, 2);

        assertEquals(comparitorCirc.generateExpression().getLogicalExpression(),
                generatedCirc.generateExpression().getLogicalExpression());
    }

    @Test
    public void testGenerateLogicalCircuitWithDuplicateVars(){
        testLogicalExpression.setLogicalExpression("(~(A ∧ B) ∨ A)");
        LogicalCircuit generatedCirc = testLogicalExpression.generateCircuit();
        LogicalCircuit comparitorCirc = new LogicalCircuit();
        CircuitVariable a = new CircuitVariable();
        CircuitVariable b = new CircuitVariable();
        NotGate notGate = new NotGate();
        AndGate andGate = new AndGate();
        OrGate orGate = new OrGate();
        comparitorCirc.addCircuitPart(a);
        comparitorCirc.addCircuitPart(b);
        comparitorCirc.addCircuitPart(notGate);
        comparitorCirc.addCircuitPart(andGate);
        comparitorCirc.addCircuitPart(orGate);

        comparitorCirc.changeOutPutConnection(orGate, comparitorCirc.getHead(), 1);
        comparitorCirc.changeOutPutConnection(notGate, orGate, 1);
        comparitorCirc.changeOutPutConnection(a, orGate, 2);
        comparitorCirc.changeOutPutConnection(andGate, notGate, 1);
        comparitorCirc.changeOutPutConnection(a, andGate, 1);
        comparitorCirc.changeOutPutConnection(b, andGate, 2);

        assertEquals(comparitorCirc.generateExpression().getLogicalExpression(),
                generatedCirc.generateExpression().getLogicalExpression());
    }

    @Test
    public void testGenerateLogicalCircuitWithNegatedVars(){
        testLogicalExpression.setLogicalExpression("((A ∧ B) ∨ ~A)");
        LogicalCircuit generatedCirc = testLogicalExpression.generateCircuit();
        LogicalCircuit comparitorCirc = new LogicalCircuit();
        CircuitVariable a = new CircuitVariable();
        CircuitVariable b = new CircuitVariable();
        NotGate notGate = new NotGate();
        AndGate andGate = new AndGate();
        OrGate orGate = new OrGate();
        comparitorCirc.addCircuitPart(a);
        comparitorCirc.addCircuitPart(b);
        comparitorCirc.addCircuitPart(notGate);
        comparitorCirc.addCircuitPart(andGate);
        comparitorCirc.addCircuitPart(orGate);

        comparitorCirc.changeOutPutConnection(orGate, comparitorCirc.getHead(), 1);
        comparitorCirc.changeOutPutConnection(notGate, orGate, 2);
        comparitorCirc.changeOutPutConnection(a, notGate, 2);
        comparitorCirc.changeOutPutConnection(andGate, orGate, 1);
        comparitorCirc.changeOutPutConnection(a, andGate, 1);
        comparitorCirc.changeOutPutConnection(b, andGate, 2);

        assertEquals(comparitorCirc.generateExpression().getLogicalExpression(),
                generatedCirc.generateExpression().getLogicalExpression());
    }



}

