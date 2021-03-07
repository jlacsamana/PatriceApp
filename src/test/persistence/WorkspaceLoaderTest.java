package persistence;

import model.CircuitComponent;
import model.CircuitVariable;
import model.LogicalCircuit;
import model.gates.AndGate;
import model.gates.NotGate;
import model.gates.OrGate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ui.PatriceApplication;
import ui.PatriceWorkspace;

import static org.junit.jupiter.api.Assertions.*;

public class WorkspaceLoaderTest {
    PatriceApplication testPatriceApp;
    WorkspaceLoader testLoader;

    @BeforeEach
    public void setup(){
        testPatriceApp = new PatriceApplication("");
        testLoader = testPatriceApp.getWorkspaceLoader();

    }

    @Test
    //tries to load from a file that doesn't exist
    public void testLoadWorkspaceFromFileInvalidPath(){
        String loadState = testLoader.loadWorkSpaceFromFile("thicc thanos");
        assertEquals("could not read file", loadState);

    }

    @Test
    //tries to load a workspace from a file that has a circuit component detailing a circuit component of a type
    //that doesn't exist
    public void testLoadWorkspaceFromFileInvalidCircComp(){
        String loadState = testLoader.loadWorkSpaceFromFile("nonexistent-circuit-comp-type-workspace");
        assertEquals("something went wrong during part translation", loadState);
    }

    @Test
    //loads a workspace from a file that has no issues and should load properly
    public void testLoadWorkspaceFromFileValidFile(){
        String loadState = testLoader.loadWorkSpaceFromFile("test-workspace");
        assertEquals("big circuit successfully loaded!", loadState);

        PatriceWorkspace loadedWorkSpace = testPatriceApp.getWorkspaceByName("big circuit");

        assertEquals("((A ∧ B) ∨ (~C ∨ D))" ,loadedWorkSpace.getLocalExpression().getLogicalExpression());

        LogicalCircuit comparitorCircuit = new LogicalCircuit();
        CircuitVariable a = new CircuitVariable();
        CircuitVariable b = new CircuitVariable();
        CircuitVariable c = new CircuitVariable();
        CircuitVariable d = new CircuitVariable();
        AndGate and = new AndGate();
        OrGate orGate = new OrGate();
        OrGate orGate2 = new OrGate();
        NotGate not = new NotGate();

        comparitorCircuit.addCircuitPart(a);
        comparitorCircuit.addCircuitPart(b);
        comparitorCircuit.addCircuitPart(c);
        comparitorCircuit.addCircuitPart(d);
        comparitorCircuit.addCircuitPart(and);
        comparitorCircuit.addCircuitPart(orGate);
        comparitorCircuit.addCircuitPart(orGate2);
        comparitorCircuit.addCircuitPart(not);

        comparitorCircuit.changeOutPutConnection(orGate, comparitorCircuit.getHead(), 1);
        comparitorCircuit.changeOutPutConnection(and, orGate, 1);
        comparitorCircuit.changeOutPutConnection(orGate2, orGate, 2);
        comparitorCircuit.changeOutPutConnection(a, and, 1);
        comparitorCircuit.changeOutPutConnection(b, and, 2);
        comparitorCircuit.changeOutPutConnection(not, orGate2, 1);
        comparitorCircuit.changeOutPutConnection(d, orGate2, 2);
        comparitorCircuit.changeOutPutConnection(c, not, 1);


        assertEquals(comparitorCircuit.generateExpression().getLogicalExpression(),
                loadedWorkSpace.getLocalCircuit().generateExpression().getLogicalExpression());
    }

    @Test
    //loads a workspace from a file that has no issues and should load properly; empty file
    public void testLoadWorkspaceFromFileValidEmptyFile(){
        String loadState = testLoader.loadWorkSpaceFromFile("template-workspace");
        assertEquals("TEST successfully loaded!", loadState);
        //todo

    }



}
