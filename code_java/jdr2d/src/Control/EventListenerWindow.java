package Control;

import Control.ThreadDealer;
import Graphic.FullLogInterface;

public interface EventListenerWindow {
    public void update(FullLogInterface fullLogInterface) throws InterruptedException;
}
