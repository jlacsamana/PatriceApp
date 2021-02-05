package model;

//represents a Logical expression; provides a means to generate an equivalent logical circuit
public class LogicalExpression {
    protected String logicalExpressionStr;

    //EFFECTS: creates a new blank logical expression
    public LogicalExpression() {

    }

    //REQUIRES: That the logical expression be a valid one, adherent to the normal rules and conventions of
    // logical expressions
    //EFFECTS: creates a new Logical Circuit that is analogous to the expression stored by this
    public LogicalCircuit generateCircuit() {
        LogicalCircuit newCircuit = new LogicalCircuit();

        return newCircuit;
    }

    //MODIFIES: this
    //EFFECTS: sets this' logical expression
    public void setLogicalExpression(String logSentence) {
        logicalExpressionStr = logSentence;
    }

    //EFFECTS: gets this' logical expression
    public String getLogicalExpression() {
        return logicalExpressionStr;
    }

}
