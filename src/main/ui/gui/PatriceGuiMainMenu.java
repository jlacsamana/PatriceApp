package ui.gui;

import ui.gui.popupdialogues.CreateNewWorkSpace;
import ui.gui.submenus.InformationPanel;
import ui.gui.submenus.MainMenuBG;
import ui.gui.submenus.PatriceGuiWorkSpace;
import ui.gui.submenus.SwitchMenu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//the GUI for the Patrice Application's main menu
public class PatriceGuiMainMenu extends JLayeredPane {
    int height;
    int width;

    JButton infoBtn;
    JButton newWorkspaceBtn;
    JButton switchWorkspaceBtn;
    JButton loadWorkspaceBtn;
    JButton closeBtn;

    JPanel bg;
    JPanel infoPanel;
    JPanel switchMenu;

    ArrayList<PatriceGuiWorkSpace> loadedWorkspaces;

    boolean dialogueCurrentlyOpen = false;



    // EFFECT: launches the Patrice main menu
    PatriceGuiMainMenu(int width, int height) {
        this.height = height;
        this.width = width;

        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(this.width, this.height));
        setBackground(Color.WHITE);

        renderDefaultMenuViews();

        renderMenuButtons();
        assignMenuBehaviors();

        loadedWorkspaces = new ArrayList<>();

    }

    //MODIFIES: this
    //EFFECTS: renders the menu buttons
    // based on and borrowing from:
    //https://stackoverflow.com/questions/42964669/placing-button-panel-in-center-java-swing
    public void renderMenuButtons() {
        GridBagConstraints gbc = generateMenuGBC();
        GridBagConstraints btnGbc = generateMenuBtnGBC();

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

        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        container.add(buttons, gbc);
        add(container, BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECTS: renders some pre-loaded menu elements
    public void renderDefaultMenuViews() {
        bg = new MainMenuBG();
        add(bg);
    }

    //MODIFIES: this
    //EFFECTS: gives the menu buttons behaviors
    public void assignMenuBehaviors() {
        infoBtn.addActionListener(e -> displayInformation());
        newWorkspaceBtn.addActionListener(e -> startNewWorkspace());
        switchWorkspaceBtn.addActionListener(e -> switchWorkSpace());
        loadWorkspaceBtn.addActionListener(e -> loadWorkSpace());
        closeBtn.addActionListener(e -> closeApplication()
        );
    }

    //MODIFIES: this
    //EFFECTS: displays the information on how to use the application
    // does not do anything if a popup dialogue is open
    public void displayInformation() {
        if (!dialogueCurrentlyOpen) {
            closeSubMenuPanels();
            infoPanel = new InformationPanel();
            add(infoPanel);
            moveToFront(infoPanel);
            updateGUI();
        }
    }

    //MODIFIES: this
    //EFFECTS: instantiates a new workspace and adds it to the list of loaded gui workspaces
    // does not do anything if a popup dialogue is open
    public void startNewWorkspace() {
        if (!dialogueCurrentlyOpen) {
            dialogueCurrentlyOpen = true;
            closeSubMenuPanels();
            CreateNewWorkSpace newWorkSpacePrompt = new CreateNewWorkSpace(this);
            updateGUI();
        }
    }

    //MODIFIES: this
    //EFFECTS: switches active workspace to the one specified by the user from the list of loaded ones
    // does not do anything if a popup dialogue is open
    public void switchWorkSpace() {
        if (!dialogueCurrentlyOpen) {
            closeSubMenuPanels();
            switchMenu = new SwitchMenu();
            add(switchMenu);
            moveToFront(switchMenu);
            updateGUI();
        }
    }

    //MODIFIES: this
    //EFFECTS: opens a file system dialogue and prompts user to select a file to load into a workspace
    // does not do anything if a popup dialogue is open
    public void loadWorkSpace() {
        if (!dialogueCurrentlyOpen) {
            dialogueCurrentlyOpen = true;


            updateGUI();
        }
    }

    //EFFECTS: closes the GUI application
    // does not do anything if a popup dialogue is open
    private void closeApplication() {
        if (!dialogueCurrentlyOpen) {
            System.exit(0);
        }
    }

    //MODIFIES: this
    //EFFECTS: returns gridbag constraints for menu buttons
    private GridBagConstraints generateMenuBtnGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipadx = 25;
        gbc.ipady = 15;
        return gbc;
    }

    //MODIFIES: this
    //EFFECTS: returns gridbag constraints for menu
    private GridBagConstraints generateMenuGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    //MODIFIES: this
    //EFFECTS: closes all open submenu panels
    private void closeSubMenuPanels() {
        if (infoPanel != null) {
            remove(infoPanel);
            infoPanel = null;
        }

        if (switchMenu != null) {
            remove(switchMenu);
            switchMenu = null;
        }
    }

    //MODIFIES: this
    //EFFECTS: updates UI
    public void updateGUI() {
        revalidate();
        repaint();
    }

    //MODIFIES: this
    //EFFECTS: closes current dialogue
    public void closeDialogue() {
        dialogueCurrentlyOpen = false;
    }

    //EFFECTS: returns the list of loaded workspaces
    public ArrayList<PatriceGuiWorkSpace> getLoadedWorkspaces() {
        return loadedWorkspaces;
    }

    //MODIFIES: this
    //EFFECTS: adds the given GUI workspace to the list of loaded GUI workspaces, and adds it to the graphics stack
    public void addToWorkspaces(PatriceGuiWorkSpace toAdd) {
        add(toAdd);
        loadedWorkspaces.add(toAdd);
    }

}
