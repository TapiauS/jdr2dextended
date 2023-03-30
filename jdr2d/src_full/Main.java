

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import Control.ClientPart;
import Log.LogLevel;
import Log.Loggy;

public class Main {
    public static void main(String[] args) {
        try {
            ClientPart.launch();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


