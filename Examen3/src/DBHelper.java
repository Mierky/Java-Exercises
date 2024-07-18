import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    public static String URL = "jdbc:sqlite:date\\facultate.db";

    public List<Specializare> getSpecializari() throws SQLException{
        List<Specializare> specializari = new ArrayList<>();
        try(var connection = DriverManager.getConnection(URL);
            var select = connection.createStatement();
            var cursor = select.executeQuery("SELECT * FROM SPECIALIZARI")
        ) {
            while(cursor.next()){
                specializari.add(new Specializare(
                        cursor.getInt("cod"),
                        cursor.getString("denumire"),
                        cursor.getInt("locuri")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return specializari;
    }

    public void comunicareServer(Socket socket,List<Specializare> lista) {
        try(var in = new ObjectInputStream(socket.getInputStream());
            var out = new ObjectOutputStream(socket.getOutputStream()
            )){

            while (true){
                var fromClient = (String) in.readObject();
                for(var element : lista){
                    if(element.getDenumire().equals(fromClient)){
                        out.writeObject(element.getLocuri());
                    }
                }
            }

            } catch (Exception e){
            System.out.println("Cerinta 4 a fost realizata!");
        }
    }

}
