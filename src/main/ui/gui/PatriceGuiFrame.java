package ui.gui;


import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;


//The PATRICE application's containing frame
// based on and borrowing from:
//https://github.students.cs.ubc.ca/CPSC210-2020W-T2/lab6_w9v2b.git
public class PatriceGuiFrame extends JFrame {
    private Dimension viewPort;



    // EFFECTS: initialises panel view with width WIDTH and height HEIGHT
    public PatriceGuiFrame() {
        viewPort = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(viewPort.width, viewPort.height));
        setBackground(Color.BLACK);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        centrePanel();

        add(new PatriceGuiMainMenu(viewPort.width, viewPort.height));
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: centres the viewport on the screen
    // borrowed from:
    //https://github.students.cs.ubc.ca/CPSC210-2020W-T2/lab6_w9v2b.git
    public void centrePanel() {
        setLocation((viewPort.width - getWidth()) / 2, (viewPort.height - getHeight()) / 2);
    }









}
