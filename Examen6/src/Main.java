import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        List<Programare> programari = getProgramariFromTXT();
        programari.forEach(System.out::println);

        System.out.println("--------------CERINTA2--------------");
        int cursuri = (int) programari.stream().filter(tip -> tip.getTip().equals(Tip.CURS)).count();
        int seminarii = (int) programari.stream().filter(tip -> tip.getTip().equals(Tip.SEMINAR)).count();
        System.out.println("CURSURI: " + cursuri + "\nSEMINARII: " + seminarii);

        System.out.println("--------------CERINTA3--------------");

        var mapProgramari = programari.stream()
                .collect(Collectors.groupingBy(Programare::getDisciplina));

        programari.stream()
                .collect(Collectors.groupingBy(Programare::getDisciplina))
                .forEach((key,value) -> {
                    int curs = (int) value.stream().filter(tip -> tip.getTip().equals(Tip.CURS)).count();
                    int sem = (int) value.stream().filter(tip -> tip.getTip().equals(Tip.SEMINAR)).count();

                    if(curs == 1 && sem == 2){
                        System.out.println(key);
                        var array = new ArrayList<>(mapProgramari.get(key));
                        for(var element : array){
                            System.out.println(" " + element.getTip() + ", " + element.getZi() + ", " + element.getInterval());
                        }
                    }
                });
        //mapProgramari.keySet().stream().forEach(key -> System.out.println(mapProgramari.get(key)));
    }

    private static List<Programare> getProgramariFromTXT() throws IOException{
        try(var reader = new BufferedReader(new FileReader("date\\orar.txt"))){
            return reader.lines().map(linie -> {
                String[] date = linie.split(",");
                return new Programare(
                        date[0],
                        Integer.parseInt(date[1]),
                        Integer.parseInt(date[2]),
                        Tip.valueOf(date[3]),
                        date[4]);
            }).toList();
        }
    }
}