import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main {
    public static void main(String[] args) throws IOException {
        String pathJSON = "data\\aventuri.json";
        String pathTXT = "data\\rezervari.txt";
        var listaAventuri = getAventuriFromJson(pathJSON);
        System.out.println(listaAventuri);
        var listaRezervari = getRezervariFromTXT(pathTXT);
        System.out.println(listaRezervari);

        System.out.println("---------------------CERINTA1---------------------");
        listaAventuri.stream()
                .filter(locuri -> locuri.getLocuriDisponibile() >= 20)
                .forEach(System.out::println);

        System.out.println("---------------------CERINTA2---------------------");
        var mapAventuri = listaAventuri.stream()
                .collect(Collectors.toMap(Aventura::getCod, aventura -> aventura));
        for(var m : mapAventuri.entrySet()) System.out.println(m);

        var sumaRezervari = getTotalRezervari(pathTXT);
        System.out.println(sumaRezervari);

        for(var aventuraKey : mapAventuri.keySet()){
            if(sumaRezervari.containsKey(aventuraKey)){
                mapAventuri.get(aventuraKey).setLocuriDisponibile(mapAventuri.get(aventuraKey).getLocuriDisponibile() - sumaRezervari.get(aventuraKey));
            }
            System.out.println(mapAventuri.get(aventuraKey));
        }

        System.out.println("---------------------CERINTA3---------------------");
        scriereInTxtValoare(mapAventuri);

        System.out.println("---------------------CERINTA4---------------------");
        Thread thread = new Thread(new Client(new MesajServerClient("TIROLIANA",listaAventuri)));
        thread.start();
    }

    private static List<Aventura> getAventuriFromJson(String path) throws IOException {
        List<Aventura> aventuri = new ArrayList<>();
        try(var fisier = new FileReader(path)){
            var jsonArray = new JSONArray(new JSONTokener(fisier));
            aventuri = StreamSupport.stream(jsonArray.spliterator(),false)
                    .map(obj -> (JSONObject)obj)
                    .map(obj -> new Aventura(
                            obj.getInt("cod_aventura"),
                            obj.getString("denumire"),
                            obj.getDouble("tarif"),
                            obj.getInt("locuri_disponibile")
                    ))
                    .collect(Collectors.toList());
        }
        return aventuri;
    }

    private static Map<Integer,Integer> getTotalRezervari(String path) throws IOException{
        try(var reader = new BufferedReader(new FileReader(path))){
            return reader.lines()
                    .map(linie ->{
                        String[] data = linie.split(",");
                        var id = data[0];
                        var codAventura = Integer.parseInt(data[1]);
                        var nrPers = Integer.parseInt(data[2]);

                        return new Rezervare(id,codAventura,nrPers);
                    }).collect(Collectors.groupingBy(Rezervare::getCodAventura,Collectors.summingInt(Rezervare::getNrLocuri)));
        }
    }

    private static List<Rezervare> getRezervariFromTXT(String path) throws IOException{
        try(var reader = new BufferedReader(new FileReader(path))){
            return reader.lines()
                    .map(linie ->{
                        String[] data = linie.split(",");
                        var id = data[0];
                        var codAventura = Integer.parseInt(data[1]);
                        var nrPers = Integer.parseInt(data[2]);

                        return new Rezervare(id,codAventura,nrPers);
                    }).collect(Collectors.toList());
        }
    }

    private static void scriereInTxtValoare(Map<Integer,Aventura> mapValoare) throws IOException{
        try(var writer = new BufferedWriter(new FileWriter("data\\venituri.txt"))){
            mapValoare.values().stream()
                    .forEach(value -> {
                        try {
                            writer.write(value.getDenumire() + ","
                                    + value.getLocuriDisponibile() + ","
                                    + (value.getTarif() * value.getLocuriDisponibile()));
                            writer.newLine();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    });
        }
        System.out.println("S-a finalizat scrierea in fisier a veniturilor!");
    }
}