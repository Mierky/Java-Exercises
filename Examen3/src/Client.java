import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            try(var socket = new Socket("localhost", Main.port);
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream())
            ) {

                out.writeObject("Cibernetica");
                var fromServer = (Integer) in.readObject();
                System.out.println("-----------------------CERINTA4------------------------");
                System.out.println("Numarul de locuri disponibile pentru Cibernetica: " + fromServer);

            }  catch (Exception e) {
                System.out.println(e.getMessage());;
            }
        });
        t1.start();
    }
}
