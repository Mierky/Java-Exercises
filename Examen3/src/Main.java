import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static int port = 1111;

    public static void main(String[] args) throws Exception {
        var database = new DBHelper();
        var listSpecializari = database.getSpecializari();
        for(var s : listSpecializari) System.out.println(s);
        var sourceTXT = "date\\inscrieri.txt";
        System.out.println("#".repeat(50));
        var listInscrieri = getInscrieriFromTXT(sourceTXT);
        for(var s : listInscrieri) System.out.println(s);
        System.out.println("-----------------------CERINTA1------------------------");
        System.out.println("Locuri disponibile: " + listSpecializari.stream()
                .map(Specializare::getLocuri)
                .reduce(0,Integer::sum));

        System.out.println("-----------------------CERINTA2------------------------");
        var locuriRamase = listSpecializari.stream()
                                .map(specializare -> {
                                    int locuri = (int)listInscrieri.stream()
                                            .filter(inscriere -> inscriere.getCod() == specializare.getCod())
                                            .count();
                                    return new Specializare(specializare.getCod(),specializare.getDenumire(), specializare.getLocuri()-locuri);
                                }).toList();
        System.out.printf("%3s %10s %6s","Cod","Specializare","Locuri\n");
        for(var loc : locuriRamase){
            if(loc.getLocuri()>=10)
                System.out.println(loc.getCod() + " | " + loc.getDenumire() + " | " + loc.getLocuri());
        }

        System.out.println("-----------------------CERINTA3------------------------");
        setInscrieriJSON("date\\inscrieri_specializari.json",listSpecializari,listInscrieri);
        System.out.println("Fisierul json a fost finalizat!");

        System.out.println("-----------------------CERINTA4------------------------");
        try(var socket = new ServerSocket(port)){
            while(true){
                var acces = socket.accept();
                new Thread(()->database.comunicareServer(acces,locuriRamase)).start();
            }
        }
    }

    private static List<Inscriere> getInscrieriFromTXT(String source) throws IOException {
        try(var buffer = new BufferedReader(new FileReader(source))){
            return buffer.lines()
                    .map(linie->{
                        String[] date = linie.split(",");
                        return new Inscriere(
                                Long.parseLong(date[0]),
                                date[1],
                                Double.parseDouble(date[2]),
                                Integer.parseInt(date[3])
                        );
                    }).collect(Collectors.toList());
        }
    }

    private static void setInscrieriJSON(String path,List<Specializare> listaSpecializari, List<Inscriere> listaInscrieri) throws IOException {
        var jsonArray = new JSONArray();
        try(var source = new FileWriter(path)){
            for(var specializare : listaSpecializari){
                var jsonObject = new JSONObject();
                var inscrieri = listaInscrieri.stream()
                        .filter(inscriere -> inscriere.getCod() == specializare.getCod())
                        .count();
                var nota = listaInscrieri.stream()
                        .filter(inscriere -> inscriere.getCod() == specializare.getCod())
                        .map(Inscriere::getNotaBac)
                        .reduce(0.0,Double::sum);

                jsonObject.put("cod_specializare",specializare.getCod());
                jsonObject.put("denumire",specializare.getDenumire());
                jsonObject.put("numar_inscrieri",inscrieri);
                jsonObject.put("medie",nota/inscrieri);

                jsonArray.put(jsonObject);
            }
            jsonArray.write(source,3,0);
            //daca ma intereseaza ordinea in care sunt afisare trb sa folosesc LinkedHashMap in loc de JSONObject.
            //abia cand adaug in array il fac JSONObject || var jsonObject = new JSONObject(linkedHashMap);
        }
    }
}