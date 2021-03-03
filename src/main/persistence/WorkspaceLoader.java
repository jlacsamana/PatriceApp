package persistence;

import org.json.JSONObject;
import ui.Main;
import ui.PatriceWorkspace;

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

    private class CantParseIntoWorkRoomError extends Exception {

    }

    // EFFECTS: reads source file as string and returns it
    // borrowed from:
    ////https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS reads the given json save data and generates a workspace from it. Returns the generated workspace.
    // Throws an CantParseIntoWorkRoomError error if it fails to
    private PatriceWorkspace buildWorkspaceFromJsonData(JSONObject jsonSaveData) throws CantParseIntoWorkRoomError {
        PatriceWorkspace constructedWorkspace = new PatriceWorkspace(jsonSaveData.getString("workspace-name"));

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
    //EFFECT: adds the parts described in the json array to the circuit in  constructedWorkspace
    private void addPartsToCircInWorkspace(PatriceWorkspace constructedWorkspace, JSONArray circuit) {
        //todo: implement this
    }

    //MODIFIES: constructedWorkspace
    //EFFECT: adds the connections described in the json array to the circuit in  constructedWorkspace
    private void addConnectionsToWorkSpace(PatriceWorkspace constructedWorkspace, JSONArray circuit) {
        //todo: implement this
    }


    // EFFECTS: tries to read the details of a PATRICE workspace from the given path and adds it to the list
    // of active workspaces. If successful, returns a message notifying of the success
    // If it fails to load from a file, returns one of several messages corresponding to how it failed.
    public String loadWorkSpaceFromFile(String filePath) {
        String rawData;
        try {
            rawData = readFile(filePath);
        } catch (IOException e) {
            return "could not read file";
        }

        PatriceWorkspace workspaceToAdd;
        try {
            workspaceToAdd = buildWorkspaceFromJsonData(new JSONObject(rawData));
        } catch (CantParseIntoWorkRoomError cantParseIntoWorkRoomError) {
            return "could not parse data into a workspace";
        }

        Main.getActiveInstance().addWorkSpace(workspaceToAdd);

        return "successfully loaded!";
    }


}
