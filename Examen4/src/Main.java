import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main {
    public static int PORT = 1234;
    public static void main(String[] args) throws IOException{
        String sourceJSON = "date\\sectii.json";
        String sourceTXT = "date\\pacienti.txt";
        var listSectii = getSectiiFromJSON(sourceJSON);
        for(var s : listSectii) System.out.println(s);
        System.out.println("#".repeat(40));
        var listPacienti = getPacientiFromTXT(sourceTXT);
        for(var p : listPacienti) System.out.println(p);

        System.out.println("-------------------CERINTA1--------------------");
        listSectii.stream().filter(sectie -> sectie.getNrLocuri()>10).forEach(System.out::println);
        System.out.println("-------------------CERINTA2--------------------");
        var sectie_varsta = listSectii.stream()
                .collect(Collectors.toMap(
                        pacienti -> (int)listPacienti.stream()
                                .filter(pacient -> pacient.getCod() == pacienti.getCod())
                                .mapToInt(Pacient::getVarsta)
                                .average()
                                .orElse(0),
                        sectie -> sectie
                ));

        sectie_varsta.keySet().stream()
                .sorted(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2.compareTo(o1);
                    }
                })
                .forEach(key -> System.out.println(sectie_varsta.get(key) + " | VARSTA MEDIE: " + key));

        System.out.println("-------------------CERINTA3--------------------");
        scriereJurnalTXT(listPacienti,listSectii);
        System.out.println("Jurnalul a fost scris cu succes!");

        System.out.println("-------------------CERINTA4--------------------");
        try(var socket = new ServerSocket(PORT)){
            while(true){
                var client = socket.accept();
                new Thread(()->Server.server(client,listSectii)).start();
            }
        }
    }

    private static List<Sectie> getSectiiFromJSON(String source) throws IOException {
        List<Sectie> sectii = new ArrayList<>();
        try(var file = new FileReader(source)){
            var jsonArray = new JSONArray(new JSONTokener(file));
            sectii = StreamSupport.stream(jsonArray.spliterator(),false)
                    .map(obj -> (JSONObject)obj)
                    .map(obj -> new Sectie(
                            obj.getInt("cod_sectie"),
                            obj.getString("denumire"),
                            obj.getInt("numar_locuri")
                    )).toList();
        }
        return sectii;
    }

    private static List<Pacient> getPacientiFromTXT(String source) throws IOException{
        try(var reader = new BufferedReader(new FileReader(source))){
            return reader.lines()
                    .map(linie->{
                        String[] date = linie.split(",");
                        return new Pacient(
                                Long.parseLong(date[0]),
                                date[1],
                                Integer.parseInt(date[2]),
                                Integer.parseInt(date[3])
                        );
                    }).collect(Collectors.toList());
        }
    }

    private static void scriereJurnalTXT(List<Pacient> listaPacienti, List<Sectie> listaSectii) throws IOException{
        try(var writer = new BufferedWriter(new FileWriter("date\\jurnal.txt"))){
            for(var sectie : listaSectii){
                var numar = listaPacienti.stream()
                        .filter(pacient -> pacient.getCod() == sectie.getCod())
                        .count();
                writer.write(sectie.getCod() + ","
                            + sectie.getDenumire() + ","
                            + numar);
                writer.newLine();
            }
        }
    }
}