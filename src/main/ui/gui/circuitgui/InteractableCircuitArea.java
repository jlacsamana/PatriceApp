package ui.gui.circuitgui;

import model.CircuitComponent;
import model.LogicalCircuit;
import model.LogicalExpression;
import ui.utility.SelectedCircPartListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.awt.Graphics;

//represents the area that allows the user to interact with a visual representation of the circuit
public class InteractableCircuitArea extends JPanel {
    LogicalCircuit localCircuit;
    LogicalExpression localExpression;
    CircuitComponentGUI currentlySelected = null;
    SelectedCircPartListener listener;
    ArrayList<CircuitComponentGUI> guiComponents;

    ArrayList<Connection> guiCompConnections;

    boolean isConnecting = true;

    //EFFECTS: creates a new area that allows the user to interact with the circuit (a visual representation of it)
    public InteractableCircuitArea() {
        guiComponents = new ArrayList<>();
        localCircuit = new LogicalCircuit();
        localExpression = new LogicalExpression();
        listener = new SelectedCircPartListener(this);
        guiCompConnections = new ArrayList<>();
        setLayout(null);
        setBackground(new Color(216, 235, 255));
        setBounds(400, 40, 1520, 1080);
        addCircOutput();
        initKeyListener();


    }

    //MODIFIES: this
    //EFFECTS: tries to add the CircuitComponentGUI's circuit part to the local Circuit, if successful,
    //adds the CircuitComponentGUI's UI component to the render queue, and the CircuitComponentGUI to currentlySelected
    //creates it in the middle of the view
    public void addNewGuiCircComp(CircuitComponentGUI toAdd) {
        if (localCircuit.addCircuitPart(toAdd.getAttachedCircComponent())) {
            toAdd.getAttachedUIElement().setBounds(960, 540, 150, 150);
            add(toAdd.getAttachedUIElement());
            toAdd.getAttachedUIElement().addMouseListener(listener);
            guiComponents.add(toAdd);
            toAdd.getAttachedUIElement().revalidate();
            toAdd.getAttachedUIElement().repaint();
        }
    }

    //MODIFIES: this
    //EFFECTS: gives the circuit output a visual representation
    public void addCircOutput() {
        CircuitComponentGUI circuitOut = new CircuitComponentGUI(localCircuit.getHead(), this);
        circuitOut.getAttachedUIElement().setBounds(960, 540, 75, 75);
        add(circuitOut.getAttachedUIElement());
        circuitOut.getAttachedUIElement().addMouseListener(listener);
        guiComponents.add(circuitOut);
        circuitOut.getAttachedUIElement().revalidate();
        circuitOut.getAttachedUIElement().repaint();
    }

    //MODIFIES: this
    //EFFECTS: establishes a connection between the two given circuit component GUIs at the given connection
    // (1 = input1, 2 = input2)
    //overrides an existing one
    public void establishConnection(CircuitComponentGUI from, CircuitComponentGUI to, int connection) {
        CircuitComponent disconnected = localCircuit.changeOutPutConnection(from.attachedCircComponent,
                to.attachedCircComponent, connection);
        System.out.println("wprking");
        if (disconnected != null) {
            Connection connectionToRemove = null;
            for (Connection c: guiCompConnections) {
                if (c.getOrigin().getAttachedCircComponent().equals(disconnected)
                        && c.getDestination().equals(to)) {
                    connectionToRemove = c;
                }
            }
            if (connectionToRemove != null) {
                guiCompConnections.remove(connectionToRemove);
            }
        }
        guiCompConnections.add(new Connection(from, to));

        revalidate();
        repaint();
    }

    //MODIFIES:
    //EFFECTS: disconnects the given circuit GUi component from all others that it is connected to
    public void disconnectFromAll(CircuitComponentGUI toDC) {
        ArrayList<Connection> toRemove = new ArrayList<>();
        for (Connection c: guiCompConnections) {
            if (c.getOrigin() == toDC || c.getDestination() == toDC) {
                toRemove.add(c);
            }
        }
        guiCompConnections.removeAll(toRemove);
    }

    @Override
    //EFFECTS: draws connections between connected circuit parts
    //derived and borrowed from:
    //https://stackoverflow.com/questions/31755985/draw-a-movable-line-between-two-java-graphics
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics.create();

        for (Connection c: guiCompConnections) {
            Point2D p1 = new Point2D.Double(c.getOrigin().getAttachedUIElement().getBounds().getCenterX(),
                    c.getOrigin().getAttachedUIElement().getBounds().getCenterY());
            Point2D p2 = new Point2D.Double(c.getDestination().getAttachedUIElement().getBounds().getCenterX(),
                    c.getDestination().getAttachedUIElement().getBounds().getCenterY());

            g2.draw(new Line2D.Double(p1, p2));

        }
        g2.dispose();
    }


    //EFFECTS: returns this circuit area's list of circuit parts
    public ArrayList<CircuitComponentGUI> getGuiComponents() {
        return guiComponents;
    }

    //EFFECTS: returns this circuit area's currently selected CircuitComponentGUI
    public CircuitComponentGUI getCurrentlySelected() {
        return currentlySelected;
    }

    //EFFECTS: sets the current selected circuitComponentGUI to currentlySelected
    public void setCurrentlySelected(CircuitComponentGUI currentlySelected) {
        this.currentlySelected = currentlySelected;
    }

    //EFFECTS: initialises keyboard listener
    //derived from:
    //https://stackoverflow.com/questions/26208179/keylistener-not-applying-to-jpanel-yes-it-is-focused
    private void initKeyListener() {
        InputMap keyBinds = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        keyBinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "deselect");
        keyBinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, false), "delete");
        keyBinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "moveUp");
        keyBinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "moveRight");
        keyBinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "moveDown");
        keyBinds.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "moveLeft");

        ActionMap keyActions = getActionMap();
        keyActions.put("deselect", new Deselect());
        keyActions.put("delete", new Delete());
        keyActions.put("moveUp", new MoveUp());
        keyActions.put("moveRight", new MoveRight());
        keyActions.put("moveDown", new MoveDown());
        keyActions.put("moveLeft", new MoveLeft());
    }

    //corresponds to deselect action
    private class Deselect extends AbstractAction {

        @Override
        //EFFECTS: deselects currently selected Circuit GUI
        public void actionPerformed(ActionEvent e) {
            currentlySelected = null;
        }
    }

    //corresponds to delete action
    private class Delete extends AbstractAction {

        @Override
        //EFFECTS: deletes currently selected Circuit GUI
        public void actionPerformed(ActionEvent e) {
            if (currentlySelected != null) {
                currentlySelected.deleteCircPart();
            }
        }
    }

    //corresponds to move up action
    private class MoveUp extends AbstractAction {

        @Override
        //EFFECTS: moves the currently selected Circuit GUI up
        public void actionPerformed(ActionEvent e) {
            if (currentlySelected != null) {
                currentlySelected.moveGuiPartUp();
            }
        }
    }

    //corresponds to move right action
    private class MoveRight extends AbstractAction {

        @Override
        //EFFECTS: moves the currently selected Circuit GUI right
        public void actionPerformed(ActionEvent e) {
            if (currentlySelected != null) {
                currentlySelected.moveGuiPartRight();
            }
        }
    }

    //corresponds to move down action
    private class MoveDown extends AbstractAction {

        @Override
        //EFFECTS: moves the currently selected Circuit GUI down
        public void actionPerformed(ActionEvent e) {
            if (currentlySelected != null) {
                currentlySelected.moveGuiPartDown();
            }
        }
    }

    //corresponds to move left action
    private class MoveLeft extends AbstractAction {

        @Override
        //EFFECTS: moves the currently selected Circuit GUI left
        public void actionPerformed(ActionEvent e) {
            if (currentlySelected != null) {
                currentlySelected.moveGuiPartLeft();
            }
        }
    }

    //represents a link between two circuit GUI components
    public class Connection {

        private CircuitComponentGUI from;
        private CircuitComponentGUI to;

        public Connection(CircuitComponentGUI from, CircuitComponentGUI to) {
            this.from = from;
            this.to = to;
        }

        public CircuitComponentGUI getOrigin() {
            return from;
        }

        public CircuitComponentGUI getDestination() {
            return to;
        }

    }

    //EFFECTS: returns this' local logical circuit
    public LogicalCircuit getLocalCircuit() {
        return localCircuit;
    }

    //EFFECTS: returns this' local logical expression
    public LogicalExpression getLocalExpression() {
        return localExpression;
    }

    //EFFECTS: sets to connection mode or disconnect mode
    public void setConnecting(boolean connecting) {
        isConnecting = connecting;
    }

    //EFFECTS: returns whether connecting mode is on or disconnect mode
    public boolean isConnecting() {
        return isConnecting;
    }

    //EFFECTS: returns the list of connections
    public ArrayList<Connection> getGuiCompConnections() {
        return guiCompConnections;
    }

}
