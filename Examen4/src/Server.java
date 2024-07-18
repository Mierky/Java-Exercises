import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Server {
    public static void server(Socket socket, List<Sectie> sectii){
        try(var out = new ObjectOutputStream(socket.getOutputStream());
            var in = new ObjectInputStream(socket.getInputStream())
        ) {
            while(true){
                var fromClient = (Integer) in.readObject();
                for(var sectie : sectii){
                    if(sectie.getCod() == fromClient){
                        out.writeObject(sectie.getNrLocuri());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("S-a finalizat comunicarea cu clientul: " + Thread.currentThread().getName());
        }
    }
}
