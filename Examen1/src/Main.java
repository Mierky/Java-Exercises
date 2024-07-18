import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
//---------------------------------------INITIALIZARI---------------------------------------------------------
        String source = "C:\\Users\\Mihai\\IdeaProjects\\Examen1\\date\\intretinere_facturi.txt";
        List<Factura> facturi = getFacturiFromTXT(source);
        System.out.println(facturi);

        List<Apartament> apartamente = Database.getApartamente();
        System.out.println(apartamente);

System.out.println("-----------------------------------------CERINTA1-----------------------------------------------");
        //afisareNumarRepartizare(facturi);
        System.out.println(facturi.stream()
                .filter(factura -> factura.getRepartizare().equals("suprafata".toLowerCase())).count()
        + " facturi per suprafata");

        System.out.println(facturi.stream()
                .filter(factura -> factura.getRepartizare().equals("persoane".toLowerCase())).count()
                + " facturi per persoana");

System.out.println("-----------------------------------------CERINTA2-----------------------------------------------");
        afisareSumaRepartizare(facturi);
        System.out.println("P - " + facturi.stream()
                .filter(factura -> factura.getRepartizare().equals("persoane".toLowerCase()))
                .map(Factura::getValoare)
                .reduce(0.0,Double::sum));
        System.out.println("S - " + facturi.stream()
                .filter(factura -> factura.getRepartizare().equals("suprafata".toLowerCase()))
                .map(Factura::getValoare)
                .reduce(0.0,Double::sum));


System.out.println("-----------------------------------------CERINTA3-----------------------------------------------");
        System.out.println("Suprafata totala a apartamentelor este: " +
                apartamente.stream()
                        .map(Apartament::getSuprafata)
                        .reduce(0,Integer::sum));

System.out.println("-----------------------------------------CERINTA4-----------------------------------------------");
        int suprafata = apartamente.stream().map(Apartament::getSuprafata).reduce(0,Integer::sum);
        int numarpers = apartamente.stream().map(Apartament::getNumarPersoane).reduce(0,Integer::sum);
        System.out.printf("%16s %9s %7s %5s\n","Numar apartament","Suprafata","Persoane","Total");
        for(Apartament ap : apartamente){
            double pretApartament = 0, pretSuprafata = 0 ,pretPersoane = 0;

            for(Factura f : facturi){
                if(f.getRepartizare().equals("suprafata")){
                    pretSuprafata+=f.getValoare()*ap.getSuprafata();
                }
                else if(f.getRepartizare().equals("persoane")){
                    pretPersoane+=f.getValoare()*ap.getNumarPersoane();
                }
                else pretApartament += f.getValoare();
            }

            double total = pretPersoane/numarpers+pretSuprafata/suprafata+pretApartament/apartamente.size();
            System.out.printf("%16s%10d %8d %6.2f\n",ap.getNumarApartament(),ap.getSuprafata(),ap.getNumarPersoane(),total);
        }

System.out.println("-----------------------------------------CERINTA5-----------------------------------------------");
        Thread client1 = new Thread(new Client());
        client1.start();
        Thread client2 = new Thread(()->{
            try(var socket = new Socket("localhost",1234);
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream())
            ) {
                out.writeObject(new Message("getApartament",2));
                var getApartament = (Apartament)in.readObject();
                System.out.println("Apartamentul cu numarul " + getApartament.getNumarApartament() + " are " + getApartament.getNumarPersoane() + " persoane");

                out.writeObject(new Message("exit"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        client2.start();
    }

    public static List<Factura> getFacturiFromTXT(String source) throws IOException {

        try(var data = new BufferedReader(new FileReader(source))){
            return data.lines()
                    .map((linie)->{
                        String[] info = linie.split(",");
                        //if(info[1].equals("suprafata".toLowerCase()) || info[1].equals("persoane".toLowerCase())){
                            String denumire = info[0];
                            String repartizare = info[1];
                            double valoare = Double.parseDouble(info[2]);

                            return new Factura(denumire,repartizare,valoare);
                        //}
                        //return null;
                    }).toList();
        }
    }

/*    public static void afisareNumarRepartizare(List<Factura> facturi){
        facturi.stream()
                .collect(Collectors.groupingBy(Factura::getRepartizare,Collectors.counting()))
                .forEach((key,value) -> {
                    if(key.equals("suprafata".toLowerCase()) || key.equals("persoane".toLowerCase()))
                        System.out.println(key.toUpperCase() + ": " + value );
                });

    }*/

    public static void afisareSumaRepartizare(List<Factura> facturi){
        facturi.stream()
                .collect(Collectors.groupingBy(Factura::getRepartizare,Collectors.summingDouble(Factura::getValoare)))
                .forEach((key,value) -> {
                    if(key.equals("suprafata".toLowerCase()) || key.equals("persoane".toLowerCase()))
                        System.out.println(key.toUpperCase() + ": " + value );
                });
    }
}