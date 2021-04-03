package ui.gui.submenus;

import model.LogicalCircuit;
import model.LogicalExpression;
import persistence.WorkspaceLoader;
import ui.cli.PatriceApplication;
import ui.cli.PatriceWorkspace;
import ui.gui.PatriceGuiMainMenu;
import ui.gui.circuitgui.InteractableCircuitArea;
import ui.gui.expressiongui.LogicalExpressionField;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//the file loading system prompt, but closable
public class SaveFileLoader extends ClosableMenuItem implements ActionListener {
    JFileChooser selector;
    PatriceGuiMainMenu parent;
    WorkspaceLoader workspaceLoader;


    //creates a new load file dialogue tab, and sets its parent to the active GUi main menu, and creates a new
    //workspace loader
    //file type restricting based on:
    //https://stackoverflow.com/questions/22705189/disable-all-files-when-using-filenameextensionfilter-in-java
    public SaveFileLoader(PatriceGuiMainMenu parent) {
        super();
        selector = new JFileChooser();
        selector.setFileFilter(new FileNameExtensionFilter("json","*.json"));
        container.add(selector);
        selector.addActionListener(this::actionPerformed);
        this.parent = parent;
    }

    @Override
    //MODIFIES: this
    //EFFECTS: listens for actions done on the active file selector
    //tries to laod the currently selected file when the approve selection button is clicked
    //and closes the tab when cancel selection is clicked
    //Derived from:
    //https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html &&
    //https://stackoverflow.com/questions/17034282/jfilechooser-regarding-the-open-and-cancel-buttons-java
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(javax.swing.JFileChooser.APPROVE_SELECTION)) {
            String filePath = selector.getSelectedFile().getAbsolutePath();
            PatriceApplication temp = new PatriceApplication("debug");
            workspaceLoader = new WorkspaceLoader(temp);
            workspaceLoader.loadWorkSpaceFromFile(filePath, true);
            PatriceWorkspace dummy = temp.getOpenWorkspaces().get(0);
            LogicalCircuit loadedCirc = dummy.getLocalCircuit();
            LogicalExpression loadedExp = dummy.getLocalExpression();
            PatriceGuiWorkSpace newWrkSpc = new PatriceGuiWorkSpace(dummy.getWorkspaceName());
            ((InteractableCircuitArea) newWrkSpc.getInteractiveCircuitSpace()).setLocalCircuit(loadedCirc);
            ((InteractableCircuitArea) newWrkSpc.getInteractiveCircuitSpace()).generateVisualForPreMadeCirc();
            ((InteractableCircuitArea) newWrkSpc.getInteractiveCircuitSpace()).setLocalExpression(loadedExp);
            ((LogicalExpressionField) newWrkSpc.getLogicalExpressionField()).getExpressionField().setText(
                    loadedExp.getLogicalExpression()
            );
            parent.addToWorkspaces(newWrkSpc);
            parent.closeDialogue();
            parent.moveToFront(newWrkSpc);
            closeThis();
        } else if (e.getActionCommand().equals(javax.swing.JFileChooser.CANCEL_SELECTION)) {
            closeThis();
        }

    }
}
