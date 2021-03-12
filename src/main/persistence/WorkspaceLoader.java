package persistence;

import model.CircuitComponent;
import model.CircuitVariable;
import model.LogicalCircuit;
import model.gates.*;
import org.json.JSONObject;
import ui.cli.PatriceApplication;
import ui.cli.PatriceWorkspace;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//represents a system that loads a PATRICE workstation from a given save file (in json format)
//code based on and partially derived from:
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class WorkspaceLoader {
    PatriceApplication activeAppInstance;

    private static class CantTranslateJsonToPart extends Exception {

    }


    //EFFECTS: creates a new WorkspaceLoader and sets its PatriceApp instance
    public WorkspaceLoader(PatriceApplication patriceApp) {
        activeAppInstance = patriceApp;
    }

    // EFFECTS: reads source file as string and returns it
    // borrowed from:
    ////https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    //EFFECTS reads the given json save data and generates a workspace from it. Returns the generated workspace.
    // Throws an CantParseIntoWorkRoomError error if it fails to
    private PatriceWorkspace buildWorkspaceFromJsonData(JSONObject jsonSaveData) throws
            CantTranslateJsonToPart {
        PatriceWorkspace constructedWorkspace =
                new PatriceWorkspace(jsonSaveData.getString("workspace-name"), false);



        addExpressionToWorkSpace(constructedWorkspace, jsonSaveData.getString("expression"));
        addPartsToCircInWorkspace(constructedWorkspace, jsonSaveData.getJSONArray("circuit"));
        addConnectionsToWorkSpace(constructedWorkspace, jsonSaveData.getJSONArray("circuit"));


        return constructedWorkspace;
    }

    //MODIFIES: constructedWorkspace
    //EFFECT: sets the expression of the constructedWorkspace to expression
    private void addExpressionToWorkSpace(PatriceWorkspace constructedWorkspace, String expression) {
        constructedWorkspace.getLocalExpression().setLogicalExpression(expression);
    }

    //MODIFIES: constructedWorkspace
    //EFFECT: adds the parts described in the json array to the circuit in  constructedWorkspace.
    //If at least one of the parts can't be added, throws CantTranslateJsonToPart
    private void addPartsToCircInWorkspace(PatriceWorkspace constructedWorkspace, JSONArray circuit)
            throws CantTranslateJsonToPart {
        for (Object part: circuit) {
            CircuitComponent partToAdd = createComponent((JSONObject) part);
            if (partToAdd != null) {
                constructedWorkspace.getLocalCircuit().addCircuitPart(partToAdd);
            }
        }
    }

    //EFFECTS: returns a new circuit part based on the given string that corresponds to the
    //part specified by the data in the given json object.
    //If a corresponding part specification can't be found, throws CantTranslateJsonToPart
    private CircuitComponent createComponent(JSONObject partToTranslate) throws CantTranslateJsonToPart {
        CircuitComponent partToCreate;
        String circuitType = partToTranslate.getString("circuit_type");
        if ("OUTPUT".equals(circuitType)) {
            return null;
        } else if ("VARIABLE".equals(circuitType)) {
            partToCreate = new CircuitVariable();
        } else if ("NOT".equals(circuitType)) {
            partToCreate = new NotGate();
        } else if ("OR".equals(circuitType)) {
            partToCreate = new OrGate();
        } else if ("AND".equals(circuitType)) {
            partToCreate = new AndGate();
        } else {
            throw new CantTranslateJsonToPart();
        }
        partToCreate.setName(partToTranslate.getString("circuit_name"));
        return partToCreate;
    }

    //MODIFIES: constructedWorkspace
    //EFFECTS: adds the connections described in the json array to the circuit in  constructedWorkspace
    private void addConnectionsToWorkSpace(PatriceWorkspace constructedWorkspace, JSONArray circuit) {
        for (Object part: circuit) {
            JSONObject jsonPart = (JSONObject) part;
            String currentPartName = jsonPart.getString("circuit_name");

            CircuitComponent partToConnect = constructedWorkspace.getLocalCircuit()
                    .getPartInListByName(currentPartName);

            if (partToConnect instanceof CircuitGate) {
                String input1Name = jsonPart.getString("input_1");
                CircuitComponent input1 = constructedWorkspace.getLocalCircuit().getPartInListByName(input1Name);
                connectParts(constructedWorkspace.getLocalCircuit(), input1, partToConnect, 1);
            }

            if (partToConnect instanceof BinaryCircuitGate) {
                String input2Name = jsonPart.getString("input_2");
                CircuitComponent input2 = constructedWorkspace.getLocalCircuit().getPartInListByName(input2Name);
                connectParts(constructedWorkspace.getLocalCircuit(), input2, partToConnect, 2);
            }
        }

    }

    //MODIFIES: circToEdit
    //EFFECTS: connects the first part to the second at the specified input (1 or 2) in the given circuit
    //doesn't connect anything if part1 is null
    public void connectParts(LogicalCircuit circToEdit, CircuitComponent part1, CircuitComponent part2,
                             int inputNumber) {

        if (part1 != null) {
            circToEdit.changeOutPutConnection(part1, part2, inputNumber);
        }
    }


    // EFFECTS: tries to read the details of a PATRICE workspace from the given path and adds it to the list
    // of active workspaces. If successful, returns a message notifying of the success
    // If it fails to load from a file, returns one of several messages corresponding to how it failed.
    public String loadWorkSpaceFromFile(String filePath) {
        String rawData;
        try {
            String fullFilePath = "./data/" + filePath + ".json";
            rawData = readFile(fullFilePath);
        } catch (IOException e) {
            return "could not read file";
        }

        PatriceWorkspace workspaceToAdd;
        JSONObject parsedData = new JSONObject(rawData);
        try {
            workspaceToAdd = buildWorkspaceFromJsonData(parsedData);
        } catch (CantTranslateJsonToPart e) {
            return "something went wrong during part translation";
        }

        activeAppInstance.addWorkSpace(workspaceToAdd);

        return parsedData.getString("workspace-name") + " successfully loaded!";
    }


}
