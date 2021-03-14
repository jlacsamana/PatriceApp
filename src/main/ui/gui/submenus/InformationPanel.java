package ui.gui.submenus;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

//a subpanel that displays instructions on how to use the program
public class InformationPanel extends ClosableMenuItem {
    JLabel title =  new JLabel("PATRICE");
    JLabel subTitle = new JLabel("Logical Expression & Circuit Translation App: "
                                  +  "Dab on Command Line Interface Edition");

    JLabel expressionSubtitle = new JLabel("Expression Usage Rules");
    JLabel expressionRules = new JLabel("-Can not be sequential\n");
    JLabel expressionRules2 = new JLabel("-Must be well-formed\n");
    JLabel expressionRules3 = new JLabel("-Currently only ~, ∨, and ∧ predicates are supported currently\n");
    JLabel expressionRules4 = new JLabel("-when using predicate symbols in your expressions please use ∨,∧ and ~,\n");
    JLabel expressionRules5 = new JLabel("they are distinct. I.e ∨ is not v, and ∧ is not ^\n");
    JLabel expressionRules6 = new JLabel("-The parser is quite sensitive; outermost brackets are usually omitted,\n");
    JLabel expressionRules7 = new JLabel(" but please include them in your statements\n");
    JLabel expressionRules8 = new JLabel(" i.e '(~A ∧ B) ∨ A' is won't work, but '((~A ∧ B) ∨ A)' will\n");
    JLabel expressionRules9 = new JLabel("-Please only use the available variable names and always in upper case\n");
    JLabel[] expRules = {expressionRules, expressionRules2, expressionRules3, expressionRules4, expressionRules5,
            expressionRules6, expressionRules7, expressionRules8, expressionRules9};

    JLabel circuitSubtitle = new JLabel("Circuit Usage Rules");
    JLabel circuitRules = new JLabel("-Can't be sequential \n");
    JLabel circuitRules2 = new JLabel("-Must be well-formed \n");

    JLabel about = new JLabel("About:");
    JLabel aboutInfo = new JLabel("<html>Using the dark sorcery that is the natural recursion that "
            + "is natural recursion, PATRICE delivers relatively fast and simple expression to circuit"
            + " translation and vice versa, <br> just like the great man that it was named for. (Hopefully)"
            + " coming to a CPSC 121 block near you soon! This version is for people who haven't seen "
            + "the light,<br> or rather more appropriately the darkness of Vim.</html>");

    //creates an info panel
    public InformationPanel() {
        super();
        container.setLayout(null);
        sizeTexts();
        arrangeTexts();
        addTexts();
    }

    //MODIFIES: this
    //EFFECTS: sizes info text objects
    private void sizeTexts() {
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 100));
        subTitle.setFont(new Font(title.getFont().getName(), Font.ITALIC, 30));

        expressionSubtitle.setFont(new Font(title.getFont().getName(), Font.BOLD, 25));
        for (int i = 0; i < expRules.length; i++) {
            expRules[i].setFont(new Font(title.getFont().getName(), Font.PLAIN, 20));
        }

        circuitSubtitle.setFont(new Font(title.getFont().getName(), Font.BOLD, 25));
        circuitRules.setFont(new Font(title.getFont().getName(), Font.PLAIN, 20));
        circuitRules2.setFont(new Font(title.getFont().getName(), Font.PLAIN, 20));

        about.setFont(new Font(title.getFont().getName(), Font.BOLD, 25));
        aboutInfo.setFont(new Font(title.getFont().getName(), Font.PLAIN, 20));
    }

    //MODIFIES: this
    //EFFECTS: positions info text objects
    private void arrangeTexts() {
        title.setBounds(0,0,1920, 110);
        subTitle.setBounds(0,100,1920, 35);
        expressionSubtitle.setBounds(0,150,1920, 35);

        expressionRules.setBounds(0,190,1920, 25);
        for (int i = 0; i < expRules.length; i++) {
            expRules[i].setBounds(0,190 + (27 * i),1920, 20);
        }

        circuitSubtitle.setBounds(0,450,1920, 25);
        circuitRules.setBounds(0,477,1920, 20);
        circuitRules2.setBounds(0,503,1920, 20);

        about.setBounds(0,550,1920, 25);
        aboutInfo.setBounds(0,585,1920, 425);
    }

    //MODIFIES: this
    //EFFECTS: adds info text objects to the container
    public void addTexts() {
        container.add(title);
        container.add(subTitle);
        container.add(expressionSubtitle);
        container.add(expressionRules);
        for (int i = 0; i < expRules.length; i++) {
            container.add(expRules[i]);
        }

        container.add(circuitSubtitle);
        container.add(circuitRules);
        container.add(circuitRules2);

        container.add(about);
        aboutInfo.setVerticalAlignment(SwingConstants.TOP);
        container.add(aboutInfo);
    }




}
