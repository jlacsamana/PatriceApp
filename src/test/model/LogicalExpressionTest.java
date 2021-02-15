package model;

import model.gates.AndGate;
import model.gates.NotGate;
import model.gates.OrGate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class LogicalExpressionTest {
    LogicalExpression testLogicalExpression;
    CircuitVariable a;
    CircuitVariable b;
    CircuitVariable c;
    CircuitVariable d;
    NotGate notGate;
    AndGate andGate;
    OrGate orGate;

    @BeforeEach
    public void setupTests(){
        testLogicalExpression = new LogicalExpression();
        a = new CircuitVariable();
        b = new CircuitVariable();
        c = new CircuitVariable();
        d = new CircuitVariable();
        notGate = new NotGate();
        andGate = new AndGate();
        orGate = new OrGate();


    }

    @Test
    //generates a logical circuit from an expression
    public void testGenerateLogicalCircuit(){
        testLogicalExpression.setLogicalExpression("((A ∨ B) ∧ C)");
        LogicalCircuit generatedCirc = testLogicalExpression.generateCircuit();
        LogicalCircuit comparitorCirc = new LogicalCircuit();
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
    //generates a logical circuit from an expression with a negated sub-expression
    public void testGenerateLogicalCircuitWithNegatedSubExp(){
        testLogicalExpression.setLogicalExpression("((~(A ∨ B) ∨ C) ∧ D)");
        LogicalCircuit generatedCirc = testLogicalExpression.generateCircuit();
        LogicalCircuit comparitorCirc = new LogicalCircuit();
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
    //generates a logical circuit from an expression that uses a variable more than once
    public void testGenerateLogicalCircuitWithDuplicateVars(){
        testLogicalExpression.setLogicalExpression("(~(A ∧ B) ∨ A)");
        LogicalCircuit generatedCirc = testLogicalExpression.generateCircuit();
        LogicalCircuit comparitorCirc = new LogicalCircuit();
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
    //generates a logical circuit from an expression with a negated variable
    public void testGenerateLogicalCircuitWithNegatedVars(){
        testLogicalExpression.setLogicalExpression("((A ∧ B) ∨ ~A)");
        LogicalCircuit generatedCirc = testLogicalExpression.generateCircuit();
        LogicalCircuit comparitorCirc = new LogicalCircuit();
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

