package ui.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//the GUI for the Patrice Application's main menu
public class PatriceGuiMainMenu extends JPanel {
    int height;
    int width;

    JButton infoBtn;
    JButton newWorkspaceBtn;
    JButton switchWorkspaceBtn;
    JButton loadWorkspaceBtn;
    JButton closeBtn;

    ImageIcon infoIcon;
    ImageIcon newWorkspaceIcon;
    ImageIcon switchWorkspaceIcon;
    ImageIcon loadWorkspaceIcon;
    ImageIcon closeIcon;

    // EFFECT: launches the Patrice main menu
    PatriceGuiMainMenu(int width, int height) {
        this.height = height;
        this.width = width;

        setLayout(new GridBagLayout());

        setPreferredSize(new Dimension(this.width, this.height));
        setBackground(Color.WHITE);
        renderMenuButtons();
        assignMenuBehaviors();
    }

    //MODIFIES: this
    //EFFECTS: renders the menu buttons
    // based on and borrowing from:
    //https://stackoverflow.com/questions/42964669/placing-button-panel-in-center-java-swing
    public void renderMenuButtons() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets((int) (this.height / 1.15),0, 0,0);

        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.ipadx = 25;
        btnGbc.ipady = 15;

        JPanel buttons = new JPanel(new GridBagLayout());
        infoBtn = new JButton("Usage Information");
        newWorkspaceBtn = new JButton("New Patrice Workspace");
        switchWorkspaceBtn = new JButton("Switch to a loaded Workspace");
        loadWorkspaceBtn = new JButton("Load a Workspace");
        closeBtn = new JButton("Close Program");

        buttons.add(infoBtn, btnGbc);
        buttons.add(newWorkspaceBtn, btnGbc);
        buttons.add(switchWorkspaceBtn, btnGbc);
        buttons.add(loadWorkspaceBtn, btnGbc);
        buttons.add(closeBtn, btnGbc);

        add(buttons, gbc);
    }

    //MODIFIES: this
    //EFFECTS: gives the menu buttons behaviors
    public void assignMenuBehaviors() {


        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeApplication();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: displays the information on how to use the application
    public void displayInformation() {

    }

    //MODIFIES: this
    //EFFECTS: instantiates a new workspace and adds it to the list of loaded gui workspaces
    public void startNewWorkspace() {

    }

    //MODIFIES: this
    //EFFECTS: switches active workspace to the one specified by the user from the list of loaded ones
    public void switchWorkSpace() {

    }

    //MODIFIES: this
    //EFFECTS: opens a file system dialogue and prompts user to select a file to load into a workspace
    public void loadWorkSpace() {

    }

    //EFFECTS: closes the GUI application
    private void closeApplication() {
        System.exit(0);
    }

}
