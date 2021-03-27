package ui.gui.submenus;


import model.LogicalCircuit;
import model.LogicalExpression;
import ui.gui.submenus.ClosableMenuItem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

//TODO work on on GUI
//TODO implement backend
//Represents the GUI shell for an individual workspace
public class PatriceGuiWorkSpace extends ClosableMenuItem {
    public final String workSpaceName;
    private JPanel interactiveCircuitSpace;

    //EFFECTS: creates a gui workspace, and sets its name
    public PatriceGuiWorkSpace(String newName) {
        super();
        container.setBounds(0, 0, 1920, 1080);
        setBackground(Color.gray);
        container.setLayout(null);
        this.workSpaceName = newName;
        renderLayout();


    }

    //MODIFIES: this
    //EFFECTS: renders the layout for a GUI workspace
    private void renderLayout() {
        renderPrimaryLayout();
        renderCircuitArea();
        renderToolBox();
    }

    //MODIFIES: this
    //EFFECTS: renders the primary UI elements
    private void renderPrimaryLayout() {
        JPanel topbar = new JPanel();
        topbar.setBounds(0,0, 1920, 40);
        topbar.setBackground(Color.lightGray);
        topbar.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
        JLabel title = new JLabel(workSpaceName);
        title.setFont(new Font(title.getName(), Font.BOLD, 20));
        topbar.add(title);
        container.add(topbar);
    }

    //MODIFIES: this
    //EFFECTS: renders the GUI toolbox
    public void renderToolBox() {
        JPanel toolBoxFrame = new JPanel();
        toolBoxFrame.setBounds(0,0, 400, 1080);
        toolBoxFrame.setBackground(Color.white);
        toolBoxFrame.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        toolBoxFrame.setLayout(null);
        JPanel circuitBtns = renderCircPartButtons();
        circuitBtns.setBounds(50, 100, 300, 300);
        toolBoxFrame.add(circuitBtns);
        container.add(toolBoxFrame);
        renderInfoPanel(toolBoxFrame);

    }

    //MODIFIES: this
    //EFFECTS: renders the buttons that let user create circuit parts and returns it
    public JPanel renderCircPartButtons() {
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());

        JButton addAND = new JButton("AND gate");
        JButton addOR = new JButton("OR gate");
        JButton addNOT = new JButton("NOT gate");
        JButton addVar = new JButton("Variable");
        container.add(addAND, generateGBC());
        container.add(addOR, generateGBC());
        container.add(addNOT, generateGBC());
        container.add(addVar, generateGBC());
        return container;
    }

    //MODIFIES: this
    //EFFECTS: sets up the interactive circuit area
    public void renderCircuitArea() {
        interactiveCircuitSpace = new JPanel();
        interactiveCircuitSpace.setBackground(new Color(216, 235, 255));
        interactiveCircuitSpace.setBounds(400, 0, 1520, 1080);

        container.add(interactiveCircuitSpace);
    }

    //MODIFIES: this
    //EFFECTS: renders info panel, for current selected part, and adds the subview to the given JPanel
    public void renderInfoPanel(JPanel parent) {
        JPanel infoPanel = new JPanel();
        infoPanel.setBounds(25,550, 350, 400);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.gray), "Info",
                TitledBorder.CENTER,0));

        parent.add(infoPanel);
    }

    //EFFECTS: returns this workspace's name
    public String getWorkSpaceName() {
        return workSpaceName;
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
