package model.CircuitIO;

import model.CircuitComponent;
import model.CircuitOutput;
import model.CircuitVariable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public abstract class CircuitComponentTest {
    CircuitComponent testCircComp;

    @BeforeEach
    public void setupTests(){

    }


    @Test
    public void testChangeOutputConnection() {
        CircuitComponent newOutConnection = new CircuitVariable();
        testCircComp.changeOutputConnection(newOutConnection);
        assertEquals(newOutConnection, testCircComp.getOutputConnection());
    }
}
