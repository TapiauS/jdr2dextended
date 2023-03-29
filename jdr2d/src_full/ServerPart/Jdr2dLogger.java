package ServerPart;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class Jdr2dLogger {
    static {
        try {
            LogManager.getLogManager().readConfiguration(
                    Jdr2dLogger.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final Logger LOGGER = Logger.getLogger(Jdr2dLogger.class.getName());


}
