package ServerPart.Control;

import java.io.IOException;

public interface JDRDSocket {
    void write(Object o) throws IOException;
    <T> T read() throws IOException,ClassNotFoundException;
}
