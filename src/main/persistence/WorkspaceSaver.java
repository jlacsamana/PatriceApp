package persistence;

import model.CircuitComponent;
import model.CircuitVariable;
import model.LogicalCircuit;
import model.gates.BinaryCircuitGate;
import model.gates.CircuitGate;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.PatriceWorkspace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static model.CircuitComponent.ComponentTypeIdentifier.*;

//represents a system that saves a PATRICE workspace to the local file system as a json file
//code based on and partially derived from:
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class WorkspaceSaver {
    private static final int TAB = 4;
    private PrintWriter writer;

    // MODIFIES: this
    // tries to open the file at the specified outputDestination
    private void open(String outputDestination) throws FileNotFoundException {
        writer = new PrintWriter(new File(outputDestination));
    }

    // MODIFIES: this
    // EFFECTS: closes the current writer object
    private void close() {
        writer.close();
    }

    // MODIFIES: toExport
    // EFFECTS: tries to convert the given PatriceWorkspace's data into a serializable JSON onject, throws
    // WorkspaceToJsonTranslateError if it can't
    private JSONObject convertWorkspaceToJson(PatriceWorkspace toConvert) {
        JSONObject toExport = new JSONObject();

        toExport.put("workspace-name", toConvert.getWorkspaceName());
        toExport.put("expression", toConvert.getLocalExpression().getLogicalExpression());
        toExport.put("circuit", convertCircuitArrayToJsonArray(toConvert.getLocalCircuit()));

        return toExport;
    }


    //EFFECTS: converts the data in circToConvert to a Json Array and returns it
    private JSONArray convertCircuitArrayToJsonArray(LogicalCircuit circToConvert) {
        JSONArray converted = new JSONArray();
        for (CircuitComponent c: circToConvert.getCircuitComponents()) {
            converted.put(convertCircuitPartToJson(c));
        }
        return converted;
    }

    //EFFECTS: converts the data in a circuit component, partToConvert to Json and returns it
    private JSONObject convertCircuitPartToJson(CircuitComponent partToConvert) {
        JSONObject converted = new JSONObject();
        addCircCompDataToJson(partToConvert, converted);
        converted.put("circuit_name", partToConvert.getComponentName());
        return converted;
    }

    //MODIFIES: converted
    //EFFECTS: add circuit type-specific data to converted, a json representation of a circuit component
    private void addCircCompDataToJson(CircuitComponent partToConvert, JSONObject converted) {
        switch (partToConvert.getComponentTypeIdentifier()) {
            case VARIABLE:
                converted.put("circuit_type", "VARIABLE");
                addVarIDToJson((CircuitVariable) partToConvert, converted);
            case NOT:
                converted.put("circuit_type", "NOT");
                converted.put("input_1", ((CircuitGate) partToConvert).getInputConnection1().getComponentName());
            case OUTPUT:
                converted.put("circuit_type", "OUTPUT");
                converted.put("input_1", ((CircuitGate) partToConvert).getInputConnection1().getComponentName());
            case AND:
                converted.put("circuit_type", "AND");
                converted.put("input_1", ((CircuitGate) partToConvert).getInputConnection1().getComponentName());
                converted.put("input_2", ((BinaryCircuitGate) partToConvert).getInputConnection2().getComponentName());
            case OR:
                converted.put("circuit_type", "OR");
                converted.put("input_1", ((CircuitGate) partToConvert).getInputConnection1().getComponentName());
                converted.put("input_2", ((BinaryCircuitGate) partToConvert).getInputConnection2().getComponentName());
        }
    }

    //MODIFIES: converted
    //EFFECTS: adds variable ID data to converted, a json representation of a circuit variable
    private void addVarIDToJson(CircuitVariable partToConvert, JSONObject converted) {
        switch (partToConvert.getVarID()) {
            case A:
                converted.put("circuit_variable_id", "A");
            case B:
                converted.put("circuit_variable_id", "B");
            case C:
                converted.put("circuit_variable_id", "C");
            case D:
                converted.put("circuit_variable_id", "D");
        }
    }


    // MODIFIES: this
    // EFFECTS: writes string to the file associated with the current writer
    private void save(String toWrite) {
        writer.print(toWrite);
    }

    // MODIFIES: this
    //EFFECTS: opens (or creates a file) at the specified file path and saves the data of the given PATRICE workspace
    //to it. Closes when finished. If it fails to do so at any step, returns a message informing of
    //where the failure occurred. If successful, returns a message notifying of the success.
    public String saveToFile(String outputDestination, PatriceWorkspace workspaceToSave) {
        try {
            open("./data/" + outputDestination + ".json");
        } catch (FileNotFoundException e) {
            return "file was not found";
        }

        JSONObject objectToSave;
        objectToSave = convertWorkspaceToJson(workspaceToSave);
        save(objectToSave.toString(TAB));
        close();
        return "successfully saved!";
    }



}
