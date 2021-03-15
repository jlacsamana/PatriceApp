package ui.gui.submenus;


import model.LogicalCircuit;
import model.LogicalExpression;
import ui.gui.submenus.ClosableMenuItem;

import javax.swing.*;
import java.awt.*;

//Represents the GUI shell for an individual workspace
public class PatriceGuiWorkSpace extends ClosableMenuItem {
    public final String workSpaceName;


    //creates a gui workspace, and sets its name
    public PatriceGuiWorkSpace(String newName) {
        super();
        container.setBounds(0, 0, 1920, 1080);
        setBackground(Color.GRAY);

        this.workSpaceName = newName;

    }




    //EFFECTS: returns this workspace's name
    public String getWorkSpaceName() {
        return workSpaceName;
    }
}
