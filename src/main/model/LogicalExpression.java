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

    }

    //REQUIRES: That the logical expression be a valid one, adherent to the normal rules and conventions of
    // logical expressions
    //EFFECTS: creates a new Logical Circuit that is analogous to the expression stored by this
    public LogicalCircuit generateCircuit() {
        LogicalCircuit newCircuit = new LogicalCircuit();
        appendLogicalCircuit(newCircuit, this.logicalExpressionStr, newCircuit.getHead());
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
            }

            determineConnection(circToEdit, connectTo, varToConnect);


        } else if (logicalExp.charAt(0) == '~') {
            NotGate notGate = new NotGate();
            circToEdit.addCircuitPart(notGate);
            determineConnection(circToEdit, connectTo, notGate);
            int expressionLength = logicalExp.toCharArray().length;
            appendLogicalCircuit(circToEdit, logicalExp.substring(1,expressionLength), notGate);
        } else {
            logicalExp =  prepareSubExpression(logicalExp);
            char[] expChar = logicalExp.toCharArray();
            String subExpression1 = "";
            String subExpression2 = "";
            char binaryOperator = '☭';

            for (int i = 0; i < expChar.length; i++) {
                if (expChar[i] == '∨' || expChar[i] == '∧') {
                    String firstHalf = logicalExp.substring(0, i - 1);
                    String secondHalf = logicalExp.substring(i + 2);
                    if (isBalanced(firstHalf) && isBalanced(secondHalf)) {
                        subExpression1 = firstHalf;
                        subExpression2 = secondHalf;
                        binaryOperator = expChar[i];
                        break;
                    }
                }
            }

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

            circToEdit.addCircuitPart(partToAdd);
            determineConnection(circToEdit, connectTo, partToAdd);
            appendLogicalCircuit(circToEdit, subExpression1, partToAdd);
            appendLogicalCircuit(circToEdit, subExpression2, partToAdd);
        }
    }

    //MODIFIES: cirdToEdit, partToConnect, connectTo
    //EFFECT: inside cirdToEdit, determines which input of connectTo to connect partToConnect.
    //input is chosen based on vacancy and the type of gate partToConnect is
    private void determineConnection(LogicalCircuit circToEdit,
                                     CircuitComponent connectTo,
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

        for (int i = 0; i < toEvalChar.length; i++) {
            if (toEvalChar[i] == '(') {
                openingBrackets.add(toEvalChar[i]);
            } else if (toEvalChar[i] == ')') {
                closingBrackets.add(toEvalChar[i]);
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
