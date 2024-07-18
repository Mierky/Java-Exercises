import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        String sourceTXT = "date\\bursa_tranzactii.txt";
        var listaPersoane = Database.getPersoaneFromDB();
        for(var p : listaPersoane) System.out.println(p);
        System.out.println("#".repeat(50));
        var listaTranzactie = citireTranzactiiDinTXT(sourceTXT);
        for(var t : listaTranzactie) System.out.println(t);

        System.out.println("-----------------CERINTA1------------------");
        System.out.println("Numarul total de clienti nerezidenti: " + listaPersoane.stream()
                .filter(persoana -> persoana.getCnp().startsWith("8") || persoana.getCnp().startsWith("9"))
                .count());

        System.out.println("-----------------CERINTA2------------------");
        listaTranzactie.stream()
                .collect(Collectors.groupingBy(Tranzactie::getSimbol, TreeMap::new, Collectors.counting()))
                .forEach((key,value) -> System.out.println(key + " -> " + value + " tranzactii"));

        System.out.println("-----------------CERINTA3------------------");

        try(var writer = new BufferedWriter(new FileWriter("date\\simboluri.txt"))){
            listaTranzactie.stream()
                    .map(Tranzactie::getSimbol)
                    .distinct()
                    .forEach(simbol -> {
                        try {
                            writer.write(simbol.toUpperCase());
                            writer.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        System.out.println("S-a finalizat crearea fisierului simboluri.txt");

        System.out.println("-----------------CERINTA4------------------");
        listaPersoane
                .forEach(persoana -> {
                    System.out.println(persoana.getNume());
                    listaTranzactie.stream()
                            .filter(tranzactie -> tranzactie.getCod()==persoana.getCod())
                            .collect(Collectors.groupingBy(Tranzactie::getSimbol,
                                    Collectors.summingDouble(tranzactie -> (double)tranzactie.getPret() * tranzactie.getCantitate())))
                            .forEach((key,value) -> System.out.println("  " + key+ " - " + value));
                });
    }

    private static List<Tranzactie> citireTranzactiiDinTXT(String source) throws IOException {
        try(var reader = new BufferedReader(new FileReader(source))){
            return reader.lines()
                    .map(linie -> {
                        String[] date = linie.split(",");
                        return new Tranzactie(
                                Integer.parseInt(date[0]),
                                date[1],
                                date[2],
                                Integer.parseInt(date[3]),
                                Float.parseFloat(date[4])
                        );
                    }).collect(Collectors.toList());
        }
    }
}