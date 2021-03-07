package model;

import model.gates.AndGate;
import model.gates.BinaryCircuitGate;
import model.gates.NotGate;
import model.gates.OrGate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;

//represents a Logical expression; provides a means to generate an equivalent logical circuit
public class LogicalExpression {
    protected String logicalExpressionStr;

    //EFFECTS: creates a new blank logical expression
    public LogicalExpression() {
        logicalExpressionStr = "";
    }

    //REQUIRES: That the logical expression be a valid one, adherent to the normal rules and conventions of
    // logical expressions
    //EFFECTS: creates a new Logical Circuit that is analogous to the expression stored by this
    public LogicalCircuit generateCircuit() {
        LogicalCircuit newCircuit = new LogicalCircuit();
        appendLogicalCircuit(newCircuit, this.logicalExpressionStr, newCircuit.getHead());
        assignOrdinalNames(newCircuit);
        return newCircuit;
    }

    //REQUIRES: circToEdit must not be null and logicalExp must be a valid expression.
    //connectTo must be a circuit component that exists in circToEdit's list of components
    //MODIFIES: circToEdit
    //EFFECTS: adds circuit parts and connections to circToEdit according to logicalExp
    //when parts are added, they will be connected to connectTo
    public void appendLogicalCircuit(LogicalCircuit circToEdit, String logicalExp,
                                               CircuitComponent connectTo) {
        if (Arrays.asList(LogicalCircuit.variableStrs).contains(logicalExp)) {
            CircuitVariable varToConnect = findCircuitVariable(circToEdit, logicalExp);
            determineConnection(circToEdit, connectTo, varToConnect);
        } else if (logicalExp.charAt(0) == '~') {
            NotGate notGate = new NotGate();
            circToEdit.addCircuitPart(notGate);
            determineConnection(circToEdit, connectTo, notGate);
            appendLogicalCircuit(circToEdit, logicalExp.substring(1, logicalExp.toCharArray().length), notGate);
        } else {
            logicalExp =  prepareSubExpression(logicalExp);
            String[] expressionData = getSubExpressions(logicalExp);
            String subExpression1 = expressionData[0];
            String subExpression2 = expressionData[1];
            char binaryOperator = expressionData[2].charAt(0);

            CircuitComponent partToAdd = determineOperatorToAdd(binaryOperator);
            circToEdit.addCircuitPart(partToAdd);
            determineConnection(circToEdit, connectTo, partToAdd);
            appendLogicalCircuit(circToEdit, subExpression1, partToAdd);
            appendLogicalCircuit(circToEdit, subExpression2, partToAdd);
        }
    }

    //MODIFIES: circToEdit
    //EFFECTS: assigns an ordinal name to each of the parts in circToEdit's list of parts
    private void assignOrdinalNames(LogicalCircuit circToEdit) {
        int ordinalCounter = 0;
        for (CircuitComponent c: circToEdit.getCircuitComponents()) {
            c.setName(String.valueOf(ordinalCounter));
            ordinalCounter += 1;
        }
    }

    //REQUIRES: that originalExpression that is a conjunction or disjunction of two terms or variables;
    // It must also be a valid logical expression
    //EFFECTS: returns an array of exactly 3 items, which contains data as follows
    //- index 0: stores the first conjunct/disjunct term/variable
    //- index 1: stores the second conjunct/disjunct term/variable
    //- index 2: stores the binary function symbol between the two generated conjunts/disjuncts
    public String[] getSubExpressions(String originalExpression) {
        char[] expChar = originalExpression.toCharArray();
        String[] expressionData = new String[3];
        for (int i = 0; i < expChar.length; i++) {
            if (expChar[i] == '∨' || expChar[i] == '∧') {
                String firstHalf = originalExpression.substring(0, i - 1);
                String secondHalf = originalExpression.substring(i + 2);

                //only need to test if first half has balanced parentheses, since
                //if this is true, then the other half is also surely balanced
                //under the assumptions made in the REQUIRES class
                if (isBalanced(firstHalf)) {
                    expressionData[0] = firstHalf;
                    expressionData[1] = secondHalf;
                    expressionData[2] = Character.toString(expChar[i]);
                }
            }
        }
        return expressionData;
    }

    //REQUIRES: logicalExp be a single latter, corresponding to one of the possible variables in circToEdit
    //returns a circuit variable corresponding to the given logicalExp if one can be found already in use
    // in circToEdit, otherwise, creates a new Variable using an available variable ID
    private CircuitVariable findCircuitVariable(LogicalCircuit circToEdit, String logicalExp) {
        CircuitVariable varToConnect = null;

        for (CircuitVariable circVar: circToEdit.getCircuitVariables()) {
            if (circVar.getVarID() == LogicalCircuit.charToVarID(logicalExp)) {
                varToConnect = circVar;
                break;
            }
        }

        if (varToConnect == null) {
            varToConnect = new CircuitVariable();
            circToEdit.addCircuitPart(varToConnect);
            //Override variable assignment to allow out of alphabetic order variable declaration
            circToEdit.removeFromUsedVarIDs(varToConnect.getVarID());
            circToEdit.addToUsedVarIDs(LogicalCircuit.charToVarID(logicalExp));
            varToConnect.setVarID(LogicalCircuit.charToVarID(logicalExp));
        }
        return varToConnect;
    }

    //EFFECT: returns a new circuit component based on the given character.
    // a new Or gate if a disjunction char and a new And gate if a conjunction char
    private CircuitComponent determineOperatorToAdd(char binaryOperator) {
        CircuitComponent partToAdd;
        switch (binaryOperator) {
            case '∨':
                // connecting character was a conjunction
                partToAdd = new OrGate();
                break;
            default:
                // connecting character was a disjunction
                partToAdd = new AndGate();
                break;
        }
        return partToAdd;
    }

    //MODIFIES: cirdToEdit, partToConnect, connectTo
    //EFFECT: inside cirdToEdit, determines which input of connectTo to connect partToConnect.
    //input is chosen based on vacancy and the type of gate partToConnect is
    private void determineConnection(LogicalCircuit circToEdit, CircuitComponent connectTo,
                                     CircuitComponent partToConnect) {
        if (connectTo instanceof BinaryCircuitGate) {
            if (((BinaryCircuitGate) connectTo).getInputConnection1() == null) {
                circToEdit.changeOutPutConnection(partToConnect, connectTo, 1);
            } else {
                circToEdit.changeOutPutConnection(partToConnect, connectTo, 2);
            }
        } else {
            circToEdit.changeOutPutConnection(partToConnect, connectTo, 1);
        }
    }

    //EFFECT: if rawSubExpression isnt a single character representing a circuit variable,
    // removes the outermost brackets
    private String prepareSubExpression(String rawSubExpression) {
        return rawSubExpression.substring(1, rawSubExpression.length() - 1);
    }

    //EFFECTS: returns true if toEval has balanced parentheses
    public boolean isBalanced(String toEval) {
        //method derived from
        //https://www.geeksforgeeks.org/check-for-balanced-parentheses-in-an-expression/
        char[] toEvalChar = toEval.toCharArray();
        ArrayList<Character> openingBrackets = new ArrayList<>();
        ArrayList<Character> closingBrackets = new ArrayList<>();

        for (char c : toEvalChar) {
            if (c == '(') {
                openingBrackets.add(c);
            } else if (c == ')') {
                closingBrackets.add(c);
            }
        }

        return openingBrackets.size() == closingBrackets.size();
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
