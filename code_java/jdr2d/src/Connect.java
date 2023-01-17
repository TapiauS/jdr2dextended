import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://10.113.28.39:5432/jdr2d_simon", "stapiau", "Afpa54*");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
