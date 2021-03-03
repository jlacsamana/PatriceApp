package persistence;

import org.json.JSONObject;
import ui.PatriceWorkspace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//represents a system that saves a PATRICE workspace to the local file system as a json file
//code based on and partially derived from:
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class WorkspaceSaver {
    private static final int TAB = 4;
    private PrintWriter writer;

    private class WorkspaceToJsonTranslateError extends Exception {

    }

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
    private JSONObject convertWorkspaceToJson(PatriceWorkspace toConvert) throws WorkspaceToJsonTranslateError {
        JSONObject toExport = new JSONObject();

        //todo fill this in
        return toExport;
    }


    // MODIFIES: this
    // EFFECTS: writes string to the file associated with the current writer
    private void save(String toWrite) {
        writer.print(toWrite);
    }

    // MODIFIES: this
    //EFFECTS: opens (or creates a file) at the specified file path and saves the data of the given PATRICE workspace
    //to it. If it fails to do so at any step, returns
    public String saveToFile(String outputDestination, PatriceWorkspace workspaceToSave) {
        try {
            open(outputDestination);
        } catch (FileNotFoundException e) {
            return "file was not found";
        }

        JSONObject objectToSave;

        try {
            objectToSave = convertWorkspaceToJson(workspaceToSave);
        } catch (WorkspaceToJsonTranslateError e) {
            return "file could not be translated into a save";
        }

        save(objectToSave.toString(TAB));

        close();
        return "successfully saved!";
    }



}
