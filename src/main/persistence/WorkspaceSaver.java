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
import java.util.Collection;

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

        toExport.put("circuit", convertCircuitArrayToJsonArray(toConvert.getLocalCircuit()));
        toExport.put("expression", toConvert.getLocalExpression().getLogicalExpression());
        toExport.put("workspace-name", toConvert.getWorkspaceName());


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
        if (partToConvert.getComponentTypeIdentifier() == VARIABLE) {
            converted.put("circuit_type", "VARIABLE");
            addVarIDToJson((CircuitVariable) partToConvert, converted);
        } else if (partToConvert.getComponentTypeIdentifier() == NOT) {
            converted.put("circuit_type", "NOT");
        } else if (partToConvert.getComponentTypeIdentifier() == OUTPUT) {
            converted.put("circuit_type", "OUTPUT");
        } else if (partToConvert.getComponentTypeIdentifier() == AND) {
            converted.put("circuit_type", "AND");
        } else if (partToConvert.getComponentTypeIdentifier() == OR) {
            converted.put("circuit_type", "OR");
        }

        assignConnections(partToConvert, converted);

    }

    //MODIFIES: converted
    //EFFECT: records connections to inputs, if there are any, in converted
    private void assignConnections(CircuitComponent partToConvert, JSONObject converted) {
        if (partToConvert instanceof CircuitGate) {
            if (((CircuitGate) partToConvert).getInputConnection1() != null) {
                converted.put("input_1", ((CircuitGate) partToConvert).getInputConnection1().getComponentName());
            } else {
                converted.put("input_1", "NONE");
            }

        }

        if (partToConvert instanceof BinaryCircuitGate) {
            if (((BinaryCircuitGate) partToConvert).getInputConnection2() != null) {
                converted.put("input_2", ((BinaryCircuitGate) partToConvert).getInputConnection2().getComponentName());
            } else {
                converted.put("input_2", "NONE");
            }
        }
    }

    //MODIFIES: converted
    //EFFECTS: adds variable ID data to converted, a json representation of a circuit variable
    private void addVarIDToJson(CircuitVariable partToConvert, JSONObject converted) {
        //todo
        if (partToConvert.getVarID() == LogicalCircuit.VariableIdentifier.A) {
            converted.put("circuit_variable_id", "A");
        } else if (partToConvert.getVarID() == LogicalCircuit.VariableIdentifier.B) {
            converted.put("circuit_variable_id", "B");
        } else if (partToConvert.getVarID() == LogicalCircuit.VariableIdentifier.C) {
            converted.put("circuit_variable_id", "C");
        } else if (partToConvert.getVarID() == LogicalCircuit.VariableIdentifier.D) {
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
            return "specified path to save to is invalid";
        }

        JSONObject objectToSave = convertWorkspaceToJson(workspaceToSave);
        save(objectToSave.toString(TAB));
        close();
        return "successfully saved!";
    }



}
