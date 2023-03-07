package Control;

import Control.ThreadDealer;
import Graphic.FullLogInterface;

import java.sql.SQLException;

public interface EventListenerWindow {
    public void update(FullLogInterface fullLogInterface) throws InterruptedException, SQLException;
}
