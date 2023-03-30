package Logging;

import java.util.logging.Logger;

public abstract class Jdr2dLogger {
/*    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static final Logger LOGGER = Logger.getLogger(Jdr2dLogger.class.getName());


}
