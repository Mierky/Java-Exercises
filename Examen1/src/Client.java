import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable{
    public void run(){
        try(var socket = new Socket("localhost",1234);
            var out = new ObjectOutputStream(socket.getOutputStream());
            var in = new ObjectInputStream(socket.getInputStream())
        ) {
            out.writeObject(new Message("getApartament",1));
            var getApartament = (Apartament)in.readObject();
            System.out.println("Apartamentul cu numarul " + getApartament.getNumarApartament() + " are " + getApartament.getNumarPersoane() + " persoane");

            out.writeObject(new Message("exit"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

