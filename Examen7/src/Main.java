import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main {
    public static void main(String[] args) throws Exception{
        var listaCandidati = getFromJSON();
        for(var candidat : listaCandidati){
            System.out.println(candidat.getCod_candidat() +
                    " | " + candidat.getNume_candidat() +
                    " | " + candidat.getMedia());

            candidat.getOptiuni().forEach(System.out::println);
            System.out.println("-".repeat(40));
        }
        var listaLicee = getLiceeFromTXT();
        for(var l : listaLicee) System.out.println(l);
        System.out.println("###################### CERINTA1 ######################");
        System.out.println("Numarul de candidati cu media mai mare sau egala cu 9: " +
                listaCandidati.stream()
                        .filter(candidat -> candidat.getMedia() >= 9)
                        .count());


        System.out.println("###################### CERINTA2 ######################");
        listaLicee.stream()
                .sorted((a,b) -> {
                    var value1 = a.getSpecializari().stream()
                            .map(Specializare::getNumar_locuri)
                            .reduce(0, Integer::sum);
                    var value2 = b.getSpecializari().stream()
                            .map(Specializare::getNumar_locuri)
                            .reduce(0, Integer::sum);

                    return Integer.compare(value2,value1);
                }).forEach(System.out::println);

    }

    private static List<Candidat> getFromJSON() throws IOException {
        List<Candidat> candidati = new ArrayList<>();
        try(var source = new FileReader("date\\canditati.json")){
            var jsonArray = new JSONArray(new JSONTokener(source));
            candidati = StreamSupport.stream(jsonArray.spliterator(), false)
                    .map(obj -> (JSONObject)obj)
                    .map(obj -> {
                        int cod_candidat = obj.getInt("cod_candidat");
                        String nume = obj.getString("nume_candidat");
                        double media = obj.getDouble("media");

                        JSONArray optiuni = obj.getJSONArray("optiuni");
                        var listaOptiuni = StreamSupport.stream(optiuni.spliterator(),false)
                                .map(object -> (JSONObject)object)
                                .map(object ->{
                                    int cod_liceu = object.getInt("cod_liceu");
                                    int cod_spec = object.getInt("cod_specializare");
                                    return new Optiune(cod_liceu,cod_spec);
                                }).collect(Collectors.toList());

                        return new Candidat(cod_candidat,nume,media,listaOptiuni);
                    }).collect(Collectors.toList());
        }
        return candidati;
    }

    private static List<Liceu> getLiceeFromTXT() throws IOException{
        List<Liceu> licee = new ArrayList<>();
        try(var reader = new BufferedReader(new FileReader("date\\licee.txt"))){
            String linie = reader.readLine();
            do{
                String[] date = linie.split(",");
                int cod = Integer.parseInt(date[0]);
                String nume = date[1];
                int nr = Integer.parseInt(date[2]);

                linie = reader.readLine();
                date = linie.split(",");
                List<Specializare> specializari = new ArrayList<>();
                for(int i = 0; i<nr * 2;i+=2){
                    int cod_spec = Integer.parseInt(date[i]);
                    int nr_locuri = Integer.parseInt(date[i+1]);
                    specializari.add(new Specializare(cod_spec,nr_locuri));
                }

                licee.add(new Liceu(cod,nume,specializari));
                linie = reader.readLine();
            }while(linie!=null);
        }
        return licee;
    }
}