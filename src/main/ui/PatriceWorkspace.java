package ui;

import model.LogicalCircuit;
import model.LogicalExpression;

//an interactive workspace where the user can manipulate expressions and circuits
// and convert them to one another
//provides a way to work on and switch between multiple workspaces,
//TODO: implement support for the above in the future (maybe)
public class PatriceWorkspace {
    LogicalCircuit localCircuit;
    LogicalExpression localExpression;

    //EFFECTS: creates a new workspace with an empty logical expression and a blank logical
    //expression
    public PatriceWorkspace() {
        localCircuit = new LogicalCircuit();
        localExpression = new LogicalExpression();
    }


}
