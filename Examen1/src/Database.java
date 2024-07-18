import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static String dburl = "jdbc:sqlite:date\\intretinere.db";

    public Database(String dburl) {
        this.dburl = dburl;
    }

    public static List<Apartament> getApartamente() throws SQLException {
        List<Apartament> apartamente= new ArrayList<>();
        try(var connection = DriverManager.getConnection(dburl);
            var statement = connection.createStatement();
            var cursor = statement.executeQuery("SELECT * FROM APARTAMENTE")){
                while(cursor.next()){
                    int numarApartament = cursor.getInt("NumarApartament");
                    int suprafata = cursor.getInt("Suprafata");
                    int numarPersoane = cursor.getInt("NumarPersoane");

                    apartamente.add(new Apartament(numarApartament,suprafata,numarPersoane));
                }
        }
        return apartamente;
    }

    public static Apartament getApartamentDupaNr(int nr) throws SQLException{
        try(var connection = DriverManager.getConnection(dburl);
            var statement = connection.prepareStatement("SELECT * FROM APARTAMENTE WHERE NumarApartament = " + nr)
        ){
            var result = statement.executeQuery();
            int nrPers = result.getInt("NumarPersoane");
            int suprafata = result.getInt("Suprafata");
            return new Apartament(nr,nrPers,suprafata);
        }
    }

    //----------------------------------------SERVER
    private static int PORT = 1234;

    private static void ServerConnection(Socket socket) {
        try(var out = new ObjectOutputStream(socket.getOutputStream());
            var in = new ObjectInputStream(socket.getInputStream())
        ) {
            while(true){
                var message = (Message)in.readObject();
                System.out.println("Current thread: " + Thread.currentThread().getName() + " received " + message);

                if(message.getMsj().equals("getApartament")){
                    out.writeObject(getApartamentDupaNr((int)message.getTask()));
                }
                else if (message.getMsj().equals("exit")){
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try(var server = new ServerSocket(PORT)) {
            while (true) {
                var socket  = server.accept();
                new Thread(()-> ServerConnection(socket)).start();
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
