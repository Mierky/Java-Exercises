import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private static int PORT = 1234;

    private static void ComunicareServer(Socket socket){
        try(var out = new ObjectOutputStream(socket.getOutputStream());
            var in = new ObjectInputStream(socket.getInputStream())){

            while(true){
                var mesaj = (MesajServerClient)in.readObject();
                //System.out.println(mesaj);

                if(mesaj.getMesaj().equals("exit")){
                    break;
                }
                var list = (List<Aventura>) mesaj.getActiune();
                for(var valoare : list){
                    if(valoare.getDenumire().equals(mesaj.getMesaj())){
                        out.writeObject(valoare.getLocuriDisponibile());
                    }
                }
                /*out.writeObject(
                        list.stream()
                                .filter(valoare -> valoare.getDenumire().equals(mesaj.getMesaj()))
                                .map(Aventura::getLocuriDisponibile)
                );*/

            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try(var socket = new ServerSocket(PORT)){
            while(true){
                var acces = socket.accept();
                new Thread (()->ComunicareServer(acces)).start();
            }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
