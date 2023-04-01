package Logging;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class Jdr2dLogger {
   static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
   }

    public static final Logger LOGGER = Logger.getLogger(Jdr2dLogger.class.getName());


}
