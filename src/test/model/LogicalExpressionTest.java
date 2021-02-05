package model;

import model.gates.AndGate;
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
        testLogicalExpression.setLogicalExpression("(A ∨ B) ∧ C");
        LogicalCircuit generatedCirc = testLogicalExpression.generateCircuit();
        LogicalCircuit comparitorCirc = new LogicalCircuit();
        CircuitVariable a = new CircuitVariable();
        CircuitVariable b = new CircuitVariable();
        CircuitVariable c = new CircuitVariable();
        OrGate orGate = new OrGate();
        AndGate andGate = new AndGate();

        comparitorCirc.addCircuitPart(a);
        comparitorCirc.addCircuitPart(b);
        comparitorCirc.addCircuitPart(c);
        comparitorCirc.addCircuitPart(orGate);
        comparitorCirc.addCircuitPart(andGate);

        comparitorCirc.changeOutPutConnection(andGate, comparitorCirc.getHead(), 1);
        comparitorCirc.changeOutPutConnection(orGate, andGate, 1);
        comparitorCirc.changeOutPutConnection(c, andGate, 2);
        comparitorCirc.changeOutPutConnection(a, orGate, 1);
        comparitorCirc.changeOutPutConnection(b, orGate, 2);

        assertEquals(comparitorCirc, generatedCirc);
    }


}
