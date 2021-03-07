package persistence;

import model.CircuitComponent;
import model.CircuitVariable;
import model.LogicalCircuit;
import model.LogicalExpression;
import model.gates.AndGate;
import model.gates.BinaryCircuitGate;
import model.gates.CircuitGate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ui.PatriceApplication;
import ui.PatriceWorkspace;

import static org.junit.jupiter.api.Assertions.*;

public class WorkspaceSaverTest {
    PatriceWorkspace testWorkSpace;
    WorkspaceSaver testWorkSpaceSaver;
    PatriceApplication testPatriceApp;
    WorkspaceLoader testLoader;

    @BeforeEach
    public void setup(){
        testWorkSpace = new PatriceWorkspace("test", false);
        testWorkSpaceSaver = testWorkSpace.getWorkspaceSaver();
        testPatriceApp = new PatriceApplication("");
        testLoader = testPatriceApp.getWorkspaceLoader();

    }

    @Test
    //tries to save a workspace file to an invalid path
    public void testSaveToFileInvalidPath() {

        String saveResult = testWorkSpaceSaver.saveToFile("thicc\0thanos", testWorkSpace);
        assertEquals("specified path to save to is invalid", saveResult);
    }

    @Test
    //tries to save a worksapce with a circuit with a part that doesn't correspond to any of the allowed types
    public void testSaveToFileInvalidPart() {
        LogicalCircuit invalidPartCirc = new LogicalCircuit();
        AndGate invalidAND = new AndGate();
        invalidPartCirc.addCircuitPart(invalidAND);
        invalidAND.setComponentTypeIdentifier(CircuitComponent.ComponentTypeIdentifier.NONE);
        testWorkSpace.debugSetCircuit(invalidPartCirc);

        String saveResult = testWorkSpaceSaver.saveToFile("invalid-part-workspace" , testWorkSpace);
        assertEquals("there is a part that can't be saved as data because " +
                "it is not of any of the allowed part types", saveResult);
    }

    @Test
    //tries to save a  worksapce with a variable that has an invalid ID
    public void testSaveToFileInvalidVarID() {
        LogicalCircuit invalidIDCircuit = new LogicalCircuit();
        CircuitVariable a = new CircuitVariable();
        invalidIDCircuit.addCircuitPart(a);
        a.setVarID(LogicalCircuit.VariableIdentifier.NONE);

        testWorkSpace.debugSetCircuit(invalidIDCircuit);

        String saveResult = testWorkSpaceSaver.saveToFile("invalid-ID-workspace" , testWorkSpace);
        assertEquals("there is an illegal variable used in the circuit that was to be translated", saveResult);
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

        testLoader.loadWorkSpaceFromFile("test-writeto-nonempty-workshop");
        PatriceWorkspace loaded = testPatriceApp.getWorkspaceByName("test");
        assertEquals("((A ∧ B) ∨ (~C ∧ D))", loaded.getLocalExpression().getLogicalExpression());
        assertEquals(9, loaded.getLocalCircuit().getCircuitComponents().size());
        for (int i = 1; i < loaded.getLocalCircuit().getCircuitComponents().size(); i++) {
            assertEquals(String.valueOf(i), loaded.getLocalCircuit().getCircuitComponents().get(i).getComponentName());
        }
        assertEquals("OUTPUT", loaded.getLocalCircuit().getCircuitComponents().get(0).getComponentName());
        assertEquals("((A ∧ B) ∨ (~C ∧ D))", loaded.getLocalExpression().getLogicalExpression());


    }

    @Test
    //saves a file to a valid path normally; saves an empty workspace
    public void testSaveToFileNormalEmpty() {
        String saveResult = testWorkSpaceSaver.saveToFile("test-writeto-workshop" , testWorkSpace);
        assertEquals("successfully saved!", saveResult);

        testLoader.loadWorkSpaceFromFile("test-writeto-workshop");
        PatriceWorkspace loaded = testPatriceApp.getWorkspaceByName("test");

        assertEquals("", loaded.getLocalExpression().getLogicalExpression());
        assertEquals(1, loaded.getLocalCircuit().getCircuitComponents().size());
        assertEquals("OUTPUT", loaded.getLocalCircuit().getCircuitComponents().get(0).getComponentName());
    }

    @Test
    //saves a file to a valid path normally; saves a workspace with a circuit with disconnected parts
    public void testSaveToFileNormalDisconnectedParts() {
        AndGate and = new AndGate();
        LogicalCircuit circuit = new LogicalCircuit();
        circuit.addCircuitPart(and);
        and.setName("AND");
        testWorkSpace.debugSetCircuit(circuit);

        String saveResult = testWorkSpaceSaver.saveToFile(
                "test-writeto-disconnectedparts-workshop" , testWorkSpace);
        assertEquals("successfully saved!", saveResult);

        testLoader.loadWorkSpaceFromFile("test-writeto-disconnectedparts-workshop");
        PatriceWorkspace loaded = testPatriceApp.getWorkspaceByName("test");

        assertEquals("", loaded.getLocalExpression().getLogicalExpression());
        assertEquals(2, loaded.getLocalCircuit().getCircuitComponents().size());
        assertEquals("OUTPUT", loaded.getLocalCircuit().getCircuitComponents().get(0).getComponentName());
        assertNull(((CircuitGate) loaded.getLocalCircuit().getCircuitComponents().get(0)).getInputConnection1());
        assertEquals("AND", loaded.getLocalCircuit().getCircuitComponents().get(1).getComponentName());
        assertNull(((BinaryCircuitGate) loaded.getLocalCircuit().getCircuitComponents().get(1)).getInputConnection1());
        assertNull(((BinaryCircuitGate) loaded.getLocalCircuit().getCircuitComponents().get(1)).getInputConnection2());
    }








}
