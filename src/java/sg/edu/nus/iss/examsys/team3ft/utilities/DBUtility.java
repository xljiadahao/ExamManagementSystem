package sg.edu.nus.iss.examsys.team3ft.utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author Lei
 */
public class DBUtility {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Using database pool to create the Connection.
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("ExamSysEJava");
            conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(DBUtility.class.getName()).log(Level.SEVERE, null, e);
        }
        return conn;
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                Logger.getLogger(DBUtility.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

}
