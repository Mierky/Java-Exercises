public class Programare implements Comparable<Programare>{
    private String disciplina;
    private int zi;
    private int interval;
    private Tip tip;
    private String formatie;

    public Programare(String disciplina, int zi, int interval, Tip tip, String formatie) {
        this.disciplina = disciplina;
        this.zi = zi;
        this.interval = interval;
        this.tip = tip;
        this.formatie = formatie;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public int getZi() {
        return zi;
    }

    public void setZi(int zi) {
        this.zi = zi;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public String getFormatie() {
        return formatie;
    }

    public void setFormatie(String formatie) {
        this.formatie = formatie;
    }

    @Override
    public String toString() {
        return "Programare{" +
                "disciplina='" + disciplina + '\'' +
                ", zi=" + zi +
                ", interval=" + interval +
                ", tip=" + tip +
                ", formatie='" + formatie + '\'' +
                '}';
    }

    @Override
    public int compareTo(Programare o) {
        if(this.zi < o.zi || (this.zi == o.zi && this.interval < o.interval))
            return -1;
        else return 1;
    }
}
