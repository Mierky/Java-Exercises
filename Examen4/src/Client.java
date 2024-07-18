import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLOutput;

public class Client{

    private static void start(Integer cod) {


        try(var socket = new Socket("localhost",Main.PORT);
            var out = new ObjectOutputStream(socket.getOutputStream());
            var in = new ObjectInputStream(socket.getInputStream())
        ) {
            out.writeObject(cod);
            var fromServer = (Integer)in.readObject();
            System.out.println("Numarul de locuri pentru sectia cu codul " + cod + ": " + fromServer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(()->start(1));
        t1.start();
        Thread t2 = new Thread(()->start(5));
        t2.start();
    }


}
