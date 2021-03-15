package ui.gui.popupdialogues;

import ui.gui.PatriceGuiMainMenu;
import ui.gui.submenus.PatriceGuiWorkSpace;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//a popup prompt that allows user to create a new workspace
public class CreateNewWorkSpace extends Dialogue {
    JButton enter;
    JButton cancel;
    JTextField nameEntryField;

    //EFFECTS: creates a propup dialogue prompting the user to enter a new name for a workspace
    //creates has a create and cancel button, and a text field
    public CreateNewWorkSpace(PatriceGuiMainMenu parent) {
        super(parent);
        setPreferredSize(new Dimension(400, 200));

        centrePanel();
        pack();

        add(container);
        container.setLayout(new BorderLayout());
        renderCreationPopupElements();
        assignButtonBehaviors();

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: renders the popup dialogue ui elements, create and cancel button, and a text field
    public void renderCreationPopupElements() {
        enter = new JButton("Enter");
        enter.setSize(200, 50);
        cancel = new JButton("Cancel");
        cancel.setSize(200, 50);
        nameEntryField = new JTextField("Enter a name...");

        JPanel subContainer = new JPanel();
        subContainer.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipadx = 130;
        gbc.ipady = 15;

        subContainer.add(enter, gbc);
        subContainer.add(cancel, gbc);
        container.add(nameEntryField, BorderLayout.CENTER);
        container.add(subContainer,BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECTS: assigns behaviors to buttons
    public void assignButtonBehaviors() {
        enter.addActionListener(e -> enterNewWorkSpaceName());
        cancel.addActionListener(e -> cancelWorkspaceCreation());
    }



    //MODIFIES: parent
    //EFFECTS: tries to make a new workspace with the current name in the field, does nothing if
    //there is already another with the same name. Also closes dialogue after
    public void enterNewWorkSpaceName() {
        for (PatriceGuiWorkSpace workSpace : parentMenu.getLoadedWorkspaces()) {
            if (nameEntryField.getText().equals(workSpace.getWorkSpaceName())) {
                closeDialogue();
            }
        }
        PatriceGuiWorkSpace newWorkSpace = new PatriceGuiWorkSpace(nameEntryField.getText());
        parentMenu.addToWorkspaces(newWorkSpace);
        parentMenu.moveToFront(newWorkSpace);
        closeDialogue();
    }

    //EFFECTS: closes this pop-up dialogue
    public void cancelWorkspaceCreation() {
        closeDialogue();
    }

}
