package ui.gui.expressiongui;

import ui.gui.circuitgui.InteractableCircuitArea;
import ui.gui.submenus.PatriceGuiWorkSpace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// displays the local logical expression and conversion buttons
public class LogicalExpressionField extends JPanel {
    JTextField expressionField;
    PatriceGuiWorkSpace parent;

    //EFFECTS: creates a new logical expression view
    public LogicalExpressionField(PatriceGuiWorkSpace parent) {
        this.parent = parent;
        setLayout(null);
        setBounds(400,800,1520,280);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.darkGray));
        renderExpField();
        renderConversionButtons();
    }

    //EFFECTS: renders the text field to display the expression
    public void renderExpField() {
        expressionField = new JTextField();
        expressionField.setBounds(500,75, 350, 60);
        expressionField.setFont(new Font(expressionField.getFont().getName(), Font.BOLD, 20));

        add(expressionField);
    }

    //EFFECTS: renders conversion buttons & applies behaviors
    public void renderConversionButtons() {
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridBagLayout());
        JButton circuitToExp = new JButton("Circuit To Expression");
        JButton expToCircuit = new JButton("Expression To Circuit");
        buttonContainer.add(circuitToExp, generateGBC());
        buttonContainer.add(expToCircuit, generateGBC());
        buttonContainer.setBounds(1350, 60, 150, 100);
        add(buttonContainer);
        circuitToExp.addActionListener(e -> {
            try {
                ((InteractableCircuitArea) parent.getInteractiveCircuitSpace()).setLocalExpression(
                        ((InteractableCircuitArea)
                        parent.getInteractiveCircuitSpace()).getLocalCircuit().generateExpression());
                expressionField.setText(
                        (((InteractableCircuitArea)
                        parent.getInteractiveCircuitSpace()).getLocalExpression().getLogicalExpression()
                    ));

            } catch (Exception x) {
                System.out.println("failed conversion attempt");
            }
        });
        expToCircuit.addActionListener(e -> {
            //todo implement
        });
    }

    //EFFECTS: returns GridBagConstraints for a vertical layout
    //Borrowed from:
    //https://codereview.stackexchange.com/questions/106043/vertical-jbutton-allignment
    public GridBagConstraints generateGBC() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.ipadx = 25;
        constraints.ipady = 35;
        return constraints;
    }


}
