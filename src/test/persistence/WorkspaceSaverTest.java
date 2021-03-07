package persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ui.PatriceApplication;
import ui.PatriceWorkspace;

import static org.junit.jupiter.api.Assertions.*;

public class WorkspaceSaverTest {
    PatriceWorkspace testWorkSpace;
    WorkspaceSaver testWorkSpaceServer;

    @BeforeEach
    public void setup(){
        testWorkSpace = new PatriceWorkspace("test", false);
        testWorkSpaceServer = testWorkSpace.getWorkspaceSaver();

    }

    @Test
    //tries to save a workspace file to an invalid path
    public void testSaveToFileInvalidPath() {

        String saveResult = testWorkSpaceServer.saveToFile("thicc\0thanos", testWorkSpace);
        assertEquals("specified path to save to is invalid", saveResult);
    }

    @Test
    //saves a file to a valid path normally
    public void testSaveToFileNormal() {
        //todo: implement
    }






}
