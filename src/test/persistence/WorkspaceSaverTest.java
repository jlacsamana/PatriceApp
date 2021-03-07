package persistence;

import model.CircuitVariable;
import model.LogicalCircuit;
import model.LogicalExpression;
import model.gates.AndGate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ui.PatriceWorkspace;

import static org.junit.jupiter.api.Assertions.*;

public class WorkspaceSaverTest {
    PatriceWorkspace testWorkSpace;
    WorkspaceSaver testWorkSpaceSaver;

    @BeforeEach
    public void setup(){
        testWorkSpace = new PatriceWorkspace("test", false);
        testWorkSpaceSaver = testWorkSpace.getWorkspaceSaver();

    }

    @Test
    //tries to save a workspace file to an invalid path
    public void testSaveToFileInvalidPath() {

        String saveResult = testWorkSpaceSaver.saveToFile("thicc\0thanos", testWorkSpace);
        assertEquals("specified path to save to is invalid", saveResult);
    }

    @Test
    //saves a file to a valid path normally
    public void testSaveToFileNormal() {
        LogicalCircuit testCirc;
        LogicalExpression testExp = new LogicalExpression();
        testExp.setLogicalExpression("((A ∧ B) ∨ (~C ∧ D))");
        testCirc = testExp.generateCircuit();



        testWorkSpace.debugSetCircuit(testCirc);
        testWorkSpace.debugSetExpression(testExp);


        String saveResult = testWorkSpaceSaver.saveToFile("test-writeto-nonempty-workshop" ,
                testWorkSpace);
        assertEquals("successfully saved!", saveResult);

    }

    @Test
    //saves a file to a valid path normally; saves an empty workspace
    public void testSaveToFileNormalEmpty() {
        String saveResult = testWorkSpaceSaver.saveToFile("test-writeto-workshop" , testWorkSpace);
        assertEquals("successfully saved!", saveResult);
        //todo

    }

    @Test
    //saves a file to a valid path normally; saves a workspace with a circuit with disconnected parts
    public void testSaveToFileNormalDisconnectedParts() {
        AndGate and = new AndGate();
        LogicalCircuit circuit = new LogicalCircuit();
        circuit.addCircuitPart(and);
        testWorkSpace.debugSetCircuit(circuit);

        String saveResult = testWorkSpaceSaver.saveToFile("test-writeto-workshop" , testWorkSpace);
        assertEquals("successfully saved!", saveResult);
        //todo

    }








}
