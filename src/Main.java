import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        Connection con = null;
        Statement stmt = null;
        ResultSet result = null;
        try {
           Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:file:src/db/endb/", "admin", "admin");
            stmt = con.createStatement();
            result = stmt.executeQuery("SELECT ID,WORD FROM PUBLIC.WORDS WHERE word LIKE '%sheer%'");

            while(result.next()){
                System.out.println(result.getInt("id") + " | " + result.getString("word"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }


    }
}

