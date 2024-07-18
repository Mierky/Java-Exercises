import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static String sourceBD = "jdbc:sqlite:date\\bursa.db";

    public static List<Persoana> getPersoaneFromDB() throws SQLException {
        List<Persoana> persoane = new ArrayList<>();
        try(var connection = DriverManager.getConnection(sourceBD);
            var select = connection.createStatement();
            var cursor = select.executeQuery("SELECT * FROM PERSOANE")
        ){
            while(cursor.next()){
                persoane.add(new Persoana(
                   cursor.getInt("Cod"),
                   cursor.getString("CNP"),
                   cursor.getString("Nume")
                ));
            }
        }
        return persoane;
    }
}
