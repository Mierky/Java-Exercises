import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable {
    public MesajServerClient mesaj;

    public Client(MesajServerClient mesaj) {
        this.mesaj = mesaj;
    }

    @Override
    public void run() {
        try(var socket = new Socket("localhost",1234);
            var out = new ObjectOutputStream(socket.getOutputStream());
            var in = new ObjectInputStream(socket.getInputStream())
        ){
            out.writeObject(mesaj);
            var fromServer = (Integer) in.readObject();
            System.out.println("Numar locuri pentru " + mesaj.getMesaj() + ": " + fromServer);

            out.writeObject(new MesajServerClient("exit"));

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
