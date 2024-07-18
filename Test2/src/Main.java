import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Produs p1 = new Produs("Samsung tv",Categorie.TV,900);
        System.out.println(p1);

        String source = "C:\\Users\\Mihai\\IdeaProjects\\Test2\\src\\produse.txt";
        System.out.println("Numar produse: " + numarProduse(source));

        System.out.println(citireTXT(source));
        System.out.println(citireTXTInMap(source));

        List<Produs> lista = citireTXT(source);
        afisareCategorii(lista);
        afisareProduseDupaCategorii(lista);

        System.out.println("================================");
        String sourceBinar = "C:\\Users\\Mihai\\IdeaProjects\\Test2\\src\\produseBin.bin";
        serializare(lista,sourceBinar);
        List<Produs> listBinar= deserializare(sourceBinar);
        System.out.println(listBinar);
    }

    private static int numarProduse(String source) throws IOException {
        try(var reader = new BufferedReader(new FileReader(source))){
            return (int)reader.lines().count();
        }
    }

    private static List<Produs> citireTXT(String source) throws IOException{
        try(var reader = new BufferedReader(new FileReader(source))){
            return reader.lines()
                    .map(linie ->{
                        String[] date = linie.split(",");
                        String nume = date[0];
                        Categorie categorie = Categorie.valueOf(date[1]);
                        int pret = Integer.parseInt(date[2]);

                        return new Produs(nume,categorie,pret);
                    }).collect(Collectors.toList());
        }
    }

    private static Map<Categorie,List<Produs>> citireTXTInMap(String source) throws IOException{
        try(var reader = new BufferedReader(new FileReader(source))){
            return reader.lines()
                    .map(linie ->{
                        String[] date = linie.split(",");
                        String nume = date[0];
                        Categorie categorie = Categorie.valueOf(date[1]);
                        int pret = Integer.parseInt(date[2]);

                        return new Produs(nume,categorie,pret);
                    }).collect(Collectors.groupingBy(Produs::getCategorie, Collectors.toList()));
        }
    }

    private static void afisareCategorii(List<Produs> lista){
        lista.stream()
                .collect(Collectors.groupingBy(Produs::getCategorie,
                        TreeMap::new, Collectors.counting()))
                .forEach((key,value) -> System.out.println(key + ": " + value));
    }

    private static void afisareProduseDupaCategorii(List<Produs> lista){
        lista.stream()
                .collect(Collectors.groupingBy(Produs::getCategorie,
                        TreeMap::new, Collectors.toList()))
                .forEach((tip,produse) ->{
                    System.out.print(tip + ": ");
                    for(var produs : produse){
                        System.out.print("{ "+produs.getNumeProdus()+" }");
                    }
                    System.out.println(" | COUNT: "+produse.size());
                });
    }

    private static void serializare(List<Produs> lista , String source) throws IOException{
        try(var obj = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(source)))){
            obj.writeObject(lista);
        }
    }

    private static List<Produs> deserializare(String source) throws IOException, ClassNotFoundException {
        try(var obj = new ObjectInputStream(new BufferedInputStream(new FileInputStream(source)))){
            return (List<Produs>)obj.readObject();
        }
    }
}